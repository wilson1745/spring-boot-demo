package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MemberDao;
import com.example.demo.entity.Member;
import com.example.demo.entity.MemberAccount;
import com.example.demo.service.MemberService;
import com.example.demo.service.ex.MemberNotFoundException;
import com.example.demo.service.ex.UpdateException;
import com.example.demo.vo.MemberVO;

/**
 * @Function: MemberServiceImpl.java
 * @Description:
 * @author: Wilson Lo
 * @date: 2022/11/28
 * @MaintenancePersonnel: Wilson Lo
 */
@Service
public class MemberServiceImpl implements MemberService {

    /** The member dao. */
    @Autowired
    private MemberDao memberDao;

    /**
     * Combine member account and member VO.
     *
     * @param memberAccount the member account
     * @param memberVO the member VO
     * @return the member
     */
    private Member combineMemberAccountAndMemberVO(MemberAccount memberAccount, MemberVO memberVO) {
        Member member = new Member();
        member.setMa_id(memberAccount.getId());
        member.setId_number(memberVO.getId_number());
        member.setPhone(memberVO.getPhone());

        if (null != memberVO.getYear() && !"".equals(memberVO.getYear()) && null != memberVO.getMonth()
            && !"".equals(memberVO.getMonth()) && null != memberVO.getDate() && !"".equals(memberVO.getDate())) {
            member.setBirthday(memberVO.getYear() + "-" + memberVO.getMonth() + "-" + memberVO.getDate());
        }

        if (null != memberVO.getC_id() && !"".equals(memberVO.getC_id()) && null != memberVO.getD_id()
            && !"".equals(memberVO.getD_id())) {
            member.setC_id(memberVO.getC_id());
            member.setD_id(memberVO.getD_id());
        }
        member.setAddress(memberVO.getAddress());

        return member;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.example.demo.service.MemberService#information(com.example.demo.entity.MemberAccount)
     */
    @Override
    public Member information(MemberAccount memberAccount) {
        // 根據MemberAccount ID 取得Member 資料
        Member member = memberDao.findMemberByMa_id(memberAccount.getId());
        if (member == null) {
            throw new MemberNotFoundException("查無會員資料");
        }
        return member;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.example.demo.service.MemberService#updateInformation(com.example.demo.entity.MemberAccount,
     *      com.example.demo.vo.MemberVO)
     */
    @Override
    public void updateInformation(MemberAccount memberAccount, MemberVO memberVO) {
        // 將MemberVO 轉為Member
        Member member = this.combineMemberAccountAndMemberVO(memberAccount, memberVO);

        // 根據MemberAccount ID 對Member 資料進行修改
        member.setUpdate_by(memberAccount.getUsername());
        Integer result = memberDao.update(member);
        if (result <= 0) {
            throw new UpdateException("修改會員資料時發生錯誤");
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.example.demo.service.MemberService#insert(com.example.demo.entity.Member)
     */
    @Override
    public Integer insert(Member member) {
        return memberDao.insert(member);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.example.demo.service.MemberService#findMemberByMa_id(java.lang.String)
     */
    @Override
    public Member findMemberByMa_id(String ma_id) {
        return memberDao.findMemberByMa_id(ma_id);
    }

}
