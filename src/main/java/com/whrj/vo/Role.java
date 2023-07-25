package com.whrj.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class Role {
    private Integer id;

    private String roleName;

    private String roleRemark;
    @JsonFormat(pattern = "yyyy-MM-dd ", timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd ", timezone = "GMT+8")
    private Date updateDate;

    private Integer isValid;

}