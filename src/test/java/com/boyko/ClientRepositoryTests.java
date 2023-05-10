package com.boyko;

import com.boyko.client.models.Client;
import com.boyko.client.repo.ClientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ClientRepositoryTests {
    @Autowired private ClientRepository repo;

    @Test
    public void testAddNew() {
        Client client = new Client();
        client.setEmail("kychaevalm@gmail.com");
        client.setPassword("darya123");
        client.setFirstName("Darya");
        client.setLastName("Boyko");
        client.setDelivery(true);
        client.setAuto_model("BWM M5 competition");

        Client savedClient = repo.save(client);

        Assertions.assertThat(savedClient).isNotNull();
        Assertions.assertThat(savedClient.getId()).isGreaterThan(0);
    }

    @Test
    public void testListsAll() {
        Iterable<Client> clients = repo.findAll();
        Assertions.assertThat(clients).hasSizeGreaterThan(0);

        for (Client client : clients) {
            System.out.println(client);
        }
    }

    @Test
    public void testUpdate() {
        Integer clientId = 1;
        Optional<Client> optionalClient = repo.findById(clientId);
        Client client = optionalClient.get();
        client.setPassword("123hi321");
        repo.save(client);

        Client updatedClient = repo.findById(clientId).get();
        Assertions.assertThat(updatedClient.getPassword()).isEqualTo("123hi321");
    }

    @Test
    public void testGet() {
        Integer clientId = 2;
        Optional<Client> optionalClient = repo.findById(clientId);
        Assertions.assertThat(optionalClient).isPresent();
        System.out.println(optionalClient.get());
    }

    @Test
    public void testDelete() {
        Integer clientId = 2;
        repo.deleteById(clientId);

        Optional<Client> optionalClient = repo.findById(clientId);
        Assertions.assertThat(optionalClient).isNotPresent();
    }
}
