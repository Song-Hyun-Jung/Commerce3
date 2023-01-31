package com.digital3.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.digital3.schema.ErrorMsg;
import com.digital3.schema.Product;
import com.digital3.service.ProductService;
import com.digital3.utils.ExceptionUtils;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "상품", description = "Product Related API")
@RequestMapping(value = "/rest/product")

public class ProductController {

	@Resource
	ProductService productSvc;
	
	@RequestMapping(value = "/insertProduct", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "상품등록", notes = "상품등록을 위한 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Product.class),
					@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> insertProduct(
			@Parameter(name = "상품 등록을 위한 카테고리 정보", description = "", required = true) @RequestBody Product product){ 
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();
		
		Product product_res = new Product();
		
		try {
			if(productSvc.insertProduct(product)) {
				product_res = productSvc.productSearch(product.getProductName());
			}
			
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		
		return new ResponseEntity<Product>(product_res, header, HttpStatus.valueOf(200)); // ResponseEntity를 활용한 응답 생성
	}
	
	//관리자 검색
	@RequestMapping(value = "/search/product/{productName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "관리자 상품 검색", notes = "관리자가 상품 검색하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Product.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> findProduct(@ApiParam(value = "상품명") @PathVariable String productName) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();

		ErrorMsg errors = new ErrorMsg();
		
		try {
			Product product = productSvc.productSearch(productName);
			return new ResponseEntity<Product>(product, header, HttpStatus.valueOf(200));  // ResponseEntity를 활용한 응답 생성
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

	}	

	
	//상품 구매자 검색-키워드가 포함된 모든 상품 검색
	@RequestMapping(value = "/searchByKeyword/{keyword}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "고객 상품 키워드 검색", notes = "고객이 상품을 키워드로 검색하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Product.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> searchByKeyword(@ApiParam(value = "검색 키워드") @PathVariable String keyword) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();

		ErrorMsg errors = new ErrorMsg();
		
		try {
			List<Product> product = productSvc.searchByKeyword(keyword);
			return new ResponseEntity<List<Product>>(product, header, HttpStatus.valueOf(200));  // ResponseEntity를 활용한 응답 생성
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

	}
	
	@RequestMapping(value = "/searchByCategory/{categoryName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "고객 상품 카테고리별 검색", notes = "고객이 상품을 카테고리별로 검색하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Product.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> searchByCategory(@ApiParam(value = "카테고리명") @PathVariable String categoryName) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();

		ErrorMsg errors = new ErrorMsg();
		
		try {
			List<Product> productList = productSvc.searchByCategory(categoryName);
			return new ResponseEntity<List<Product>>(productList, header, HttpStatus.valueOf(200));  // ResponseEntity를 활용한 응답 생성
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

	}
	
}
