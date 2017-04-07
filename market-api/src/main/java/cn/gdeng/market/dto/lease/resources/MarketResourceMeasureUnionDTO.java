package cn.gdeng.market.dto.lease.resources;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.gdeng.market.entity.lease.resources.MarketAreaBuildingEntity;

/**
 * 关联计量表信息
 * 
 * @author weiwenke
 *
 */
public class MarketResourceMeasureUnionDTO implements Serializable {
	private static final long serialVersionUID = -8986621122000117891L;
	private String expenditure_name; //费项名称
	private String measure_type_name; //计量分类名称
	private String measure_type_code; //计量分类编码

	private Integer resourceId; //资源id
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getExpenditure_name() {
		return expenditure_name;
	}
	public void setExpenditure_name(String expenditure_name) {
		this.expenditure_name = expenditure_name;
	}
	public String getMeasure_type_name() {
		return measure_type_name;
	}
	public void setMeasure_type_name(String measure_type_name) {
		this.measure_type_name = measure_type_name;
	}
	public String getMeasure_type_code() {
		return measure_type_code;
	}
	public void setMeasure_type_code(String measure_type_code) {
		this.measure_type_code = measure_type_code;
	}
	
	@Override
	public String toString() {
		return "MarketResourceMeasureUnionDTO [expenditure_name=" + expenditure_name + ", measure_type_name="
				+ measure_type_name + ", measure_type_code=" + measure_type_code + ", resourceId=" + resourceId + "]";
	}
	
	
}
