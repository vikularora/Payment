package com.paymentApp.payment.respository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.paymentApp.payment.domain.TransactionDetail;

@Repository
public interface TransactionDetailRepository extends PagingAndSortingRepository<TransactionDetail, Long> {

	TransactionDetail findTopByUserIdOrderByTransactionIdDesc(String user);

	List<TransactionDetail> getByUserId(String userId);

	TransactionDetail findByTransactionId(long transactionId);

	List<TransactionDetail> findByTransactionDateTimeGreaterThanOrderByTransactionDateTimeDesc(long datetime);

//	List<TransactionDetail> getByUserIdOrderByTransactionIdDesc(String userId);
	List<TransactionDetail> getByUserIdOrPayerUserIdOrderByTransactionIdDesc(String userId, String payerUserId);

	TransactionDetail findTopByPayerUserIdOrderByTransactionIdDesc(String payerUserId);
	
	TransactionDetail findTopByPayerUserIdAndUserIdOrderByTransactionIdDesc(String payerUserId, String userId);

	List<TransactionDetail> findByDeliveryUserId(String userId);

	List<TransactionDetail> findByDeliveryUserIdOrderByTransactionIdDesc(String userId);

	List<TransactionDetail> findByAppOwnerUserIdOrderByTransactionIdDesc(String userId);

	List<TransactionDetail> findByMerchantUserIdOrPayerUserIdOrderByTransactionIdDesc(String userId, String userId1);

}
