package com.flameking.lottery.rpc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flameking.lottery.common.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.flameking.lottery.rpc.entity.Activity;
import com.flameking.lottery.rpc.service.IActivityService;
import com.flameking.lottery.rpc.model.so.ActivityQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 活动配置 Controller 类
 *
 * @author WangWei
 * @dateTime 2023-06-19 18:22:03
 */
@RestController
@RequestMapping("/api/activity")
@Validated
public class ActivityController {

    @Autowired
    private IActivityService activityService;

    /**
     * 查询活动配置分页
     *
     * @param query
     */
    @PostMapping("/page")
    public CommonResult<IPage<Activity>> getPage(@Valid @RequestBody ActivityQuery query) {
        return CommonResult.success(activityService.getPage(query));
    }

    /**
     * 添加活动配置记录
     *
     * @param activity 实体对象
     */
    @PostMapping("/create")
    public CommonResult<Long> insert(@Valid @RequestBody Activity activity) {
        return CommonResult.success(activityService.create(activity));
    }

    /**
     * 修改活动配置记录
     *
     * @param activity 实体对象
     */
    @PutMapping("/update")
    public CommonResult update(@Valid @RequestBody Activity activity) {
        return CommonResult.success(activityService.update(activity));
    }

    /**
     * 删除活动配置记录
     *
     * @param id 记录ID
     */
    @DeleteMapping("/del")
    public CommonResult del(@NotBlank @RequestParam("id") String id) {
        return CommonResult.success(activityService.del(id));
    }

    /**
     * 查询活动配置记录详情
     *
     * @param id 记录ID
     */
    @GetMapping("/detail")
    public CommonResult<Activity> detail(@NotBlank @RequestParam("id") String id) {
        return CommonResult.success(activityService.findById(id));
    }

}

