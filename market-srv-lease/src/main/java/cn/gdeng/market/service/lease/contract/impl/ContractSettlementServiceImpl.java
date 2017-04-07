package cn.gdeng.market.service.lease.contract.impl;

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
import cn.gdeng.market.dto.lease.contract.ContractStatementsDTO;
import cn.gdeng.market.entity.lease.contract.ContractApprovalEntity;
import cn.gdeng.market.entity.lease.contract.ContractMainEntity;
import cn.gdeng.market.entity.lease.contract.ContractStatementsEntity;
import cn.gdeng.market.enums.ApprovalMethodEnum;
import cn.gdeng.market.enums.ApprovalRecordStatusEnum;
import cn.gdeng.market.enums.ApprovalStatusEnum;
import cn.gdeng.market.enums.ContractStatusEnum;
import cn.gdeng.market.enums.MarketResourceLeaseStatusEnum;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.contract.ContractSettlementService;
import cn.gdeng.market.service.lease.finance.FinanceFeeService;
import cn.gdeng.market.service.lease.resources.MarketResourceService;

import com.gudeng.framework.dba.transaction.annotation.Transactional;

/**
 * 合同结算服务层实现
 * @author Administrator
 *
 */
@Service(value="contractSettlementService")
public class ContractSettlementServiceImpl implements ContractSettlementService {

	@Autowired
	private BaseDao<ContractApprovalEntity> baseDaoApproval;
	
	@Autowired
	private BaseDao<ContractMainEntity> baseDaoMain;
	@Autowired
	private BaseDao<?> baseDao;
	
	@Resource	
	private MarketResourceService marketResourceService;
	
	@Resource
	private FinanceFeeService financeFeeService;
	@Autowired
	private ContractManageServiceImpl contractManageService;

	/**
	 * 合同结算人工审核操作
	 */
	@Override
	@Transactional
	public void auditByHuman(ContractApprovalEntity entity) throws BizException{
		baseDaoApproval.persist(entity);
		ContractMainEntity main  = new ContractMainEntity();
		main.setId(entity.getContractId());
		main = baseDaoMain.find(main);
		//main.setApprovalType(ApprovalTypeEnum.CONTRACT_CLOSE.getCode());
		//根据合同Id查出对应的结算记录信息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractId", entity.getContractId());
		ContractStatementsEntity statEntity = getByContractId(map);
		if(entity.getApprovalStatus().equals(ApprovalRecordStatusEnum.TG.getCode())){
			statEntity.setApprovalStatus(ApprovalStatusEnum.YTG.getCode());
		}else{
			statEntity.setApprovalStatus(ApprovalStatusEnum.YBH.getCode());
		}
		main.setApprovalMethod(ApprovalMethodEnum.CONTRACT_HUMAN.getCode());
		main.setApprovalTimeC(new Date());
		if(entity.getApprovalStatus().equals(ApprovalRecordStatusEnum.TG.getCode())){
			//更改合同的状态为已结算
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
				feeMap.put("contractId", entity.getContractId());
				feeMap.put("currentTime", statEntity.getCreateTime());
				feeMap.put("isDeteled", 1);
				financeFeeService.updateBycontract(feeMap);
			}
		}
		baseDao.dynamicMerge(statEntity);
		baseDaoMain.dynamicMerge(main);
	}

	/**
	 * 分页查询
	 * @param GuDengPage
	 * @return GuDengPage
	 */
	@Override
	public GuDengPage<ContractStatementsDTO> getByConditionPage(GuDengPage<ContractStatementsDTO> page){
		int total = baseDao.queryForObject("ContractStatements.countByCondition",page.getParaMap(),Integer.class);
		List<ContractStatementsDTO> list=null;
		if(total>0){
			list=baseDao.queryForList("ContractStatements.queryByConditionPage", page.getParaMap(),ContractStatementsDTO.class);
		}
		page.setTotal(total);
		page.setRecordList(list);
		return page;
	}
	
	@Override
	public List<ContractStatementsDTO> getExpConditionList(Map<String, Object> map){
		return  (List<ContractStatementsDTO>)baseDao.queryForList("ContractStatements.queryByConditionPage",map,ContractStatementsDTO.class);
	}
	@Override
	public int getExpConditionCount(Map<String, Object> map) {
		return baseDao.queryForObject("ContractStatements.countByCondition",map,Integer.class);
	}

	/**
	 * 根据主键Id查询合同结算实体
	 */
	@Override
	public ContractStatementsEntity findById(Integer id) {
		ContractStatementsEntity entity = new ContractStatementsEntity();
		entity.setId(id);
		return baseDao.find(ContractStatementsEntity.class, entity);
	}

	/**
	 * 保存合同结算信息
	 */
	@Override
	public void edit(ContractStatementsEntity entity) throws Exception{
		baseDao.dynamicMerge(entity);
	}

	/**
	 * 根据合同Id查找合同结算记录
	 */
	@Override
	public ContractStatementsEntity getByContractId(Map<String, Object> map) {
		return baseDao.queryForObject("ContractStatements.getStatementByContractId", map, ContractStatementsEntity.class);
	}
    /**
     * 添加结算信息
     */
	@Override
	public void save(ContractStatementsEntity entity) throws Exception {
		Map <String ,Object> paramMap=new HashMap <String ,Object>();
		paramMap.put("contractId", entity.getContractId());
		if(!contractManageService.getAddContractSettlementPower(paramMap)){
			throw new BizException(MsgCons.C_20000, "此合同有未审批的结算信息！");	
		}
		ContractMainEntity main = new ContractMainEntity();
		main.setId(entity.getContractId());
		main = baseDao.find(ContractMainEntity.class, main);
		entity.setApprovalStatus(ApprovalStatusEnum.DSP.getCode());
		entity.setContractNo(main.getContractNo());
		entity.setMarketId(main.getMarketId());
		baseDao.persist(entity, Long.class);
		}
}
