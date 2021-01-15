package com.paymentApp.payment.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymentApp.payment.dto.PushNotificationRequest;
import com.paymentApp.payment.util.FCMService;

@RestController
@RequestMapping("/fcm_notification/")
public class PushNotificationApi {

	@Autowired
	FCMService fcmService;

	@PostMapping("add")
	public ResponseEntity<String> sendPushNotification(@RequestBody PushNotificationRequest request) {

		return fcmService.send(request);

	}
}