package com.wintersoft.webfluxch2;

import com.wintersoft.webfluxch2.cart.Cart;
import com.wintersoft.webfluxch2.cart.CartRepository;
import com.wintersoft.webfluxch2.cart.CartService;
import com.wintersoft.webfluxch2.cartitem.CartItem;
import com.wintersoft.webfluxch2.item.Item;
import com.wintersoft.webfluxch2.item.ItemRepository;
import com.wintersoft.webfluxch2.item.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ItemServiceUnitTest {
    CartService cartService;

    @MockBean private ItemRepository itemRepository;
    @MockBean private CartRepository cartRepository;

    @BeforeEach
    void setUp() {
        itemRepository = mock(ItemRepository.class);
        cartRepository = mock(CartRepository.class);

        Item sampleItem = new Item("Alf TV tray", "TV Tray", 19.99);
        sampleItem.setId("item1");
        CartItem sampleCartItem = new CartItem(sampleItem);
        Cart sampleCart = new Cart("My Cart", Collections.singletonList(sampleCartItem));

        when(cartRepository.findById(anyString())).thenReturn(Mono.empty());
        when(itemRepository.findById(anyString())).thenReturn(Mono.just(sampleItem));
        when(cartRepository.save(any(Cart.class))).thenReturn(Mono.just(sampleCart));

        cartService = new CartService(itemRepository, cartRepository);
    }

    @Test
    void addItemToEmptyCartShouldProduceOneCartItem() {

        cartService.addToCart("My Cart", "item1")
                .as(StepVerifier::create)
                .expectNextMatches(cart -> {
                    assertThat(cart.getCartItems()).extracting(CartItem::getQuantity)
                            .containsExactlyInAnyOrder(2);

                    assertThat(cart.getCartItems().get(0).getItem())
                            .extracting("id", "name", "description", "price")
                            .contains("item1", "Alf TV tray", "TV Tray", 19.99);

                    return true;
                })
                .verifyComplete();
    }
}
