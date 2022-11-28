package com.example.demo.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Function: MemberAccountVO.java
 * @Description:
 * @author: Wilson Lo
 * @date: 2022/11/28
 * @MaintenancePersonnel: Wilson Lo
 */
@Getter
@Setter
@ToString
public class MemberAccountVO {

    /** The name. */
    @NotBlank(message = "姓名不可為空")
    private String name;

    /** The username. */
    @Email(message = "帳號必須是Email 格式")
    @NotBlank(message = "帳號不可為空")
    private String username;

    /** The password. */
    @NotBlank(message = "密碼不可為空")
    // @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\w]{6,16}$", message = "密碼必須為長度6~16位碼大小寫英文加數字")
    private String password;

    /** The check password. */
    @NotBlank(message = "再次輸入密碼不可為空")
    private String checkPassword;

}
