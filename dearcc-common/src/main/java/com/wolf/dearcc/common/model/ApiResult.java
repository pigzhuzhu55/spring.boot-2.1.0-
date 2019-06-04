package com.wolf.dearcc.common.model;


import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class ApiResult <T>{
    public ApiResult(){

    }
    private  int code;
    private T data;
    private String message;

    public static ApiResult Success(){
        return new ApiResult(){};
    }

    public static ApiResult Success(Object data)
    {
        ApiResult ar = new ApiResult();
        ar.setData(data);
        return ar;
    }
    public static ApiCntResult Success(long cnt, Object data)
    {
        ApiCntResult ar = new ApiCntResult();
        ar.setCnt(cnt);
        ar.setData(data);
        return ar;
    }
    public static ApiResult Success(String msg, Object data)
    {
        ApiResult ar = new ApiResult();
        ar.setMessage(msg);
        ar.setData(data);
        return ar;
    }

    public static ApiResult Success(String msg)
    {
        ApiResult ar = new ApiResult();
        ar.setMessage(msg);
        return ar;
    }
    public static ApiResult Fail()
    {
        return Fail("");
    }
    public static ApiResult Fail(String msg)
    {
        ApiResult ar = new ApiResult();
        ar.setCode(1);
        ar.setMessage(msg);
        return ar;
    }

    @Override
    public String toString()
    {
        return JSON.toJSONString(this);
    }
}
