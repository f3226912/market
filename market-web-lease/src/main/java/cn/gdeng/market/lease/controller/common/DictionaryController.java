package cn.gdeng.market.lease.controller.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.gdeng.market.entity.admin.SysDictionaryEntity;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.service.admin.SysDictionaryService;

/**
 * 数据字典
 *
 */
@Controller
@RequestMapping("dictionary")
public class DictionaryController {
	
	@Reference
	private SysDictionaryService dictionaryService;

	/**
	 * 根据数据字典类型获取字典列表
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getByType")
	@ResponseBody
	public AjaxResult<List<SysDictionaryEntity>> getByType(String type) throws Exception{
		AjaxResult<List<SysDictionaryEntity>> result = new AjaxResult<>();
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", type);
		List<SysDictionaryEntity> dictionarys = dictionaryService.getList(param);
		
		result.setResult(dictionarys);
		return result;
	}
}
