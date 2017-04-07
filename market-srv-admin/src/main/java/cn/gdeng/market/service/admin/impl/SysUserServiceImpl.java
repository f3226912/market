package cn.gdeng.market.service.admin.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.framework.dba.transaction.annotation.Transactional;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.admin.SysPostDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.admin.SysUserPostDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.entity.admin.SysOrgEntity;
import cn.gdeng.market.entity.admin.SysPostEntity;
import cn.gdeng.market.entity.admin.SysUserEntity;
import cn.gdeng.market.entity.admin.SysUserOrgEntity;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.enums.SysOrgEnum;
import cn.gdeng.market.enums.SysUserEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.admin.SysDictionaryService;
import cn.gdeng.market.service.admin.SysOrgService;
import cn.gdeng.market.service.admin.SysPostService;
import cn.gdeng.market.service.admin.SysUserOrgService;
import cn.gdeng.market.service.admin.SysUserPostService;
import cn.gdeng.market.service.admin.SysUserService;
import cn.gdeng.market.util.Assert;
import cn.gdeng.market.util.MD5;
import cn.gdeng.market.util.OrgUserUtil;

/**
 * 对系统用户表（sys_user）操作
 * 
 * @author houxiaoping
 */
@Service(value = "sysUserService")
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private BaseDao<SysUserEntity> baseDao;

	@Autowired
	SysPostService sysPostService;

	@Resource
	private SysUserPostService sysUserPostService;

	@Resource
	private SysUserOrgService sysUserOrgService;

	@Resource
	private SysOrgService sysOrgService;

	@Resource
	private SysDictionaryService sysDictionaryService;

	/**
	 * 新增用户（包括平台用户 ，公司管理员， 普通用户）
	 * 
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	@Override
	@Transactional
	public int addSysUser(SysUserDTO dto) throws BizException {
		// 用户code唯一
		Map<String, Object> params = new HashMap<>();
		params.put("code", dto.getCode());
		int total = getSourceTotal(params);
		if (total > 0) {
			throw new BizException(MsgCons.C_20000, "帐号[" + dto.getCode() + "]已存在");
		}
		// 先保存用户
		String md5Key = sysDictionaryService.getEnableDictionary(Const.DICTIONARY_ENCODE, Const.ENCRYPTION_TYPE_MD5).getValue();
		dto.setPwd(MD5.sign(Const.RESET_PASSWROD_VALUE, md5Key, Const.CHARSET_UTF8));
		SysUserEntity entity = new SysUserEntity();
		BeanUtils.copyProperties(dto, entity);
		long userId = baseDao.persist(entity, Long.class);
		// 保存用户与组织的关系
		if (SysUserEnum.TYPE.MANAGER.equals(dto.getType())) {
			// 集团用户

		} else if (SysUserEnum.TYPE.PLATFORM.equals(dto.getType())) {

		}
		return (int) userId;
	}

	@Override
	public int persist(SysUserEntity entity) throws BizException {
		long id = baseDao.persist(entity, Long.class);
		return (int) id;
	}

	/**
	 * 根据ID删除
	 * 
	 * @param id
	 * @return 返回条数
	 * @throws BizException
	 */
	@Override
	public int deleteById(String id) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("SysUser.deleteById", map);
	}

	/**
	 * 根据ID修改
	 * 
	 * @param id
	 * @return 返回条数
	 * @throws BizException
	 */
	@Override
	public int dynamicMerge(SysUserEntity entity) throws BizException {
		return baseDao.dynamicMerge(entity);
	}

	/**
	 * 根据ID查询
	 * 
	 * @param id
	 * @return 返回条数
	 * @throws BizException
	 */
	@Override
	public SysUserDTO getById(int id) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		SysUserDTO dto = baseDao.queryForObject("SysUser.getById", map, SysUserDTO.class);
		// 获取用户所属公司
		SysOrgDTO comDto = getUserOrgInfo(dto.getId(), Const.ORG_TYPE_COMPANY);
		if (comDto != null) {
			dto.setCompanyId(comDto.getId());
			dto.setCompanyName(comDto.getFullName());
		}
		// 获取用户所属市场
		SysOrgDTO marDto = getUserOrgInfo(dto.getId(), Const.ORG_TYPE_MARKET);
		if (marDto != null) {
			dto.setMarketId(marDto.getId());
			dto.setMarketName(marDto.getFullName());
		}
		// 获取用户所属部门
		SysOrgDTO depDto = getUserOrgInfo(dto.getId(), Const.ORG_TYPE_DEPT);
		if (depDto != null) {
			dto.setDepartmentId(depDto.getId());
			dto.setDepartmentName(depDto.getFullName());
		}
		// 获取岗位信息
		List<SysPostDTO> postList = getPostInfoByUserId(dto.getId());
		if (postList.size() > 0) {
			String postId = "";
			String postDesc = "";
			for (SysPostDTO reDto : postList) {
				postId += reDto.getId() + ",";
				postDesc += reDto.getName() + ",";
			}
			dto.setPostId(postId.substring(0, postId.length() - 1));
			dto.setPostName(postDesc.substring(0, postDesc.length() - 1));
		}

		// 如果用户是禁用状态则前端显示启用动作,反之
		String status = dto.getStatus();
		if (status.equals(Const.USER_STATUS_1)) {// 用户正常，显示启用
			dto.setStatusDesc(Const.USER_STATUS_DESC_1);
		} else {
			if (status.equals(Const.USER_STATUS_3)) {// 用户禁用，显示禁用
				dto.setStatusDesc(Const.USER_STATUS_DESC_3);
			}
		}

		// 帐号类型
		String type = dto.getType();
		if (type.equals(Const.USER_TYPE_1)) {
			dto.setTypeDesc(Const.USER_TYPE_DESC_1);
		} else if (type.equals(Const.USER_TYPE_2)) {
			dto.setTypeDesc(Const.USER_TYPE_DESC_2);
		} else if (type.equals(Const.USER_TYPE_3)) {
			dto.setTypeDesc(Const.USER_TYPE_DESC_3);
		}
		return dto;
	}

	/**
	 * 根据条件查询-分页
	 * 
	 * @param Map
	 * @return 返回分页信息
	 * @throws BizException
	 */
	@Override
	public GuDengPage<SysUserDTO> getDetailUserListPage(GuDengPage<SysUserDTO> page) throws BizException {
		Map<String, Object> param = page.getParaMap();

		String postName = (String) param.get("postName");
		Set<Integer> userIds = new HashSet<>();
		Map<String, Object> params = new HashMap<>();
		// 先处理岗位 岗位不支付模糊查询,根据岗位获取userId 的集合，再统一走sys_user 表进行分页
		if (postName != null && !"".equals(postName)) {

			List<Integer> pUserIds = handlePostName4Page(postName);
			if (pUserIds.size() == 0) {
				page.setTotal(0);
				page.setRecordList(null);
				return page;
			} else {
				userIds.addAll(pUserIds);
			}
		}
		// 处理所属公司
		String companyName = (String) param.get("companyName");
		if (StringUtils.isNotEmpty(companyName)) {
			List<Integer> pUserIds = handleCompanyName4Page(companyName);
			if (pUserIds.size() == 0) {
				page.setTotal(0);
				page.setRecordList(null);
				return page;
			} else {
				userIds.addAll(pUserIds);
			}
		}

		// 处理当前组织以及下级组织的问题
		Integer subOrgId = (Integer) param.get("subOrgId");
		if (null != subOrgId) {
			Set<Integer> userIds3 = handleSubDepartmentOrgId4Page(subOrgId);
			if (userIds3.size() == 0) {
				page.setTotal(0);
				page.setRecordList(null);
				return page;
			} else {
				userIds.addAll(userIds3);
			}
		}

		// 处理用户
		if (userIds.size() > 0) {
			param.put("userIds", userIds);
		}

		// 获取总页数
		List<SysUserDTO> list = null;
		int total = getSourceTotal(param);
		if (total > 0) {
			List<SysUserDTO> users = getSourceUserListByPage(param);
			userIds.clear();
			for (SysUserDTO dto : users) {
				userIds.add(dto.getId());
			}
			params.clear();
			params.put("userIds", userIds);
			list = baseDao.queryForList("SysUser.getUserDetailList", params, SysUserDTO.class);
		}
		page.setTotal(total);
		page.setRecordList(list);
		return page;
	}

	/**
	 * 查询记录条数
	 * 
	 * @param map
	 * @return 返回条数
	 * @throws BizException
	 */
	@Override
	public int getTotal(Map<String, Object> map) throws BizException {
		return baseDao.queryForObject("SysUser.getTotal", map, Integer.class);
	}

	/**
	 * 根据用户组织信息获取所属公司信息/所属市场
	 * 
	 * @param pId
	 * @param orgType
	 *            1：公司，3：市场
	 * @return
	 * @throws BizException
	 */
	public SysOrgDTO getParOrgByUserId(int pId, String orgType) throws BizException {
		SysOrgDTO orgDto = null;
		Map<String, Object> map = new HashMap<>();
		map.put("parentId", pId);
		// 获取上级组织
		orgDto = baseDao.queryForObject("SysOrg.getParOrgByUserId", map, SysOrgDTO.class);
		System.out.println(orgDto == null ? null : orgDto.getType());

		if (orgDto != null) {
			// 如果查找到的组织为目标组织，则直接返回，否则，继续查找父级
			if (orgDto.getType().equals(orgType)) {
				return orgDto;
			} else {
				if (orgDto.getParentId() != null) {
					return getParOrgByUserId(orgDto.getParentId(), orgType);
				} else {
					return null;
				}
			}
		}
		return null;

		/**
		 * 
		 * 
		 * //获取所属公司信息 if(orgType.equals(Const.ORG_TYPE_COMPANY)){ //如果不是公司架构类型继续往上级找 if(!orgDto.getType().equals(Const.ORG_TYPE_COMPANY)){ getParOrgByUserId(orgDto.getParentId(),
		 * orgType); }
		 * 
		 * //return orgDto; }else if(orgType.equals(Const.ORG_TYPE_MARKET)){//获取所属市场信息 //如果不是市场架构类型继续往上级找 if(!orgDto.getType().equals(Const.ORG_TYPE_MARKET)){
		 * getParOrgByUserId(orgDto.getParentId(), orgType); } //return orgDto; }else if(orgDto == null){ throw new BizException(MsgCons.C_30000, MsgCons.M_30000); } return orgDto;
		 */
	}

	@Override
	public List<SysUserDTO> getSourceUserListByPage(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("SysUser.getSourceUserListByPage", map, SysUserDTO.class);
	}

	/**
	 * 根据用户Id获取用户组织信息
	 * 
	 * @param userId
	 * @param orgType
	 * @return
	 * @throws BizException
	 */
	@Override
	public SysOrgDTO getUserOrgInfo(Integer userId, String orgType) throws BizException {
		if (userId == null || orgType == null) {
			throw new BizException(MsgCons.C_30000, MsgCons.M_30000);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("id", userId);
		map.put("type", orgType);
		return baseDao.queryForObject("SysUser.getUserOrgInfo", map, SysOrgDTO.class);
	}

	/**
	 * 根据用户ID获取岗位信息
	 */
	@Override
	public List<SysPostDTO> getPostInfoByUserId(Integer userId) throws BizException {
		Map<String, Object> map = new HashMap<>();
		map.put("id", userId);
		return baseDao.queryForList("SysUser.getPostInfoByUserId", map, SysPostDTO.class);
	}

	@Override
	public int getListTotal(Map<String, Object> map) throws BizException {
		return baseDao.queryForObject("SysUser.getListTotal", map, Integer.class);
	}

	@Override
	public int getSourceTotal(Map<String, Object> map) throws BizException {
		return baseDao.queryForObject("SysUser.getSourceTotal", map, Integer.class);
	}

	private List<Integer> handlePostName4Page(String postName) throws BizException {
		List<Integer> userIds = new ArrayList<>();
		Map<String, Object> params = new HashMap<>();
		params.put("name", postName);
		List<SysPostEntity> postList = sysPostService.getSourcePostListByPage(params);
		// 根据岗位关联用户
		if (postList != null && postList.size() > 0) {
			List<Integer> postIds = new ArrayList<>();
			for (SysPostEntity sysPost : postList) {
				postIds.add(sysPost.getId());
			}
			params.clear();
			params.put("postIds", postIds);
			List<SysUserPostDTO> userPostList = sysUserPostService.getList(params);
			if (userPostList != null && userPostList.size() > 0) {
				for (SysUserPostDTO userPost : userPostList) {
					userIds.add(userPost.getUserId());
				}
			}
		}
		return userIds;

	}

	public List<Integer> handleCompanyName4Page(String companyName) throws BizException {

		List<Integer> userIds = new ArrayList<>();
		Map<String, Object> params = new HashMap<>();

		params.put("likeFullName", companyName);
		List<SysOrgDTO> sysOrgs = sysOrgService.queryByCondition(params);
		if (null != sysOrgs && sysOrgs.size() > 0) {
			List<Integer> orgIds = new ArrayList<>();
			for (SysOrgDTO sysOrgDto : sysOrgs) {
				orgIds.add(sysOrgDto.getId());
			}
			// 查用户列表
			params.clear();
			params.put("orgIds", orgIds);
			List<SysUserOrgEntity> userOrgs = sysUserOrgService.queryListByCondition(params);
			if (null != userOrgs && userOrgs.size() > 0) {
				for (SysUserOrgEntity userOrg : userOrgs) {
					userIds.add(userOrg.getUserId());
				}
			}
		}
		return userIds;

	}

	public Set<Integer> handleSubDepartmentOrgId4Page(int subOrgId) throws BizException {
		// 1 先查询该组织对应的集团下的所有组织
		SysOrgEntity org = sysOrgService.querySysOrgById(subOrgId);
		Assert.notNull(org, "组织[" + subOrgId + "]不存在");
		Assert.notNull(org.getTopId(), "数据异常，该组织[" + subOrgId + "]对应的顶级公司为空");
		int topId = org.getTopId();
		Set<Integer> userIds3 = new HashSet<>();
		Map<String, Object> params2 = new HashMap<>();
		params2.put("topId", topId);
		params2.put("status", SysOrgEnum.STATUS.NORMAL);
		List<SysOrgEntity> allOrg = baseDao.queryForList("SysOrg.queryByCondition", params2, SysOrgEntity.class);
		if (null != allOrg && allOrg.size() > 0) {
			// 过滤，查找所有祖辈组织为orgId的部门,因为只有部门下才有人员,或者=orgId的部门
			Set<Integer> orgIds = OrgUserUtil.getDepartmentSubOrgIds(allOrg, subOrgId);
			// 根据组织ID，获取所有用户ID
			params2.clear();
			if (orgIds.size() > 0) {
				params2.put("orgIds", orgIds);
				List<SysUserOrgEntity> userOrgList = sysUserOrgService.queryListByCondition(params2);
				// 转成userId 的列表
				for (SysUserOrgEntity userOrg : userOrgList) {
					userIds3.add(userOrg.getUserId());
				}
			}

		}
		return userIds3;
	}

	@Override
	public SysUserEntity getEntityById(Integer id) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("id", id);
		return baseDao.queryForObject("SysUser.getById", map, SysUserEntity.class);
	}

	/**
	 * 根据条件获取用户列表
	 * 
	 * @param map
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月23日 下午5:25:44
	 */
	@Override
	public List<SysUserDTO> getList(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("SysUser.getList", map, SysUserDTO.class);
	}

	@Override
	public SysUserEntity findById(Integer id) throws BizException {
		SysUserEntity entity = new SysUserEntity();
		entity.setId(id);
		return baseDao.find(entity);
	}

	@Override
	public List<SysUserEntity> getUserListByOrg(Integer orgId) throws BizException {
		if (orgId == null) {
			return null;
		}
		Map<String, Object> params = new HashMap<>();
		params.put("orgId", orgId);
		List<SysUserEntity> list = baseDao.queryForList("SysUser.getUserListByOrg", params, SysUserEntity.class);
		return list;
	}

	@Override
	public List<SysUserDTO> getAdmin(Map<String, Object> map) throws BizException {

		return baseDao.queryForList("SysUser.getAdmin", map, SysUserDTO.class);
	}

	@Override
	public GuDengPage<SysUserDTO> getUserListPage(GuDengPage<SysUserDTO> page) throws BizException {
		// 查询总页数
		Map<String, Object> paraMap = page.getParaMap();
		int count = baseDao.queryForObject("SysUser.getListCount", paraMap, Integer.class);
		page.setTotal(count);
		List<SysUserDTO> list = null;
		if (count > 0) {
			list = baseDao.queryForList("SysUser.getListByPage", paraMap, SysUserDTO.class);
		}
		page.setRecordList(list);
		return page;
	}
}