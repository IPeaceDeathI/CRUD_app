package com.boyko.client.controllers;

import com.boyko.client.ClientNotFoundException;
import com.boyko.client.services.ClientService;
import com.boyko.client.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ClientController {
    @Autowired private ClientService service;

    @GetMapping("/clients")
    public String showClientList(Model model) {
        List<Client> listClients = service.listAll();
        model.addAttribute("listClients", listClients);

        return "clients";
    }

    @GetMapping("/clients/new")
    public String showNewForm(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("pageTitle", "Новый заказ");
        return "client_form";
    }

    @PostMapping("/clients/save")
    public String saveClient(Client client, RedirectAttributes ra) {
        service.save(client);
        ra.addFlashAttribute("message", "Заказ был сохранен успешно.");
        return "redirect:/clients";
    }

    @GetMapping("/clients/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Client client = service.get(id);
            model.addAttribute("client", client);
            model.addAttribute("pageTitle", "Изменить заказ (ID: " + id + ")");

            return "client_form";
        } catch (ClientNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/clients";
        }
    }

    @GetMapping("/clients/delete/{id}")
    public String deleteClient(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "Заказ номер " + id + " был успешно удален.");
        } catch (ClientNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/clients";
    }
}
