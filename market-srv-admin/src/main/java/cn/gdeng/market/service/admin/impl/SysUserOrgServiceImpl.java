package cn.gdeng.market.service.admin.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.entity.admin.SysUserOrgEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.admin.SysUserOrgService;

/**
 * 用户组织关联关系
 * 
 * @author houxp
 *
 */
@Service(value = "sysUserOrgService")
public class SysUserOrgServiceImpl implements SysUserOrgService {

	@Autowired
	private BaseDao<?> baseDao;

	/**
	 * 新增用户组织关系
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@Override
	public int persist(SysUserOrgEntity entity) throws BizException {
		long id = baseDao.persist(entity, Long.class);
		return (int) id;
	}

	@Override
	public List<SysUserOrgEntity> queryListByCondition(Map<String, Object> param) throws BizException {
		return baseDao.queryForList("SysUserOrg.queryByCondition", param, SysUserOrgEntity.class);
	}

	@Override
	public int getTotal(Map<String, Object> param) throws BizException {
		return baseDao.queryForObject("SysUserOrg.countByCondition", param, Integer.class);
	}

	/**
	 * 根据用户id删除用户-组织关系
	 * 
	 * @param userId
	 *            关联组织的用户
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月20日 下午3:04:07
	 */
	@Override
	public int deleteByUserId(Integer userId) throws BizException {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		return baseDao.execute("SysUserOrg.deleteByUserId", map);
	}

}