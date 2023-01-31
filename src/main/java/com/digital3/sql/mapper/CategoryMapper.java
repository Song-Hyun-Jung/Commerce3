package com.digital3.sql.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.digital3.sql.vo.CategoryVO;

@Mapper
public interface CategoryMapper {
	public int insertCategory(CategoryVO categoryVO);
	public CategoryVO getCategory(String categoryName);
}
