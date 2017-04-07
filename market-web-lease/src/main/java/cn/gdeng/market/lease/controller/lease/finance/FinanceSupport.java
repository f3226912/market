package cn.gdeng.market.lease.controller.lease.finance;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.gdeng.market.entity.lease.finance.FinanceFeeReceivedEntity;
import cn.gdeng.market.entity.lease.finance.FinanceFeeRecordEntity;
import cn.gdeng.market.enums.FinEffecStatusEnum;
import cn.gdeng.market.enums.FinRemedyStatusEnum;

public class FinanceSupport {

/*	public static String generateInterString(SysContractRemindSettingDTO dto){
		String interString = "INTERVAL " + dto.getRemindTime().intValue() + " ";
		if (ContractRemindTimeTypeEnum.MONTH.getCode() == dto.getRemindTimeType()){
			interString += "MONTH";
		}else {
			interString += "DAY";
		}
		return interString;
	}*/
	
	public static FinanceFeeReceivedEntity generateFinanceFeeReceived(FinanceFeeRecordEntity entity, 
			String accountPayed, String reciever, String phone,	String remark, String sysUserId, Integer marketId){
		
		FinanceFeeReceivedEntity receive = new FinanceFeeReceivedEntity() ;
		receive.setContractNo(entity.getContractNo());
		receive.setContractVersion(entity.getContractVersion());
		receive.setMarketId(marketId);
		receive.setFeeItemId(entity.getFeeItemId());
		receive.setFeeItemTypeId(entity.getFeeItemTypeId());
		receive.setAccountPayable(entity.getAccountPayable());
		receive.setAccountPayed(Double.valueOf(accountPayed));
		receive.setAccountRebate(entity.getAccountRebate());
		receive.setAgent(sysUserId);
		receive.setStartTime(entity.getStartTime());
		receive.setEndTime(entity.getEndTime());
		receive.setFundType(entity.getFundType());
		receive.setPayer(reciever);
		receive.setPhone(phone);
		receive.setRemark(remark);
		receive.setTimeLimit(entity.getTimeLimit());
		Date now = new Date();
		receive.setReceiveDate(now);
		receive.setCreateTime(now);
		receive.setCreateUserId(sysUserId);
		receive.setUpdateTime(now);
		receive.setUpdateUserId(sysUserId);
		receive.setNeedReceiveId(entity.getId().intValue());
		return receive;
	}
	
	public static FinanceFeeRecordEntity generateFinanceFeeRecord(FinanceFeeRecordEntity old, String sysUserId, Double differ
			){
		FinanceFeeRecordEntity entity = new FinanceFeeRecordEntity();
		//合同编号、版本号
		entity.setContractNo(old.getContractNo());
		entity.setContractVersion(old.getContractVersion());
		//市场id
		entity.setMarketId(old.getMarketId());
		//费项
		entity.setFeeItemId(old.getFeeItemId());
		//费项类型id
		entity.setFeeItemTypeId(old.getFeeItemTypeId());
		//缴费期限
		entity.setTimeLimit(old.getTimeLimit());
		//计费起始时间
		entity.setStartTime(old.getStartTime());
		entity.setEndTime(old.getEndTime());
		//应收金额
		entity.setAccountPayable(differ);
		//优惠金额
		entity.setAccountRebate(old.getAccountRebate());
		//款项类型
		entity.setFundType(old.getFundType());
		//是否补交记录
		entity.setIsRemedy(FinRemedyStatusEnum.YES.getCode());
		//是否删除
		entity.setIsDeteled(FinEffecStatusEnum.NO.getCode());
		//计费标准id
		entity.setFreightBasisId(old.getFreightBasisId());
		//创建/更新时间
		Date now = new Date();
		entity.setCreateTime(now);
		entity.setCreateUserId(sysUserId);
		entity.setUpdateTime(now);
		entity.setUpdateUserId(sysUserId);
		return entity;
	}
	
	public static <T> void typeConvert(List<T> list) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		for (T item : list){
			typeConvert(item, null);
		}
	}
	
	/**
	 * 将日期型的字段值经过转化后, 写入到其对应的字符串类型字段中; 
	 * 仅支持没有字段覆盖(重写)的情况, 否则将产生意料之外的错误 ;
	 * @param source
	 * @param candidateFields
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public static <T> T typeConvert(T source, Set<Field> candidateFields) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		if (candidateFields == null) {
			candidateFields = new HashSet<Field>();
		}
		getFields(candidateFields, source.getClass());;
		for (Field item : candidateFields){
			item.setAccessible(true);
			Object fieldValue = item.get(source) ;
			//查询日期类型的变量
			if (fieldValue != null && fieldValue instanceof Date ){
				//如果当前对象存在与该日期对应的String类型的字段, 则将该String类型的字段设置为日期字段格式化后的值
				Field correspondingStringField = getCorresStringField(candidateFields, item);
/*				if (correspondingStringField.get(source) instanceof String){
					correspondingStringField.set(source, DATETIME_FORMATTER.format(fieldValue));
				}*/
				if (correspondingStringField != null && correspondingStringField.getType() == String.class){
					correspondingStringField.setAccessible(true);
					correspondingStringField.set(source, DATE_FORMATTER.format(fieldValue));
				}
				
			} 
			if (fieldValue != null && fieldValue instanceof Double ){
				//如果当前对象存在与该Double字段对应的String类型的字段, 则将该String类型的字段设置为Double字段格式化后的值
				Field correspondingStringField = getCorresStringField(candidateFields, item);
				if (correspondingStringField != null && correspondingStringField.getType() == String.class){
					BigDecimal b = new BigDecimal((Double)fieldValue);
					correspondingStringField.setAccessible(true);
					correspondingStringField.set(source, b.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				}
			}
		}
		return source;
	}
	
	private static Field getCorresStringField(Set<Field> candidateFields, Field source){
		for (Field item : candidateFields) {
			if (item.getName().equals(source.getName() + DATE_FIELD_SUFFIX)){
				return item;
			}
		}
		return null;
	}
	
	private static void getFields(Set<Field> candidateFields, Class<?> source){
		Class<?> supperClass = source.getSuperclass() ;
		if (supperClass ==  Object.class){
			//忽略Object属性字段
		}else {
			getFields(candidateFields, supperClass) ;
		}
		for (Field item : source.getDeclaredFields()){
			candidateFields.add(item);
		}
	}
	
	public static final SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	
	private static String DATE_FIELD_SUFFIX = "String" ;
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
//		ContractMainDTO dto = new ContractMainDTO();
//		dto.setStartLeasingDay(new Date());
//		dto.setEndLeasingDay(new Date());
//		typeConvert(dto, null);
		double f = 111231.5;
		BigDecimal b = new BigDecimal(f);
		System.out.println(b.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
	}
	
}
