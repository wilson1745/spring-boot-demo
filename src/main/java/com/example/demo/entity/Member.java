package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Function: Member.java
 * @Description:
 * @author: Wilson Lo
 * @date: 2022/11/28
 * @MaintenancePersonnel: Wilson Lo
 */
@Getter
@Setter
@ToString
public class Member extends Base {

    /** The ma id. */
    private String ma_id;

    /** The name. */
    private String name;

    /** The id number. */
    private String id_number;

    /** The birthday. */
    private String birthday;

    /** The phone. */
    private String phone;

    /** The c id. */
    private String c_id;

    /** The d id. */
    private String d_id;

    /** The address. */
    private String address;

}
