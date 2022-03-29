package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.event.AnalysisFinishEvent;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.ExcelSubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.ExceptionHandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    public EduSubjectService subjectService;

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public SubjectExcelListener() {}



    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {

        if (excelSubjectData == null){
            throw new GuliException(20001,"文件数据为空");
        }


        EduSubject exitone = this.exitOneSubject(subjectService, excelSubjectData.getOneSubjectName());

        //插入一级分类
        if (exitone == null){
            exitone = new EduSubject();
            exitone.setParentId("0");
            exitone.setTitle(excelSubjectData.getOneSubjectName());
            subjectService.save(exitone);
        }

        String pid = exitone.getId();

        EduSubject exitTwo = this.exitTwoSubject(subjectService, excelSubjectData.getTwoSubjectName(),pid);

        //插入二级分类
        if (exitTwo == null){
            exitTwo = new EduSubject();
            exitTwo.setTitle(excelSubjectData.getTwoSubjectName());
            exitTwo.setParentId(exitone.getId());
            subjectService.save(exitTwo);

        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }


    //查询一级分类
    private EduSubject exitOneSubject(EduSubjectService subjectService,String name){

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject one = subjectService.getOne(wrapper);

        return one;
    }

    //查询二级分类
    private EduSubject exitTwoSubject(EduSubjectService subjectService,String name,String pid){

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject Two = subjectService.getOne(wrapper);

        return Two;
    }
}
