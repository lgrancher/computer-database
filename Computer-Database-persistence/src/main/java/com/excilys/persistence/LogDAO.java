package com.excilys.persistence;

import java.sql.SQLException;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.excilys.om.Log;

@Repository
public class LogDAO extends JdbcDaoSupport
{		
	public void create(Log log) throws SQLException
	{
		String sql = "insert into log values(default,?,?,NOW())";
		
		getJdbcTemplate().update(sql, new Object[] {log.getTypeLog().toString(), log.getOperation()});
	}
}
