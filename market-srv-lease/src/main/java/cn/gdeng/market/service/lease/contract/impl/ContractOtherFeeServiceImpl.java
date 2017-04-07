package cn.gdeng.market.service.lease.contract.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dto.lease.contract.ContractLeaseDTO;
import cn.gdeng.market.dto.lease.contract.ContractOthersFeeDTO;
import cn.gdeng.market.dto.lease.contract.ContractPaymentDTO;
import cn.gdeng.market.entity.lease.contract.ContractMainEntity;
import cn.gdeng.market.entity.lease.finance.FinanceFeeRecordEntity;
import cn.gdeng.market.enums.ContractChargingWaysEnum;
import cn.gdeng.market.enums.ContractPaymentCycleEnum;
import cn.gdeng.market.enums.ContractPaymentDayTypeEnum;
import cn.gdeng.market.enums.FinRemedyStatusEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.contract.ContractManageService;
import cn.gdeng.market.service.lease.contract.ContractOtherFeeService;
import cn.gdeng.market.service.lease.finance.FinanceFeeService;
import cn.gdeng.market.service.lease.settings.MarketExpenditureService;
import cn.gdeng.market.util.DateUtil;

/**
 * 合同其他费用约定服务接口实现类
 * @author Administrator
 *
 */
@Service(value="contractOtherFeeService")
public class ContractOtherFeeServiceImpl implements ContractOtherFeeService{

	@Resource
	private FinanceFeeService financeFeeService;
	
	@Resource
	private ContractManageService contractManageService;
	
	@Autowired
	private MarketExpenditureService marketExpenditureService;
	
	
	@Override
	public void generateOthersFee(ContractMainEntity entity,
			List<FinanceFeeRecordEntity> params, String user)
			throws BizException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractId", entity.getId());
		List<ContractOthersFeeDTO> othersFeeLists = contractManageService.findAllOthersFee(map);
		if(othersFeeLists.size() > 0){
			//生成其他费用应收款
			for(ContractOthersFeeDTO dto : othersFeeLists){
				int type = marketExpenditureService.getById(dto.getItemNameId()).getExpType();
				if(type == 1){
					// 元/日
					if(dto.getChargeUnit() == 1){
						generateFeesDay(entity.getStartLeasingDay(), entity.getEndLeasingDay(), Double.valueOf(dto.getTotal()), params, entity, user, dto);
					// 元/月
					}if(dto.getChargeUnit() == 2){ 
						generateFeesMonth(entity.getStartLeasingDay(), entity.getEndLeasingDay(), Double.valueOf(dto.getTotal()), params, entity, user, dto);
					// 元/季度
					}else if(dto.getChargeUnit() == 3){
						generarteFeesQuarterly(entity.getStartLeasingDay(), entity.getEndLeasingDay(), Double.valueOf(dto.getTotal()), params, entity, user, dto);
					// 元/半年
					}else if(dto.getChargeUnit() == 4){
						generateFeesHalfYear(entity.getStartLeasingDay(), entity.getEndLeasingDay(), Double.valueOf(dto.getTotal()), params, entity, user, dto);
					// 元/年
					}else if(dto.getChargeUnit() == 5){
						generarteFeesYear(entity.getStartLeasingDay(), entity.getEndLeasingDay(), Double.valueOf(dto.getTotal()), params, entity, user, dto);
					}
				}else if(type == 3){
					setFinanceFeeRecordEntity(entity, user, dto, params, null, null, Double.valueOf(dto.getTotal()), getRentalFirst(entity, dto));
				}
			}
		}
	}
	
	/**
	 * 按 元/天 计量单位生成应付款
	 */
	public void generateFeesDay(Date startDay, Date endDay, Double rental, List<FinanceFeeRecordEntity> params, 
			ContractMainEntity entity, String user, ContractOthersFeeDTO dto){
		double totalDays = DateUtil.getBetweenDays(startDay, endDay) + 1;
		for(int i=0; i<totalDays; i++){
			Date start = DateUtil.getStartOfDay(DateUtil.getAfterDate(startDay, i));
			Date end = DateUtil.getEndOfDay(DateUtil.getAfterDate(startDay, i));
			Date timeLimit = getTimeLimit(start, entity, dto);
			setFinanceFeeRecordEntity(entity, user, dto, params, start, end, rental, timeLimit);
		}
	}
	
	/**
	 * 按 元/月 计量单位生成应付款
	 */
	public void generateFeesMonth(Date startDay, Date endDay, Double rental, List<FinanceFeeRecordEntity> params, 
			ContractMainEntity entity, String user, ContractOthersFeeDTO dto){
		//根据日期查出年
		int startYear = DateUtil.getYearOfDate(startDay);
		int endYear = DateUtil.getYearOfDate(endDay);
		//根据日期查出月
		int startMonth = DateUtil.getMonthOfDate(startDay);
		int endMonth = DateUtil.getMonthOfDate(endDay);
		//根据日期查具体月份的天数
		int startDays = DateUtil.getDaysByDate(startDay);
		int endDays = DateUtil.getDaysByDate(endDay);
		//根据日期查出日
		int currentDayStart = DateUtil.getDayOfDate(startDay);
		int currentDayEnd = DateUtil.getDayOfDate(endDay);
		
		//前后日期不跨年
		if(startYear == endYear){
			if((endMonth > startMonth)){
				//计算开始月的租金
				double startFee = rental * (startDays-currentDayStart + 1) / startDays;
				Date startStart = startDay;
				Date startEnd = DateUtil.getLastDayOfMonth(startYear, startMonth);
				Date startTimeLimit = getTimeLimit(startStart, entity, dto);
				setFinanceFeeRecordEntity(entity, user, dto, params, startStart, startEnd, startFee, startTimeLimit);
				
				//除去首尾月份的应收款费用
				for(int i=startMonth+1; i<endMonth; i++){
					Date start = DateUtil.formateDate(startYear + "-" + i + "-" + "01");
					Date end = DateUtil.getLastDayOfMonth(startYear, i);
					double fee = rental;
					Date timeLimit = getTimeLimit(start, entity, dto);
					setFinanceFeeRecordEntity(entity, user, dto, params, start, end, fee, timeLimit);
				}

				//计算结束月的租金
				double endFee = rental * currentDayEnd / endDays;
				Date endStart = DateUtil.formateDate(endYear + "-" + endMonth + "-01");
				Date endEnd = endDay;
				Date endTimeLimit = getTimeLimit(endStart, entity, dto);
				setFinanceFeeRecordEntity(entity, user, dto, params, endStart, endEnd, endFee, endTimeLimit);
			}else if(endMonth == startMonth){
				double endFee = rental * (DateUtil.getBetweenDays(startDay, endDay) + 1) / startDays;
				Date timeLimit = getTimeLimit(startDay, entity, dto);
				setFinanceFeeRecordEntity(entity, user, dto, params, startDay, endDay, endFee, timeLimit);
			}
		//跨年日期
		}else{
			//计算开始月的租金
			double startFee = rental * (startDays-currentDayStart + 1) / startDays;
			Date startStart = startDay;
			Date startEnd = DateUtil.getLastDayOfMonth(startYear, startMonth);
			Date startTimeLimit = getTimeLimit(startStart, entity, dto);
			setFinanceFeeRecordEntity(entity, user, dto, params, startStart, startEnd, startFee, startTimeLimit);
			
			//上一年
			for(int i=startMonth+1; i<=12; i++){
				double fee = rental;
				Date start = DateUtil.formateDate(startYear + "-" + i + "-" + "01");
				Date end = DateUtil.getLastDayOfMonth(startYear, i);
				Date timeLimit = getTimeLimit(start, entity, dto);
				setFinanceFeeRecordEntity(entity, user, dto, params, start, end, fee, timeLimit);
			}
			
			for(int i=startYear+1; i<endYear; i++){
				for(int j=1; j<12; j++){
					double fee = rental;
					Date start = DateUtil.formateDate(i + "-" + j + "-" + "01");
					Date end = DateUtil.getLastDayOfMonth(i, j);
					Date timeLimit = getTimeLimit(start, entity, dto);
					setFinanceFeeRecordEntity(entity, user, dto, params, start, end, fee, timeLimit);
				}
			}

			//下一年
			for(int i=1; i<endMonth; i++){
				double fee = rental;
				Date start = DateUtil.formateDate(endYear + "-" + i + "-" + "01");
				Date end = DateUtil.getLastDayOfMonth(endYear, i);
				Date timeLimit = getTimeLimit(start, entity, dto);
				setFinanceFeeRecordEntity(entity, user, dto, params, start, end, fee, timeLimit);
			}

			//计算结束月的租金
			double endFee = rental * currentDayEnd / endDays;
			Date endStart = DateUtil.formateDate(endYear + "-" + endMonth + "-" + "01");
			Date endEnd = endDay;
			Date endTimeLimit = getTimeLimit(endStart, entity, dto);
			setFinanceFeeRecordEntity(entity, user, dto, params, endStart, endEnd, endFee, endTimeLimit);
		}
	}
	
	/**
	 * 按 元/季度  的计量单位生成应收款
	 */
	public void generarteFeesQuarterly(Date startDay, Date endDay, Double rental, List<FinanceFeeRecordEntity> params, 
			ContractMainEntity entity, String user, ContractOthersFeeDTO dto){
		//根据日期查出年
		int startYear = DateUtil.getYearOfDate(startDay);
		int endYear = DateUtil.getYearOfDate(endDay);
		
		//根据日期查出月
		int startMonth = DateUtil.getMonthOfDate(startDay);
		int endMonth = DateUtil.getMonthOfDate(endDay);
		//根据月份获取当前第几季度
		int startQuarter = getQuarterByMonth(startMonth);
		int endQuarter = getQuarterByMonth(endMonth);
		
		//前后日期不跨年
		if(startYear == endYear){
			putParamsQuarter(startQuarter, endQuarter, startYear, startDay, endDay, rental, params, entity, user, dto);
		//前后跨年	
		}else{
			putParamsQuarter(startQuarter, 4, startYear, startDay, DateUtil.formateDate(startYear+"-12-31"), rental, params, entity, user, dto);

			for(int i=startYear+1; i<endYear; i++){
				for(int j=1; j<5; j++){
					if(j==1){
						double fee =  rental;
						Date start = DateUtil.formateDate(i+"-01-01");
						Date end = DateUtil.formateDate(i+"-03-31");
						Date timeLimit = getTimeLimit(start, entity, dto);
						setFinanceFeeRecordEntity(entity, user, dto, params, start, end, fee, timeLimit);
					}else if(j==2){
						double fee =  rental;
						Date start = DateUtil.formateDate(i+"-04-01");
						Date end = DateUtil.formateDate(i+"-06-30");
						Date timeLimit = getTimeLimit(start, entity, dto);
						setFinanceFeeRecordEntity(entity, user, dto, params, start, end, fee, timeLimit);
					}else if(j==3){
						double fee =  rental;
						Date start = DateUtil.formateDate(i+"-07-01");
						Date end = DateUtil.formateDate(i+"-09-30");
						Date timeLimit = getTimeLimit(start, entity, dto);
						setFinanceFeeRecordEntity(entity, user, dto, params, start, end, fee, timeLimit);
					}else if(j==4){
						double fee =  rental;
						Date start = DateUtil.formateDate(i+"-10-01");
						Date end = DateUtil.formateDate(i+"-12-31");
						Date timeLimit = getTimeLimit(start, entity, dto);
						setFinanceFeeRecordEntity(entity, user, dto, params, start, end, fee, timeLimit);
					}
				}
			}
			
			putParamsQuarter(1, endQuarter, endYear, DateUtil.formateDate(endYear+"-01-01"), endDay, rental, params, entity, user, dto);
		}
	}
	
	/**
	 * 按季度计量单位收取应收款中不完整年份的处理
	 */
	public void putParamsQuarter(int startQuarter, int endQuarter, int year, Date startDay, Date endDay, double rental, List<FinanceFeeRecordEntity> params, ContractMainEntity entity, String user, ContractOthersFeeDTO dto){
		//前后日期属于一个季度
		if(startQuarter == endQuarter){
			double days = DateUtil.getBetweenDays(startDay, endDay) + 1;
			double fee = rental / getDaysByQuarterAndYear(startQuarter, year) * days;
			Date timeLimit = getTimeLimit(startDay, entity, dto);
			setFinanceFeeRecordEntity(entity, user, dto, params, startDay, endDay, fee, timeLimit);
		//前后日期跨越4个季度
		}else if((endQuarter - startQuarter) == 3){
			for(int i=1; i<5; i++){
				Date start = null;
				Date end = null;
				if(i==1){
					start = startDay;
				}else{
					start = DateUtil.formateDate(year+"-"+((i-1)*3+1)+"-01");
				}
				if(i==4){
					end = endDay;
				}else{
					end = DateUtil.getLastDayOfMonth(year, i*3);
				}
				double finalDays = DateUtil.getBetweenDays(start, end) + 1;
				double totalDays = getDaysByQuarterAndYear(i, year);
				double fee =  rental * finalDays / totalDays;
				Date timeLimit = getTimeLimit(start, entity, dto);
				setFinanceFeeRecordEntity(entity, user, dto, params, start, end, fee, timeLimit);
			}

		//前后日期跨越3个季度
		}else if((endQuarter - startQuarter) == 2){
			if(startQuarter == 1){
				for(int i=1; i<4; i++){
					Date start = null;
					Date end = null;
					if(i == 1){
						start = startDay;
					}else{
						start = DateUtil.formateDate(year+"-"+((i-1)*3+1)+"-01");
					}
					if(i == 3){
						end = endDay;
					}else{
						end = DateUtil.getLastDayOfMonth(year, i*3);
					}
					double finalDays = DateUtil.getBetweenDays(start, end) + 1;
					double totalDays = getDaysByQuarterAndYear(i, year);
					double fee =  rental  / totalDays * finalDays;	
					Date timeLimit = getTimeLimit(start, entity, dto);
					setFinanceFeeRecordEntity(entity, user, dto, params, start, end, fee, timeLimit);
				}
			}else if(startQuarter == 2){
				for(int i=2; i<5; i++){
					Date start = null;
					Date end = null;
					if(i == 2){
						start = startDay;
					}else{
						start = DateUtil.formateDate(year+"-"+((i-1)*3+1)+"-01");
					}
					if(i == 4){
						end = endDay;
					}else{
						end = DateUtil.getLastDayOfMonth(year, i*3);
					}
					double finalDays = DateUtil.getBetweenDays(start, end) + 1;
					double totalDays = getDaysByQuarterAndYear(i, year);
					double fee =  rental  / totalDays * finalDays;
					Date timeLimit = getTimeLimit(start, entity, dto);
					setFinanceFeeRecordEntity(entity, user, dto, params, start, end, fee, timeLimit);
				}
			}

		//前后日期跨越2个季度	
		}else if((endQuarter - startQuarter) == 1){
			if(startQuarter == 1){
				for(int i=1; i<3; i++){
					Date start = null;
					Date end = null;
					if(i == 1){
						start = startDay;
					}else{
						start = DateUtil.formateDate(year+"-"+((i-1)*3+1)+"-01");
					}
					if(i == 2){
						end = endDay;
					}else{
						end = DateUtil.getLastDayOfMonth(year, i*3);
					}
					double finalDays = DateUtil.getBetweenDays(start, end) + 1;
					double totalDays = getDaysByQuarterAndYear(i, year);
					double fee =  rental  / totalDays * finalDays;	
					Date timeLimit = getTimeLimit(start, entity, dto);
					setFinanceFeeRecordEntity(entity, user, dto, params, start, end, fee, timeLimit);
				}
			}else if(startQuarter == 2){
				for(int i=2; i<4; i++){
					Date start = null;
					Date end = null;
					if(i == 2){
						start = startDay;
					}else{
						start = DateUtil.formateDate(year+"-"+((i-1)*3+1)+"-01");
					}
					if(i == 3){
						end = endDay;
					}else{
						end = DateUtil.getLastDayOfMonth(year, i*3);
					}
					double finalDays = DateUtil.getBetweenDays(start, end) + 1;
					double totalDays = getDaysByQuarterAndYear(i, year);
					double fee =  rental  / totalDays * finalDays;
					Date timeLimit = getTimeLimit(start, entity, dto);
					setFinanceFeeRecordEntity(entity, user, dto, params, start, end, fee, timeLimit);
				}
			}else if(startQuarter == 3){
				for(int i=3; i<5; i++){
					Date start = null;
					Date end = null;
					if(i == 3){
						start = startDay;
					}else{
						start = DateUtil.formateDate(year+"-"+((i-1)*3+1)+"-01");
					}
					if(i == 4){
						end = endDay;
					}else{
						end = DateUtil.getLastDayOfMonth(year, i*3);
					}
					double finalDays = DateUtil.getBetweenDays(start, end) + 1;
					double totalDays = getDaysByQuarterAndYear(i, year);
					double fee =  rental  / totalDays * finalDays;
					Date timeLimit = getTimeLimit(start, entity, dto);
					setFinanceFeeRecordEntity(entity, user, dto, params, start, end, fee, timeLimit);
				}
			}
		}
	}
	
	/**
	 * 获取当前月所在的季度
	 */
	public int getQuarterByMonth(int month) {
		if (month >= 1 && month <= 3) // 1-3月
			return 1;
		else if (month >= 4 && month <= 6) // 4-6月
			return 2;
		else if (month >= 7 && month <= 9) // 7-9月
			return 3;
		else
			// 10-12月
			return 4;
	}
	
	/**
	 * 根据当前季度和当前年份获取每个季度天数
	 */
	public double getDaysByQuarterAndYear(int quarter, int year){
		double days = 0;
		switch(quarter){
			case 1:days = DateUtil.getBetweenDays(year+"-01-01", year+"-03-31", "yyyy-MM-dd") + 1;break;
			case 2:days = DateUtil.getBetweenDays(year+"-04-01", year+"-06-30", "yyyy-MM-dd") + 1;break;
			case 3:days = DateUtil.getBetweenDays(year+"-07-01", year+"-9-30", "yyyy-MM-dd") + 1;break;
			case 4:days = DateUtil.getBetweenDays(year+"-10-01", year+"-12-31", "yyyy-MM-dd") + 1;break;
		}
		return days;
	}
	
	/**
	 * 根据年份获取一年总天数
	 */
	public double getTotalDaysOfYear(int year){
		return DateUtil.getBetweenDays(year+"-01-01", year+"-12-31", "yyyy-MM-dd") + 1;
	}
	
	/**
	 * 按 元/半年  计量单位生成应收款
	 */
	public void generateFeesHalfYear(Date startDay, Date endDay, Double rental, List<FinanceFeeRecordEntity> params,
			ContractMainEntity entity, String user, ContractOthersFeeDTO dto){
		//根据日期查出年
		int startYear = DateUtil.getYearOfDate(startDay);
		int endYear = DateUtil.getYearOfDate(endDay);
		
		//根据日期查出月
		int startMonth = DateUtil.getMonthOfDate(startDay);
		int endMonth = DateUtil.getMonthOfDate(endDay);
		
		if(startYear == endYear){
			halfYearPublicMethod(startMonth, endMonth, startYear, startDay, endDay, rental, params, entity, user, dto);
		//跨年处理方法
		}else{
			halfYearPublicMethod(startMonth, 12, startYear, startDay, DateUtil.formateDate(startYear+"-12-31"), rental, params, entity, user, dto);
			
			for(int i=startYear+1; i<endYear; i++){
				for(int j=1; j<3; j++){
					double fee =  rental;
					Date timeLimit = getTimeLimit(startDay, entity, dto);
					setFinanceFeeRecordEntity(entity, user, dto, params, startDay, endDay, fee, timeLimit);
				}
			}
			
			halfYearPublicMethod(1, endMonth, endYear, DateUtil.formateDate(endYear+"-01-01"), endDay, rental, params, entity, user, dto);
		}
	}
	
	/**
	 * 按元/半年/平计量单位 中不足一年的完整处理方法
	 */
	public void halfYearPublicMethod(int startMonth, int endMonth, int year, Date startDay, Date endDay, double rental, List<FinanceFeeRecordEntity> params, ContractMainEntity entity, String user, ContractOthersFeeDTO dto){
		if(startMonth>6 || endMonth<7){
			double finalDays = DateUtil.getBetweenDays(startDay, endDay) + 1;
			double totalDays = 0;
			if(startMonth>6){
				totalDays = DateUtil.getBetweenDays(DateUtil.formateDate(year+"-07-01"), DateUtil.formateDate(year+"-12-31")) + 1;
			}else if(endMonth<7){
				totalDays = DateUtil.getBetweenDays(DateUtil.formateDate(year+"-01-01"), DateUtil.formateDate(year+"-06-30")) + 1;
			}
			double fee =  rental / totalDays * finalDays;
			Date timeLimit = getTimeLimit(startDay, entity, dto);
			setFinanceFeeRecordEntity(entity, user, dto, params, startDay, endDay, fee, timeLimit);
		}else{
			double startFinalDays = DateUtil.getBetweenDays(startDay, DateUtil.formateDate(year+"-06-30")) + 1;
			double startTotalDays = DateUtil.getBetweenDays(DateUtil.formateDate(year+"-01-01"), DateUtil.formateDate(year+"-06-30")) + 1;
			double startFee =  rental / startTotalDays * startFinalDays;
			Date startTimeLimit = getTimeLimit(startDay, entity, dto);
			setFinanceFeeRecordEntity(entity, user, dto, params, startDay, DateUtil.formateDate(year+"-06-30"), startFee, startTimeLimit);
			
			double endFinalDays = DateUtil.getBetweenDays(DateUtil.formateDate(year+"-07-01"), endDay) + 1;
			double endTotalDays = DateUtil.getBetweenDays(DateUtil.formateDate(year+"-07-01"), DateUtil.formateDate(year+"-12-31")) + 1;
			double endFee = rental / endTotalDays * endFinalDays;
			Date endTimeLimit = getTimeLimit(DateUtil.formateDate(year+"-07-01"), entity, dto);
			setFinanceFeeRecordEntity(entity, user, dto, params, DateUtil.formateDate(year+"-07-01"), endDay, endFee, endTimeLimit);
		}
	}
	
	/**
	 * 按 元/年  的计量单位生成应收款
	 */
	public void generarteFeesYear(Date startDay, Date endDay, Double rental, List<FinanceFeeRecordEntity> params, 
			ContractMainEntity entity, String user, ContractOthersFeeDTO dto){
		//根据日期查出年
		int startYear = DateUtil.getYearOfDate(startDay);
		int endYear = DateUtil.getYearOfDate(endDay);
		
		if(startYear == endYear){
			double finalDays = DateUtil.getBetweenDays(startDay, endDay) + 1;
			double totalDays = getTotalDaysOfYear(startYear);
			double fee =  rental / totalDays * finalDays;
			Date timeLimit = getTimeLimit(startDay, entity, dto);
			setFinanceFeeRecordEntity(entity, user, dto, params, startDay, endDay, fee, timeLimit);
		}else{
			//开始年份的租金
			double startFinalDays = DateUtil.getBetweenDays(startDay, DateUtil.formateDate(startYear + "-12-31")) + 1;
			double startTotalDays = getTotalDaysOfYear(startYear);
			double startFee =  rental / startTotalDays * startFinalDays;
			Date startStart = startDay;
			Date startEnd = DateUtil.formateDate(startYear + "-12-31");
			Date startTimeLimit = getTimeLimit(startStart, entity, dto);
			setFinanceFeeRecordEntity(entity, user, dto, params, startStart, startEnd, startFee, startTimeLimit);
			
			for(int i=startYear+1; i<endYear; i++){
				double fee =  rental;
				Date start = DateUtil.formateDate(i+"-01-01");
				Date end = DateUtil.formateDate(i+"-12-31");
				Date timeLimit = getTimeLimit(start, entity, dto);
				setFinanceFeeRecordEntity(entity, user, dto, params, start, end, fee, timeLimit);
			}
			
			//结尾年份的租金
			double endFinalDays = DateUtil.getBetweenDays(DateUtil.formateDate(endYear + "-01-01"), endDay) + 1;
			double endTotalDays = getTotalDaysOfYear(endYear);
			double endFee =  rental / endTotalDays * endFinalDays;
			Date endStart = DateUtil.formateDate(endYear + "-01-01");
			Date endEnd = endDay;
			Date endTimeLimit = getTimeLimit(endStart, entity, dto);
			setFinanceFeeRecordEntity(entity, user, dto, params, endStart, endEnd, endFee, endTimeLimit);
		}
	}
	
	public void setFinanceFeeRecordEntity(ContractMainEntity entity, String user, ContractOthersFeeDTO dto, 
			List<FinanceFeeRecordEntity> params, Date start, Date end, double fee, Date timeLimit){
		FinanceFeeRecordEntity record = new FinanceFeeRecordEntity();
		record.setFeeItemId(dto.getItemNameId());
		record.setFeeItemTypeId(dto.getItemCategoryId().toString());
		record.setFreightBasisId(dto.getFreightBasisId());
		record.setContractVersion(entity.getId());
		record.setContractNo(entity.getContractNo());
		record.setFundType("0");
		record.setIsDeteled("0");
		record.setIsRemedy(FinRemedyStatusEnum.NO.getCode());
		record.setStatus("0");
		record.setCreateTime(new Date());
		record.setCreateUserId(user);
		record.setUpdateTime(new Date());
		record.setUpdateUserId(user);
		record.setAccountPayable(fee);
		record.setStartTime(start);
		record.setEndTime(end);
		record.setTimeLimit(timeLimit);
		record.setMarketId(entity.getMarketId());
		params.add(record);
	}
	
	/**
	 * 获取应收款记录的缴费期限
	 */
	public Date getTimeLimit(Date start, ContractMainEntity entity, ContractOthersFeeDTO dto){
		int year = DateUtil.getYearOfDate(start);
		int month = DateUtil.getMonthOfDate(start);
		Byte paymentDays = entity.getPaymentDays();
		//如果主合同的租金约定为总金额（没有缴费期限约定），其他费用的统一按缴费期限填为空
		if(paymentDays == null){
			return null;
		}
		//当前季度
		int quarter = getQuarterByMonth(DateUtil.getMonthOfDate(start));
		Date timeLimit = null;
		//当其他费用的计费单位为   元/日 时，不按照合同约定的缴费周期来计算
		if(dto.getChargeUnit() == 1){
			timeLimit = start;
		}else{
			//月
			if(entity.getPaymentCycle().equals(ContractPaymentCycleEnum.MONTH.getCode())){
				//期初
				if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.BEFORE.getCode())){
					timeLimit = DateUtil.formateDate(year+"-"+month+"-"+paymentDays);
				//期末
				}else if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.LAST.getCode())){
					timeLimit = DateUtil.getDateBefore(DateUtil.getLastDayOfMonth(start), paymentDays);
				//下个期初	
				}else if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.NEXT_BEFORE.getCode())){
					timeLimit = DateUtil.formateDate(year+"-"+(month+1)+"-"+paymentDays);
				}
			//季度
			}else if(entity.getPaymentCycle().equals(ContractPaymentCycleEnum.SEASON.getCode())){
				//期初
				if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.BEFORE.getCode())){
					switch(quarter){
						case 1: timeLimit = DateUtil.formateDate(year+"-"+"01-"+paymentDays); break;
						case 2: timeLimit = DateUtil.formateDate(year+"-"+"04-"+paymentDays); break;
						case 3: timeLimit = DateUtil.formateDate(year+"-"+"07-"+paymentDays); break;
						case 4: timeLimit = DateUtil.formateDate(year+"-"+"10-"+paymentDays); break;
					}
				//期末
				}else if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.LAST.getCode())){
					switch(quarter){
					case 1: timeLimit = DateUtil.formateDate(year+"-"+"03-"+(31-paymentDays)); break;
					case 2: timeLimit = DateUtil.formateDate(year+"-"+"06-"+(30-paymentDays)); break;
					case 3: timeLimit = DateUtil.formateDate(year+"-"+"09-"+(30-paymentDays)); break;
					case 4: timeLimit = DateUtil.formateDate(year+"-"+"12-"+(31-paymentDays)); break;
				}
				//下个期初	
				}else if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.NEXT_BEFORE.getCode())){
					switch(quarter){
					case 1: timeLimit = DateUtil.formateDate(year+"-"+"04-"+paymentDays); break;
					case 2: timeLimit = DateUtil.formateDate(year+"-"+"07-"+paymentDays); break;
					case 3: timeLimit = DateUtil.formateDate(year+"-"+"10-"+paymentDays); break;
					case 4: timeLimit = DateUtil.formateDate((year+1)+"-"+"01-"+paymentDays); break;
				}
				}
			//半年	
			}else if(entity.getPaymentCycle().equals(ContractPaymentCycleEnum.HALF_YEAR.getCode())){
				//期初
				if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.BEFORE.getCode())){
					if(month <=6 ){
						timeLimit = DateUtil.formateDate(year+"-01-"+paymentDays);
					}else{
						timeLimit = DateUtil.formateDate(year+"-07-"+paymentDays);
					}
				//期末
				}else if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.LAST.getCode())){
					if(month <=6 ){
						timeLimit = DateUtil.formateDate(year+"-06-"+(30-paymentDays));
					}else{
						timeLimit = DateUtil.formateDate(year+"-12-"+(31-paymentDays));
					}
				//下个期初	
				}else if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.NEXT_BEFORE.getCode())){
					if(month <=6 ){
						timeLimit = DateUtil.formateDate(year+"-07-"+paymentDays);
					}else{
						timeLimit = DateUtil.formateDate((year+1)+"-01-"+paymentDays);
					}
				}
			//年
			}else if(entity.getPaymentCycle().equals(ContractPaymentCycleEnum.YEAR.getCode())){
				//期初
				if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.BEFORE.getCode())){
					timeLimit = DateUtil.formateDate(year+"-01-"+paymentDays);
				//期末
				}else if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.LAST.getCode())){
					timeLimit = DateUtil.formateDate(year+"-12-"+(31-paymentDays));
				//下个期初	
				}else if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.NEXT_BEFORE.getCode())){
					timeLimit = DateUtil.formateDate((year+1)+"-01-"+paymentDays);
				}
			}
		}
		return timeLimit;
	}
	
	
	public Date getRentalFirst(ContractMainEntity entity, ContractOthersFeeDTO dto){
		Date timeLimit = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractId", entity.getId());
		if(entity.getChargingWays().equals(ContractChargingWaysEnum.TOTAL.getCode())){
			List<ContractPaymentDTO> paymentlists = contractManageService.getFirstPayment(map);
			if(paymentlists.size() > 0){
				timeLimit = paymentlists.get(0).getPaymentTime();
			}
		}else{
			List<ContractLeaseDTO> leaselists = contractManageService.getFirstLease(map);
			timeLimit = getTimeLimitRental(leaselists.get(0).getStartDay(), entity);
		}
		return timeLimit;
	}
	
	/**
	 * 获取周期租金约定的缴费期限
	 * @param start
	 * @param entity
	 * @return
	 */
	public Date getTimeLimitRental(Date start, ContractMainEntity entity){
		int year = DateUtil.getYearOfDate(start);
		int month = DateUtil.getMonthOfDate(start);
		Byte paymentDays = entity.getPaymentDays();
		//当前季度
		int quarter = getQuarterByMonth(DateUtil.getMonthOfDate(start));
		Date timeLimit = null;
		//月
		if(entity.getPaymentCycle().equals(ContractPaymentCycleEnum.MONTH.getCode())){
			//期初
			if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.BEFORE.getCode())){
				timeLimit = DateUtil.formateDate(year+"-"+month+"-"+paymentDays);
			//期末
			}else if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.LAST.getCode())){
				timeLimit = DateUtil.getDateBefore(DateUtil.getLastDayOfMonth(start), paymentDays);
			//下个期初	
			}else if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.NEXT_BEFORE.getCode())){
				timeLimit = DateUtil.formateDate(year+"-"+(month+1)+"-"+paymentDays);
			}
		//季度
		}else if(entity.getPaymentCycle().equals(ContractPaymentCycleEnum.SEASON.getCode())){
			//期初
			if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.BEFORE.getCode())){
				switch(quarter){
					case 1: timeLimit = DateUtil.formateDate(year+"-"+"01-"+paymentDays); break;
					case 2: timeLimit = DateUtil.formateDate(year+"-"+"04-"+paymentDays); break;
					case 3: timeLimit = DateUtil.formateDate(year+"-"+"07-"+paymentDays); break;
					case 4: timeLimit = DateUtil.formateDate(year+"-"+"10-"+paymentDays); break;
				}
			//期末
			}else if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.LAST.getCode())){
				switch(quarter){
				case 1: timeLimit = DateUtil.formateDate(year+"-"+"03-"+(31-paymentDays)); break;
				case 2: timeLimit = DateUtil.formateDate(year+"-"+"06-"+(30-paymentDays)); break;
				case 3: timeLimit = DateUtil.formateDate(year+"-"+"09-"+(30-paymentDays)); break;
				case 4: timeLimit = DateUtil.formateDate(year+"-"+"12-"+(31-paymentDays)); break;
			}
			//下个期初	
			}else if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.NEXT_BEFORE.getCode())){
				switch(quarter){
				case 1: timeLimit = DateUtil.formateDate(year+"-"+"04-"+paymentDays); break;
				case 2: timeLimit = DateUtil.formateDate(year+"-"+"07-"+paymentDays); break;
				case 3: timeLimit = DateUtil.formateDate(year+"-"+"10-"+paymentDays); break;
				case 4: timeLimit = DateUtil.formateDate((year+1)+"-"+"01-"+paymentDays); break;
			}
			}
		//半年	
		}else if(entity.getPaymentCycle().equals(ContractPaymentCycleEnum.HALF_YEAR.getCode())){
			//期初
			if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.BEFORE.getCode())){
				if(month <=6 ){
					timeLimit = DateUtil.formateDate(year+"-01-"+paymentDays);
				}else{
					timeLimit = DateUtil.formateDate(year+"-07-"+paymentDays);
				}
			//期末
			}else if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.LAST.getCode())){
				if(month <=6 ){
					timeLimit = DateUtil.formateDate(year+"-06-"+(30-paymentDays));
				}else{
					timeLimit = DateUtil.formateDate(year+"-12-"+(31-paymentDays));
				}
			//下个期初	
			}else if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.NEXT_BEFORE.getCode())){
				if(month <=6 ){
					timeLimit = DateUtil.formateDate(year+"-07-"+paymentDays);
				}else{
					timeLimit = DateUtil.formateDate((year+1)+"-01-"+paymentDays);
				}
			}
		//年
		}else if(entity.getPaymentCycle().equals(ContractPaymentCycleEnum.YEAR.getCode())){
			//期初
			if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.BEFORE.getCode())){
				timeLimit = DateUtil.formateDate(year+"-01-"+paymentDays);
			//期末
			}else if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.LAST.getCode())){
				timeLimit = DateUtil.formateDate(year+"-12-"+(31-paymentDays));
			//下个期初	
			}else if(entity.getPaymentDayType().equals(ContractPaymentDayTypeEnum.NEXT_BEFORE.getCode())){
				timeLimit = DateUtil.formateDate((year+1)+"-01-"+paymentDays);
			}
			
		}
		return timeLimit;
	}
	
}
