package com.example.demo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.MemberAccount;
import com.example.demo.service.MemberAccountService;
import com.example.demo.vo.MemberAccountVO;

/**
 * @Function: MemberAccountController.java
 * @Description:
 * @author: Wilson Lo
 * @date: 2022/11/28
 * @MaintenancePersonnel: Wilson Lo
 */
@Controller
public class MemberAccountController {

    /** The member account service. */
    @Autowired
    private MemberAccountService memberAccountService;

    /**
     * Login.
     *
     * @param memberAccount the member account
     * @param message the message
     * @return the string
     */
    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String login(@ModelAttribute MemberAccount memberAccount,
        @ModelAttribute(value = "MESSAGE") String message) {
        return "login";
    }

    /**
     * Do login.
     *
     * @param memberAccount the member account
     * @param session the session
     * @param redirectAttributes the redirect attributes
     * @return the string
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin(@Valid @ModelAttribute MemberAccount memberAccount, HttpSession session,
        RedirectAttributes redirectAttributes) {
        memberAccount = memberAccountService.login(memberAccount);
        session.setAttribute("member", memberAccount);
        return "redirect:information";
    }

    /**
     * Register.
     *
     * @param memberAccountVO the member account VO
     * @return the string
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(@ModelAttribute MemberAccountVO memberAccountVO) {
        return "register";
    }

    /**
     * Do register.
     *
     * @param memberAccountVO the member account VO
     * @param redirectAttributes the redirect attributes
     * @return the string
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String doRegister(@Valid @ModelAttribute MemberAccountVO memberAccountVO,
        RedirectAttributes redirectAttributes) {
        memberAccountService.register(memberAccountVO);
        redirectAttributes.addFlashAttribute("MESSAGE", "註冊成功");
        return "redirect:login";
    }

    /**
     * Logout.
     *
     * @param session the session
     * @param sessionStatus the session status
     * @return the string
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session, SessionStatus sessionStatus) {
        if (session.getAttribute("member") != null) {
            session.removeAttribute("member");
            sessionStatus.setComplete();
        }
        return "redirect:login";
    }

}
