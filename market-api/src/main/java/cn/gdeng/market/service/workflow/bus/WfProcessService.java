package cn.gdeng.market.service.workflow.bus;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.workflow.WfProcessDTO;
import cn.gdeng.market.exception.BizException;

/**
 * 
 * 流程定义管理
 * @author xphou
 *
 */
public interface WfProcessService{
	
	/**
	 * 查询分页条数
	 * @param param
	 * @return
	 * @throws BizException
	 */
	int countByCondition(Map<String,Object> param) throws BizException;
	/**
	 * 查询分页
	 * @param page
	 * @return
	 * @throws BizException
	 */
	GuDengPage<WfProcessDTO> queryByConditionPage(GuDengPage<WfProcessDTO> page) throws BizException;
	
	/**
	 * 根据ID查询流程
	 * @param param
	 * @return
	 * @throws BizException
	 */
	public WfProcessDTO getById(String id) throws BizException;
	
	/**
	 * 查询当前集团下所有流程模板
	 * @param orgIdList
	 * @return
	 * @throws BizException
	 */
	public List<WfProcessDTO> getCurGroupProcess(Map<String,Object> map) throws BizException;
	
	/**
	 * 是否唯一的流程名称 
	 * @param map
	 * @return
	 * @throws BizException
	 */
	public int isUniqueForProcessDisplayName(Map<String ,Object> map) throws BizException;
}
