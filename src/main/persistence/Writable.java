package persistence;

import org.json.JSONObject;

//From the JsonSerializationDemo Provided from UBC Class CPSC 210
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

