package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R deleteAliyunVideo(String id) {
        return R.error().message("time out");

    }

    @Override
    public R deleteMoreAliyunVideo(List<String> videolist) {
        return R.error().message("time out");

    }
}
