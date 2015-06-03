package com.zhongli.twetDAO;

import java.util.ArrayList;

/**
 * 静态类，用于存储数据库一些参数
 * 
 * @author John
 *
 */
public class DaoSetting {

	private static ArrayList<LocArea> dbAreas = new ArrayList<LocArea>();

	public static ArrayList<LocArea> getDbAreas() {
		return dbAreas;
	}

	private DaoSetting() {
	};

	public static void init() {
		if (dbAreas.size() == 0) {
			LocArea dbArea1 = new LocArea(71.52490903732816, -11.953125,
					36.5978891330702, 52.03125, "twets1");
			LocArea dbArea2 = new LocArea(36.5978891330702, -19.6875,
					 -35.460669951495292, 52.03125, "twets2");
			LocArea dbArea3 = new LocArea(77.91566898632584, 52.03125,
					50.064191736659104, 180, "twets3");
			LocArea dbArea4 = new LocArea(50.064191736659104, 52.03125,
					0, 149.0625, "twets4");
			LocArea dbArea5 = new LocArea(0, 85.078125,
					-47.279229002570816, 180, "twets5");
			LocArea dbArea6 = new LocArea(78.76779175784321, -180,
					48.99463598353405, -11.953125, "twets6");
			LocArea dbArea7 = new LocArea(48.99463598353405, -128.3203125,
					 12.89748918375589, -51.328125, "twets7");
			LocArea dbArea8 = new LocArea( 12.89748918375589, -90.3515625,
					-55.97379820507658, -34.1015625, "twets8");
			dbAreas.add(dbArea1);
			dbAreas.add(dbArea2);
			dbAreas.add(dbArea3);
			dbAreas.add(dbArea4);
			dbAreas.add(dbArea5);
			dbAreas.add(dbArea6);
			dbAreas.add(dbArea7);
			dbAreas.add(dbArea8);
		}
	}

	/**
	 * 根据传入的经纬度确定坐标所在的数据库
	 * 
	 * @param lat
	 * @param lan
	 * @return
	 */
	public static String DBChooese(double lat, double lan) {
		System.out.println(dbAreas.size());
		for (int i = 0; i < dbAreas.size(); i++) {
			if (lat <= dbAreas.get(i).getNorth()
					&& lat > dbAreas.get(i).getSouth()
					&& lan >= dbAreas.get(i).getWest()
					&& lan < dbAreas.get(i).getEast()) {
				System.out.println(dbAreas.get(i).getLocName());
				return dbAreas.get(i).getLocName();
			}
		}
		System.out.println("twets9");
		return "twets9";
	}
}
