package com.orders;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Orders extends ListActivity {

	
	Context context=this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orders);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 



		
		Intent it=getIntent();
		String tablen=it.getStringExtra("tablenumber");
		
		TextView tv=(TextView)findViewById(R.id.textView1);
		tv.setText("Table "+tablen);
		
		ArrayList<NameValuePair> a1=new ArrayList<NameValuePair>();
		//a1.add(new BasicNameValuePair("pname", pname));
		//a1.add(new BasicNameValuePair("padd", padd));
		a1.add(new BasicNameValuePair("tablenum", tablen));
		
		
		
		 Log.e("log_tag", "ONE");

		 Log.e("ArrayList", ""+a1.toString());
		 
		 
		 
		 InputStream is2 = null;
			// TODO Auto-generated method stub
			 try 
			 {
				
				 
				 HttpClient httpclient = new DefaultHttpClient();
				 HttpPost httppost = new HttpPost("http://vamsidhar.esy.es/final.php");
				 httppost.setEntity(new UrlEncodedFormEntity(a1));
				 HttpResponse response = httpclient.execute(httppost);
				 HttpEntity entity = response.getEntity();
				  is2 = entity.getContent();
				 
				// Toast.makeText(getApplicationContext(), "Connection Success Inserted",Toast.LENGTH_SHORT).show();					 
				 
				 //Log.e("log_tag", " in doinbackground connection success");
								
				 // Toast.makeText(getApplicationContext(), “pass”,Toast.LENGTH_SHORT).show();
				  } catch (Exception e) 
				  {
					//  x="false";
					  Log.e("log_tag", "Error in http connection" + e.toString());
				  }
				//convert response to string
			  try {
				  Log.e("log_tag", "TWO");
			   BufferedReader reader = new BufferedReader(new InputStreamReader(is2,"iso-8859-1"));
			  StringBuilder sb = new StringBuilder();
			 String line = null;
			  while ((line = reader.readLine()) != null) {
			  sb.append(line + "\n");
			  // Toast.makeText(getApplicationContext(), “Input Reading pass”, Toast.LENGTH_SHORT).show();
			  }
			  is2.close();
			 
			  Log.e("log_tag", "THREE");
			 String result = sb.toString();
			 
			 
			 Log.e("log_tag", " JSON"+result);
			 

			 boolean x=result!=null;
			 Log.e("log_tag", "444");
			 if(!x||result.equals("")||result.equals(null)||result==""||result==null)
			 {						 
				 Log.e("log_tag", "2222");
				Toast.makeText(getApplicationContext(),"No ResultsFound",Toast.LENGTH_SHORT).show();
			 }
			 Log.e("log_tag", "1111");
			  JSONArray jArray=new JSONArray(result);
			  Log.e("log_tag", "FOUR");
			  //JSONArray ar=new JSONArray(ob);
			  
			//  String tabnum=new String("");
			  String[] item=new String[jArray.length()];
			  String[] quantity=new String[jArray.length()];
			  
			  String[] h=new String[jArray.length()];
			  for(int i=0;i<jArray.length();i++)
			  {
				  Log.e("FOR", ""+i);
				  JSONObject json_data = jArray.getJSONObject(i);
				  item[i]=json_data.getString("item");
				  quantity[i]=json_data.getString("quantity");
				  
				  h[i]=item[i]+"               "+quantity[i];
				  //Log.e("log_tag", "LOOP fname: "+name[i].toString()+" bgroup : "+bgr[i].toString()+"\nPhone:"+ phone[i].toString()+" loc : "+location[i].toString());
				   
			  }
			  
			  /*
			  if(jArray.length()==0)
			  {
				  Toast.makeText(context, "No ResultsFound",Toast.LENGTH_SHORT).show();
			  }
			  else
			  {
				  reader.close();
				  //Intent it=new Intent(Orders.this, SearchActivity.class);
				  for(int i=0;i<jArray.length();i++)
				  {
					  it.putExtra("name"+i, name[i]);
					  it.putExtra("bgroup"+i, bgr[i]);
					  it.putExtra("location"+i, location[i]);
					  it.putExtra("phone"+i, phone[i]);
				  }
				  
				  it.putExtra("noOfPeople",""+jArray.length());
				  startActivity(it);
				  
				  
			  }
			  
			  */

				ListView listview= getListView();
		    	//	listview.setChoiceMode(listview.CHOICE_MODE_NON	E);
		    	//	listview.setChoiceMode(listview.CHOICE_MODE_SINGLE);
		    		//listview.setAdapter(getListAdapter());
		    		listview.setChoiceMode(listview.CHOICE_MODE_MULTIPLE);
		    		listview.setTextFilterEnabled(true);
//	    			listview.scrollBy(0, 20);
	    			setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,h));
	    			 Log.e("WHOLE",""+h);
		    
			  
			  
			  //tv.setText(result);
			  } catch (Exception e) {
				  
			  Log.e("log_tag", " Error converting result" + e.toString());
			  				 
			  }	 
		
		
		
	}

}
