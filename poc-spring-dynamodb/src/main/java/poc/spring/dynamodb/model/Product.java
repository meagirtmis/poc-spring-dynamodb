package poc.spring.dynamodb.model;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "productTable")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;

	public Product() {
	}

	public Product(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@DynamoDBHashKey(attributeName = "productId")
	@DynamoDBAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@DynamoDBRangeKey
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
