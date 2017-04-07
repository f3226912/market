package cn.gdeng.market.service.admin;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.admin.SysPostDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.entity.admin.SysUserEntity;
import cn.gdeng.market.exception.BizException;

/**
 * 对系统用户表（sys_user）操作
 * 
 * @author houxiaoping
 */
public interface SysUserService {
	/**
	 * 新增用户（包括平台用户 ，公司管理员， 普通用户）
	 * 
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	public int addSysUser(SysUserDTO dto) throws BizException;

	public int persist(SysUserEntity entity) throws Exception;

	/**
	 * 根据ID删除
	 * 
	 * @param id
	 * @return 返回条数
	 * @throws BizException
	 */
	public int deleteById(String id) throws Exception;

	/**
	 * 根据ID修改
	 * 
	 * @param id
	 * @return 返回条数
	 * @throws BizException
	 */
	public int dynamicMerge(SysUserEntity entity) throws BizException;

	/**
	 * 根据ID查询
	 * 
	 * @param id
	 * @return 返回条数
	 * @throws BizException
	 */
	public SysUserDTO getById(int id) throws BizException;

	/**
	 * 获取用户的详细信息。包含公司，部门，市场，岗位信息
	 * 
	 * @param Map
	 * @return 返回分页信息
	 * @throws BizException
	 */
	public GuDengPage<SysUserDTO> getDetailUserListPage(GuDengPage<SysUserDTO> map) throws BizException;

	/**
	 * 查询记录条数
	 * 
	 * @param map
	 * @return 返回条数
	 * @throws BizException
	 */
	public int getTotal(Map<String, Object> map) throws BizException;

	/**
	 * 根据用户组织获取所属公司信息/所属市场
	 * 
	 * @param pId
	 * @param orgType
	 *            1：公司，3：市场
	 * @return
	 * @throws BizException
	 */
	public SysOrgDTO getParOrgByUserId(int pId, String orgType) throws BizException;

	/**
	 * 分页获取源始的用户信息
	 * 
	 * @param map
	 * @return
	 * @throws BizException
	 */
	public List<SysUserDTO> getSourceUserListByPage(Map<String, Object> map) throws BizException;

	/**
	 * 根据用户Id获取用户组织信息
	 * 
	 * @param userId
	 * @param orgType
	 * @return
	 * @throws BizException
	 */
	public SysOrgDTO getUserOrgInfo(Integer userId, String orgType) throws BizException;

	/**
	 * 根据用户ID获取岗位信息
	 * 
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	public List<SysPostDTO> getPostInfoByUserId(Integer userId) throws BizException;

	public int getListTotal(Map<String, Object> map) throws BizException;

	public int getSourceTotal(Map<String, Object> map) throws BizException;

	/**
	 * 根据id获取用户信息实体对象。
	 * 
	 * @param id
	 * @return 用户基本信息。
	 * @throws BizException
	 */
	public SysUserEntity getEntityById(Integer id) throws BizException;

	/**
	 * 根据条件获取用户列表
	 * 
	 * @param map
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月23日 下午5:25:44
	 */
	public List<SysUserDTO> getList(Map<String, Object> map) throws BizException;

	public SysUserEntity findById(Integer id) throws BizException;

	/**
	 * 根据部门获取当前部门的用户
	 * 
	 * @return
	 * @throws BizException
	 */
	public List<SysUserEntity> getUserListByOrg(Integer orgId) throws BizException;

	/**
	 * 获取集团管理员
	 * 
	 * @param map
	 * @return
	 * @throws BizException
	 */
	public List<SysUserDTO> getAdmin(Map<String, Object> map) throws BizException;

	/**
	 * 用户管理，用户列表查询
	 * 
	 * @param pageInfo
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年11月3日 上午11:04:55
	 */
	public GuDengPage<SysUserDTO> getUserListPage(GuDengPage<SysUserDTO> pageInfo) throws BizException;
}
