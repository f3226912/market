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
import cn.gdeng.market.dto.lease.contract.ContractChangeDTO;
import cn.gdeng.market.dto.lease.contract.ContractEntityDTO;
import cn.gdeng.market.entity.lease.contract.ContractAccessoriesEntity;
import cn.gdeng.market.entity.lease.contract.ContractApprovalEntity;
import cn.gdeng.market.entity.lease.contract.ContractChangeEntity;
import cn.gdeng.market.entity.lease.contract.ContractFreeEntity;
import cn.gdeng.market.entity.lease.contract.ContractLeaseEntity;
import cn.gdeng.market.entity.lease.contract.ContractMainEntity;
import cn.gdeng.market.entity.lease.contract.ContractOthersFeeEntity;
import cn.gdeng.market.entity.lease.contract.ContractPaymentEntity;
import cn.gdeng.market.entity.lease.finance.FinanceFeeRecordEntity;
import cn.gdeng.market.entity.lease.resources.MarketLeasePostageEntity;
import cn.gdeng.market.enums.ApprovalMethodEnum;
import cn.gdeng.market.enums.ApprovalRecordStatusEnum;
import cn.gdeng.market.enums.ApprovalStatusEnum;
import cn.gdeng.market.enums.ApprovalTypeEnum;
import cn.gdeng.market.enums.ContractBillingAreaEnum;
import cn.gdeng.market.enums.ContractChargingWaysEnum;
import cn.gdeng.market.enums.ContractIsCancelEnum;
import cn.gdeng.market.enums.ContractStatusEnum;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.contract.ContractChangeService;
import cn.gdeng.market.service.lease.contract.ContractManageService;
import cn.gdeng.market.service.lease.contract.ContractOtherFeeService;
import cn.gdeng.market.service.lease.contract.ContractRentService;
import cn.gdeng.market.service.lease.finance.FinanceFeeService;

import com.gudeng.framework.dba.transaction.annotation.Transactional;

/**
 * 合同变更服务接口实现类
 * @author Administrator
 *
 */
@Service(value="contractChangeService")
public class ContractChangeServiceImpl implements ContractChangeService {
	
	@Resource
	private ContractManageService manageService;
	
	@Autowired
	private BaseDao<ContractApprovalEntity> baseDaoApproval;
	@Autowired
	private BaseDao<ContractChangeEntity> baseDaoChange;
	@Autowired
	private BaseDao<ContractMainEntity> baseDaoMain;
	@Autowired
	private BaseDao<ContractLeaseEntity> baseDaoLease;
	@Autowired 
	private BaseDao<ContractPaymentEntity> baseDaoPayment;
	@Autowired
	private BaseDao<ContractOthersFeeEntity> baseDaoOthersFee;
	
	@Autowired
	private BaseDao<ContractFreeEntity> baseDaoContractFree;

	@Autowired
	private BaseDao<ContractAccessoriesEntity> baseDaoAccessories;
	
	@Autowired
	private BaseDao<?> baseDao;
	
	@Autowired
	private FinanceFeeService financeFeeService;
	
	@Autowired
	private ContractRentService contractRentService;
	
	@Autowired
	private ContractOtherFeeService contractOtherFeeService;
	
	
	@Autowired
	private ContractManageServiceImpl contractManageService;
	

	/**
	 * 合同变更人工审核操作
	 */
	@Override
	@Transactional
	public void auditByHuman(ContractApprovalEntity entity, String user) throws BizException{
		baseDaoApproval.persist(entity);
		ContractMainEntity main  = new ContractMainEntity();
		main.setId(entity.getContractId());
		main = baseDaoMain.find(main);
		main.setApprovalType(ApprovalTypeEnum.CONTRACT_CHANGED.getCode());
		if(entity.getApprovalStatus().equals(ApprovalRecordStatusEnum.TG.getCode())){
			main.setApprovalStatus(ApprovalStatusEnum.YTG.getCode());
		}else{
			main.setApprovalStatus(ApprovalStatusEnum.YBH.getCode());
		}
		main.setApprovalMethod(ApprovalMethodEnum.CONTRACT_HUMAN.getCode());
		main.setApprovalTimeB(new Date());
		if(entity.getApprovalStatus().equals(ApprovalRecordStatusEnum.TG.getCode())){
			main.setContractStatus(ContractStatusEnum.ZXZ.getCode());
			//更改老合同的是否作废状态为作废
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("contractId", entity.getContractId());
			ContractChangeDTO change = getConditionChange(param);
			ContractMainEntity mainOld = new ContractMainEntity();
			mainOld.setId(change.getContractId());
			mainOld = baseDaoMain.find(mainOld);
			mainOld.setIsCancel(ContractIsCancelEnum.IS.getCode());
			baseDaoMain.dynamicMerge(mainOld);
			//删除老合同变更后的应付款记录
			Map<String, Object> feeMap = new HashMap<String, Object>();
			feeMap.put("contractId", change.getContractId());
			feeMap.put("isDeteled", 1);
			financeFeeService.updateByContractAndStatus(feeMap);
			//生成应付款记录并插入到应收款表中
			List<FinanceFeeRecordEntity> params = new ArrayList<FinanceFeeRecordEntity>();
			contractRentService.generateRental(main, params, user);
			contractOtherFeeService.generateOthersFee(main, params, user);
			financeFeeService.insertFeeRecordList(params);
			//写入合同资源关联表
			String [] ids=main.getLeasingResourceIds().split(",");
			for (String id:ids) {
				MarketLeasePostageEntity lease = new MarketLeasePostageEntity();
				lease.setContractId(entity.getContractId());
				lease.setResourceId(Integer.parseInt(id));
				lease.setCreateUserId(user);
				lease.setCreateTime(new Date());
				baseDao.persist(lease, MarketLeasePostageEntity.class);
			}
		}
		baseDaoMain.dynamicMerge(main);
	}

	@Override
	public List<ContractChangeDTO> getExpConditionList(Map<String, Object> map){
		return  (List<ContractChangeDTO>)baseDao.queryForList("ContractChange.queryByConditionPage",map,ContractChangeDTO.class);
	}
	@Override
	public int getExpConditionCount(Map<String, Object> map) {
		return baseDao.queryForObject("ContractChange.countByCondition",map,Integer.class);
	}
	
	@Override
	public GuDengPage<ContractChangeDTO> getByConditionPage(
			GuDengPage<ContractChangeDTO> page) throws BizException {
		int total = baseDao.queryForObject("ContractChange.countByCondition",page.getParaMap(),Integer.class);
		List<ContractChangeDTO> list=null;
		if(total>0){
			list=baseDao.queryForList("ContractChange.queryByConditionPage", page.getParaMap(),ContractChangeDTO.class);
		}
		page.setTotal(total);
		page.setRecordList(list);
		return page;
	}
	@Override
	@Transactional
	public void updateSaveContract(ContractEntityDTO dto) throws Exception {
		Map<String ,Object> oldIdMap=new HashMap<String, Object>();
		oldIdMap.put("contractId", dto.getMainEntity().getId());
		ContractMainEntity contractMainEntity =new ContractMainEntity ();
		//根据原ID查出合同主体信息
		contractMainEntity.setId( dto.getMainEntity().getId());
		contractMainEntity=baseDao.find(ContractMainEntity.class, contractMainEntity);
		oldIdMap.put("contractNo", contractMainEntity.getContractNo());
		if(!contractManageService.getAddContractUpdatePower(oldIdMap)){
			throw new BizException(MsgCons.C_20000, "此合同有未审批的变更合同！");	
		}
		contractMainEntity.setApprovalStatus(ApprovalStatusEnum.DSP.getCode());
		contractMainEntity.setApprovalType(ApprovalTypeEnum.CONTRACT_CHANGED.getCode());
		contractMainEntity.setContractStatus(ContractStatusEnum.DZX.getCode());
		contractMainEntity.setId(null);
		//复制一条合同记录
		if(dto.getMainEntity().getChargingWays().equals(ContractChargingWaysEnum.CYCLE.getCode())){
			contractMainEntity.setPaymentCycle(dto.getMainEntity().getPaymentCycle());
			contractMainEntity.setPaymentDayType(dto.getMainEntity().getPaymentDayType());
			contractMainEntity.setPaymentDays(dto.getMainEntity().getPaymentDays());
			contractMainEntity.setBillingArea(dto.getMainEntity().getBillingArea());
		}else{
			contractMainEntity.setBillingArea(ContractBillingAreaEnum.FREE_AREA.getCode());
			contractMainEntity.setTotalAmt(dto.getMainEntity().getTotalAmt());
		}
		int id=baseDao.persist(contractMainEntity, Long.class).intValue();
		dto.getChangeEntity().setReviewedTime(new Date());
		dto.getChangeEntity().setContractNewId(id);
		dto.getChangeEntity().setContractId(dto.getMainEntity().getId());
		dto.getChangeEntity().setContractNo(contractMainEntity.getContractNo());
		dto.getChangeEntity().setMarketId(contractMainEntity.getMarketId());
		List<ContractChangeDTO> changeList=changeContractList(oldIdMap);
		if(null == changeList || changeList.size() == 0){
			dto.getChangeEntity().setContractRootId(dto.getMainEntity().getId());	
		}else{
			dto.getChangeEntity().setContractRootId(changeList.get(0).getContractRootId());
		}
		baseDao.persist(dto.getChangeEntity(), Long.class).intValue();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		if(dto.getMainEntity().getChargingWays().equals(ContractChargingWaysEnum.CYCLE.getCode())){
			 if(null!=dto.getLeaseList()){
			 for(ContractLeaseEntity leaseDTO: dto.getLeaseList()){
				  leaseDTO.setContractId(id);
				  leaseDTO.setIsDeleted((byte) 0);
				  leaseDTO.setCreateUserId(dto.getMainEntity().getCreateUserId());
				  leaseDTO.setCreateTime(new Date());
				  leaseDTO.setContractNo(contractMainEntity.getContractNo());
				  leaseDTO.setStartDay(sdf.parse(leaseDTO.getStartDayStr()));
				  leaseDTO.setEndDay(sdf.parse(leaseDTO.getEndDayStr()));
				  baseDao.persist(leaseDTO, Long.class);
			 }}
			 if(null!=dto.getFreeList()){
			 for(ContractFreeEntity freeDTO : dto.getFreeList()){
				  freeDTO.setContractId(id);
				  freeDTO.setIsDeleted((byte) 0);
				  freeDTO.setContractNo(contractMainEntity.getContractNo());
				  freeDTO.setCreateUserId(contractMainEntity.getCreateUserId());
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
				 paymentDTO.setContractNo(contractMainEntity.getContractNo());
				 paymentDTO.setCreateUserId(contractMainEntity.getCreateUserId());
				 paymentDTO.setPaymentTime(sdf.parse(paymentDTO.getPaymentTimeStr()));
				 paymentDTO.setCreateTime(new Date());
				 baseDao.persist(paymentDTO, Long.class);
			 }}
		}
		if(null!=dto.getOthersFeeList()){
		for(ContractOthersFeeEntity othersFeeDTO : dto.getOthersFeeList()){
			 othersFeeDTO.setContractId(id);
			 othersFeeDTO.setIsDeleted((byte) 0);
			 if(null==othersFeeDTO.getTotal() ||"".equals(othersFeeDTO.getTotal())){
				 othersFeeDTO.setTotal("0.00");
			 }
			 othersFeeDTO.setContractNo(contractMainEntity.getContractNo());
			 othersFeeDTO.setCreateUserId(contractMainEntity.getCreateUserId());
			 othersFeeDTO.setCreateTime(new Date());
			 baseDao.persist(othersFeeDTO, Long.class);
		}}
		if(null!=contractManageService.findAllAccessories(oldIdMap)){
		for(ContractAccessoriesDTO contractAccessories : contractManageService.findAllAccessories(oldIdMap)){
			ContractAccessoriesEntity accessoriesEntity=new ContractAccessoriesEntity();
			accessoriesEntity.setContractId(id);
			accessoriesEntity.setIsDeleted((byte) 0);
			accessoriesEntity.setContractNo(contractMainEntity.getContractNo());
			accessoriesEntity.setCreateUserId(contractMainEntity.getCreateUserId());
			accessoriesEntity.setCreateTime(new Date());
			accessoriesEntity.setFileName(contractAccessories.getFileUrl());
			accessoriesEntity.setFileUrl(contractAccessories.getFileUrl());
			accessoriesEntity.setIsDeleted(contractAccessories.getIsDeleted());
			accessoriesEntity.setCreateUserId(dto.getMainEntity().getCreateUserId());
			baseDao.persist(accessoriesEntity, Long.class);
		}}
	}
	
	
	/**
	 * 编辑（先删除后添加）
	 *  key: contractId
	 */
	@Override
	@Transactional
	public void updateContractMainAndChangeInfo(ContractEntityDTO dto)  throws Exception{
		ContractMainEntity en = baseDaoMain.find(dto.getMainEntity());
		if(!en.getApprovalStatus().equals(ApprovalStatusEnum.DSP.getCode())&&!en.getApprovalStatus().equals(ApprovalStatusEnum.YBH.getCode())){
			throw new BizException(MsgCons.C_20000, "此合同状态已变更！");	
		}
        baseDaoMain.dynamicMerge(dto.getMainEntity());
        baseDaoChange.dynamicMerge(dto.getChangeEntity());
        Map<String,Object>	paramMap=new HashMap<String,Object>();
        int id=dto.getMainEntity().getId();
        paramMap.put("contractId", dto.getMainEntity().getId());
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		//租赁周期 约定信息
    	contractManageService.deletAllLease(paramMap);
		for(ContractLeaseEntity entity:dto.getLeaseList()){
			entity.setId(null);
			entity.setContractId(id);
			entity.setIsDeleted((byte) 0);
			entity.setCreateUserId(en.getCreateUserId());
			entity.setCreateTime(new Date());
			entity.setContractNo(en.getContractNo());
			entity.setStartDay(sdf.parse(entity.getStartDayStr()));
			entity.setEndDay(sdf.parse(entity.getEndDayStr()));
			baseDaoLease.persist(entity);
		}
		//免租约定
		contractManageService.deleteAllFree(paramMap);
		for(ContractFreeEntity entity:dto.getFreeList()){
			entity.setId(null);
			entity.setContractId(id);
			entity.setIsDeleted((byte) 0);
			entity.setContractNo(en.getContractNo());
			entity.setCreateUserId(en.getCreateUserId());
			entity.setStartDay(sdf.parse(entity.getStartDayStr()));
			entity.setEndDay(sdf.parse(entity.getEndDayStr()));
			entity.setCreateTime(new Date());
			baseDaoContractFree.persist(entity);
		}
		//租赁约定总金额 约定信息
		contractManageService.deleteAllPayment(paramMap);
		for(ContractPaymentEntity entity:dto.getPaymentList()){
			 entity.setId(null);
			 entity.setContractId(id);
			 entity.setIsDeleted((byte) 0);
			 entity.setContractNo(en.getContractNo());
			 entity.setCreateUserId(en.getCreateUserId());
			 entity.setPaymentTime(sdf.parse(entity.getPaymentTimeStr()));
			 entity.setCreateTime(new Date());
			 baseDaoPayment.persist(entity);
		}
		//其它费项
		contractManageService.deleteAllOthersFee(paramMap);
		for(ContractOthersFeeEntity entity:dto.getOthersFeeList()){
			 entity.setId(null);
			 entity.setContractId(id);
			 entity.setIsDeleted((byte) 0);
			 if(null==entity.getTotal()||"".equals(entity.getTotal())){
				 entity.setTotal("0.00");
			 }
			 entity.setContractNo(en.getContractNo());
			 entity.setCreateUserId(en.getCreateUserId());
			 entity.setCreateTime(new Date());
			 baseDaoOthersFee.persist(entity);
		}
	}
	@Override
	public List<ContractChangeDTO> getOldContract(Map<String, Object> map) {
		return baseDao.queryForList("ContractChange.getOldContract",map,ContractChangeDTO.class);
	}

	@Override
	public ContractChangeDTO getConditionChange(Map<String, Object> map) {
		return baseDao.queryForObject("ContractChange.queryByCondition", map,ContractChangeDTO.class);
    }


	@Override
	public List<ContractChangeDTO> changeContractList(Map<String, Object> map) {
		return baseDao.queryForList("ContractChange.ContractChangeList",map,ContractChangeDTO.class);
	}

	@Override
	public ContractChangeEntity getEntityById(int id) {
		ContractChangeEntity entity = new ContractChangeEntity();
		entity.setId(id);
		return baseDao.find(ContractChangeEntity.class, entity);
	}

}
