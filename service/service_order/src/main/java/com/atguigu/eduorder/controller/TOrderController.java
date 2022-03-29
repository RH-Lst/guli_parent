package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.entity.vo.Ids;
import com.atguigu.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author RH
 * @since 2022-03-25
 */
@RestController
@RequestMapping("/eduorder/torder")
@CrossOrigin
public class TOrderController {

    @Autowired
    private TOrderService orderService;

    //根据课程id和用户id创建订单，返回订单id
    @PostMapping("createOrder/{courseId}")
    public R save(@PathVariable String courseId, HttpServletRequest request) {
        String orderId = orderService.saveOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderId);
    }

    @GetMapping("getOrder/{orderId}")
    public R getOrder(@PathVariable String orderId) {
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }

    @PostMapping("isBuyCheck")
    public R isBuyCheck(@RequestBody Ids ids){

        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();

        wrapper.eq("course_id",ids.getCid());
        wrapper.eq("member_id",ids.getMid());
        wrapper.eq("status",1);

        int count = orderService.count(wrapper);
        System.out.println(count);
        boolean flag = false;
        if (count > 0){
            flag = true;
            return R.ok().data("flag",flag);
        }else {
            return R.ok().data("flag",flag);
        }


    }
}

