package com.restaurent;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
      //  Checkbo
        ListView menuList;
        CheckBox c[]=new CheckBox[5];
       // c[1].set
     //   for(int i=0;i<c.length;i++)
       // 	c[i].append("OK");
       
        menuList=(ListView)findViewById(R.id.menulist);
        
        ArrayAdapter<CheckBox> arr=new ArrayAdapter<CheckBox>(this,R.layout.menu_list,c); 
        menuList.setAdapter(arr);
    }
}
