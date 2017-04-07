package cn.gdeng.test.market.service.admin;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.bean.Node;
import cn.gdeng.market.dto.lease.settings.MarketDTO;
import cn.gdeng.market.enums.SysOrgTreeEnum;
import cn.gdeng.market.service.admin.SysOrgService;
import cn.gdeng.test.market.service.BaseTest;

public class SysOrgServiceTest extends BaseTest{

	@Resource
	private SysOrgService sysOrgService;
	
	@Test
	public void test查询顶级公司个数() throws Exception{
		int count = sysOrgService.queryTopOrgCount(null);
		System.out.println(count);
	}
	
	@Test
	public void test分页查询顶级公司() throws Exception{
		GuDengPage<SysOrgDTO> page = new GuDengPage<>(1, 10);
		page = sysOrgService.queryTopOrgByPage(page);
		
		System.out.println(JSONObject.toJSONString(page));
	}
	
	@Test
	public void testQueryChildrenById() throws Exception {
		List<Node> list = sysOrgService.queryChildrenById("16");
		System.out.println(JSONObject.toJSONString(list));
	}
	
	@Test
	public void testInitOrgTree() throws Exception {
		List<Node> list = sysOrgService.initOrgTree("16",SysOrgTreeEnum.MARKET);
		System.out.println(JSONObject.toJSONString(list));
	}
	
	@Test
	public void testQueryUserPageByOrgId() throws Exception {
		GuDengPage<SysUserDTO> page = new GuDengPage<>(1, 10);
		page = sysOrgService.queryUserPageByOrgId(page, 16);
		System.out.println(JSONObject.toJSONString(page));
	}
	
	public void testAddSysOrg() throws Exception{
		SysOrgDTO org = new SysOrgDTO();
		org.setType("3");
		org.setShortName("宏进市场");
		org.setFullName("宏进市场");
		org.setParentId(15);
		
		
		MarketDTO m = new MarketDTO();
		m.setName(org.getShortName());
		m.setCode("A0010");
		m.setOpenTime(new Date());
		m.setMarketStatus(1);
		m.setProvinceId(110000);
		m.setCityId(110100);
		m.setAreaId(110101);
		m.setPca("北京市/北京市/东城区");
		org.setMarKetEntity(m);
		
		int a = sysOrgService.addSysOrg(org);
		System.out.println(a);
	}
}
