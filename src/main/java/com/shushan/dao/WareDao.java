package com.shushan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.shushan.util.DBConn;

@Repository
public class WareDao {


		public boolean add(String warehouse, int warehouseno, String warehousetext) {
			Connection conn = DBConn.getConnection();
			String sql = "insert into `table_warehouse`(warehouse, warehouseno, warehousetext) values (?,?,?)";
			PreparedStatement psmt = null;
			int result = 0;
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, warehouse);
				psmt.setInt(2, warehouseno);
				psmt.setString(3, warehousetext);
				result = psmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				DBConn.close(conn, psmt, null);
			}
			return (result>0)?true:false;
		}

		public void updateIDJDY(String warehouse, String idJDY) {
			Connection connection = DBConn.getConnection();
			String sql = "update `table_warehouse` set id_jdy=? where warehouse=?";
			PreparedStatement psmt = null;
			try {
				psmt = connection.prepareStatement(sql);
				psmt.setString(1, idJDY);
				psmt.setString(2, warehouse);
				psmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				DBConn.close(connection, psmt, null);
			}
		}
}
