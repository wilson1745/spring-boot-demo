package com.example.demo.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.MemberAccountDao;
import com.example.demo.entity.MemberAccount;

/**
 * @Function: MemberAccountDaoImpl.java
 * @Description:
 * @author: Wilson Lo
 * @date: 2022/11/28
 * @MaintenancePersonnel: Wilson Lo
 */
@Repository
public class MemberAccountDaoImpl implements MemberAccountDao {

    /** The jdbc template. */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /** The jdbc name template. */
    @Autowired
    private NamedParameterJdbcTemplate jdbcNameTemplate;

    /**
     * (non-Javadoc)
     *
     * @see com.example.demo.dao.MemberAccountDao#insert(com.example.demo.entity.MemberAccount)
     */
    @Override
    public Integer insert(MemberAccount memberAccount) {
        String sql = " INSERT INTO mydb.member_account (USERNAME, PASSWORD, SALT, "
            + "	CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) VALUE ( "
            + "	:username, :password, :salt, :create_by, NOW(), :update_by, NOW()) ";

        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(memberAccount);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcNameTemplate.update(sql, paramSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.example.demo.dao.MemberAccountDao#findMemberAccountByUsername(java.lang.String)
     */
    @Override
    public MemberAccount findMemberAccountByUsername(String username) {
        String sql = " SELECT ID, USERNAME, PASSWORD, SALT FROM mydb.member_account WHERE USERNAME = ? ";

        List<MemberAccount> result = jdbcTemplate.query(sql,
            new BeanPropertyRowMapper<MemberAccount>(MemberAccount.class), new Object[] {username});
        if (result != null && result.size() > 0) {
            return result.get(0);
        }
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.example.demo.dao.MemberAccountDao#update(com.example.demo.entity.MemberAccount)
     */
    @Override
    public Integer update(MemberAccount memberAccount) {
        String sql = " UPDATE mydb.member_account SET "
            + "	PASSWORD = :password, UPDATE_BY = :update_by, UPDATE_TIME = NOW() WHERE ID = :id ";

        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(memberAccount);
        return jdbcNameTemplate.update(sql, paramSource);
    }

}
