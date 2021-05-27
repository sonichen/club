package com.cyj.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@ApiModel(value="User对象", description="")
public class User implements Serializable {

//    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String number;

    private String password;

    private String username;

    private Integer sex;

    private String college;

    private String grade;

    private String major;

    private Integer studentType;

    private String email;

    private String tel;

    private String photo;

    private String remark;

    private Integer type;
    private Integer status;

    private List<Club> clubs;

    private List<Club> managerClubs;
    private Date intotime;

    public User(String number, String username) {
        this.number = number;
        this.username = username;
    }

    public User(String number, String username, Integer sex, String college, String grade, String major, Integer studentType, String email, String tel, String remark) {
        this.number = number;
        this.username = username;
        this.sex = sex;
        this.college = college;
        this.grade = grade;
        this.major = major;
        this.studentType = studentType;
        this.email = email;
        this.tel = tel;
        this.remark = remark;
    }

    public User() {

    }
}
