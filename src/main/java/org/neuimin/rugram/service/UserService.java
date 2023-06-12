package org.neuimin.rugram.service;

import org.neuimin.rugram.entity.Subscription;
import org.neuimin.rugram.entity.User;
import org.neuimin.rugram.repository.SubscriptionRepository;
import org.neuimin.rugram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public String createUser(User user) {
        userRepository.save(user);
        return String.format("User with id = %s added", user.getId());
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public String updateUser(User user, Long id) {
        if(!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        userRepository.save(user);
        return String.format("User with id = %s was modified", id);
    }

    public String deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setDeleted(true);
        userRepository.save(user);
        return String.format("User with id = %s was deleted", id);
    }

    public String subscribe(Subscription subscription) {
        if(!subscriptionRepository.existsById(subscription.getSubscriberId()) &&
                subscriptionRepository.existsById(subscription.getSubscriptionId())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        subscriptionRepository.save(subscription);

        return String.format("User with id = %s subscribed on user with id = %s", subscription.getSubscriberId(), subscription.getSubscriptionId());
    }

    public String unsubscribe(Long subscriberId, Long subscriptionId) {
        if(subscriptionRepository.findBySubscriberIdAndSubscriptionId(subscriberId, subscriptionId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        subscriptionRepository.deleteBySubscriberIdAndSubscriptionId(subscriberId, subscriptionId);

        return String.format("User with id = %s unsubscribed of user with id = %s", subscriberId, subscriptionId);
    }
}
