package com.example.demo.dao;

import com.example.demo.entity.Member;

/**
 * @Function: MemberDao.java
 * @Description:
 * @author: Wilson Lo
 * @date: 2022/11/28
 * @MaintenancePersonnel: Wilson Lo
 */
public interface MemberDao {

    /**
     * Insert.
     *
     * @param member the member
     * @return the integer
     */
    public Integer insert(Member member);

    /**
     * Find member by ma id.
     *
     * @param ma_id the ma id
     * @return the member
     */
    public Member findMemberByMa_id(String ma_id);

    /**
     * Update.
     *
     * @param member the member
     * @return the integer
     */
    public Integer update(Member member);

}
