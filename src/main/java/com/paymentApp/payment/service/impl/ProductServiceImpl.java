package com.paymentApp.payment.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymentApp.payment.domain.Product;
import com.paymentApp.payment.respository.ProductRepository;
import com.paymentApp.payment.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository repo;

	@Override
	public Product addProduct(Product product) {

		return repo.save(product);
	}

	@Override
	public Product getProductById(long productId) {

		Optional<Product> product = repo.findById(productId);

		if (product.isPresent()) {
			return product.get();
		}
		return null;
	}

	@Override
	public List<Product> getProductByMerchantId(String merchantId) {
		
		List<Product> product = repo.findByMerchantId(merchantId);		
		
		return product;
	}

}
