package com.example.sanjuanlalaguna;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONObject;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("empieza...");
        cargarJson();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /**
     * carga el json
     * */
    public void cargarJson(){
	    InputStream in = null;
	    String result = null;
	    HttpURLConnection urlConnection = null;
	    try {
		    URL url = new URL("https://www.googleapis.com/fusiontables/v1/query?sql=select%20*%20from%201hKN7NLPXk9J-NHre-qj5PRHFop7eSIQ-zv4qHos&key=AIzaSyAzFJf7mcuzbJuf_eE0F4KZh9fhVKab0jk");
		    urlConnection = (HttpURLConnection) url.openConnection();

	    	System.out.println("lo intenta");
	        System.out.println("tuvo la direcci—n");
	        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"), 8);
	        StringBuilder sb = new StringBuilder();
	
	        String line = null;
	        while ((line = reader.readLine()) != null)
	        {
	            sb.append(line + "\n");
	        }
	        result = sb.toString();
	        System.out.println(result);
	        
	        JSONObject jObject = new JSONObject(result);
	        JSONArray jArray = jObject.getJSONArray("rows");
	        String[] strarray = new String[jArray.length()];
	        for(int i = 0; i<jArray.length();i++){
	        	JSONArray j = jArray.getJSONArray(i);
	        	strarray[i]=j.getString(0);
	        }
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,strarray);
	        ListView lst = (ListView)findViewById(R.id.listView1);
	        lst.setAdapter(adapter);
	    } catch (Exception e) { 
	    	System.out.println("rayooos:"+e.getMessage()+","+e.toString());
	    }
	    finally {
	    	if(urlConnection!=null)
	    		urlConnection.disconnect();
	    }
    }
    
    
}


