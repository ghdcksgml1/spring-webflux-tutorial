package com.wintersoft.webfluxch2.item;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
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
}
