package cn.gdeng.market.service.lease.contract.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.lease.contract.ContractFreeDTO;
import cn.gdeng.market.dto.lease.contract.ContractLeaseDTO;
import cn.gdeng.market.dto.lease.contract.ContractPaymentDTO;
import cn.gdeng.market.dto.lease.settings.MarketExpenditureDTO;
import cn.gdeng.market.entity.lease.contract.ContractMainEntity;
import cn.gdeng.market.entity.lease.finance.FinanceFeeRecordEntity;
import cn.gdeng.market.enums.BillingUnitEnum;
import cn.gdeng.market.enums.ContractChargingWaysEnum;
import cn.gdeng.market.enums.ContractPaymentCycleEnum;
import cn.gdeng.market.enums.ContractPaymentDayTypeEnum;
import cn.gdeng.market.enums.FinRemedyStatusEnum;
import cn.gdeng.market.service.lease.contract.ContractManageService;
import cn.gdeng.market.service.lease.contract.ContractRentService;
import cn.gdeng.market.service.lease.finance.FinanceFeeService;
import cn.gdeng.market.service.lease.settings.MarketExpenditureService;
import cn.gdeng.market.util.DateUtil;

/**
 * 合同管理服务接口实现类
 * @author Administrator
 *
 */
@Service(value="contractRentService")
public class ContractRentServiceImpl implements ContractRentService {
	@Autowired
	private BaseDao<?> baseDao;

	@Resource
	private FinanceFeeService financeFeeService;
	
	@Resource
	private ContractManageService contractManageService;
	
	@Resource
	private MarketExpenditureService marketExpenditureService;
	
	@Override
	public List<FinanceFeeRecordEntity> generateRental(ContractMainEntity entity,
			List<FinanceFeeRecordEntity> params, String user) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractId", entity.getId());
		//按周期生成租金应收款记录
		if(entity.getChargingWays().equals(ContractChargingWaysEnum.CYCLE.getCode())){
			List<ContractLeaseDTO> leaselists = contractManageService.findAllLease(map);
			List<ContractFreeDTO> freeLists = contractManageService.findAllFree(map);
			if(leaselists.size()  > 0){
				for(ContractLeaseDTO dto : leaselists){
					//计费单位为：元/月/平
					if(dto.getBillingUnit().equals(BillingUnitEnum.PER_MONTH.getCode())){
						generateFeesMonth(dto.getStartDay(), dto.getEndDay(), dto.getRental(), params, freeLists, entity, user);
				    //计费单位为：元/季度/平
					}else if(dto.getBillingUnit().equals(BillingUnitEnum.PER_SEASON.getCode())){
						generarteFeesQuarterly(dto.getStartDay(), dto.getEndDay(), dto.getRental(), params, freeLists, entity, user);
				    //计费单位为：元/半年/平
					}else if(dto.getBillingUnit().equals(BillingUnitEnum.PER_HALF_YEAR.getCode())){
						generateFeesHalfYear(dto.getStartDay(), dto.getEndDay(), dto.getRental(), params, freeLists, entity, user);
					//计费单位为：元/年/平
					}else if(dto.getBillingUnit().equals(BillingUnitEnum.PER_YEAR.getCode())){
						generarteFeesYear(dto.getStartDay(), dto.getEndDay(), dto.getRental(), params, freeLists, entity, user);
					}
				}
			}
		//按约定总金额生成租金应收款记录
		}else if(entity.getChargingWays().equals(ContractChargingWaysEnum.TOTAL.getCode())){
			List<ContractPaymentDTO> paymentLists = contractManageService.findAllPayment(map);
			if(paymentLists.size() > 0){
				for(ContractPaymentDTO dto : paymentLists){
					setFinanceFeeRecordEntity(entity, user, params, null, null, dto.getPaymentAmt(), 0, dto.getPaymentTime());
				}
			}
		}
		
		return params;
	}
	
	/**
	 * 按 元/月/平 计量单位生成应付款
	 */
	public void generateFeesMonth(Date startDay, Date endDay, Double rental, List<FinanceFeeRecordEntity> params, 
			List<ContractFreeDTO> freeLists, ContractMainEntity entity, String user){
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
				double startFreeDays = getFreeDays(startDay, DateUtil.getLastDayOfMonth(startYear, startMonth), freeLists);
				double startFee = rental * (startDays - currentDayStart + 1 - startFreeDays) / startDays;
				double startFreeFee = startFreeDays * rental / startDays;
				Date startStart = startDay;
				Date startEnd = DateUtil.getLastDayOfMonth(startYear, startMonth);
				Date startTimeLimit = getTimeLimit(startStart, entity);
				setFinanceFeeRecordEntity(entity, user, params, startStart, startEnd, startFee, startFreeFee, startTimeLimit);
				
				//除去首尾月份的应收款费用
				for(int i=startMonth+1; i<endMonth; i++){
					Date start = DateUtil.formateDate(startYear + "-" + i + "-" + "01");
					Date end = DateUtil.getLastDayOfMonth(startYear, i);
					double totalDays = DateUtil.getBetweenDays(start, end) + 1;
					double freeDays = getFreeDays(start, end, freeLists);
					double fee = rental * (totalDays - freeDays) / totalDays;
					double freeFee = freeDays * rental / totalDays;
					Date timeLimit = getTimeLimit(start, entity);
					setFinanceFeeRecordEntity(entity, user, params, start, end, fee, freeFee, timeLimit);
				}

				//计算结束月的租金
				double endFreeDays = getFreeDays(DateUtil.formateDate(endYear + "-" + endMonth + "-01"), endDay, freeLists);
				double endFee = rental * (currentDayEnd - endFreeDays) / endDays;
				double endFreeFee = endFreeDays * rental / endDays;
				Date endStart = DateUtil.formateDate(endYear + "-" + endMonth + "-01");
				Date endEnd = endDay;
				Date endTimeLimit = getTimeLimit(endStart, entity);
				setFinanceFeeRecordEntity(entity, user, params, endStart, endEnd, endFee, endFreeFee, endTimeLimit);
			}else if(endMonth == startMonth){
				double freeDays = getFreeDays(startDay, endDay, freeLists);
				double fee = rental * (DateUtil.getBetweenDays(startDay, endDay) + 1 - freeDays) / endDays;
				double freeFee = freeDays * rental / endDays;
				Date timeLimit = getTimeLimit(startDay, entity);
				setFinanceFeeRecordEntity(entity, user, params, startDay, endDay, fee, freeFee, timeLimit);
			}
		//跨年日期
		}else{
			//计算开始月的租金
			double startFreeDays = getFreeDays(startDay, DateUtil.getLastDayOfMonth(startYear, startMonth), freeLists);
			double startFee = rental * (startDays - currentDayStart - startFreeDays + 1) / startDays;
			double startFreeFee = startFreeDays * rental / startDays;
			Date startStart = startDay;
			Date startEnd = DateUtil.getLastDayOfMonth(startYear, startMonth);
			Date startTimeLimit = getTimeLimit(startStart, entity);
			setFinanceFeeRecordEntity(entity, user, params, startStart, startEnd, startFee, startFreeFee, startTimeLimit);
			
			//上一年
			for(int i=startMonth+1; i<=12; i++){
				double freeDays = getFreeDays(DateUtil.formateDate(startYear+"-"+i+"-01"), DateUtil.getLastDayOfMonth(startYear, i), freeLists);
				double totalDays = DateUtil.getDaysByDate(startYear, i);
				double fee = rental * (totalDays - freeDays) / totalDays;
				double freeFee = freeDays * rental / totalDays ;
				Date start = DateUtil.formateDate(startYear + "-" + i + "-" + "01");
				Date end = DateUtil.getLastDayOfMonth(startYear, i);
				Date timeLimit = getTimeLimit(start, entity);
				setFinanceFeeRecordEntity(entity, user, params, start, end, fee, freeFee, timeLimit);
			}
			
			for(int i=startYear+1; i<endYear; i++){
				for(int j=1; j<12; j++){
					double freeDays = getFreeDays(DateUtil.formateDate(i+"-"+j+"-01"), DateUtil.getLastDayOfMonth(i, j), freeLists);
					double totalDays = DateUtil.getDaysByDate(i, j);
					double fee = rental * (DateUtil.getDaysByDate(i, j) - freeDays) / totalDays;
					double freeFee = freeDays * rental / totalDays;
					Date start = DateUtil.formateDate(i + "-" + j + "-" + "01");
					Date end = DateUtil.getLastDayOfMonth(i, j);
					Date timeLimit = getTimeLimit(start, entity);
					setFinanceFeeRecordEntity(entity, user, params, start, end, fee, freeFee, timeLimit);
				}
			}
			//下一年
			for(int i=1; i<endMonth; i++){
				double freeDays = getFreeDays(DateUtil.formateDate(endYear+"-"+i+"-01"), DateUtil.getLastDayOfMonth(endYear, i), freeLists);
				double totalDays = DateUtil.getDaysByDate(endYear, i);
				double fee = rental * (totalDays - freeDays) / totalDays;
				double freeFee = freeDays * rental / totalDays;
				Date start = DateUtil.formateDate(endYear + "-" + i + "-" + "01");
				Date end = DateUtil.getLastDayOfMonth(endYear, i);
				Date timeLimit = getTimeLimit(start, entity);
				setFinanceFeeRecordEntity(entity, user, params, start, end, fee, freeFee, timeLimit);
			}
			
			//计算结束月的租金
			double endFreeDays = getFreeDays(DateUtil.formateDate(endYear + "-" + endMonth + "-" + "01"), endDay, freeLists);
			double endFee = rental * (currentDayEnd - endFreeDays) / endDays;
			double endFreeFee = endFreeDays * rental / endDays;
			Date endStart = DateUtil.formateDate(endYear + "-" + endMonth + "-" + "01");
			Date endEnd = endDay;
			Date endTimeLimit = getTimeLimit(endStart, entity);
			setFinanceFeeRecordEntity(entity, user, params, endStart, endEnd, endFee, endFreeFee, endTimeLimit);
		}

	}
	
	/**
	 * 获取免租天数
	 * @return
	 */
	public double getFreeDays(Date start, Date end, List<ContractFreeDTO> freeLists){
		double freeDays = 0;
		start = DateUtil.getStartOfDay(start);
		end = DateUtil.getStartOfDay(end);
		for(ContractFreeDTO dto : freeLists){
			Date startDate = DateUtil.getStartOfDay(dto.getStartDay());
			Date endDate = DateUtil.getStartOfDay(dto.getEndDay());
			if(start.getTime() >= startDate.getTime() && end.getTime() <= endDate.getTime()){
				freeDays = DateUtil.getBetweenDays(start, end) + 1;
			}else if(start.getTime() <= startDate.getTime() && end.getTime() >= endDate.getTime()){
				freeDays = DateUtil.getBetweenDays(startDate, endDate) + 1;
			}else if(start.getTime() <= startDate.getTime() && end.getTime() >= startDate.getTime() && end.getTime() <= endDate.getTime()){
				freeDays = DateUtil.getBetweenDays(startDate, end) + 1;
			}else if(start.getTime() >= startDate.getTime() && start.getTime() <= endDate.getTime() && end.getTime() >= endDate.getTime()){
				freeDays = DateUtil.getBetweenDays(start, endDate) + 1;
			}
		}
		return freeDays;
	}
	
	/**
	 * 按 元/季度/平 的计量单位生成应收款
	 */
	public void generarteFeesQuarterly(Date startDay, Date endDay, Double rental, List<FinanceFeeRecordEntity> params, 
			List<ContractFreeDTO> freeLists, ContractMainEntity entity, String user){
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
			putParamsQuarter(startQuarter, endQuarter, startYear, startDay, endDay, rental, params, freeLists, entity, user);
		//前后跨年	
		}else{
			putParamsQuarter(startQuarter, 4, startYear, startDay, DateUtil.formateDate(startYear+"-12-31"), rental, params, freeLists, entity, user);
			
			for(int i=startYear+1; i<endYear; i++){
				for(int j=1; j<5; j++){
					if(j==1){
						double totalDays = getDaysByQuarterAndYear(i, startYear);
						double freeDays = getFreeDays(DateUtil.formateDate(i+"-01-01"), DateUtil.formateDate(i+"-03-31"), freeLists);
						double fee = rental  / totalDays * (totalDays  - freeDays);
						double freeFee = freeDays * rental / totalDays;
						Date start = DateUtil.formateDate(i+"/01/01");
						Date end = DateUtil.formateDate(i+"/03/31");
						Date timeLimit = getTimeLimit(start, entity);
						setFinanceFeeRecordEntity(entity, user, params, start, end, fee, freeFee, timeLimit);
					}else if(j==2){
						double freeDays = getFreeDays(DateUtil.formateDate(i+"-04-01"), DateUtil.formateDate(i+"-06-30"), freeLists);
						double totalDays = getDaysByQuarterAndYear(i, startYear);
						double fee = rental  / totalDays * (totalDays - freeDays);
						double freeFee = freeDays * rental / totalDays;
						Date start = DateUtil.formateDate(i+"-04-01");
						Date end = DateUtil.formateDate(i+"-06-30");
						Date timeLimit = getTimeLimit(start, entity);
						setFinanceFeeRecordEntity(entity, user, params, start, end, fee, freeFee, timeLimit);
					}else if(j==3){
						double freeDays = getFreeDays(DateUtil.formateDate(i+"-07-01"), DateUtil.formateDate(i+"-09-30"), freeLists);
						double totalDays = getDaysByQuarterAndYear(i, startYear);
						double fee = rental  / totalDays  * (totalDays - freeDays);
						double freeFee = freeDays * rental / totalDays;
						Date start = DateUtil.formateDate(i+"-07-01");
						Date end = DateUtil.formateDate(i+"-09-30");
						Date timeLimit = getTimeLimit(start, entity);
						setFinanceFeeRecordEntity(entity, user, params, start, end, fee, freeFee, timeLimit);
					}else if(j==4){
						double freeDays = getFreeDays(DateUtil.formateDate(i+"-10-01"), DateUtil.formateDate(i+"-12-31"), freeLists);
						double totalDays = getDaysByQuarterAndYear(i, startYear);
						double fee = rental  / totalDays * (totalDays - freeDays);
						double freeFee = freeDays * rental / totalDays;
						Date start = DateUtil.formateDate(i+"-10-01");
						Date end = DateUtil.formateDate(i+"-12-31");
						Date timeLimit = getTimeLimit(start, entity);
						setFinanceFeeRecordEntity(entity, user, params, start, end, fee, freeFee, timeLimit);
					}
				}
			}
			
			putParamsQuarter(1, endQuarter, endYear, DateUtil.formateDate(endYear+"-01-01"), endDay, rental, params, freeLists, entity, user);
		}
	}
	
	/**
	 * 按季度计量单位收取应收款中不完整年份的处理
	 */
	public void putParamsQuarter(int startQuarter, int endQuarter, int year, Date startDay, Date endDay, double rental, List<FinanceFeeRecordEntity> params, List<ContractFreeDTO> freeLists, ContractMainEntity entity, String user){
		//前后日期属于一个季度
		if(startQuarter == endQuarter){
			double freeDays = getFreeDays(startDay, endDay, freeLists);
			double days = DateUtil.getBetweenDays(startDay, endDay) + 1;
			double fee = rental / getDaysByQuarterAndYear(startQuarter, year) * (days- freeDays);
			double freeFee = freeDays * rental / getDaysByQuarterAndYear(startQuarter, year);
			Date timeLimit = getTimeLimit(startDay, entity);
			setFinanceFeeRecordEntity(entity, user, params, startDay, endDay, fee, freeFee, timeLimit);
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
				double freeDays = getFreeDays(start, end, freeLists); 
				double fee = rental * (finalDays - freeDays) / totalDays;
				double freeFee = freeDays * rental / totalDays;
				Date timeLimit = getTimeLimit(start, entity);
				setFinanceFeeRecordEntity(entity, user, params, start, end, fee, freeFee, timeLimit);
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
					double freeDays = getFreeDays(start, end, freeLists);
					double fee = rental  / totalDays * (finalDays - freeDays);
					double freeFee = freeDays * rental / totalDays;
					Date timeLimit = getTimeLimit(start, entity);
					setFinanceFeeRecordEntity(entity, user, params, start, end, fee, freeFee, timeLimit);
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
					double freeDays = getFreeDays(start, end, freeLists);
					double fee = rental  / totalDays * (finalDays - freeDays);
					double freeFee = freeDays * rental / totalDays;
					Date timeLimit = getTimeLimit(start, entity);
					setFinanceFeeRecordEntity(entity, user, params, start, end, fee, freeFee, timeLimit);
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
					double freeDays = getFreeDays(start, end, freeLists);
					double fee = rental  / totalDays * (finalDays - freeDays);
					double freeFee = freeDays * rental / totalDays;	
					Date timeLimit = getTimeLimit(start, entity);
					setFinanceFeeRecordEntity(entity, user, params, start, end, fee, freeFee, timeLimit);
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
					double freeDays = getFreeDays(start, end, freeLists);
					double fee = rental  / totalDays * (finalDays - freeDays);
					double freeFee = freeDays * rental / totalDays;
					Date timeLimit = getTimeLimit(start, entity);
					setFinanceFeeRecordEntity(entity, user, params, start, end, fee, freeFee, timeLimit);
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
					double freeDays = getFreeDays(start, end, freeLists);
					double fee = rental  / totalDays * (finalDays - freeDays);
					double freeFee = freeDays * rental / totalDays;
					Date timeLimit = getTimeLimit(start, entity);
					setFinanceFeeRecordEntity(entity, user, params, start, end, fee, freeFee, timeLimit);
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
	 * 按 元/半年/平 计量单位生成应收款
	 */
	public void generateFeesHalfYear(Date startDay, Date endDay, Double rental, List<FinanceFeeRecordEntity> params,
			List<ContractFreeDTO> freeLists, ContractMainEntity entity, String user){
		//根据日期查出年
		int startYear = DateUtil.getYearOfDate(startDay);
		int endYear = DateUtil.getYearOfDate(endDay);
		
		//根据日期查出月
		int startMonth = DateUtil.getMonthOfDate(startDay);
		int endMonth = DateUtil.getMonthOfDate(endDay);
		
		if(startYear == endYear){
			halfYearPublicMethod(startMonth, endMonth, startYear, startDay, endDay, rental, params, freeLists, entity, user);
		//跨年处理方法
		}else{
			halfYearPublicMethod(startMonth, 12, startYear, startDay, DateUtil.formateDate(startYear+"-12-31"), rental, params, freeLists, entity, user);
			
			for(int i=startYear+1; i<endYear; i++){
				for(int j=1; j<3; j++){
					double totalDays = 0;
					double freeDays = 0;
					if(j==2){
						freeDays = getFreeDays(DateUtil.formateDate(startYear+"-07-01"), DateUtil.formateDate(startYear+"-12-31"), freeLists);
						totalDays = DateUtil.getBetweenDays(DateUtil.formateDate(startYear+"-07-01"), DateUtil.formateDate(startYear+"-12-31")) + 1;
					}else if(j==1){
						freeDays = getFreeDays(DateUtil.formateDate(startYear+"-01-01"), DateUtil.formateDate(startYear+"-06-30"), freeLists);
						totalDays = DateUtil.getBetweenDays(DateUtil.formateDate(startYear+"-01-01"), DateUtil.formateDate(startYear+"-06-30")) + 1;
					}
					double fee = rental / totalDays * (totalDays - freeDays);
					double freeFee = freeDays * rental / totalDays;
					Date start = startDay;
					Date end = endDay;
					Date timeLimit = getTimeLimit(start, entity);
					setFinanceFeeRecordEntity(entity, user, params, start, end, fee, freeFee, timeLimit);
				}
			}
			
			halfYearPublicMethod(1, endMonth, endYear, DateUtil.formateDate(endYear+"-01-01"), endDay, rental, params, freeLists, entity, user);
		}
	}
	
	/**
	 * 按元/半年/平计量单位 中不足一年的完整处理方法
	 */
	public void halfYearPublicMethod(int startMonth, int endMonth, int year, Date startDay, Date endDay, double rental, List<FinanceFeeRecordEntity> params, List<ContractFreeDTO> freeLists, ContractMainEntity entity, String user){
		if(startMonth>6 || endMonth<7){
			double finalDays = DateUtil.getBetweenDays(startDay, endDay) + 1;
			double totalDays = 0;
			if(startMonth>6){
				totalDays = DateUtil.getBetweenDays(DateUtil.formateDate(year+"-07-01"), DateUtil.formateDate(year+"-12-31")) + 1;
			}else if(endMonth<7){
				totalDays = DateUtil.getBetweenDays(DateUtil.formateDate(year+"-01-01"), DateUtil.formateDate(year+"-06-30")) + 1;
			}
			double freeDays = getFreeDays(startDay, endDay, freeLists);
			double fee = rental / totalDays * (finalDays - freeDays);
			double freeFee = freeDays * rental / totalDays;
			Date start = startDay;
			Date end = endDay;
			Date timeLimit = getTimeLimit(start, entity);
			setFinanceFeeRecordEntity(entity, user, params, start, end, fee, freeFee, timeLimit);
			
		}else{
			Date endStart  = DateUtil.formateDate(year+"-06-30");
			double startFinalDays = DateUtil.getBetweenDays(startDay, endStart) + 1;
			double startTotalDays = DateUtil.getBetweenDays(DateUtil.formateDate(year+"-01-01"), endStart) + 1;
			double startFreeDays = getFreeDays(startDay, endStart, freeLists);
			double startFee = rental / startTotalDays * (startFinalDays - startFreeDays);
			double startFreeFee = startFreeDays * rental / startTotalDays;
			Date startTimeLimit = getTimeLimit(startDay, entity);
			setFinanceFeeRecordEntity(entity, user, params, startDay, endStart, startFee, startFreeFee, startTimeLimit);
			
			Date startEnd  = DateUtil.formateDate(year+"-07-01");
			double endFinalDays = DateUtil.getBetweenDays(startEnd, endDay) + 1;
			double endFreeDays = getFreeDays(startEnd, endDay, freeLists);
			double endTotalDays = DateUtil.getBetweenDays(startEnd, DateUtil.formateDate(year+"-12-31")) + 1;
			double endFee = rental / endTotalDays * (endFinalDays - endFreeDays);
			double endFreeFee = endFreeDays * rental / endTotalDays;
			Date endTimeLimit = getTimeLimit(startEnd, entity);
			setFinanceFeeRecordEntity(entity, user, params, startEnd, endDay, endFee, endFreeFee, endTimeLimit);
		}
	}
	
	/**
	 * 按 元/年/平 的计量单位生成应收款
	 */
	public void generarteFeesYear(Date startDay, Date endDay, Double rental, List<FinanceFeeRecordEntity> params, 
			List<ContractFreeDTO> freeLists, ContractMainEntity entity, String user){
		//根据日期查出年
		int startYear = DateUtil.getYearOfDate(startDay);
		int endYear = DateUtil.getYearOfDate(endDay);
		
		if(startYear == endYear){
			double finalDays = DateUtil.getBetweenDays(startDay, endDay) + 1;
			double totalDays = getTotalDaysOfYear(startYear);
			double freeDays = getFreeDays(startDay, endDay, freeLists);
			double fee = rental / totalDays * (finalDays - freeDays);
			double freeFee = freeDays * rental / totalDays;
			Date start = startDay;
			Date end = endDay;
			Date timeLimit = getTimeLimit(start, entity);
			setFinanceFeeRecordEntity(entity, user, params, start, end, fee, freeFee, timeLimit);
			
		}else{
			//开始年份的租金
			double startFinalDays = DateUtil.getBetweenDays(startDay, DateUtil.formateDate(startYear + "-12-31")) + 1;
			double startTotalDays = getTotalDaysOfYear(startYear);
			double startFreeDays = getFreeDays(startDay, DateUtil.formateDate(startYear + "-12-31"), freeLists);
			double startFee = rental / startTotalDays * (startFinalDays - startFreeDays);
			double startFreeFee = startFreeDays * rental / startTotalDays;
			Date startStart = startDay;
			Date startEnd = DateUtil.formateDate(startYear + "-12-31");
			Date startTimeLimit = getTimeLimit(startStart, entity);
			setFinanceFeeRecordEntity(entity, user, params, startStart, startEnd, startFee, startFreeFee, startTimeLimit);
			
			for(int i=startYear+1; i<endYear; i++){
				double totalDays = getTotalDaysOfYear(i);
				double freeDays = getFreeDays(startDay, endDay, freeLists);
				double fee = rental / totalDays * (totalDays - freeDays);
				double freeFee = freeDays * rental / totalDays;
				Date start = DateUtil.formateDate(i+"-01-01");
				Date end = DateUtil.formateDate(i+"-12-31");
				Date timeLimit = getTimeLimit(start, entity);
				setFinanceFeeRecordEntity(entity, user, params, start, end, fee, freeFee, timeLimit);
			}
			
			//结尾年份的租金
			double endFinalDays = DateUtil.getBetweenDays(DateUtil.formateDate(endYear + "-01-01"), endDay) + 1;
			double endTotalDays = getTotalDaysOfYear(endYear);
			double endFreeDays = getFreeDays(DateUtil.formateDate(endYear + "-01-01"), endDay, freeLists);
			double endFee = rental / endTotalDays * (endFinalDays - endFreeDays);
			double endFreeFee = endFreeDays * rental / endTotalDays;
			Date endStart = DateUtil.formateDate(endYear + "-01-01");
			Date endEnd = endDay;
			Date endTimeLimit = getTimeLimit(endStart, entity);
			setFinanceFeeRecordEntity(entity, user, params, endStart, endEnd, endFee, endFreeFee, endTimeLimit);
		}
	}
	
	/**
	 * 初始化应收款记录的字段
	 */
	public void setFinanceFeeRecordEntity(ContractMainEntity entity, String user, List<FinanceFeeRecordEntity> params, Date start, Date end,
			double fee, double freeFee, Date timeLimit){
		FinanceFeeRecordEntity record = new FinanceFeeRecordEntity();
		record.setAccountRebate(freeFee);
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", "1");
		map.put("marketId", entity.getMarketId());
		MarketExpenditureDTO me = marketExpenditureService.getByParentId(map);
		record.setFeeItemId(me.getId());
		record.setFeeItemTypeId(me.getExpType().toString());
		params.add(record);
	}
	
	/**
	 * 获取应收款记录的缴费期限
	 */
	public Date getTimeLimit(Date start, ContractMainEntity entity){
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
	
	@Override
	public double getTotalAmt(ContractMainEntity entity) {
		List<FinanceFeeRecordEntity> params = new ArrayList<FinanceFeeRecordEntity>();
		generateRental(entity, params, null);
		
		double totalAmt = 0;
		for(FinanceFeeRecordEntity record : params) {
			totalAmt += record.getAccountPayable();
		}
		return totalAmt;
	}
}
