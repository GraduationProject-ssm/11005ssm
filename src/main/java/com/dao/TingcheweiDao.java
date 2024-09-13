package com.dao;

import com.entity.TingcheweiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.TingcheweiVO;
import com.entity.view.TingcheweiView;


/**
 * 停车位
 * 
 * @author 
 * @email 
 * @date 2021-03-10 10:20:19
 */
public interface TingcheweiDao extends BaseMapper<TingcheweiEntity> {
	
	List<TingcheweiVO> selectListVO(@Param("ew") Wrapper<TingcheweiEntity> wrapper);
	
	TingcheweiVO selectVO(@Param("ew") Wrapper<TingcheweiEntity> wrapper);
	
	List<TingcheweiView> selectListView(@Param("ew") Wrapper<TingcheweiEntity> wrapper);

	List<TingcheweiView> selectListView(Pagination page,@Param("ew") Wrapper<TingcheweiEntity> wrapper);
	
	TingcheweiView selectView(@Param("ew") Wrapper<TingcheweiEntity> wrapper);
	
}