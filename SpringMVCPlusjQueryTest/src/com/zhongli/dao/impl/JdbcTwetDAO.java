package com.zhongli.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.zhongli.dao.TwetDAO;
import com.zhongli.model.TwetMsg;

public class JdbcTwetDAO implements TwetDAO {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void insert(TwetMsg tweet) {
		String sqlString = "INSERT INTO alltwets (username, msg, week, month, day, time, timezone, year, location_lat, location_lan, language) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sqlString);
			ps.setString(1, tweet.getUserName());
			ps.setString(2, tweet.getMsg());
			ps.setString(3, tweet.getWeek());
			ps.setString(4, tweet.getMonth());
			ps.setInt(5, tweet.getDay());
			ps.setString(6, tweet.getTime());
			ps.setString(7, tweet.getTimeZone());
			ps.setInt(8, tweet.getYear());
			ps.setDouble(9, tweet.getLocation()[0]);
			ps.setDouble(10, tweet.getLocation()[1]);
			ps.setString(11, tweet.getLanguage());
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	@Override
	public TwetMsg findByID(int id) {
		String sqlString = "SELECT * FROM twitterproject.alltwets where numID=?;";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sqlString);
			ps.setInt(1, id);
			TwetMsg twetMsg = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				twetMsg = new TwetMsg(rs.getString("userName"),
						rs.getString("msg"), rs.getString("week"),
						rs.getString("month"), rs.getInt("day"),
						rs.getString("time"), rs.getString("timezone"),
						rs.getInt("year"), rs.getDouble("location_lat"),
						rs.getDouble("location_lan"), rs.getString("language"));
			}
			return twetMsg;
		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	@Override
	public TwetMsg findByMaxID() {
		String sqlString = "SELECT * FROM alltwets where numID = (SELECT max(numID) FROM alltwets)";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sqlString);
			TwetMsg twetMsg = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				twetMsg = new TwetMsg(rs.getString("userName"),
						rs.getString("msg"), rs.getString("week"),
						rs.getString("month"), rs.getInt("day"),
						rs.getString("time"), rs.getString("timezone"),
						rs.getInt("year"), rs.getDouble("location_lat"),
						rs.getDouble("location_lan"), rs.getString("language"));
			}
			return twetMsg;
		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

}
