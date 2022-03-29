package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author RH
 * @since 2022-03-02
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("addSubject")
    @ApiOperation(value = "Excel批量导入")
    public R addSubject(MultipartFile file){

        eduSubjectService.saveSubject(file,eduSubjectService);

        return R.ok();
    }

    @GetMapping("getAllsubject")
    public R getAllsubject(){

        List<OneSubject> list =eduSubjectService.getAllsubject();

        return R.ok().data("list",list);
    }

}

