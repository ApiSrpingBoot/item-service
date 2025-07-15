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

    //declaramos el cliente con openfeign 
    private ProductFeignClient client;

    //se puede iyectar con constructor o autowired
    public ItemServiceFeing(ProductFeignClient client) {
        this.client = client;
    }

    @Override
    public List<Item> findAll() {
        //nos debuelve lista de productos y lo cambiamos a lista de items, la cantidad nos devuelve un random entre 1 y 10
        return client.findAll()
            .stream()
            .map(product -> new Item(product,new Random().nextInt(10)+1))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findById(Long id) {
        //nos debuelve un producto y lo cambiamos a item, la cantidad nos devuelve un random entre 1 y 10
        try {
            Product product = client.getDetails(id);
            return Optional.ofNullable(new Item(product, new Random().nextInt(10)+1));
        } catch (FeignException e) {
            return Optional.empty();
        }        
    }

    
}
