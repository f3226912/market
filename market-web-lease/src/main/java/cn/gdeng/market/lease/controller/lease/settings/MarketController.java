package cn.gdeng.market.lease.controller.lease.settings;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.MarketDTO;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.service.lease.settings.MarketService;

import com.alibaba.dubbo.config.annotation.Reference;

/**
 * 市场控制器
 * @author bdhuang
 *
 */
@Controller
@RequestMapping("market")
public class MarketController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(MarketController.class);
	
	@Reference
	private MarketService marketService;
	
	
	@RequestMapping("index")
	public ModelAndView marketIndex(ModelAndView mv) throws BizException {
		mv.setViewName("settings/market/index");
		return mv;
	}
	
	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 * @author huangbd
	 * @throws BizException 
	 */
	@RequestMapping("query")
	@ResponseBody
	public AjaxResult<GuDengPage<MarketDTO>> query(HttpServletRequest request,MarketDTO dto) throws BizException {
		GuDengPage<MarketDTO> page = getPageInfoByRequest();
		AjaxResult<GuDengPage<MarketDTO>> res = new AjaxResult<>();
		SysOrgDTO sysDto=super.getCurrentMarket();
		if(sysDto==null){
			return res;
		}
		Integer id =sysDto.getId();
		//获取分页信息
		page.getParaMap().put("id", id);
		page = marketService.queryByPage(page);
		res.setResult(page);
		return res;
	}
	
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("view")
	public ModelAndView view(ModelAndView mv) throws BizException {
		mv.setViewName("settings/market/viewMarket");
		return mv;
	}
	
	/**
	 * 根据id查看数据
	 * 
	 * @param request
	 * @return
	 * @author hbd
	 * 
	 */
	@RequestMapping("getByMarketId")
	@ResponseBody
	public AjaxResult<MarketDTO> getByMarketId(String marketId, HttpServletRequest request) throws BizException{
		AjaxResult<MarketDTO> res=new AjaxResult<>();
		MarketDTO dto = marketService.getById(marketId);
		res.setResult(dto);
		return res;
	}
	
	
	
//
//	/**
//	 * 根据id删除数据
//	 * 
//	 * @param request
//	 * @return
//	 * @author lidong
//	 */
//	@RequestMapping(value = "delete")
//	@ResponseBody
//	public String delete(String ids, HttpServletRequest request) {
//		Map<String, Object> map = new HashMap<>();
//		try {
//		    if (StringUtils.isEmpty(ids)) {
//                map.put("msg", "未选中删除数据");
//            } else {
//	            List<String> list = Arrays.asList(ids.split(","));
//				int i = marketToolService.deleteBatch(list);
//				map.put("msg", "success");
//            }
//		} catch (Exception e) {
//			map.put("msg", "服务器出错");
//		}
//		return JSONObject.toJSONString(map)
//	}

	/**
	 * 市场列表（下拉选择选项）
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("queryListForSelect")
	@ResponseBody
	public AjaxResult<List<MarketDTO>> queryListForSelect() throws BizException {
		List<MarketDTO> list = marketService.queryListForSelect();
		AjaxResult<List<MarketDTO>> res=new AjaxResult<List<MarketDTO>>();
		res.setResult(list);
		return res;
	}
}
