package com.atguigu.educms.service;

import com.atguigu.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author RH
 * @since 2022-03-20
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 前台用户查询所有banner
     * @return
     */
    List<CrmBanner> getAllBanner();
}
