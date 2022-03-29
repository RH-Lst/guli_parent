package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author RH
 * @since 2022-03-04
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    EduChapterService eduChapterService;

    @GetMapping("getChapterAndVideo/{courseid}")
    public R getChapterAndVideo(@PathVariable String courseid){

        List<ChapterVo> chapterVos = eduChapterService.getChapterAndVideo(courseid);

        return R.ok().data("list",chapterVos);
    }

    //新增课程章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){

        eduChapterService.save(eduChapter);

        return R.ok();
    }
    //根据章节id查询
    @GetMapping("selectChapterbyId/{chapterid}")
    public R selectChapterbyId(@PathVariable String chapterid){


        EduChapter eduChapter = eduChapterService.getById(chapterid);

        return R.ok().data("eduChapter",eduChapter);
    }

    //修改课程章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){

        eduChapterService.updateById(eduChapter);

        return R.ok();
    }

    //删除课程章节
    @DeleteMapping("{chapterid}")
    public R deleteChapter(@PathVariable String chapterid){

        boolean resulet = eduChapterService.deleteChapter(chapterid);
        if (resulet){
            return R.ok();
        }else {
            return R.error();
        }

    }
}

