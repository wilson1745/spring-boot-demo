package com.example.demo.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.example.demo.dao.MemberAccountDao;
import com.example.demo.entity.Member;
import com.example.demo.entity.MemberAccount;
import com.example.demo.service.MemberAccountService;
import com.example.demo.service.MemberService;
import com.example.demo.service.ex.InsertException;
import com.example.demo.service.ex.MemberAccountNotFoundException;
import com.example.demo.service.ex.MemberNotFoundException;
import com.example.demo.service.ex.PasswordNotMatchException;
import com.example.demo.service.ex.UsernameDuplicateException;
import com.example.demo.vo.MemberAccountVO;

/**
 * @Function: MemberAccountServiceImpl.java
 * @Description:
 * @author: Wilson Lo
 * @date: 2022/11/28
 * @MaintenancePersonnel: Wilson Lo
 */
@Service
public class MemberAccountServiceImpl implements MemberAccountService {

    /** The member account dao. */
    @Autowired
    private MemberAccountDao memberAccountDao;

    /** The member service. */
    @Autowired
    private MemberService memberService;

    /**
     * Gets the md 5 password.
     *
     * @param password the password
     * @param salt the salt
     * @return the md 5 password
     */
    private String getMd5Password(String password, String salt) {
        // 對password + salt 進行三次加密
        String str = password + salt;
        for (int i = 0; i < 3; i++) {
            str = DigestUtils.md5DigestAsHex(str.getBytes()).toUpperCase();
        }
        return str;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.example.demo.service.MemberAccountService#login(com.example.demo.entity.MemberAccount)
     */
    @Override
    public MemberAccount login(MemberAccount memberAccount) {
        // 檢查帳號是否存在
        MemberAccount data =
            Optional.ofNullable(memberAccountDao.findMemberAccountByUsername(memberAccount.getUsername()))
                .orElseThrow(() -> new MemberAccountNotFoundException("帳號或密碼錯誤"));
        // if (data == null) {
        // throw new MemberAccountNotFoundException("帳號或密碼錯誤");
        // }

        // 使用資料庫鹽值對輸入密碼進行加密
        String md5Password = this.getMd5Password(memberAccount.getPassword(), data.getSalt());

        // 比對密碼是否相等
        if (!md5Password.equals(data.getPassword())) {
            throw new PasswordNotMatchException("帳號或密碼錯誤");
        }

        // 取得對應Member 資料
        Member member = memberService.findMemberByMa_id(data.getId());
        if (member == null) {
            throw new MemberNotFoundException("帳號或密碼錯誤");
        }

        // 將密碼設為null 後回傳
        data.setPassword(null);
        data.setSalt(null);
        return data;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.example.demo.service.MemberAccountService#register(com.example.demo.vo.MemberAccountVO)
     */
    @Override
    public void register(MemberAccountVO memberAccountVO) {
        // 驗證欄位是否填寫及格式
        if (!memberAccountVO.getPassword().equals(memberAccountVO.getCheckPassword())) {
            throw new PasswordNotMatchException("兩次輸入密碼不相符");
        }

        // 檢查帳號是否重複註冊
        MemberAccount data = memberAccountDao.findMemberAccountByUsername(memberAccountVO.getUsername());
        if (data != null) {
            throw new UsernameDuplicateException("該帳號已被使用");
        }

        // 產生鹽值
        String salt = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");

        // 密碼加密
        String md5Password = this.getMd5Password(memberAccountVO.getPassword(), salt);

        // 新增MemberAccount 資料
        MemberAccount memberAccount = new MemberAccount();
        memberAccount.setUsername(memberAccountVO.getUsername());
        memberAccount.setPassword(md5Password);
        memberAccount.setSalt(salt);
        memberAccount.setCreate_by(memberAccountVO.getUsername());
        memberAccount.setUpdate_by(memberAccountVO.getUsername());
        Integer id = memberAccountDao.insert(memberAccount);
        if (id == 0) {
            throw new InsertException("新增會員帳號時發生錯誤");
        }

        // 新增Member 資料
        Member member = new Member();
        member.setMa_id(String.valueOf(id));
        member.setName(memberAccountVO.getName());
        member.setCreate_by(memberAccountVO.getUsername());
        member.setUpdate_by(memberAccountVO.getUsername());
        Integer result = memberService.insert(member);
        if (result == 0) {
            throw new InsertException("新增會員資料時發生錯誤");
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.example.demo.service.MemberAccountService#findMemberAccountByUsername(java.lang.String)
     */
    @Override
    public MemberAccount findMemberAccountByUsername(String username) {
        return memberAccountDao.findMemberAccountByUsername(username);
    }

}
