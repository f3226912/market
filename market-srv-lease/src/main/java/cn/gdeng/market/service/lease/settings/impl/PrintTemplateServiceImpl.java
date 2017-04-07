package cn.gdeng.market.service.lease.settings.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.PrintTemplateDTO;
import cn.gdeng.market.entity.lease.settings.PrintTemplateEntity;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.redis.support.JodisTemplate;
import cn.gdeng.market.service.lease.settings.PrintTemplateService;
import cn.gdeng.market.util.PropertyUtil;

import com.gudeng.framework.dba.transaction.annotation.Transactional;

@Service(value="printTemplateService")
public class PrintTemplateServiceImpl implements PrintTemplateService{
	
	@Resource
	private BaseDao<?> baseDao;
	
	@Resource
	private JodisTemplate jodisTemplate;
	
	@Resource
	private PropertyUtil propertyUtil;

	@Override
	public GuDengPage<PrintTemplateDTO> queryByPage(GuDengPage<PrintTemplateDTO> page) throws BizException {
		int total = baseDao.queryForObject("PrintTemplate.countByCondition", page.getParaMap(), Integer.class);
		List<PrintTemplateDTO> recordList = null;
		if(total > 0){
			recordList = baseDao.queryForList("PrintTemplate.queryByConditionPage", page.getParaMap(), PrintTemplateDTO.class);
		}
		if(recordList != null){
			for(PrintTemplateDTO dto : recordList){
				dto.setTemplateUrl(propertyUtil.getValue("gd.uplaod.host")+dto.getTemplateUrl());
			}
		}
		page.setTotal(total);
		page.setRecordList(recordList);
		return page;
	}
	
	@Override
	public int countByCondition(Map<String, Object> paramMap) throws BizException {
		int total = baseDao.queryForObject("PrintTemplate.countByCondition", paramMap, Integer.class);
		return total;
	}

	@Override
	public List<PrintTemplateDTO> queryPageListByCondition(Map<String, Object> paramMap) throws Exception {
		List<PrintTemplateDTO> list = baseDao.queryForList("PrintTemplate.queryByConditionPage", paramMap, PrintTemplateDTO.class);
		return list;
	}
	
	@Override
	public int add(PrintTemplateEntity entity) throws BizException {
		long id = baseDao.persist(entity, Long.class);
		return (int)id;
	}

	@Override
	public void delete(int id) throws BizException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("templateId", id);
		int count = baseDao.queryForObject("PrintSetting.countByTemplateId", paramMap, Integer.class);
		if(count > 0){
			throw new BizException(MsgCons.C_20000, "该模板["+id+"]被其他套打设置使用,不能被删除");
		}
		paramMap.clear();
		paramMap.put("id", id);
		paramMap.put("isDeleted", 1);
		baseDao.execute("PrintTemplate.dynamicUpdate", paramMap);
	}

	@Override
	public void update(PrintTemplateEntity entity) throws BizException {
		// 查询修改前的模板信息
		PrintTemplateDTO dto = queryById(entity.getId());
		if(dto == null){
			throw new BizException(MsgCons.C_20000, "修改记录不存在");
		}
		
		// 判断模板编码是否存在,唯一性验证
		String paramTemplateCode = entity.getTemplateCode();
		if(!paramTemplateCode.equals(dto.getTemplateCode())){
			int codeCount = countByTemplateCode(paramTemplateCode);
			if(codeCount > 0){
				throw new BizException(MsgCons.C_20000, "模板编码已存在");
			}
		}
		
		// 判断模板名称是否存在，唯一性验证
		String paramTemplateName = entity.getTemplateName();
		if(!paramTemplateName.endsWith(dto.getTemplateName())){
			int nameCount = countByTemplateName(paramTemplateName);
			if(nameCount > 0) {
				throw new BizException(MsgCons.C_20000, "模板名称已存在");
			}
		}
		
		baseDao.dynamicMerge(entity);
	}

	@Override
	public PrintTemplateDTO queryById(int id) throws BizException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		PrintTemplateDTO dto = baseDao.queryForObject("PrintTemplate.queryById", paramMap, PrintTemplateDTO.class);
		return dto;
	}

	@Override
	public List<PrintTemplateDTO> queryListByCondition(Map<String, Object> paramMap) throws BizException {
		List<PrintTemplateDTO> list = baseDao.queryForList("PrintTemplate.queryByCondition", paramMap, PrintTemplateDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void batchDelete(String ids) throws BizException {
		if(StringUtils.isBlank(ids)){
			return;
		}
		String[] idArr = ids.split(",");
		if(idArr == null || idArr.length < 1){
			return;
		}
		// 验证套打模板是否被套打设置使用
		int len = idArr.length;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for(int i = 0; i < len; i++){
			Integer templateId = Integer.parseInt(idArr[i]);
			paramMap.put("templateId", templateId);
			int count = baseDao.queryForObject("PrintSetting.countByTemplateId", paramMap, Integer.class);
			if(count > 0){
				throw new BizException(MsgCons.C_20000,"当前套打模板被套打设置引用，不允许删除");
			}
			
			// 获取模板url；删除缓存模板文件
			String templateUrl = baseDao.queryForObject("PrintTemplate.getTemplateUrl", paramMap, String.class);
			if(templateUrl != null) {
				String key = Const.PRINT_TEMPLATE_KEY_PRE + templateUrl;
				jodisTemplate.del(key.getBytes());
			}
		}
		
		@SuppressWarnings("rawtypes")
		Map[] batchValues = new Map[len];
		for(int i = 0; i < len; i++){
			batchValues[i] = new HashMap<String, Object>();
			batchValues[i].put("id", Integer.parseInt(idArr[i]));
			batchValues[i].put("isDeleted", 1);
		}
		baseDao.batchUpdate("PrintTemplate.dynamicUpdate", batchValues);
	}

	@Override
	public int countByTemplateCode(String templateCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("templateCode", templateCode);
		int count = baseDao.queryForObject("PrintTemplate.countByTemplateCode", paramMap, Integer.class);
		return count;
	}

	@Override
	public int countByTemplateName(String templateName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("templateName", templateName);
		int count = baseDao.queryForObject("PrintTemplate.countByTemplateName", paramMap, Integer.class);
		return count;
	}
	
}
