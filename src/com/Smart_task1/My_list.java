package com.Smart_task1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import com.Smart_task1.DatabaseHelper;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
public class My_list extends ListActivity implements OnItemClickListener
{	private ArrayList<String> results = new ArrayList<String>();
	TextView tname,ddate,time,tv1;
		String lname;
	DatabaseHelper dbHelper;
	public void onCreate(Bundle savedInstanceState)
	 {
	        super.onCreate(savedInstanceState);
	       
 
	        dbHelper=new DatabaseHelper(this);
	        Cursor c=dbHelper.getcalllist();
	        showtask(c);
	        displayResultList();  
	       }
	private void displayResultList() {
		CheckBox cb=new CheckBox(this);  
		 
		TextView tView = new TextView(this);
        tView.setText("Call Lists!!! ");
        //tView.setTextColor("#000000");
        getListView().addHeaderView(tView);
      //  getListView().setBackgroundColor(R.drawable.back);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, results));
        getListView().setTextFilterEnabled(true);
        getListView().setOnItemClickListener(this);
	}
	private void showtask(Cursor c) 
	{
	
		//get cal list name
		    while (c.moveToNext())
		    {	
		    
		    	lname=c.getString(1);
		    	System.out.println("lnmae:---"+lname);
		    	results.add(lname);

			      
		    }
		 

		    }
	
		  
	 public void onItemClick(AdapterView<?> a, View v, int position, long id) 
	 {
		//get name of the list on item select
		 System.out.println("Position..."+position);
		 String ans= (String) a.getItemAtPosition(position);
		 System.out.println("value isss"+ans);
		 Intent i= new Intent(this,Mylist.class);
		 Bundle bun=new Bundle();
		 bun.putString("task",ans);
		 i.putExtras(bun);
		 startActivity(i);
		
		 
	}
		
}



