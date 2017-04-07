package cn.gdeng.market.util;

import java.util.Properties;

/**
 * 属性文件公用类
 */
public class PropertyUtil {

    private Properties properties;

    /**
     * 获取value
     * 
     * @param key
     * @return Properties
     */
    public String getValue(String key) {
    	try {
    		String msg = (String) getProperties().get(key);
    		return msg;
    	} catch (Exception e) {
    		return null;
    	}
    }
    
    /**
     * 获取配置文件数据 ,替换占位符 {0},{1},{2}
     * 
     * @param key,数组
     * @return Properties
     */
    public String getValue(String key, String... param) {
    	try {
    		String msg = (String) getProperties().get(key);
    		for (int i = 0; i < param.length; i++) {
    			msg = msg.replace("{" + i + "}", param[i]);
    		}
    		return msg;
    	} catch (Exception e) {
    		return null;
    	}
    }
    
    /**
     * 获取整型的value
     * 
     * @param key
     * @return Properties
     */
    public Integer getInt(String key) {
        try {
        	Integer val = (Integer) getProperties().get(key);
            return val;
        } catch (Exception e) {
            return null;
        }
    }


    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
