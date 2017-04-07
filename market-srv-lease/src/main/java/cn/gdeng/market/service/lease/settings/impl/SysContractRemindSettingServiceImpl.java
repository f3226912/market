package cn.gdeng.market.service.lease.settings.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.framework.dba.transaction.annotation.Transactional;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.lease.settings.MarketDTO;
import cn.gdeng.market.dto.lease.settings.SysContractRemindSettingDTO;
import cn.gdeng.market.dto.lease.settings.SysContractRemindSettingInteractionDTO;
import cn.gdeng.market.entity.admin.SysOrgEntity;
import cn.gdeng.market.entity.lease.settings.SysContractRemindSettingEntity;
import cn.gdeng.market.entity.lease.settings.SysWorkflowSettingEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.settings.SysContractRemindSettingService;

@Service(value="sysContractRemindSettingService")
public class SysContractRemindSettingServiceImpl implements SysContractRemindSettingService {

	@Autowired
	private BaseDao<SysOrgEntity> baseDao;
	
	/**
	 * 根据市场查合同提醒信息
	 * @param map
	 * @return SysContractRemindSettingDTO
	 */
	@Override
	public SysContractRemindSettingDTO queryContractRemindSettingByMarket(
			Map<String,Object> map) {
		SysContractRemindSettingDTO scrs=baseDao.queryForObject("SysContractRemindSetting.queryByCondition", map, SysContractRemindSettingDTO.class);
		return scrs;
	}

	
	/**
	 * 查询市场租约管理设置
	 */
	@Override
	public SysContractRemindSettingInteractionDTO queryByMarketId(Integer marketId) throws BizException {
		Map<String,Object> map=new HashMap<>();
		if (marketId==null) {
			return null;
		}
		map.put("marketId", marketId);
		List<SysContractRemindSettingEntity> entityList=baseDao.queryForList("SysContractRemindSetting.queryByMarketId", map,SysContractRemindSettingEntity.class);
		List<SysWorkflowSettingEntity> sysWorkflowSettingEntities=baseDao.queryForList("SysWorkflowSetting.queryByMarketId", map,SysWorkflowSettingEntity.class);
		SysContractRemindSettingInteractionDTO sysContractRemindSettingInteractionDTO=new SysContractRemindSettingInteractionDTO(entityList,sysWorkflowSettingEntities);
		
		return sysContractRemindSettingInteractionDTO;
	}
	/**
	 * 修改租约管理设置
	 */ 
	
	@Override
	@Transactional
	public SysContractRemindSettingInteractionDTO update(SysContractRemindSettingInteractionDTO sysContractRemindSettingInteractionDTO) throws BizException {
			
		
			SysContractRemindSettingEntity sysContractRemindSettingEntity=null;
			sysContractRemindSettingEntity=new SysContractRemindSettingEntity();
			sysContractRemindSettingEntity.setMarketId(sysContractRemindSettingInteractionDTO.getMarketId());
			sysContractRemindSettingEntity.setOrgId(sysContractRemindSettingInteractionDTO.getOrgId());
			sysContractRemindSettingEntity.setCreateTime(new Date());
			sysContractRemindSettingEntity.setCreateUserId(sysContractRemindSettingInteractionDTO.getUpdateUserId().toString());
			sysContractRemindSettingEntity.setUpdateUserId(sysContractRemindSettingInteractionDTO.getUpdateUserId().toString());
			
			//合同到期提醒
			sysContractRemindSettingEntity.setType(1);
			sysContractRemindSettingEntity.setRemindTime(sysContractRemindSettingInteractionDTO.getDeadlineTime());
			sysContractRemindSettingEntity.setRemindTimeType(2);
			updateSysContractRemindSetting(sysContractRemindSettingEntity);
			
			//合同收取租金提醒
			sysContractRemindSettingEntity.setType(2);
			sysContractRemindSettingEntity.setRemindTime(sysContractRemindSettingInteractionDTO.getRentTime());
			sysContractRemindSettingEntity.setRemindTimeType(1);
			updateSysContractRemindSetting(sysContractRemindSettingEntity);
			
			
			SysWorkflowSettingEntity sysWorkflowSettingEntity=null;
			
			sysWorkflowSettingEntity=new SysWorkflowSettingEntity();
			sysWorkflowSettingEntity.setMarketId(sysContractRemindSettingInteractionDTO.getMarketId());
			sysWorkflowSettingEntity.setOrgId(sysContractRemindSettingInteractionDTO.getOrgId());
			sysWorkflowSettingEntity.setCreateTime(new Date());
			sysWorkflowSettingEntity.setCreateUserId(sysContractRemindSettingInteractionDTO.getUpdateUserId().toString());
			sysWorkflowSettingEntity.setUpdateUserId(sysContractRemindSettingInteractionDTO.getUpdateUserId().toString());
			
			//合同审批模式
			sysWorkflowSettingEntity.setType(1);
			sysWorkflowSettingEntity.setWorkType(sysContractRemindSettingInteractionDTO.getCheckWorkType());
			updateSysWorkflowSetting(sysWorkflowSettingEntity);
			
			//合同变更模式
			sysWorkflowSettingEntity.setType(2);
			sysWorkflowSettingEntity.setWorkType(sysContractRemindSettingInteractionDTO.getChangeWorkType());
			updateSysWorkflowSetting(sysWorkflowSettingEntity);
			
			//合同结算审批模式
			sysWorkflowSettingEntity.setType(3);
			sysWorkflowSettingEntity.setWorkType(sysContractRemindSettingInteractionDTO.getSettlementWorkType());
			updateSysWorkflowSetting(sysWorkflowSettingEntity);
		return sysContractRemindSettingInteractionDTO;
	}
	@Transactional
	private void updateSysContractRemindSetting(SysContractRemindSettingEntity sysContractRemindSettingEntity) throws BizException{
		Map<String, Object> map=new HashMap<>();
		map.put("marketId", sysContractRemindSettingEntity.getMarketId());
		map.put("type", sysContractRemindSettingEntity.getType());
		SysContractRemindSettingEntity sysContractRemindSettingEntity2=baseDao.queryForObject("SysContractRemindSetting.queryByMarketId", map,SysContractRemindSettingEntity.class);
		if (sysContractRemindSettingEntity2==null) {
			if(sysContractRemindSettingEntity.getRemindTime()!=null){
				//添加
				Long id=baseDao.persist(sysContractRemindSettingEntity, Long.class);
				//sysContractRemindSettingEntity.setId(id.intValue());
			}	
		}else if (sysContractRemindSettingEntity.getRemindTime().compareTo(sysContractRemindSettingEntity2.getRemindTime())!=0){
			//修改
			baseDao.execute("SysContractRemindSetting.updateByMarketId", sysContractRemindSettingEntity);
		}
	}
	@Transactional
	private void updateSysWorkflowSetting(SysWorkflowSettingEntity sysWorkflowSettingEntity) throws BizException{
		Map<String, Object> map=new HashMap<>();
		map.put("marketId", sysWorkflowSettingEntity.getMarketId());
		map.put("type", sysWorkflowSettingEntity.getType());
		SysWorkflowSettingEntity sysWorkflowSettingEntity2=baseDao.queryForObject("SysWorkflowSetting.queryByMarketId", map,SysWorkflowSettingEntity.class);
		if (sysWorkflowSettingEntity2==null) {
			if(sysWorkflowSettingEntity.getWorkType()!=null){
				//添加
				Long id= baseDao.persist(sysWorkflowSettingEntity, Long.class);
				//sysWorkflowSettingEntity.setId(id.intValue());
			}	
		}else if (sysWorkflowSettingEntity.getWorkType().compareTo(sysWorkflowSettingEntity2.getWorkType())!=0) {
			//修改
			baseDao.execute("SysWorkflowSetting.updateByMarketId", sysWorkflowSettingEntity);
		}
	}
	

	
	
	
}
