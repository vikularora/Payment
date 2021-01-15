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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymentApp.payment.domain.Product;
import com.paymentApp.payment.domain.TransactionDetail;
import com.paymentApp.payment.service.ProductService;

@RestController
@RequestMapping("/product/")
public class ProductApi {

	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private ProductService service;

	@CrossOrigin
	@GetMapping("{productId}")
	public Object getProduct(@PathVariable(name = "productId", required = true) long productId) throws Exception {

		logger.info("CALLING USER LIST AND USERID IS :: " + productId);
		Product product = service.getProductById(productId);

		logger.info("Product LIST ::" + product);

		if (product != null) {
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		}
		return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("add")
	public Object addProduct(@RequestBody Product product) throws Exception {

		logger.info("CALLING ADD ");
		Product produc = service.addProduct(product);

		logger.info("User RESPONSE ::" + produc);

		return new ResponseEntity<Product>(produc, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("getProductByMerchantId/{merchantId}")
	public Object getProductByMerchantId(@PathVariable(name = "merchantId", required = true) String merchantId)
			throws Exception {

		logger.info("CALLING getProductByMerchantId:: " + merchantId);
		List<Product> productList = service.getProductByMerchantId(merchantId);

		logger.info("User LIST ::" + productList);

		if (productList != null) {
			return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
		}
		return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
	}

}
