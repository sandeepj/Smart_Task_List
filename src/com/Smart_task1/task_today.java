package com.Smart_task1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import android.content.Intent;
import android.database.Cursor;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
public class task_today extends Activity implements OnItemClickListener
{	private ArrayList<String> results = new ArrayList<String>();

	TextView tname,ddate,time,tv1;
		String tn,duedate,tm,sdate;
		ListView list;
		Button ok,delete;
		int tid,type;
		int selected=1;
	DatabaseHelper dbHelper;
	public void onCreate(Bundle savedInstanceState)
	 {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.task2);
	        list=(ListView)findViewById(R.id.list);
	     
	        tv1 = (TextView) findViewById(R.id.tv1);
	        Calendar cal = new GregorianCalendar();
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			sdate=(+ year + "-" + (month+1)+ "-" + day);	
			dbHelper=new DatabaseHelper(this);
	  
	    Cursor c=dbHelper.gettask_today(sdate);
	   showtask(c);
	   displayResultList();  
	       }
	private void displayResultList() {
	
		 
		TextView tView = new TextView(this);
        tView.setText("Todays Task!!! ");
        
        list.addHeaderView(tView);
    
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,results));
        list.setTextFilterEnabled(true);
        list.setOnItemClickListener(this);
	}
	private void showtask(Cursor c) 
	{
	
		//Calendar.get(Calendar.DATE);
		// StringBuilder ret = new StringBuilder("Saved Events:\n\n");
		    while (c.moveToNext())
		    {	
		    
		    	tn=c.getString(0);
		    	 duedate=c.getString(2);
		    	//tm=c.getString(1);
		    	results.add(tn);

			      
		    }
		 

		    }
	
		  
	 public void onItemClick(AdapterView<?> a, View v, int position, long id) 
	 {
		 //showtask(position);
		 System.out.println("Position..."+position);
		 String ans= (String) a.getItemAtPosition(position);
		 System.out.println("value isss"+ans);
		 Cursor c=dbHelper.gettaskid(ans);
		 while(c.moveToNext())
		 {
			 tid=c.getInt(0);
			 type=c.getInt(1);
			 System.out.println("tid..."+tid+"type==="+type);
		 }
		 if(type==0)
		 {
			 Intent i= new Intent(this,DDetails.class);
			 Bundle bun=new Bundle();
			 bun.putInt("tid",tid);
			 i.putExtras(bun);
			 startActivity(i);
			
		 }
		 else if(type==1)
		 {
			 Intent i= new Intent(this,CDetails.class);
			 Bundle bun=new Bundle();
			 bun.putInt("tid",tid);
			 i.putExtras(bun);
			 startActivity(i);
			
		 }
		 else if(type==2)
		 {
			 Intent i= new Intent(this,GDetails.class);
			 Bundle bun=new Bundle();
			 bun.putInt("tid",tid);
			 i.putExtras(bun);
			 startActivity(i);
			
		 }
		/* Intent i= new
		 *  Intent(this,Details.class);
		 Bundle bun=new Bundle();
		 bun.putInt("task",tid);
		 i.putExtras(bun);
		 startActivity(i);*/
		 
		 
	}
		
}



