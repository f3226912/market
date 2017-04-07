package cn.gdeng.market.service.workflow.core.impl;

import java.util.Map;

import org.snaker.engine.IOrderService;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Process;

import cn.gdeng.market.service.workflow.core.SnakerOrderService;

public class SnakerOrderServiceImpl implements SnakerOrderService {
	/**
	 * snaker框架的内部服务类接口
	 */
	private IOrderService orderService;
	
	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	public Order createOrder(Process process, String operator, Map<String, Object> args) {
		return orderService.createOrder(process, operator, args);
	}

	@Override
	public Order createOrder(Process process, String operator, Map<String, Object> args, String parentId,
			String parentNodeName) {
		return orderService.createOrder(process, operator, args, parentId, parentNodeName);
	}

	@Override
	public void addVariable(String orderId, Map<String, Object> args) {
		orderService.addVariable(orderId, args);
	}

	@Override
	public void createCCOrder(String orderId, String creator, String... actorIds) {
		orderService.createCCOrder(orderId, creator, actorIds);
	}

	@Override
	public void complete(String orderId) {
		orderService.complete(orderId);
	}

	@Override
	public void saveOrder(Order order) {
		orderService.saveOrder(order);
	}


	@Override
	public void terminate(String orderId, String remark) {
		orderService.terminate(orderId, remark);
	}

	@Override
	public Order resume(String orderId) {
		return orderService.resume(orderId);
	}

	@Override
	public void updateOrder(Order order) {
		orderService.updateOrder(order);
	}

	@Override
	public void updateCCStatus(String orderId, String... actorIds) {
		orderService.updateCCStatus(orderId, actorIds);
	}

	@Override
	public void deleteCCOrder(String orderId, String actorId) {
		orderService.deleteCCOrder(orderId, actorId);
	}

	@Override
	public void cascadeRemove(String id) {
		orderService.cascadeRemove(id);
	}

	@Override
	public void complete(String orderId, String remark) {
		orderService.complete(orderId, remark);
	}

	@Override
	public void terminate(String orderId, String operator, String remark) {
		orderService.terminate(orderId, operator, remark);
	}
	
	
}
