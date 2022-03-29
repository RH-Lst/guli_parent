package com.atguigu.educomment.client;

import com.atguigu.commonutils.vo.UcenterMemberVo;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientImpl implements UcenterClient {
    @Override
    public UcenterMemberVo getInfo(String memberId) {
        System.out.println("进入熔断");
        return null;
    }
}
