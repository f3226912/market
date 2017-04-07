package cn.gdeng.market.web.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import cn.gdeng.market.lease.util.HttpUtil;

public class SysOrgControllerTest {

	private String SYS_ORG_URL = "http://localhost:8080//market-web-lease/sysOrg/";
	
	@Test
	public void test查询下级组织(){
		Map<String,String> param = new HashMap<>();
		
		param.put("id", "16");
		String str = HttpUtil.ajaxClientPost(SYS_ORG_URL+"queryChildrenById", param);
		System.out.println(str);
	}
	
	@ Test
	public void test新增组织(){
		
	}
}
