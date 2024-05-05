package com.fan.wpdogschat.common.user.controller;

import com.fan.wpdogschat.common.user.domain.vo.resp.BadgeResp;
import com.fan.wpdogschat.common.common.domain.vo.resp.ApiResult;
import com.fan.wpdogschat.common.user.domain.vo.req.ModifyNameReq;
import com.fan.wpdogschat.common.common.utils.RequestHolder;
import com.fan.wpdogschat.common.user.domain.vo.req.WearingBadgeReq;
import com.fan.wpdogschat.common.user.domain.vo.resp.UserInfoResp;
import com.fan.wpdogschat.common.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author fan
 * @since 2024-05-03
 */
@RestController
@RequestMapping("/capi/user")
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;
//    @Autowired
//    private IRoleService iRoleService;

    @GetMapping("/userInfo")
    @ApiOperation("获取用户个人信息")
    public ApiResult<UserInfoResp> getUserInfo() {
        return ApiResult.success(userService.getUserInfo(RequestHolder.get().getUid()));
    }

    @PutMapping("/name")
    @ApiOperation("修改用户名")
    public ApiResult<Void> modifyName(@Valid @RequestBody ModifyNameReq req) {
        userService.modifyName(RequestHolder.get().getUid(), req.getName());
        return ApiResult.success();
    }

    @GetMapping("/badges")
    @ApiOperation("可选徽章预览")
    public ApiResult<List<BadgeResp>> badges() {
        return ApiResult.success(userService.badges(RequestHolder.get().getUid()));
    }

    @PutMapping("/badge")
    @ApiOperation("佩戴徽章")
    public ApiResult<Void> wearingBadge(@Valid @RequestBody WearingBadgeReq req) {
        userService.wearingBadge(RequestHolder.get().getUid(), req.getItemId());
        return ApiResult.success();
    }

//    @PutMapping("/black")
//    @ApiOperation("拉黑用户")
//    public ApiResult<Void> black(@Valid @RequestBody BlackReq req) {
//        Long uid = RequestHolder.get().getUid();
//        boolean hasPower = iRoleService.hasPower(uid, RoleEnum.ADMIN);
//        AssertUtil.isTrue(hasPower, "抹茶管理员没权限");
//        userService.black(req);
//        return ApiResult.success();
//    }
}

