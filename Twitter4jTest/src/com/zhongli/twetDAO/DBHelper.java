package com.zhongli.twetDAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHelper {
	public static final String url = "jdbc:mysql://localhost:3306/twitterproject";
	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "root";
	public static final String password = "jcsss130";

	public Connection getConnection() throws Exception {
		Class.forName(name);// 指定连接类型
		return DriverManager.getConnection(url, user, password);// 获取连接
	}

}
