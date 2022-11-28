package com.example.demo.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.CountyDao;
import com.example.demo.entity.County;

/**
 * @Function: CountyDaoImpl.java
 * @Description:
 * @author: Wilson Lo
 * @date: 2022/11/28
 * @MaintenancePersonnel: Wilson Lo
 */
@Repository
public class CountyDaoImpl implements CountyDao {

    /** The jdbc template. */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * (non-Javadoc)
     *
     * @see com.example.demo.dao.CountyDao#findAllCounty()
     */
    @Override
    public List<County> findAllCounty() {
        String sql = " SELECT ID, NAME FROM mydb.county ORDER BY ID ";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<County>(County.class));
    }

}
