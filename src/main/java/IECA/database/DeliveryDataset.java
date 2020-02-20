package IECA.database;

import IECA.logic.Restaurant;
import IECA.logic.Delivery;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class DeliveryDataset extends DatasetManager {
    @Override
    public void addToDataset(String datasetInString) throws IOException {
        ObjectMapper deliveryMapper = new ObjectMapper();
        ArrayList<Delivery> deliveries;
        deliveries = deliveryMapper.readValue(datasetInString, new TypeReference<ArrayList<Delivery>>(){});
        setDeliveries(deliveries);
    }
}
