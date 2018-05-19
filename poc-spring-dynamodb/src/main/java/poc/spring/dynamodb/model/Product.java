package poc.spring.dynamodb.model;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "productTable")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	private String productID;
	private String productName;

	public Product() {
	}

	public Product(String id, String name) {
		this.productID = id;
		this.productName = name;
	}

	@DynamoDBHashKey(attributeName = "productId")
	@DynamoDBAttribute
	public String getId() {
		return productID;
	}

	public void setId(String id) {
		this.productID = id;
	}

	@DynamoDBRangeKey
	public String getName() {
		return productName;
	}

	public void setName(String name) {
		this.productName = name;
	}

}
