package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    @DeleteMapping("/eduvod/video/deleteAliyunVideo/{id}")
    public R deleteAliyunVideo(@PathVariable("id") String id);

    @DeleteMapping("/eduvod/video/deleteMoreAliyunVideo")
    public R deleteMoreAliyunVideo(@RequestParam("videolist") List<String> videolist);

}
