package io.dmcapps.dshopping.store.address;

import java.time.Instant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.jboss.logging.Logger;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;

@MongoEntity(collection = "addresses")
public class Address extends PanacheMongoEntityBase {

    private static final Logger LOGGER = Logger.getLogger(
        Address.class);

    @BsonId
    @JsonSerialize(using = ToStringSerializer.class)
    public ObjectId id;
    public  String city;
    public  String addressLine1;
    public  String addressLine2;
    public  Location location;
    public  Instant dateCreated;
    public  Instant dateUpdated;

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
