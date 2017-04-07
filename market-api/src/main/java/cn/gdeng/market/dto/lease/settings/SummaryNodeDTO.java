package cn.gdeng.market.dto.lease.settings;

import java.io.Serializable;

/**
 * 资源节点统计DTO
 * @author youj 
 *
 */
public class SummaryNodeDTO extends SummaryDTO implements Serializable{

	private static final long serialVersionUID = -8370099432018019290L;
	
	private String id; //坐标

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	
	
	
}

