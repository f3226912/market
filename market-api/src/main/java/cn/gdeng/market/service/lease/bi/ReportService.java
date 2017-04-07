package cn.gdeng.market.service.lease.bi;



import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.lease.bi.BiFeetakeInfoDTO;
import cn.gdeng.market.dto.lease.bi.BiKeyIndexDTO;
import cn.gdeng.market.exception.BizException;
/**
 * bi报表服务
 * @author gcwu
 *
 */
public interface ReportService {

	/**
	 * 获取关键指标信息
	 * @param map
	 * @return
	 * @throws BizException
	 */
   BiKeyIndexDTO getReportKeyIndex(Map<Object,Object> map) throws BizException;

   /**
    * 获取费项收缴情况
    * @param map
    * @return
    */
   List<BiFeetakeInfoDTO> getReportExpfeeInfo(Map<Object, Object> map) throws BizException;
   
   
   /**
    * 获取优惠情况分析
    * @param map
    * @return
    */
   List<BiFeetakeInfoDTO> getReportPreferentialInfo(Map<Object, Object> map) throws BizException;
   
   /**
    * 获取优惠情况分析pie图
    * @param map
    * @return
    */
   List<BiFeetakeInfoDTO> getReportPreferentialInfoPie(Map<Object, Object> map) throws BizException;
   
   /**
    * 获取未收款况
    * @param map
    * @return
    */
   List<BiFeetakeInfoDTO> getReportDidfeeInfo(Map<Object, Object> map) throws BizException;
   
}
