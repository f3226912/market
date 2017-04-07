package cn.gdeng.market.service.workflow.bus.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.snaker.engine.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.workflow.WfTaskActorDTO;
import cn.gdeng.market.dto.workflow.WfTaskDTO;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.workflow.bus.WfTaskService;
import cn.gdeng.market.util.SysMessageHelper;

/**
 * 流程任务
 * @author xphou
 */
@Service(value = "wfTaskService")
public class WfTaskServiceImpl implements WfTaskService{

	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public List<WfTaskDTO> queryByOrderId(WfTaskDTO dto) throws BizException {
		return baseDao.queryForList("WfTask.queryByOrderId", dto, WfTaskDTO.class);
	}

	/**
	 * 获取待办任务
	 * @param actorId
	 * @return
	 * @throws BizException
	 */
	@Override
	public GuDengPage<WfTaskDTO> getGtasks4Page(GuDengPage<WfTaskDTO> page) throws BizException {
		Map<String,Object> param = page.getParaMap();
		int count = getTotal(param);
		List<WfTaskDTO> list = null;
		if(count > 0 ){
			list = baseDao.queryForList("WfTask.getGtasks4Page", param, WfTaskDTO.class);
			for (int i = 0; i < list.size(); i++) {
				WfTaskDTO dto = list.get(i);
				list.get(i).setOrderNo("请对"+dto.getOrderNo()+"进行"+getPerformTypeName(dto.getPerformType()));
				list.get(i).setForwardUrl(SysMessageHelper.getWfRouteUrl(dto.busType));
				
			}
		}
		page.setRecordList(list).setTotal(count);
		return page;
	}
	
	/**
	 * 获取待办任务-条数
	 * @param actorId
	 * @return
	 * @throws BizException
	 */
	@Override
	public int getTotal(Map<String, Object> map) throws BizException {
		return baseDao.queryForObject("WfTask.getTotal", map, Integer.class);
	}

	/** 获取任务参与类型的名称
	 * @param performType 任务参与类型
	 * @return 如果没有对应的名称，返回空字符串。
	 */
	public String getPerformTypeName(int performType) {
		String result = "";
		if (TaskModel.PerformType.ANY.ordinal() == performType) {
			result = "审批";
		} else if (TaskModel.PerformType.ANY.ordinal() == performType) {
			result = "会签";
		}
		return result;
	}
}