package org.neuimin.rugram.controller;

import org.neuimin.rugram.entity.Subscription;
import org.neuimin.rugram.entity.User;
import org.neuimin.rugram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    // CRUD

    @PostMapping
    public String createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping(path = "/{id}")
    public User getUser(@PathVariable Long id) {
        return service.getUser(id);
    }

    @GetMapping
    public List<User> getUsers() {
        return service.getUsers();
    }

    @PutMapping(path = "/{id}")
    public String updateUser(@RequestBody User user, @PathVariable Long id) {
        return service.updateUser(user, id);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteUser(@PathVariable Long id) {
        return service.deleteUser(id);
    }

    // subscriptions

    @PostMapping(path = "/subscribe")
    public String subscribe(@RequestBody Subscription subscription) {
        return service.subscribe(subscription);
    }

    @DeleteMapping(path = "/unsubscribe/{subscriberId}/{subscriptionId}")
    public String unsubscribe(@PathVariable Long subscriberId, @PathVariable Long subscriptionId) {
        return service.unsubscribe(subscriberId, subscriptionId);
    }
}
