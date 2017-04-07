package cn.gdeng.market.service.lease.contract;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.contract.ContractChangeDTO;
import cn.gdeng.market.dto.lease.contract.ContractEntityDTO;
import cn.gdeng.market.entity.lease.contract.ContractApprovalEntity;
import cn.gdeng.market.entity.lease.contract.ContractChangeEntity;
import cn.gdeng.market.exception.BizException;

/**
 * 合同变更服务接口
 * @author Administrator
 *
 */
public interface ContractChangeService{
	/**
	 * 合同变更人工审核操作
	 */
	void auditByHuman(ContractApprovalEntity entity, String user) throws BizException;
	/**
	 * 合同变更分页
	 */
	public GuDengPage<ContractChangeDTO> getByConditionPage(GuDengPage<ContractChangeDTO> page) throws BizException ;
	/**
	 * 合同变更
	 */
	public List<ContractChangeDTO> getExpConditionList(Map<String, Object> map);
	
	/**
	 * 合同变更总条数
	 */
	public int  getExpConditionCount(Map<String, Object> map);
	/**
     * 合同变更保存合同
     */
	public void updateSaveContract(ContractEntityDTO dto) throws Exception;
	/**
	 * 获取旧合同的相关信息
	 */
	public List<ContractChangeDTO> getOldContract(Map<String, Object> map);
	/**
	 * 合同变更信息
	 */
	public ContractChangeDTO getConditionChange(Map<String, Object> map);
	
	/**
	 * 合同历史记录
	 */
	public List<ContractChangeDTO> changeContractList(Map<String, Object> map);
	/**
	 * 根据id获取变更信息
	 */
	public ContractChangeEntity getEntityById(int id);
	/**
	 * 编辑合同变更信息
	 * @param dto
	 */
	public void updateContractMainAndChangeInfo(ContractEntityDTO dto)throws Exception;
}
