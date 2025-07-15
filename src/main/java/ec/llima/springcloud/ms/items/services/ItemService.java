package ec.llima.springcloud.ms.items.services;

import java.util.List;
import java.util.Optional;

import ec.llima.springcloud.ms.items.models.Item;

//se definen los metodos que se deben implementar
public interface ItemService {

    List<Item> findAll();

    Optional<Item> findById(Long id);

}
