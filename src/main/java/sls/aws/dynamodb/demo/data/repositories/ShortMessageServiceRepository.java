package sls.aws.dynamodb.demo.data.repositories;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import sls.aws.dynamodb.demo.data.entity.ShortMessageServiceData;
import sls.aws.dynamodb.demo.data.entity.ShortMessageServiceIDData;

import java.util.List;
import java.util.Optional;

@EnableScan
public interface ShortMessageServiceRepository extends CrudRepository<ShortMessageServiceData, ShortMessageServiceIDData> {

    Optional<ShortMessageServiceData> findByUuid(String uuid);
    List<ShortMessageServiceData> findBySigla(String sigla);
}
