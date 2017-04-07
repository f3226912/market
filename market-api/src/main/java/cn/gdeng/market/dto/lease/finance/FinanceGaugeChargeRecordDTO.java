package cn.gdeng.market.dto.lease.finance;

import cn.gdeng.market.entity.lease.finance.FinanceGaugeChargeRecordEntity;

public class FinanceGaugeChargeRecordDTO extends FinanceGaugeChargeRecordEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2175655980027780238L;
	
    /**
     * 费项名称
     */
    private String feeItemName ;
    
    /**
     * 费项类型名称
     */
    private String feeItemTypeName ;
    
    /**
     * 资源名称
     */
    private String resourceNames ;
    
	
	/**
	 * 计量表编号
	 */
	private String measureCode;
	
	/**
	 * 计量表最大读数
	 */
	private Double maxMeasureVal;
	
	/**
	 * 计量表分类ID
	 */
	private Integer measureTypeId;
	
	/**
	 * 计量表分类名称
	 */
	private String measureTypeName;
	
	/**
	 * 楼栋ID
	 */
	private Integer buildingId;
	
	/**
	 * 楼栋名称
	 */
	private String buildName;
	
	/**
	 * 区域ID
	 */
	private Integer areaId;
	/**
	 * 区域名称
	 */
	private String areaName;
	
	/**
	 * 损耗率
	 */
	private Double wastageRate;
	
	/**
	 * 是否分段计费 默认 1 是 0 否
	 */
	private Integer sectionalCharge;
	
	/**
	 * 本次用量
	 */
	private double thisQuantity;
	
	public Double getMaxMeasureVal() {
		return maxMeasureVal;
	}

	public void setMaxMeasureVal(Double maxMeasureVal) {
		this.maxMeasureVal = maxMeasureVal;
	}

	public double getThisQuantity() {
		return thisQuantity;
	}
	
	public void setThisQuantity(double thisQuantity) {
		this.thisQuantity = thisQuantity;
	}

	/**
	 * 计费标准ID
	 */
	private Integer expStandardId;
	
	private String noteDateStr;
	
	private Double newPrice;
	
	private String lastNoteDateStr;
	
	private String noteDateStart;
	
	private String noteDateEnd;
	
	public String getFeeItemName() {
		return feeItemName;
	}
	public void setFeeItemName(String feeItemName) {
		this.feeItemName = feeItemName;
	}
	public String getFeeItemTypeName() {
		return feeItemTypeName;
	}
	public void setFeeItemTypeName(String feeItemTypeName) {
		this.feeItemTypeName = feeItemTypeName;
	}
	public String getResourceNames() {
		return resourceNames;
	}
	public void setResourceNames(String resourceNames) {
		this.resourceNames = resourceNames;
	}

	public String getMeasureCode() {
		return measureCode;
	}

	public void setMeasureCode(String measureCode) {
		this.measureCode = measureCode;
	}

	public String getMeasureTypeName() {
		return measureTypeName;
	}

	public void setMeasureTypeName(String measureTypeName) {
		this.measureTypeName = measureTypeName;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getMeasureTypeId() {
		return measureTypeId;
	}

	public void setMeasureTypeId(Integer measureTypeId) {
		this.measureTypeId = measureTypeId;
	}

	public Double getWastageRate() {
		return wastageRate;
	}

	public void setWastageRate(Double wastageRate) {
		this.wastageRate = wastageRate;
	}

	public Integer getSectionalCharge() {
		return sectionalCharge;
	}

	public void setSectionalCharge(Integer sectionalCharge) {
		this.sectionalCharge = sectionalCharge;
	}

	public Integer getExpStandardId() {
		return expStandardId;
	}

	public void setExpStandardId(Integer expStandardId) {
		this.expStandardId = expStandardId;
	}
	public String getNoteDateStr() {
		return noteDateStr;
	}
	public void setNoteDateStr(String noteDateStr) {
		this.noteDateStr = noteDateStr;
	}
	public Double getNewPrice() {
		return newPrice;
	}
	public void setNewPrice(Double newPrice) {
		this.newPrice = newPrice;
	}
	public String getLastNoteDateStr() {
		return lastNoteDateStr;
	}
	public void setLastNoteDateStr(String lastNoteDateStr) {
		this.lastNoteDateStr = lastNoteDateStr;
	}
	public String getNoteDateStart() {
		return noteDateStart;
	}
	public void setNoteDateStart(String noteDateStart) {
		this.noteDateStart = noteDateStart;
	}
	public String getNoteDateEnd() {
		return noteDateEnd;
	}
	public void setNoteDateEnd(String noteDateEnd) {
		this.noteDateEnd = noteDateEnd;
	}

	@Override
	public String toString() {
		return "FinanceGaugeChargeRecordDTO [feeItemName=" + feeItemName + ", feeItemTypeName=" + feeItemTypeName
				+ ", resourceNames=" + resourceNames + ", measureCode=" + measureCode + ", measureTypeId="
				+ measureTypeId + ", measureTypeName=" + measureTypeName + ", buildingId=" + buildingId + ", buildName="
				+ buildName + ", areaId=" + areaId + ", areaName=" + areaName + ", wastageRate=" + wastageRate
				+ ", sectionalCharge=" + sectionalCharge + ", thisQuantity=" + thisQuantity + ", expStandardId="
				+ expStandardId + ", noteDateStr=" + noteDateStr + ", newPrice=" + newPrice + ", lastNoteDateStr="
				+ lastNoteDateStr + ", noteDateStart=" + noteDateStart + ", noteDateEnd=" + noteDateEnd + "]";
	}
	
	
}
