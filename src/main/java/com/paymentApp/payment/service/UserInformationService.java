package com.paymentApp.payment.service;

import java.util.ArrayList;

import com.paymentApp.payment.domain.UserInformation;

public interface UserInformationService {

	UserInformation list(String userId, String bluetoothId);

	UserInformation addUserDetails(UserInformation userInfo);

	UserInformation updateBluethoothList(UserInformation userInfo) throws Exception;

	ArrayList<UserInformation> getRecevierDetail(String userId);

	UserInformation addUser(UserInformation userInfo);

	UserInformation updateConnectionStatus(UserInformation userInfo);

	UserInformation logoutUser(String userId);

	UserInformation list(String userId);

	UserInformation updateDeviceAndLogin(String userId, String deviceId);

	UserInformation logoutUserFromDevice(String userId);

	UserInformation getByUserId(String userId);

	ArrayList<UserInformation> getNearByUsers(String userId);
	
	UserInformation updateFCMToken(String userId, String fireBaseToken) throws Exception;
}
