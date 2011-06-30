package com.Smart_task1;

import java.util.ArrayList;

import com.Smart_task1.R.id;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class Mylist extends ListActivity implements OnItemClickListener{
	TextView tname,tddate,tdesp,ttime;
	DatabaseHelper db;
	int callid;
	String cnames,task,num;
	private ArrayList<String> results = new ArrayList<String>();
	 public void onCreate(Bundle savedInstanceState)
	 {
	        super.onCreate(savedInstanceState);
	        db=new DatabaseHelper(this);
	        //setContentView(R.layout.showdetails);
	        Bundle bun=getIntent().getExtras();
	        task=bun.getString("task");
	        
	        System.out.println("task is..."+task);
	       // show(task);
	        Cursor c = db.getcallid(task);
 	        while(c.moveToNext())
 			{
 				callid=c.getInt(0);
 				System.out.print("cid...:-"+callid);

 			}
 	       System.out.print("cid...:-"+callid);
	        Cursor c1=db.getcallnamelist(callid);
			   while(c1.moveToNext())
			   {
				   cnames=c1.getString(0);
				   System.out.print("cnamess...:-"+cnames);
				   results.add(cnames);
			   }
			   
		        displayResultList();  
	 }
	 private void displayResultList()
	 {
			CheckBox cb=new CheckBox(this);  
			 
			TextView tView = new TextView(this);
	        tView.setText("Todays Task!!! ");
	        //tView.setTextColor("#000000");
	        getListView().addHeaderView(tView);
	      //  getListView().setBackgroundColor(R.drawable.back);
	        
	        

	        setListAdapter(new ArrayAdapter<String>(this,
	                android.R.layout.simple_list_item_1, results));
	        getListView().setTextFilterEnabled(true);
	        getListView().setOnItemClickListener(this);
		}
	

	 public void onItemClick(AdapterView<?> a, View v, int position, long id) 
	{
		 System.out.println("Position..."+position);
		 String ans= (String) a.getItemAtPosition(position);
		 System.out.println("value isss"+ans);
		   Uri lkup = Uri.withAppendedPath(People.CONTENT_FILTER_URI,ans);
		   Cursor idCursor = getContentResolver().query(lkup, null, null, null, null);
		   while (idCursor.moveToNext())
		   {
		     num =  idCursor.getString(idCursor.getColumnIndex(People.NUMBER));
		     System.out.println("number...."+num);
		   }
		   idCursor.close();

			    Intent intent = new Intent(Intent.ACTION_CALL);
			    intent.setData(Uri.parse("tel:" +num));
			    startActivity(intent);

		
	}

}