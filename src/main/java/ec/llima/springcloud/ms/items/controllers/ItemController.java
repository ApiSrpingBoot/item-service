package ec.llima.springcloud.ms.items.controllers;

import org.springframework.web.bind.annotation.RestController;

import ec.llima.springcloud.ms.items.models.Item;
import ec.llima.springcloud.ms.items.services.ItemService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class ItemController {

    //usara el service por defecto como solo exite uno usa openfeign
    //luego cuando agregados el web cliente 
    private final ItemService service;

    // si no uso @Primary en una de las implementaciones debo usar @Qualifier("itemServiceWebClient") indicndo cual deseo usar
    public ItemController(@Qualifier("itemServiceWebClient") ItemService service) {
        this.service = service;
    }

    @GetMapping("/lista")
    public List<Item> getList() {
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
    
}
