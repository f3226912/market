package cn.gdeng.market.dto.lease.common;

import java.util.List;

import cn.gdeng.market.entity.lease.common.AreaEntity;

/**
 *  地区DTO
 * @author wjguo 
 *
 * datetime:2016年10月10日 上午10:21:47
 */
public class AreaDTO  extends AreaEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8370099432018934551L;
	
	/**
	 * 子地区集合
	 */
	private List<AreaDTO> childAreas;

	public List<AreaDTO> getChildAreas() {
		return childAreas;
	}

	public void setChildAreas(List<AreaDTO> childAreas) {
		this.childAreas = childAreas;
	}
	
	
}

