package cn.gdeng.market.service.admin;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.entity.admin.SysUserOrgEntity;
import cn.gdeng.market.exception.BizException;

/**
 * 用户组织关联关系
 * 
 * @author houxp
 *
 */
public interface SysUserOrgService {

	/**
	 * 新增用户组织关系
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int persist(SysUserOrgEntity entity) throws BizException;

	/**
	 * 查询用户个数
	 * 
	 * @param param
	 * @return
	 * @throws BizException
	 */
	List<SysUserOrgEntity> queryListByCondition(Map<String, Object> param) throws BizException;

	/**
	 * 查询个数
	 * 
	 * @return
	 * @throws BizException
	 */
	int getTotal(Map<String, Object> param) throws BizException;

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
	public int deleteByUserId(Integer userId) throws BizException;
}