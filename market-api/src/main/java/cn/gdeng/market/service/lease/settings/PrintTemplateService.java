package cn.gdeng.market.service.lease.settings;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.PrintTemplateDTO;
import cn.gdeng.market.entity.lease.settings.PrintTemplateEntity;
import cn.gdeng.market.exception.BizException;
/**
 * 套打模板
 * @author dengjianfeng
 *
 */
public interface PrintTemplateService {

	/**
	 * 分页查询（包括总记录数和列表数据）
	 * @param page
	 * @return
	 * @throws BizException
	 */
	public GuDengPage<PrintTemplateDTO> queryByPage(GuDengPage<PrintTemplateDTO> page) throws BizException;
	
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
	 * @throws Exception
	 */
	public List<PrintTemplateDTO> queryPageListByCondition(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 新增
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	public int add(PrintTemplateEntity entity) throws BizException;

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
	public void update(PrintTemplateEntity entity) throws BizException;
	
	/**
	 * 根据id获取详情
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public PrintTemplateDTO queryById(int id) throws BizException;
	
	/**
	 * 获取列表（不分页）
	 * @param paramMap
	 * @return
	 * @throws BizException
	 */
	public List<PrintTemplateDTO> queryListByCondition(Map<String, Object> paramMap) throws BizException;

	/**
	 * 批量删除（逻辑删除）
	 * @param ids
	 * @throws BizException 
	 */
	public void batchDelete(String ids) throws BizException;

	/**
	 * 用于判断模板编码是否存在
	 * @param templateCode
	 * @return
	 */
	public int countByTemplateCode(String templateCode);
	
	/**
	 * 用于判断模板名称唯一性
	 * @param templateCode
	 * @return
	 */
	public int countByTemplateName(String templateName);

}
