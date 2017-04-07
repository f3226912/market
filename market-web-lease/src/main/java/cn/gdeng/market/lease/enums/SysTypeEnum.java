package cn.gdeng.market.lease.enums;

/**
 * 系统级类型枚举
 * @author cai.xu
 *
 */
public enum SysTypeEnum {
	
	YES(1, "是"), 
	NO(0, "不是");
	
	private int key;  
    private String value;
    
    private SysTypeEnum(int key, String value)  
    {  
        this.key = key;  
        this.value = value;  
    }  
  
    public int getKey()  
    {  
        return key;  
    }  
  
    public void setKey(int key)  
    {  
        this.key = key;  
    }  
  
    public String getValue()  
    {  
        return value;  
    }  
  
    public void setValue(String value)  
    {  
        this.value = value;  
    }  
    
    public static String getNameByCode(int code){
    	SysTypeEnum[] values = SysTypeEnum.values();
		for(SysTypeEnum val : values){
			if(val.getKey() == code ){
				return val.getValue();
			}
		}
		return null;
	}
    public static void main(String[] args) {
    	System.out.println(SysTypeEnum.YES.getKey());
	}
}
