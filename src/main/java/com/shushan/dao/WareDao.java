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
			String sql = "insert into `warehouse`(warehouse, warehouseno, warehousetext) values (?,?,?)";
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
			}
			return (result>0)?true:false;
		}
}
