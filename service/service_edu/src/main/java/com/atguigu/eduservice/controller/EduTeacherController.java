package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author RH
 * @since 2022-01-18
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllteacher(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeteacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageTeacher(
            @ApiParam(name = "current",value = "当前页码",required = true)
            @PathVariable long current,
            @ApiParam(name = "limit",value = "每页显示数据量",required = true)
            @PathVariable long limit){

        Page<EduTeacher> pageTeacher=new Page<>(current,limit);
        eduTeacherService.page(pageTeacher,null);
        long total=pageTeacher.getTotal();
        List<EduTeacher> records=pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "分页条件查询讲师")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(
            @ApiParam(name = "current",value = "当前页码",required = true)
            @PathVariable long current,
            @ApiParam(name = "limit",value = "每页显示数据量",required = true)
            @PathVariable long limit,
            @ApiParam(name = "teacherQuery",value = "查询对象")
            @RequestBody(required = false) TeacherQuery teacherQuery
    ){

        Page<EduTeacher> pageTeacher=new Page<>(current,limit);

        QueryWrapper<EduTeacher> queryWrapper=new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level) ) {
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }

        eduTeacherService.page(pageTeacher,queryWrapper);
        long total=pageTeacher.getTotal();
        List<EduTeacher> records=pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);

    }

    @ApiOperation(value = "新增教师")
    @PostMapping("addTeacher")
    public R addTeacher(
            @ApiParam(name = "eduTeacher",value = "新增教师",required = true)
            @RequestBody EduTeacher eduTeacher){

        boolean result=eduTeacherService.save(eduTeacher);
        if (result){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "根据id查询教师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(
            @ApiParam(name = "id",value = "教师id",required = true)
            @PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    @ApiOperation(value = "根据id修改教师")
    @PostMapping("updateTeacher")
    public R updateTeacher(
            @ApiParam(name = "eduTeacher",value = "修改教师信息",required = true)
            @RequestBody EduTeacher eduTeacher){

        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag){
           return R.ok();
        }else {
           return R.error();
        }
    }
}

