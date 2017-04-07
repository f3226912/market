package cn.gdeng.market.enums;

/**
 *  地区状态枚举
 * @author wjguo
 *
 * datetime:2016年10月10日 上午11:27:26
 */
public enum AreaStatusEnum {
	/**
	 * 启用
	 */
	enabled((byte)1, "启用"),
	/**禁用
	 * 
	 */
	disabled((byte)0, "禁用");
	
	private AreaStatusEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}

	private Byte code;
	
	private String name;

	public Byte getCode() {
		return code;
	}

	public void setCode(Byte code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
