package cn.gdeng.market.enums;

public interface SysOrgEnum {
	
	interface STATUS{
		String NORMAL = "1";//正常
		
		String DISABLED = "2";//禁用
		
		String DELETED = "3"; //已删除
	}

	interface TYPE {
		String COMPANY = "1";
		
		String DEPARTMENT = "2";
		
		String MARKET = "3";
	}
}
