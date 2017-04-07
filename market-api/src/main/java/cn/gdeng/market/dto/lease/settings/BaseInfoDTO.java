package cn.gdeng.market.dto.lease.settings;

import java.io.Serializable;

/**
 * 基础信息DTO
 * @author youj 
 *
 */
public class BaseInfoDTO implements Serializable{

	private static final long serialVersionUID = -8370099432018010965L;
	
	
	private String id;
	
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
    
	
}

