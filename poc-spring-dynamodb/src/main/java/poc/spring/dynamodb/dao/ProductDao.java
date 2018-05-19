package poc.spring.dynamodb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputDescription;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

import poc.spring.dynamodb.model.Product;

@Named
public class ProductDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductDao.class);

	@Inject
	private AmazonDynamoDB ddb;

	public Product getProduct(String id) {
		System.out.println("Retreiving fund from DB, NOT from cache");
		Random rand = new Random();
		int fundNumber = rand.nextInt(100);

		return new Product(id, "Fund - " + fundNumber);
	}

	public void addProduct(String id, Product product) {
		TableDescription tableInfo = ddb.describeTable("productTable").getTable();
		if (tableInfo == null) {
			createTable("productTable");
		} else {
			describeTable(tableInfo);
		}

		Map<String, AttributeValue> itemValues = new HashMap<String, AttributeValue>();
		itemValues.put("ProductName", new AttributeValue("a product name"));
		itemValues.put("ProductId", new AttributeValue("a product id"));
		ddb.putItem("productTable", itemValues);
		
		describeTable(tableInfo);

	}

	private void describeTable(TableDescription table_info) {
		if (table_info != null) {
			System.out.format("Table name  : %s\n", table_info.getTableName());
			System.out.format("Table ARN   : %s\n", table_info.getTableArn());
			System.out.format("Status      : %s\n", table_info.getTableStatus());
			System.out.format("Item count  : %d\n", table_info.getItemCount().longValue());
			System.out.format("Size (bytes): %d\n", table_info.getTableSizeBytes().longValue());

			ProvisionedThroughputDescription throughput_info = table_info.getProvisionedThroughput();
			System.out.println("Throughput");
			System.out.format("  Read Capacity : %d\n", throughput_info.getReadCapacityUnits().longValue());
			System.out.format("  Write Capacity: %d\n", throughput_info.getWriteCapacityUnits().longValue());

			List<AttributeDefinition> attributes = table_info.getAttributeDefinitions();
			System.out.println("Attributes");
			for (AttributeDefinition a : attributes) {
				System.out.format("  %s (%s)\n", a.getAttributeName(), a.getAttributeType());
			}
		}

	}

	private void createTable(String tableName) {
		CreateTableRequest request = new CreateTableRequest()
				.withAttributeDefinitions(new AttributeDefinition("Name", ScalarAttributeType.S))
				.withKeySchema(new KeySchemaElement("Name", KeyType.HASH))
				.withProvisionedThroughput(new ProvisionedThroughput(new Long(10), new Long(10)))
				.withTableName(tableName);

		try {
			CreateTableResult result = ddb.createTable(request);
			System.out.println(result.getTableDescription().getTableName());
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
		}

	}

}
