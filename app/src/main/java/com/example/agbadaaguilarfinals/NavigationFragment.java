package com.example.agbadaaguilarfinals;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class NavigationFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private Button btnSearch;
    private Button btnClear;
    private EditText editTextLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_navigation,container,false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(NavigationFragment.this);

        btnSearch = (Button) v.findViewById(R.id.buttonSearch);
        btnClear = (Button) v.findViewById(R.id.buttonClear);
        editTextLocation = (EditText) v.findViewById(R.id.editTextLocation);

        btnSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String location = editTextLocation.getText().toString();

                if (location.length() != 0) {

                    List<Address> addressList = null;
                    if (location != null || !location.equals("")) {
                        Geocoder geocoder = new Geocoder(getContext());
                        try {
                            addressList = geocoder.getFromLocationName(location, 1);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        map.addMarker(new MarkerOptions().position(latLng).title(location));
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                        Toast.makeText(getActivity().getApplicationContext(), address.getLatitude() + " " + address.getLongitude(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "First location is empty!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextLocation.setText("");
                map.clear();
            }
        });

        return v;
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        LatLng LetranCalamba = new LatLng(14.1887,121.1654);
        map.moveCamera(CameraUpdateFactory.newLatLng(LetranCalamba));
    }

}