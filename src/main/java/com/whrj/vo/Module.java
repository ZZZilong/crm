package com.whrj.vo;


import lombok.Setter;
import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Setter
@Getter
@ToString
public class Module {
    private Integer id;

    private String moduleName;

    private String moduleStyle;

    private String url;

    private Integer parentId;

    private String parentOptValue;

    private Integer grade;

    private String optValue;

    private Integer orders;

    private Byte isValid;


    private Date createDate;

    private Date updateDate;


}