package CallBacks;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public interface CallBackMapHistory {
    void showTrace(ArrayList<LatLng> locations);
}
