package org.olmedo.apiservlet.webapp.session.models;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import org.olmedo.apiservlet.webapp.session.configs.CarroCompra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@CarroCompra
public class Carro  implements Serializable { // siempre tiene que tener implementado el Serializable en las inyecciones
    private List<ItemCarro> items;
    @Inject
    private transient Logger log; // el logger no es Serializable por lo tanto no es compatible por eso se le agreaa el modficador transient: de esta forma el log no farma parte de la session del carro

    @PostConstruct // esto es como un constructor
    public void inicializar(){
        this.items = new ArrayList<>();
        log.info("Inicializando el carro de compras!");
    }
    @PreDestroy
    public void destruir(){
        log.info("destruyendo el carro de compras!");
    }
    public void addItemCarro(ItemCarro itemCarro) {
        if(items.contains(itemCarro)) {
            Optional<ItemCarro> optionalItemCarro = items.stream()
                    .filter(i -> i.equals(itemCarro))
                    .findAny();
            if (optionalItemCarro.isPresent()) {
                ItemCarro i = optionalItemCarro.get();
                i.setCantidad(i.getCantidad() + 1);
            }
        } else {
            this.items.add(itemCarro);
        }
    }

    public List<ItemCarro> getItems(){
        return items;
    }

    public int getTotal(){
        return items.stream().mapToInt(ItemCarro::getImporte).sum();
    }

    public void removeProductos(List<String> productosIds) {
        if(productosIds != null) {
            productosIds.forEach(this::removeProductos);
        }
    }

    public void removeProductos(String productoId) {
        Optional<ItemCarro> producto = findProducto(productoId);
        producto.ifPresent(itemCarro -> items.remove(itemCarro));
    }

    public void updateCantidad(String productoId, int cantidad) {
        Optional<ItemCarro> producto = findProducto(productoId);
        producto.ifPresent(itemCarro -> itemCarro.setCantidad(cantidad));
    }

    private Optional<ItemCarro> findProducto(String productoId) {
        return items.stream()
                .filter(itemCarro -> productoId.equals(Long.toString(itemCarro.getProducto().getId())))
                .findAny();
    }
}
