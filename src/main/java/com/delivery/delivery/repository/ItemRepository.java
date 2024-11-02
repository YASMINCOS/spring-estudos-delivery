package com.delivery.delivery.repository;

import com.delivery.delivery.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "itens",path = "itens")
public interface ItemRepository extends JpaRepository<Item, Long> {

}
