package com.atguigu.eduservice.controller.userfront;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.vo.front.CourseQueryVo;
import com.atguigu.eduservice.entity.vo.front.CourseWebVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("eduservice/courseF")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduChapterService eduChapterService;

    @PostMapping(value = "{page}/{limit}")
    public R pageList(
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestBody(required = false) CourseQueryVo courseQuery){
        Page<EduCourse> pageParam = new Page<EduCourse>(page, limit);
        Map<String, Object> map = eduCourseService.pageListWeb(pageParam, courseQuery);
        return R.ok().data(map);
    }

    @GetMapping("getCourseDetilsInfo/{id}")
    public R getCourseDetilsInfo(@PathVariable String id){

        CourseWebVo courseWebVo = eduCourseService.getCourseDetilsInfo(id);

        List<ChapterVo> chapterAndVideo = eduChapterService.getChapterAndVideo(id);
        return R.ok().data("course", courseWebVo).data("chapterVoList", chapterAndVideo);
    }

}
