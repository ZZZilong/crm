package com.whrj.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TreeModule {
    private Integer id;
    private Integer pid;
    private String name;
    private boolean checked = false;
}
