package cn.gdeng.market.service.lease.bi.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.lease.bi.BiFeetakeInfoDTO;
import cn.gdeng.market.dto.lease.bi.BiKeyIndexDTO;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.bi.ReportService;

@Service(value="reportService")
public class ReportServiceImpl implements ReportService {

	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public BiKeyIndexDTO getReportKeyIndex(Map<Object, Object> map) throws BizException {
	
		return baseDao.queryForObject("BiReport.getReportKeyIndex", map,BiKeyIndexDTO.class);
	}

	@Override
	public List<BiFeetakeInfoDTO> getReportExpfeeInfo(Map<Object, Object> map) throws BizException {
		return baseDao.queryForList("BiReport.getReportExpfeeInfo", map,BiFeetakeInfoDTO.class);
	}
	@Override
	public List<BiFeetakeInfoDTO> getReportPreferentialInfo(Map<Object, Object> map) throws BizException {
		return baseDao.queryForList("BiReport.getReportPreferentialInfo", map,BiFeetakeInfoDTO.class);
	}

	@Override
	public List<BiFeetakeInfoDTO> getReportPreferentialInfoPie(Map<Object, Object> map) throws BizException {
		return baseDao.queryForList("BiReport.getReportPreferentialInfoPie", map,BiFeetakeInfoDTO.class);
	}
	@Override
	public List<BiFeetakeInfoDTO> getReportDidfeeInfo(Map<Object, Object> map) throws BizException {
		return baseDao.queryForList("BiReport.getReportDidfeeInfo", map,BiFeetakeInfoDTO.class);
	}
}
