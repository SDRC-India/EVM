package org.sdrc.evm.repository.springdatajpa;

import org.sdrc.evm.repository.EvmRequirementRepository;
import org.sdrc.odkaggregate.domain.EvmRequirement;
import org.springframework.data.repository.Repository;

public interface SpringDataEvmRequirementRepository extends
		EvmRequirementRepository, Repository<EvmRequirement,Integer> {

}
