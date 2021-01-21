package com.paymentApp.payment.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paymentApp.payment.domain.TransactionDetail;
import com.paymentApp.payment.domain.UserInformation;
import com.paymentApp.payment.dto.PushNotificationRequest;
import com.paymentApp.payment.respository.UserInformationRepository;

@Component
public class CommonUtil {

	@Autowired
	UserInformationRepository userInfomationRepository;

	@Autowired
	FCMService fcmService;

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
		PushNotificationRequest request = new PushNotificationRequest();
		fcmService.send(request);
	}
}
