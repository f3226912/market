package cn.gdeng.market.service.lease.bi;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.bi.BiContractMainDTO;
import cn.gdeng.market.exception.BizException;

/**
 * 
 * @作者 XieZhongGuo
 * @创建时间 2016年10月13日
 * @说明  租赁合同服务接口
 * @版本 v1.0
 */
public interface BiContractMainService {
   /**
    * 
    * @作者 XieZhongGuo
    * @创建时间 2016年10月13日
    * @方法说明 获取租赁合同列表分页结果集函数
    * @param page
    * @return
    * @throws BizException
    * @版本 V1.0
    */
	public GuDengPage<BiContractMainDTO>  getContractMainList(GuDengPage<BiContractMainDTO> page,BiContractMainDTO dto) throws BizException;

    public int countTotal(Map<String, Object> paramMap)throws BizException;
    
    /**
     * 查询已过期未过期合同  type=0 有效合同 type=1无效合同
     * @param dto
     * @return
     * @throws BizException
     */
    public List<BiContractMainDTO>  getContractMainList(Map<String, Object> obj) throws BizException;
    
}
