package cn.gdeng.market.service.lease.contract;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.contract.ContractAccessoriesDTO;
import cn.gdeng.market.dto.lease.contract.ContractDTO;
import cn.gdeng.market.dto.lease.contract.ContractEntityDTO;
import cn.gdeng.market.dto.lease.contract.ContractFreeDTO;
import cn.gdeng.market.dto.lease.contract.ContractLeaseDTO;
import cn.gdeng.market.dto.lease.contract.ContractMainDTO;
import cn.gdeng.market.dto.lease.contract.ContractOthersFeeDTO;
import cn.gdeng.market.dto.lease.contract.ContractPaymentDTO;
import cn.gdeng.market.dto.lease.resources.MarketLeasePostageDTO;
import cn.gdeng.market.dto.workflow.WfProcessDTO;
import cn.gdeng.market.entity.lease.contract.ContractAccessoriesEntity;
import cn.gdeng.market.entity.lease.contract.ContractApprovalEntity;
import cn.gdeng.market.entity.lease.contract.ContractFreeEntity;
import cn.gdeng.market.entity.lease.contract.ContractLeaseEntity;
import cn.gdeng.market.entity.lease.contract.ContractMainEntity;
import cn.gdeng.market.entity.lease.contract.ContractOthersFeeEntity;
import cn.gdeng.market.entity.lease.contract.ContractPaymentEntity;
import cn.gdeng.market.entity.lease.settings.SysWorkflowSettingEntity;
import cn.gdeng.market.exception.BizException;

/**
 * 合同管理服务接口类
 *
 */

public interface ContractManageService {
	/**
	 * 合同主信息
	 */
	public ContractMainDTO showMainInfo(Map<String, Object> paramMap);
	
	/**
	 * 获取合同根据资源id
	 * @param paramMap
	 * @return
	 */
	public int getContractsByResourceId(Map<String, Object> paramMap);

	/**
	 * 按周期收费-租赁约定记录
	 */
	public List<ContractLeaseDTO> findAllLease(Map<String, Object> paramMap);
	public int deletAllLease(Map<String, Object> paramMap);
	/**
	 * 按周期收费-免租约定记录
	 */
	public List<ContractFreeDTO> findAllFree(Map<String, Object> paramMap);
	public int deleteAllFree(Map<String, Object> paramMap);
	/**
	 * 按周期收费-其他费约定记录
	 */
	public List<ContractOthersFeeDTO> findAllOthersFee(Map<String, Object> paramMap);
	public int deleteAllOthersFee(Map<String, Object> paramMap);
	/**
	 * 按约定总金额-缴费约定记录
	 */
	public List<ContractPaymentDTO> findAllPayment(Map<String, Object> paramMap);
	public int deleteAllPayment(Map<String, Object> paramMap);
	/**
	 * 合同综合信息（包含主信息、租金信息）
	 */
	public ContractDTO findContract(Map<String, Object> paramMap) throws BizException;
    /**
     * 保存新增合同
     */
	public void saveContract(ContractEntityDTO dto) throws Exception;

	/**
	 * 保存租赁约定
	 */
	public void saveLease(Map<String, Object> paramMap);
	/**
	 * 保存免租约定
	 */
	public void saveFree(Map<String, Object> paramMap);
	/**
	 * 保存其他费项约定
	 */
	public void saveOthersFee(Map<String, Object> paramMap);
	/**
	 * 保存缴费约定
	 */
	public void savePayment(Map<String, Object> paramMap);
	/**
	 * 保存附件
	 */
	public void saveAccessories(Map<String, Object> paramMap);
	/**
	 * 合同管理人工审核操作
	 */
	void auditByHuman(ContractApprovalEntity entity, String user) throws BizException;

	/**
	 * 合同管理分页面
	 */
	public GuDengPage<ContractMainDTO> getByConditionPage(GuDengPage<ContractMainDTO> page);

	/**
	 * 合同管理
	 */
	public List<ContractMainDTO> getExpConditionList(Map<String, Object> map);
	
	/**
	 * 合同管理总条数
	 */
	public int  getExpConditionCount(Map<String, Object> map);
	

	/**通过id获取实体
	 * @param id
	 * @return
	 */
	public ContractMainEntity getById(Integer id);
	
	/** 
	 * 根据 费项Id（itemCategoryId）,计费标准Id（freightBasisId） 查询 未执行 或执行中  的合同数量  
	 * 
	 * @param map
	 * 
	 * map.put("itemCategoryId",itemCategoryId); 
	 * 或
	 * map.put("freightBasisId",freightBasisId);
	 * 
	 * @return
	 */
	public int  getCountByMap(Map<String, Object> map);
	
	/**
	 * 获取已结算的合同，根据计费标准ID
	 * @param map map.put("freightBasisId",freightBasisId);
	 * @return
	 */
	public int countByExpStandard(Map<String,Object> map);
	
	public int updateContractTaskStatusById(Map<String, Object> paramMap);
	public List<ContractMainDTO> queryByLeaseStatusTask(Map<String, Object> map);
	
	/**
	 * 查询满足特定条件的合同主信息
	 */
	public ContractMainDTO findSpecialContract(Map<String, Object> paramMap);

	/**
	 * 查是否有资源租用时间重叠的合同
	 * @param mapKey:  leasingResourceId startLeasingDay  endLeasingDay  contractId
	 * @return
	 */
	public Integer leasingResourceIdByDateTimeCount(Map<String ,Object> map);
	/**
	 * 查是否有此合同编号
	 * @param mapKey:  contractCode  contractId
	 * @return
	 */
	public Integer ContractCodeByCount(Map<String ,Object> map);
	
	/**
	 * 修改合同主表信息
	 * @param entity
	 */
	public int updateContractMain(ContractMainEntity entity);
	/**
	 * 修改合同信息_租赁约定 周期缴费
	 * @param entity
	 */
	public int updateContractLease(ContractLeaseEntity entity);
	/**
	 * 修改合同信息_约定金额
	 * @param entity
	 */
	public int updateContractPayment(ContractPaymentEntity entity);
	
	/**
	 * 修改合同信息_其它费项目
	 * @param entity
	 */
	public int updateContractOthersFee(ContractOthersFeeEntity entity);
	

	/**
	 * 修改合同信息_附件
	 * @param entity
	 */
	public int updateContractAccessories(ContractAccessoriesEntity entity);
	
    /**
     * 修改免租信息
     * @param entity
     * @return
     */
	public int updateContractFree(ContractFreeEntity entity);
	/**
	 * 修改合同信息编辑
	 * @param ContractEntityDTO dto
	 * 通过JSON 解析转换对应
	 */
	public void updateContractMainInfo( ContractEntityDTO  dto)throws Exception;
	
	/**
	 * 查合同所有租赁周期 约定信息
	 * Map<String ,Object> map  key:contractId
	 */
	public List<ContractLeaseDTO> queryAllContractLease(Map<String ,Object> map);
	/**
	 * 查合同所有租赁免租约定信息
	 * Map<String ,Object> map  key:contractId
	 */
	public List<ContractFreeDTO> queryAllContractFree(Map<String ,Object> map);
	
	/**
	 * 查合同所有租赁约定总金额 约定信息
	 * Map<String ,Object> map  key:contractId
	 */
	public List<ContractPaymentDTO> queryAllContractPayment(Map<String ,Object> map);
	

	/**
	 * 查合同所有其它费项
	 * Map<String ,Object> map  key:contractId
	 */
	public List<ContractOthersFeeDTO> queryAllContractOthersFee(Map<String ,Object> map);
	/**
	 * 查合同所有附件信息
	 * Map<String ,Object> map  key:contractId
	 */
	public List<ContractAccessoriesDTO> queryAllContractAccessories(Map<String ,Object> map);

	
	/**
	 * 获取流程列表
	 * @param map
	 * @return
	 */
	public List<WfProcessDTO> queryProcessList(Map<String,Object> map);
	
	/**
	 * 附件
	 * Map<String ,Object> map  key:contractId
	 */
	public List<ContractAccessoriesDTO> findAllAccessories(Map<String, Object> paramMap);
	public int deleteAllAccessories(Map<String, Object> paramMap);
	
	/**
	 * 获取租金周期约定表的最开始的一条记录
	 * @param paramMap
	 * @return
	 */
	public List<ContractLeaseDTO> getFirstLease(Map<String, Object> paramMap);
	
	/**
	 * 获取租金约定总金额表的最开始的一条记录
	 * @param paramMap
	 * @return
	 */
	public List<ContractPaymentDTO> getFirstPayment(Map<String, Object> paramMap);
	
	
	/**
	 *获取工作流审批模式设置
	 * @param map
	 * @return
	 */
	public SysWorkflowSettingEntity getWorkflowSetting(Map<String, Object> paramMap);
	
	/**
	 * 该合同是否可以添加变更信息
	 * @param map
	 * @return  boolean 可以true 不可以  false
	 */
	public boolean getAddContractUpdatePower(Map<String, Object> paramMap);
	/**
	 * 该合同是否可以添加结算信息
	 * @param map
	 * @return  boolean 可以true 不可以  false
	 */
	public boolean getAddContractSettlementPower(Map<String, Object> paramMap);
	
	/**
	 * 根据合同状态和资源ID获取合同列表
	 * @param paramMap
	 * @return
	 */
	public List<MarketLeasePostageDTO> getContractsByResAndStatus(Map<String,Object> paramMap);
	

	/**
	 * 根据合同ID集合查  面积和
	 */
	public Map<String , Object> queryByResourceIds(Map<String, Object> map);
}
