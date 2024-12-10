package com.test_ststem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author higashi
 * @since 2024-07-15
 */
@Data
@ApiModel(value = "Record对象", description = "")
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("货品id")
    private Integer goods;

    @ApiModelProperty("取货人/补货人")
    private Integer userid;

    @ApiModelProperty("操作人id")
    private Integer adminid;

    @ApiModelProperty("数量")
    private Integer count;

    @ApiModelProperty("操作时间")
    private LocalDateTime createtime;

    @ApiModelProperty("备注")
    private String remark;

    @TableField(exist = false)
    private String action;

}
