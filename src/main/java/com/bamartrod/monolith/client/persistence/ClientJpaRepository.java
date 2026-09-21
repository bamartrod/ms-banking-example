package com.bamartrod.monolith.client.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Spring Data JPA repository for ClientJpaRepository — persistence port for write model.
 *
 * @author Brandon Martinez
 */

@Repository

public interface ClientJpaRepository extends JpaRepository<ClientEntity, String> {
}
