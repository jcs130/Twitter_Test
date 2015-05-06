package com.zhongli.model;

import java.util.Arrays;

public class ShowMsg {
	private String userName;
	private String msg;
	private double[] location;
	private String emotion;

	public ShowMsg(TwetMsg tMsg) {
		this.userName = tMsg.getUserName();
		this.msg=" "+tMsg.getMsg().replace("'", "’").replace("\n", " ")+" ";
		this.location = tMsg.getLocation();
	}

	public String getUserName() {
		return userName;
	}

	public String getMsg() {
		return msg;
	}

	public double[] getLocation() {
		return location;
	}

	public String getEmotion() {
		return emotion;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}

	@Override
	public String toString() {
		return "ShowMsg [userName=" + userName + " , msg=" + msg + " , location="
				+ Arrays.toString(location) + ", emotion=" + emotion + "]";
	}
	
	

}
