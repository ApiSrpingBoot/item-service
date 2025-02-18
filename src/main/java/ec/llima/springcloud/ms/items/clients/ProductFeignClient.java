package ec.llima.springcloud.ms.items.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ec.llima.springcloud.ms.items.models.Product;

@FeignClient(url = "http://localhost:8010/api", name = "products")
public interface ProductFeignClient {

    @GetMapping("/lista")
    public List<Product> findAll();

    @GetMapping("/{id}")
    public Product getDetails(@PathVariable Long id);

}
