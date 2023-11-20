package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Currency;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.configuration.SpringDataConfiguration;
import com.example.demo.model.Address;
import com.example.demo.model.AuctionType;
import com.example.demo.model.City;
import com.example.demo.model.Item;
import com.example.demo.model.MonetaryAmount;
import com.example.demo.model.User;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.UserRepository;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringDataConfiguration.class})
public class TestingSpringData {

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;
    
    @Test
    void storeLoadEntities() {

    	City city = new City();
        city.setName("Boston");
        city.setZipcode("12345");
        city.setCountry("USA");
        City city2 = new City();
        city2.setName("Bahía Blanca");
        city2.setZipcode("8000");
        city2.setCountry("Argentina");
    	
        User user = new User();
        user.setUsername("username");
        user.setHomeAddress(new Address("Flowers Street", city));
        user.setBillingAddress(new Address("12 de Octubre 265", city2)); //nuestro, testeando otro address
        userRepository.save(user);

        Item item = new Item();
        item.setName("Some Item");
        item.setMetricWeight(2);
        item.setDescription("descriptiondescription"); // se guarda como string pero usamos como clase compleja MonetaryAmount
        item.setBuyNowPrice(new MonetaryAmount(BigDecimal.valueOf(1.1), Currency.getInstance("USD"))); //seteando precio usando converter
        itemRepository.save(item);

        List<User> users = (List<User>) userRepository.findAll();
        List<Item> items = (List<Item>) itemRepository.findByMetricWeight(2.0); //creada por nosotros

        assertAll(
                () -> assertEquals(1, users.size()),
                () -> assertEquals("username", users.get(0).getUsername()),
                () -> assertEquals("Flowers Street", users.get(0).getHomeAddress().getStreet()),
                () -> assertEquals("12345", users.get(0).getHomeAddress().getCity().getZipcode()),
                () -> assertEquals("Boston", users.get(0).getHomeAddress().getCity().getName()),
                () -> assertEquals(1, items.size()),
                () -> assertEquals("AUCTION: Some Item", items.get(0).getName()),
                () -> assertEquals("1.1 USD", items.get(0).getBuyNowPrice().toString()),
                () -> assertEquals("descriptiondescription", items.get(0).getDescription()),
                () -> assertEquals(AuctionType.HIGHEST_BID, items.get(0).getAuctionType()),
                () -> assertEquals("descriptiond...", items.get(0).getShortDescription()),
                () -> assertEquals(2.0, items.get(0).getMetricWeight()),
                () -> assertEquals(LocalDate.now(), items.get(0).getCreatedOn()),
                () -> assertTrue(ChronoUnit.SECONDS.between(LocalDateTime.now(), items.get(0).getLastModified()) < 1),
                () -> assertEquals(new BigDecimal("1.00"), items.get(0).getInitialPrice())
        );

    }
}
