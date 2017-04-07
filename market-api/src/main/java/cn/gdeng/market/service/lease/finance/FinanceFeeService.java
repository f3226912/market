package cn.gdeng.market.service.lease.finance;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.lease.finance.FinanceFeeRecordDTO;
import cn.gdeng.market.entity.lease.finance.FinanceFeeRecordEntity;
import cn.gdeng.market.exception.BizException;

/**
 * 财务应收款记录服务接口
 *
 */
public interface FinanceFeeService {

	/**
	 * 新增财务应收款记录
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	public Long insertFeeRecord(FinanceFeeRecordEntity entity) throws BizException;
	
	/**
	 * 新增财务应收款记录
	 * @param entitys
	 * @return
	 * @throws BizException
	 */
	public Long[] insertFeeRecordList(List<FinanceFeeRecordEntity> entitys) throws BizException;
	
	/**
	 * 更新财务应收款记录
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	public int updateFeeRecord(FinanceFeeRecordDTO dto) throws BizException;
	
	/**
	 * 更新财务应收款记录
	 * @param params
	 * @return
	 * @throws BizException
	 */
	public int updateFeeRecord(Map<String, Object> params) throws BizException;
	
	/**
	 * 查询财务应收款记录列表
	 * @param params
	 * @return
	 * @throws BizException
	 */
	public List<FinanceFeeRecordDTO> queryFeeRecord(Map<String, Object> params) throws BizException;
	
	/**
	 * 查询财务应收款记录总数
	 * @param params
	 * @return
	 * @throws BizException
	 */
	public int getFeeRecordCount(Map<String, Object> params) throws BizException;
	
	/**
	 * 根据id查询财务应收款记录
	 * @param params
	 * @return
	 * @throws BizException
	 */
	public FinanceFeeRecordDTO queryFeeRecordById(Map<String, Object> params) throws BizException;
	
	
	/**
	 * 根据合同Id和应收款开始时间删除未收款的记录
	 * @param params
	 * @return
	 * @throws BizException
	 */
	public int updateBycontract(Map<String, Object> params) throws BizException;
	
	/**
	 * 根据合同Id和财务未实际收款来删除记录
	 * @param params
	 * @return
	 * @throws BizException
	 */
	public int updateByContractAndStatus(Map<String, Object> params) throws BizException;
}
