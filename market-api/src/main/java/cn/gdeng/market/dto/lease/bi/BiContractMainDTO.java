package cn.gdeng.market.dto.lease.bi;

import java.io.Serializable;

import cn.gdeng.market.util.ExcelConf;

public class BiContractMainDTO  implements Serializable {

	private static final long serialVersionUID = -294220833881650738L;
	
	private Integer marketId;
	@ExcelConf(excelHeader="合同状态",sort=1)
	private String contractStatus;
	@ExcelConf(excelHeader="合同编号",sort=2)
	private String contractNo;
	@ExcelConf(excelHeader="租赁资源",sort=3)
	private String leasingResource;
	@ExcelConf(excelHeader="乙方",sort=5)
	private String partyB;
	@ExcelConf(excelHeader="计费面积",sort=6)
	private String countArea;
	@ExcelConf(excelHeader="起租日期",sort=7)
	private String startLeasingDay;
	@ExcelConf(excelHeader="結算日期",sort=8)
	private String endLeasingDay;
	@ExcelConf(excelHeader="累计租金收入",sort=4)
	private String accountPayed;
	private Integer type;

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getLeasingResource() {
		return leasingResource;
	}

	public void setLeasingResource(String leasingResource) {
		this.leasingResource = leasingResource;
	}

	public String getPartyB() {
		return partyB;
	}

	public void setPartyB(String partyB) {
		this.partyB = partyB;
	}

	public String getCountArea() {
		return countArea;
	}

	public void setCountArea(String countArea) {
		this.countArea = countArea;
	}

	public String getStartLeasingDay() {
		return startLeasingDay;
	}

	public void setStartLeasingDay(String startLeasingDay) {
		this.startLeasingDay = startLeasingDay;
	}

	public String getEndLeasingDay() {
		return endLeasingDay;
	}

	public void setEndLeasingDay(String endLeasingDay) {
		this.endLeasingDay = endLeasingDay;
	}

	public String getAccountPayed() {
		return accountPayed;
	}

	public void setAccountPayed(String accountPayed) {
		this.accountPayed = accountPayed;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
    
}
