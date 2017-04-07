package cn.gdeng.market.service.lease.resources;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.resources.MarketResourceDTO;
import cn.gdeng.market.dto.lease.resources.MarketResourceMeasureUnionDTO;
import cn.gdeng.market.dto.lease.resources.MarketResourcePrintDTO;
import cn.gdeng.market.dto.lease.settings.ResourceContractDTO;
import cn.gdeng.market.entity.lease.resources.MarketResourceEntity;
import cn.gdeng.market.enums.MarketResourceLeaseStatusEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.BaseService;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author bdhuang
 *
 */
public interface MarketResourceService extends BaseService<MarketResourceDTO> {

	public int selCountByCondition(Map<String, Object> paramMap) throws BizException;

	public GuDengPage<MarketResourceDTO> selByPage(GuDengPage<MarketResourceDTO> page, 
			MarketResourceDTO dto) throws BizException;

	/**
	 * 放租前(只有租赁状态为"待放租"才能执行放租操作, 成功后状态变为"待租")
	 * 和
	 * 回收前(只有租赁状态为"待租"才能执行回收操作, 成功后状态变为"待放租")
	 * 检查可行性
	 * @param idList
	 * @return
	 * @throws BizException
	 */
	public int rentFeasibility(List<Integer> idList, MarketResourceLeaseStatusEnum status) throws BizException;

	/**
	 * 放租和回收
	 * @param idList
	 * @param operatorId
	 * @return
	 * @throws BizException
	 */
	public boolean rentAndRecovery(List<Integer> idList, String operatorId, MarketResourceLeaseStatusEnum status) throws BizException;

	public boolean insert(MarketResourceEntity entity) throws BizException;

	public boolean update(MarketResourceEntity entity) throws BizException;
	
	/**
	 * 批量删除
	 * @param idList
	 * @param operatorId
	 * @return
	 * @throws BizException
	 */
	public boolean batchDelete(List<Integer> idList, String operatorId) throws BizException;

	public boolean delete(int id, String operatorId) throws BizException;

	public Long persist(MarketResourceEntity entity) throws BizException;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return
	 * @throws BizException
	 */
	public GuDengPage<MarketResourceDTO> queryByPage(GuDengPage<MarketResourceDTO> page)throws BizException;
	
	/**
	 * 根据条件查询其它资源(分页查询)
	 * @param map
	 * @return
	 * @throws BizException
	 */
	public GuDengPage<MarketResourceDTO> queryOtherByPage(GuDengPage<MarketResourceDTO> page)throws BizException;

	/**
	 * 根据ID集合批量修改对象
	 * 
	 * @param list
	 * @return 影响条数
	 * 
	 */
	public int updateBatch(List<Map<String,Object>> list) throws BizException;
	
	
	/**
	 * 根据ID获取 费项名称+计量表分类：计量表编号
	 * 
	 * @param resourceId
	 * @return 列表
	 * 
	 */
	public List<MarketResourceMeasureUnionDTO>  getMeasureResourceById(Map<String, Object> paramMap) throws BizException;
	
	/**
	 * 获取租赁资源打印需要的信息
	 * @param ids
	 * @return
	 */
	public List<MarketResourcePrintDTO> queryPrintInfoByIds(String ids);
	
	/**
	 * 根据条件查询
	 * 
	 * @param map
	 * @return List<DTO>
	 */
	public List<MarketResourceDTO> getResources(Map<String, Object> map) throws BizException;
	
	/**
	 * 当前市场根据id和资源编码查询总数
	 * @param paramMap
	 * @return
	 * @throws BizException
	 */
	public int findCountByIdAndCodeAndMarketId(Map<String, Object> paramMap) throws BizException;
	
	/**
	 * 当前市场根据id和资源简码查询总数
	 * @param paramMap
	 * @return
	 * @throws BizException
	 */
	public int findCountByIdAndShortCodeAndMarketId(Map<String, Object> paramMap) throws BizException;
	
	/**
	 * 当前市场根据id和资源名称查询总数
	 * @param paramMap
	 * @return
	 * @throws BizException
	 */
	public int findCountByIdAndNameAndMarketId(Map<String, Object> paramMap) throws BizException;
	
	/**
     * 根据资源ID查询合同信息
     * 
     * */
    public ResourceContractDTO queryByResourceId(String resourceId) throws BizException;
    
	/**--------------------------------------平面图接口优化----------------------------------*/
	public List<MarketResourceDTO> queryResourceDotByCondition(Map<String, Object> map) throws BizException;
	
	/**
	 * 根据条件查询未绘制的资源
	 */
	public List<MarketResourceDTO> getNoDotResources(Map<String, Object> map) throws BizException;
	
	/**
	 * 根据合同ID集合查  面积和
	 */
	public Map<String , Object> queryByResourceIds(Map<String, Object> map);
}


