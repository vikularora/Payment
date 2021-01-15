package com.paymentApp.payment.dto;

public class UserConnectionDTO {

	private String connectionStatus;

	public String getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(String connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	@Override
	public String toString() {
		return "UserConnectionDTO [connectionStatus=" + connectionStatus + "]";
	}

}
