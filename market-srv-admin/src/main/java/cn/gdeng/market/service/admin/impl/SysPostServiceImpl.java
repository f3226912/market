package cn.gdeng.market.service.admin.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.framework.dba.transaction.annotation.Transactional;
import com.gudeng.framework.dba.util.DalUtils;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.admin.SysPostDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.entity.admin.SysPostDataEntity;
import cn.gdeng.market.entity.admin.SysPostEntity;
import cn.gdeng.market.entity.admin.SysPostFunctionEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.admin.SysPostService;

/**
 * 系统岗位管理
 * 
 * @author lidong dli@gdeng.cn
 * @time 2016年10月12日 上午10:40:37
 */
@Service(value = "sysPostService")
public class SysPostServiceImpl implements SysPostService {

	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public Long persist(SysPostEntity entity) throws BizException {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<SysPostDTO> getList(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("SysPost.querySimpleByCondition", map, SysPostDTO.class);
	}

	@Override
	public GuDengPage<SysPostDTO> getListPage(GuDengPage<SysPostDTO> page) throws BizException {
		// 查询总页数
		Map<String, Object> paraMap = page.getParaMap();
		int count = getTotal(paraMap);
		page.setTotal(count);
		List<SysPostDTO> list = null;
		if (count > 0) {
			list = baseDao.queryForList("SysPost.queryByConditionPage", paraMap, SysPostDTO.class);
		}
		page.setRecordList(list);
		return page;
	}

	@Override
	public int deleteById(SysPostEntity entity) throws BizException {
		return baseDao.dynamicMerge(entity);
	}

	@Override
	public int update(SysPostDTO t) throws BizException {
		return 0;
	}

	@Override
	public int update(SysPostEntity t) throws BizException {
		return baseDao.dynamicMerge(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws BizException {
		return baseDao.queryForObject("SysPost.countByCondition", map, Integer.class);
	}

	@Override
	public GuDengPage<SysUserDTO> getPostUserListPage(GuDengPage<SysUserDTO> page) throws BizException {
		// 查询总页数
		Map<String, Object> paraMap = page.getParaMap();
		int count = baseDao.queryForObject("SysUserPost.countByCondition", paraMap, Integer.class);
		page.setTotal(count);
		List<SysUserDTO> list = null;
		if (count > 0) {
			list = baseDao.queryForList("SysUserPost.getUsersByPostId", paraMap, SysUserDTO.class);
		}
		page.setRecordList(list);
		return page;
	}

	@Override
	public int checkPost(Map<String, Object> map) throws BizException {
		return baseDao.queryForObject("SysUserPost.countByCondition", map, Integer.class);// 查询该岗位下是否有用户
	}

	@Override
	public List<SysPostEntity> getSourcePostListByPage(Map<String, Object> param) throws BizException {
		return baseDao.queryForList("SysPost.getSourcePostListByPage", param, SysPostEntity.class);
	}


	/**
	 * 新增岗位，并同时批量添加功能权限、数据权限
	 * 
	 * @param entity
	 * @param dto
	 *            用于获取数据权限和功能权限
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月17日 下午4:53:08
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Long addPost(SysPostEntity entity, SysPostDTO dto) throws BizException {
		entity.setStatus("1");
		Long id = baseDao.persist(entity, Long.class);// 新增岗位,获取岗位id
		String userId = entity.getCreateUserId();
		List<SysPostDataEntity> pdatas = dto.getPdatas();
		List<SysPostFunctionEntity> pfuncs = dto.getPfuncs();
		if (id != null && id > 0) {
			// 保存功能权限
			if (pfuncs != null) {
				int sizeF = pfuncs.size();
				if (sizeF > 0) {
					Map<String, Object>[] batchValues = new HashMap[sizeF];
					for (int i = 0; i < sizeF; i++) {
						SysPostFunctionEntity pfunc = pfuncs.get(i);
						pfunc.setPostId(id);
						pfunc.setCreateUserId(userId);
						batchValues[i] = DalUtils.convertToMap(pfunc);
					}
					baseDao.batchUpdate("SysPostFunction.insert", batchValues);
				}
			}
			// 保存数据权限
			if (pdatas != null) {
				int sizeD = pdatas.size();
				if (sizeD > 0) {
					Map<String, Object>[] batchValues = new HashMap[sizeD];
					for (int i = 0; i < sizeD; i++) {
						SysPostDataEntity pdata = pdatas.get(i);
						pdata.setPostId(id);
						pdata.setCreateUserId(userId);
						batchValues[i] = DalUtils.convertToMap(pdata);
					}
					baseDao.batchUpdate("SysPostData.insert", batchValues);
				}
			}
		}
		return id;
	}

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
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Long updatePost(SysPostEntity entity, SysPostDTO dto) throws BizException {
		long result = baseDao.dynamicMerge(entity);
		Long id = Long.valueOf(entity.getId());
		String userId = entity.getUpdateUserId();
		List<SysPostDataEntity> pdatas = dto.getPdatas();
		List<SysPostFunctionEntity> pfuncs = dto.getPfuncs();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("postId", id);
		// 删除岗位下功能权限
		baseDao.execute("SysPostFunction.delete", paramMap);
		// 删除岗位下数据权限
		baseDao.execute("SysPostData.delete", paramMap);
		// 保存功能权限
		if (pfuncs != null) {
			int sizeF = pfuncs.size();
			if (sizeF > 0) {
				Map<String, Object>[] batchValues = new HashMap[sizeF];
				for (int i = 0; i < sizeF; i++) {
					SysPostFunctionEntity pfunc = pfuncs.get(i);
					pfunc.setPostId(id);
					pfunc.setCreateUserId(userId);
					batchValues[i] = DalUtils.convertToMap(pfunc);
				}
				baseDao.batchUpdate("SysPostFunction.insert", batchValues);
			}
		}
		// 保存数据权限
		if (pdatas != null) {
			int sizeD = pdatas.size();
			if (sizeD > 0) {
				Map<String, Object>[] batchValues = new HashMap[sizeD];
				for (int i = 0; i < sizeD; i++) {
					SysPostDataEntity pdata = pdatas.get(i);
					pdata.setPostId(id);
					pdata.setCreateUserId(userId);
					batchValues[i] = DalUtils.convertToMap(pdata);
				}
				baseDao.batchUpdate("SysPostData.insert", batchValues);
			}
		}
		return result;
	}

	/**
	 * 根据岗位获取功能权限集合
	 * 
	 * @param map
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月17日 下午4:56:05
	 */
	public List<SysPostFunctionEntity> getFuncsByPost(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("SysPostFunction.queryByCondition", map, SysPostFunctionEntity.class);
	}

	/**
	 * 根据岗位获取数据权限集合
	 * 
	 * @param map
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月17日 下午4:56:42
	 */
	public List<SysPostDataEntity> getDatasByPost(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("SysPostData.queryByCondition", map, SysPostDataEntity.class);
	}

	@Override
	public SysPostEntity getEntityById(Integer id) throws BizException {
		SysPostEntity paramEntity = new SysPostEntity();
		paramEntity.setId(id);
		return baseDao.find(SysPostEntity.class, paramEntity);
	}

	@Override
	public List<SysPostDTO> queryValidByIds(List<Integer> ids) throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("postIds", ids);
		params.put("status", "1");
		return baseDao.queryForList("SysPost.getSourcePostListByPage", params, SysPostDTO.class);
	}

}