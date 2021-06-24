package com.wzp.nflj.enums;

/**
 * @Author: zp.wei
 * @DATE: 2020/9/1 10:31
 */
public enum ResultCodeEnum {

    RESULT_SUCCESS(true, 0, "请求成功"),
    BUSINESS_FAIL(false, 1, "业务处理失败"),
    FORBIDDEN(false, 400, "授权失败"),
    UNAUTHORIZED(false, 401, "未经授权的请求"),
    INVALID_TOKEN(false, 402, "无效的token"),
    ACCESS_DENIED(false, 403, "无权访问"),

    SYSTEM_ERROR(false, 500, "系统错误"),

    JSON_PARSE_ERROR(false, 10001, "json解析异常"),
    PARAM_ERROR(false, 10002, "请求参数不正确"),
    LACK_NEEDS_PARAM(false, 10003, "缺少必要的参数"),
    ERROR_USERNAME_OR_PASSWORD(false, 10004, "账户名或密码错误"),
    USER_NOT_ENABLE(false, 10005, "用户未激活"),
    HAS_USER(false, 10006, "该用户名已存在"),
    OLD_PASSWORD_ERROR(false, 10007, "旧密码错误，请重新输入"),
    NEW_PASSWORD_HAS_SAME(false, 10008, "新旧密码相同，请重新输入新密码"),
    AUTHORITY_HAS_USE(false, 10009, "该权限有角色在使用，不能删除"),

    EXCEL_DOWNLAND_FAIL(false, 20001, "excel下载失败，请重新下载"),
    EXCEL_IMPORT_SUCCESS(true, 20002, "excel导入成功"),
    EXCEL_IMPORT_FAIL(true, 20002, "excel导入失败"),
    EXCEL_EXPORT_SUCCESS(true, 20002, "excel导出成功"),
    EXCEL_EXPORT_FAIL(true, 20002, "excel导出失败"),

    FILE_NOT_EXISTS(false, 30001, "文件不存在"),
    FILE_UPLOAD_ERROR(false, 30002, "文件上传错误"),
    CHUNK_EXISTS(false, 30003, "分片已存在"),
    CHUNK_NOT_EXISTS(false, 30004, "分片不存在"),
    CHUNK_UPLOAD_ERROR(false, 30005, "分片上传错误"),
    CHUNK_MERGE_FAIL(false, 30006, "分片合并失败"),
    UPLOADING(false, 30007, "上传中..."),
    UPLOAD_FAIL(false, 30008, "上传失败");

    private Boolean success;

    private Integer code;

    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }


}
