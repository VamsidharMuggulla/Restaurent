package com.restaurent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends ListActivity {

       	//String[] city= {};
       	String[] sbu;
       	String[] item;
       	String[] price;
       	Context context=this;
   		//String[] x;
       	int xx;
       	
       	
       	int[] quantity;
       	JSONArray jArray;
    	@Override
    	protected void onCreate(Bundle savedInstanceState) {
    		super.onCreate(savedInstanceState);
    		setContentView(R.layout.activity_main);
    		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    		StrictMode.setThreadPolicy(policy); 
    	
    		Button order=(Button)findViewById(R.id.order);
    		order.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				int count=0;
					Intent it=new Intent(MainActivity.this,ReviewActivity.class);
					for(int i=0;i<jArray.length();i++)
					{
						if(quantity[i]!=0) {			
							count++;
						it.putExtra("item"+i, item[i]);
						it.putExtra("price"+i, price[i]);
						it.putExtra("quantity"+i,""+quantity[i]);
						}
						else
						   continue;
					}
					it.putExtra("noOfItems",""+jArray.length());
					it.putExtra("noOfItems2",""+count);
					for(int a=0;a<quantity.length;a++)
		    			Log.e("A Loop",""+quantity[a]);
		    		
					startActivity(it);
				}
			});
    		
    		
    	ListView listview= getListView();
    	//	listview.setChoiceMode(listview.CHOICE_MODE_NON	E);
    	//	listview.setChoiceMode(listview.CHOICE_MODE_SINGLE);
    		//listview.setAdapter(getListAdapter());
    		listview.setChoiceMode(listview.CHOICE_MODE_MULTIPLE);
    		
    		//--	text filtering
    		
    		
			 InputStream is2 = null;
				// TODO Auto-generated method stub
				 try 
				 {
					
					 
					 HttpClient httpclient = new DefaultHttpClient();
					 
					/* httpclient.getParams().setParameter(
							    HttpMethodParams.USER_AGENT,

							    "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2"
							);
					 ((AbstractHttpClient) httpclient).setCookieStore(new BasicCookieStore());
					 httpclient.getParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
					 */
					 HttpPost httppost = new HttpPost("http://vamsidhar.esy.es/search.php");
					 
					 
					// CookieStore cookieStore = new BasicCookieStore();
					    // Create local HTTP context
					 //   HttpContext localContext = new BasicHttpContext();
					    // Bind custom cookie store to the local context
					//    localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
					
					 
					 //httppost.setEntity(new UrlEncodedFormEntity(a1));
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
				 

				/* boolean x=result!=null;
				 Log.e("log_tag", "444");
				if(!x||result.equals("")||result.equals(null)||result==""||result==null)
				{						 
					 Log.e("log_tag", "2222");
					Toast.makeText(getApplicationContext(),"No ResultsFound",Toast.LENGTH_SHORT);
				 }*/
				 Log.e("log_tag", "1111");
				  jArray=new JSONArray(result);
				  Log.e("log_tag", "FOUR");
				  //JSONArray ar=new JSONArray(ob);
				  
				  item=new String[jArray.length()];
				 price=new String[jArray.length()];
				  quantity=new int[jArray.length()];
				  //String[] location=new String[jArray.length()];
				  //String[] phone=new String[jArray.length()];
				 sbu=new String[jArray.length()];
				  for(int i=0;i<jArray.length();i++)
				  {
					  Log.e("FOR", ""+i);
					  JSONObject json_data = jArray.getJSONObject(i);
					  item[i]=json_data.getString("item");
					  price[i]=json_data.getString("price");
					  Log.e("fff",item[i]+"      Rs"+price[i]);
					  int x=item[i].length()+5;
					  sbu[i]=item[i];
					  for(int j=45;j>x;j--)
						  sbu[i]+=" ";
					  sbu[i]=sbu[i]+"Rs."+price[i];
					  
					  Log.e("log_tag", "LOOP Item: "+item[i].toString()+" Price : "+price[i].toString());
					   
				  }
				  
				  
				 
				  
				/*  if(jArray.length()==0)
				  {
					  Toast.makeText(getApplicationContext(), "No ResultsFound",Toast.LENGTH_SHORT);
				  }
				  else
				  {
					  reader.close();
					  Intent it=new Intent(MainActivity.this,ReviewActivity.class);
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
				  
				
				  //tv.setText(result);
				  } catch (Exception e) {
					  
				  Log.e("log_tag", " Error converting result" + e.toString());
				  				 
				  }	 

    		
    		
    		
    		
    		
    		
    		/*
    		x=new String[sbu.length];
 
    		for(int i=0;i<sbu.length;i++)
    		{
    			x[i]=sbu[i].toString();
    		}
    		
    		*/
    		
    		listview.setTextFilterEnabled(true);
//    			listview.scrollBy(0, 20);
    			setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,sbu));
    			 Log.e("WHOLE",""+sbu);
    	}
    	
    	public void onListItemClick(ListView parent, View v,final int position,long id){
    		  xx=position;
    		final CheckedTextView item = (CheckedTextView) v;
    		//Toast.makeText(this, sbu[position] +item.isChecked(), Toast.LENGTH_SHORT).show();
    		if(item.isChecked())
    		{ 		
    		
    		
    		LayoutInflater li = LayoutInflater.from(context);
			View promptsView = li.inflate(R.layout.list_view_adapter, null);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

			// set prompts.xml to alertdialog builder
			alertDialogBuilder.setView(promptsView);

			final Spinner userInput = (Spinner) promptsView
					.findViewById(R.id.spinner1);

			// set dialog message
			alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									
									// get user input and set it to result
									// edit text
									quantity[xx]=Integer.parseInt(userInput.getSelectedItem().toString());
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									item.setChecked(false);
									dialog.cancel();
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();

    		}    	
    		else
    		{
    			quantity[position]=0;
    		}
    		
    		for(int a=0;a<quantity.length;a++)
    			Log.e("A Loop",""+quantity[a]);
    		
    		
    	}	       
    	
    	
    }
