package cn.gdeng.market.service.admin;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.admin.SysUserPostDTO;
import cn.gdeng.market.entity.admin.SysUserPostEntity;
import cn.gdeng.market.exception.BizException;

/**
 * 
 * 
 * @author lidong
 *
 */
public interface SysUserPostService {
	/**
	 * 根据岗位id查询
	 * 
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public List<SysUserPostDTO> queryByPostId(Integer id) throws BizException;

	/**
	 * 新增用户岗位关系
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int persist(SysUserPostEntity entity) throws Exception;

	/**
	 * 查询用户岗位信息
	 * 
	 * @param params
	 * @return
	 * @throws BizException
	 */
	List<SysUserPostDTO> getList(Map<String, Object> params) throws BizException;

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
	public int deleteByUserId(Integer userId) throws BizException;
	/**
	 * 根据岗位id查询有效的用户岗位映射DTO。<br/>
	 * ps：所谓有效指的是要求岗位和用户的状态都必须是正常状态。
	 * 
	 * @param postId
	 * @return
	 * @throws BizException
	 */
	public List<SysUserPostDTO> queryValidByPostId(Integer postId) throws BizException;
}