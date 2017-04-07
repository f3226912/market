package cn.gdeng.market.lease.controller.lease.bi;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.bi.BiContractMainDTO;
import cn.gdeng.market.dto.lease.bi.BiLeaseListDTO;
import cn.gdeng.market.dto.lease.settings.MarketResourceTypeDTO;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.controller.lease.contract.ContractManageController;
import cn.gdeng.market.service.lease.bi.BiContractMainService;
import cn.gdeng.market.service.lease.bi.BiLeaseListService;
import cn.gdeng.market.service.lease.settings.MarketResourceTypeService;
import cn.gdeng.market.util.DateUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("biLeaseList")
public class BiLeaseListContriller extends BaseController {


	
	private Logger logger = LoggerFactory.getLogger(BiLeaseListContriller.class);

   //注册service服务
   @Resource(name="biLeaseListService")
   private BiLeaseListService biLeaseListService;
   
   @Resource
   private MarketResourceTypeService marketResourceTypeService;
   
   /** 导出Excel单个sheet的最大行数 */
   protected final int SHEET_MAX_ROW_NUM = 1048576;
	/** 导出Excel时每次分页大小*/
   protected final int EXPORT_PAGE_SIZE = 1000;
   /** 导出结果集上限*/
   protected final int EXPORT_MAX_SIZE = 50000;
   
	@RequestMapping(value = "/index")
	public ModelAndView index(ModelAndView mav) throws BizException {
		mav.setViewName("bi/biLeaseList/biLeaseList");
		return mav;
	}
	/**
	 * 
	 * @作者 XieZhongGuo
	 * @创建时间 2016年10月26日
	 * @方法说明 校验市场ID是否存在 
	 * @return
	 * @throws BizException
	 * @版本 V1.0
	 */
	@RequestMapping(value = "/checkMarket")
	public @ResponseBody String checkMarket() throws BizException{
		if(null==getCurrentMarket()||getCurrentMarket().getId()==null){
			return "0";
		}else{
			return "1";
		}
	}

   /**
    * 
    * @作者 XieZhongGuo
    * @创建时间 2016年10月13日
    * @方法说明 根据筛选条件查询租赁合同信息分页列表
    * @param request
    * @return
    * @throws BizException
    * @版本 V1.0
    */
   @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult<GuDengPage<BiLeaseListDTO>> findByPage(HttpServletRequest request, 
			HttpServletResponse response, BiLeaseListDTO dto) throws BizException{
		AjaxResult<GuDengPage<BiLeaseListDTO>> ar = new AjaxResult<>();
		  GuDengPage<BiLeaseListDTO> page = getPageInfoByRequest();
	      //判断市场ID是否为空 空则不查数据 直接返回空集合
		  if(checkMarket().equals("1")){
		   if(dto.getMarketResourceTypeId()==null||dto.getMarketResourceTypeId().equals("")){
			   MarketResourceTypeDTO typeDTO=new MarketResourceTypeDTO();
			   typeDTO.setMarketId(getCurrentMarket().getId());
			   List<MarketResourceTypeDTO> dtos=marketResourceTypeService.selAllByCondition(typeDTO);
				  //初始化默认展示商铺资源类型的统计
				  if(dtos!=null){
				   for (MarketResourceTypeDTO dto2:dtos) {
					 if(dto2.getName().equals("商铺")){
						 dto.setMarketResourceTypeId(dto2.getId()+"");
						 }
					} 
				  }
			   }
		   		dto.setMarketId(super.getCurrentMarket().getId());
		   		page = biLeaseListService.getBiLeaseList(page, dto);
		   }ar.setResult(page);
		    return ar;
	}
   /**
    * 
    * @作者 XieZhongGuo
    * @创建时间 2016年10月19日
    * @方法说明 根据当前登陆用户的权限 获取市场ID 根据市场ID取得对应的资源类型 
    * @return
    * @throws BizException
    * @版本 V1.0
    */
   @RequestMapping(value = "/getAllTypes", method = RequestMethod.POST)
   public @ResponseBody JSONArray getAllTypes() throws BizException{
	   JSONArray array=new JSONArray();
	   if(checkMarket().equals("1")){
		   MarketResourceTypeDTO dto=new MarketResourceTypeDTO();
		   dto.setMarketId(getCurrentMarket().getId());
		   List<MarketResourceTypeDTO> dtos=marketResourceTypeService.selAllByCondition(dto);
		   JSONObject object=null;
		  if(dtos!=null){
		   for (MarketResourceTypeDTO dto2:dtos) {
			object=new JSONObject();
			object.put("id", dto2.getId());
			object.put("name", dto2.getName());
			array.add(object);
		} 
		  }
	   }
	  
	   return array;
   }

	 /**
	    * 
	    * @作者 XieZhongGuo
	    * @创建时间 2016年10月19日
	    * @方法说明 根据所传参数校验导出的结果集大小 
	    * @param request
	    * @return
	    * @版本 V1.0
	    */
		@RequestMapping(value="checkExportParams", produces="application/json;charset=utf-8")
		@ResponseBody
		public AjaxResult<String> checkExportParams(HttpServletRequest request){
			AjaxResult<String> re =new AjaxResult<String> ();
			try{
				if(checkMarket().equals("1")){
					
				
				// 设置查询参数
				Map<String, Object> map = getParametersMap(request);
				map.put("marketId",getCurrentMarket().getId());
				int total = biLeaseListService.countTotal(map);
				if (total ==0){
					re.setCode(202);
					re.setMsg( "查询没有符合的结果 ,请修改其他查询条件...");
					return re;
				}
				if (total > EXPORT_MAX_SIZE){
					re.setCode(202);
					re.setMsg("查询结果集太大(>"+EXPORT_MAX_SIZE+"条), 请缩减日期范围, 或者修改其他查询条件...");
					return re;
				}
				}else{
					re.setCode(202);
					re.setMsg("请选择市场");
					return re;
				}
			}catch(Exception e){
				logger.warn(e.getMessage());
				re.setCode(202);
				re.setMsg("查询异常");
				return re;
			}
			
			return re;
		}
		/**
		 * 
		 * @作者 XieZhongGuo
		 * @创建时间 2016年10月13日
		 * @方法说明 excel导出函数  
		 * @param request
		 * @param response
		 * @throws BizException 
		 * @版本 V1.0
		 */
		@RequestMapping("export")
		public void export(HttpServletRequest request, HttpServletResponse response, BiLeaseListDTO dto) throws BizException{
			//查询参数
			Map<String, Object> paramMap = getParametersMap(request);	
			OutputStream ouputStream = null;
			dto.setMarketId(getCurrentMarket().getId());
            
            	
           
			try{
				if(checkMarket().equals("1")){
				String fileName="";
				
					fileName= "租赁情况一览"+DateUtil.toString(new Date(), "yyyy-MM-dd_HH:mm:ss");

				 // 设置输出响应头信息，
	            response.setContentType("application/vnd.ms-excel");
	            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1") + ".xls");
	            ouputStream = response.getOutputStream();
	            
	            WritableWorkbook wwb = Workbook.createWorkbook(ouputStream);
	            if(wwb == null){
	            	return;
	            }
	            
	            // 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
	            WritableSheet sheet = wwb.createSheet("未过期合同", 0);
	            
	            String[] headers = {"资源类型", "区域", "资源总数", "已租数量", "出租率(数量)%", "可租面积 ㎡", "已租面积 ㎡", "出租率(面积)%", "租金收入", "租赁坪效（总面积）", "租赁坪效(已租面积)"};
	            for(int i = 0, len = headers.length; i < len; i++){
	            	 sheet.addCell(new Label(i, 0, headers[i]));
	            }
	            
				// 查询导出数据总数
	            int countApiResult = biLeaseListService.countTotal(paramMap);
	            int totalCount = 0;
	         
	            	totalCount =countApiResult;
	            // 计算分几次查询导出数据
	            int exportCount = totalCount / EXPORT_PAGE_SIZE;
	            if((totalCount % EXPORT_PAGE_SIZE) != 0){
	            	exportCount++;
	            }
	            int startRow = 0;
	            
	            for(int i = 0; i < exportCount; i++){
	            	// 查询分页数据
	            	paramMap.put("startRow", startRow);
	            	paramMap.put("endRow", EXPORT_PAGE_SIZE);
	    			GuDengPage<BiLeaseListDTO> page=new GuDengPage<BiLeaseListDTO> ();
	            	page.setParaMap(paramMap);
	    			page=biLeaseListService.getBiLeaseList(page,dto);
	    			
	            	List<BiLeaseListDTO> list = null;
		    			list = (List<BiLeaseListDTO>) page.getRecordList();
		   
		            if(CollectionUtils.isEmpty(list)){
		            	break;
		            }
		            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	            	for(int j = 0, len = list.size(); j < len; j++){
	            		BiLeaseListDTO dto1 = list.get(j);
	                	sheet.addCell(new Label(0, j+1+startRow, dto1.getMarketResourceTypeName()));
	                    sheet.addCell(new Label(1, j+1+startRow, dto1.getAreaName()));
	                    sheet.addCell(new Label(2, j+1+startRow, dto1.getCountResource()+""));
	                    sheet.addCell(new Label(3, j+1+startRow, dto1.getCountRented()+""));
	                    sheet.addCell(new Label(4, j+1+startRow, dto1.getRentalRate()+""));
	                    sheet.addCell(new Label(5, j+1+startRow, dto1.getAreaAvailableForRent()+""));
	                    sheet.addCell(new Label(6, j+1+startRow, dto1.getLeasedArea()+""));
	                    sheet.addCell(new Label(7, j+1+startRow, dto1.getRentalRateArea()+""));
	                    sheet.addCell(new Label(8, j+1+startRow, dto1.getRentalIncome()+""));
	                    sheet.addCell(new Label(9, j+1+startRow, dto1.getLeasePxZmj()+""));
	                    sheet.addCell(new Label(10, j+1+startRow, dto1.getLeasePxYzmj()+""));
	                }
	            	startRow += EXPORT_PAGE_SIZE;
	            }
	            wwb.write();
	            wwb.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally {
	            try {
	                ouputStream.flush();
	                ouputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	
		 }	
}
