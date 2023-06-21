package org.neuimin.rugram.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.neuimin.rugram.entity.City;
import org.neuimin.rugram.entity.Subscription;
import org.neuimin.rugram.entity.User;
import org.neuimin.rugram.repository.SubscriptionRepository;
import org.neuimin.rugram.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    private User user;

    private User savedUser;

    private User updatedUser;

    private User deletedUser;

    private User subscriberUser;

    private Subscription subscription;

    private Subscription savedSubscription;

    private UserRepository userRepository;

    private SubscriptionRepository subscriptionRepository;

    private UserService userService;

    @BeforeEach
    void init() {
        userRepository = Mockito.mock(UserRepository.class);
        subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
        userService = new UserService(userRepository, subscriptionRepository);
        user = new User("Ivan","Ivanov",null,new Date(),"M",new City("Moscow"),null,
                null,"Ivan",null,"ivanov@gmail.com","+79000000000",false);

        savedUser = new User(1L, "Ivan","Ivanov",null,new Date(),"M",new City("Moscow"),null,
                null,"Ivan",null,"ivanov@gmail.com","+79000000000",false);

        updatedUser = new User(1L, "Petr","Ivanov",null,new Date(),"M",new City("Moscow"),null,
                null,"Ivan",null,"ivanov@gmail.com","+79000000000",false);

        deletedUser = new User(1L, "Petr","Ivanov",null,new Date(),"M",new City("Moscow"),null,
                null,"Ivan",null,"ivanov@gmail.com","+79000000000",true);

        subscriberUser = new User(2L, "Ivan","Ivanov",null,new Date(),"M",new City("Moscow"),null,
                null,"Ivan",null,"ivanov@gmail.com","+79000000000",false);

        subscription = new Subscription(2L,1L);

        savedSubscription = new Subscription(1L, 2L,1L);
    }

    @Test
    void createUser() {
        Mockito.when(userRepository.save(user)).thenReturn(savedUser);
        String result = userService.createUser(user);
        Assertions.assertEquals("User with id = 1 added", result);
    }

//    @Test
//    void createUserError() {
//        Mockito.when(userRepository.save(user)).thenThrow(PersistenceException.class);
//        Executable executable = () -> userService.createUser(user);
//        Assertions.assertThrows(PersistenceException.class, executable);
//    }

    @Test
    void getUser() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(savedUser));
        User userResult = userService.getUser(1L);
        Assertions.assertEquals(savedUser, userResult);
    }

    @Test
    void getUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(savedUser, subscriberUser));
        List<User> users = userService.getUsers();
        Assertions.assertEquals(2, users.size());
    }

    @Test
    void getUserError() {
        Mockito.when(userRepository.findById(2L)).thenThrow(EntityNotFoundException.class);
        Executable executable = () -> userService.getUser(2L);
        Assertions.assertThrows(EntityNotFoundException.class, executable);
    }

    @Test
    void updateUser() {
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(userRepository.save(savedUser)).thenReturn(updatedUser);
        String result = userService.updateUser(savedUser, 1L);
        Assertions.assertEquals("User with id = 1 was modified", result);
    }

    @Test
    void deleteUser() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(updatedUser));
        Mockito.when(userRepository.save(updatedUser)).thenReturn(deletedUser);
        String result = userService.deleteUser(1L);
        Assertions.assertEquals("User with id = 1 was deleted", result);
    }

    @Test
    void subscribe() {
        Mockito.when(userRepository.existsById(2L)).thenReturn(true);
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(subscriptionRepository.save(subscription)).thenReturn(savedSubscription);
        String result = userService.subscribe(subscription);
        Assertions.assertEquals("User with id = 2 subscribed on user with id = 1", result);
    }

    @Test
    void unsubscribe() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(updatedUser));
        Mockito.when(userRepository.save(updatedUser)).thenReturn(deletedUser);
        String result = userService.deleteUser(1L);
        Assertions.assertEquals("User with id = 1 was deleted", result);
    }
}