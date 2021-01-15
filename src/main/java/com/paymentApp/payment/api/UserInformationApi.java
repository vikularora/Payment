package com.paymentApp.payment.api;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymentApp.payment.domain.UserInformation;
import com.paymentApp.payment.dto.UserConnectionDTO;
import com.paymentApp.payment.service.UserInformationService;

@RestController
@RequestMapping("/user/")
public class UserInformationApi {

	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private UserInformationService userInfoService;

	@CrossOrigin
	@GetMapping("{userId}")
	public Object list(@PathVariable(name = "userId", required = true) String userId,
			@RequestParam(name = "deviceId", required = true) String deviceId) throws Exception {

		logger.info("CALLING USER LIST AND USERID IS :: " + userId);
		UserInformation userList = userInfoService.list(userId, deviceId);

		logger.info("USER LIST ::" + userList);

		if (userList != null) {

			if (userList.isFlag()) {
				return new ResponseEntity<UserInformation>(HttpStatus.NOT_ACCEPTABLE);
			} else {
				return new ResponseEntity<UserInformation>(userList, HttpStatus.OK);
			}

		}
		return new ResponseEntity<UserInformation>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("add")
	public Object addUserDetails(@RequestBody UserInformation userInfo) throws Exception {

		logger.info("CALLING ADD :: " + userInfo);
		UserInformation info = userInfoService.addUserDetails(userInfo);

		logger.info("User RESPONSE ::" + info);

		if (info != null) {
			return new ResponseEntity<UserInformation>(info, HttpStatus.OK);
		}
		return new ResponseEntity<UserInformation>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("update/bluetoothList")
	public Object updateBluethoothList(@RequestBody UserInformation userInfo) throws Exception {

		logger.info("CALLING ADD :: " + userInfo);
		UserInformation info = userInfoService.updateBluethoothList(userInfo);

		logger.info("User RESPONSE ::" + info);

		return new ResponseEntity<UserInformation>(info, HttpStatus.OK);
	}

	@GetMapping("getPayee/{userId}")
	public Object getPayeeDetails(@PathVariable(name = "userId", required = true) String userId) throws Exception {

		logger.info("getPayeeDetails :: " + userId);
		ArrayList<UserInformation> userList = userInfoService.getRecevierDetail(userId);

		logger.info("User LIST ::" + userList);

		if (userList != null) {
			return new ResponseEntity<ArrayList<UserInformation>>(userList, HttpStatus.OK);
		}
		return new ResponseEntity<UserInformation>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("addUserDetail")
	public Object addUser(@RequestBody UserInformation userInfo) throws Exception {

		logger.info("CALLING ADDUSER :: " + userInfo);
		UserInformation info = userInfoService.addUser(userInfo);

		logger.info("User RESPONSE ::" + info);

		if (info != null) {
			return new ResponseEntity<UserInformation>(info, HttpStatus.OK);
		}
		return new ResponseEntity<UserInformation>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("getConnectionStatus/{userId}")
	public Object getConnectionStatus(@PathVariable(name = "userId", required = true) String userId) throws Exception {

		UserConnectionDTO userInfo = new UserConnectionDTO();
		logger.info("GETCONNECTIONSTATUS ADD :: " + userId);
		UserInformation info = userInfoService.list(userId);

		logger.info("User RESPONSE ::" + info);

		if (info != null) {
			userInfo.setConnectionStatus(info.getConnectionStatus());
			return new ResponseEntity<UserConnectionDTO>(userInfo, HttpStatus.OK);
		}
		return new ResponseEntity<UserConnectionDTO>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("update/ConnectionStatus")
	public Object updateConnectionStatus(@RequestBody UserInformation userInfo) throws Exception {

		logger.info("UPDATECONNECTIONSTATUS :: " + userInfo);
		UserInformation info = userInfoService.updateConnectionStatus(userInfo);

		logger.info("USER RESPONSE ::" + info);

		if (info != null) {
			return new ResponseEntity<UserInformation>(info, HttpStatus.OK);
		}
		return new ResponseEntity<UserInformation>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("logout/{userId}")
	public Object logoutUser(@PathVariable(name = "userId", required = true) String userId) throws Exception {

		logger.info("logoutUser :: " + userId);
		UserInformation info = userInfoService.logoutUser(userId);

		if (info != null) {
			return new ResponseEntity<UserInformation>(info, HttpStatus.OK);
		}
		return new ResponseEntity<UserInformation>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("update_device_login/{userId}")
	public Object loginLogout(@PathVariable(name = "userId", required = true) String userId,
			@RequestParam(name = "deviceId", required = true) String deviceId) throws Exception {

		logger.info("CALLING USER loginLogout:: " + userId);
		UserInformation user = userInfoService.updateDeviceAndLogin(userId, deviceId);

		logger.info("USER LIST ::" + user);

		if (user != null) {

			return new ResponseEntity<UserInformation>(user, HttpStatus.OK);
		}
		return new ResponseEntity<UserInformation>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("logout_device/{userId}")
	public Object logoutUserDevice(@PathVariable(name = "userId", required = true) String userId) throws Exception {

		logger.info("logoutUser :: " + userId);
		UserInformation info = userInfoService.logoutUserFromDevice(userId);

		if (info != null) {
			return new ResponseEntity<UserInformation>(info, HttpStatus.OK);
		}
		return new ResponseEntity<UserInformation>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("getByUserId/{userId}")
	public Object getByUserId(@PathVariable(name = "userId", required = true) String userId) throws Exception {

		logger.info("CALLING getByUserId USERID IS :: " + userId);
		UserInformation userList = userInfoService.getByUserId(userId);

		logger.info("USER LIST ::" + userList);

		if (userList != null) {
			return new ResponseEntity<UserInformation>(userList, HttpStatus.OK);
		}
		return new ResponseEntity<UserInformation>(HttpStatus.NO_CONTENT);
	}
	
	
	@GetMapping("getNearByUsers/{userId}")
	public Object getNearByUsers(@PathVariable(name = "userId", required = true) String userId) throws Exception {

		logger.info("getPayeeDetails :: " + userId);
		ArrayList<UserInformation> userList = userInfoService.getNearByUsers(userId);

		logger.info("User LIST ::" + userList);

		if (userList != null) {
			return new ResponseEntity<ArrayList<UserInformation>>(userList, HttpStatus.OK);
		}
		return new ResponseEntity<UserInformation>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("updateFCMToken/{userId}")
	public Object updateFCMToken(@PathVariable(name = "userId", required = true) String userId,
			@RequestParam(name = "fireBaseToken", required = true) String fireBaseToken) throws Exception {

		logger.info("CALLING USER LIST AND USERID IS :: " + userId);
		UserInformation info = userInfoService.updateFCMToken(userId, fireBaseToken);

		logger.info("User RESPONSE ::" + info);

		return new ResponseEntity<UserInformation>(info, HttpStatus.OK);
	}

}
