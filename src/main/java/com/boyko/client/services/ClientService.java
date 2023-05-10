package com.boyko.client.services;

import com.boyko.client.ClientNotFoundException;
import com.boyko.client.models.Client;
import com.boyko.client.repo.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired private ClientRepository repo;

    public List<Client> listAll() {
        return (List<Client>) repo.findAll();
    }

    public void save(Client client) {
        repo.save(client);
    }

    public Client get(Integer id) throws ClientNotFoundException {
        Optional<Client> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ClientNotFoundException("Не получается найти заказ с номером  " + id);
    }

    public void delete(Integer id) throws ClientNotFoundException {
        Long count = repo.countById(id);
        if (count == null || count == 0) {
            throw new ClientNotFoundException("Не получается найти заказ с номером " + id);
        }
        repo.deleteById(id);
    }
}
