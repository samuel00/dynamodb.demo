package sls.aws.dynamodb.demo.data.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShortMessageServiceIDData {

    @DynamoDBHashKey(attributeName = "PK")
    private String cpf;

    @DynamoDBRangeKey(attributeName = "SK")
    private String uuid;
}
