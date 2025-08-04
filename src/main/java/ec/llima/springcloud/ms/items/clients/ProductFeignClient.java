package ec.llima.springcloud.ms.items.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ec.llima.springcloud.ms.items.models.Product;

//se define el clinete que vamos a usar con feign
//@FeignClient(url = "http://localhost:8010/api", name = "products") //por el balanceo de carga ya no usamos el url
@FeignClient(name = "products")
public interface ProductFeignClient {

    /*
     *este es un cliente usando openfeign 
     */

    @GetMapping("/api/lista") //hacemos que el metodo se comunique con este servicio
    public List<Product> findAll();

    @GetMapping("/api/{id}") //el mapping y los parametros deben ser iguales que en el microservicio
    public Product getDetails(@PathVariable Long id);

    @PostMapping("/api/save")
    public Product save(@RequestBody Product product);

    @PutMapping("/api/update/{id}")
    public Product update(@RequestBody Product product, @PathVariable Long id);

    @DeleteMapping("/api/{id}")
    public void deleteById(@PathVariable Long id);


}
