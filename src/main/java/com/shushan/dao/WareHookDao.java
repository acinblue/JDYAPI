package com.shushan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.shushan.util.DBConn;

@Repository
public class WareHookDao {

	public void add(JSONObject data) {
		// TODO Auto-generated method stub
		String sql = "insert into `table_warehouse` value (?,?,?,?)";
		Connection conn = DBConn.getConnection();
		PreparedStatement psmt = null;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, data.getString("_id"));
			psmt.setString(2, data.getString("warehouse"));
			psmt.setInt(3, data.getInteger("warehouseno"));
			psmt.setString(4, data.getString("warehousetext"));
			int i =psmt.executeUpdate();
			System.out.println(i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBConn.close(conn, psmt, null);
		}
	}

	public void update(JSONObject data) {
		// TODO Auto-generated method stub
		String sql = "update `table_warehouse` set warehouse = ?, warehouseno = ?, warehousetext = ? where id = ?";
		Connection conn = DBConn.getConnection();
		PreparedStatement psmt = null;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, data.getString("warehouse"));
			psmt.setInt(2, data.getInteger("warehouseno"));
			psmt.setString(3, data.getString("warehousetext"));
			psmt.setString(4, data.getString("_id"));
			psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBConn.close(conn, psmt, null);
		}
	}

	public void delete(JSONObject data) {
		// TODO Auto-generated method stub
		String sql = "delete from `table_warehouse` where id = ?";
		Connection conn = DBConn.getConnection();
		PreparedStatement psmt = null;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, data.getString("_id"));
			psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBConn.close(conn, psmt, null);
		}
	}
	
}
