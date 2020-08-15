package io.dmcapps.dshopping.store;

import java.time.Instant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.jboss.logging.Logger;

import io.dmcapps.dshopping.store.address.Address;
import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;

@MongoEntity(collection = "stores")
public class Store extends PanacheMongoEntityBase {
    
    private static final Logger LOGGER = Logger.getLogger(
            Store.class);

    @BsonProperty("_id")
    @BsonId
    // @JsonSerialize(using = ToStringSerializer.class)
    public ObjectId id;
    public String name;
    public String email;
    public String mobile;
    public Address address;
    public Instant dateCreated;
    public Instant dateUpdated;


    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = null;
        try {
            jsonResult = mapper
                .writeValueAsString(this);
            
        } catch ( JsonProcessingException  e) {
            LOGGER.error(e.getMessage());
        }
        return jsonResult;
    }
}