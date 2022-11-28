package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Function: Base.java
 * @Description:
 * @author: Wilson Lo
 * @date: 2022/11/28
 * @MaintenancePersonnel: Wilson Lo
 */
@Getter
@Setter
@ToString
public class Base {

    /** The create by. */
    private String create_by;

    /** The create time. */
    private String create_time;

    /** The update by. */
    private String update_by;

    /** The update time. */
    private String update_time;

}
