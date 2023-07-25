package com.whrj.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Permission {
    private Integer id;

    private Integer roleId;

    private Integer moduleId;

    private String aclValue;

    private Date createDate;

    private Date updateDate;


}