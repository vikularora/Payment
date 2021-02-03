//package com.paymentApp.payment.util;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.paymentApp.payment.domain.TransactionDetail;
//import com.paymentApp.payment.domain.UserInformation;
//import com.paymentApp.payment.dto.PushNotificationRequest;
//import com.paymentApp.payment.respository.UserInformationRepository;
//
//@Component
//public class CommonUtil {
//
//	@Autowired
//	UserInformationRepository userInfomationRepository;
//
//	@Autowired
//	FCMService fcmService;
//
//	private final static Logger logger = LoggerFactory.getLogger(CommonUtil.class);
//
//	public void prepareCallForNotification(TransactionDetail transactionDetail) throws Exception {
//		UserInformation userInformationDetail = userInfomationRepository.findByUserId(transactionDetail.getUserId());
//
//		if (userInformationDetail != null && userInformationDetail.getFireBaseToken() != null) {
//			if (transactionDetail.getPayerUserId() != null) {
//
//				sendRequestForNotification(PaymentConstants.INVOICE_TTL, PaymentConstants.INVOICE_GEN_SUCCESS_TTL,
//						userInformationDetail.getFireBaseToken());
//			} else if (transactionDetail.getUserId() != null) {
//
//				sendRequestForNotification(PaymentConstants.INVOICE_TTL, PaymentConstants.INVOICE_GEN_SUCCESS_TTL,
//						userInformationDetail.getFireBaseToken());
//			} else if (transactionDetail.getDeliveryUserId() != null) {
//
//				sendRequestForNotification(PaymentConstants.INVOICE_TTL, PaymentConstants.INVOICE_GEN_SUCCESS_TTL,
//						userInformationDetail.getFireBaseToken());
//			} else if (transactionDetail.getAppOwnerUserId() != null) {
//
//				sendRequestForNotification(PaymentConstants.INVOICE_TTL, PaymentConstants.INVOICE_GEN_SUCCESS_TTL,
//						userInformationDetail.getFireBaseToken());
//			} else if (transactionDetail.getMerchantUserId() != null) {
//
//				sendRequestForNotification(PaymentConstants.INVOICE_TTL, PaymentConstants.INVOICE_GEN_SUCCESS_TTL,
//						userInformationDetail.getFireBaseToken());
//			}
//		} else {
//			throw new Exception("UserInfo Not found");
//		}
//	}
//
//	public void sendRequestForNotification(String title, String message, String fireBaseToken) {
//		// Send Notification
//		logger.info("IN SEND REQUEST FOR NOTIFICATION , TITTLE:" + title + " :: MESSAGE :" + message
//				+ " :: FIREBASE TOKEN: " + fireBaseToken);
//
//		PushNotificationRequest request = new PushNotificationRequest();
//		request.setMessage(message);
//		request.setToken(fireBaseToken);
//		request.setTitle(title);
//		fcmService.send(request);
//	}
//
//	public void prepareCallForNotificationForUpdateStatus(TransactionDetail transactionDetail, long serviceType)
//			throws Exception {
//		UserInformation userInformationDetail = userInfomationRepository.findByUserId(transactionDetail.getUserId());
//
//		if (userInformationDetail != null && userInformationDetail.getFireBaseToken() != null) {
//
//			if (serviceType == 1) {
//				sendRequestForNotification(PaymentConstants.PAYMENT_RECEVIED_TTL,
//						PaymentConstants.INVOICE_PAID_SUCCESS_USER_MSG, userInformationDetail.getFireBaseToken());
//
//			} else if (serviceType == 2) {
//				sendRequestForNotification(PaymentConstants.PAYMENT_RECEVIED_TTL,
//						PaymentConstants.INVOICE_PAID_SUCCESS_USER_MSG, userInformationDetail.getFireBaseToken());
//
//			} else if (serviceType == 3) {
//				sendRequestForNotification(PaymentConstants.PAYMENT_RECEVIED_TTL,
//						PaymentConstants.INVOICE_PAID_SUCCESS_USER_MSG, userInformationDetail.getFireBaseToken());
//				sendRequestForNotification(PaymentConstants.PAYMENT_RECEVIED_TTL,
//						PaymentConstants.COMMISSION_RECEIVED_MSG, userInformationDetail.getFireBaseToken());
//
//			} else if (serviceType == 4) {
//				sendRequestForNotification(PaymentConstants.PAYMENT_RECEVIED_TTL,
//						PaymentConstants.INVOICE_PAID_SUCCESS_USER_MSG, userInformationDetail.getFireBaseToken());
//
//			} else if (serviceType == 5) {
//				sendRequestForNotification(PaymentConstants.PAYMENT_RECEVIED_TTL,
//						PaymentConstants.INVOICE_PAID_SUCCESS_USER_MSG, userInformationDetail.getFireBaseToken());
//				sendRequestForNotification(PaymentConstants.PAYMENT_RECEVIED_TTL,
//						PaymentConstants.COMMISSION_RECEIVED_MSG, userInformationDetail.getFireBaseToken());
//
//			} else if (serviceType == 6) {
//				sendRequestForNotification(PaymentConstants.PAYMENT_RECEVIED_TTL, PaymentConstants.PAYMENT_RECEIVED_MSG,
//						userInformationDetail.getFireBaseToken());
//				sendRequestForNotification(PaymentConstants.PARKING_TIME_END_TTL, PaymentConstants.PARKING_TIME_END_MSG,
//						userInformationDetail.getFireBaseToken());
//			}
//
//			// send notification to app owner
//			sendRequestForNotification(PaymentConstants.PAYMENT_RECEVIED_TTL, PaymentConstants.COMMISSION_RECEIVED_MSG,
//					userInformationDetail.getFireBaseToken());
//
//		} else {
//			throw new Exception("UserInfo Not found");
//		}
//	}
//
//	public void prepareCallForTransaction(TransactionDetail transactionDetail, long serviceType) throws Exception {
//		UserInformation userInformationDetail = userInfomationRepository.findByUserId(transactionDetail.getUserId());
//
//		if (userInformationDetail != null && userInformationDetail.getFireBaseToken() != null) {
//
//			if (serviceType == 1) {
//				sendRequestForNotification(PaymentConstants.INVOICE_CREATED_SUCESSFULLY_TTL,
//						PaymentConstants.PAYMENT_CREATED_MSG, userInformationDetail.getFireBaseToken());
//
//			} else if (serviceType == 2) {
//				if (transactionDetail.getPayerUserId() != null) {
//
//					sendRequestForNotification(PaymentConstants.INVOICE_CREATED_BROADCASTED_TTL,
//							PaymentConstants.INVOICE_CREATED_WITH_PAYER_MSG, userInformationDetail.getFireBaseToken());
//
//				} else {
//					sendRequestForNotification(PaymentConstants.INVOICE_CREATED_BROADCASTED_TTL,
//							PaymentConstants.INVOICE_CREATED_WITHOUT_PAYER_MSG,
//							userInformationDetail.getFireBaseToken());
//				}
//			} else if (serviceType == 3) {
//				sendRequestForNotification(PaymentConstants.INVOICE_CREATED_SUCESSFULLY_TTL,
//						PaymentConstants.PAYMENT_CREATED_MSG, userInformationDetail.getFireBaseToken());
//				
//				sendRequestForNotification(PaymentConstants.INVOICE_RECEIVED_TTL,
//						PaymentConstants.INVOICE_GEN_WITH_SCAN_MSG, userInformationDetail.getFireBaseToken());
//				
//				sendRequestForNotification(PaymentConstants.INVOICE_PENDING_TTL,
//						PaymentConstants.PAYMENT_GEN_TO_PAYER_MSG, userInformationDetail.getFireBaseToken());
//				
//				
//
//			} else if (serviceType == 4) {
//				sendRequestForNotification(PaymentConstants.INVOICE_CREATED_SUCESSFULLY_TTL,
//						PaymentConstants.PAYMENT_CREATED_MSG, userInformationDetail.getFireBaseToken());
//
//			} else if (serviceType == 5) {
//				sendRequestForNotification(PaymentConstants.INVOICE_CREATED_SUCESSFULLY_TTL,
//						PaymentConstants.PAYMENT_CREATED_MSG, userInformationDetail.getFireBaseToken());
//
//			} else if (serviceType == 6) {
//				sendRequestForNotification(PaymentConstants.INVOICE_CREATED_SUCESSFULLY_TTL,
//						PaymentConstants.PAYMENT_CREATED_MSG, userInformationDetail.getFireBaseToken());
//			}
//
//		} else {
//			throw new Exception("UserInfo Not found");
//		}
//	}
//
//}
