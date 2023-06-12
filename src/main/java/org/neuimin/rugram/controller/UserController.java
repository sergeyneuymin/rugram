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

    @Autowired
    private UserService service;

    // CRUD

    @PostMapping
    String createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping(path = "/{id}")
    User getUser(@PathVariable Long id) {
        return service.getUser(id);
    }

    @GetMapping
    List<User> getUsers() {
        return service.getUsers();
    }

    @PutMapping(path = "/{id}")
    String updateUser(@RequestBody User user, @PathVariable Long id) {
        return service.updateUser(user, id);
    }

    @DeleteMapping(path = "/{id}")
    String deleteUser(@PathVariable Long id) {
        return service.deleteUser(id);
    }

    // subscriptions

    @PostMapping(path = "/subscribe")
    String subscribe(@RequestBody Subscription subscription) {
        return service.subscribe(subscription);
    }

    @DeleteMapping(path = "/unsubscribe/{subscriberId}/{subscriptionId}")
    String unsubscribe(@PathVariable Long subscriberId, @PathVariable Long subscriptionId) {
        return service.unsubscribe(subscriberId, subscriptionId);
    }
}
