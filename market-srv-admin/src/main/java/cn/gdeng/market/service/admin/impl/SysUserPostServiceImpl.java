package cn.gdeng.market.service.admin.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.admin.SysUserPostDTO;
import cn.gdeng.market.entity.admin.SysUserPostEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.admin.SysUserPostService;

/**
 * 
 * 
 * @author lidong
 *
 */
@Service(value = "sysUserPostService")
public class SysUserPostServiceImpl implements SysUserPostService {

	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public List<SysUserPostDTO> queryByPostId(Integer id) throws BizException {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("postId", id);
		return baseDao.queryForList("SysUserPost.queryByCondition", params, SysUserPostDTO.class);
	}

	/**
	 * 新增用户岗位关系
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@Override
	public int persist(SysUserPostEntity entity) throws Exception {
		long id = baseDao.persist(entity, Long.class);
		return (int) id;
	}

	@Override
	public List<SysUserPostDTO> getList(Map<String, Object> params) throws BizException {

		return baseDao.queryForList("SysUserPost.queryByCondition", params, SysUserPostDTO.class);
	}

	/**
	 * 根据用户id删除用户-岗位关系
	 * 
	 * @param userId
	 *            关联岗位的用户
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月20日 下午3:04:07
	 */
	@Override
	public int deleteByUserId(Integer userId) throws BizException {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		return baseDao.execute("SysUserPost.deleteByUserId", map);
	}

	@Override
	public List<SysUserPostDTO> queryValidByPostId(Integer postId) throws BizException {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("postId", postId);
		return baseDao.queryForList("SysUserPost.queryValidByPostId", params, SysUserPostDTO.class);
	
	}
}