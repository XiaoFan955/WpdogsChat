package com.fan.wpdogschat.common.user.domain.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoResp {
    @ApiModelProperty(value = "uid")
    private Long id;
    @ApiModelProperty(value="用户名")
    private String name;
    @ApiModelProperty(value="头像")
    private String avatar;
    @ApiModelProperty(value="性别")
    private Integer sex;
    @ApiModelProperty(value="剩余改名次数")
    private Integer modifyNameChance;
}
