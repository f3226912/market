package cn.gdeng.market.dto.lease.settings;

import java.io.Serializable;

/**
 * 平面描点DTO
 * @author youj 
 *
 */
public class PlaneDrawDot implements Serializable{

	private static final long serialVersionUID = -8370099432018019036L;
	
	private String id;
	
	private String name;
	
	private String x;
	
	private String y;

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

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}
	
	
    
	
}

