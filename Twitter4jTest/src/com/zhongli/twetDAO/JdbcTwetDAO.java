package com.zhongli.twetDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JdbcTwetDAO implements TwetDAO {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void insertEarthSqure(double south, double north, double west,
			double east, int row, int col, Connection conn) {
		String sqlString = "INSERT INTO earthsqure (south, north, west, east, row, col) VALUES ("
				+ south
				+ ", "
				+ north
				+ ", "
				+ west
				+ ", "
				+ east
				+ ", "
				+ row
				+ ", " + col + ");";
		try {
			PreparedStatement ps = conn.prepareStatement(sqlString);
			ps.executeUpdate();
			ps.close();
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	@Override
	public void insert(TwetMsg tweet, Connection conn) {

		String sqlString = "INSERT INTO ";
		// 根据地理位置信息写入数据道不同的数据库
		sqlString += DaoSetting.DBChooese(tweet.getLocation()[0],
				tweet.getLocation()[1]);
		sqlString += "(username, msg, date, time, location_lat, location_lan, language) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sqlString);
			ps.setString(1, tweet.getUserName());
			ps.setString(2, tweet.getMsg());
			ps.setString(3,
					makeDate(tweet.getYear(), tweet.getMonth(), tweet.getDay()));
			ps.setString(4, tweet.getTime());
			ps.setDouble(5, tweet.getLocation()[0]);
			ps.setDouble(6, tweet.getLocation()[1]);
			ps.setString(7, tweet.getLanguage());
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

	};

	/**
	 * 生成符合格式的日期字符串
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	private String makeDate(int year, String month, int day) {
		// 将月份转化为数字
		int mo = getIntMonthFromString(month);
		Date date = Date.valueOf("" + year + "-" + mo + "-" + day);
		return date.toString();
	}

	// public void insert(TwetMsg tweet,Connection conn) {
	// String sqlString =
	// "INSERT INTO alltwets (username, msg, week, month, day, time, timezone, year, location_lat, location_lan, language) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	// try {
	// PreparedStatement ps = conn.prepareStatement(sqlString);
	// ps.setString(1, tweet.getUserName());
	// ps.setString(2, tweet.getMsg());
	// ps.setString(3, tweet.getWeek());
	// ps.setString(4, tweet.getMonth());
	// ps.setInt(5, tweet.getDay());
	// ps.setString(6, tweet.getTime());
	// ps.setString(7, tweet.getTimeZone());
	// ps.setInt(8, tweet.getYear());
	// ps.setDouble(9, tweet.getLocation()[0]);
	// ps.setDouble(10, tweet.getLocation()[1]);
	// ps.setString(11, tweet.getLanguage());
	// ps.executeUpdate();
	// ps.close();
	//
	// } catch (SQLException e) {
	// throw new RuntimeException(e);
	//
	// } finally {
	// if (conn != null) {
	// try {
	// conn.close();
	// } catch (SQLException e) {
	// }
	// }
	// }
	// }

	private int getIntMonthFromString(String month) {
		if (month.toLowerCase().equals("january")
				|| month.toLowerCase().equals("jan")
				|| month.toLowerCase().equals("jan.")) {
			return 1;
		} else if (month.toLowerCase().equals("february")
				|| month.toLowerCase().equals("feb")
				|| month.toLowerCase().equals("feb.")) {
			return 2;
		} else if (month.toLowerCase().equals("march")
				|| month.toLowerCase().equals("mar")
				|| month.toLowerCase().equals("mar.")) {
			return 3;
		} else if (month.toLowerCase().equals("april")
				|| month.toLowerCase().equals("apr")
				|| month.toLowerCase().equals("apr.")) {
			return 4;
		} else if (month.toLowerCase().equals("may")) {
			return 5;
		} else if (month.toLowerCase().equals("june")
				|| month.toLowerCase().equals("jun")
				|| month.toLowerCase().equals("jun.")) {
			return 6;
		} else if (month.toLowerCase().equals("july")
				|| month.toLowerCase().equals("jul")
				|| month.toLowerCase().equals("jul.")) {
			return 7;
		} else if (month.toLowerCase().equals("august")
				|| month.toLowerCase().equals("aug")
				|| month.toLowerCase().equals("aug.")) {
			return 8;
		} else if (month.toLowerCase().equals("september")
				|| month.toLowerCase().equals("sep")
				|| month.toLowerCase().equals("sep.")) {
			return 9;
		} else if (month.toLowerCase().equals("october")
				|| month.toLowerCase().equals("oct")
				|| month.toLowerCase().equals("oct.")) {
			return 10;
		} else if (month.toLowerCase().equals("november")
				|| month.toLowerCase().equals("nov")
				|| month.toLowerCase().equals("nov.")) {
			return 11;
		} else if (month.toLowerCase().equals("december")
				|| month.toLowerCase().equals("dec")
				|| month.toLowerCase().equals("dec.")) {
			return 12;
		} else {
			return 0;
		}
	}

	@Override
	public TwetMsg findByID(int id, Connection conn) {
		String sqlString = "SELECT * FROM twitterproject.alltwets where numID=?;";

		try {
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

}
