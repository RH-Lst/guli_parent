package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.AliyunVodSDKUtils;
import com.atguigu.vod.utils.ConstantPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {


    @Autowired
    private VodService vodService;

    @PostMapping("uploadAliVideo")
    public R uploadAliVideo(MultipartFile file){

        String vid = vodService.uploadAliVideo(file);

        return R.ok().data("vdeioid",vid);
    }

    @DeleteMapping("deleteAliyunVideo/{id}")
    public R deleteAliyunVideo(@PathVariable String id){

        vodService.deleteAliyunVideo(id);

        return R.ok().message("视频删除成功");
    }

    @DeleteMapping("deleteMoreAliyunVideo")
    public R deleteMoreAliyunVideo(@RequestParam("videolist") List<String> videolist){

        vodService.deleteMoreAliyunVideo(videolist);

        return R.ok();
    }

    @GetMapping("getVideoPlayAuth/{videoId}")
    public R getVideoPlayAuth(@PathVariable("videoId") String videoId) throws
            Exception {
        //获取阿里云存储相关常量
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        //初始化
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId,
                accessKeySecret);
        //请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        //响应
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        //得到播放凭证
        String playAuth = response.getPlayAuth();
        //返回结果
        return R.ok().message("获取凭证成功").data("playAuth", playAuth);}
}
