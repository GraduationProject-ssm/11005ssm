package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.WupinjinchuEntity;
import com.entity.view.WupinjinchuView;

import com.service.WupinjinchuService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;


/**
 * 物品进出
 * 后端接口
 * @author 
 * @email 
 * @date 2021-03-10 10:20:19
 */
@RestController
@RequestMapping("/wupinjinchu")
public class WupinjinchuController {
    @Autowired
    private WupinjinchuService wupinjinchuService;
    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,WupinjinchuEntity wupinjinchu, HttpServletRequest request){

        EntityWrapper<WupinjinchuEntity> ew = new EntityWrapper<WupinjinchuEntity>();
    	PageUtils page = wupinjinchuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, wupinjinchu), params), params));
		request.setAttribute("data", page);
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,WupinjinchuEntity wupinjinchu, HttpServletRequest request){
        EntityWrapper<WupinjinchuEntity> ew = new EntityWrapper<WupinjinchuEntity>();
    	PageUtils page = wupinjinchuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, wupinjinchu), params), params));
		request.setAttribute("data", page);
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( WupinjinchuEntity wupinjinchu){
       	EntityWrapper<WupinjinchuEntity> ew = new EntityWrapper<WupinjinchuEntity>();
      	ew.allEq(MPUtil.allEQMapPre( wupinjinchu, "wupinjinchu")); 
        return R.ok().put("data", wupinjinchuService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(WupinjinchuEntity wupinjinchu){
        EntityWrapper< WupinjinchuEntity> ew = new EntityWrapper< WupinjinchuEntity>();
 		ew.allEq(MPUtil.allEQMapPre( wupinjinchu, "wupinjinchu")); 
		WupinjinchuView wupinjinchuView =  wupinjinchuService.selectView(ew);
		return R.ok("查询物品进出成功").put("data", wupinjinchuView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        WupinjinchuEntity wupinjinchu = wupinjinchuService.selectById(id);
        return R.ok().put("data", wupinjinchu);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        WupinjinchuEntity wupinjinchu = wupinjinchuService.selectById(id);
        return R.ok().put("data", wupinjinchu);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WupinjinchuEntity wupinjinchu, HttpServletRequest request){
    	wupinjinchu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(wupinjinchu);

        wupinjinchuService.insert(wupinjinchu);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody WupinjinchuEntity wupinjinchu, HttpServletRequest request){
    	wupinjinchu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(wupinjinchu);

        wupinjinchuService.insert(wupinjinchu);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody WupinjinchuEntity wupinjinchu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(wupinjinchu);
        wupinjinchuService.updateById(wupinjinchu);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        wupinjinchuService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<WupinjinchuEntity> wrapper = new EntityWrapper<WupinjinchuEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}


		int count = wupinjinchuService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	
	


}
