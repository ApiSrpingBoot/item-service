package ec.llima.springcloud.ms.items.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ec.llima.springcloud.ms.items.clients.ProductFeignClient;
import ec.llima.springcloud.ms.items.models.Item;
import ec.llima.springcloud.ms.items.models.Product;
import feign.FeignException;

@Service
public class ItemServiceFeing implements ItemService {


    private ProductFeignClient client;

    public ItemServiceFeing(ProductFeignClient client) {
        this.client = client;
    }

    @Override
    public List<Item> findAll() {
        return client.findAll()
            .stream()
            .map(product -> new Item(product,new Random().nextInt(10)+1))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findById(Long id) {
        try {
            Product product = client.getDetails(id);
            return Optional.ofNullable(new Item(product, new Random().nextInt(10)+1));
        } catch (FeignException e) {
            return Optional.empty();
        }        
    }

    
}
