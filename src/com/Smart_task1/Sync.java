package com.Smart_task1;

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
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TextView;

public class Sync extends Activity 
{

	Button yes,no;
	DatabaseHelper db;
	String tname,town,tdesp,tprio,tsdate,tddate,ttime,con,loc,ttype,tcat;
	String tname1,town1,tdesp1,tprio1,tsdate1,tddate1,ttime1;
	String lid,loc_name,loc_addr;
	private Button reset = null;

	
	private EditText uname = null;
	private EditText pwd = null;
	private Button login = null,done,cancel;
	private TextView signup1 = null;
	private TextView name = null;
	private TextView text3 = null,txt1;
	private TextView text4 = null;
	private TextView text5 = null;
	String u_id;
	String result = "";
	String temp="";
	private RadioButton t,f;
	private TextView text,text1, text2;
	int con1,loc1,ttype1,tcat1;
	InputStream is = null;
	@Override
	public void onCreate(Bundle icicle)
	{	
		db=new DatabaseHelper(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(icicle);
		setContentView(R.layout.login);
		signup1 = (TextView) findViewById(R.id.signup1);
		signup1.setMovementMethod(LinkMovementMethod.getInstance());
		signup1.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
		{ 
			signup();
		}
		});

		uname = (EditText)findViewById(R.id.uname);
		pwd = (EditText)findViewById(R.id.pwd);
		text=(TextView)findViewById(R.id.text);
		text1=(TextView)findViewById(R.id.text1);
		text2=(TextView)findViewById(R.id.text2);
		text3 = (TextView)findViewById(R.id.text3);
		text4 = (TextView)findViewById(R.id.text4);
		text5 = (TextView)findViewById(R.id.text5);
		reset = (Button)findViewById(R.id.reset);
		initControls();



	}

	protected void signup()
	{
		//call register class
		Intent newActivity = new Intent(this,Register.class);     
		startActivity(newActivity);

	}

	private void initControls()
	{

		text3.setText("");
		text4.setText("");
		text5.setText("");
		login = (Button)findViewById(R.id.login);
		login.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
		{ 

			checkValidate();

		}
		});
		reset.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
		{ 

			reset_func();

		}
		});
	}
//reset feilds
	private void reset_func()
	{
		uname.setText("");
		pwd.setText("");
	}

	//check validations
	private void checkValidate()
	{
		String result = "";
		


		if((uname.getText().toString()).equals(""))
		{
			text1.setText("Uname not entered!!");
		}
		else if((pwd.getText().toString()).equals(""))
		{
			text1.setText("Password not entered!!");
		}
		else
		{

			System.out.println("uname.."+uname.getText().toString()+"Pass"+pwd.getText().toString());
			//the data to send
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("uname",uname.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("pwd",pwd.getText().toString()));

			//http post
			try
			{//setting up connection to call php file
				
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/stlist/select_query_server.php");
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
			}
			catch(Exception e)
			{
				//text2.setText("Error in http connection");
			}
			try
			{
				//read the data send from php file
				BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
				StringBuilder sb = new StringBuilder();
				String line = null;

				while ((line = reader.readLine()) != null) 
				{
					sb.append(line + "\n");
				}
				is.close();
				result=sb.toString();
				System.out.println("reslt"+result);
			}
			catch(Exception e)
			{
				text2.setText("Error converting result");
			}
			//parse data


			try{


				JSONArray jArray = new JSONArray(result);

				for(int i=0;i<jArray.length();i++)
				{
					JSONObject json_data = jArray.getJSONObject(i);

					int u_id1 = json_data.getInt("u_id");
					u_id=""+u_id1;
					System.out.println("u_idstring"+json_data.getString("u_id"));
				}
				
				System.out.println("u_id"+u_id);
				text1.setText("Sync Successful!!");
				//name.setText("Welcome "+uname.getText().toString());
				sync();
			}
			catch(JSONException e)
			{
				text1.setText("Invalid Login!!");
			}
		}

	}

	private void sync()
	{	
		//select the type of sync
		setContentView(R.layout.sync);
		t=(RadioButton)findViewById(R.id.tserver);
		f=(RadioButton)findViewById(R.id.fserver);
		done=(Button)findViewById(R.id.done);
		cancel=(Button)findViewById(R.id.cancel);
		txt1=(TextView)findViewById(R.id.txt);
		done.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick (View v)
			{ 
				sync1();

			}

			

		});

	}
		protected void sync1()
		{
			//if sync to server selected
			if(t.isChecked()==true)
			{

				Cursor c2=db.getFlag();
				while(c2.moveToNext())
				{	
					int flag=c2.getInt(0);
					int t_id=c2.getInt(1);
					if(flag==0)
					{
						Cursor c= db.gettask();
					while(c.moveToNext())
					{	

					tname = c.getString(1);
					town =	c.getString(0);
					tdesp =	c.getString(2);
					ttype =	c.getString(3);
					tprio =	c.getString(4);
					tcat  =	c.getString(5);
					tsdate =c.getString(6);
					tddate = c.getString(7);
					ttime =	c.getString(8);
					con = c.getString(9);
					loc = c.getString(10);
					System.out.println("task are:--"+tname+town+tdesp+ttype+tprio+tcat+tsdate+tddate+ttime+con+loc);
					//send task data to php file to add it in server database
					ArrayList<NameValuePair> nv = new ArrayList<NameValuePair>();
					nv.add(new BasicNameValuePair("town",town));
					nv.add(new BasicNameValuePair("tname",tname));
					nv.add(new BasicNameValuePair("tdesp",tdesp));
					nv.add(new BasicNameValuePair("ttype",ttype));
					nv.add(new BasicNameValuePair("tprio",tprio));
					nv.add(new BasicNameValuePair("tcat",tcat));
					nv.add(new BasicNameValuePair("tsdate",tsdate));
					nv.add(new BasicNameValuePair("tddate",tddate));
					nv.add(new BasicNameValuePair("ttime",ttime));
					nv.add(new BasicNameValuePair("con",con));
					nv.add(new BasicNameValuePair("loc",loc));
					nv.add(new BasicNameValuePair("u_id",u_id));
					
					//http post
					try
					{	
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/stlist/insert.php");
						httppost.setEntity(new UrlEncodedFormEntity(nv));
						HttpResponse response = httpclient.execute(httppost);
						HttpEntity entity = response.getEntity();
						is = entity.getContent();
					}
					catch(Exception e)
					{
						//text2.setText("Error in http connection");
					}
					
				}
				db.updateflag(t_id);
					}
					

				}
				//check flag
				Cursor c3=db.getFlagl();
				while(c3.moveToNext())
				{	
					int flag=c3.getInt(0);
					int l_id=c3.getInt(1);
					if(flag==0)
					{
				Cursor c1= db.getAllloc();
				while(c1.moveToNext())
				{
					//insert location data in server database
					lid=c1.getString(0);
					loc_name=c1.getString(1);
					loc_addr=c1.getString(2);
					ArrayList<NameValuePair> nv = new ArrayList<NameValuePair>();
					nv.add(new BasicNameValuePair("lid",lid));
					nv.add(new BasicNameValuePair("loc_name",loc_name));
					nv.add(new BasicNameValuePair("loc_addr",loc_addr));
					try
				{
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/stlist/insertloc.php");
						httppost.setEntity(new UrlEncodedFormEntity(nv));
						HttpResponse response = httpclient.execute(httppost);
						HttpEntity entity = response.getEntity();
						is = entity.getContent();
					}
					catch(Exception e)
					{
						//text2.setText("Error in http connection");
					}
			
				}
				db.updateflagl(l_id);
					}
				}
			}
			//on syn from server selected
			else if(f.isChecked()==true)
			{
				try
				{	
					
				//get task data from php file	
				ArrayList<NameValuePair> nv = new ArrayList<NameValuePair>();
				nv.add(new BasicNameValuePair("u_id",u_id));
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/stlist/gettask.php");
				httppost.setEntity(new UrlEncodedFormEntity(nv));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				}
				catch(Exception e)
				{
					//text2.setText("Error in http connection");
				}
				//convert response to string
				try
				{
					BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
					StringBuilder sb = new StringBuilder();
					String line = null;

					while ((line = reader.readLine()) != null) 
					{
						sb.append(line + "\n");
					}
					is.close();
					result=sb.toString();
				}
				catch(Exception e)
				{
					text2.setText("Error converting result");
				}
				//parse data


				try{


					JSONArray jArray = new JSONArray(result);

					for(int i=0;i<jArray.length();i++)
					{
						JSONObject json_data = jArray.getJSONObject(i);
						town1 = json_data.getString("t_owner");
						tname1 = json_data.getString("t_name");
						tdesp1 = json_data.getString("t_desp");
						ttype1=json_data.getInt("t_type");
						tprio1=json_data.getString("t_priority");
						tcat1=json_data.getInt("t_cat");
						tsdate1=json_data.getString("t_sdate");
						tddate1=json_data.getString("t_ddate");
						ttime1=json_data.getString("t_time");
						con1=json_data.getInt("con");
						loc1=json_data.getInt("loc");
						//System.out.println(town+tname+tdesp+t_type+t_priority+t_cat+t_sdate+t_ddate+t_time+con+loc);
					}
					text1.setText("Login Successful!!");
					//name.setText("Welcome "+uname.getText().toString());

				}
				catch(JSONException e)
				{
					text1.setText("Invalid Login!!");
				}
				//insert the data in database
				db.Inserttask(town1, tname1, tdesp1,ttype1,tprio1,tcat1,tsdate1,tddate1,ttime1,con1,loc1);
			}
			}
		
		
	



}	


