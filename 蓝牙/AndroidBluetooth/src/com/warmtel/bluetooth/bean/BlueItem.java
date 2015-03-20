package com.warmtel.bluetooth.bean;

public class BlueItem {
	public String message;
	public boolean isFrom;

	public BlueItem(String msg, boolean siri) {
		message = msg;
		isFrom = siri;
	}
}
