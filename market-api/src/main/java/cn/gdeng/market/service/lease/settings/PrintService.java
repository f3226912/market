package cn.gdeng.market.service.lease.settings;

import java.util.Map;

import cn.gdeng.market.exception.BizException;

public interface PrintService {

	public String convertDocToPdf(int settingId, Map<String, String> dataMap) throws BizException;
}
