package com.paymentApp.payment.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymentApp.payment.domain.TransactionDetail;
import com.paymentApp.payment.domain.UserInformation;
import com.paymentApp.payment.dto.PushNotificationRequest;
import com.paymentApp.payment.respository.TransactionDetailRepository;
import com.paymentApp.payment.respository.UserInformationRepository;
import com.paymentApp.payment.service.UserInformationService;
import com.paymentApp.payment.util.PaymentConstants;

@Service
public class UserInformationServiceImpl implements UserInformationService {

	@Autowired
	UserInformationRepository userInformationRepository;

	@Autowired
	TransactionDetailRepository detailRepository;

//	@Autowired
//	FCMService fcmService;
//
//	@Autowired
//	CommonUtil commonUtil;

	private final static Logger logger = LoggerFactory.getLogger(UserInformationServiceImpl.class);

	@Override
	public UserInformation list(String userId) {

		return userInformationRepository.findByUserId(userId);
	}

//	@Override
//	public UserInformation list(String userId) {
//
//		UserInformation user = userInformationRepository.findByUserId(userId);
//
//		if (user != null) {
//
//			UserInformation tempUser = new UserInformation();
//			try {
//				tempUser = (UserInformation) user.clone();
//			} catch (CloneNotSupportedException e) {
//				e.printStackTrace();
//			}
////			tempUser = user;
//
//			if (user.getLoginStatus().equalsIgnoreCase("Login")) {
//			} else {
//				user.setLoginStatus("Login");
//			}
//			userInformationRepository.save(user);
//			return tempUser;
//		} else {
//
//			user = userInformationRepository.findByAccountName(userId);
//			if (user != null) {
//
//				UserInformation tempUser = new UserInformation();
//				try {
//					tempUser = (UserInformation) user.clone();
//				} catch (CloneNotSupportedException e) {
//					e.printStackTrace();
//				}
////				tempUser = user;
//
//				if (user.getLoginStatus().equalsIgnoreCase("Login")) {
//				} else {
//					user.setLoginStatus("Login");
//				}
//				userInformationRepository.save(user);
//				return tempUser;
//			}
//			return null;
//		}
//
//	}

	@Override
	public UserInformation list(String userId, String deviceId) {

		UserInformation user = userInformationRepository.findByUserId(userId);

		if (user != null) {

			if (user.getDeviceId() == null) {
				user.setDeviceId(deviceId);
				return userInformationRepository.save(user);
			}

			if (!deviceId.equalsIgnoreCase(user.getDeviceId())) {
				user.setFlag(true);
			} else {
				user.setFlag(false);
			}

			prepareForNotification(user);
			return user;
		} else {

			user = userInformationRepository.findByAccountName(userId);
			if (user != null) {

				if (user.getDeviceId() == null) {
					user.setDeviceId(deviceId);
					return userInformationRepository.save(user);
				}

				if (!deviceId.equalsIgnoreCase(user.getDeviceId())) {
					user.setFlag(true);
				} else {
					user.setFlag(false);
				}
				prepareForNotification(user);
				return user;
			}
			return null;
		}

	}

	private void prepareForNotification(UserInformation user) {

		logger.info("IN PREPARE FOR NOTIFICATION");

		if (user != null && user.getFireBaseToken() != null) {

			PushNotificationRequest request = new PushNotificationRequest();
			request.setMessage(PaymentConstants.INVOICE_GEN_SUCCESS_TTL);
			request.setTitle(PaymentConstants.INVOICE_PAID_SUCCESS_USER_MSG);
			request.setToken(user.getFireBaseToken());

//			ResponseEntity<String> response = fcmService.send(request);
//			logger.info("RESPONSE :: " + response);
		} else {
			logger.error("ERROR :: FIREBASE TOKEN NOT FOUND :" + user);
		}

	}

	@Override
	public UserInformation addUserDetails(UserInformation userInfo) {

		// check if userId exists
		UserInformation info = userInformationRepository.findByUserId(userInfo.getUserId());

		if (info != null) {

			userInformationRepository.updateByUserId(userInfo.getLattitude(), userInfo.getLongitute(),
					userInfo.getUserId());

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			UserInformation user = userInformationRepository.getUserInformationByUserId(info.getUserId());
			return user;
		}

		return null;
	}

	@Override
	public UserInformation updateBluethoothList(UserInformation userInfo) throws Exception {

		System.out.println("LIST ::  " + userInfo.getBlutoothList());
		System.out.println("LIST ::  " + userInfo.getUserId());

		UserInformation info = userInformationRepository.findByUserId(userInfo.getUserId());
		if (info != null) {
			info.setBlutoothList(userInfo.getBlutoothList());
			return userInformationRepository.save(info);
		} else {
			throw new Exception("USER ID " + userInfo.getUserId() + " not Exists");
		}
	}

	@Override
	public ArrayList<UserInformation> getRecevierDetail(String userId) {

		long currentmillis = System.currentTimeMillis() - 600000;

		System.out.println(" CURRENT TIME :: " + currentmillis);

		Set<String> userSet = new HashSet<String>();
		UserInformation userInformation = userInformationRepository.findByUserId(userId);
//		ArrayList<String> blueList = userInformation.getBlutoothList();
		ArrayList<UserInformation> userInfomationList = new ArrayList<UserInformation>();
		Iterable<UserInformation> userInfomationIterable = userInformationRepository.findAll();

		List<UserInformation> userInfoList = StreamSupport.stream(userInfomationIterable.spliterator(), false)
				.collect(Collectors.toList());

		for (UserInformation user : userInfoList) {

			if (user.getUserId().equalsIgnoreCase(userId)) {
				continue;
			}

//			if (blueList.contains(user.getBluetoothId())) {

			List<TransactionDetail> transactions = detailRepository
					.findByTransactionDateTimeGreaterThanOrderByTransactionDateTimeDesc(currentmillis);

			user.setDistance(distance(userInformation.getLattitude(), userInformation.getLongitute(),
					user.getLattitude(), user.getLongitute()));
			if (user.getDistance() <= 200) {
				for (TransactionDetail transaction : transactions) {
					if (transaction.getStatus().equalsIgnoreCase("Pending")) {
						if (transaction.getUserId().equalsIgnoreCase(user.getUserId())) {

							if (userSet.contains(transaction.getUserId())) {
								continue;
							}
							userSet.add(user.getUserId());
							userInfomationList.add(user);
						}
					}
				}

			}
//				userInfomationList.add(user);

//			}
		}

		return userInfomationList;

	}

	private static double distance(double lat1, double lon1, double lat2, double lon2) {
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		} else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
					+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			dist = dist * 1609.344;

			return (dist);
		}
	}

	@Override
	public UserInformation addUser(UserInformation userInfo) {

		userInfo.setConnectionStatus("Disconnected");
		return userInformationRepository.save(userInfo);

	}

	@Override
	public UserInformation updateConnectionStatus(UserInformation userInfo) {

		UserInformation user = userInformationRepository.findByUserId(userInfo.getUserId());

		if (user != null) {
			user.setConnectionStatus(userInfo.getConnectionStatus());
			return userInformationRepository.save(user);
		}
		return null;
	}

	@Override
	public UserInformation logoutUser(String userId) {

		UserInformation user = userInformationRepository.findByUserId(userId);

		if (user != null) {
			user.setLoginStatus("Logout");
			return userInformationRepository.save(user);
		}
		return null;
	}

	@Override
	public UserInformation updateDeviceAndLogin(String userId, String deviceId) {

		UserInformation user = userInformationRepository.findByUserId(userId);

		if (user != null) {
			user.setDeviceId(deviceId);
			return userInformationRepository.save(user);
		} else {
			user = userInformationRepository.findByAccountName(userId);
			if (user != null) {
				user.setDeviceId(deviceId);
				return userInformationRepository.save(user);
			}
		}
		return null;
	}

	@Override
	public UserInformation logoutUserFromDevice(String userId) {

		UserInformation user = userInformationRepository.findByUserId(userId);
		if (user != null) {
			user.setDeviceId(null);
			return userInformationRepository.save(user);
		}
		return null;
	}

	@Override
	public UserInformation getByUserId(String userId) {

		return userInformationRepository.findByUserId(userId);
	}

	@Override
	public ArrayList<UserInformation> getNearByUsers(String userId) {

		UserInformation userInformation = userInformationRepository.findByUserId(userId);
//		ArrayList<String> blueList = userInformation.getBlutoothList();
		ArrayList<UserInformation> userInfomationList = new ArrayList<UserInformation>();
		Iterable<UserInformation> userInfomationIterable = userInformationRepository.findAll();

		List<UserInformation> userInfoList = StreamSupport.stream(userInfomationIterable.spliterator(), false)
				.collect(Collectors.toList());

		for (UserInformation user : userInfoList) {

			if (user.getUserId().equalsIgnoreCase(userId)) {
				continue;
			}

//			if (blueList.contains(user.getBluetoothId())) {

			user.setDistance(distance(userInformation.getLattitude(), userInformation.getLongitute(),
					user.getLattitude(), user.getLongitute()));
			if (user.getDistance() <= 200) {

				userInfomationList.add(user);
			}

		}
//				userInfomationList.add(user);

//			}

		return userInfomationList;
	}

	@Override
	public UserInformation updateFCMToken(String userId, String fireBaseToken) throws Exception {
		UserInformation user = userInformationRepository.findByUserId(userId);

		if (user != null) {
			user.setFireBaseToken(fireBaseToken);
			return userInformationRepository.save(user);
		}
		return null;

	}

}
