package com.cyj.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 财务项目表
 * </p>
 *
 * @author cyj
 * @since 2021-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Finance对象", description="财务项目表")
public class Finance implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer clubId;

    private String title;

    private Double money;

    private Date createTime;

    private Date beginTime;

    private Date endTime;

    private Integer status;

    private String creator;

    private String approver;

    private String remark;

    private Date checkTime;

    private String clubName;

}
