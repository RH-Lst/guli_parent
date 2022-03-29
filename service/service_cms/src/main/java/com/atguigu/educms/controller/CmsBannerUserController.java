package com.atguigu.educms.controller;

import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author RH
 * @since 2022-03-20
 */
@RestController
@RequestMapping("/educms/Userbanner")
@CrossOrigin
public class CmsBannerUserController {

    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation(value = "获取首页banner")
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = crmBannerService.getAllBanner();
        return R.ok().data("bannerList", list);
    }


}

