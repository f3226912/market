package cn.gdeng.market.service.lease.finance;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.lease.finance.FinanceFeeReceivedDTO;
import cn.gdeng.market.entity.lease.finance.FinanceFeeReceivedEntity;
import cn.gdeng.market.exception.BizException;

/**
 *
 *	财务实收款接口
 */
public interface FinanceFeeReceivedService {

	/**
	 * 新增财务实收款记录
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	public Long insertFeeReceivedRecord(FinanceFeeReceivedEntity entity) throws BizException;
	
	/**
	 * 根据id查询财务实收款记录
	 * @param params
	 * @return
	 * @throws BizException
	 */
	public FinanceFeeReceivedDTO queryFeeReceivedRecordById(Map<String, Object> params) throws BizException;
	
	/**
	 * 查询财务实收款记录列表
	 * @param params
	 * @return
	 * @throws BizException
	 */
	public List<FinanceFeeReceivedDTO> queryFeeReceivedRecord(Map<String, Object> params) throws BizException;
	
	/**
	 * 查询财务实收款记录总数
	 * @param params
	 * @return
	 * @throws BizException
	 */
	public int getFeeReceivedRecordCount(Map<String, Object> params) throws BizException;
	
}
