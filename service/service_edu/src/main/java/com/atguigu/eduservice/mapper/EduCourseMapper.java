package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.front.CourseWebVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author RH
 * @since 2022-03-04
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo selectCoursePublishVoById(String id);

    CourseWebVo getCourseDetilsInfo(String id);
}