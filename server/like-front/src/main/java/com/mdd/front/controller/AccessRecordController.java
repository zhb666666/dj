package com.mdd.front.controller;

import com.mdd.common.aop.NotLogin;
import com.mdd.common.core.AjaxResult;
import com.mdd.front.service.IAccessRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/accessRecord")
@Api(value = "访问记录")
public class AccessRecordController {

    @Resource
    IAccessRecordService iAccessRecordService;

    @NotLogin
    @PostMapping("add")
    @ApiOperation(value = "添加访问记录")
    public AjaxResult addRecord(HttpServletRequest request) {
        String xffHeader = request.getHeader("X-Forwarded-For");
        if (xffHeader == null){
            xffHeader = request.getRemoteAddr();
        }
        iAccessRecordService.add(xffHeader);
        return AjaxResult.success("成功");
    }
}
