package poc.spring.dynamodb;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@ApplicationPath("dynamodb-poc")
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		packages("poc.spring.dynamodb.resource ");
	}
}