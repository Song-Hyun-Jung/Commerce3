package com.digital3.schema;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class SuccessMsg {
	
	@ApiModelProperty(required = true, position = 2, example = "성공 메세지", dataType = "string")
	private String successMsg;

	public String getSuccessMsg() {
		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

	public SuccessMsg() {}
	public SuccessMsg(String successMsg) {
		super();
		this.successMsg = successMsg;
	}

	
		
}
