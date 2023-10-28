package cloud.dev.dev_log_resource.config;

import com.amazonaws.auth.*;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {
    @Value("${spring.amazon.aws.endpoint}")
    private String dynamoDbEndpoint;

    @Value("${spring.amazon.aws.accesskey}")
    private String awsAccessKey;

    @Value("${spring.amazon.aws.secretkey}")
    private String awsSecretKey;

    @Value("${spring.amazon.aws.region}")
    private String awsRegion;

    @Value("${spring.amazon.aws.sessiontoken}")
    private String awsSessionToken;

   @Bean
    public DynamoDBMapper dynamoDBMapper() {
       return new DynamoDBMapper(amazonDynamoDb());
   }

    private AmazonDynamoDB amazonDynamoDb() {
       return AmazonDynamoDBClientBuilder.standard()
               .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dynamoDbEndpoint, awsRegion))
               .withCredentials(awsCredentials()).build();
    }

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(awsCredentials())
                .withRegion(awsRegion)
                .build();
    }

    private AWSCredentialsProvider awsCredentials() {
       return new AWSStaticCredentialsProvider(new BasicSessionCredentials(awsAccessKey, awsSecretKey,awsSessionToken));
    }
}

