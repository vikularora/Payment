package com.paymentApp.payment.service;

import java.util.List;

import com.paymentApp.payment.domain.Product;

public interface ProductService {

	Product addProduct(Product product);

	Product getProductById(long productId);

	List<Product> getProductByMerchantId(String merchantId);

}
