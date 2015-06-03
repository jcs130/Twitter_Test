package com.zhongli.twitterTool;

import com.zhongli.twetDAO.DBHelper;
import com.zhongli.twetDAO.JdbcTwetDAO;

public class SaveEarthSqure2DB {

	public static void main(String[] args) {
		SaveEarthSqure2DB tm = new SaveEarthSqure2DB();
		tm.calculate();

	}

	public void calculate() {
		DBHelper DBh = new DBHelper();
		JdbcTwetDAO jtDAO = new JdbcTwetDAO();
		try {
			// 地球周长
			int l_earth = 40075000;
			// 区块大小，单位：米
			int squre_size = 20000;
			// 北半球的行数
			int row_num = (int) Math.ceil((l_earth / 4) / (double) squre_size);
			// 每行间隔多少度
			double latCon = 90 / (double) row_num;
			// 北半球的存入数据库中的行数
			int row_save = (int) Math.ceil((85 / latCon));
			// 地球半径
			double r_earth = l_earth / (2 * Math.PI);
			// 区块东南西北四个边界
			double S, N, W, E;
			for (int r = 0; r < row_save; r++) {
				S = latCon * r;
				N = latCon * (r + 1);
				if (N > 180.0) {
					N = 180.0;
				}
				// 对应维度的周长=cos纬度×地球半径×∏×2
				// 通过纬度求得周长，除2得到一个象限的长度
				int l_current = (int)Math.round( (Math.cos(Math.toRadians(S)) * r_earth
						* Math.PI * 2))/ 2;
				// 除20公里得到一共有多少列
				int col_num = (int) Math.ceil(l_current / (double) squre_size);
				for (int c = 0; c < col_num; c++) {
					W = 180 * c / (double) col_num;
					E = 180 * (c + 1) / (double) col_num;
					if (E > 180.0) {
						E = 180.0;
					}
					String line=S+"<>"+N+"<>"+W+"<>"+E+"<>"+(r+1)+"<>"+(c+1);
					 System.out.println(line);
					jtDAO.insertEarthSqure(S, N, W, E, r + 1, c + 1,
							DBh.getConnection());
					jtDAO.insertEarthSqure(S, N, -E, -W, r + 1, -(c + 1),
							DBh.getConnection());
					jtDAO.insertEarthSqure(-N, -S, -E, -W, -(r + 1), -(c + 1),
							DBh.getConnection());
					jtDAO.insertEarthSqure(-N, -S, W, E, -(r + 1), c + 1,
							DBh.getConnection());

				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
