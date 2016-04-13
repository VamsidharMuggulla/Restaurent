package com.restaurent;

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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ReviewActivity extends ListActivity {

	
	Context context=this;
	String tableNum="0";
	String[] item;
	String[] quanti;
	String[] price;
	String[] l;
	Button ib;
	TextView tvv;
	int total=0;
	@SuppressWarnings("null")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy);
		
		 ib=(Button)findViewById(R.id.back);
		ib.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				finish();
			}
		});
		
		
		Intent it=getIntent();
		
		int x=Integer.parseInt(it.getStringExtra("noOfItems"));
		int y=Integer.parseInt(it.getStringExtra("noOfItems2"));
		String[] itemQuantiPrice=new String[x];
		
		 item=new String[y];
		quanti=new String[y];
		 price=new String[y];
		
		int u=0;
		int[] index=new int[x];
		for(int i=0,o=0;i<x;i++)
		{
			//Log.e("LALA1", ""+it.getStringExtra("item"+i).length());
			
			
			//it.getData().getQueryParameter("item"+i);
			boolean b=it.getStringExtra("item"+i) != null;
			if(b) {
				item[o]=it.getStringExtra("item"+i);
				//Log.e("if",""+item[i]);
				quanti[o]=it.getStringExtra("quantity"+i);
				price[o]=it.getStringExtra("price"+i);
				o++;
				Log.e("LALA2", ""+it.getStringExtra("item"+i));
				itemQuantiPrice[i]=it.getStringExtra("item"+i)+"    "+it.getStringExtra("quantity"+i)+"    " +
					""+it.	getStringExtra("price"+i);
				//l[i]=itemQuantiPrice[i];
				index[i]=1;
			u++;
			}	
			else
				index[i]=0;
			Log.e("phone "+i,""+itemQuantiPrice[i]);
		}
	 //=new String[u];
		l=new String[u];
	try {
			//boolean r=false;
		for(int a=0,h=0;a<itemQuantiPrice.length;a++)
		{
			
			//r=itemQuantiPrice[a]==null;
			if(index[a]==1) {

				l[h]=itemQuantiPrice[a];
				
				Log.e("LOO",""+l[h]);
				h++;
				}			
		} } catch(Exception e) {
			Log.e("POINT",""+e.getCause());
		}

    	ListView listview= getListView();
    	//	listview.setChoiceMode(listview.CHOICE_MODE_NON	E);
    	//	listview.setChoiceMode(listview.CHOICE_MODE_SINGLE);
    		listview.setAdapter(getListAdapter());
    		

		
		listview.setTextFilterEnabled(true);
//		listview.scrollBy(0, 20);
		setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,l));
		Log.e("REVIEWACTIVITYLOG","Hello");
		
		
		
		
		
		
		
		
		
		final Button done=(Button)findViewById(R.id.orderdone);
		done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			

	    		LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.tablenumber, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);

				final Spinner userInput = (Spinner) promptsView
						.findViewById(R.id.tablenum);

				// set dialog message
				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										
										// get user input and set it to result
										// edit text
										tableNum=userInput.getSelectedItem().toString();
										
									
										ArrayList<NameValuePair> al=new ArrayList<NameValuePair>();
										al.add(new BasicNameValuePair("tablenum",tableNum));
										al.add(new BasicNameValuePair("numbeer",item.length+""));
										for(int e=0;e<item.length;e++) {
										al.add(new BasicNameValuePair("item"+e,item[e]));
										al.add(new BasicNameValuePair("quantity"+e,quanti[e]));
										al.add(new BasicNameValuePair("price"+e,price[e]));
										
										Log.e("LISTOO",""+item[e]+" "+quanti[e]+" ");
										
										total=total+Integer.parseInt(quanti[e])*Integer.parseInt(price[e]);
										}
										al.add(new BasicNameValuePair("total",total+""));
										
										
										Log.e("ArrayList", ""+al.toString());
										
										InputStream is2 = null;
										// TODO Auto-generated method stub
										 try 
										 {
											
											 
											 HttpClient httpclient = new DefaultHttpClient();
											 HttpPost httppost = new HttpPost("http://vamsidhar.esy.es/placeorder.php");
											 httppost.setEntity(new UrlEncodedFormEntity(al));
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
										 try 
										 {
											 BufferedReader br=new BufferedReader(new InputStreamReader(is2,"iso-8859-1"));
											 StringBuilder sb=new StringBuilder();
											 String line=null;
											 while((line=br.readLine())!=null)
												 sb.append(line+"\n");
											 is2.close();
											String result2=sb.toString();
											Log.e("LOG INSRT1==",result2);
											/*	Log.e("LOG INSRT2",""+result2);
											Log.e("LOG INSRT3==",""+result2.equals(true));
											Log.e("LOG INSRT4==",""+result2.equals(false));
											Log.e("LOG INSRT5==",""+result2.equals("false"));
											Log.e("LOG INSRT6==",""+result2.equals("true"));
											
											
											
											Log.e("LOG INSRT7==",""+(result2=="true"));*/
											String x="true";
											//x=result2; != null;
											
											Log.e("res",""+result2.equals(false));
											
											Log.e("X==",""+x);
											if(result2.contains("false"))
											{
												x="false";
												Log.e("resulllllll",""+result2.equals(false));
												
												
											}
											
											 if(!x.equals("false"))
											 {
											 
											 //DISPLAY TOTAL
											 Log.e("FALSE","RESULT IS FALSE");
											 Toast.makeText(getApplicationContext(),"Order Placed", Toast.LENGTH_SHORT).show();
											 done.setEnabled(false);
											 
											 //ib.setEnabled(false);
											 
											 //finishAffinity();
											 //Intent it=new Intent(ReviewActivity.this,ButtonActivity.class);
											// it.putExtra("Username", email);
											// Log.e("LoginActivity=",email);
											// startActivity(it);
											// finishAffinity();
											 
										 }
											else
											{
												Log.e("FALSE","RESULT IS NOT FALSE");
												Toast.makeText(getApplicationContext(),"FAILED TO PLACE ORDER,\n", Toast.LENGTH_LONG).show();
											}
										 }
										 catch(Exception e) 
										 {
											 //x="false2";
											 Log.e("Exception String",e.toString());
											 Log.e("Exception Message",e.getMessage());
											 Log.e("Exception Cause",e.getCause().toString());
											 //Log.e("Exception StackTrace",e.printStackTrace());
											 
											 Log.e("log_tag","Error  in insert "+e.toString());	
										}
									
										
										
										
										
									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										done.setActivated(false);
										Toast.makeText(context,"Select Table Number to Place Order...", Toast.LENGTH_SHORT).show();
										dialog.cancel();
										tvv=(TextView)findViewById(R.id.total);
										tvv.setText("Bill Inc All Taxes : "+total);
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
		
				
				
			}
		});
		
	}
}

