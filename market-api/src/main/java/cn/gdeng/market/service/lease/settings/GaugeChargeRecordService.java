package cn.gdeng.market.service.lease.settings;

import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.GaugeChargeRecordDTO;
import cn.gdeng.market.exception.BizException;

public interface GaugeChargeRecordService {

	/**
	 * 查询记录数
	 * @param map
	 * @return 记录数
	 */
	public GuDengPage<GaugeChargeRecordDTO> queryList(GuDengPage<GaugeChargeRecordDTO> page) throws BizException;
	
	/**
	 * 查询总条数 
	 * @param param
	 * @return
	 * @throws BizException
	 */
	public int queryCount(Map<String, Object> param) throws BizException;
}
