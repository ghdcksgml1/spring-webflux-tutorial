package com.wintersoft.webfluxch2.item;

import com.wintersoft.webfluxch2.cart.Cart;
import com.wintersoft.webfluxch2.cart.CartRepository;
import com.wintersoft.webfluxch2.cartitem.CartItem;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public ItemService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    public Flux<Item> search(String partialName, String partialDescription, boolean useAnd) {
        Item item = new Item(partialName, partialDescription, 0.0);

        ExampleMatcher matcher = (useAnd
                ? ExampleMatcher.matchingAll()
                : ExampleMatcher.matchingAny())
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                    .withIgnoreCase()
                    .withIgnorePaths("price");

        Example<Item> probe = Example.of(item, matcher);

        return itemRepository.findAll(probe);
    }

    public Mono<Void> delete(String cartId, String itemId) {
        return this.cartRepository.findById(cartId)
                .flatMap(cart -> cart.getCartItems().stream()
                        .filter(cartItem -> (cartItem.getItem().getId().equals(itemId))
                                && (cartItem.getQuantity() > 0))
                        .findAny()
                        .map(cartItem -> {
                            if (cartItem.getQuantity() > 1) {
                                cartItem.decrement();
                            } else {
                                cart.getCartItems().remove(cartItem);
                            }

                            return Mono.just(cart);
                        })
                        .orElseThrow(IllegalArgumentException::new)
                )
                .flatMap(this.cartRepository::save)
                .then();
    }
}
