package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.ExceptionHandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author RH
 * @since 2022-03-04
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterAndVideo(String courseid) {

        //创建最终返回集合
        List<ChapterVo> finalList = new ArrayList<>();

        //获取章节数据
        QueryWrapper chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseid);
        List<EduChapter> eduChapters = baseMapper.selectList(chapterWrapper);

        //获取小结数据
        QueryWrapper VideoWrapper = new QueryWrapper<>();
        VideoWrapper.eq("course_id",courseid);
        List<EduVideo> eduVideos = eduVideoService.list(VideoWrapper);

        //封装章节数据
        for (int i = 0; i < eduChapters.size(); i++) {
            //获取每个EduChapter对象
            EduChapter eduChapter=eduChapters.get(i);
            //创建ChapterVo对象，准备获取值
            ChapterVo chapterVo = new ChapterVo();
            //将eduChapter中值赋给chapterVo
            BeanUtils.copyProperties(eduChapter,chapterVo);

            //finalList.add(chapterVo);

            //封装小结数据
            List<VideoVo> videoVoList = new ArrayList<>();

            for (int j = 0; j < eduVideos.size(); j++) {

                //获取EduVideo对象
                EduVideo eduVideo = eduVideos.get(j);
                //如果eduVideo中的ChapterId与Chapter中的ID相同，将所有章节下的小结存入videoVoList
                //再将videoVoList放入chapterVo中
                if(eduVideo.getChapterId().equals(eduChapter.getId())){

                    VideoVo videoVo = new VideoVo();

                    BeanUtils.copyProperties(eduVideo,videoVo);

                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);

            finalList.add(chapterVo);
        }

        return finalList;
    }

    @Override
    public Boolean deleteChapter(String chapterid) {
        //查询小结表，如果有数据就不删除
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterid);
        int count = eduVideoService.count(wrapper);

        if (count > 0){
            throw new GuliException(20001,"不能删除");
        }else {
            int i = baseMapper.deleteById(chapterid);

            return i>0;
        }
    }

    @Override
    public void deleteChapterById(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
