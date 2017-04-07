package cn.gdeng.market.service.lease.contract;

import java.util.List;

import cn.gdeng.market.entity.lease.contract.ContractMainEntity;
import cn.gdeng.market.entity.lease.finance.FinanceFeeRecordEntity;
import cn.gdeng.market.exception.BizException;

/**
 * 合同其他费用约定服务接口类
 *
 */

public interface ContractOtherFeeService {
	public void generateOthersFee(ContractMainEntity entity, List<FinanceFeeRecordEntity> params, String user) throws BizException;
}
