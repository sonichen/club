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
 * 社团成员表
 * </p>
 *
 * @author cyj
 * @since 2021-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Member对象", description="社团成员表")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String studentId;

    private Integer type;

    private Date createTime;

    private Date intoTime;

    private String remark;

    private Date rwTime;

    private  Integer status;

    private Integer clubId;
    private String checker;

    private String clubName;
    private String studentName;
    private String checkerName;

}
