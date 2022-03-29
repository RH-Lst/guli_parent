package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author RH
 * @since 2022-01-18
 */
public interface EduTeacherService extends IService<EduTeacher> {


    Map<String,Object> getTeacherByPageInFront(Page<EduTeacher> pageParam);
}
