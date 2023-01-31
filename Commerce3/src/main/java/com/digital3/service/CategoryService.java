package com.digital3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digital3.schema.Category;
import com.digital3.sql.mapper.CategoryMapper;
import com.digital3.sql.vo.CategoryVO;

@Component
public class CategoryService {
	
	@Autowired
	CategoryMapper categoryMapper;
	
	public Category categorySearch(String categoryName) throws Exception {
		try {
			CategoryVO cv = categoryMapper.getCategory(categoryName);
	
			Category category = new Category();
			
			if (cv != null) {
				category.setCategoryId(cv.getCategoryId());
				category.setCategoryName(cv.getCategoryName());
			}
			
			return category;
		}catch(Exception e) {
			throw e;
		}
	}
	
	public boolean categoryInsert(Category category) throws Exception {

		try {
			if (categorySearch(category.getCategoryName()).getCategoryName() != null) {
				throw new Exception("이미 등록된 카테고리 입니다.");
			}

			category.setCategoryId(System.currentTimeMillis());
			CategoryVO cv = new CategoryVO(category.getCategoryId(), category.getCategoryName());
			int insertResult = categoryMapper.insertCategory(cv);
			
			if(insertResult == 1)
				return true;
			else
				return false;
		} catch (Exception e) {
			throw e;
		}
	}

}
