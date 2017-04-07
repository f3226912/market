package cn.gdeng.market.service;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.exception.BizException;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface BaseService<T> {
	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return DTO
	 * 
	 */
	public T getById(String id) throws BizException;

	/**
	 * 保存
	 * 
	 * @param dto
	 * @return
	 * @throws BizException
	 */
	public Integer insert(T t) throws BizException;

	/**
	 * 根据条件查询list(不分页查询)
	 * 
	 * @param map
	 * @return List<DTO>
	 */
	public List<T> getList(Map<String, Object> map) throws BizException;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return
	 * @throws BizException
	 */
	public List<T> getListPage(Map<String, Object> map) throws BizException;

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
	 * @throws BizException
	 */
	public int update(T t) throws BizException;

	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String, Object> map) throws BizException;
	
}
