package com.atguigu.educomment.client;

import com.atguigu.commonutils.vo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name="service-ucenter",fallback = UcenterClientImpl.class)
@Component
public interface UcenterClient {
    //根据用户id获取用户信息
    @GetMapping("/educenter/member/getInfoUc/{id}")
    public UcenterMemberVo getInfo(@PathVariable("id") String id);
}
