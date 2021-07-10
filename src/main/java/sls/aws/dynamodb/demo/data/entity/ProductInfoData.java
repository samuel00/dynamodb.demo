package sls.aws.dynamodb.demo.data.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "Messageria")
@Builder
public class ProductInfoData {

    @Id
    @DynamoDBIgnore
    private ProductInfoIDData productInfoIDData;

    private String uuid;

    private String status;

    @DynamoDBAttribute
    @Setter
    @Getter
    private String dataInsercao;

    @DynamoDBHashKey(attributeName = "PK")
    public String getUuid() {
        return this.productInfoIDData != null ? this.productInfoIDData.getUuid() : null;
    }

    public void setUuid(String uuid) {
        if (productInfoIDData == null) {
            productInfoIDData = ProductInfoIDData.builder().build();
        }
        productInfoIDData.setUuid(uuid);
    }



    @DynamoDBRangeKey(attributeName = "SK")
    public String getStatus() {
        return this.productInfoIDData != null ? this.productInfoIDData.getStatus() : null;
    }

    public void setStatus(String status) {
        if (productInfoIDData == null) {
            productInfoIDData = ProductInfoIDData.builder().build();
        }
        productInfoIDData.setStatus(status);
    }


}
