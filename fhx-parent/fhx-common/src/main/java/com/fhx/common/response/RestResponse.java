package com.fhx.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fhx
 * @date 2019/7/21 12:12
 */
@Data
public class RestResponse implements Serializable {

    private Boolean status;
    private int code;
    private Object data;
    private String msg;

    RestResponse(Boolean status, int code, Object data) {
        this.status = status;
        this.code = code;
        this.data = data;
    }

    RestResponse(Boolean status, int code, Object data, String msg) {
        this.status = status;
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static RestResponse success() {
        return new RestResponse(true,200, null);
    }

    public static RestResponse success(Object data) {
        return new RestResponse(true, 200, data);
    }

    public static RestResponse error(String msg, Object data,int code) {
        return new RestResponse(false, code, data, msg);
    }




}