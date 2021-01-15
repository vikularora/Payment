package com.paymentApp.payment.util;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.paymentApp.payment.dto.PushNotificationRequest;

@Service
public class FCMService {

	private static final String FIREBASE_SERVER_KEY = "AAAAue5-zrM:APA91bFWAViDc2AQFrSyHiBGnN4-Ca0Yt42Hv0uKySh8AHJIsY5KQPcBJIAhV1RuHbcGjk_4-l94W7nGqM7OFCDavW1z19RXR6aWaSPUMnB1qs6QIC7DSG2XPKyDgv4CcTfpaiBJErJs";
	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

	public ResponseEntity<String> send(PushNotificationRequest entity) {

		HttpEntity<String> request = prepareDataForSend(entity);

		CompletableFuture<String> pushNotification = sendNotification(request);
		CompletableFuture.allOf(pushNotification).join();

		try {
			String firebaseResponse = pushNotification.get();
			return new ResponseEntity<String>(firebaseResponse, HttpStatus.OK);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<String>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);

	}

	private HttpEntity<String> prepareDataForSend(PushNotificationRequest entity) {
		JSONObject body = new JSONObject();
		body.put("to", entity.getToken());
		body.put("priority", "high");

		JSONObject notification = new JSONObject();
		notification.put("title", entity.getTitle());
		notification.put("body", entity.getMessage());

		JSONObject data = new JSONObject();
		data.put("Key-1", "JSA Data 1");
		data.put("Key-2", "JSA Data 2");

		body.put("notification", notification);
		body.put("data", data);

		HttpEntity<String> request = new HttpEntity<>(body.toString());
		return request;
	}

	@Async
	public CompletableFuture<String> sendNotification(HttpEntity<String> entity) {

		RestTemplate restTemplate = new RestTemplate();

		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);

		String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

		return CompletableFuture.completedFuture(firebaseResponse);
	}
}