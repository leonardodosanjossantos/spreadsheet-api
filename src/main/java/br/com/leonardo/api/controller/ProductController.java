package br.com.leonardo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.leonardo.commons.dao.ProductDAO;
import br.com.leonardo.commons.model.Product;

@Controller
@RequestMapping("product")
public class ProductController {

	@Autowired
	private ProductDAO productDAO;

	@GetMapping
	public ResponseEntity<List<Product>> getProducts() {
		List<Product> listProduct = productDAO.getProducts();
		if(!CollectionUtils.isEmpty(listProduct)) {
			return ResponseEntity.ok().body(listProduct);
		}
		return ResponseEntity.noContent().build();
		
	}

	@GetMapping(path = "/{id}")
	@ResponseBody
	public ResponseEntity<Product> getProductById(@PathVariable long id) {
		Product product = productDAO.getProduct(id);
		if (product != null) {
			return ResponseEntity.ok().body(product);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
		Product productUpdated = productDAO.updateProduct(id, product);
		if(productUpdated != null) {
			return ResponseEntity.ok().body(productUpdated);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(value = "/create")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		Product productReturn = productDAO.saveProduct(product);
		if(productReturn != null) {
			return ResponseEntity.ok().body(productReturn);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable long id) {
		if (productDAO.deleteProduct(id)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
