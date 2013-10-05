package com.example.sanjuanlalaguna;

import org.json.JSONArray;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class detallelugarActivity extends FragmentActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_detallelugar);
    	inicializar();
    }
    
    public void inicializar(){
    	Intent myintent = getIntent();
    	String titulo = myintent.getStringExtra("titulo");
    	String lati = myintent.getStringExtra("lati");
    	String longi = myintent.getStringExtra("longi");
    	TextView txt1= (TextView)findViewById(R.id.textView1);
    	txt1.setText(titulo);
    	LatLng posic = new LatLng(Float.parseFloat(lati),Float.parseFloat(longi));
    	GoogleMap map = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();
    	map.addMarker(new MarkerOptions()
        .position(posic)
        .title(titulo));
    	//Valores de zoom mas altos son un zoom mas cercano
    	map.moveCamera(CameraUpdateFactory.newLatLngZoom(posic,16));
    	
    }


}
