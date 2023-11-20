package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {

	Iterable<Item> findByMetricWeight(double weight);
}
