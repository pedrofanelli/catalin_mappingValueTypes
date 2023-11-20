package com.example.demo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.configuration.SpringDataConfiguration;
import com.example.demo.model.Address;
import com.example.demo.model.AuctionType;
import com.example.demo.model.Item;
import com.example.demo.model.User;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringDataConfiguration.class})
public class TestingSpringData {

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;
    
    @Test
    void storeLoadEntities() {

        User user = new User();
        user.setUsername("username");
        user.setHomeAddress(new Address("Flowers Street", "12345", "Boston"));
        user.setBillingAddress(new Address("12 de Octubre 265", "8000", "Bahía Blanca")); //nuestro, testeando otro address
        userRepository.save(user);

        Item item = new Item();
        item.setName("Some Item");
        item.setMetricWeight(2);
        item.setDescription("description of the item");
        itemRepository.save(item);

        List<User> users = (List<User>) userRepository.findAll();
        List<Item> items = (List<Item>) itemRepository.findByMetricWeight(2.0); //creada por nosotros

        assertAll(
                () -> assertEquals(1, users.size()),
                () -> assertEquals("username", users.get(0).getUsername()),
                () -> assertEquals("Flowers Street", users.get(0).getHomeAddress().getStreet()),
                () -> assertEquals("12345", users.get(0).getHomeAddress().getZipcode()),
                () -> assertEquals("Boston", users.get(0).getHomeAddress().getCity()),
                () -> assertEquals(1, items.size()),
                () -> assertEquals("AUCTION: Some Item", items.get(0).getName()),
                () -> assertEquals("description of the item", items.get(0).getDescription()),
                () -> assertEquals(AuctionType.HIGHEST_BID, items.get(0).getAuctionType()),
                () -> assertEquals("descriptiond...", items.get(0).getShortDescription()),
                () -> assertEquals(2.0, items.get(0).getMetricWeight()),
                () -> assertEquals(LocalDate.now(), items.get(0).getCreatedOn()),
                () -> assertTrue(ChronoUnit.SECONDS.between(LocalDateTime.now(), items.get(0).getLastModified()) < 1),
                () -> assertEquals(new BigDecimal("1.00"), items.get(0).getInitialPrice())
        );

    }
}
