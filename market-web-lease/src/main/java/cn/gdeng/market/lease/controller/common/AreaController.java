package cn.gdeng.market.lease.controller.common;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gdeng.market.dto.lease.common.AreaDTO;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.service.lease.common.AreaService;

@Controller
@RequestMapping("area")
public class AreaController {

	private Logger logger = LoggerFactory.getLogger(AreaController.class);
	
	@Resource
	private AreaService areaService;
	
	/**
	 * 获取区域
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("queryChildrenArea")
	@ResponseBody
	public AjaxResult<List<AreaDTO>> queryChildrenArea(String areaId) throws Exception{

		logger.info("获取区域ID="+areaId);
		AjaxResult<List<AreaDTO>> result = new AjaxResult<>();
		List<AreaDTO> as = areaService.queryChildrenArea(areaId);
		result.setResult(as);
		return result;
	}
}
