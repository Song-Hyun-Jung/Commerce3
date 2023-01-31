package com.digital3.sql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.digital3.sql.vo.ProductVO;

@Mapper
public interface ProductMapper {
	public ProductVO getProduct(String productName);
	public ProductVO getProductById(long productId);
	public List<ProductVO> searchByKeyword(String keyword);
	public List<ProductVO> searchByCategory(String categoryName);
	public int insertProduct(ProductVO productVO);
}
