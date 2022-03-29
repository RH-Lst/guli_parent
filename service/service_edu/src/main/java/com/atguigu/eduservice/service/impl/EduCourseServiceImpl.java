package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.entity.vo.front.CourseQueryVo;
import com.atguigu.eduservice.entity.vo.front.CourseWebVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.ExceptionHandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author RH
 * @since 2022-03-04
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {


    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    EduVideoService eduVideoService;

    @Autowired
    EduChapterService eduChapterService;

    @Override
    @Transactional
    public String addCourse(CourseInfo courseInfo) {

        EduCourse eduCourse =new EduCourse();

        BeanUtils.copyProperties(courseInfo, eduCourse);

        int insert = baseMapper.insert(eduCourse);

        if (insert == 0){
            throw new GuliException(20001,"课程添加失败");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();

        BeanUtils.copyProperties(courseInfo, eduCourseDescription);

        eduCourseDescription.setId(eduCourse.getId());

        eduCourseDescriptionService.save(eduCourseDescription);

        return eduCourse.getId();
    }

    @Override
    public CourseInfo getCourseInfo(String courseId) {

        //创建返回对象
        CourseInfo courseInfo = new CourseInfo();
        //根据课程id获取eduCourse
        EduCourse eduCourse = baseMapper.selectById(courseId);
        //根据课程id获取description
        EduCourseDescription description = eduCourseDescriptionService.getById(courseId);
        //将内容存入courseInfo
        BeanUtils.copyProperties(eduCourse,courseInfo);

        BeanUtils.copyProperties(description,courseInfo);

        return courseInfo;
    }

    @Override
    public void updateCourseInfo(CourseInfo courseInfo) {

        EduCourse eduCourse = new EduCourse();

        EduCourseDescription eduCourseDescription = new EduCourseDescription();

        BeanUtils.copyProperties(courseInfo,eduCourse);

        BeanUtils.copyProperties(courseInfo,eduCourseDescription);

        int i = baseMapper.updateById(eduCourse);

        if (i == 0){
            throw new GuliException(20001,"更新课程信息失败");
        }

        boolean b = eduCourseDescriptionService.updateById(eduCourseDescription);

        if (!b){
            throw new GuliException(20001,"更新课程描述失败");
        }
    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {

        CoursePublishVo coursePublishVo = baseMapper.selectCoursePublishVoById(id);

        return coursePublishVo;
    }

    @Override
    public void pageTeacherCondition(Page<EduCourse> page, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        if (courseQuery == null){
            baseMapper.selectPage(page, queryWrapper);
            return;
        }
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }
        baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void deleteCourseById(String id) {
        //1.删除小节
        eduVideoService.deleteVideoById(id);
        //2.删除章节
        eduChapterService.deleteChapterById(id);
        //3.删除描述
        eduCourseDescriptionService.removeById(id);
        //4.删除课程
        int i = baseMapper.deleteById(id);

        if (i == 0){
            throw new GuliException(20001,"删除课程失败");
        }
    }

    @Override
    public Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseQueryVo courseQuery) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id",
                    courseQuery.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
            queryWrapper.eq("subject_id", courseQuery.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseQuery.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseQuery.getPriceSort())) {
            queryWrapper.orderByDesc("price");}

        baseMapper.selectPage(pageParam, queryWrapper);
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo getCourseDetilsInfo(String id) {
        this.updatePageViewCount(id);
        return baseMapper.getCourseDetilsInfo(id);
    }

    @Override
    public void updatePageViewCount(String id) {
        EduCourse course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
    }
}
