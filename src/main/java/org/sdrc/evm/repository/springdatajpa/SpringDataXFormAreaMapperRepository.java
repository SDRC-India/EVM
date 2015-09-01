package org.sdrc.evm.repository.springdatajpa;

import org.sdrc.evm.repository.XFormAreaMapperRepository;
import org.sdrc.odkaggregate.domain.XFormAreaMapper;
import org.springframework.data.repository.Repository;

public interface SpringDataXFormAreaMapperRepository extends
		XFormAreaMapperRepository, Repository<XFormAreaMapper, String> {

}
