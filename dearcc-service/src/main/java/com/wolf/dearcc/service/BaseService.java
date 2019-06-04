package com.wolf.dearcc.service;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;

@Service
public interface BaseService<T,ID extends Serializable>{
    T insert(T entity);

    T insertSelective(T entity);

    int updateByExample(T entity, Example example);

    int updateByExampleSelective(T entity,Example example);

    int delete(T entity);

    int deleteByPrimaryKey(ID id);

    int updateByPrimaryKey(T entity) ;

    int updateByPrimaryKeySelective(T entity);

    boolean existsWithPrimaryKey(ID id);

    T queryOneByExample(Example example);

    T queryOneByPrimaryKey(ID id);

    List<T> queryByExample(Example example);

    int selectCountByExample(Example example);



    /**
     * 不满足条件的才更新
     * 不更新null字段
     * @param entity
     * @param example
     * @return
     */
    int updateOneByPrimaryKeyNotAtExampleSelective(T entity,Example example);
    /**
     * 不满足条件的才插入
     * 不更新null字段
     * @param entity
     * @param example
     * @return
     */
    int insertOneNotAtExampleSelective(T entity, Example example);

    /**
     * 自定义查询条件，分页查询
     * @param example
     * @param page
     * @param rows
     * @return
     */
    PageInfo<T> queryPageListByExample(Example example, Integer page, Integer rows) ;

    /**
     *批量插入数据
     * @param list
     * @return
     */
    int insertList(List<T> list);
}
