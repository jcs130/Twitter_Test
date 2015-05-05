package com.zhongli.controller;

import java.util.Arrays;

import com.zhongli.model.TwetMsg;

public class ShowMsg {
	private String userName;
	private String msg;
	private double[] location;

	public ShowMsg(TwetMsg tMsg) {
		this.userName = tMsg.getUserName();
		this.msg = tMsg.getMsg();
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

	@Override
	public String toString() {
		return "ShowMsg [userName=" + userName + ", msg=" + msg + ", location="
				+ Arrays.toString(location) + "]";
	}

}
