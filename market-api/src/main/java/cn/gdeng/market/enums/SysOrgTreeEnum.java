package cn.gdeng.market.enums;

public enum SysOrgTreeEnum {

	MARKET(1,"展开到市场级别"),
	
	GROUP(2,"只展开集团");
	
	private int level;
	
	private String desc;
	
	private SysOrgTreeEnum(int level,String desc){
		this.level = level;
		
		this.desc = desc;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
