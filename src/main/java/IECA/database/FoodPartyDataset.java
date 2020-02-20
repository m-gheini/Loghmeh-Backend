package IECA.database;

import IECA.logic.Delivery;
import IECA.logic.Food;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class FoodPartyDataset extends DatasetManager{
    @Override
    public void addToDataset(String datasetInString) throws IOException {
        ObjectMapper foodMapper = new ObjectMapper();
        ArrayList<Food> foods;
        foods = foodMapper.readValue(datasetInString, new TypeReference<ArrayList<Food>>(){});
        setFoodsOnSale(foods);
    }
}
