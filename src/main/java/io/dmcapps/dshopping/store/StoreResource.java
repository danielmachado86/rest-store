package io.dmcapps.dshopping.store;

import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/stores")
@Produces(APPLICATION_JSON)
public class StoreResource {

    private static final Logger LOGGER = Logger.getLogger(StoreResource.class);

    @Inject
    StoreService service;

    @GET
    public Response getAllStores() {
        List<Store> stores = service.findAllStores();
        LOGGER.debug("Total number of stores " + stores);
        return Response.ok(stores).build();
    }

    @GET
    @Path("/{id}")
    public Response getStore(
        @PathParam("id") Long id) {
        Store store = service.findStoreById(id);
        if (store != null) {
            LOGGER.debug("Found store " + store);
            return Response.ok(store).build();
        } else {
            LOGGER.debug("No store found with id " + id);
            return Response.noContent().build();
        }
    }

    @POST
    public Response createStore(
        @Valid Store store, @Context UriInfo uriInfo) {
        store = service.persistStore(store);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(store.id.toString());
        LOGGER.debug("New store created with URI " + builder.build().toString());
        return Response.created(builder.build()).build();
    }

    @PUT
    public Response updateStore(
        @Valid Store store) {
        store = service.updateStore(store);
        LOGGER.debug("Store updated with new valued " + store);
        return Response.ok(store).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteStore(
        @PathParam("id") Long id) {
        service.deleteStore(id);
        LOGGER.debug("Store deleted with " + id);
        return Response.noContent().build();
    }
}