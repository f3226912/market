package cn.gdeng.market.dto.lease.settings;

/**
 *  地区DTO
 * @author youj 
 *
 * datetime:2016年10月10日 上午10:21:47
 */
public class ResourceResponseDTO  implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8370099432018919002L;
	
	private String id;
	
	private String name;
	
	private String shortCode;
	
	private String leaseArea;
	
	private String leaseStatus;
	
    private String x;
	
	private String y;
	
	private String contractId;
	
	private String customerName;
	
	private String customerMobile;

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public String getLeaseArea() {
		return leaseArea;
	}

	public void setLeaseArea(String leaseArea) {
		this.leaseArea = leaseArea;
	}

	public String getLeaseStatus() {
		return leaseStatus;
	}

	public void setLeaseStatus(String leaseStatus) {
		this.leaseStatus = leaseStatus;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

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

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	
	
	
}

