package com.wintersoft.webfluxch2.hello;

import com.wintersoft.webfluxch2.cart.Cart;
import com.wintersoft.webfluxch2.cart.CartRepository;
import com.wintersoft.webfluxch2.cart.CartService;
import com.wintersoft.webfluxch2.cartitem.CartItem;
import com.wintersoft.webfluxch2.item.Item;
import com.wintersoft.webfluxch2.item.ItemRepository;
import com.wintersoft.webfluxch2.item.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
public class HelloController {

    private ItemRepository itemRepository;
    private CartRepository cartRepository;
    private CartService cartService;
    private ItemService itemService;

    public HelloController(ItemRepository itemRepository, CartRepository cartRepository, CartService cartService,
                           ItemService itemService) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.itemService = itemService;
    }

    @GetMapping
    Mono<Rendering> home() {
        return Mono.just(Rendering.view("home.html")
                .modelAttribute("items", this.itemRepository.findAll())
                .modelAttribute("cart", this.cartRepository.findById("My Cart")
                        .switchIfEmpty(this.cartRepository.save(new Cart("My Cart"))))
                .build());
    }

    @PostMapping("/add/{id}")
    Mono<String> addToCart(@PathVariable("id") String id) {

        return this.cartService.addToCart("My Cart", id)
                .thenReturn("redirect:/");
    }

    @GetMapping("/search")
    Mono<Rendering> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam boolean useAnd) {

        return Mono.just(Rendering.view("home.html")
                .modelAttribute("items", itemService.search(name, description, useAnd))
                .modelAttribute("cart", this.cartRepository.findById("My Cart")
                        .switchIfEmpty(this.cartRepository.save(new Cart("My Cart"))))
                .build());
    }
}
