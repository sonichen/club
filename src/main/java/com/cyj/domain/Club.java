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
 * 社团信息表
 * </p>
 *
 * @author cyj
 * @since 2021-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Club对象", description="社团信息表")
public class Club implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer type;

    private String chief;

    private String name;

    private String content;

    private Integer number;

    private String photo;

    private Date applTime;

    private Date createTime;

    private String remark;

    private String approver;
// 0-申请，1-成立，-1 -冻结, -2申请失败
    private Integer status;
//    private Clubtype clubtype;
//    private User user;
    private String username;
    private String typeName;
    private String studentName;
    private String teacherName;
    private Integer count;
    private String college;
    private String scope;

}
