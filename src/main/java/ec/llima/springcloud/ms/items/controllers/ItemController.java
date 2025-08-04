package ec.llima.springcloud.ms.items.controllers;

import org.springframework.web.bind.annotation.RestController;

import ec.llima.springcloud.ms.items.models.Item;
import ec.llima.springcloud.ms.items.models.Product;
import ec.llima.springcloud.ms.items.services.ItemService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
public class ItemController {

    //usara el service por defecto como solo exite uno usa openfeign
    //luego cuando agregados el web cliente 
    private final ItemService service;

    // si no uso @Primary en una de las implementaciones debo usar @Qualifier("itemServiceWebClient") indicndo cual deseo usar
    public ItemController(@Qualifier("itemServiceFeing") ItemService service) {
        this.service = service;
    }

    @GetMapping("/lista")
    public List<Item> getList(@RequestParam(name = "AuthorizationName", required = false) String nombre,
    @RequestHeader(name = "Authorization-request", required = false) String token) {
        //con esto imprimimos los headers que nos llegan
        //estos los aplicamos en el gateway
        //con los filtros que tiene por defecto Spring Cloud Gateway
        //estos filtros se aplicaron solo en el yml
        System.err.println("AuthorizationName: " + nombre);
        System.err.println("Authorization-request: " + token);
        return this.service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetails(@PathVariable Long id) {
        Optional<Item> optionalItem = this.service.findById(id);
        if (optionalItem.isPresent()){
            return ResponseEntity.ok(optionalItem.orElseThrow());
        }
        return ResponseEntity.status(404).body(Collections.singletonMap("message", "No existe el producto!")); 
    }

    //Crud items web client
    /*@PostMapping("/save")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return ResponseEntity.status(201).body(this.service.save(product));
    }*/
    @PostMapping("/save")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return this.service.save(product);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return this.service.update(product, id);  
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.service.deleteById(id); 
    }
    
}
