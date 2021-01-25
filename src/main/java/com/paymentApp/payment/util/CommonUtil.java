package com.paymentApp.payment.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paymentApp.payment.domain.TransactionDetail;
import com.paymentApp.payment.domain.UserInformation;
import com.paymentApp.payment.dto.PushNotificationRequest;
import com.paymentApp.payment.respository.UserInformationRepository;
import com.paymentApp.payment.service.impl.TransactionDetailServiceImpl;

@Component
public class CommonUtil {

	@Autowired
	UserInformationRepository userInfomationRepository;

	@Autowired
	FCMService fcmService;

	private final static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	public void prepareCallForNotification(TransactionDetail transactionDetail) throws Exception {
		UserInformation userInformationDetail = userInfomationRepository.findByUserId(transactionDetail.getUserId());

		if (userInformationDetail != null && userInformationDetail.getFireBaseToken() != null) {
			if (transactionDetail.getPayerUserId() != null) {

				sendRequestForNotification(PaymentConstants.INVOICE, PaymentConstants.INVOICE_GEN_SUCCESS,
						userInformationDetail.getFireBaseToken());
			} else if (transactionDetail.getUserId() != null) {

				sendRequestForNotification(PaymentConstants.INVOICE, PaymentConstants.INVOICE_GEN_SUCCESS,
						userInformationDetail.getFireBaseToken());
			} else if (transactionDetail.getDeliveryUserId() != null) {

				sendRequestForNotification(PaymentConstants.INVOICE, PaymentConstants.INVOICE_GEN_SUCCESS,
						userInformationDetail.getFireBaseToken());
			} else if (transactionDetail.getAppOwnerUserId() != null) {

				sendRequestForNotification(PaymentConstants.INVOICE, PaymentConstants.INVOICE_GEN_SUCCESS,
						userInformationDetail.getFireBaseToken());
			} else if (transactionDetail.getMerchantUserId() != null) {

				sendRequestForNotification(PaymentConstants.INVOICE, PaymentConstants.INVOICE_GEN_SUCCESS,
						userInformationDetail.getFireBaseToken());
			}
		} else {
			throw new Exception("UserInfo Not found");
		}
	}

	private void sendRequestForNotification(String title, String message, String fireBaseToken) {
		// Send Notification
		logger.info("IN SEND REQUEST FOR NOTIFICATION , TITTLE:" + title + " :: MESSAGE :" + message
				+ " :: FIREBASE TOKEN: " + fireBaseToken);
		PushNotificationRequest request = new PushNotificationRequest();
		fcmService.send(request);
	}
}
