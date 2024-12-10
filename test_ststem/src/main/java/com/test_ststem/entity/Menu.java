package com.test_ststem.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Menu对象", description = "")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty("菜单编码")
    private String menucode;

    @ApiModelProperty("菜单名字")
    private String menuname;

    @ApiModelProperty("菜单级别")
    private String menulevel;

    @ApiModelProperty("菜单的父code")
    private String menuparentcode;

    @ApiModelProperty("点击触发的函数")
    private String menuclick;

    @ApiModelProperty("权限 0超级管理员，1表示管理员，2表示普通用户,可以用逗号组合表示")
    private String menuright;

    private String menucomponent;

    private String menuicon;


}
