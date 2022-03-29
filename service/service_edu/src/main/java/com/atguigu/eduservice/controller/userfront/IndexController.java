package com.atguigu.eduservice.controller.userfront;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.eduservice.service.impl.EduCourseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/index")
@CrossOrigin
public class IndexController {

    @Autowired
    private EduCourseServiceImpl eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询八条最热门的课程，四条讲师记录
     * bug:mp的排序有问题，需要手动写mapper
     * @return
     */
    @GetMapping("index")
    public R index(){

        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();

        eduCourseQueryWrapper.orderByDesc("id");

        eduCourseQueryWrapper.last("limit 8");

        List<EduCourse> eduCourseList = eduCourseService.list(eduCourseQueryWrapper);

        QueryWrapper<EduTeacher> eduTeacherQueryWrapper = new QueryWrapper<>();

        eduTeacherQueryWrapper.orderByDesc("id");

        eduTeacherQueryWrapper.last("limit 4");

        List<EduTeacher> eduTeacherList = eduTeacherService.list(eduTeacherQueryWrapper);

        return R.ok().data("CourseList",eduCourseList).data("TeacherList",eduTeacherList);
    }

}
