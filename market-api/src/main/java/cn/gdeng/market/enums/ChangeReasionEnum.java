package cn.gdeng.market.enums;

/**变更原因 
 * 
 * @author kwang
 *
 * datetime:2016年10月14日 下午3:16:07
 */
public enum ChangeReasionEnum {
    ZJSPTZ((byte)0, "租金水平调整 "),
	FXZJ((byte)1, "费项增减"), 
	ZJJM((byte)2, "租金减免");
	
	private Byte code;
	
	private String name;

	private ChangeReasionEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}

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
	
	public static String getNameByCode(Byte code){
		ChangeReasionEnum[] values = ChangeReasionEnum.values();
		for(ChangeReasionEnum val : values){
			if(val.getCode().equals(code)){
				return val.getName();
			}
		}
		return "";
	}
	
}	