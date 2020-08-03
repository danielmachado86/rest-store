package io.dmcapps.dshopping.store;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;


import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

@ApplicationScoped
@Transactional(REQUIRED)
public class StoreService {


    @Transactional(SUPPORTS)
    public List<Store> findNearbyStores(double lon, double lat, int range) {

        return Store.find(String.format(
            "{'address.location': {" +
                "'$near': {" +
                    "'$geometry': {" +
                        "'type': 'Point'," +
                        "'coordinates': [%f, %f]}," +
                        "'$maxDistance': %d" +
                   "}" +
                "}" +
            "}",
            lon, lat, range))
            .list();
    }

    @Transactional(SUPPORTS)
    public List<Store> findAllStores() {
        return Store.listAll();
    }

    @Transactional(SUPPORTS)
    public Store findStoreById(Long id) {
        return Store.findById(id);
    }

    public Store persistStore(@Valid Store store) {
        Store.persist(store);
        return store;
    }

    public Store updateStore(@Valid Store store) {
        Store entity = Store.findById(store.id);
        entity.name = store.name;
        entity.email = store.email;
        entity.mobile = store.mobile;
        entity.address = store.address;
        return entity;
    }

    public void deleteStore(Long id) {
        Store store = Store.findById(id);
        store.delete();
    }
}