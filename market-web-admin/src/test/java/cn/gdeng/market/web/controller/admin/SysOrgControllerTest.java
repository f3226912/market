package cn.gdeng.market.web.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import cn.gdeng.market.admin.util.HttpUtil;


public class SysOrgControllerTest {
	
	private String SYS_ORG_URL = "http://localhost:8080//market-web-admin/company/";

	@Test
	public void test新增组织(){
		Map<String,String> param = new HashMap<>();
		
		param.put("type", "2");
		param.put("shortName", "深圳宏进有限公司2");
		param.put("fullName", "深圳宏进有限公司2");
		String str = HttpUtil.ajaxClientPost(SYS_ORG_URL+"addSysOrg", param);
		System.out.println(str);
	}
	
	@Test
	public void test修改组织(){
		Map<String,String> param = new HashMap<>();
		param.put("id", "5");
		param.put("shortName", "深圳宏进有限公司2");
		param.put("fullName", "深圳宏进有限公司2");
		String str = HttpUtil.ajaxClientPost(SYS_ORG_URL+"updateSysOrg", param);
		System.out.println(str);
	}
	
	@Test
	public void test删除组织(){
		Map<String,String> param = new HashMap<>();
		param.put("id", "5");
		String str = HttpUtil.ajaxClientPost(SYS_ORG_URL+"deleteSysOrg", param);
		System.out.println(str);
	}
	
	@Test
	public void test分页查询(){
		Map<String,String> param = new HashMap<>();
		param.put("pageNum", "1");
		param.put("pageSize", "1");
		param.put("createTime", "2016-10-11");
		String str = HttpUtil.ajaxClientPost(SYS_ORG_URL+"queryTopSysOrg4Page", param);
		System.out.println(str);
	}
}
