package cn.gdeng.market.enums;

/**合同资源处理状态
 * 
 * @author kwang
 *
 * datetime:2016年10月19日 下午3:16:07
 */
public enum ContractTaskStatusEnum {
//0 未处理 1 处理成功 2 处理失败',
    ING((byte)0, "未处理"),
	SUCCESS((byte)1, "处理成功"),
	ERROR((byte)2, "处理失败");
	
	private Byte code;
	
	private String name;

	private ContractTaskStatusEnum(Byte code, String name) {
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
		ContractTaskStatusEnum[] values = ContractTaskStatusEnum.values();
		for(ContractTaskStatusEnum val : values){
			if(val.getCode().equals(code)){
				return val.getName();
			}
		}
		return null;
	}
	
}	