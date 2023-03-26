package com.lee.demo.entity.response;

import lombok.Data;

/**
 * 返回结果封装
 */
@Data
public class BaseResult {
    private boolean success;
    private String code;
    private String message;
    private Object data;

    public BaseResult(Boolean success){
        this.success=success;
        this.code=success?"10000":"00000";
        this.message=success?"执行成功":"执行失败";
    }

    public BaseResult(Boolean success,String code,String message){
        this.success=success;
        this.code=code;
        this.message=message;
    }

    public BaseResult(Boolean success,String code,String message,Object data){
        this.success=success;
        this.code=code;
        this.message=message;
        this.data=data;
    }

    public static BaseResult success(){
        return success(null);
    }

    public static BaseResult success(Object data){
        return new BaseResult(true,"1000","执行成功",data);
    }

    public static BaseResult fail(String code,String message){
        return new BaseResult(false,code,message,null);
    }

    public static BaseResult generalFail(){
        return fail("0000","未知错误");
    }
}
