package sls.aws.dynamodb.demo.data.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "Messageria")
@Builder
public class ShortMessageServiceData {

    @Id
    @DynamoDBIgnore
    private ShortMessageServiceIDData shortMessageServiceIDData;

    @DynamoDBAttribute
    @Setter
    @Getter
    private String dataInsercao;

    private String sigla;

    @DynamoDBHashKey(attributeName = "PK")
    public String getCpf(){
        return shortMessageServiceIDData != null ? shortMessageServiceIDData.getCpf() : null;
    }

    public void setCpf(String cfp) {
        if (shortMessageServiceIDData == null) {
            shortMessageServiceIDData = ShortMessageServiceIDData.builder().build();
        }
        shortMessageServiceIDData.setCpf(cfp);
    }

    @DynamoDBRangeKey(attributeName = "SK")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "MensageriaIndex")
    public String getUuid() {
        return shortMessageServiceIDData != null ? shortMessageServiceIDData.getUuid() : null;
    }

    public void setUuid(String uuid) {
        if (shortMessageServiceIDData == null) {
            shortMessageServiceIDData = ShortMessageServiceIDData.builder().build();
        }
        shortMessageServiceIDData.setUuid(uuid);
    }


    @DynamoDBAttribute(attributeName = "sigla")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "MensageriaIndex")
    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}
