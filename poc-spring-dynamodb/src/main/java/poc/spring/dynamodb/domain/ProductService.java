package poc.spring.dynamodb.domain;

import javax.inject.Inject;
import javax.inject.Named;

import poc.spring.dynamodb.dao.ProductDao;
import poc.spring.dynamodb.model.Product;

@Named
public class ProductService {

	@Inject
	ProductDao productDao;

	public Product getProduct(String id) {
		System.out.println("NOT in the cache: Getting the item with :" + id);
		return productDao.getProduct(id);
	}

	public void setProduct(String id, Product product) {
		productDao.addProduct(id, product);
		System.out.println("Setting the item to the cache :" + product);
	}

}
