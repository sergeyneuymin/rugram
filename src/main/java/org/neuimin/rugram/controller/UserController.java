package org.neuimin.rugram.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.neuimin.rugram.entity.Subscription;
import org.neuimin.rugram.entity.User;
import org.neuimin.rugram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    // CRUD

    @PostMapping
    @Operation(summary = "Adding new user")
    public String createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Getting user")
    public User getUser(@PathVariable Long id) {
        return service.getUser(id);
    }

    @GetMapping
    @Operation(summary = "Getting list of all users")
    public List<User> getUsers() {
        return service.getUsers();
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Updating user")
    public String updateUser(@RequestBody User user, @PathVariable Long id) {
        return service.updateUser(user, id);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Deleting user")
    public String deleteUser(@PathVariable Long id) {
        return service.deleteUser(id);
    }

    // subscriptions

    @PostMapping(path = "/subscribe")
    @Operation(summary = "Subscribe")
    public String subscribe(@RequestBody Subscription subscription) {
        return service.subscribe(subscription);
    }

    @DeleteMapping(path = "/unsubscribe/{subscriberId}/{subscriptionId}")
    @Operation(summary = "Unsubscribe")
    public String unsubscribe(@PathVariable Long subscriberId, @PathVariable Long subscriptionId) {
        return service.unsubscribe(subscriberId, subscriptionId);
    }
}
