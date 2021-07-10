package sls.aws.dynamodb.demo.data.repositories;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import sls.aws.dynamodb.demo.data.entity.ProductInfoData;
import sls.aws.dynamodb.demo.data.entity.ProductInfoIDData;

@EnableScan
public interface ProductInfoRepository extends CrudRepository<ProductInfoData, ProductInfoIDData> {
}
