package com.boyko;

import com.boyko.client.models.Car;
import com.boyko.client.models.Client;
import com.boyko.client.repo.ClientRepository;
import com.boyko.client.repo.CarRepository;
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
    @Autowired private CarRepository carRepository;

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
        Integer clientId = 20;
        Optional<Client> optionalClient = repo.findById(clientId);
        Client client = optionalClient.get();
        client.setFirstName("Denis");
        repo.save(client);

        Client updatedClient = repo.findById(clientId).get();
        Assertions.assertThat(updatedClient.getFirstName()).isEqualTo("Denis");
    }

    @Test
    public void testGet() {
        Integer clientId = 17;
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

    @Test
    public void testAddNews() {
        Car car = new Car();
        car.setTitle("TestTitle");
        car.setAnons("TestAnons");
        car.setFull_text("TestFullText");

        Car savedCar = carRepository.save(car);

        Assertions.assertThat(savedCar).isNotNull();
        Assertions.assertThat(savedCar.getId()).isGreaterThan(0);
    }

    @Test
    public void testDeleteNews() {
        int newsId = 502;
        carRepository.deleteById((long) newsId);

        Optional<Car> optionalCar = carRepository.findById((long) newsId);
        Assertions.assertThat(optionalCar).isNotPresent();
    }

    @Test
    public void testNewsUpdate() {
        int newsId = 502;
        Optional<Car> optionalCar = carRepository.findById((long) newsId);
        Car car = optionalCar.get();
        car.setTitle("Changed Title");
        carRepository.save(car);

        Car updatedCar = carRepository.findById((long) newsId).get();
        Assertions.assertThat(updatedCar.getTitle()).isEqualTo("Changed Title");
    }
}
