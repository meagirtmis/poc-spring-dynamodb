package poc.spring.dynamodb.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;

import poc.spring.dynamodb.model.Product;

@Named
public class ProductDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductDao.class);

	@Inject
	private DynamoDBMapper mapper;

	public Product getProduct(String id) {
		System.out.println("Retreiving fund from DB, NOT from cache");
		Random rand = new Random();
		int fundNumber = rand.nextInt(100);

		return new Product(id, "Fund - " + fundNumber);
	}

	public void addProduct(String id, Product product) {

		try {
			DynamoDBSaveExpression expr = new DynamoDBSaveExpression();
			Map expected = new HashMap();
			expected.put("productId", new ExpectedAttributeValue(false));
			expr.setExpected(expected);
			mapper.save(product, expr);
		} catch (ConditionalCheckFailedException e) {
			LOGGER.error("error in insertIntoDynamoDB method(), object already exists in database - " + e);
		}
	}

}
