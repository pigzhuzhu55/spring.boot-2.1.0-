package com.wolf.dearcc.dao;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

@RegisterMapper
public interface MyMapper<T> extends Mapper<T>, InsertListMapper<T> {

}
