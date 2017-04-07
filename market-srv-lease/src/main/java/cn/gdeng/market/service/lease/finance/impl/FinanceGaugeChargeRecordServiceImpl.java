package cn.gdeng.market.service.lease.finance.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.framework.dba.transaction.annotation.Transactional;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.finance.FinanceGaugeChargeRecordDTO;
import cn.gdeng.market.dto.lease.resources.MarketAreaBuildingDTO;
import cn.gdeng.market.dto.lease.resources.MarketAreaDTO;
import cn.gdeng.market.dto.lease.resources.MarketSectionalChargeDTO;
import cn.gdeng.market.dto.lease.settings.MarketMeasureTypeDTO;
import cn.gdeng.market.dto.lease.settings.MarketResourceTypeDTO;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.finance.FinanceGaugeChargeRecordService;

/**
 * 计量表分类接口实现
 * @author cai.xu
 *
 */
@Service(value="financeGaugeChargeRecordService")
public class FinanceGaugeChargeRecordServiceImpl implements FinanceGaugeChargeRecordService {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(FinanceGaugeChargeRecordServiceImpl.class);
	
	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public GuDengPage<FinanceGaugeChargeRecordDTO> queryMeterReadingList(GuDengPage<FinanceGaugeChargeRecordDTO> page) throws BizException {
		Map<String,Object> param = page.getParaMap();
		//查询总页数
		int count = queryMeterReadingCount(param);
		page.setTotal(count);
		List<FinanceGaugeChargeRecordDTO> list = null;
		if(count>0){
			list = baseDao.queryForList("FinanceGaugeChargeRecord.queryMeterReadingListPage", param, FinanceGaugeChargeRecordDTO.class);
		}
		page.setRecordList(list);
		return page;
	}
	
	@Override
	public GuDengPage<FinanceGaugeChargeRecordDTO> queryByPage(GuDengPage<FinanceGaugeChargeRecordDTO> page) throws BizException {
		Map<String,Object> param = page.getParaMap();
		//查询总页数
		int count = baseDao.queryForObject("FinanceGaugeChargeRecord.countByCondition", param,Integer.class);;
		page.setTotal(count);
		List<FinanceGaugeChargeRecordDTO> list = null;
		if(count>0){
			list = baseDao.queryForList("FinanceGaugeChargeRecord.queryByConditionPage", param, FinanceGaugeChargeRecordDTO.class);
			for (FinanceGaugeChargeRecordDTO dto : list) {
				dto.setThisQuantity(dto.getCurrent() - dto.getLast());
			}
		}
		page.setRecordList(list);
		return page;
	}
	
	@Override
	public int queryMeterReadingCount(Map<String, Object> param) throws BizException {
		int count = baseDao.queryForObject("FinanceGaugeChargeRecord.queryMeterReadingCount", param,Integer.class);
		return count;
	}

	@Override
	public FinanceGaugeChargeRecordDTO findMeterReadingInfo(
			Map<String, Object> param) throws BizException {
		FinanceGaugeChargeRecordDTO dto = baseDao.queryForObject("FinanceGaugeChargeRecord.findMeterReadingInfo", param,FinanceGaugeChargeRecordDTO.class);
		return dto;
	}

	@Override
	public List<MarketSectionalChargeDTO> findSectionalchargeList(
			Map<String, Object> param) throws BizException {
		List<MarketSectionalChargeDTO> list = baseDao.queryForList("FinanceGaugeChargeRecord.findSectionalchargeList", param, MarketSectionalChargeDTO.class);
		return list;
	}

	@Override
	@Transactional
	public int batchSettlement(List<FinanceGaugeChargeRecordDTO> list,SysUserDTO user,Integer marketId)
			throws BizException {
		Map<String,Object> paramMap = null;
		int count = 0;
		for(FinanceGaugeChargeRecordDTO dto : list){
			paramMap = new HashMap<String,Object>();
			paramMap.put("measureId", dto.getMeasureId());
			//根据计量表ID查询合同资源等信息
			FinanceGaugeChargeRecordDTO dtoInfo = this.findMeterReadingInfo(paramMap);
			//如果当前计量表没有关联有效的合同，或者合同没有关联有效的走表类信息则不生成待付款记录
			if (dtoInfo == null || dtoInfo.getResourceName() == null
					|| dtoInfo.getContractVersion() == null
					|| dtoInfo.getExpStandardId() == null) {
				logger.info("计量表ID为" + dto.getMeasureId()
						+ "没有关联有效的合同或者合同没有关联其它费项有效的走表类信息");
				continue;
			}
			setContractInfo(dto,dtoInfo,user,marketId);
			//生成走表类待付款记录
			count += this.insert(dto);
		}
		return count;
	}
	/**
	 * 封装合同等相关信息
	 * @param dto
	 * @return
	 * @throws BizException
	 */
	public void setContractInfo(FinanceGaugeChargeRecordDTO dto,FinanceGaugeChargeRecordDTO dtoInfo,SysUserDTO user,Integer marketId) throws BizException{
		dto.setContractVersion(dtoInfo.getContractVersion());
		dto.setContractNo(dtoInfo.getContractNo());
		dto.setLast(dtoInfo.getLast()==null?0:dtoInfo.getLast());
		dto.setLastNoteDate(dtoInfo.getLastNoteDate());
		dto.setCustomerName(dtoInfo.getCustomerName());
		dto.setResourceName(dtoInfo.getResourceName());
		dto.setPartyB(dtoInfo.getPartyB());
		dto.setExpStandardId(dtoInfo.getExpStandardId());
		dto.setWastageRate(dtoInfo.getWastageRate()==null?0:dtoInfo.getWastageRate());
		dto.setPrice(dtoInfo.getPrice());
		dto.setSectionalCharge(dtoInfo.getSectionalCharge());
		dto.setCreateUserId(user.getUserId());
		dto.setUpdateUserId(user.getUserId());
		dto.setNoteUser(user.getName());
		dto.setMarketId(marketId);
		//设置收费金额
		setExpStandardInfo(dto,dtoInfo);
		
	}
	/**
	 * 设置计算单价和收费金额
	 */
	
	public void setExpStandardInfo(FinanceGaugeChargeRecordDTO dto,FinanceGaugeChargeRecordDTO dtoInfo) throws BizException{
		Map<String,Object> paramMap = new HashMap<String,Object>();
		double totalAmount = 0.0d;
		double totalQuantity = dto.getCurrent()-dto.getLast() + dto.getWastage();
		//如果当前计量表所在合同中的计费标准为分段计费，查询每段单价
		if (null != dtoInfo && null != dtoInfo.getSectionalCharge()){
				if(dtoInfo.getSectionalCharge().intValue() == Const.MEASURE_SECTIONALCHARGE_SEGMENTATION) {
					paramMap.put("expStandardId", dto.getExpStandardId());
					List<MarketSectionalChargeDTO> marketSectionalChargeList = this.findSectionalchargeList(paramMap);
					if(null != marketSectionalChargeList && marketSectionalChargeList.size()>0){
						//如果是分段计费，计算单价取最小值
						dto.setPrice(marketSectionalChargeList.get(0).getMinPrice());
						List<String> priceList = getPrice(totalQuantity,marketSectionalChargeList);
						dto.setAmount(Double.valueOf(priceList.get(priceList.size()-1)));
					}
				}else{
					//计算总金额
					totalAmount = (totalQuantity)*dto.getPrice(); 
					dto.setAmount(dealTotalMoney(totalAmount));
				}
			}
	}
	

	@Override
	public Integer insert(FinanceGaugeChargeRecordDTO entity) throws BizException {
		return baseDao.execute("FinanceGaugeChargeRecord.insert", entity);
	}

	@Override
	public GuDengPage<FinanceGaugeChargeRecordDTO> queryFinanceGaugeChargeList(
			GuDengPage<FinanceGaugeChargeRecordDTO> page) throws BizException {
		Map<String,Object> param = page.getParaMap();
		//查询总页数
		int count = queryFinanceGaugeChargeListCount(param);
		page.setTotal(count);
		List<FinanceGaugeChargeRecordDTO> list = null;
		if(count>0){
			list = baseDao.queryForList("FinanceGaugeChargeRecord.queryFinanceGaugeChargeRecordListPage", param, FinanceGaugeChargeRecordDTO.class);
		}
		page.setRecordList(list);
		return page;
	}

	@Override
	public int queryFinanceGaugeChargeListCount(Map<String, Object> param)
			throws BizException {
		int count = baseDao.queryForObject("FinanceGaugeChargeRecord.queryFinanceGaugeChargeRecordListCount", param,Integer.class);
		return count;
	}
	
	@Override
	@Transactional
	public int batchUpdateStatus(List<String> list,SysUserDTO user) throws BizException {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			map.put("updateUserId", user.getUserId());
			map.put("receiver", user.getName());
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("FinanceGaugeChargeRecord.updateStatus", batchValues).length;
	}

	@Override
	public int update(Map<String, Object> param) throws BizException {
		return baseDao.execute("FinanceGaugeChargeRecord.update", param);
	}

	@Override
	public FinanceGaugeChargeRecordDTO queryFinanceGaugeChargeById(
			Map<String, Object> param) throws BizException {
		FinanceGaugeChargeRecordDTO dto = baseDao.queryForObject("FinanceGaugeChargeRecord.queryFinanceGaugeChargeById", param,FinanceGaugeChargeRecordDTO.class);
		return dto;
	}

	@Override
	public List<FinanceGaugeChargeRecordDTO> queryFinanceGaugeChargeRecordList(
			Map<String, Object> param) throws BizException {
		List<FinanceGaugeChargeRecordDTO> list = baseDao.queryForList("FinanceGaugeChargeRecord.queryFinanceGaugeChargeRecordList", param, FinanceGaugeChargeRecordDTO.class);
		return list;
	}
	
	/**
	 * 计算分段收费的价钱,最后一条记录为收费金额
	 * @param quantity
	 * @param list
	 * @return
	 */
	public List<String> getPrice(double quantity,List<MarketSectionalChargeDTO> list)throws BizException{
		List<String> returnlist = new ArrayList<String>();
		double sumPrice = 0.0d;
		String formatResult="";
		DecimalFormat df =new DecimalFormat("0.00");  
		for(int i=0;i<list.size();i++){
			MarketSectionalChargeDTO dto = list.get(i);
			if(dto.getDownValue()>=quantity){
				break;
			}
			if(i==list.size()-1 || dto.getUpValue()> quantity){
				double result = dealTotalMoney((quantity-dto.getDownValue())*dto.getChargeUnitPrice());
				formatResult = df.format(result);
				returnlist.add("第"+(i+1)+"档收费:"+df.format((quantity-dto.getDownValue()))+"*"+df.format(dto.getChargeUnitPrice())+"="+formatResult);
				sumPrice+=result;
			}else{
				formatResult = df.format(dto.getResult());
				returnlist.add("第"+(i+1)+"档收费:"+df.format((dto.getUpValue()-dto.getDownValue()))+"*"+df.format(dto.getChargeUnitPrice())+"="+formatResult);
				sumPrice+=dto.getResult();
			}
		}
		returnlist.add(String.valueOf(dealTotalMoney(sumPrice)));
		return returnlist;
	}

	@Override
	public List<MarketAreaDTO> findCurrentAreaList(Map<String, Object> param)
			throws BizException {
		List<MarketAreaDTO> list = baseDao.queryForList("FinanceGaugeChargeRecord.findCurrentAreaList", param, MarketAreaDTO.class);
		return list;
	}
	/**
	 * 保留2位小数向上取
	 * @param money
	 * @return
	 */
	public double dealTotalMoney(double money){
		return Math.ceil(money*100)/100;
	}
	@Override
	public List<MarketAreaBuildingDTO> findCurrentBuildList(Map<String, Object> param)
			throws BizException {
		List<MarketAreaBuildingDTO> list = baseDao.queryForList("FinanceGaugeChargeRecord.findCurrentBuildList", param, MarketAreaBuildingDTO.class);
		return list;
	}

	@Override
	public List<MarketMeasureTypeDTO> findAllMeasureType(Map<String, Object> param) throws BizException {
		List<MarketMeasureTypeDTO> list = baseDao.queryForList("FinanceGaugeChargeRecord.findAllMeasureType", param, MarketMeasureTypeDTO.class);
		return list;
	}

	@Override
	public MarketResourceTypeDTO findMarketResourceType(Map<String, Object> param) throws BizException {
		return baseDao.queryForObject("FinanceGaugeChargeRecord.findMarketResourceType", param, MarketResourceTypeDTO.class);
	}

	@Override
	public FinanceGaugeChargeRecordDTO findContractInfoById(
			Map<String, Object> param) throws BizException {
		FinanceGaugeChargeRecordDTO dto = baseDao.queryForObject("FinanceGaugeChargeRecord.findContractInfoById", param,FinanceGaugeChargeRecordDTO.class);
		return dto;
	}
	
}
