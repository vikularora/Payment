package com.paymentApp.payment.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymentApp.payment.domain.TransactionDetail;
import com.paymentApp.payment.domain.TransactionType;
import com.paymentApp.payment.domain.UserInformation;
import com.paymentApp.payment.respository.TransactionDetailRepository;
import com.paymentApp.payment.respository.UserInformationRepository;
import com.paymentApp.payment.service.TransactionDetailService;

@Service
public class TransactionDetailServiceImpl implements TransactionDetailService {

	private final static Logger logger = LoggerFactory.getLogger(TransactionDetailServiceImpl.class);

	@Autowired
	TransactionDetailRepository transactionDetailRepository;

	@Autowired
	UserInformationRepository userInfomationRepository;

	@Override
	public TransactionDetail list(String userId) {

		TransactionDetail detail = transactionDetailRepository.findTopByUserIdOrderByTransactionIdDesc(userId);

		if (detail != null && detail.getStatus().equalsIgnoreCase("Pending")) {
			return detail;
		}
		return null;

	}

	@Override
	public TransactionDetail addTransactionDetail(TransactionDetail transactionDetail) {

		transactionDetail.setStatus("Pending");
		transactionDetail
				.setDiscountedAmount(transactionDetail.getAmount() * ((100 - transactionDetail.getDiscount()) / 100));

		if (transactionDetail.getDiscountedAmount() == 0.0) {
			transactionDetail.setDiscountedAmount(transactionDetail.getAmount());
		}
		if (transactionDetail.getServiceType() != null && transactionDetail.getServiceType().getId() == 1) {
			TransactionDetail transDetail = transactionDetailRepository
					.findTopByPayerUserIdAndUserIdOrderByTransactionIdDesc(transactionDetail.getPayerUserId(),
							transactionDetail.getUserId());

			if (transDetail != null && transDetail.getStatus() != null
					&& transDetail.getStatus().equalsIgnoreCase("Pending")) {
				return null;
			}
		}

		return transactionDetailRepository.save(transactionDetail);

	}

	@Override
	public List<TransactionDetail> getAllTransactionDetails(String userId) {

		UserInformation user = userInfomationRepository.findByUserId(userId);

		if (user.getUserRole().getId() == 1) {

			return transactionDetailRepository.findByDeliveryUserIdOrderByTransactionIdDesc(userId);
		} else if (user.getUserRole().getId() == 2) {
			return transactionDetailRepository.findByAppOwnerUserIdOrderByTransactionIdDesc(userId);
		} else if (user.getUserRole().getId() == 4) {
			return transactionDetailRepository.findByMerchantUserIdOrPayerUserIdOrderByTransactionIdDesc(userId,
					userId);
		}

		return transactionDetailRepository.getByUserIdOrPayerUserIdOrderByTransactionIdDesc(userId, userId);

	}

	@Override
	public TransactionDetail updateStatus(Boolean flag, long transactionId, String payerUserId,
			TransactionType transType) {

		logger.info(":: IN updateStatus ::" + transactionId + " " + "flag " + flag + " payerUserId :: " + payerUserId);

		TransactionDetail transactionDetail = transactionDetailRepository.findByTransactionId(transactionId);

		logger.info(":: IN TRANSACTION DETAIL ::" + transactionDetail);

		transactionDetail.setPayerUserId(payerUserId);
		transactionDetail.setTransactionType(transType);

		if (transactionDetail.getAppOwnerUserId() == null) {
			transactionDetail.setAppOwnerUserId("AOR01-01");
		}

		if (!transactionDetail.getStatus().equalsIgnoreCase("Pending")) {
			logger.info(":: NOT PENDING ::" + transactionDetail);
			return null;
		}

		if (flag) {

			UserInformation userInformationDetail = userInfomationRepository
					.findByUserId(transactionDetail.getUserId());

			UserInformation userInformationPayer = userInfomationRepository
					.findByUserId(transactionDetail.getPayerUserId());

			UserInformation userInformationOwner = userInfomationRepository
					.findByUserId(transactionDetail.getAppOwnerUserId());

			UserInformation userInformatinDelivery = userInfomationRepository
					.findByUserId(transactionDetail.getDeliveryUserId());

			UserInformation userInformationMerchant = userInfomationRepository
					.findByUserId(transactionDetail.getMerchantUserId());

			if (transactionDetail.getServiceType().getId() == 1) {
				// Deduct balance from payee account
				deductAmountFromPayeeAccount(userInformationPayer, transactionDetail);

				// Add amount in Owner Account
				AddAmountInOwnerAccount(userInformationOwner, transactionDetail);

				// Add amount in receiver account
				double amountReciever = userInformationDetail.getAccountBalance()
						+ transactionDetail.getDiscountedAmount() - transactionDetail.getDiscountedAmount() * 0.0018;
				userInformationDetail.setAccountBalance(amountReciever);
				transactionDetail.setReceivedAmount(
						transactionDetail.getDiscountedAmount() - transactionDetail.getDiscountedAmount() * 0.0018);

				userInfomationRepository.save(userInformationDetail);

			} else if (transactionDetail.getServiceType().getId() == 3) {

				// Deduct balance from payee account
				deductAmountFromPayeeAccount(userInformationPayer, transactionDetail);

				// Add amount in Owner Account
				AddAmountInOwnerAccount(userInformationOwner, transactionDetail);

				// Add Amount in Deleviery Boy Account
				double amountDelivered = userInformatinDelivery.getAccountBalance() + 1.5;
				userInformatinDelivery.setAccountBalance(amountDelivered);
				userInfomationRepository.save(userInformatinDelivery);
				transactionDetail.setDeveileryUserAmount(1.5);

				// Add amount in reciever account
				double amountReciever = userInformationDetail.getAccountBalance()
						+ transactionDetail.getDiscountedAmount() - transactionDetail.getDiscountedAmount() * 0.0018
						- 1.5;
				userInformationDetail.setAccountBalance(amountReciever);
				transactionDetail.setReceivedAmount(transactionDetail.getDiscountedAmount()
						- transactionDetail.getDiscountedAmount() * 0.0018 - 1.5);
				userInfomationRepository.save(userInformationDetail);

			} else if (transactionDetail.getServiceType().getId() == 4) {

				// Deduct balance from payee account
				deductAmountFromPayeeAccount(userInformationPayer, transactionDetail);

				// Add amount in Owner Account
				AddAmountInOwnerAccount(userInformationOwner, transactionDetail);

				// Add amount in reciever account
				double amountReciever = userInformationDetail.getAccountBalance()
						+ transactionDetail.getDiscountedAmount() - transactionDetail.getDiscountedAmount() * 0.0018;
				userInformationDetail.setAccountBalance(amountReciever);
				transactionDetail.setReceivedAmount(
						transactionDetail.getDiscountedAmount() - transactionDetail.getDiscountedAmount() * 0.0018);
				userInfomationRepository.save(userInformationDetail);

			} else if (transactionDetail.getServiceType().getId() == 5) {
				// Deduct balance from payee account
				deductAmountFromPayeeAccount(userInformationPayer, transactionDetail);

				// Add amount in Owner Account
				AddAmountInOwnerAccount(userInformationOwner, transactionDetail);

				// Add amount in Merchant Account
				double amountMerchant = userInformationMerchant.getAccountBalance()
						+ transactionDetail.getDiscountedAmount() * 0.1;
				userInformationMerchant.setAccountBalance(amountMerchant);
				userInfomationRepository.save(userInformationMerchant);

				transactionDetail.setMerchantAmount(transactionDetail.getDiscountedAmount() * 0.1);

				// Add amount in reciever account
				double amountReciever = userInformationDetail.getAccountBalance()
						+ transactionDetail.getDiscountedAmount() - transactionDetail.getDiscountedAmount() * 0.0018
						- transactionDetail.getDiscountedAmount() * 0.1;

				userInformationDetail.setAccountBalance(amountReciever);
				transactionDetail.setReceivedAmount(
						transactionDetail.getDiscountedAmount() - transactionDetail.getDiscountedAmount() * 0.0018
								- transactionDetail.getDiscountedAmount() * 0.1);

				userInfomationRepository.save(userInformationDetail);

			} else {
				double amount = userInformationDetail.getAccountBalance() + transactionDetail.getDiscountedAmount();
				userInformationDetail.setAccountBalance(amount);

				transactionDetail.setReceivedAmount(transactionDetail.getDiscountedAmount());
				userInfomationRepository.save(userInformationDetail);

				double amountPayer = userInformationPayer.getAccountBalance() - transactionDetail.getDiscountedAmount();
				userInformationPayer.setAccountBalance(amountPayer);

				userInfomationRepository.save(userInformationPayer);
			}

			transactionDetail.setStatus("Confirmed");
		} else {
			transactionDetail.setStatus("Rejected");
		}

		transactionDetailRepository.save(transactionDetail);

		return transactionDetail;
	}

	private void deductAmountFromPayeeAccount(UserInformation userInformationPayer,
			TransactionDetail transactionDetail) {

		double amountPayer = userInformationPayer.getAccountBalance() - transactionDetail.getDiscountedAmount();
		userInformationPayer.setAccountBalance(amountPayer);
		userInfomationRepository.save(userInformationPayer);

	}

	private void AddAmountInOwnerAccount(UserInformation userInformationOwner, TransactionDetail transactionDetail) {

		double ownerAmount = userInformationOwner.getAccountBalance()
				+ transactionDetail.getDiscountedAmount() * 0.0018;
		userInformationOwner.setAccountBalance(ownerAmount);
		userInfomationRepository.save(userInformationOwner);

		transactionDetail.setAppOwnerAmount(transactionDetail.getDiscountedAmount() * 0.0018);
	}

	@Override
	public TransactionDetail getByTransactionId(long id) {

		long currentmillis = System.currentTimeMillis() - 600000;
		Optional<TransactionDetail> tranDetail = transactionDetailRepository.findById(id);

//		if (tranDetail != null && tranDetail.get().getTransactionDateTime() > currentmillis) {

		if (tranDetail.isPresent() && tranDetail.get().getStatus().equalsIgnoreCase("Pending")) {
			return tranDetail.get();
		}

//		}

		return null;
	}

	@Override
	public TransactionDetail getTransactionDetail(long id) {

		Optional<TransactionDetail> tranDetail = transactionDetailRepository.findById(id);

		if (tranDetail.isPresent()) {

			return tranDetail.get();
		}
		return null;
	}

	@Override
	public TransactionDetail getByPayerUserId(String payerUserId) {

		TransactionDetail detail = transactionDetailRepository
				.findTopByPayerUserIdOrderByTransactionIdDesc(payerUserId);

		if (detail != null && detail.getStatus().equalsIgnoreCase("Pending")) {
			return detail;
		}
		return null;
	}

	@Override
	public TransactionDetail getParkingTransactionDetail(TransactionDetail detail) {

		TransactionDetail transactionDetail = transactionDetailRepository
				.findTopByPayerUserIdAndUserIdOrderByTransactionIdDesc(detail.getPayerUserId(), detail.getUserId());

		if (transactionDetail != null && transactionDetail.getStatus().equalsIgnoreCase("Pending")) {
			transactionDetail.setPayTimestamp(detail.getPayTimestamp());

			transactionDetail.setAmount(
					((int) ((detail.getPayTimestamp() - transactionDetail.getCreateTimestamp()) / 1000)) * 0.0014);
			transactionDetail.setDiscountedAmount(transactionDetail.getAmount());

			return transactionDetailRepository.save(transactionDetail);
		}

		return null;
	}

}
