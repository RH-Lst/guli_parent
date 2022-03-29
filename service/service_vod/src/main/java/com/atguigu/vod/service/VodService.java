package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadAliVideo(MultipartFile file);

    void deleteAliyunVideo(String id);

    void deleteMoreAliyunVideo(List videolist);
}
