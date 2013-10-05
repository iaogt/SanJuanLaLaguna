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


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {

	private ProgressDialog pd = null;
	private Object data = null; 
	private JSONArray jArray=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
        this.pd = ProgressDialog.show(this, "Cargando...", "Descargando informaci—n",true,false);
        new DescargaDatos().execute();

        //cargarJson();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public JSONArray darDato(int pos){
    	JSONArray jarr=null;
    	try{
    		jarr = this.jArray.getJSONArray(pos);
    	}catch(Exception e){
    		System.out.println("error de JSON");
    	}
    	return jarr;
    }
    
    /**
     * carga el json
     * */
    public void cargarTabla(){
    	String[] strarr = (String[]) data;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,strarr);
        ListView lst = (ListView)findViewById(R.id.listView1);
        lst.setAdapter(adapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
		        Intent detalle = new Intent(MainActivity.this,detallelugarActivity.class);
		        try{
		        JSONArray xtr = MainActivity.this.darDato(arg2);
		        detalle.putExtra("titulo",xtr.getString(0));
		        detalle.putExtra("lati",xtr.getString(1));
		        detalle.putExtra("longi",xtr.getString(2));
		        }catch(Exception e){
		        	
		        }
				startActivity(detalle);
			}
        });
    }
    
    public class DescargaDatos extends AsyncTask<String,Void,Object>{
    	
    	protected void onPostExecute(Object result){
    		MainActivity.this.data = result;
    		MainActivity.this.cargarTabla();
    		if(MainActivity.this.pd != null){
    			MainActivity.this.pd.dismiss();
    		}
    	}

		@Override
		protected Object doInBackground(String... arg0) {
			InputStream in = null;
		    String result = null;
		    HttpURLConnection urlConnection = null;
		    String[] strarray=null;
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
		        MainActivity.this.jArray = jObject.getJSONArray("rows");
		        strarray = new String[MainActivity.this.jArray.length()];
		        for(int i = 0; i<MainActivity.this.jArray.length();i++){
		        	JSONArray j = MainActivity.this.jArray.getJSONArray(i);
		        	strarray[i]=j.getString(0);
		        }

		    } catch (Exception e) { 
		    	System.out.println("rayooos:"+e.getMessage()+","+e.toString());
		    }
		    finally {
		    	if(urlConnection!=null)
		    		urlConnection.disconnect();
		    }
			return strarray;
		}
    	
    }
    
    
}


