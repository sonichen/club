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
 * 
 * </p>
 *
 * @author cyj
 * @since 2021-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Activity对象", description="")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer clubId;

    private Date beginTime;

    private Date endTime;

    private String title;

    private String context;

    private String creator;

    private Date createTime;

    private String place;

    private String remark;

    private String approver;

    private Integer status;

    private Date checkTime;

    private Date reTime;

    private Integer mcount;

    private String leaderName;

    private String teacherName;
    private String clubName;
    private String projecttype;
}
