package com.wellsfargo.LamaBackend.jpaRepos;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wellsfargo.LamaBackend.entities.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

}
