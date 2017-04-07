package cn.gdeng.market.lease.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dto.lease.contract.ContractFreeDTO;
import cn.gdeng.market.dto.lease.contract.ContractLeaseDTO;
import cn.gdeng.market.dto.lease.contract.ContractMainDTO;
import cn.gdeng.market.dto.lease.contract.ContractOthersFeeDTO;
import cn.gdeng.market.dto.lease.contract.ContractPaymentDTO;
import cn.gdeng.market.dto.lease.finance.FinanceFeeReceivedDTO;
import cn.gdeng.market.dto.lease.finance.FinanceFeeRecordDTO;
import cn.gdeng.market.entity.lease.contract.ContractChangeEntity;
import cn.gdeng.market.entity.lease.contract.ContractStatementsEntity;
import cn.gdeng.market.enums.ChangeReasionEnum;
import cn.gdeng.market.enums.ContractBillingAreaEnum;
import cn.gdeng.market.enums.StatementsTypeEnum;

/**
 * 打印数据转换工具类
 * @author dengjianfeng
 *
 */
@Service
public class PrintConvertDataUtil {

	/**
	 * 合同基本信息转换
	 * @param contractMainDTO
	 * @param dataMap
	 */
	public void convertContractMainToMap(ContractMainDTO contractMainDTO, Map<String, String> dataMap) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年 MM月dd日");
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		
		dataMap.put("${leasingResource}", contractMainDTO.getLeasingResource());
		dataMap.put("${contractNo}", contractMainDTO.getContractNo());
		dataMap.put("${partA}", contractMainDTO.getPartyA());
		dataMap.put("${partB}", contractMainDTO.getPartyB());
		dataMap.put("${customerName}", contractMainDTO.getCustomerName());
		
		String shopName = contractMainDTO.getShopName();
		if(shopName != null) {
			dataMap.put("${shopName}", shopName);
		}
		
		// 起租日期
		if(contractMainDTO.getStartLeasingDay() != null){
			dataMap.put("${startLeasingDay}", sf.format(contractMainDTO.getStartLeasingDay()));
		}
		
		// 租赁结束日期
		if(contractMainDTO.getEndLeasingDay() != null){
			dataMap.put("${endLeasingDay}", sf.format(contractMainDTO.getEndLeasingDay()));
		}

		// 延迟交租罚金（元/天）
		double leasingForfeit = contractMainDTO.getLeasingForfeit() == null ? 0 : contractMainDTO.getLeasingForfeit();
		if(leasingForfeit != 0) {
			dataMap.put("${leasingForfeit}", nf.format(leasingForfeit));
			
			BigDecimal leasingForfeitCN = new BigDecimal(leasingForfeit);
			dataMap.put("${leasingForfeitCN}", NumberToCN.number2CNMontrayUnit(leasingForfeitCN)); 
		}

		// 延迟还铺罚金（元/天）
		double shopForfeit = contractMainDTO.getShopForfeit() == null ? 0 : contractMainDTO.getShopForfeit();
		if(shopForfeit != 0) {
			dataMap.put("${shopForfeit}", nf.format(shopForfeit));
			
			BigDecimal shopForfeitCN = new BigDecimal(shopForfeit);
			dataMap.put("${shopForfeitCN}", NumberToCN.number2CNMontrayUnit(shopForfeitCN));
		}

		dataMap.put("${categoryName}", contractMainDTO.getCategoryName());
		
		// 可租面积
		double freeArea = contractMainDTO.getFreeArea() == null ? 0 : contractMainDTO.getFreeArea();
		if(freeArea != 0) {
			dataMap.put("${freeArea}", nf.format(freeArea));
		}
		
		// 建筑面积
		double floorArea = contractMainDTO.getFloorArea() == null ? 0 : contractMainDTO.getFloorArea();
		if(floorArea != 0) {
			dataMap.put("${floorArea}", nf.format(floorArea));
		}
		
		// 套内面积
		double innerArea = contractMainDTO.getInnerArea() == null ? 0 : contractMainDTO.getInnerArea();
		if(innerArea != 0) {
			dataMap.put("${innerArea}", nf.format(innerArea));
		}
		
		//计费面积
		dataMap.put("${billingArea}", contractMainDTO.getBillAreaString());
		byte billingArea = contractMainDTO.getBillingArea() == null ? -1 : contractMainDTO.getBillingArea();
		if(billingArea == ContractBillingAreaEnum.FLOOR_AREA.getCode().byteValue()){
			if(freeArea != 0) {
				dataMap.put("${area}", nf.format(floorArea));
			}
		}
		else if(billingArea == ContractBillingAreaEnum.FREE_AREA.getCode().byteValue()){
			if(freeArea != 0) {
				dataMap.put("${area}", nf.format(freeArea));
			}
		}
		else if(billingArea == ContractBillingAreaEnum.INNER_AREA.getCode().byteValue()){
			if(freeArea != 0) {
				dataMap.put("${area}", nf.format(innerArea));
			}
		}
		
		// 收费方式
		dataMap.put("${chargingWays}", contractMainDTO.getChargingWaysString());
		
		// 收费方式为：约定总金额时，合同总金额
		double totalAmt = contractMainDTO.getTotalAmt() == null ? 0 : contractMainDTO.getTotalAmt();
		if(totalAmt != 0) {
			dataMap.put("${totalAmt}", nf.format(totalAmt));
		
			BigDecimal totalAmtCN = new BigDecimal(totalAmt);
			dataMap.put("${totalAmtCN}", NumberToCN.number2CNMontrayUnit(totalAmtCN));
		}
		
		dataMap.put("${additionalTerms}", contractMainDTO.getAdditionalTerms());
		dataMap.put("${paymentCycle}", contractMainDTO.getPaymentCycleString());
		
		//　缴费日期
		String paymentDays = contractMainDTO.getPaymentDays() == null ? "" : contractMainDTO.getPaymentDays().toString();
		String paymentDay = contractMainDTO.getPaymentDayTypeString() + paymentDays + "天";
		dataMap.put("${paymentDayType}", paymentDay);
	
		// 经办人
		dataMap.put("${trustees}", contractMainDTO.getTrustees());
		// 经办日期（签约日期）
		if(contractMainDTO.getDateOfContract() != null){
			dataMap.put("${dateOfContract}", sf.format(contractMainDTO.getDateOfContract()));
		}
		
		// 押金
		double contractDeposit = contractMainDTO.getContractDeposit() == null ? 0 : Double.parseDouble(contractMainDTO.getContractDeposit());
		if(contractDeposit != 0){
			dataMap.put("${contractDeposit}", nf.format(contractDeposit));
			
			BigDecimal contractDepositCN = new BigDecimal(contractDeposit);
			dataMap.put("${contractDepositCN}", NumberToCN.number2CNMontrayUnit(contractDepositCN));
		}
	}
	
	/**
	 * 收费方式为：按周期收费时，租赁约定
	 * @param leaseList
	 * @return
	 */
	public void convertLeaseListToMap(List<ContractLeaseDTO> leaseList, Map<String, String> dataMap) {
		if(CollectionUtils.isEmpty(leaseList)){
			return;
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);

		for(int i = 0, len = leaseList.size(); i < len; i++){
			ContractLeaseDTO dto = leaseList.get(i);
			// 起始日期
			if(dto.getStartDay() != null){
				dataMap.put("${leaseStartDay"+(i+1)+"}", sf.format(dto.getStartDay()));
			}

			// 截止时间
			if(dto.getEndDay() != null){
				dataMap.put("${leaseEndDay"+(i+1)+"}", sf.format(dto.getEndDay()));
			}
			
			// 计费单位
			String billingUnitStr = dto.getBillingUnitStr();
			dataMap.put("${billingUnit"+(i+1)+"}", billingUnitStr);
			
			// 租金单价
			double unitPrice = dto.getUnitPrice() == null ? 0 : dto.getUnitPrice();
			if(unitPrice != 0) {
				dataMap.put("${unitPrice"+(i+1)+"}", nf.format(unitPrice));
			}
			
			// 计费面积
			double billingArea = dto.getBillingArea() == null ? 0 : dto.getBillingArea();
			if(billingArea != 0) {
				dataMap.put("${billingArea"+(i+1)+"}", nf.format(billingArea));
			}
			
			// 租金
			double rental = dto.getRental() == null ? 0 : dto.getRental();
			if(rental != 0) {
				dataMap.put("${rental"+(i+1)+"}", nf.format(rental));
	
				BigDecimal rentalCN = new BigDecimal(rental);
				dataMap.put("${rentalCN"+(i+1)+"}", NumberToCN.number2CNMontrayUnit(rentalCN));
			}
		}
	}
	
	/**
	 * 按约定总金额-缴费约定记录
	 * @param paymentList
	 * @return
	 */
	public void convertPaymentListToMap(List<ContractPaymentDTO> paymentList, Map<String, String> dataMap){
		if(CollectionUtils.isEmpty(paymentList)){
			return;
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		
		for(int i = 0, len = paymentList.size(); i < len; i++){
			ContractPaymentDTO dto = paymentList.get(i);
			// 缴费期限
			if(dto.getPaymentTime() != null){
				dataMap.put("${payTime"+(i+1)+"}", sf.format(dto.getPaymentTime()));
			}

			// 缴费金额
			double paymentAmt = dto.getPaymentAmt() == null ? 0 : dto.getPaymentAmt();
			if(paymentAmt != 0) {
				dataMap.put("${paymentAmt"+(i+1)+"}", nf.format(paymentAmt));
			
				BigDecimal paymentAmtCN = new BigDecimal(paymentAmt);
				dataMap.put("${paymentAmtCN"+(i+1)+"}", NumberToCN.number2CNMontrayUnit(paymentAmtCN));
			}
		}
	}
	
	/**
	 * 免租约定
	 * @param freeList
	 * @return
	 */
	public void convertFreeListToMap(List<ContractFreeDTO> freeList, Map<String, String> dataMap) {
		if(CollectionUtils.isEmpty(freeList)){
			return;
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
		
		for(int i = 0, len = freeList.size(); i < len; i++){
			ContractFreeDTO dto = freeList.get(i);
			
			if(dto.getStartDay() != null){
				dataMap.put("${freeStartDay"+(i+1)+"}", sf.format(dto.getStartDay()));
			}
	
			if(dto.getEndDay() != null){
				dataMap.put("${freeEndDay"+(i+1)+"}", sf.format(dto.getEndDay()));
			}
			
			double freeMonths = dto.getFreeMonths() == null ? 0 : dto.getFreeMonths();
			if(freeMonths != 0) {
				dataMap.put("${freeMonths"+(i+1)+"}", dto.getFreeMonths().toString());
			}
			
			double freeDays = dto.getFreeDays() == null ? 0 : dto.getFreeDays();
			if(freeDays != 0) {
				dataMap.put("${freeDays"+(i+1)+"}", dto.getFreeDays().toString());
			}
		}
	}
	
	/**
	 * 其他费约定记录
	 * @param othersFeeList
	 * @return
	 */
	public void convertOthersFeeListToMap(List<ContractOthersFeeDTO> othersFeeList, Map<String, String> dataMap) {
		if(CollectionUtils.isEmpty(othersFeeList)){
			return;
		}
		
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		
		for(int i = 0, len = othersFeeList.size(); i < len; i++){
			ContractOthersFeeDTO dto = othersFeeList.get(i);
		
			String itemCategory = dto.getItemCategory() == null ? "" : dto.getItemCategory();
			dataMap.put("${feeItemCategory"+(i+1)+"}", itemCategory);
			
			String itemName = dto.getItemName() == null ? "" : dto.getItemName();
			dataMap.put("${feeItemName"+(i+1)+"}", itemName);
			
			String freightBasis = dto.getFreightBasis() == null ? "" : dto.getFreightBasis();
			dataMap.put("${feeFreightBasis"+(i+1)+"}", freightBasis);
			double total =  Double.valueOf(dto.getTotal())== null ? 0 :Double.valueOf(dto.getTotal());
			if(total != 0) {
				dataMap.put("${feeTotal"+(i+1)+"}", nf.format(total));
			
				BigDecimal feeTotalCN = new BigDecimal(total);
				dataMap.put("${feeTotalCN"+(i+1)+"}", NumberToCN.number2CNMontrayUnit(feeTotalCN));
			}
			
			String chargeUnitName = dto.getChargeUnitName() == null ? "" : dto.getChargeUnitName();
			dataMap.put("${chargeUnitName"+(i+1)+"}", chargeUnitName);
		}
	}
	
	/**
	 * 合同结算信息转换
	 * @param statements
	 * @param dataMap
	 */
	public void convertContractStatementsToMap(ContractStatementsEntity statements, Map<String, String> dataMap){
		if(statements == null){
			return;
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
		
		// 经办人
		String statementTrustees = statements.getTrustees() == null ? "" : statements.getTrustees();
		dataMap.put("${statementHandler}", statementTrustees);
		
		// 经办时间
		String reviewedTime = "";
		if(statements.getReviewedTime() != null){
			reviewedTime = sf.format(statements.getReviewedTime());
		}
		dataMap.put("${statementHandleTime}", reviewedTime);
		
		// 结算类型
		String statementsType = StatementsTypeEnum.getNameByCode(statements.getStatementsType());
		dataMap.put("${statementType}", statementsType);
		
		// 退还保证金
		double deposit = statements.getDeposit() == null ? 0 : statements.getDeposit();
		if(deposit != 0) {
			dataMap.put("${deposit}", nf.format(deposit));
	
			BigDecimal depositCN = new BigDecimal(deposit);
			dataMap.put("${depositCN}", NumberToCN.number2CNMontrayUnit(depositCN));
		}
		
		// 罚款金额
		double forfeit = statements.getForfeit() == null ? 0 : statements.getForfeit();
		if(forfeit != 0) {
			dataMap.put("${forfeit}", nf.format(forfeit)) ;
		
			BigDecimal forfeitCN = new BigDecimal(forfeit);
			dataMap.put("${forfeitCN}", NumberToCN.number2CNMontrayUnit(forfeitCN));		
		}
		
		// 结算说明
		String statementsInfo = statements.getInfo() == null ? "" : statements.getInfo();
		dataMap.put("${statementRemark}", statementsInfo);
	}
	
	/**
	 * 应收款信息转换
	 */
	public void convertFeeRecordToMap(FinanceFeeRecordDTO dto, Map<String, String> dataMap) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		
		String feeItemName = dto.getFeeItemName() == null ? "" : dto.getFeeItemName();
		dataMap.put("${feeItemName}", feeItemName);
		
		// 缴费期限
		Date timeLimit = dto.getTimeLimit();
		if(timeLimit != null) {
			dataMap.put("${timeLimit}", sf.format(timeLimit));
		}
		
		// 计费起始日期
		Date startTime = dto.getStartTime();
		if(startTime != null) {
			dataMap.put("${feeStartTime}", sf.format(startTime));
		}
		
		// 计费结束时间
		Date endTime = dto.getEndTime();
		if(endTime != null) {
			dataMap.put("${feeEndTime}", sf.format(endTime));
		}
		
		// 应付金额
		double accountPayable = dto.getAccountPayable() == null ? 0 : dto.getAccountPayable();
		if(accountPayable != 0) {
			dataMap.put("${accountPayable}", nf.format(accountPayable));
			
			BigDecimal accountPayableCN = new BigDecimal(accountPayable);
			dataMap.put("${accountPayableCN}", NumberToCN.number2CNMontrayUnit(accountPayableCN));
		}
		
		// 优惠金额
		double accountRebate = dto.getAccountRebate() == null ? 0 : dto.getAccountRebate();
		if(accountRebate != 0) {
			dataMap.put("${accountRebate}", nf.format(accountRebate));
			
			BigDecimal accountRebateCN = new BigDecimal(accountRebate);
			dataMap.put("${accountRebateCN}", NumberToCN.number2CNMontrayUnit(accountRebateCN));
		}
		
		// 当前时间
		dataMap.put("${currentDate}", sf.format(new Date()));
	}
	
	/**
	 * 已收款信息转换
	 * @param dto
	 * @param dataMap
	 */
	public void convertFeeReceived(FinanceFeeReceivedDTO dto, Map<String, String> dataMap) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年 MM月dd日");
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		
		//费项名称
		String feeItemName = dto.getFeeItemName() == null ? "" : dto.getFeeItemName();
		dataMap.put("${feeItemName}", feeItemName);
		
		// 缴费期限
		Date timeLimit = dto.getTimeLimit();
		if(timeLimit != null) {
			dataMap.put("${timeLimit}", sf.format(timeLimit));
		}
		
		// 计费起始日期
		Date startTime = dto.getStartTime();
		if(startTime != null) {
			dataMap.put("${feeStartTime}", sf.format(startTime));
		}
		
		// 计费结束时间
		Date endTime = dto.getEndTime();
		if(endTime != null) {
			dataMap.put("${feeEndTime}", sf.format(endTime));
		}
		
		// 应付金额
		double accountPayable = dto.getAccountPayable() == null ? 0 : dto.getAccountPayable();
		if(accountPayable != 0) {
			dataMap.put("${accountPayable}", nf.format(accountPayable));
			
			BigDecimal accountPayableCN = new BigDecimal(accountPayable);
			dataMap.put("${accountPayableCN}", NumberToCN.number2CNMontrayUnit(accountPayableCN));
		}
		
		// 实付金额
		double accountPayed = dto.getAccountPayed() == null ? 0 : dto.getAccountPayed();
		if(accountPayed != 0) {
			dataMap.put("${accountPayed}", nf.format(accountPayed));
			
			BigDecimal accountPayedCN = new BigDecimal(accountPayed);
			dataMap.put("${accountPayedCN}", NumberToCN.number2CNMontrayUnit(accountPayedCN));
		}
		
		// 优惠金额
		double accountRebate = dto.getAccountRebate() == null ? 0 : dto.getAccountRebate();
		if(accountRebate != 0) {
			dataMap.put("${accountRebate}", nf.format(accountRebate));
			
			BigDecimal accountRebateCN = new BigDecimal(accountRebate);
			dataMap.put("${accountRebateCN}", NumberToCN.number2CNMontrayUnit(accountRebateCN));
		}
		
		// 付款人
		String payer = dto.getPayer() == null ? "" : dto.getPayer();
		dataMap.put("${payer}", payer);
		
		// 付款人手机
		String payerPhone = dto.getPhone() == null ? "" : dto.getPhone();
		dataMap.put("${payerPhone}", payerPhone);
		
		// 经办人
		String feeHandler = dto.getAgent() == null ? "" : dto.getAgent();
		dataMap.put("${feeHandler}", feeHandler);
		
		// 制单时间/经办时间
		Date receiveDate = dto.getReceiveDate();
		if(receiveDate != null) {
			dataMap.put("${feeReceiveDate}", sf.format(receiveDate));
		}
		
		// 收款说明
		String feeRemark = dto.getRemark() == null ? "" : dto.getRemark();
		dataMap.put("${feeRemark}", feeRemark);
	}

	/**
	 * 合同变更信息转换
	 * @param changeEntity
	 */
	public void convertContractChangeToMap(ContractChangeEntity changeEntity, Map<String, String> dataMap) {
		String changeReason = ChangeReasionEnum.getNameByCode(changeEntity.getChangeReasion());
		dataMap.put("${changeReason}", changeReason);
		
		String changeHandler = changeEntity.getTrustees() == null ? "" : changeEntity.getTrustees();
		dataMap.put("${changeHandler}", changeHandler);
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年 MM月dd日");
		Date changeHandleTime = changeEntity.getReviewedTime();
		if(changeHandleTime != null) {
			dataMap.put("${changeHandleTime}", sf.format(changeHandleTime));
		}
	}
}
