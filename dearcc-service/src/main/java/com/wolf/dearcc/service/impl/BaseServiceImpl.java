package com.wolf.dearcc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wolf.dearcc.dao.MyMapper;
import com.wolf.dearcc.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceImpl<T,ID extends Serializable> implements BaseService<T,ID> {
    @Autowired
    private MyMapper<T> tMyMapper;

    public T insert(T entity) {
        tMyMapper.insert(entity);
        return entity;
    }

    public T insertSelective(T entity){
        tMyMapper.insertSelective(entity);
        return entity;
    }

    public int updateByExample(T entity, Example example) {
        return tMyMapper.updateByExample(entity,example);
    }

    public int updateByExampleSelective(T entity, Example example) {
        return tMyMapper.updateByExampleSelective(entity,example);
    }

    public int delete(T entity) {
        return tMyMapper.delete(entity);
    }

    public int deleteByPrimaryKey(ID id) {
        return tMyMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKey(T entity) {
        return tMyMapper.updateByPrimaryKey(entity);
    }

    public int updateByPrimaryKeySelective(T entity) {
        return tMyMapper.updateByPrimaryKeySelective(entity);
    }

    public boolean existsWithPrimaryKey(ID id)
    {
        return tMyMapper.existsWithPrimaryKey(id);
    }

    public T queryOneByExample(Example example)
    {
        return tMyMapper.selectOneByExample(example);
    }

    public T queryOneByPrimaryKey(ID id){
        return tMyMapper.selectByPrimaryKey(id);
    }

    public List<T> queryByExample(Example example)
    {
        return tMyMapper.selectByExample(example);
    }

    public int selectCountByExample(Example example){
        return tMyMapper.selectCountByExample(example);
    }



    /**
     * 不满足条件的才更新
     * 不更新null字段
     * @param entity
     * @param example
     * @return
     */
    public int updateOneByPrimaryKeyNotAtExampleSelective(T entity, Example example)
    {
        int cnt = tMyMapper.selectCountByExample(example);
        if( cnt > 0)
        {
            return 0;
        }
        else
        {
            return tMyMapper.updateByPrimaryKeySelective(entity);
        }
    }
    /**
     * 不满足条件的才插入
     * 不更新null字段
     * @param entity
     * @param example
     * @return
     */
    public int insertOneNotAtExampleSelective(T entity, Example example)
    {
        int cnt = tMyMapper.selectCountByExample(example);
        if( cnt > 0)
        {
            return 0;
        }
        else
        {
            return tMyMapper.insertSelective(entity);
        }
    }

    /**
     * 自定义查询条件，分页查询
     * @param example
     * @param page
     * @param rows
     * @return
     */
    public PageInfo<T> queryPageListByExample(Example example, Integer page, Integer rows) {
        PageHelper.startPage(page, rows, true);// 设置分页参数
        // 查询数据
        List<T> lists = tMyMapper.selectByExample(example);
        return new PageInfo<T>(lists);
    }

    /**
     *批量插入数据
     * @param list
     * @return
     */
    public int insertList(List<T> list)
    {
        return tMyMapper.insertList(list);
    }

}
