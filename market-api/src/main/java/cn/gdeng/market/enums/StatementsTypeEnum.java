package cn.gdeng.market.enums;

/**结算类型
 * 
 * @author kwang
 *
 * datetime:2016年10月14日 下午3:16:07 
 */
public enum StatementsTypeEnum {
//0 合同到期 1 提前解约',
    HTDQ((byte)0, "合同到期"),
	TQJY((byte)1, "提前解约");
	
	private Byte code;
	
	private String name;

	private StatementsTypeEnum(Byte code, String name) {
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
		StatementsTypeEnum[] values = StatementsTypeEnum.values();
		for(StatementsTypeEnum val : values){
			if(val.getCode().equals(code)){
				return val.getName();
			}
		}
		return "";
	}
	
}	