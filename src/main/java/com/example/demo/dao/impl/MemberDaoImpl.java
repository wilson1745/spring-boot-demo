package com.example.demo.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.MemberDao;
import com.example.demo.entity.Member;

/**
 * @Function: MemberDaoImpl.java
 * @Description:
 * @author: Wilson Lo
 * @date: 2022/11/28
 * @MaintenancePersonnel: Wilson Lo
 */
@Repository
public class MemberDaoImpl implements MemberDao {

    /** The jdbc template. */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /** The jdbc name template. */
    @Autowired
    private NamedParameterJdbcTemplate jdbcNameTemplate;

    /**
     * (non-Javadoc)
     *
     * @see com.example.demo.dao.MemberDao#insert(com.example.demo.entity.Member)
     */
    @Override
    public Integer insert(Member member) {
        String sql = " INSERT INTO mydb.member (MA_ID, NAME, ID_NUMBER, BIRTHDAY, PHONE, C_ID, D_ID, ADDRESS, "
            + "	CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) VALUE ( "
            + "	:ma_id, :name, :id_number, :birthday, :phone, :c_id, :d_id, :address, "
            + "	:create_by, NOW(), :update_by, NOW()) ";

        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(member);
        return jdbcNameTemplate.update(sql, paramSource);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.example.demo.dao.MemberDao#findMemberByMa_id(java.lang.String)
     */
    @Override
    public Member findMemberByMa_id(String ma_id) {
        String sql = " SELECT MA_ID, NAME, ID_NUMBER, BIRTHDAY, PHONE, C_ID, D_ID, ADDRESS FROM mydb.member "
            + " WHERE MA_ID = ? ";

        List<Member> result =
            jdbcTemplate.query(sql, new BeanPropertyRowMapper<Member>(Member.class), new Object[] {ma_id});
        if (result != null && result.size() > 0) {
            return result.get(0);
        }

        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.example.demo.dao.MemberDao#update(com.example.demo.entity.Member)
     */
    @Override
    public Integer update(Member member) {
        String sql = " UPDATE mydb.member SET "
            + "	ID_NUMBER = :id_number, BIRTHDAY = :birthday, PHONE = :phone, C_ID = :c_id, D_ID = :d_id, ADDRESS = :address, "
            + "	UPDATE_BY = :update_by, UPDATE_TIME = NOW() WHERE MA_ID = :ma_id ";

        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(member);
        return jdbcNameTemplate.update(sql, paramSource);
    }

}
