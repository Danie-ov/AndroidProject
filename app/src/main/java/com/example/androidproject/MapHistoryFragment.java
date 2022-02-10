package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapHistoryFragment extends Fragment {

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    GoogleMap gMap;
    Polyline polyline;

    CallBackMapHistory callBackMapHistory;

    public void setCallBackMapHistory(CallBackMapHistory callBackMapHistory) {
        this.callBackMapHistory = callBackMapHistory;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_history, container, false);
        supportMapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        return view;
    }

    public void displayTrack(ArrayList<LatLng> locations){
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                gMap = googleMap;
                if(polyline != null) polyline.remove();
                PolylineOptions polylineOptions = new PolylineOptions()
                        .addAll(locations).clickable(true);
                polyline = gMap.addPolyline(polylineOptions);
                polyline.setColor(Color.BLUE);
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locations.get(0), 18));
            }
        });
    }

}