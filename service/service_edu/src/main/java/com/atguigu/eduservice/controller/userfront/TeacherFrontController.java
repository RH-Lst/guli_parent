package com.atguigu.eduservice.controller.userfront;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("eduservice/teacherF")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    @GetMapping("getTeacherByPageInFront/{limit}/{page}")
    public R getTeacherByPageInFront(@PathVariable long limit, @PathVariable long page){

        Page<EduTeacher> pageParam = new Page<EduTeacher>(page, limit);

        Map<String,Object> map = eduTeacherService.getTeacherByPageInFront(pageParam);;

        return R.ok().data(map);
    }

    @GetMapping("getTeacherCourse/{id}")
    public R getTeacherCourse(@PathVariable String id){

        EduTeacher eduTeacher = eduTeacherService.getById(id);

        QueryWrapper<EduCourse> wrapper =new QueryWrapper<>();

        wrapper.eq("teacher_id", id);

        List<EduCourse> courseList = eduCourseService.list(wrapper);

        return R.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }

}
