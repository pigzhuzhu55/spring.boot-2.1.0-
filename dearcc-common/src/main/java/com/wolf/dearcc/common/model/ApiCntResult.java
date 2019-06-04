package com.wolf.dearcc.common.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class ApiCntResult<T> extends ApiResult {
    public ApiCntResult() {
    }

    private long cnt;

    @Override
    public String toString()
    {
        return JSON.toJSONString(this);
    }
}
