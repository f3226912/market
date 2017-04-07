package cn.gdeng.market.service.workflow.bus.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.workflow.WfProcessDTO;
import cn.gdeng.market.entity.admin.SysOrgEntity;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.WorkflowBusTypeEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.admin.SysOrgService;
import cn.gdeng.market.service.workflow.bus.WfProcessService;

/**
 * 流程定义管理
 * @author xphou
 */
@Service(value = "wfProcessService")
public class WfProcessServiceImpl implements WfProcessService{

	@Autowired
	private BaseDao<?> baseDao;

	@Autowired
	private SysOrgService sysOrgService;
	/**
	 * 查询分页条数
	 * @param param
	 * @return
	 * @throws BizException
	 */
	@Override
	public int countByCondition(Map<String, Object> param) throws BizException {
		
		return baseDao.queryForObject("WfProcess.countByCondition", param, Integer.class);
	}

	/**
	 * 查询分页
	 * @param page
	 * @return
	 * @throws BizException
	 */
	@Override
	public GuDengPage<WfProcessDTO> queryByConditionPage(GuDengPage<WfProcessDTO> page) throws BizException {
		Map<String,Object> param = page.getParaMap();
		int total = countByCondition(param);
		List<WfProcessDTO>list = null;
		if(total > 0 ){
			list = baseDao.queryForList("WfProcess.queryByConditionPage", param, WfProcessDTO.class);
			for(int i=0; i < list.size(); i++){
				WfProcessDTO dto = list.get(i);
				if(dto.getState().equals(Const.WF_PROCESS_STATE_0)){//流程模板不可用
					list.get(i).setStateDesc(Const.WF_PROCESS_STATE_DESC_0);
					
				} else if(dto.getState().equals(Const.WF_PROCESS_STATE_1)){//流程模板可用
					list.get(i).setStateDesc(Const.WF_PROCESS_STATE_DESC_1);
					
				} else if(dto.getState().equals(Const.WF_PROCESS_STATE_2)){//流程模板已删除
					list.get(i).setStateDesc(Const.WF_PROCESS_STATE_DESC_2);
				}
				
				if(dto.getBusType().equals(WorkflowBusTypeEnum.CONTRACT_MANAGER.getCode())){//流程模板业务类型：合同首次审批 
					list.get(i).setBusTypeDesc(WorkflowBusTypeEnum.CONTRACT_MANAGER.getDesc());
					
				} else if(dto.getBusType().equals(WorkflowBusTypeEnum.CONTRACT_CHANGED.getCode())){//流程模板业务类型：合同变更 
					list.get(i).setBusTypeDesc(WorkflowBusTypeEnum.CONTRACT_CHANGED.getDesc());
					
				} else if(dto.getBusType().equals(WorkflowBusTypeEnum.CONTRACT_CLOSE.getCode())){//流程模板业务类型：合同结算
					list.get(i).setBusTypeDesc(WorkflowBusTypeEnum.CONTRACT_CLOSE.getDesc());
				}
				//模板所属市场
				SysOrgEntity orgDto = sysOrgService.querySysOrgById(Integer.valueOf(dto.getOrgId()));
				if(orgDto != null){
					list.get(i).setOrgDesc(orgDto.getFullName());
				}
			}
		}
		page.setRecordList(list).setTotal(total);
		return page;
	}
	
	/**
	 * 查询分页条数
	 * @param param
	 * @return
	 * @throws BizException
	 */
	@Override
	public WfProcessDTO getById(String id) throws BizException {
		Map<String,Object> map = new HashMap<>();
		map.put("id", id);
		return baseDao.queryForObject("WfProcess.getById", map, WfProcessDTO.class);
	}

	/**
	 * 查询当前集团下所有流程模板
	 */
	@Override
	public List<WfProcessDTO> getCurGroupProcess(Map<String,Object> map) throws BizException {
		
		return baseDao.queryForList("WfProcess.getCurGroupProcess", map, WfProcessDTO.class);
	}

	/**
	 * 是否唯一的流程名称 
	 */
	@Override
	public int isUniqueForProcessDisplayName(Map<String, Object> map) throws BizException {
		return baseDao.queryForObject("WfProcess.isUniqueForProcessDisplayName", map, Integer.class);
	}
}