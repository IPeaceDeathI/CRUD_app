package com.boyko.client.controllers;

import com.boyko.client.models.Car;
import com.boyko.client.repo.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/cars")
    public String carsMain(Model model){
        Iterable<Car> cars = carRepository.findAll();
        model.addAttribute("cars", cars);
        return "cars-main";
    }

    @GetMapping("/cars/add")
    public String carsAdd(Model model){
        return "cars-add";
    }

    @PostMapping("/cars/add")
    public String carsPostAdd(@RequestParam String title, @RequestParam String anons,
                              @RequestParam String full_text, Model model){
        Car car = new Car(title, anons, full_text);
        carRepository.save(car);
        return "redirect:/cars";
    }

    @GetMapping("/cars/{id}")
    public String carsDetails(@PathVariable(value = "id") long id, Model model){
        if(!carRepository.existsById(id)){
            return "redirect:/cars";
        }

        Optional<Car> car = carRepository.findById(id);
        ArrayList<Car> res = new ArrayList<>();
        car.ifPresent(res::add);
        model.addAttribute("car", res);
        return "cars-details";
    }

    @GetMapping("/cars/{id}/edit")
    public String carsEdit(@PathVariable(value = "id") long id, Model model){
        if(!carRepository.existsById(id)){
            return "redirect:/cars";
        }

        Optional<Car> car = carRepository.findById(id);
        ArrayList<Car> res = new ArrayList<>();
        car.ifPresent(res::add);
        model.addAttribute("car", res);
        return "cars-edit";
    }

    @PostMapping("/cars/{id}/edit")
    public String carsPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title,
                                 @RequestParam String anons, @RequestParam String full_text, Model model){
        Car car = carRepository.findById(id).orElseThrow();
        car.setTitle(title);
        car.setAnons(anons);
        car.setFull_text(full_text);
        carRepository.save(car);
        return "redirect:/cars";
    }

    @PostMapping("/cars/{id}/remove")
    public String carsPostDelete(@PathVariable(value = "id") long id, Model model){
        Car car = carRepository.findById(id).orElseThrow();
        carRepository.delete(car);
        return "redirect:/cars";
    }
}
