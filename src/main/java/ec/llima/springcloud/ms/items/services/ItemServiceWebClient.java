package ec.llima.springcloud.ms.items.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import ec.llima.springcloud.ms.items.models.Item;
import ec.llima.springcloud.ms.items.models.Product;
import feign.FeignException;
//se puede poner @ Prymary para definir que este es el que se va a usar ya que hay 2 implementaciones de ItemService
// @Primary
@Service
public class ItemServiceWebClient implements ItemService{

    //para este caso usamos web client
    private final WebClient.Builder client;
    
    //se puede iyectar con autowired o con el constructor
    public ItemServiceWebClient(Builder client) {
        this.client = client;
    }

    @Override
    public List<Item> findAll() {
        //para trabajar con servlet con una app no reactiva
        return this.client.build()
        .get()
        //.uri("http://products/api/lista").accept(MediaType.APPLICATION_JSON) //se usa produc porque con la configuracion del .properties va a tomar cualquiera de los dos para hacer el balnaceo de carga
        .uri("/api/lista").accept(MediaType.APPLICATION_JSON) //la base url ya esta definifa en WebClient
        .retrieve()
        .bodyToFlux(Product.class)
        .map(product -> new Item(product,new Random().nextInt(10)+1))
        .collectList()
        .block();
    }

    @Override
    public Optional<Item> findById(Long id) {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
         try {
        return Optional.ofNullable(this.client.build()
        .get()
        //.uri("http://products/api/{id}", params).accept(MediaType.APPLICATION_JSON)
        .uri("/api/{id}", params).accept(MediaType.APPLICATION_JSON) //la base url ya esta definifa en WebClient
        .retrieve()
        .bodyToMono(Product.class)
        .map(product -> new Item(product,new Random().nextInt(10)+1))
        .block()) ;
         } catch (WebClientResponseException e) {
            return Optional.empty();
        }
        
    }

}
