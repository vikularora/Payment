package com.paymentApp.payment.api;

import java.util.List;

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

import com.paymentApp.payment.domain.TransactionDetail;
import com.paymentApp.payment.domain.UserInformation;
import com.paymentApp.payment.service.TransactionDetailService;

@RestController
@RequestMapping("/transaction/")
public class TransactionDetailApi {

	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private TransactionDetailService transactionDetailService;

	@CrossOrigin
	@GetMapping("{userId}")
	public Object list(@PathVariable(name = "userId", required = true) String userId) throws Exception {

		logger.info("CALLING USER LIST AND USERID IS :: " + userId);
		TransactionDetail user = transactionDetailService.list(userId);

		logger.info("User LIST ::" + user);

		if (user != null) {
			return new ResponseEntity<TransactionDetail>(user, HttpStatus.OK);
		}
		return new ResponseEntity<TransactionDetail>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("add")
	public Object addTransactionDetails(@RequestBody TransactionDetail trnsactionoInfo) throws Exception {

		logger.info("CALLING ADD :: " + trnsactionoInfo);
		TransactionDetail transaction = transactionDetailService.addTransactionDetail(trnsactionoInfo);

		logger.info("User RESPONSE ::" + transaction);

		if (transaction != null) {
			return new ResponseEntity<TransactionDetail>(transaction, HttpStatus.OK);
		} else {
			return new ResponseEntity<TransactionDetail>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("allTransactions/{userId}")
	public Object getAllTransactionByUserId(@PathVariable(name = "userId", required = true) String userId)
			throws Exception {

		logger.info("CALLING  ALL TRANSACTIONS USERID IS :: " + userId);
		List<TransactionDetail> user = transactionDetailService.getAllTransactionDetails(userId);

		logger.info("User LIST ::" + user);

		if (!user.isEmpty()) {
			return new ResponseEntity<List<TransactionDetail>>(user, HttpStatus.OK);
		}
		return new ResponseEntity<TransactionDetail>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("updateStatus")
	public Object updateTransactionStatus(@RequestBody TransactionDetail detail) throws Exception {

		logger.info("UPDATESTATUS ADD :: " + detail.isFlag());
		TransactionDetail info = transactionDetailService.updateStatus(detail.isFlag(), detail.getTransactionId(),
				detail.getPayerUserId(),detail.getTransactionType());

		logger.info("User RESPONSE ::" + info);

		if (info != null) {
			return new ResponseEntity<TransactionDetail>(info, HttpStatus.OK);
		} else {
			return new ResponseEntity<TransactionDetail>(HttpStatus.NO_CONTENT);
		}

	}

	@GetMapping("getByTransactionId/{id}")
	public Object getByTransactionId(@PathVariable(name = "id", required = true) long id) throws Exception {

		logger.info("CALLING getByTransactionId ID :: " + id);
		TransactionDetail transaction = transactionDetailService.getByTransactionId(id);

		logger.info("TRANSACTION ::" + transaction);

		if (transaction != null) {
			return new ResponseEntity<TransactionDetail>(transaction, HttpStatus.OK);
		}
		return new ResponseEntity<TransactionDetail>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("getTransaction/{id}")
	public Object getByTransactionDetail(@PathVariable(name = "id", required = true) long id) throws Exception {

		logger.info("CALLING getTransactionDetail ID :: " + id);
		TransactionDetail transaction = transactionDetailService.getTransactionDetail(id);

		logger.info("TRANSACTION ::" + transaction);

		if (transaction != null) {
			return new ResponseEntity<TransactionDetail>(transaction, HttpStatus.OK);
		}
		return new ResponseEntity<TransactionDetail>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("getByPayerUserId/{payerUserId}")
	public Object getByPayerUserId(@PathVariable(name = "payerUserId", required = true) String payerUserId)
			throws Exception {

		logger.info("CALLING getByPayerUserId :: " + payerUserId);
		TransactionDetail user = transactionDetailService.getByPayerUserId(payerUserId);

		logger.info("User LIST ::" + user);

		if (user != null) {
			return new ResponseEntity<TransactionDetail>(user, HttpStatus.OK);
		}
		return new ResponseEntity<TransactionDetail>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("getParkingTransactionDetail")
	public Object getParkingTransactionDetail(@RequestBody TransactionDetail detail) throws Exception {

		logger.info(" :: GETPARKINGTRANSACTIONDETAIL PUT :: ");
		TransactionDetail info = transactionDetailService.getParkingTransactionDetail(detail);

		logger.info("TRANSACTIONDETAIL RESPONSE ::" + info);

		if (info != null) {
			return new ResponseEntity<TransactionDetail>(info, HttpStatus.OK);
		} else {
			return new ResponseEntity<TransactionDetail>(HttpStatus.NO_CONTENT);
		}

	}

}
