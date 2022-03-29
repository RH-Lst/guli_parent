package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author RH
 * @since 2022-03-04
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    //注入vodclient
    @Autowired
    private VodClient vodClient;

    @Override
    public void deleteVideoById(String id) {

        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();

        videoQueryWrapper.eq("course_id",id);
        videoQueryWrapper.select("video_source_id");

        List<EduVideo> list = baseMapper.selectList(videoQueryWrapper);

        List<String> Ids = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            EduVideo eduVideo = list.get(i);
            String videoSourseId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourseId)){
                Ids.add(videoSourseId);
            }

        }

        if (Ids.size() > 0 ){
            vodClient.deleteMoreAliyunVideo(Ids);
        }


        //删除小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
