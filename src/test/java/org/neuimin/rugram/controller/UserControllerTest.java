package org.neuimin.rugram.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.neuimin.rugram.entity.City;
import org.neuimin.rugram.entity.Subscription;
import org.neuimin.rugram.entity.User;
import org.neuimin.rugram.repository.SubscriptionRepository;
import org.neuimin.rugram.repository.UserRepository;
import org.neuimin.rugram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

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

    @BeforeAll
    private void init() {
        userRepository = Mockito.mock(UserRepository.class);
        subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
        userService = new UserService(userRepository, subscriptionRepository);
        user = new User("test","Ivanov",null,new Date(),"M",new City("Moscow"),null,
                null,"Ivan",null,"ivanov@gmail.com","+79000000000",false);

        savedUser = new User(26L, "test","Ivanov",null,new Date(),"M",new City("Moscow"),null,
                null,"Ivan",null,"ivanov@gmail.com","+79000000000",false);

        updatedUser = new User(26L, "Petr","Ivanov",null,new Date(),"M",new City("Moscow"),null,
                null,"Ivan",null,"ivanov@gmail.com","+79000000000",false);

        deletedUser = new User(26L, "Petr","Ivanov",null,new Date(),"M",new City("Moscow"),null,
                null,"Ivan",null,"ivanov@gmail.com","+79000000000",true);

        subscriberUser = new User(2L, "Ivan","Ivanov",null,new Date(),"M",new City("Moscow"),null,
                null,"Ivan",null,"ivanov@gmail.com","+79000000000",false);

        subscription = new Subscription(2L,1L);

        savedSubscription = new Subscription(1L, 2L,1L);

    }

    @BeforeEach
    private void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void createUser() throws Exception {
        String request = "{\n" +
                "    \"name\": \"Ivan\",\n" +
                "    \"surname\": \"Ivanov\",\n" +
                "    \"middleName\": \"Ivanovich\",\n" +
                "    \"birthday\": \"2000-01-01\",\n" +
                "    \"gender\": \"M\",\n" +
                "    \"city\": {\n" +
                "        \"city\": \"Moscow\"\n" +
                "    },\n" +
                "    \"imageUrl\": \"test_url\",\n" +
                "    \"description\": \"description\",\n" +
                "    \"nickname\": \"Ivan\",\n" +
                "    \"hardskills\": \"Responsibility\",\n" +
                "    \"email\": \"ivan@gmail.com\",\n" +
                "    \"phone\": \"+79000000000\",\n" +
                "    \"is_deleted\": \"false\"\n" +
                "}";

         ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                 .post("/users")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(request));

         resultActions.andExpect(status().isOk())
                 .andExpect(jsonPath("$", Matchers.containsString("added")));
    }

    @Test
    void updateUser() throws Exception {
        String request = "{\n" +
                "    \"name\": \"Ivan\",\n" +
                "    \"surname\": \"Ivanov\",\n" +
                "    \"middleName\": \"Ivanovich\",\n" +
                "    \"birthday\": \"2000-01-01\",\n" +
                "    \"gender\": \"M\",\n" +
                "    \"city\": {\n" +
                "        \"city\": \"Moscow\"\n" +
                "    },\n" +
                "    \"imageUrl\": \"test_url\",\n" +
                "    \"description\": \"description\",\n" +
                "    \"nickname\": \"Ivan\",\n" +
                "    \"hardskills\": \"Responsibility\",\n" +
                "    \"email\": \"ivan@gmail.com\",\n" +
                "    \"phone\": \"+79000000000\",\n" +
                "    \"is_deleted\": \"false\"\n" +
                "}";

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .put("/users/26")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.containsString("modified")));
    }

    @Test
    void deleteUser() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .delete("/users/{id}", savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.containsString("deleted")));
    }

    @Test
    public void getUsers() throws Exception {
        List<User> users = List.of(savedUser,subscriberUser);
        Mockito.when(userRepository.findAll()).thenReturn(users);

        ResultActions response = mockMvc.perform(get("/users"));
        response.andExpect(status().isOk());
    }

    @Test
    public void getUserById() throws Exception {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(savedUser));
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/users/{id}", savedUser.getId()));

        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(savedUser.getName())));

    }
}