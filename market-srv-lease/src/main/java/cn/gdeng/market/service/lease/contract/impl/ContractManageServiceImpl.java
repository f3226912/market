package cn.gdeng.market.service.lease.contract.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
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
import cn.gdeng.market.entity.lease.finance.FinanceFeeRecordEntity;
import cn.gdeng.market.entity.lease.resources.MarketLeasePostageEntity;
import cn.gdeng.market.entity.lease.settings.SysWorkflowSettingEntity;
import cn.gdeng.market.enums.ApprovalMethodEnum;
import cn.gdeng.market.enums.ApprovalRecordStatusEnum;
import cn.gdeng.market.enums.ApprovalStatusEnum;
import cn.gdeng.market.enums.ApprovalTypeEnum;
import cn.gdeng.market.enums.ContractBillingAreaEnum;
import cn.gdeng.market.enums.ContractChargingWaysEnum;
import cn.gdeng.market.enums.ContractStatusEnum;
import cn.gdeng.market.enums.MarketResourceLeaseStatusEnum;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.contract.ContractChangeService;
import cn.gdeng.market.service.lease.contract.ContractManageService;
import cn.gdeng.market.service.lease.contract.ContractOtherFeeService;
import cn.gdeng.market.service.lease.contract.ContractRentService;
import cn.gdeng.market.service.lease.finance.FinanceFeeService;
import cn.gdeng.market.service.lease.resources.MarketResourceService;
import cn.gdeng.market.util.DateUtil;
import cn.gdeng.market.util.PropertyUtil;

import com.gudeng.framework.dba.transaction.annotation.Transactional;

/**
 * 合同管理服务接口实现类
 * @author Administrator
 *
 */
@Service(value="contractManageService")
public class ContractManageServiceImpl implements ContractManageService {

	@Resource
	private PropertyUtil propertyUtil;
	@Autowired
	private BaseDao<?> baseDao;
	
	@Autowired
	private BaseDao<ContractApprovalEntity> baseDaoApproval;
	
	@Autowired
	private BaseDao<ContractMainEntity> baseDaoMain;
	
	@Autowired 
	private BaseDao<ContractPaymentEntity> baseDaoPayment;
	
	@Autowired
	private BaseDao<ContractLeaseEntity> baseDaoLease;
	
	@Autowired
	private BaseDao<ContractOthersFeeEntity> baseDaoOthersFee;
	
	@Autowired
	private BaseDao<ContractFreeEntity> baseDaoContractFree;
	
	@Autowired
	private BaseDao<ContractAccessoriesEntity> baseDaoAccessories;
	
//	@Autowired
//	private BaseDao<ContractChangeEntity> baseDaoChange;
	
	@Resource	
	private MarketResourceService marketResourceService;
	
	@Resource
	private FinanceFeeService financeFeeService;
	
	@Resource
	private ContractRentService contractRentService;
	
	@Resource
	private ContractOtherFeeService contractOtherFeeService;
	
	@Resource
	private ContractChangeService contractChangeService;

	@Override
	public GuDengPage<ContractMainDTO> getByConditionPage(
			GuDengPage<ContractMainDTO> page)  {
		int total = baseDao.queryForObject("ContractMain.countByCondition",page.getParaMap(),Integer.class);
		List<ContractMainDTO> recordList=null;
		if(total>0){
		 recordList = baseDao.queryForList("ContractMain.queryByConditionPage",page.getParaMap(),ContractMainDTO.class);
		}
		page.setTotal(total);
		page.setRecordList(recordList);
		return page;
	}

	@Override
	public List<ContractMainDTO> getExpConditionList(Map<String, Object> map){
		return  (List<ContractMainDTO>)baseDao.queryForList("ContractMain.queryByConditionPage",map,ContractMainDTO.class);
	}
	@Override
	public List<ContractMainDTO> queryByLeaseStatusTask(Map<String, Object> map){
		return  (List<ContractMainDTO>)baseDao.queryForList("ContractMain.queryByLeaseStatusTask",map,ContractMainDTO.class);
	}
	@Override
	public int getExpConditionCount(Map<String, Object> map) {
		return (int)baseDao.queryForObject("ContractMain.countByCondition",map,Integer.class);
	}
	
	/**
	 * 合同管理人工审核操作
	 */
	@Transactional
	@Override
	public void auditByHuman(ContractApprovalEntity entity, String user)throws BizException{
		baseDaoApproval.persist(entity);
		ContractMainEntity main  = new ContractMainEntity();
		main.setId(entity.getContractId());
		main = baseDaoMain.find(main);
		main.setApprovalType(ApprovalTypeEnum.CONTRACT_MANAGER.getCode());
		if(entity.getApprovalStatus().equals(ApprovalRecordStatusEnum.TG.getCode())){
			main.setApprovalStatus(ApprovalStatusEnum.YTG.getCode());
		}else{
			main.setApprovalStatus(ApprovalStatusEnum.YBH.getCode());
		}
		main.setApprovalMethod(ApprovalMethodEnum.CONTRACT_HUMAN.getCode());
		main.setApprovalTimeA(new Date());
		if(entity.getApprovalStatus().equals(ApprovalRecordStatusEnum.TG.getCode())){
			main.setContractStatus(ContractStatusEnum.ZXZ.getCode());
			//更新资源为已租
			String [] ids=main.getLeasingResourceIds().split(",");
			List<Map<String,Object>>  listMap=new ArrayList<Map<String,Object>> ();
			for (String id:ids) {
				Map<String,Object> resourceMap=new HashMap<String, Object>();
				resourceMap.put("id",id);
				resourceMap.put("leaseStatus", MarketResourceLeaseStatusEnum.RENTED.getStatus());
				listMap.add(resourceMap);
				//写入合同资源关联表
				MarketLeasePostageEntity lease = new MarketLeasePostageEntity();
				lease.setContractId(entity.getContractId());
				lease.setResourceId(Integer.parseInt(id));
				lease.setCreateUserId(user);
				lease.setCreateTime(new Date());
				baseDao.persist(lease, MarketLeasePostageEntity.class);
			}
			marketResourceService.updateBatch(listMap);
			//生成应收款参数List
			List<FinanceFeeRecordEntity> params = new ArrayList<FinanceFeeRecordEntity>();
			contractRentService.generateRental(main, params,user);
			contractOtherFeeService.generateOthersFee(main, params,user);
			financeFeeService.insertFeeRecordList(params);
		}
		baseDaoMain.dynamicMerge(main);
	}

	@Override
	public ContractMainDTO showMainInfo(Map<String,Object> paramMap) {
		ContractMainDTO dto = baseDao.queryForObject("ContractMain.findOneById", paramMap, ContractMainDTO.class);
		return dto;
	}

	@Override
	public List<ContractLeaseDTO> findAllLease(Map<String, Object> paramMap) {
		List<ContractLeaseDTO> list = baseDao.queryForList("ContractLease.queryAll", paramMap, ContractLeaseDTO.class);
		return list;
	}
	@Override
	public int deletAllLease(Map<String, Object> paramMap) {
		return  (int)baseDao.execute("ContractLease.deleteAll", paramMap);
	}
	
	@Override
	public List<ContractFreeDTO> findAllFree(Map<String, Object> paramMap) {
		List<ContractFreeDTO> list = baseDao.queryForList("ContractFree.queryAll", paramMap, ContractFreeDTO.class);
		return list;
	}
	@Override
	public int deleteAllFree(Map<String, Object> paramMap) {
		return  (int)baseDao.execute("ContractFree.deleteAll", paramMap);
	}
	
	@Override
	public List<ContractOthersFeeDTO> findAllOthersFee(Map<String, Object> paramMap) {
		List<ContractOthersFeeDTO> list = baseDao.queryForList("ContractOthersFee.queryAll", paramMap, ContractOthersFeeDTO.class);
		return list;
	}
	@Override
	public int deleteAllOthersFee(Map<String, Object> paramMap) {
		return  (int)baseDao.execute("ContractOthersFee.deleteAll", paramMap);
	}
	
	
	@Override                          
	public List<ContractAccessoriesDTO> findAllAccessories(Map<String, Object> paramMap) {
		List<ContractAccessoriesDTO> list = baseDao.queryForList("ContractAccessories.queryAll", paramMap, ContractAccessoriesDTO.class);
		return list;
	}
	@Override
	public int deleteAllAccessories(Map<String, Object> paramMap) {
		return  (int)baseDao.execute("ContractAccessories.deleteAll", paramMap);
	}
	
	
	@Override
	public List<ContractPaymentDTO> findAllPayment(Map<String, Object> paramMap) {
		List<ContractPaymentDTO> list = baseDao.queryForList("ContractPayment.queryAll", paramMap, ContractPaymentDTO.class);
		return list;
	}
	@Override
	public int deleteAllPayment(Map<String, Object> paramMap) {
		return  (int)baseDao.execute("ContractPayment.deleteAll", paramMap);
	}

	@Override
	public ContractDTO findContract(Map<String, Object> paramMap) throws BizException{
		ContractDTO dto = new ContractDTO();
		dto.setFileCompleteUrl(propertyUtil.getValue("gd.uplaod.host"));
		dto.setMainDTO(showMainInfo(paramMap));
	   if(dto.getMainDTO().getChargingWays().equals(ContractChargingWaysEnum.CYCLE.getCode())){
			dto.setLeaseList(findAllLease(paramMap));
			dto.setFreeList(findAllFree(paramMap));
		}else{
			dto.setPaymentList(findAllPayment(paramMap));
		}			
		dto.setOthersFeeList(findAllOthersFee(paramMap));	
		dto.setAccessoriesList(findAllAccessories(paramMap));
		dto.setChangeDto(contractChangeService.getConditionChange(paramMap));
		return dto;
	}

	@Override
	@Transactional
	public void saveContract(ContractEntityDTO dto) throws Exception {
		Map<String ,Object> countMap=new HashMap<String ,Object> ();
		countMap.put("contractCode", dto.getMainEntity().getContractNo());
		int contractCodeCount=ContractCodeByCount(countMap);
		if(contractCodeCount>0){
			throw new BizException(MsgCons.C_20000, "合同编号已存在！");	
		}
		countMap.put("startLeasingDay", DateUtil.toString(dto.getMainEntity().getStartLeasingDay(), "yyyy-MM-dd"));
		countMap.put("endLeasingDay",DateUtil.toString(dto.getMainEntity().getEndLeasingDay(), "yyyy-MM-dd"));
		String [] LeasingResourceIds=dto.getMainEntity().getLeasingResourceIds().split(",");
		String [] LeasingResource=dto.getMainEntity().getLeasingResource().split(",");
		StringBuffer msg=new StringBuffer();
		int index=0;
		int repeatCount=0;
		for (String LeasingResourceId:LeasingResourceIds) {
			countMap.put("leasingResourceId", LeasingResourceId);
			int re =leasingResourceIdByDateTimeCount(countMap);
			if(re>0){
				repeatCount++;
				msg.append(LeasingResource[index]+" ");	
			}
			index++;
		}
		if(repeatCount>0){
			throw new BizException(MsgCons.C_20000, msg.append(" 已被租出 请修改租用时间或租用资源！").toString());
		}
		dto.getMainEntity().setContractStatus(ContractStatusEnum.DZX.getCode());
		ContractMainEntity entity=dto.getMainEntity();
		if(dto.getMainEntity().getChargingWays().equals(ContractChargingWaysEnum.TOTAL.getCode())){
			entity.setBillingArea(ContractBillingAreaEnum.FREE_AREA.getCode());
			entity.setPaymentCycle(null);
			entity.setPaymentDayType(null);
			entity.setPaymentDays(null);
		}
		double count=entity.getCountArea();
		entity.setApprovalStatus(ApprovalStatusEnum.DSP.getCode());
		entity.setApprovalType(ApprovalTypeEnum.CONTRACT_MANAGER.getCode());
		int id = baseDao.persist(entity, Long.class).intValue();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		if(dto.getMainEntity().getChargingWays().equals(ContractChargingWaysEnum.CYCLE.getCode())){
			 if(null!=dto.getLeaseList()){
			 for(ContractLeaseEntity leaseDTO: dto.getLeaseList()){
				  leaseDTO.setContractId(id);
				  leaseDTO.setIsDeleted((byte) 0);
				  leaseDTO.setCreateUserId(dto.getMainEntity().getCreateUserId());
				  leaseDTO.setCreateTime(new Date());
				  leaseDTO.setContractNo(dto.getMainEntity().getContractNo());
				  leaseDTO.setStartDay(sdf.parse(leaseDTO.getStartDayStr()));
				  leaseDTO.setEndDay(sdf.parse(leaseDTO.getEndDayStr()));
				  baseDao.persist(leaseDTO, Long.class);
			 }}
			 if(null!=dto.getFreeList()){
			 for(ContractFreeEntity freeDTO : dto.getFreeList()){
				  freeDTO.setContractId(id);
				  freeDTO.setIsDeleted((byte) 0);
				  freeDTO.setContractNo(dto.getMainEntity().getContractNo());
				  freeDTO.setCreateUserId(dto.getMainEntity().getCreateUserId());
				  freeDTO.setStartDay(sdf.parse(freeDTO.getStartDayStr()));
				  freeDTO.setEndDay(sdf.parse(freeDTO.getEndDayStr()));
				  freeDTO.setCreateTime(new Date());
				  baseDao.persist(freeDTO, Long.class);
			 }}
		}else{
			 if(null!=dto.getPaymentList()){
			 for(ContractPaymentEntity paymentDTO : dto.getPaymentList()){
				 paymentDTO.setContractId(id);
				 paymentDTO.setIsDeleted((byte) 0);
				 paymentDTO.setContractNo(dto.getMainEntity().getContractNo());
				 paymentDTO.setCreateUserId(dto.getMainEntity().getCreateUserId());
				 paymentDTO.setPaymentTime(sdf.parse(paymentDTO.getPaymentTimeStr()));
				 paymentDTO.setCreateTime(new Date());
				 baseDao.persist(paymentDTO, Long.class);
			 }}
		}
		if(null!=dto.getOthersFeeList()){
		for(ContractOthersFeeEntity othersFeeDTO : dto.getOthersFeeList()){
			 othersFeeDTO.setContractId(id);
			 if(null==othersFeeDTO.getTotal()||"".equals(othersFeeDTO.getTotal())){
				 othersFeeDTO.setTotal("0.00");
			 }
			 othersFeeDTO.setIsDeleted((byte) 0);
			 othersFeeDTO.setContractNo(dto.getMainEntity().getContractNo());
			 othersFeeDTO.setCreateUserId(dto.getMainEntity().getCreateUserId());
			 othersFeeDTO.setCreateTime(new Date());
			 baseDao.persist(othersFeeDTO, Long.class);
		}}
		if(null!=dto.getAccessoriesList()){
		for(ContractAccessoriesEntity accessoriesDTO : dto.getAccessoriesList()){
			accessoriesDTO.setContractId(id);
			accessoriesDTO.setIsDeleted((byte) 0);
			accessoriesDTO.setContractNo(dto.getMainEntity().getContractNo());
			accessoriesDTO.setCreateUserId(dto.getMainEntity().getCreateUserId());
			accessoriesDTO.setCreateTime(new Date());
			baseDao.persist(accessoriesDTO, Long.class);
		}}
	}
	
	

	@Override
	public void saveLease(Map<String, Object> paramMap) {
		baseDao.execute("ContractLease.save", paramMap);
	}

	@Override
	public void saveFree(Map<String, Object> paramMap) {
		baseDao.execute("ContractFree.save", paramMap);
	}

	@Override
	public void saveOthersFee(Map<String, Object> paramMap) {
		baseDao.execute("ContractOthersFee.save", paramMap);
	}

	@Override
	public void savePayment(Map<String, Object> paramMap) {
		baseDao.execute("ContractPayment.save", paramMap);
	}

	@Override
	public void saveAccessories(Map<String, Object> paramMap) {
		baseDao.execute("ContractAccessories.save", paramMap);		
	}

	@Override
	public ContractMainEntity getById(Integer id) {
		ContractMainEntity paramEntity = new ContractMainEntity();
		paramEntity.setId(id);
		return baseDao.find(ContractMainEntity.class, paramEntity);
	}

	@Override
	public int updateContractTaskStatusById(Map<String, Object> paramMap) {
	
		return baseDao.execute("ContractMain.updateContractTaskStatusById", paramMap);
	}

	@Override
	public int getCountByMap(Map<String, Object> map) {
		return (int)baseDao.queryForObject("ContractMain.countByMap",map,Integer.class);
	}
	
	@Override
	public int countByExpStandard(Map<String,Object> map)
	{
		return (int)baseDao.queryForObject("ContractMain.countByExpStandard",map,Integer.class);
	}

	@Override
	public ContractMainDTO findSpecialContract(Map<String, Object> paramMap) {
		return baseDao.queryForObject("ContractMain.findSpecialContract", paramMap, ContractMainDTO.class);
	}

	/**
	 * 查是否有资源租用时间重叠的合同
	 * @param mapKey:  leasingResourceId startLeasingDay  endLeasingDay  contractId
	 * @return
	 */
	@Override
	public Integer leasingResourceIdByDateTimeCount(Map<String, Object> map) {
		return (int)baseDao.queryForObject("ContractMain.leasingResourceIdCount",map,Integer.class);
	}
	/**
	 * 查是否有此合同编号
	 * @param mapKey:  contractCode  contractId
	 * @return
	 */
	@Override
	public Integer ContractCodeByCount(Map<String, Object> map) {
		return (int)baseDao.queryForObject("ContractMain.contractCodeCount",map,Integer.class);
	}

	@Override
	public int updateContractMain(ContractMainEntity entity) {
		return baseDaoMain.dynamicMerge(entity);
	}

	@Override
	public int updateContractPayment(ContractPaymentEntity entity) {
		return  baseDaoPayment.dynamicMerge(entity);
	}

	@Override
	public int updateContractLease(ContractLeaseEntity entity) {
		return  baseDaoLease.dynamicMerge(entity);
	}

	@Override
	public int updateContractOthersFee(ContractOthersFeeEntity entity) {
		return baseDaoOthersFee.dynamicMerge(entity);
	}
	

	@Override
	public int updateContractAccessories(ContractAccessoriesEntity entity) {
		return baseDaoAccessories.dynamicMerge(entity);
	}
	@Override
	public int updateContractFree(ContractFreeEntity entity) {
		return baseDaoContractFree.dynamicMerge(entity);
	}
	/**
	 * 编辑（先删除后添加）
	 *  key: contractId
	 */
	@Override
	@Transactional
	public void updateContractMainInfo(ContractEntityDTO dto)  throws Exception{
		ContractMainEntity en = baseDaoMain.find(dto.getMainEntity());
		if(!en.getApprovalStatus().equals(ApprovalStatusEnum.DSP.getCode())&&!en.getApprovalStatus().equals(ApprovalStatusEnum.YBH.getCode())){
			throw new BizException(MsgCons.C_20000, "此合同状态已变更！");	
		}
		Map<String ,Object> countMap=new HashMap<String ,Object> ();
		countMap.put("contractId", dto.getMainEntity().getId());
		countMap.put("contractCode", dto.getMainEntity().getContractNo());
		int contractCodeCount=ContractCodeByCount(countMap);
		if(contractCodeCount>0){
			throw new BizException(MsgCons.C_20000, "合同编号已存在！");	
		}
		countMap.put("startLeasingDay", DateUtil.toString(dto.getMainEntity().getStartLeasingDay(), "yyyy-MM-dd"));
		countMap.put("endLeasingDay",DateUtil.toString(dto.getMainEntity().getEndLeasingDay(), "yyyy-MM-dd"));
		String [] LeasingResourceIds=dto.getMainEntity().getLeasingResourceIds().split(",");
		String [] LeasingResource=dto.getMainEntity().getLeasingResource().split(",");
		StringBuffer msg=new StringBuffer();
		int index=0;
		int repeatCount=0;
		for (String LeasingResourceId:LeasingResourceIds) {
			countMap.put("leasingResourceId", LeasingResourceId);
			int re =leasingResourceIdByDateTimeCount(countMap);
			if(re>0){
				repeatCount++;
				msg.append(LeasingResource[index]+" ");	
			}
			index++;
		}
		if(repeatCount>0){
			throw new BizException(MsgCons.C_20000, msg.append(" 已被租出 请修改租用时间或租用资源！").toString());
		}
		if(dto.getMainEntity().getChargingWays().equals(ContractChargingWaysEnum.TOTAL.getCode())){
			dto.getMainEntity().setBillingArea(ContractBillingAreaEnum.FREE_AREA.getCode());
			dto.getMainEntity().setPaymentCycle(null);
			dto.getMainEntity().setPaymentDayType(null);
			dto.getMainEntity().setPaymentDays(null);
		}
		updateContractMain(dto.getMainEntity());
        Map<String,Object>	paramMap=new HashMap<String,Object>();
        int id=dto.getMainEntity().getId();
        paramMap.put("contractId", dto.getMainEntity().getId());
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		//租赁周期 约定信息
    	deletAllLease(paramMap);
		for(ContractLeaseEntity entity:dto.getLeaseList()){
			entity.setId(null);
			entity.setContractId(id);
			entity.setIsDeleted((byte) 0);
			entity.setCreateUserId(dto.getMainEntity().getCreateUserId());
			entity.setCreateTime(new Date());
			entity.setContractNo(en.getContractNo());
			entity.setStartDay(sdf.parse(entity.getStartDayStr()));
			entity.setEndDay(sdf.parse(entity.getEndDayStr()));
			baseDaoLease.persist(entity);
		}
		//免租约定
		deleteAllFree(paramMap);
		for(ContractFreeEntity entity:dto.getFreeList()){
			entity.setId(null);
			entity.setContractId(id);
			entity.setIsDeleted((byte) 0);
			entity.setContractNo(en.getContractNo());
			entity.setCreateUserId(dto.getMainEntity().getCreateUserId());
			entity.setStartDay(sdf.parse(entity.getStartDayStr()));
			entity.setEndDay(sdf.parse(entity.getEndDayStr()));
			entity.setCreateTime(new Date());
			baseDaoContractFree.persist(entity);
		}
		//租赁约定总金额 约定信息
		deleteAllPayment(paramMap);
		for(ContractPaymentEntity entity:dto.getPaymentList()){
			 entity.setId(null);
			 entity.setContractId(id);
			 entity.setIsDeleted((byte) 0);
			 entity.setContractNo(en.getContractNo());
			 entity.setCreateUserId(dto.getMainEntity().getCreateUserId());
			 entity.setPaymentTime(sdf.parse(entity.getPaymentTimeStr()));
			 entity.setCreateTime(new Date());
			 baseDaoPayment.persist(entity);
		}
		//其它费项
		deleteAllOthersFee(paramMap);
		for(ContractOthersFeeEntity entity:dto.getOthersFeeList()){
			 entity.setId(null);
			 entity.setContractId(id);
			 entity.setIsDeleted((byte) 0);
			 if(null==entity.getTotal()||"".equals(entity.getTotal())){
				 entity.setTotal("0.00");
			 }
			 entity.setContractNo(en.getContractNo());
			 entity.setCreateUserId(dto.getMainEntity().getCreateUserId());
			 entity.setCreateTime(new Date());
			 baseDaoOthersFee.persist(entity);
		}
		//附件
		deleteAllAccessories(paramMap);
		for(ContractAccessoriesEntity entity:dto.getAccessoriesList()){
			 entity.setId(null);
			 entity.setContractId(id);
			 entity.setIsDeleted((byte) 0);
			 entity.setContractNo(en.getContractNo());
			 entity.setCreateUserId(dto.getMainEntity().getCreateUserId());
			 entity.setCreateTime(new Date());
			 baseDaoAccessories.persist(entity);
		}
	}
	/**
	 * 查合同所有附件信息
	 * Map<String ,Object> map  key:contractId
	 */
	@Override
	public List<ContractAccessoriesDTO> queryAllContractAccessories(Map<String ,Object> map) {
		return baseDao.queryForList("ContractAccessories.queryAll", map,ContractAccessoriesDTO.class);
	}
	/**
	 * 查合同所有租赁周期 约定信息
	 * Map<String ,Object> map  key:contractId
	 */
	@Override
	public List<ContractLeaseDTO> queryAllContractLease(Map<String, Object> map) {
	  return baseDao.queryForList("ContractLease.queryAll", map,ContractLeaseDTO.class);
	}
	/**
	 * 查合同所有租赁免租约定信息
	 * Map<String ,Object> map  key:contractId
	 */
	@Override
	public List<ContractFreeDTO> queryAllContractFree(Map<String, Object> map) {
		return baseDao.queryForList("ContractFree.queryAll", map,ContractFreeDTO.class);
	}
	
	/**
	 * 查合同所有租赁约定总金额 约定信息
	 * Map<String ,Object> map  key:contractId
	 */
	@Override
	public List<ContractPaymentDTO> queryAllContractPayment(
			Map<String, Object> map) {
		return baseDao.queryForList("ContractPayment.queryAll", map,ContractPaymentDTO.class);
	}
    
	/**
	 * 查合同所有其它费项
	 * Map<String ,Object> map  key:contractId
	 */
	@Override
	public List<ContractOthersFeeDTO> queryAllContractOthersFee(
			Map<String, Object> map) {
		return baseDao.queryForList("ContractOthersFee.queryAll", map,ContractOthersFeeDTO.class);
	}

	

	
	/**
	 * 获取流程列表
	 * @param map
	 * @return
	 */
	@Override
	public List<WfProcessDTO> queryProcessList(Map<String, Object> map) {
		return baseDao.queryForList("ContractMain.queryProcessList", map,WfProcessDTO.class);
	}

	@Override
	public int getContractsByResourceId(Map<String, Object> paramMap) {
		return (int)baseDao.queryForObject("ContractMain.getContractsByResourceId",paramMap,Integer.class);
	}

	@Override
	public List<ContractLeaseDTO> getFirstLease(Map<String, Object> paramMap) {
		return baseDao.queryForList("ContractLease.getFirstLease", paramMap, ContractLeaseDTO.class);
	}

	@Override
	public List<ContractPaymentDTO> getFirstPayment(Map<String, Object> paramMap) {
		return baseDao.queryForList("ContractPayment.getFirstPayment", paramMap, ContractPaymentDTO.class);
	}
	/**
	 *获取工作流审批模式设置
	 * @param map
	 * @return
	 */
	public SysWorkflowSettingEntity getWorkflowSetting(Map<String, Object> paramMap){
		return baseDao.queryForObject("SysWorkflowSetting.queryByOrgIdAndMarketId", paramMap, SysWorkflowSettingEntity.class);
	}
	/**
	 * 该合同是否可以添加变更信息
	 * @param map
	 * @return  boolean 可以true 不可以  false
	 */
	@Override
	public boolean getAddContractUpdatePower(Map<String, Object> paramMap) {
		int count= baseDao.queryForObject("ContractChange.findUpdateCountbyContractNo", paramMap, Integer.class);
		if(count>0){
		return false;
		}else{
		return true;
		}
	}
	/**
	 * 该合同是否可以添加结算信息
	 * @param map
	 * @return  boolean 可以true 不可以  false
	 */
	@Override
	public boolean getAddContractSettlementPower(Map<String, Object> paramMap) {
		int count= baseDao.queryForObject("ContractStatements.findStatementsCountbyContractId", paramMap, Integer.class);
		if(count>0){
		return false;
		}else{
		return true;
		}
	}

	@Override
	public List<MarketLeasePostageDTO> getContractsByResAndStatus(Map<String, Object> paramMap) {
		return  baseDao.queryForList("ContractMain.getContractsByResAndStatus", paramMap, MarketLeasePostageDTO.class);
	}
	
	public Map<String , Object> queryByResourceIds(Map<String, Object> map){
		Map<String , Object> queryForObject = baseDao.queryForMap("MarketResource.queryByResourceIds", map);
		return 	queryForObject;
	}
}
