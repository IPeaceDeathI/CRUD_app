package com.boyko.client.repo;

import com.boyko.client.models.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
    public Long countById(Integer id);
}
