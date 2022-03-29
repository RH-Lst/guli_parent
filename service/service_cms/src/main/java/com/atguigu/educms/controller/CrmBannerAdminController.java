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
@RequestMapping("/educms/Adminbanner")
@CrossOrigin
public class CrmBannerAdminController {
    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation(value = "后台分页查询banner")
    @GetMapping("getBannerByPage/{page}/{limit}")
    public R getBannerByPage(@PathVariable long page, @PathVariable long limit){

        Page<CrmBanner> pageTeacher=new Page<>(page,limit);
        crmBannerService.page(pageTeacher,null);
        long total=pageTeacher.getTotal();
        List<CrmBanner> records=pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){

        crmBannerService.save(crmBanner);

        return R.ok();
    }

    @ApiOperation(value = "根据id修改banner")
    @PostMapping("updateBannerById")
    public R updateBannerById(@RequestBody CrmBanner banner){

        crmBannerService.updateById(banner);

        return R.ok();
    }

    @ApiOperation(value = "根据id删除banner")
    @DeleteMapping("deleteBannerByid/{id}")
    public R deleteBannerByid(@PathVariable String id){

        crmBannerService.removeById(id);

        return R.ok();
    }
}

