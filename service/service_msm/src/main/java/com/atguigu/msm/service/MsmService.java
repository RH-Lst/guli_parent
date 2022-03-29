package com.atguigu.msm.service;


import java.util.Map;

public interface MsmService {
    boolean sendmsg(String phonenum, String template, Map<String, Object> param);
}
