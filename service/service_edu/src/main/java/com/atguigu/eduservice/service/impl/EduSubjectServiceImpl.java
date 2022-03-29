package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.SyncReadListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.ExcelSubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author RH
 * @since 2022-03-02
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {

        try{

            InputStream in =file.getInputStream();

            EasyExcel.read(in, ExcelSubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch (Exception e){

            e.printStackTrace();

        }

    }

    @Override
    public List<OneSubject> getAllsubject() {

        //查询一级分类
        QueryWrapper<EduSubject> onewrapper = new QueryWrapper<>();
        onewrapper.eq("parent_id","0");
        List<EduSubject> oneSubjects = baseMapper.selectList(onewrapper);

        //查询二级分类
        QueryWrapper<EduSubject> twowrapper = new QueryWrapper<>();
        twowrapper.ne("parent_id","0");
        List<EduSubject> twoSubjects = baseMapper.selectList(twowrapper);

        //创建返回集合
        List<OneSubject> finalsubjectlist = new ArrayList<>();

        //封装一级分类
        for (int i = 0; i < oneSubjects.size(); i++) {
            //创建一级分类实体类
            OneSubject oneSubject = new OneSubject();
            //注入属性
            oneSubject.setId(oneSubjects.get(i).getId());
            oneSubject.setTitle(oneSubjects.get(i).getTitle());
            //将一级分类注入返回集合中
            finalsubjectlist.add(oneSubject);

            //创建封装一级分类中的二级分类集合
            List<TwoSubject> Twofinalsubjectlist = new ArrayList<>();
            //封装二级分类
            for (int j = 0; j < twoSubjects.size(); j++) {
                TwoSubject twoSubject =new TwoSubject();
                //判断二级分类是否属于一级分类下
                if (oneSubject.getId().equals(twoSubjects.get(j).getParentId())){
                    twoSubject.setId(twoSubjects.get(j).getId());
                    twoSubject.setTitle(twoSubjects.get(j).getTitle());
                    Twofinalsubjectlist.add(twoSubject);
                }
            }

            oneSubject.setChildren(Twofinalsubjectlist);
        }

        return finalsubjectlist;
    }
}
