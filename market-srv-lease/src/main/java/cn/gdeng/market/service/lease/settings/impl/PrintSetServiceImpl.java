package cn.gdeng.market.service.lease.settings.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.gudeng.framework.dba.transaction.annotation.Transactional;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.PrintSettingDTO;
import cn.gdeng.market.entity.lease.settings.PrintSettingEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.settings.PrintSetService;

@Service(value="printSetService")
public class PrintSetServiceImpl implements PrintSetService {
	
	@Resource
	private BaseDao<?> baseDao;

	@Override
	public GuDengPage<PrintSettingDTO> queryByPage(GuDengPage<PrintSettingDTO> page) throws BizException {
		int total = baseDao.queryForObject("PrintSetting.countByCondition", page.getParaMap(), Integer.class);
		List<PrintSettingDTO> recordList = null;
		if(total > 0){
			recordList = baseDao.queryForList("PrintSetting.queryByConditionPage", page.getParaMap(), PrintSettingDTO.class);
		}
		page.setTotal(total);
		page.setRecordList(recordList);
		return page;
	}

	@Override
	public int countByCondition(Map<String, Object> paramMap) throws BizException {
		int total = baseDao.queryForObject("PrintSetting.countByCondition", paramMap, Integer.class);
		return total;
	}

	@Override
	public List<PrintSettingDTO> queryPageListByCondition(Map<String, Object> paramMap) throws BizException {
		List<PrintSettingDTO> list = baseDao.queryForList("PrintSetting.queryByConditionPage", paramMap, PrintSettingDTO.class);
		return list;
	}

	@Override
	public int add(PrintSettingEntity entity) throws BizException {
		long id = baseDao.persist(entity, Long.class);
		return (int)id;
	}

	@Override
	public void delete(int id) throws BizException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("isDeleted", 1);
		baseDao.execute("PrintSetting.dynamicUpdate", paramMap);
	}

	@Override
	public void update(PrintSettingDTO dto) throws BizException {
		baseDao.execute("PrintSetting.update", dto);
	}

	@Override
	public PrintSettingDTO queryById(int id) throws BizException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		PrintSettingDTO dto = baseDao.queryForObject("PrintSetting.queryById", paramMap, PrintSettingDTO.class);
		return dto;
	}

	@Override
	public List<PrintSettingDTO> queryListByCondition(Map<String, Object> paramMap) throws BizException {
		List<PrintSettingDTO> list = baseDao.queryForList("PrintSetting.queryByCondition", paramMap, PrintSettingDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void batchDelete(String ids) {
		if(StringUtils.isBlank(ids)){
			return;
		}
		String[] idArr = ids.split(",");
		int len = idArr.length;
		@SuppressWarnings("rawtypes")
		Map[] batchValues = new Map[len];
		for(int i = 0; i < len; i++){
			batchValues[i] = new HashMap<String, Object>();
			batchValues[i].put("id", Integer.parseInt(idArr[i]));
			batchValues[i].put("isDeleted", 1);
		}
		baseDao.batchUpdate("PrintSetting.dynamicUpdate", batchValues);
	}

	@Override
	public PrintSettingDTO queryTemplateDocById(int id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return baseDao.queryForObject("PrintSetting.queryTemplateDocById", paramMap, PrintSettingDTO.class);
	}

}
