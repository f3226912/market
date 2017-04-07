package cn.gdeng.market.dto.lease.settings;

import java.io.Serializable;

/**
 * 资源描点DTO
 * @author youj 
 *
 */
public class ResourcePlaneDrawDot extends PlaneDrawDot implements Serializable{

	private static final long serialVersionUID = -8370099432018123907L;
	
	private String leaseStatus;

	public String getLeaseStatus() {
		return leaseStatus;
	}

	public void setLeaseStatus(String leaseStatus) {
		this.leaseStatus = leaseStatus;
	}
    
	
}

