package com.orders;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	GridView grid;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 

		
		
		
		 String[] table = new String[] {};
		
		
		
		
		
		
		
		InputStream is=null;
		
		/*Alerts alerts=new Alerts();
		boolean b=n.testNetwork();
		if(!b)
		{
			alerts.networkError(context);
			return false;
		}*/
		 try {
			 HttpClient httpclient = new DefaultHttpClient();
			 HttpPost httppost = new HttpPost("http://vamsidhar.esy.es/orderslist.php");
			 HttpResponse response = httpclient.execute(httppost);
			 HttpEntity entity = response.getEntity();
			 is = entity.getContent();
			
			 Log.e("log_tag", "Orderlist.php success");
			 // Toast.makeText(getApplicationContext(), “pass”,Toast.LENGTH_SHORT).show();
			  } catch (Exception e) {
				  Log.e("log_tag", "Orders   failure" + e.toString());
				  //return false;				 					 
			  }
		//convert response to string
		  try {
		   BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
		  StringBuilder sb = new StringBuilder();
		 String line = null;
		  while ((line = reader.readLine()) != null) {
		  sb.append(line + "\n");
		  // Toast.makeText(getApplicationContext(), “Input Reading pass”, Toast.LENGTH_SHORT).show();
		  }
		  is.close();
		 
		 String result = sb.toString();
		 
		  JSONArray jArray=new JSONArray(result);
		  
		 table=new String[jArray.length()];
		  //JSONArray ar=new JSONArray(ob);
		  for(int i=0;i<jArray.length();i++)
		  {
			  JSONObject json_data = jArray.getJSONObject(i);
			  table[i]=json_data.getString("table_num");
		  
			  Log.e("log_tag", "Tablle num: "+table[i].toString());
			  
		  }
		  reader.close();
		  
		
		  //tv.setText(result);
		  } catch (Exception e) {
			  
		  Log.e("log_tag", "LOGON Error converting result" + e.toString()+"  asd"+e.getMessage()+"asd"+e.getCause());
		  				 
		  }	 

		
		
		
		
		
		
		
		
		
		
		
		
		
		
        
      grid=(GridView)findViewById(R.id.gridView1);
      ArrayAdapter<String> adap=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,table);
      grid.setAdapter(adap);
      grid.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent it=new Intent(MainActivity.this,Orders.class);
			it.putExtra("tablenumber", ""+((TextView)v).getText());
			startActivity(it);
			Toast.makeText(getApplicationContext(),"table",Toast.LENGTH_SHORT).show();
			
			
		}
    	  
	});
      
      Button b=(Button)findViewById(R.id.button1);
      b.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			finish();
			startActivity(getIntent());
		}
	});
        
        
    }

}
