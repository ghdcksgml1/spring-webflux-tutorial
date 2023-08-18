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

        return this.cartRepository.findById(cartId).log("foundCart")
                .switchIfEmpty(this.cartRepository.save(new Cart(cartId))).log("emptyCart")
                .flatMap(cart ->
                    cart.getCartItems().stream()
                            .filter(cartItem -> cartItem.getItem().getId().equals(itemId))
                            .findAny()
                            .map(cartItem -> {
                                cartItem.increment();

                                return Mono.just(cart);
                            })
                            .orElseGet(() ->
                                this.itemRepository.findById(itemId).log("fetchedItem")
                                        .map(CartItem::new).log("cartItem")
                                        .map(cartItem -> {
                                            cart.getCartItems().add(cartItem);

                                            return cart;
                                        }).log("addedCartItem")
                            )).log("cartWithAnotherItem")
                .flatMap(this.cartRepository::save).log("savedCart");
    }
}
