package cn.gdeng.market.service.lease.contract;

import java.util.List;

import cn.gdeng.market.entity.lease.contract.ContractMainEntity;
import cn.gdeng.market.entity.lease.finance.FinanceFeeRecordEntity;

/**
 * 合同租金约定服务接口类
 *
 */

public interface ContractRentService {
	/**
	 * 生成应付款信息
	 */
	public List<FinanceFeeRecordEntity> generateRental(ContractMainEntity entity, List<FinanceFeeRecordEntity> params, String user);

	/**
	 * 获取按周期收费的合同总金额
	 * @param contractId
	 * @return
	 */
	public double getTotalAmt(ContractMainEntity entity);
}
