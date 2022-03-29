package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author RH
 * @since 2022-03-04
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){

        eduVideoService.save(eduVideo);

        return R.ok();
    }


    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){

        EduVideo eduVideo = eduVideoService.getById(id);

        String vid = eduVideo.getVideoSourceId();

        if(!StringUtils.isEmpty(vid)){
            vodClient.deleteAliyunVideo(vid);
        }

        eduVideoService.removeById(id);
        return R.ok();
    }

    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){

        eduVideoService.updateById(eduVideo);

        return R.ok();
    }

    @GetMapping("selectVideoById/{videoId}")
    public R selectVideoById(@PathVariable String videoId){

        EduVideo eduVideo = eduVideoService.getById(videoId);

        return R.ok().data("eduVideo",eduVideo);
    }


}

