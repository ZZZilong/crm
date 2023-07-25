package com.whrj.query;

import com.whrj.base.BaseQuery;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class RoleQuery extends BaseQuery {
    private String roleName;

    private String roleRemark;

    private Date createDate;

    private Date updateDate;

    private Integer isValid;
}
