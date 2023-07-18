package com.whrj.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class SaleChance {
    private Integer id;

    private String chanceSource;

    private String customerName;

    private Integer cgjl;

    private String overview;

    private String linkMan;

    private String linkPhone;

    private String description;

    private String createMan;

    private String assignMan;

    private Date assignTime;

    private Integer state;

    private Integer devResult;

    private Integer isValid;

    private Date createDate;

    private Date updateDate;

}