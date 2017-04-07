package cn.gdeng.market.service.admin;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.admin.SysPostDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.entity.admin.SysPostDataEntity;
import cn.gdeng.market.entity.admin.SysPostEntity;
import cn.gdeng.market.entity.admin.SysPostFunctionEntity;
import cn.gdeng.market.exception.BizException;

/**
 * 岗位管理
 * 
 * @author lidong dli@gdeng.cn
 * @time 2016年10月12日 上午10:37:11
 */
public interface SysPostService {

	public Long persist(SysPostEntity entity) throws BizException;

	/**
	 * 根据条件查询list(不分页查询)
	 * 
	 * @param map
	 * @return List<DTO>
	 */
	public List<SysPostDTO> getList(Map<String, Object> map) throws BizException;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public GuDengPage<SysPostDTO> getListPage(GuDengPage<SysPostDTO> page) throws BizException;

	public int getTotal(Map<String, Object> map) throws BizException;

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 * @return 影响条数
	 * 
	 */
	public int deleteById(SysPostEntity entity) throws BizException;

	/**
	 * 检查岗位下是否有用户
	 * 
	 * @param map
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月12日 下午4:16:25
	 */
	public int checkPost(Map<String, Object> map) throws BizException;

	/**
	 * 修改
	 * 
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public int update(SysPostDTO t) throws BizException;

	/**
	 * 修改
	 * 
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public int update(SysPostEntity t) throws BizException;

	/**
	 * 获取岗位下用户列表信息
	 * 
	 * @param page
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月12日 下午4:05:16
	 */
	public GuDengPage<SysUserDTO> getPostUserListPage(GuDengPage<SysUserDTO> page) throws BizException;

	/**
	 * 获取岗位
	 * 
	 * @param param
	 * @return
	 * @throws BizException
	 */
	List<SysPostEntity> getSourcePostListByPage(Map<String, Object> param) throws BizException;


	/**
	 * 新增岗位、数据权限、功能权限
	 * 
	 * @param entity
	 * @param dto
	 *            用于获取数据权限和功能权限
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月17日 下午1:51:00
	 */
	public Long addPost(SysPostEntity entity, SysPostDTO dto) throws BizException;

	/**
	 * 修改岗位、数据权限、功能权限
	 * 
	 * @param entity
	 * @param dto
	 *            用于获取数据权限和功能权限
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月17日 下午4:53:08
	 */
	public Long updatePost(SysPostEntity entity, SysPostDTO dto) throws BizException;

	/**
	 * 根据岗位获取功能权限集合
	 * 
	 * @param map
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月17日 下午4:56:05
	 */
	public List<SysPostFunctionEntity> getFuncsByPost(Map<String, Object> map) throws BizException;

	/**
	 * 根据岗位获取数据权限集合
	 * 
	 * @param map
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月17日 下午4:56:42
	 */
	public List<SysPostDataEntity> getDatasByPost(Map<String, Object> map) throws BizException;
	
	/**根据id获取实体
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public SysPostEntity getEntityById(Integer id) throws BizException;
	
	/**根据id查询有效的岗位
	 * @param ids
	 * @return
	 * @throws BizException
	 */
	public List<SysPostDTO> queryValidByIds(List<Integer> ids) throws BizException;

}