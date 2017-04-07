package cn.gdeng.market.dto.lease.contract;

import java.io.Serializable;
import java.util.List;

/**
 * 合同综合信息
 *
 */
public class ContractDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1161458885845226810L;
	/**
	 * 合同基本信息
	 */
	private ContractMainDTO mainDTO;
	/**
	 * 按周期收费-租赁约定记录
	 */
	private List<ContractLeaseDTO> leaseList;
	/**
	 * 按周期收费-免租约定记录
	 */
	private List<ContractFreeDTO> freeList;
	/**
	 * 按周期收费-其他费约定记录
	 */
	private List<ContractOthersFeeDTO> othersFeeList;
	/**
	 * 按约定总金额-缴费约定记录
	 */
	private List<ContractPaymentDTO> paymentList;
	/**
	 * 变更记录
	 */
	private List<ContractChangeDTO> changeList;
	/**
	 * 文件路径
	 */
	private  String fileCompleteUrl;
	/**
	 * 附件
	 */
	private List<ContractAccessoriesDTO> accessoriesList;
	/**
	 * 结算按钮
	 */
	private boolean settlementButtonPower=false; 
	/**
	 * 变更按钮
	 */
	private boolean updateButtonPower=false; 

	/**
	 * 变更信息 
	 */
	private   ContractChangeDTO changeDto;
	
	public ContractMainDTO getMainDTO() {
		return mainDTO;
	}
	public void setMainDTO(ContractMainDTO mainDTO) {
		this.mainDTO = mainDTO;
	}
	public List<ContractLeaseDTO> getLeaseList() {
		return leaseList;
	}
	public void setLeaseList(List<ContractLeaseDTO> leaseList) {
		this.leaseList = leaseList;
	}
	public List<ContractFreeDTO> getFreeList() {
		return freeList;
	}
	public void setFreeList(List<ContractFreeDTO> freeList) {
		this.freeList = freeList;
	}
	public List<ContractOthersFeeDTO> getOthersFeeList() {
		return othersFeeList;
	}
	public void setOthersFeeList(List<ContractOthersFeeDTO> othersFeeList) {
		this.othersFeeList = othersFeeList;
	}
	public List<ContractPaymentDTO> getPaymentList() {
		return paymentList;
	}
	public void setPaymentList(List<ContractPaymentDTO> paymentList) {
		this.paymentList = paymentList;
	}
	public List<ContractAccessoriesDTO> getAccessoriesList() {
		return accessoriesList;
	}
	public void setAccessoriesList(List<ContractAccessoriesDTO> accessoriesList) {
		this.accessoriesList = accessoriesList;
	}
	public ContractChangeDTO getChangeDto() {
		return changeDto;
	}
	public void setChangeDto(ContractChangeDTO changeDto) {
		this.changeDto = changeDto;
	}
	public String getFileCompleteUrl() {
		return fileCompleteUrl;
	}
	public void setFileCompleteUrl(String fileCompleteUrl) {
		this.fileCompleteUrl = fileCompleteUrl;
	}
	public List<ContractChangeDTO> getChangeList() {
		return changeList;
	}
	public void setChangeList(List<ContractChangeDTO> changeList) {
		this.changeList = changeList;
	}
	public boolean isSettlementButtonPower() {
		return settlementButtonPower;
	}
	public void setSettlementButtonPower(boolean settlementButtonPower) {
		this.settlementButtonPower = settlementButtonPower;
	}
	public boolean isUpdateButtonPower() {
		return updateButtonPower;
	}
	public void setUpdateButtonPower(boolean updateButtonPower) {
		this.updateButtonPower = updateButtonPower;
	}
	
	
	
	
}
