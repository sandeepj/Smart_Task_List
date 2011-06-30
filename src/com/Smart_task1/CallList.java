package com.Smart_task1;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.OnChildClickListener;

public class CallList extends Activity implements OnItemClickListener
{	
DatabaseHelper db;
Button search,done;
EditText ecname,esname;
TextView tv1;
String name,contname,listname;
int callid;
private ArrayList<String> res = new ArrayList<String>();
private ListView lv1;
final static String LOG_TAG = "PocketMagic";
private static final int PICK_REQUEST=1337;
public void onCreate(Bundle savedInstanceState)
{
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);  
	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	setContentView(R.layout.callist1);
	db=new DatabaseHelper(this);
	search=(Button)findViewById(R.id.search);
	done=(Button)findViewById(R.id.done);
	ecname=(EditText)findViewById(R.id.elname);
	esname=(EditText)findViewById(R.id.con_name);
	tv1=(TextView)findViewById(R.id.tv1);
	lv1=(ListView)findViewById(R.id.ListView1);
	lv1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	lv1.setVisibility(View.GONE);
	search.setOnClickListener(new OnClickListener() 
	{
		public void onClick(View v)
		{	
			lv1.setVisibility(View.VISIBLE);
			lv1.setTextFilterEnabled(true);
			listname = ecname.getText().toString();
			System.out.println("listname...."+listname);
			db.insercalllist(listname);
			Cursor c = db.getcallid(listname);
			while(c.moveToNext())
			{
				callid=c.getInt(0);
				System.out.print("cid...:-"+callid);

			}
			System.out.println("callid....."+callid+" name..."+name);
			showList();
		}
	});
	done.setOnClickListener(new OnClickListener() {
		public void onClick(View v)
		{	if(listname==null)
			show(); 

		}
	});
}

protected void show() 
{
	Intent i = new Intent(this,Smart_task1.class);     
	startActivity(i);

}
/*
	type :func
	name:showList
	param:none
	return type:void
	date:29-06-2011
	purpose:to show the contacts from the addressbook based on the name searched
*/
protected void showList() 
{		
	contname = esname.getText().toString();
	if (contname != null && contname.length()>0)
	{
		Uri lkup = Uri.withAppendedPath(People.CONTENT_FILTER_URI, contname);        
		String results = "";
		Cursor idCursor = getContentResolver().query(lkup, null, null, null, null);
		while (idCursor.moveToNext())
		{
			long lid = idCursor.getLong(idCursor.getColumnIndex(People._ID));
			//String key = idCursor.getString(idCursor.getColumnIndex(People.LOOKUP_KEY));
			String name = idCursor.getString(idCursor.getColumnIndex(People.DISPLAY_NAME));
			String num =  idCursor.getString(idCursor.getColumnIndex(People.NUMBER));
			Log.d(LOG_TAG, "search: "+lid+"name: "+name+ "nummm..."+num); 
			results+=("\n"+"Name:"+name+" ID:"+lid);
			res.add(name);
			lv1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,res));

			lv1.setTextFilterEnabled(true);
			lv1.setOnItemClickListener(this);
		}
		idCursor.close();
		
	}
	
}
/* gets the name of contact i=on selecting a particular list*/
public void onItemClick(AdapterView<?> a, View v, int position, long id)
{
	System.out.println("Position..."+position);
	name= (String) a.getItemAtPosition(position);
	db.insertcall(callid,name);
	System.out.println("Position..."+position+" name===="+name);

}


}
