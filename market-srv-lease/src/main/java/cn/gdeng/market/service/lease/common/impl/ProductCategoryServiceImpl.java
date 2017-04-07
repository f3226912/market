package cn.gdeng.market.service.lease.common.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.lease.common.ProductCategoryDTO;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.common.ProductCategoryService;

@Service(value = "productCategoryService")
public class ProductCategoryServiceImpl implements ProductCategoryService {
	@Autowired
	BaseDao<?> baseDao;

	@Override
	public List<ProductCategoryDTO> findAllByParent(Map<String,Object> paramMap) {		
		return baseDao.queryForList("ProductCategory.findAllByParent", paramMap,ProductCategoryDTO.class);
	}

	@Override
	public List<ProductCategoryDTO> getAllCategory(Map<String, Object> paramMap) throws BizException  {
		return baseDao.queryForList("ProductCategory.queryByCondition", paramMap,ProductCategoryDTO.class);
	}

}
