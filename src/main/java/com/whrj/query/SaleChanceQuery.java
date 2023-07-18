package com.whrj.query;

import com.whrj.base.BaseQuery;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 营销机会管理多条件查询
 */

@Getter
@Setter
@ToString
public class SaleChanceQuery extends BaseQuery {
    private  String customerName;
    private  String createMan;
    private String state;
}
