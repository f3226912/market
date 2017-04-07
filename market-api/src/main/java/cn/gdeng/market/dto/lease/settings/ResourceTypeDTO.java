package cn.gdeng.market.dto.lease.settings;

import java.io.Serializable;

/**
 * 资源类型DTO
 * @author youj 
 *
 */
public class ResourceTypeDTO implements Serializable{

	private static final long serialVersionUID = -8370099432018012389L;
	
	private String id;
	
	private String code;
	
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	
}

