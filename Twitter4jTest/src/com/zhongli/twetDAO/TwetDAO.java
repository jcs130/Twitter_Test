package com.zhongli.twetDAO;

import java.sql.Connection;

/**
 * 数据库操作接口类
 * @author John
 *
 */
public interface TwetDAO {	


	public void insert(TwetMsg tweet, Connection conn);

	public TwetMsg findByID(int id, Connection conn);
}
