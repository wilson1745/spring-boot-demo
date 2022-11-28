package com.example.demo.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.DistrictDao;
import com.example.demo.entity.District;

/**
 * @Function: DistrictDaoImpl.java
 * @Description:
 * @author: Wilson Lo
 * @date: 2022/11/28
 * @MaintenancePersonnel: Wilson Lo
 */
@Repository
public class DistrictDaoImpl implements DistrictDao {

    /** The jdbc template. */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * (non-Javadoc)
     *
     * @see com.example.demo.dao.DistrictDao#findDistrictByC_id(java.lang.String)
     */
    @Override
    public List<District> findDistrictByC_id(String c_id) {
        String sql = " SELECT ID, C_ID, NAME FROM mydb.district WHERE C_ID = ? ";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<District>(District.class), new Object[] {c_id});
    }

}
