package com.zhongli.dao;

import com.zhongli.model.TwetMsg;

/**
 * 数据库操作接口类
 * @author John
 *
 */
public interface TwetDAO {
	/**
	 * 向数据库中插入推特数据
	 * @param tweet
	 */
	public void insert(TwetMsg tweet);
	
	/**
	 * 向数据库中插入感情数据
	 * @param id
	 * @param motionType 感情种类
	 * @param confidence
	 */
	public void updateEmotion(int id,String motionType,double confidence) ;
	
	/**
	 * 根据消息ID获取推特
	 * @param id
	 * @return
	 */
	public TwetMsg findByID(int id);

	/**
	 * 返回最新的推特
	 * @return
	 */
	public TwetMsg findByMaxID();
	
}
