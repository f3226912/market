package cn.gdeng.market.service.workflow.core.impl;

import java.util.List;

import org.snaker.engine.IManagerService;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.Surrogate;

import cn.gdeng.market.service.workflow.core.SnakerManagerService;

public class SnakerManagerServiceImpl implements SnakerManagerService {
	/**
	 * snaker框架的内部服务类接口
	 */
	private IManagerService managerService;
	
	public void setManagerService(IManagerService managerService) {
		this.managerService = managerService;
	}

	@Override
	public void saveOrUpdate(Surrogate surrogate) {
		managerService.saveOrUpdate(surrogate);
	}

	@Override
	public void deleteSurrogate(String id) {
		managerService.deleteSurrogate(id);
	}

	@Override
	public Surrogate getSurrogate(String id) {
		return managerService.getSurrogate(id);
	}

	@Override
	public List<Surrogate> getSurrogate(QueryFilter filter) {
		return managerService.getSurrogate(filter);
	}

	@Override
	public String getSurrogate(String operator, String processName) {
		return managerService.getSurrogate(operator, processName);
	}

	@Override
	public List<Surrogate> getSurrogate(Page<Surrogate> page, QueryFilter filter) {
		return managerService.getSurrogate(page, filter);
	}

}
