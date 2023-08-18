package com.wintersoft.webfluxch2.cart;

import com.wintersoft.webfluxch2.cartitem.CartItem;
import com.wintersoft.webfluxch2.item.ItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CartService {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public CartService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    // cartId로 카트 찾고, cartItems에 itemId로 찾은 아이템 추가
    public Mono<Cart> addToCart(String cartId, String itemId) {

        return this.cartRepository.findById(cartId)
                .switchIfEmpty(this.cartRepository.save(new Cart(cartId)))
                .flatMap(cart ->
                    cart.getCartItems().stream()
                            .filter(cartItem -> cartItem.getItem().getId().equals(itemId))
                            .findAny()
                            .map(cartItem -> {
                                cartItem.increment();

                                return Mono.just(cart);
                            })
                            .orElseGet(() ->
                                this.itemRepository.findById(itemId)
                                        .map(CartItem::new)
                                        .map(cartItem -> {
                                            cart.getCartItems().add(cartItem);

                                            return cart;
                                        })
                            )
                )
                .flatMap(this.cartRepository::save);
    }
}
