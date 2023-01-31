package com.digital3.sql.vo;

import lombok.Data;

@Data
public class CategoryVO {
	private long categoryId;
	private String categoryName;
	
	public CategoryVO() {}

	public CategoryVO(long categoryId, String categoryName) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}
	
}
