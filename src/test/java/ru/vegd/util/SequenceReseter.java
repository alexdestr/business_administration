package ru.vegd.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SequenceReseter {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String SQL_RESET_EMPLOYEE_SEQ = "ALTER SEQUENCE test.employee_employee_id_seq RESTART";

    public void resetEmployeeSeq() {
        jdbcTemplate.execute(SQL_RESET_EMPLOYEE_SEQ);
    }
}
