package cn.gdeng.market.service.lease.contract;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.contract.ContractStatementsDTO;
import cn.gdeng.market.entity.lease.contract.ContractApprovalEntity;
import cn.gdeng.market.entity.lease.contract.ContractStatementsEntity;
import cn.gdeng.market.exception.BizException;

/**
 * 合同结算服务接口层
 * @author Administrator
 *
 */
public interface ContractSettlementService{
	/**
	 * 合同结算人工审核操作
	 */
	public void auditByHuman(ContractApprovalEntity entity) throws BizException; 
	public GuDengPage<ContractStatementsDTO> getByConditionPage(
			GuDengPage<ContractStatementsDTO> page);
	/**
	 * 合同结算
	 */
	public List<ContractStatementsDTO> getExpConditionList(Map<String, Object> map);
	
	/**
	 * 合同结算总条数
	 */
	public int  getExpConditionCount(Map<String, Object> map);
	
	/**
	 * 根据主键Id查询合同结算实体
	 */
	public ContractStatementsEntity findById(Integer id);
	
	/**
	 * 合同结算信息保存
	 */
	public void edit(ContractStatementsEntity entity) throws Exception;
	
	/**
	 * 根据合同Id查找合同结算记录
	 */
	public ContractStatementsEntity getByContractId(Map<String, Object> map);
	
	/**
	 * 合同结算信息保存
	 */
	public void save(ContractStatementsEntity entity) throws Exception;
	
}
