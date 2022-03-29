package com.atguigu.eduorder.client;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.vo.OrderCourseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface EduClient {

    @GetMapping("/eduservice/course/getOrderCourseInfo/{courseId}")
    public OrderCourseInfo getOrderCourseInfo(@PathVariable(value = "courseId") String courseId);

}
