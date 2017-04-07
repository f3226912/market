package cn.gdeng.market.service.lease.contract;

import cn.gdeng.market.dto.lease.contract.ContractMainDTO;
import cn.gdeng.market.entity.lease.contract.ContractStatementsEntity;
import cn.gdeng.market.exception.BizException;

/**
 * 工作流服务审批服务接口
 * @author Administrator
 *
 */
public interface ContractWorkflowService {
	
	/**
	 * 工作流发起审批
	 * @param Id 合同ID
	 * @param type 合同审批类型
	 * @return
	 */
	public void submitApproval(Integer contractId, Byte type);
	
	/**
	 * 工作流合同初次审批结果
	 * @param Id 合同Id
	 * @param status 审批状态
	 * @return
	 */
	public void contractApproval(Integer contractId, Byte status) throws BizException;
	
	/**
	 * 工作流合同变更审批结果
	 * @param Id 合同Id
	 * @param status 审批状态
	 * @return
	 */
	public void contractChangeApproval(Integer contractId, Byte status) throws BizException;
	
	/**
	 * 工作流合结算审批结果
	 * @param Id 合同Id
	 * @param status 审批状态
	 * @return
	 */
	public void contractSettlementApproval(Integer contractId, Byte status) throws BizException;
	
	/**
	 * 合同详情信息
	 * @param Id 合同Id
	 * @return ContractMainDTO
	 */
	public ContractMainDTO contractApprovalStatus(Integer contractId);
	/**
	 * 合同变更详情信息
	 * @param Id 合同Id
	 * @return ContractChangeDTO 合同变更详情信息
	 */
	public ContractMainDTO contractChangeApprovalStatus(Integer contractId);
	/**
	 * 合同结算详情信息
	 * @param Id 合同Id
	 * @return ContractStatementsDTO 合同结算详情信息
	 */
	public ContractMainDTO contractStatementsApprovalStatus(Integer contractId);
	
	/**
	 *  合同总金额
	 * @param contractId
	 * @return  double 合同总金额
	 */
	public Double ContractMoney(Integer ContractId);
	
	/**
	 *  根据合同Id查询结算记录
	 * @param contractId
	 */
	public ContractStatementsEntity getStatementByContractId(Integer contractId);

}
