package com.appalber.firststepsgeolocalizacion;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends AppCompatActivity {

    LocationManager lm = null;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==666){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //TENEMOS PERMISO

            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        comprobarPermisos();


//        List<String> providers = lc.getProviders(true);
//        Log.d("Proveedores", providers.toString());
}
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void comprobarPermisos() {
        //Esto significa que si no tenemos permiso FINE y tampovo el COARSE, entra en el if y hace
        // return para evitar acceder a la ubicación
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String [] permisos = {Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permisos, 666);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        else{
            obtenerUbicacion();
        }


    }

    private void obtenerUbicacion() {
        LocationListener oyenteLocalizacion = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
               // lm.removeUpdates(this);
                Log.d("CAMBIANTE", location.getLatitude()+" "+location.getLongitude());
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Aquí no entramos nunca porque la llamada a este método se hace desde el else
            //del método comprobarPermisos()
            return;
        }

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, oyenteLocalizacion);
        Location localizacion = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (localizacion != null) {
            Log.d("POSICION", localizacion.getLatitude() + ", " + localizacion.getLongitude());
        }


    }
}


//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        //Habilitamos el botón cuando el apa esté ready
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }
