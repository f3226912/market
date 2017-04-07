package cn.gdeng.market.lease.enums;

/**
 * 状态枚举类
 * @author cai.xu
 *
 */
public enum StatusEnum {
	
	NORMAL(1, "正常"), 
	DELETED(0, "删除");
	
	private int key;  
    private String value;
    
    private StatusEnum(int key, String value)  
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
    	StatusEnum[] values = StatusEnum.values();
		for(StatusEnum val : values){
			if(val.getKey() == code ){
				return val.getValue();
			}
		}
		return null;
	}
    
    public static void main(String[] args) {
    	System.out.println(StatusEnum.getNameByCode(0));
	}
}
