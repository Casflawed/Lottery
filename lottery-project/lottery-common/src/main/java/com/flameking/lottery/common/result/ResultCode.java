package com.flameking.lottery.common.result;

/**
 * 枚举了一些常用API操作码
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "内部请求出错"),

    BADREQUEST(400,"请求参数不完整或不正确"),
    UNAUTHORIZED(401, "暂未登录或登录状态已经过期"),
    FORBIDDEN(403, "暂无相关操作权限"),
    VALIDATE_FAILED(404, "找不到与请求匹配的 HTTP 资源"),
    MEHTODERROR(405,"HTTP请求类型不合法"),
    REQUESTERROR(406,"HTTP请求不合法,请求参数可能被篡改"),
    EXPIREERROR(407,"该URL已经失效"),

    EXCEL_IMPORT_FAIL(10001,"数据导入失败"),
    DATA_ADD_FAIL(10002,"数据添加失败"),
    DATA_UPDATE_FAIL(10003,"数据更新失败"),
    DATA_DELETE_FAIL(10004,"数据删除失败");

    private long code;
    private String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
