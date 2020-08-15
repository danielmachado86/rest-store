package io.dmcapps.dshopping.store;

import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
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

    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Store.class, type = SchemaType.ARRAY)))
    @APIResponse(responseCode = "204", description = "No stores")
    @GET
    public Response getAllStoreStocks() {
        List<Store> stores = service.findAllStores();
        LOGGER.debug("Total number of stores " + stores);
        return Response.ok(stores).build();
    }

    @Operation(summary = "Returns all the stores from the database")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Store.class, type = SchemaType.ARRAY)))
    @APIResponse(responseCode = "204", description = "No stores")
    @GET
    @Path("/search")
    public Response getNearByStores(
            @Parameter(description = "longitud", required = true)
            @QueryParam("lon") double lon,
            @Parameter(description = "latitud", required = true)
            @QueryParam("lat") double lat,
            @Parameter(description = "Store search range", required = true)
            @QueryParam("range") int range) {
        LOGGER.info("Query params lon: " + String.valueOf(lon) + "lat: " + String.valueOf(lat));
        List<Store> stores = service.findNearbyStores(lon, lat, range);
        LOGGER.info("Total number of stores " + stores.size());
        return Response.ok(stores).build();
    }

    @Operation(summary = "Returns a store for a given identifier")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Store.class)))
    @APIResponse(responseCode = "204", description = "The store is not found for a given identifier")
    @GET
    @Path("/{id}")
    public Response getStore(
            @Parameter(description = "Store identifier", required = true)
            @PathParam("id") String id) {
        ObjectId objectId = new ObjectId(id);
        Store store = service.findStoreById(objectId);
        if (store != null) {
            LOGGER.info("Found store " + store);
            return Response.ok(store).build();
        } else {
            LOGGER.info("No store found with id " + objectId);
            return Response.noContent().build();
        }
    }

    @Operation(summary = "Creates a valid store")
    @APIResponse(responseCode = "201", description = "The URI of the created store", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = URI.class)))
    @POST
    public Response createStore(
        @RequestBody(required = true, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Store.class)))
        @Valid Store store, @Context UriInfo uriInfo) {
        store = service.persistStore(store);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(store.id.toString());
        LOGGER.debug("New store created with URI " + builder.build().toString());
        return Response.created(builder.build()).build();
    }

    @Operation(summary = "Updates an exiting  store")
    @APIResponse(responseCode = "200", description = "The updated store", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Store.class)))
    @PUT
    public Response updateStore(
        @RequestBody(required = true, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Store.class)))
        @Valid Store store) {
        store = service.updateStore(store);
        LOGGER.debug("Store updated with new valued " + store);
        return Response.ok(store).build();
    }

    @Operation(summary = "Deletes an exiting store")
    @APIResponse(responseCode = "204")
    @DELETE
    @Path("/{id}")
    public Response deleteStore(
        @Parameter(description = "Store identifier", required = true)
        @PathParam("id") Long id) {
        service.deleteStore(id);
        LOGGER.debug("Store deleted with " + id);
        return Response.noContent().build();
    }
}