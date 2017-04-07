package cn.gdeng.market.service.lease.finance;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.finance.FinanceGaugeChargeRecordDTO;
import cn.gdeng.market.dto.lease.resources.MarketAreaBuildingDTO;
import cn.gdeng.market.dto.lease.resources.MarketAreaDTO;
import cn.gdeng.market.dto.lease.resources.MarketSectionalChargeDTO;
import cn.gdeng.market.dto.lease.settings.MarketMeasureTypeDTO;
import cn.gdeng.market.dto.lease.settings.MarketResourceTypeDTO;
import cn.gdeng.market.exception.BizException;

public interface FinanceGaugeChargeRecordService {
	
	/**
	 * 查询计量抄表记录数
	 * @param map
	 * @return 记录数
	 */
	public GuDengPage<FinanceGaugeChargeRecordDTO> queryMeterReadingList(GuDengPage<FinanceGaugeChargeRecordDTO> page) throws BizException;
	public GuDengPage<FinanceGaugeChargeRecordDTO> queryByPage(GuDengPage<FinanceGaugeChargeRecordDTO> page) throws BizException;
	
	/**
	 * 查询计量抄表总条数 
	 * @param param
	 * @return
	 * @throws BizException
	 */
	public int queryMeterReadingCount(Map<String, Object> param) throws BizException;
	
	/**
	 * 查询抄表页面合同等信息
	 * @param param
	 * @return
	 * @throws BizException
	 */
	public FinanceGaugeChargeRecordDTO findMeterReadingInfo(Map<String, Object> param) throws BizException;
	
	/**
	 * 查询分段计费信息
	 * @param param
	 * @return
	 * @throws BizException
	 */
	public List<MarketSectionalChargeDTO> findSectionalchargeList(Map<String, Object> param) throws BizException;
	
	/**
	 * 查询当前市场的所有区域
	 * @param param
	 * @return
	 * @throws BizException
	 */
	public List<MarketAreaDTO> findCurrentAreaList(Map<String, Object> param) throws BizException;
	
	/**
	 * 查询当前区域下的所有楼栋
	 * @param param
	 * @return
	 * @throws BizException
	 */
	public List<MarketAreaBuildingDTO> findCurrentBuildList(Map<String, Object> param) throws BizException;
	
	
	/**
	 * 批量结转为待付款
	 * @param list
	 * @return
	 * @throws BizException
	 */
	public int batchSettlement(List<FinanceGaugeChargeRecordDTO> list,SysUserDTO user,Integer marketId)throws BizException;
	
	/**
	 * 插入数据到抄表记录表
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	public Integer insert(FinanceGaugeChargeRecordDTO entity) throws BizException;
	
	/**
	 * 查询计量抄表收费记录数
	 * @param map
	 * @return 记录数
	 */
	public GuDengPage<FinanceGaugeChargeRecordDTO> queryFinanceGaugeChargeList(GuDengPage<FinanceGaugeChargeRecordDTO> page) throws BizException;
	
	/**
	 * 查询计量抄表收费总条数 
	 * @param param
	 * @return
	 * @throws BizException
	 */
	public int queryFinanceGaugeChargeListCount(Map<String, Object> param) throws BizException;
	
	/**
	 * 批量收款 
	 * @param list
	 * @return
	 * @throws BizException
	 */
	public int batchUpdateStatus(List<String> list,SysUserDTO user) throws BizException; 
	
	
	/**
	 * 更新收款记录详细信息
	 * @param param
	 * @return
	 * @throws BizException
	 */
	public int update(Map<String, Object> param) throws BizException; 
	
	/**
	 * 根据条件查询抄表收费记录详情
	 * @param param
	 * @return
	 * @throws BizException
	 */
	public FinanceGaugeChargeRecordDTO queryFinanceGaugeChargeById(Map<String, Object> param) throws BizException;
	
	/**
	 * 根据收费记录Id查询合同信息
	 * @param param
	 * @return
	 * @throws BizException
	 */
	public FinanceGaugeChargeRecordDTO findContractInfoById(Map<String, Object> param) throws BizException;
	
	/**
	 * 查询计量抄表收费记录数列表
	 * @param map
	 * @return 记录数
	 */
	public List<FinanceGaugeChargeRecordDTO> queryFinanceGaugeChargeRecordList(Map<String, Object> param) throws BizException;
	
	
	/**
	 * 查询分段计费数据
	 * @param map
	 * @return 记录数
	 */
	public List<String> getPrice(double quantity,List<MarketSectionalChargeDTO> list)throws BizException;
	
	/**
	 * 查询计量表分类列表(只查询水表和电表)
	 * @param param
	 * @return
	 * @throws BizException
	 */
	public List<MarketMeasureTypeDTO> findAllMeasureType(Map<String, Object> param) throws BizException ;
	
	/**
	 * 查询资源类型
	 * @param param
	 * @return
	 * @throws BizException
	 */
	public MarketResourceTypeDTO findMarketResourceType(Map<String, Object> param) throws BizException ;
}
