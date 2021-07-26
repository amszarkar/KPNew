package velociter.kumar.property.data;

import java.io.Serializable;

public class Location implements Serializable {
    String id,locationName;

    public Location(String id, String locationName) {
        this.id = id;
        this.locationName = locationName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
