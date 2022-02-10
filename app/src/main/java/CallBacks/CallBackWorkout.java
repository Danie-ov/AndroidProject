package CallBacks;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public interface CallBackWorkout {
    Location getMapCurrentLocation();
    float getDistancePoints(Location location);
}
