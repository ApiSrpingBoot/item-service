package ec.llima.springcloud.ms.items.services;

import java.util.List;
import java.util.Optional;

import ec.llima.springcloud.ms.items.models.Item;

public interface ItemService {

    List<Item> findAll();

    Optional<Item> findById(Long id);

}
