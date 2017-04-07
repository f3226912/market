package cn.gdeng.market.service.workflow.core.impl;

import java.io.InputStream;
import java.util.List;

import org.snaker.engine.IProcessService;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.Process;

import cn.gdeng.market.service.workflow.core.SnakerProcessService;

public class SnakerProcessServiceImpl implements SnakerProcessService {
	/**
	 * snaker框架的内部服务类接口
	 */
	private IProcessService processService;

	public void setProcessService(IProcessService processService) {
		this.processService = processService;
	}

	@Override
	public void check(Process process, String idOrName) {
		processService.check(process, idOrName);
	}

	@Override
	public void saveProcess(Process process) {
		processService.saveProcess(process);
	}

	@Override
	public void updateType(String id, String type) {
		processService.updateType(id, type);
	}

	@Override
	public Process getProcessById(String id) {
		return processService.getProcessById(id);
	}

	@Override
	public Process getProcessByName(String name) {
		return processService.getProcessByName(name);
	}

	@Override
	public Process getProcessByVersion(String name, Integer version) {
		return processService.getProcessByVersion(name, version);
	}

	@Override
	public List<Process> getProcesss(QueryFilter filter) {
		return processService.getProcesss(filter);
	}

	@Override
	public List<Process> getProcesss(Page<Process> page, QueryFilter filter) {
		return processService.getProcesss(page, filter);
	}

	@Override
	public String deploy(InputStream input) {
		return processService.deploy(input);
	}

	@Override
	public String deployForFile(String xml) {
		return processService.deployForFile(xml);
	}

	@Override
	public String deploy(InputStream input, String creator) {
		return processService.deploy(input, creator);
	}

	@Override
	public void redeploy(String id, InputStream input) {
		processService.redeploy(id, input);
	}

	@Override
	public void redeployForFile(String id, String xml) {
		processService.redeployForFile(id, xml);
	}

	@Override
	public void undeploy(String id) {
		processService.undeploy(id);
	}

	@Override
	public void cascadeRemove(String id) {
		processService.cascadeRemove(id);
	}

	@Override
	public void updateType(String id, String type, String modificator) {
		processService.updateType(id, type, modificator);
	}

	@Override
	public void redeploy(String id, InputStream input, String modificator) {
		processService.redeploy(id, input, modificator);
	}

	@Override
	public void redeployForFile(String id, String xml, String modificator) {
		processService.redeployForFile(id, xml, modificator);
	}

	@Override
	public void undeploy(String id, String modificator) {
		processService.undeploy(id, modificator);
	}

	@Override
	public String saveProcessTemplate(Process entity) {
		return processService.saveProcessTemplate(entity);
	}

	@Override
	public void updateProcessTemplate(Process entity) {
		processService.updateProcessTemplate(entity);
	}
	
}
