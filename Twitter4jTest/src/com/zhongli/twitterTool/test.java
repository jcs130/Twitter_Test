package com.zhongli.twitterTool;

public class test {
	public static void main(String[] args) {
		test t = new test();
		double lat = -2.2;
		double lon = 29.7;
		EarthSqure res = t.getArea(lat, lon);
		System.out.println(res.getRow() + "<>" + res.getCol() + "\n S: "
				+ res.getSouth() + "\t N:" + res.getNorth() + "\t W:"
				+ res.getWest() + "\t E:" + res.getEast());
		EarthSqure res2 = new EarthSqure(lat, lon);
		System.out.println(res2.getRow() + "<>" + res2.getCol() + "\n S: "
				+ res2.getSouth() + "\t N:" + res2.getNorth() + "\t W:"
				+ res2.getWest() + "\t E:" + res2.getEast());
	}

	/**
	 * 输入经纬度然后确定是在哪个Stream区块上
	 * 
	 * @param lat
	 *            0~90
	 * @param lon
	 *            0~180
	 * @return
	 */
	public EarthSqure getArea(double lat, double lon) {
		// 检查输入是否符合规定
		double t_lat = Math.abs(lat);
		double t_lon = Math.abs(lon);
		if (t_lat >= 0 && t_lat <= 90 && t_lon <= 180 && t_lon >= 0) {

			// 该点所在区块行号和列号
			int row, col;
			// 地球周长
			int l_earth = 40075000;
			// 区块大小，单位：米
			int squre_size = 20000;
			// 北半球的行数
			int row_num = (int) Math.ceil((l_earth / 4) / (double) squre_size);
			// 地球半径
			double r_earth = l_earth / (2 * Math.PI);
			// 计算区块间的纬度差
			double latCon = 90 / (double) row_num;
			row = (int) (t_lat / latCon);
			// 对应维度的周长=cos纬度×地球半径×∏×2
			// 通过纬度求得周长，除2得到一个象限的长度
			int l_current = (int) Math.round((Math.cos(Math.toRadians((row)
					* latCon))
					* r_earth * Math.PI * 2) / 2.0);
			// 除20公里得到一共有多少列
			int col_num = (int) Math.ceil(l_current / (double) squre_size);
			// 再用180除以列数得到每列间隔多少度
			double degPerSqure_lon = 180 / (double) col_num;
			// 再用输入的经度除以这个差值向下取整得到所在区块的列数
			col = (int) (t_lon / degPerSqure_lon);
			double S = latCon * row;
			double N = latCon * (row + 1);
			if (N > 180.0) {
				N = 180.0;
			}

			double W = 180 * col / (double) col_num;
			double E = 180 * (col + 1) / (double) col_num;
			if (E > 180.0) {
				E = 180.0;
			}
			// 第一象限
			if (lat >= 0 && lon >= 0) {
				return new EarthSqure(S, N, W, E, row + 1, col + 1,
						degPerSqure_lon);
			} else
			// 第二象限
			if (lat > 0 && lon < 0) {
				return new EarthSqure(S, N, -E, -W, row + 1, -(col + 1),
						degPerSqure_lon);
			} else
			// 第三象限
			if (lat < 0 && lon < 0) {
				return new EarthSqure(-N, -S, -E, -W, -(row + 1), -(col + 1),
						degPerSqure_lon);
			} else
			// 第四象限
			{
				return new EarthSqure(-N, -S, W, E, -(row + 1), col + 1,
						degPerSqure_lon);
			}
		} else {
			System.out.println("Input Error...");
			return new EarthSqure(0, 0);
		}
	}
}
