package cn.gdeng.market.lease.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gdeng.market.dto.admin.SysMessageDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.entity.admin.SysMessageEntity;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.service.admin.SysMessageService;

/**
 * 消息Controller
 * @author sss
 *
 */
@Controller
@RequestMapping("sysMessage")
public class SysMessageController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(SysOrgController.class);
	
	@Resource
	private SysMessageService sysMessageService;
	
	@RequestMapping("list")
	public String index(){
		return "sys/message/sysMessage";
	}

	@RequestMapping("queryMessage4Page")
	@ResponseBody
	public AjaxResult<GuDengPage<SysMessageDTO>> queryMessage4Page(HttpServletRequest request) throws Exception{

		logger.info("分页查询我的消息");
		AjaxResult<GuDengPage<SysMessageDTO>> res = new AjaxResult<>();
		GuDengPage<SysMessageDTO> page = getPageInfoByRequest();
		//设置接收者
		SysUserDTO user = getSysUser();
		page.getParaMap().put("receiver", user.getUserId());
		page = sysMessageService.getMessage4Page(page);
		res.setResult(page);
		return res;
	}
	
	/**
	 * 获取未读消息的个数
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getNoReadMessageCount")
	@ResponseBody
	public AjaxResult<Integer> getNoReadMessageCount(HttpServletRequest request) throws Exception{

		logger.info("获取未读消息的个数");
		AjaxResult<Integer> res = new AjaxResult<>();
		Map<String,Object> params = new HashMap<>();
		SysUserDTO user = getSysUser();
		params.put("hasRead", 0); //0 未读 1 已读
		params.put("receiver", user.getUserId()); 
		int count = sysMessageService.getTotalByCondition(params);
		res.setResult(count);
		return res;
	}
	
	@RequestMapping("setRead/{id}")
	@ResponseBody
	public AjaxResult<Integer> setRead(@PathVariable("id") Integer id) throws Exception{

		logger.info("设置消息已读:id="+id);
		AjaxResult<Integer> res = new AjaxResult<>();
		SysMessageEntity entity = new SysMessageEntity();
		fillUpdateUser(entity);
		entity.setId(id);
		entity.setHasRead(1);//0 未读 1 已读
		sysMessageService.dynamicUpdate(entity);
		return res;
	}

}
