package cn.gdeng.market.service.lease.common;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.lease.common.ProductCategoryDTO;
import cn.gdeng.market.exception.BizException;

public interface ProductCategoryService {
   
	public List<ProductCategoryDTO> findAllByParent(Map<String,Object> paramMap);
	
	/**
	 * 获取经营分类
	 * @param paramMap
	 * @return
	 */
	public List<ProductCategoryDTO> getAllCategory(Map<String,Object> paramMap) throws BizException;
}
