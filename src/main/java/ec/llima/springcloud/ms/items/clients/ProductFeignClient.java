package ec.llima.springcloud.ms.items.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ec.llima.springcloud.ms.items.models.Product;

//@FeignClient(url = "http://localhost:8010/api", name = "products") //por el balanceo de carga ya no usamos el url
@FeignClient(name = "products")
public interface ProductFeignClient {

    /*
     *este es un cliente usando openfeign 
     */

    @GetMapping("/api/lista")
    public List<Product> findAll();

    @GetMapping("/api/{id}")
    public Product getDetails(@PathVariable Long id);

}
