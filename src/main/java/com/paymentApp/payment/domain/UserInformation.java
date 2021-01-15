package com.paymentApp.payment.domain;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

//import org.springframework.cache.annotation.Cacheable;

@Entity
public class UserInformation implements Cloneable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String userId;
	private String countryNetwork;
	private String issuer;
	private String accountNumber;
	private String accountName;
	private String status;
	private String balancePercentage;
	private double lattitude;
	private double longitute;
	private String deviceId;
	private double accountBalance;
	private ArrayList<String> blutoothList = null;
	@Transient
	private double distance;
	private String connectionStatus;
	private String loginStatus;

	@Transient
	private boolean flag;

	@ManyToOne
	private UserRole userRole;
	private String fireBaseToken;
	private double discount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCountryNetwork() {
		return countryNetwork;
	}

	public void setCountryNetwork(String countryNetwork) {
		this.countryNetwork = countryNetwork;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBalancePercentage() {
		return balancePercentage;
	}

	public void setBalancePercentage(String balancePercentage) {
		this.balancePercentage = balancePercentage;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
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

	public ArrayList<String> getBlutoothList() {
		return blutoothList;
	}

	public void setBlutoothList(ArrayList<String> blutoothList) {
		this.blutoothList = blutoothList;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(String connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public String getFireBaseToken() {
		return fireBaseToken;
	}

	public void setFireBaseToken(String fireBaseToken) {
		this.fireBaseToken = fireBaseToken;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	@Override
	public String toString() {
		return "UserInformation [id=" + id + ", userId=" + userId + ", countryNetwork=" + countryNetwork + ", issuer="
				+ issuer + ", accountNumber=" + accountNumber + ", accountName=" + accountName + ", status=" + status
				+ ", balancePercentage=" + balancePercentage + ", lattitude=" + lattitude + ", longitute=" + longitute
				+ ", deviceId=" + deviceId + ", accountBalance=" + accountBalance + ", blutoothList=" + blutoothList
				+ ", distance=" + distance + ", connectionStatus=" + connectionStatus + ", loginStatus=" + loginStatus
				+ ", flag=" + flag + ", userRole=" + userRole + ", fireBaseToken=" + fireBaseToken + ", discount="
				+ discount + "]";
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
//	
//	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "UserInformation" );
//    
//    EntityManager entitymanager = emfactory.createEntityManager( );

}
