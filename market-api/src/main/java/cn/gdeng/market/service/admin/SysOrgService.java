package cn.gdeng.market.service.admin;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.bean.Node;
import cn.gdeng.market.entity.admin.SysOrgEntity;
import cn.gdeng.market.enums.SysOrgTreeEnum;
import cn.gdeng.market.exception.BizException;

/**
 * 
 * 组织架构服务
 * @author lidong
 *
 */
public interface SysOrgService {

	/**
	 * 新增组织架构
	 * @return
	 * @throws BizException
	 */
	int addSysOrg(SysOrgDTO dto) throws BizException;
	
	/**
	 * 删除组织，如果存在下级组织不能删除
	 * @throws BizException
	 */
	void deleteSysOrg(SysOrgEntity entity) throws BizException;
	
	
	/**
	 * 修改组织名称
	 * @throws BizException
	 */
	void updateSysOrg(SysOrgDTO entity) throws BizException;
	
	/**
	 * 根据组织ID查询级织
	 * @param orgId
	 * @return
	 * @throws BizException
	 */
	SysOrgDTO querySysOrg(SysOrgEntity entity) throws BizException;
	
	/**
	 * 根据组织ID查询级织
	 * @param orgId
	 * @return
	 * @throws BizException
	 */
	SysOrgEntity querySysOrgById(int id) throws BizException;
	
	/**
	 * 查询顶级公司个数
	 * @throws BizException
	 */
	int queryTopOrgCount(Map<String,Object> param) throws BizException;
	
	/**
	 * 分页查询顶级公司
	 * @throws BizException
	 */
	GuDengPage<SysOrgDTO> queryTopOrgByPage(GuDengPage<SysOrgDTO> page) throws BizException;
	
	/**
	 * 查询个数
	 * @param param
	 * @return
	 * @throws BizException
	 */
	int queryCountByCondition(Map<String,Object> param) throws BizException;
	
	/**
	 * 根据组织查询下级组织
	 * @param dto
	 * @return
	 * @throws BizException
	 */
	List<Node> queryChildrenById(String id) throws BizException;
	
	/**
	 * 初始化组织树
	 * @return
	 * @throws BizException
	 */
	List<Node> initOrgTree(String companyId,SysOrgTreeEnum level) throws BizException;
	
	/**
	 * 根据组织查询用户
	 * @param orgId
	 * @return
	 * @throws BizException
	 */
	GuDengPage<SysUserDTO> queryUserPageByOrgId(GuDengPage<SysUserDTO> page,int orgId) throws BizException;
	
	/**
	 * 获取所有的组织
	 * @param param
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月14日 上午11:22:23
	 */
	public List<SysOrgDTO> queryByCondition(Map<String,Object> param) throws BizException;
}