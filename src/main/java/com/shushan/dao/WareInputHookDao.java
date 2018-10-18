package com.shushan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shushan.util.CommonUtil;
import com.shushan.util.DBConn;
@Repository
public class WareInputHookDao {

	public void add(JSONObject data) {
		String sql = "insert into `table_input` (id_jdy, warehouse, warehouseno, warehousing_in_time, warehousing_in_detail, creator, updater) values(?, ?, ?, ?, ?, ?, ?)";
		Connection conn = DBConn.getConnection();
		PreparedStatement psmt = null;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, (String) data.get("_id"));
			psmt.setString(2, (String) data.get("warehouse"));
			psmt.setInt(3, data.getInteger("warehouseno"));
			psmt.setString(4, (String) data.get("warehousing_in_time"));
			psmt.setString(5, CommonUtil.handleSubform((JSONArray)data.get("warehousing_in_detail")));
			psmt.setString(6, CommonUtil.handleJSONObject(data.getJSONObject("creator")));
			psmt.setString(7, CommonUtil.handleJSONObject(data.getJSONObject("updater")));
			psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBConn.close(conn, psmt, null);
		}
	}

	public void update(JSONObject data) {
		String sql = "update `table_input` set warehouse=?, warehouseno=?, warehousing_in_time=?, warehousing_in_detail=?, creator=?, updater=? where id_jdy=?";
		Connection conn = DBConn.getConnection();
		PreparedStatement psmt = null;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, (String) data.get("warehouse"));
			psmt.setInt(2, data.getInteger("warehouseno"));
			String time = (String) data.get("warehousing_in_time");
			time = (String) time.replace("T", " ").subSequence(0, time.indexOf("."));
			psmt.setString(3, time);
			psmt.setString(4, CommonUtil.handleSubform((JSONArray)data.get("warehousing_in_detail")));
			psmt.setString(5, CommonUtil.handleJSONObject(data.getJSONObject("creator")));
			psmt.setString(6, CommonUtil.handleJSONObject(data.getJSONObject("updater")));
			psmt.setString(7, (String) data.get("_id"));
			psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBConn.close(conn, psmt, null);
		}
		
	}

	public void delete(JSONObject data) {
		String sql = "delete from `table_input` where id_jdy=?";
		Connection conn = DBConn.getConnection();
		PreparedStatement psmt = null;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, (String) data.get("_id"));
			psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBConn.close(conn, psmt, null);
		}
	}
}
