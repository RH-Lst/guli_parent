package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.entity.vo.front.CourseQueryVo;
import com.atguigu.eduservice.entity.vo.front.CourseWebVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author RH
 * @since 2022-03-04
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourse(CourseInfo courseInfo);

    CourseInfo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfo courseInfo);

    CoursePublishVo getPublishCourseInfo(String id);

    void pageTeacherCondition(Page<EduCourse> page, CourseQuery courseQuery);

    void deleteCourseById(String id);

    Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseQueryVo courseQuery);

    CourseWebVo getCourseDetilsInfo(String id);

    void updatePageViewCount(String id);

}
