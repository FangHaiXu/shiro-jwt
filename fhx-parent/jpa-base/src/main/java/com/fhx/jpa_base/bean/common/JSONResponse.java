package com.fhx.jpa_base.bean.common;

import java.io.Serializable;

public class JSONResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8375479099312357283L;
	/**
	 * 是否成功
	 */
	private boolean success;
	/**
	 * 数据
	 */
	private Object data;
	/**
	 * 消息提示
	 */
	private String msg;
	/**
	 * 状态码
	 */
	private int status;

	public static JSONResponse Create(boolean success, String msg){
		JSONResponse result=new JSONResponse();
		result.success=success;
		result.msg=msg;
		return result;
	}
	
	public static JSONResponse Create(boolean success, String msg, Object data){
		JSONResponse result=new JSONResponse();
		result.success=success;
		result.msg=msg;
		result.data=data;
		return result;
	}

	public boolean isSuccess() {
		return success;
	}


	public JSONResponse setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public Object getData() {
		return data;
	}


	public JSONResponse setData(Object data) {
		this.data = data;
		return this;
	}

	public String getMsg() {
		return msg;
	}


	public JSONResponse setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public int getStatus() {
		return status;
	}

	public JSONResponse setStatus(int status) {
		this.status = status;
		return this;
	}
	
}
