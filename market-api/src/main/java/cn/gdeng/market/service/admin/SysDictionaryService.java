package cn.gdeng.market.service.admin;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.entity.admin.SysDictionaryEntity;
import cn.gdeng.market.exception.BizException;

/**
 * 
 * 
 * @author lidong
 *
 */
public interface SysDictionaryService {
	public Long persist(SysDictionaryEntity entity) throws BizException;

	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return DTO
	 * 
	 */
	public SysDictionaryEntity getById(String id) throws BizException;

	/**
	 * 保存
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Integer insert(SysDictionaryEntity t) throws BizException;

	/**
	 * 根据条件查询list(不分页查询)
	 * 
	 * @param map
	 * @return List<DTO>
	 */
	public List<SysDictionaryEntity> getList(Map<String, Object> map) throws BizException;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<SysDictionaryEntity> getListPage(Map<String, Object> map) throws BizException;

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 * @return 影响条数
	 * 
	 */
	public int deleteById(String id) throws BizException;

	/**
	 * 根据ID集合批量删除对象
	 * 
	 * @param list
	 * @return 影响条数
	 * 
	 */
	public int deleteBatch(List<String> list) throws BizException;

	/**
	 * 修改
	 * 
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public int update(SysDictionaryEntity t) throws BizException;

	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String, Object> map) throws BizException;
	
	/**
	 * 根据code值获取value
	 * @param code
	 * @return value
	 */
	public String getDicVal(String code) throws BizException;
	
	public SysDictionaryEntity getEnableDictionary(String type,String code) throws BizException;
}