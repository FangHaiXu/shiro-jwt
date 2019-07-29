package com.fhx.jpa_base.controller.common;

import com.fhx.jpa_base.bean.common.JSONResponse;

/**
 * 父控制器
 * 存放每个控制器公共的方法
 * @author fhx
 */

public class BaseController {

	/**
	 * 成功
	 * 
	 * @param data
	 *  返回的数据
	 * @return
	 */
	public JSONResponse success(Object data) {
		return JSONResponse.Create(true,"OK").setData(data);
	}

	/**
	 * 失败
	 * 
	 * @param msg
	 *            返回的消息
	 * @return
	 */
	public JSONResponse error(String msg) {
		return JSONResponse.Create(false, msg).setStatus(1);
	}
}
