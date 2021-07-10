package sls.aws.dynamodb.demo.data.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import sls.aws.dynamodb.demo.Application;
import sls.aws.dynamodb.demo.data.entity.ProductInfoData;
import sls.aws.dynamodb.demo.data.entity.ProductInfoIDData;
import sls.aws.dynamodb.demo.data.entity.ShortMessageServiceData;
import sls.aws.dynamodb.demo.data.entity.ShortMessageServiceIDData;
import sls.aws.dynamodb.demo.data.repositories.rule.LocalDbCreationRule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("local")
@TestPropertySource(properties = { "amazon.dynamodb.endpoint=http://localhost:8000/", "amazon.aws.accesskey=test1", "amazon.aws.secretkey=test231" })
public class ShortMessageServiceRepositoryTest {

    @ClassRule
    public static LocalDbCreationRule dynamoDB = new LocalDbCreationRule();

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    ShortMessageServiceRepository repository;

    @Autowired
    ProductInfoRepository productInfoRepository;

    static final String CPF = "93277776204";
    static final String SIGLA = "FZ3";
    static final String STATUS = "EM_PROCESSAMENTO";


    @Before
    public void setup() throws Exception {

        try {
            dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

            CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(ShortMessageServiceData.class);

            tableRequest.setProvisionedThroughput(new ProvisionedThroughput(5L, 10L));
            tableRequest.getGlobalSecondaryIndexes().forEach(v -> v.setProvisionedThroughput(new ProvisionedThroughput(5L, 10L)));


            amazonDynamoDB.createTable(tableRequest);
        } catch (ResourceInUseException e) {
            // Do nothing, table already created
        }

        // TODO How to handle different environments. i.e. AVOID deleting all entries in ProductInfoData on table
        dynamoDBMapper.batchDelete(repository.findAll());
    }

    @Test
    public void givenItemWithMessage_whenRunFindAll_thenItemIsFound() {

        ShortMessageServiceData data = new ShortMessageServiceData();
        data.setCpf(CPF);
        data.setSigla(SIGLA);
        data.setUuid(UUID.randomUUID().toString());
        data.setDataInsercao(LocalDateTime.now().toString());
        repository.save(data);

        List<ShortMessageServiceData> result = (List<ShortMessageServiceData>) repository.findAll();
        assertThat(result.size(), is(greaterThan(0)));
        assertThat(result.get(0).getSigla(), is(equalTo(SIGLA)));
    }

    @Test
    public void givenItemWithMessage_whenRunFindById_thenItemIsFound() {

        ShortMessageServiceData data = new ShortMessageServiceData();
        data.setCpf(CPF);
        data.setSigla(SIGLA);
        data.setUuid(UUID.randomUUID().toString());
        data.setDataInsercao(LocalDateTime.now().toString());
        repository.save(data);

        Optional<ShortMessageServiceData> result =  repository.findById(ShortMessageServiceIDData.builder().cpf(CPF).uuid(data.getUuid()).build());
        assertThat(result.get().getSigla(), is(equalTo(SIGLA)));
    }

    @Test
    public void givenItemWithMessage_whenRunFindByUuid_thenItemIsFound() {

        ShortMessageServiceData data = new ShortMessageServiceData();
        data.setCpf(CPF);
        data.setSigla(SIGLA);
        data.setUuid(UUID.randomUUID().toString());
        data.setDataInsercao(LocalDateTime.now().toString());
        repository.save(data);

        Optional<ShortMessageServiceData> result =  repository.findByUuid(data.getUuid());
        assertThat(result.get().getSigla(), is(equalTo(SIGLA)));
    }

    @Test
    public void givenItemWithMessage_whenRunFindBySigla_thenItemIsFound() {

        ShortMessageServiceData data = new ShortMessageServiceData();
        data.setCpf(CPF);
        data.setSigla(SIGLA);
        data.setUuid(UUID.randomUUID().toString());
        data.setDataInsercao(LocalDateTime.now().toString());
        repository.save(data);


        ProductInfoData productInfoData = new ProductInfoData();
        productInfoData.setUuid(data.getUuid());
        productInfoData.setStatus(STATUS);
        productInfoData.setDataInsercao(LocalDateTime.now().toString());
        productInfoRepository.save(productInfoData);

        List<ShortMessageServiceData> result =  repository.findBySigla(data.getSigla());
        Optional<ProductInfoData> resulProduct =  productInfoRepository.findById(ProductInfoIDData.builder().uuid(data.getUuid()).status(STATUS).build());
        assertThat(result.get(0).getSigla(), is(equalTo(SIGLA)));
        assertThat(result.get(0).getCpf(), is(equalTo(CPF)));
    }

}