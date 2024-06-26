package com.dao;

import com.entity.ShijianrenshuEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.ShijianrenshuView;

/**
 * 任务 Dao 接口
 *
 * @author 
 */
public interface ShijianrenshuDao extends BaseMapper<ShijianrenshuEntity> {

   List<ShijianrenshuView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}
