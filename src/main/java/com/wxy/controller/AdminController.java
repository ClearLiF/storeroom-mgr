package com.wxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wxy.config.response.QueryResponseResult;
import com.wxy.config.response.ResponseResult;
import com.wxy.dao.SrRoleDao;
import com.wxy.model.SrAdmin;
import com.wxy.model.SrRole;
import com.wxy.model.request.*;
import com.wxy.model.response.SrAdminVO;
import com.wxy.model.response.SrRoleVO;
import com.wxy.model.response.SrSubAdminVO;
import com.wxy.service.AdminService;
import com.wxy.utils.JwtUtil;
import com.wxy.utils.StreamUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : AdminController
 * @packageName : com.wxy.controller
 * @description : 一般类
 * @date : 2021-03-20 12:28
 **/
@RestController
@CrossOrigin("*")
@Slf4j
@Api(tags = "管理员模块")
public class AdminController {
    @Resource
    private AdminService adminService;

    @Resource
    private HttpServletRequest request;


    @ApiOperation(value = "添加管理员[临时]", notes = "添加一个管理员两种类型")
    @ApiResponses({@ApiResponse(code = 10000, message = "调用成功")
            , @ApiResponse(code = 11111, message = "调用失败")})
    @PostMapping("adminInfoInit")
    public ResponseResult addAdmin(@RequestBody SrAdminDTO srAdminDTO) {
        return adminService.initAdmin(srAdminDTO);
    }


    @ApiOperation(value = "修改管理员信息(只能修改自己 不修改的可以不填)")
    @PutMapping("modifyAdminInfo")
    public ResponseResult modifyAdminInfo(@RequestBody SrAdminModifyDTO adminModifyDTO) {
        SrAdmin admin = JwtUtil.getAdmin(request);
        log.info(admin.toString());
        return adminService.modifyAdminInfo(adminModifyDTO, admin.getId());
    }

    @ApiOperation(value = "修改下级管理员信息")
    @PutMapping("modifySubAdminInfo")
    public ResponseResult modifySubAdminInfo(@RequestBody SrSubAdminModifyDTO adminModifyDTO){
        return adminService.modifySubAdminInfo(adminModifyDTO);
    }

    @ApiOperation(value = "修改管理员角色")
    @Secured("ROLE_SUP_ADMIN")
    @PutMapping("modifyAdminRole")
    public ResponseResult modifyAdminRole(@RequestBody SrAdminModifyRoleDTO adminModifyRoleDTO) {
        return adminService.modifyAdminRole(adminModifyRoleDTO);
    }


    @ApiOperation(value = "获取所有管理员角色")
    @GetMapping("allAdminRole")
    public QueryResponseResult<SrRoleVO> getAllAdminRole() {
      return   adminService.getAllAdminRole();
    }


    @ApiOperation(value = "根据条件查询管理员列表")
    @Secured("ROLE_SUP_ADMIN")
    @PostMapping("adminListByType")
    public QueryResponseResult<SrAdminVO> getAdminListByType(@RequestBody SearchAdminListDTO adminListDTO){
        return   adminService.getAdminListByType(adminListDTO);
    }

    @ApiOperation(value = "获取所有仓库管理员 用于绑定仓库")
    @Secured("ROLE_SUP_ADMIN")
    @GetMapping("storeroomAdminList")
    public QueryResponseResult<SrSubAdminVO> getSubAdminList(){

        return adminService.getSubAdminList();
    }

    @ApiOperation(value = "查询个人信息")
    @GetMapping("adminInfo")
    public QueryResponseResult<SrAdminVO> getAdminInfo(){

        SrAdmin admin = JwtUtil.getAdmin(request);
        return adminService.getAdminInfo(admin.getId());
    }

    @ApiOperation(value = "删除管理员")
    @Secured("ROLE_SUP_ADMIN")
    @DeleteMapping("admin/{adminId}")
    public ResponseResult deleteAdmin(@PathVariable Long adminId){

        return adminService.deleteAdmin(adminId);
    }




}
