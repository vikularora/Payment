package com.paymentApp.payment.domain;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

@Entity
public class TransactionDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;
	@Column(precision = 5, scale = 4)
	private double amount;
	private long transactionDateTime;
	private String business_name;
	private String address;
	private String status;
	private String reasonName;
	private ArrayList<String> productList;
	@Transient
	private boolean flag;

	private String payerUserId;
	private String userId;
	private String deliveryUserId;
	private String appOwnerUserId;
	private String merchantUserId;

	private double receivedAmount;
	private double deveileryUserAmount;
	private double merchantAmount;
	private double appOwnerAmount;

	@ManyToOne
	private ServiceType serviceType;
	@ManyToOne
	private TransactionType transactionType;
	private double lattitude;
	private double longitute;
	private long createTimestamp;
	private long payTimestamp;
	@Column(scale = 2)
	private double discountedAmount;
	@Column(precision = 4, scale = 2)
	private double discount;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPayerUserId() {
		return payerUserId;
	}

	public void setPayerUserId(String payerUserId) {
		this.payerUserId = payerUserId;
	}

	public long getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(long transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public String getBusiness_name() {
		return business_name;
	}

	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ArrayList<String> getProductList() {
		return productList;
	}

	public void setProductList(ArrayList<String> productList) {
		this.productList = productList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getReasonName() {
		return reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	public String getDeliveryUserId() {
		return deliveryUserId;
	}

	public void setDeliveryUserId(String deliveryUserId) {
		this.deliveryUserId = deliveryUserId;
	}

	public String getAppOwnerUserId() {
		return appOwnerUserId;
	}

	public void setAppOwnerUserId(String appOwnerUserId) {
		this.appOwnerUserId = appOwnerUserId;
	}

	public String getMerchantUserId() {
		return merchantUserId;
	}

	public void setMerchantUserId(String merchantUserId) {
		this.merchantUserId = merchantUserId;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public double getLattitude() {
		return lattitude;
	}

	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}

	public double getLongitute() {
		return longitute;
	}

	public void setLongitute(double longitute) {
		this.longitute = longitute;
	}

	public long getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(long createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public long getPayTimestamp() {
		return payTimestamp;
	}

	public void setPayTimestamp(long payTimestamp) {
		this.payTimestamp = payTimestamp;
	}

	public double getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(double receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public double getDeveileryUserAmount() {
		return deveileryUserAmount;
	}

	public void setDeveileryUserAmount(double deveileryUserAmount) {
		this.deveileryUserAmount = deveileryUserAmount;
	}

	public double getMerchantAmount() {
		return merchantAmount;
	}

	public void setMerchantAmount(double merchantAmount) {
		this.merchantAmount = merchantAmount;
	}

	public double getAppOwnerAmount() {
		return appOwnerAmount;
	}

	public void setAppOwnerAmount(double appOwnerAmount) {
		this.appOwnerAmount = appOwnerAmount;
	}

	@Override
	public String toString() {
		return "TransactionDetail [transactionId=" + transactionId + ", amount=" + amount + ", transactionDateTime="
				+ transactionDateTime + ", business_name=" + business_name + ", address=" + address + ", status="
				+ status + ", reasonName=" + reasonName + ", productList=" + productList + ", flag=" + flag
				+ ", payerUserId=" + payerUserId + ", userId=" + userId + ", deliveryUserId=" + deliveryUserId
				+ ", appOwnerUserId=" + appOwnerUserId + ", merchantUserId=" + merchantUserId + ", receivedAmount="
				+ receivedAmount + ", deveileryUserAmount=" + deveileryUserAmount + ", merchantAmount=" + merchantAmount
				+ ", appOwnerAmount=" + appOwnerAmount + ", serviceType=" + serviceType + ", transactionType="
				+ transactionType + ", lattitude=" + lattitude + ", longitute=" + longitute + ", createTimestamp="
				+ createTimestamp + ", payTimestamp=" + payTimestamp + ", discountedAmount=" + discountedAmount
				+ ", discount=" + discount + "]";
	}

}
