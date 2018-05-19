package poc.spring.dynamodb.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import poc.spring.dynamodb.domain.ProductService;
import poc.spring.dynamodb.model.Product;

@EnableAutoConfiguration
@Path("/product")
public class ProductResource {

	@Inject
	ProductService productService;

	@GET
	@Produces("application/json")
	@Path("{id}")
	public Product getProducttByID(@PathParam("id") String id) {
		return productService.getProduct(id);

	}

	@POST
	@Produces("application/json")
	public Product postSomething() {
		System.out.println("in the post....");
		Product product = new Product();
		product.setId("1");
		product.setName("a product name");
		productService.setProduct("1", product);
		return product;
	}

}
