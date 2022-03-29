package com.atguigu.eduorder.client;

import com.atguigu.commonutils.vo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    //根据用户id获取用户信息
    @GetMapping("/educenter/member/getInfoUc/{id}")
    public UcenterMemberVo getInfo(@PathVariable("id") String id);
}
