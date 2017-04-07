package cn.gdeng.market.lease.controller.workflow.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.workflow.WfTaskDTO;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.service.workflow.bus.WfTaskService;

/**
 *  流程任务控制类
 * @author hxp
 */
@Controller
@RequestMapping("wfTask")
public class WfTaskController extends BaseController{
		
	@Autowired
	private WfTaskService wfTaskService;
	/**
	 * 调转到我的待办任务列表界面
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("jumpToGtasks")
	public ModelAndView index(ModelAndView mv) throws BizException {
		mv.setViewName("workflow/monitor/mygtasksIndex");
		return mv;
	}
	/**
	 * 获取待办任务
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "getGtasks4Page")
	@ResponseBody
	public AjaxResult<GuDengPage<WfTaskDTO>> getGtasks4Page() throws BizException {
		AjaxResult<GuDengPage<WfTaskDTO>> result = new AjaxResult<GuDengPage<WfTaskDTO>>();
		//获取分页信息
		GuDengPage<WfTaskDTO> pageInfo = super.getPageInfoByRequest();
		//查询条件
		pageInfo.getParaMap().put("actorId", super.getSysUser().getId());
		pageInfo = wfTaskService.getGtasks4Page(pageInfo);
		result.setResult(pageInfo);
		return result;
	}
	
}
