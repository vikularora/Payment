package com.paymentApp.payment.respository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.paymentApp.payment.domain.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

	Product findByProductId(String productId);

	List<Product> findByMerchantId(String merchantId);

}
