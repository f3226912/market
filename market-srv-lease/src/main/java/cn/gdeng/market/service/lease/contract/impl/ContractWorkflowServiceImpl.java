package cn.gdeng.market.service.lease.contract.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.framework.dba.transaction.annotation.Transactional;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.lease.contract.ContractChangeDTO;
import cn.gdeng.market.dto.lease.contract.ContractMainDTO;
import cn.gdeng.market.entity.lease.contract.ContractApprovalEntity;
import cn.gdeng.market.entity.lease.contract.ContractMainEntity;
import cn.gdeng.market.entity.lease.contract.ContractStatementsEntity;
import cn.gdeng.market.entity.lease.finance.FinanceFeeRecordEntity;
import cn.gdeng.market.entity.lease.resources.MarketLeasePostageEntity;
import cn.gdeng.market.enums.ApprovalMethodEnum;
import cn.gdeng.market.enums.ApprovalRecordStatusEnum;
import cn.gdeng.market.enums.ApprovalStatusEnum;
import cn.gdeng.market.enums.ApprovalTypeEnum;
import cn.gdeng.market.enums.ContractChargingWaysEnum;
import cn.gdeng.market.enums.ContractIsCancelEnum;
import cn.gdeng.market.enums.ContractStatusEnum;
import cn.gdeng.market.enums.MarketResourceLeaseStatusEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.contract.ContractChangeService;
import cn.gdeng.market.service.lease.contract.ContractOtherFeeService;
import cn.gdeng.market.service.lease.contract.ContractRentService;
import cn.gdeng.market.service.lease.contract.ContractSettlementService;
import cn.gdeng.market.service.lease.contract.ContractWorkflowService;
import cn.gdeng.market.service.lease.finance.FinanceFeeService;
import cn.gdeng.market.service.lease.resources.MarketResourceService;
import cn.gdeng.market.service.lease.settings.MarketExpenditureService;
/**
 * 合同给工作流提供
 * @author  wj
 *
 */
@Service(value="contractWorkflowService")
public class ContractWorkflowServiceImpl implements ContractWorkflowService {
	
	@Autowired
	private BaseDao<ContractApprovalEntity> baseDaoApproval;
	@Autowired
	private BaseDao<ContractMainEntity> baseDaoMain;
	@Autowired
	private BaseDao<?> baseDao;
	@Autowired
	private MarketExpenditureService marketExpenditureService;
	@Resource
	private FinanceFeeService financeFeeService;
	@Resource	
	private MarketResourceService marketResourceService;
	@Autowired
	private ContractRentService contractRentService;
	@Autowired
	private ContractOtherFeeService contractOtherFeeService;
	@Autowired
	private ContractChangeService contractChangeService;
	@Autowired
	private ContractSettlementService contractSettlementService;
	
	@Override
	public void submitApproval(Integer contractId, Byte type) {
		ContractMainEntity main  = new ContractMainEntity();
		main.setId(contractId);
		main = baseDaoMain.find(main);
		main.setApprovalMethod(ApprovalMethodEnum.CONTRACT_WORKFLOW.getCode());
		if(type.equals(ApprovalTypeEnum.CONTRACT_CLOSE.getCode())){
			ContractStatementsEntity statement = getStatementByContractId(contractId);
			statement.setApprovalStatus(ApprovalStatusEnum.SPZ.getCode());
			baseDao.dynamicMerge(statement);
		}else{
			main.setApprovalType(type);
			main.setApprovalStatus(ApprovalStatusEnum.SPZ.getCode());
		}
		baseDaoMain.dynamicMerge(main);
	}
	
	@Transactional
	@Override
	public void contractApproval(Integer contractId, Byte status) throws BizException{
		ContractMainEntity main  = new ContractMainEntity();
		main.setId(contractId);
		main = baseDaoMain.find(main);
		ContractApprovalEntity entity=new ContractApprovalEntity();
		entity.setContractId(contractId);
		entity.setContractNo(main.getContractNo());
		if(status.equals(ApprovalStatusEnum.YTG.getCode())){
			entity.setApprovalStatus(ApprovalRecordStatusEnum.TG.getCode());
		}else{
			entity.setApprovalStatus(ApprovalRecordStatusEnum.BH.getCode());
		}
		entity.setApprovalType(ApprovalTypeEnum.CONTRACT_MANAGER.getCode());
		entity.setApprovalMethod(ApprovalMethodEnum.CONTRACT_WORKFLOW.getCode());
		baseDaoApproval.persist(entity);
		main.setApprovalStatus(status);
		main.setApprovalTimeA(new Date());
		//审核通过
		if(ApprovalStatusEnum.YTG.getCode().equals(status)){
			//更改合同状态为执行中
			main.setContractStatus(ContractStatusEnum.ZXZ.getCode());
			//生成应收款参数List
			List<FinanceFeeRecordEntity> params = new ArrayList<FinanceFeeRecordEntity>();
			String [] ids=main.getLeasingResourceIds().split(",");
			List<Map<String,Object>>  listMap=new ArrayList<Map<String,Object>> ();
			//更新资源为已租
			for (String id:ids) {
				Map<String,Object> resourceMap=new HashMap<String, Object>();
				resourceMap.put("id",id);
				resourceMap.put("leaseStatus", MarketResourceLeaseStatusEnum.RENTED.getStatus());
				listMap.add(resourceMap);
				//写入合同资源关联表
				MarketLeasePostageEntity lease = new MarketLeasePostageEntity();
				lease.setContractId(contractId);
				lease.setResourceId(Integer.parseInt(id));
				lease.setCreateTime(new Date());
				baseDao.persist(lease, MarketLeasePostageEntity.class);
			}
			marketResourceService.updateBatch(listMap);
			contractRentService.generateRental(main, params,null);
			contractOtherFeeService.generateOthersFee(main, params,null);
			financeFeeService.insertFeeRecordList(params);
		}
		baseDaoMain.dynamicMerge(main);
	}
	@Transactional
	@Override
	public void contractChangeApproval(Integer contractId, Byte status) throws BizException{
		ContractMainEntity main  = new ContractMainEntity();
		main.setId(contractId);
		main = baseDaoMain.find(main);
		ContractApprovalEntity entity=new ContractApprovalEntity();
		entity.setContractId(contractId);
		entity.setContractNo(main.getContractNo());
		if(status.equals(ApprovalStatusEnum.YTG.getCode())){
			entity.setApprovalStatus(ApprovalRecordStatusEnum.TG.getCode());
		}else{
			entity.setApprovalStatus(ApprovalRecordStatusEnum.BH.getCode());
		}
		entity.setApprovalType(ApprovalTypeEnum.CONTRACT_CHANGED.getCode());
		entity.setApprovalMethod(ApprovalMethodEnum.CONTRACT_WORKFLOW.getCode());
		baseDaoApproval.persist(entity);
		main.setApprovalStatus(status);
		main.setApprovalTimeB(new Date());
		if(ApprovalStatusEnum.YTG.getCode().equals(status)){
			//更改变更后的合同状态为执行中
			main.setContractStatus(ContractStatusEnum.ZXZ.getCode());
			//更改老合同的是否作废状态为作废
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("contractId", contractId);
			ContractChangeDTO change = contractChangeService.getConditionChange(param);
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
			//生成应付款记录
			List<FinanceFeeRecordEntity> params = new ArrayList<FinanceFeeRecordEntity>();
			contractRentService.generateRental(main, params, null);
			contractOtherFeeService.generateOthersFee(main, params, null);
			financeFeeService.insertFeeRecordList(params);
			//写入合同资源关联表
			String [] ids=main.getLeasingResourceIds().split(",");
			for (String id:ids) {
				MarketLeasePostageEntity lease = new MarketLeasePostageEntity();
				lease.setContractId(contractId);
				lease.setResourceId(Integer.parseInt(id));
				lease.setCreateTime(new Date());
				baseDao.persist(lease, MarketLeasePostageEntity.class);
			}
		}
		baseDaoMain.dynamicMerge(main);
	}
	
	@Transactional
	@Override
	public void contractSettlementApproval(Integer contractId, Byte status) throws BizException{
		ContractMainEntity main  = new ContractMainEntity();
		main.setId(contractId);
		main = baseDaoMain.find(main);
		ContractApprovalEntity entity=new ContractApprovalEntity();
		entity.setContractId(contractId);
		entity.setContractNo(main.getContractNo());
		if(status.equals(ApprovalStatusEnum.YTG.getCode())){
			entity.setApprovalStatus(ApprovalRecordStatusEnum.TG.getCode());
		}else{
			entity.setApprovalStatus(ApprovalRecordStatusEnum.BH.getCode());
		}
		entity.setApprovalType(ApprovalTypeEnum.CONTRACT_CLOSE.getCode());
		entity.setApprovalMethod(ApprovalMethodEnum.CONTRACT_WORKFLOW.getCode());
		baseDaoApproval.persist(entity);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractId", contractId);
		ContractStatementsEntity statEntity = contractSettlementService.getByContractId(map);
		statEntity.setApprovalStatus(status);
		main.setApprovalTimeC(new Date());
		if(ApprovalStatusEnum.YTG.getCode().equals(status)){
			main.setContractStatus(ContractStatusEnum.JS.getCode());
			//更新资源为未租
			String [] ids=main.getLeasingResourceIds().split(",");
			List<Map<String,Object>>  listMap=new ArrayList<Map<String,Object>> ();
			for (String id:ids) {
				Map<String,Object> resourceMap=new HashMap<String, Object>();
				resourceMap.put("id",id);
				resourceMap.put("leaseStatus", MarketResourceLeaseStatusEnum.WAIT_FOR_RENT.getStatus());
				listMap.add(resourceMap);
			}
			marketResourceService.updateBatch(listMap);
			//提前解约删除未收费的预付款记录
			if(statEntity.getStatementsType() == 1){
				Map<String, Object> feeMap = new HashMap<String, Object>();
				feeMap.put("contractId", contractId);
				feeMap.put("currentTime", statEntity.getCreateTime());
				feeMap.put("isDeteled", 1);
				financeFeeService.updateBycontract(feeMap);
			}
		}
		baseDao.dynamicMerge(statEntity);
		baseDaoMain.dynamicMerge(main);
	}
	
	@Override
	public ContractMainDTO contractApprovalStatus(Integer contractId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractId", contractId);
		ContractMainDTO dto = baseDao.queryForObject("ContractMain.queryByWorkFlow", map, ContractMainDTO.class);
		ContractMainEntity entity = new ContractMainEntity();
		entity.setId(contractId);
		entity = baseDao.find(ContractMainEntity.class, entity);
		if(entity.getChargingWays().equals(ContractChargingWaysEnum.CYCLE.getCode())){
			List<FinanceFeeRecordEntity> params = new ArrayList<FinanceFeeRecordEntity>();
			contractRentService.generateRental(entity, params, null);
			double totalAmt = 0;
			if(params.size() > 0){
				for(FinanceFeeRecordEntity record : params){
					totalAmt += record.getAccountPayable();
				}
			}
			dto.setTotalAmt(totalAmt);
		}
		return dto;
	}
	
	@Override
	public ContractMainDTO contractChangeApprovalStatus(Integer contractId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractId", contractId);
		ContractMainDTO dto = baseDao.queryForObject("ContractChange.queryByWorkFlow", map, ContractMainDTO.class);
		ContractMainEntity entity = new ContractMainEntity();
		entity.setId(contractId);
		entity = baseDao.find(ContractMainEntity.class, entity);
		if(entity.getChargingWays().equals(ContractChargingWaysEnum.CYCLE.getCode())){
			List<FinanceFeeRecordEntity> params = new ArrayList<FinanceFeeRecordEntity>();
			contractRentService.generateRental(entity, params, null);
			double totalAmt = 0;
			if(params.size() > 0){
				for(FinanceFeeRecordEntity record : params){
					totalAmt += record.getAccountPayable();
				}
			}
			dto.setTotalAmt(totalAmt);
		}
	    return dto;
	}
	
	@Override
	public ContractMainDTO contractStatementsApprovalStatus(Integer contractId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractId", contractId);
	    return baseDao.queryForObject("ContractStatements.queryByWorkFlow", map, ContractMainDTO.class);
	}

	@Override
	public Double ContractMoney(Integer ContractId) {
		List<FinanceFeeRecordEntity> params = new ArrayList<FinanceFeeRecordEntity>();
		ContractMainEntity main  = new ContractMainEntity();
		main.setId(ContractId);
		main = baseDaoMain.find(main);
		contractRentService.generateRental(main, params, null);
		double money=0.0;
		if(params.size()>0){
           for (FinanceFeeRecordEntity f: params) {
        	   money=f.getAccountPayable()+money;
		}
          return Double.valueOf( new java.text.DecimalFormat("0.00").format(money));
		}else{
			return null;
		}		
	}

	@Override
	public ContractStatementsEntity getStatementByContractId(Integer contractId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractId", contractId);
		return baseDao.queryForObject("ContractStatements.getStatementByContractId", map, ContractStatementsEntity.class);
	}
	

}
