package cn.gdeng.market.lease.enums;

public enum ExpType {
	//费项类型  1 周期性费项  2 走表类费项  3  一次性费项   4 临时性费项
	CYCLE(1, "周期性费项"),
	TABLE(2, "走表类费项"),
	DISPOSABLE(3, "一次性费项"),
	TEMP(4, "临时性费项");
	
	private int key;  
    private String value;
    
    private ExpType(int key, String value)  
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
    	ExpType[] values = ExpType.values();
		for(ExpType val : values){
			if(val.getKey() == code ){
				return val.getValue();
			}
		}
		return null;
	}
}
