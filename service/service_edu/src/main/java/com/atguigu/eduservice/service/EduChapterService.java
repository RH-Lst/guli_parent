package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author RH
 * @since 2022-03-04
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterAndVideo(String courseid);

    Boolean deleteChapter(String courseid);

    //删除课程时调用，删除小节用
    void deleteChapterById(String id);
}
