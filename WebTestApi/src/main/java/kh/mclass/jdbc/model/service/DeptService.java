package kh.mclass.jdbc.model.service;

import static kh.mclass.jdbc.common.JdbcTemplate.*;

import java.sql.Connection;

import java.util.List;

import kh.mclass.jdbc.common.JdbcTemplate;
import kh.mclass.jdbc.model.dao.DeptDao;
import kh.mclass.jdbc.model.vo.Dept;

public class DeptService {
	DeptDao dao = new DeptDao();

	public Dept selectOne(int deptno) {
		Dept result = null;
		Connection conn = JdbcTemplate.getConnection();
		dao.selectOne(conn, deptno);
		close(conn);
		return result;
	}

	public List<Dept> selectList() {
		List<Dept> result = null; 
		Connection conn = JdbcTemplate.getConnection();
		result = dao.selectList(conn);
		System.out.println("service " + result);
		close(conn);
		return result;
	}

	public int insert(Dept vo) {
		int result = 0;
		Connection conn = JdbcTemplate.getConnection();
		result = dao.insert(conn, vo);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return result;
	}

	public int delete(int deptno) {
		int result = 0;
		Connection conn = JdbcTemplate.getConnection();
		result = dao.delete(conn, deptno);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return result;
	}
}
