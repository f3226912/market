package cn.gdeng.market.service.lease.bi;

import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.bi.BiLeaseListDTO;
import cn.gdeng.market.exception.BizException;
/**
 * 
 * @作者 XieZhongGuo
 * @创建时间 2016年10月17日
 * @说明 租赁情况一览表服务接口
 * @版本 v1.0
 */
public interface BiLeaseListService {
    /**
     * 
     * @作者 XieZhongGuo
     * @创建时间 2016年10月17日
     * @方法说明 根据条件查询租赁情况一览列表信息函数
     * @param page
     * @param dto
     * @return
     * @版本 V1.0
     */
	public  GuDengPage<BiLeaseListDTO> getBiLeaseList(GuDengPage<BiLeaseListDTO> page,BiLeaseListDTO dto)throws BizException;;
    /**
     * 
     * @作者 XieZhongGuo
     * @创建时间 2016年10月17日
     * @方法说明 统计总记录数函数
     * @param paramMap
     * @return
     * @throws BizException
     * @版本 V1.0
     */
    public int countTotal(Map<String, Object> paramMap)throws BizException;
    /**
     * 
     * @作者 XieZhongGuo
     * @创建时间 2016年10月22日
     * @方法说明 获取租赁情况合计数据
     * @param paramMap
     * @return
     * @throws BizException
     * @版本 V1.0
     */
    public BiLeaseListDTO getSum(Map<String, Object> paramMap)throws BizException;

}
