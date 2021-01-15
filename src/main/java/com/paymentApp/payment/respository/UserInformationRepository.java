package com.paymentApp.payment.respository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.paymentApp.payment.domain.UserInformation;

@Repository
public interface UserInformationRepository extends PagingAndSortingRepository<UserInformation, Long> {

	UserInformation findByUserId(String user);
	UserInformation findByAccountName(String user);
	

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update UserInformation u set u.lattitude = :lat , u.longitute = :log where u.userId = :userId")
	void updateByUserId(Double lat, Double log, String userId);
	
	@Query("select u from UserInformation u where u.userId = :userId")
	UserInformation getUserInformationByUserId(String userId);
	
//	@Transactional
//	@Modifying
//	@Query("update UserInformation u set u.bluetoothId = :bluetoothId, u.lattitude = :lat , u.longitute = :log where u.userId = :userId")
//	void updateByUserId(String bluetoothId, Double lat, Double log, String userId, long balance);

	@Transactional
	@Modifying
	@Query("update UserInformation u set u.blutoothList = :bluetooth where u.userId = :userId")	
	void updateBluethoothList(ArrayList<String> bluetooth, String userId);


}
