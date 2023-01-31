package com.digital3.schema;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ErrorMsg {
	@ApiModelProperty(required = true, position = 1, example = "1", dataType = "long", notes = "에러코드")
	private long errorCode;
	
	@ApiModelProperty(required = true, position = 2, example = "에러 메세지", dataType = "string")
	private String errorMsg;

	public long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
