package cn.gdeng.market.service.lease.contract.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.entity.lease.contract.ContractTaskEntity;
import cn.gdeng.market.service.lease.contract.ContractTaskService;

/**
 * 合同结算服务层实现
 * @author Administrator
 *
 */
@Service(value="contractTaskService")
public class ContractTaskServiceImpl implements ContractTaskService {
 

	@Autowired
	private BaseDao<?> baseDao;

	/**
	 * 保存定时记录
	 */
	@Override
	public void insertContractTask(ContractTaskEntity entity){
		baseDao.persist(entity, Long.class);
	}


}
