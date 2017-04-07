package cn.gdeng.market.service.lease.settings;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.PrintSettingDTO;
import cn.gdeng.market.entity.lease.settings.PrintSettingEntity;
import cn.gdeng.market.exception.BizException;

public interface PrintSetService {

	/**
	 * 分页查询（包含总记录数和列表数据）
	 * @param page
	 * @return
	 * @throws BizException
	 */
	public GuDengPage<PrintSettingDTO> queryByPage(GuDengPage<PrintSettingDTO> page) throws BizException;

	/**
	 * 获取总记录数
	 * @param paramMap
	 * @return
	 * @throws BizException
	 */
	public int countByCondition(Map<String, Object> paramMap) throws BizException;
	
	/**
	 * 获取分页列表数据
	 * @param paramMap
	 * @return
	 * @throws BizException
	 */
	public List<PrintSettingDTO> queryPageListByCondition(Map<String, Object> paramMap) throws BizException;
	
	/**
	 * 新增
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	public int add(PrintSettingEntity entity) throws BizException;
	
	/**
	 * 删除（逻辑删除）
	 * @param id
	 * @throws BizException
	 */
	public void delete(int id) throws BizException;
	
	/**
	 * 修改
	 * @param dto
	 * @throws BizException
	 */
	public void update(PrintSettingDTO dto) throws BizException;
	
	/**
	 * 根据id获取详情
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public PrintSettingDTO queryById(int id) throws BizException;
	
	/**
	 * 获取列表数据（不分页）
	 * @param paramMap
	 * @return
	 * @throws BizException
	 */
	public List<PrintSettingDTO> queryListByCondition(Map<String, Object> paramMap) throws BizException;

	/**
	 * 批量删除（逻辑删除）
	 * @param ids
	 */
	public void batchDelete(String ids);
	
	/**
	 * 获取套打设置中的模板文件
	 * @param id
	 * @return
	 */
	public PrintSettingDTO queryTemplateDocById(int id);
}
