package com.wintersoft.webfluxch2.cart;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CartRepository extends ReactiveMongoRepository<Cart, String> {

}
