package com.paymentApp.payment.service;

import java.util.List;

import com.paymentApp.payment.domain.TransactionDetail;
import com.paymentApp.payment.domain.TransactionType;
import com.paymentApp.payment.domain.UserInformation;

public interface TransactionDetailService {

	TransactionDetail list(String userId);

	TransactionDetail addTransactionDetail(TransactionDetail trnsactionoInfo);

	List<TransactionDetail> getAllTransactionDetails(String userId);

	TransactionDetail updateStatus(Boolean flag, long transactionId, String payerUserId, TransactionType transactionType);

	TransactionDetail getByTransactionId(long id);

	TransactionDetail getTransactionDetail(long id);

	TransactionDetail getByPayerUserId(String payerUserId);

	TransactionDetail getParkingTransactionDetail(TransactionDetail detail);

}
