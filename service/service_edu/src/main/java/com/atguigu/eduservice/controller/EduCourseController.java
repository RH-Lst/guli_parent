package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.commonutils.vo.OrderCourseInfo;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author RH
 * @since 2022-03-04
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    EduCourseService eduCourseService;

    @PostMapping("addCourse")
    public R addCourse(@RequestBody CourseInfo courseInfo){

        String id = eduCourseService.addCourse(courseInfo);

        return R.ok().data("courserId",id);
    }

    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){

        CourseInfo courseInfo = eduCourseService.getCourseInfo(courseId);

        return R.ok().data("courseinfo",courseInfo);
    }

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfo courseInfo){

        eduCourseService.updateCourseInfo(courseInfo);
        return R.ok();
    }

    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){

        CoursePublishVo coursePublishVo = eduCourseService.getPublishCourseInfo(id);

        return R.ok().data("coursePublish",coursePublishVo);
    }

    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){

        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal"); //课程已发布状态Normal

        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    @GetMapping("pageCourse/{current}/{limit}")
    public R pageCourse(@PathVariable long current,
                        @PathVariable long limit){

        Page<EduCourse> eduCoursePage = new Page<>(current,limit);
        eduCourseService.page(eduCoursePage,null);
        long total = eduCoursePage.getTotal();
        List<EduCourse> records = eduCoursePage.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }

    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(
            @PathVariable Long current,
            @PathVariable Long limit,
            @RequestBody(required = false) CourseQuery courseQuery){
        Page<EduCourse> page = new Page<>(current, limit);
        eduCourseService.pageTeacherCondition(page, courseQuery);
        List<EduCourse> records = page.getRecords();
        long total = page.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @DeleteMapping("{courseId}")
    public R deleteCourseById(@PathVariable String courseId){

        eduCourseService.deleteCourseById(courseId);

        return R.ok();
    }

    @GetMapping("getOrderCourseInfo/{courseid}")
    public OrderCourseInfo getOrderCourseInfo(@PathVariable String courseid){

        CourseInfo courseInfo = eduCourseService.getCourseInfo(courseid);

        OrderCourseInfo orderCourseInfo = new OrderCourseInfo();

        BeanUtils.copyProperties(courseInfo,orderCourseInfo);

        return orderCourseInfo;
    };
}

