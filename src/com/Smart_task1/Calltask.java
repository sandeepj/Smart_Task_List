package com.Smart_task1;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;


public class Calltask extends Activity implements OnItemClickListener
{
	DatabaseHelper db;
	ArrayAdapter adapter,adapter2,adapter3,adapter4;
	private ArrayList<String> res = new ArrayList<String>();
	private TextView mDateDisplay,mTimeDisplay,t1,t2,tv;;
	private int mHour;
	private int mMinute,cat;
	private RadioButton rb1,rb2,rb3;
	private Button mPickDate,mPickTime,ddone,dcancel,pcont,call,ok,search;
	static final int TIME_DIALOG_ID = 0;
	String date,time,pr,tm;
	Spinner s ;
	private int mYear;
	private int mMonth;
	private int mDay;
	//PopupWindow popupWindow,pw1;
	LinearLayout layout;
	EditText ename,edesp,ecname,ecnum,esrch;
	LayoutParams params;
	LinearLayout mainLayout;
	int type,pr1,cid,lid=0;
	String sdate,srchname;;
	ListView list;
	String tname,tdesp,ans,tcname,cname,cnum,scname,snum,tcnum;
	static final int DATE_DIALOG_ID = 1;
	protected static RadioGroup rg;

	private String array_spinner[];

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.tocall);
		db=new DatabaseHelper(this);
		rg = (RadioGroup) findViewById(R.id.rg);
		rb1=(RadioButton)findViewById(R.id.rhigh);
		rb2=(RadioButton)findViewById(R.id.rmed);
		rb3=(RadioButton)findViewById(R.id.rlow);
		search=(Button)findViewById(R.id.search);
		ename=(EditText)findViewById(R.id.etname);
		edesp=(EditText)findViewById(R.id.etdescp);
		ecname=(EditText)findViewById(R.id.ecname);
		ecnum=(EditText)findViewById(R.id.ecnumb);
		esrch=(EditText)findViewById(R.id.esrch);
		list=(ListView)findViewById(R.id.list);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		list.setVisibility(View.GONE);
		mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
		mPickDate = (Button) findViewById(R.id.pickDate);
		ddone=(Button)findViewById(R.id.cdone);
		dcancel=(Button)findViewById(R.id.ccancel);
		mTimeDisplay = (TextView) findViewById(R.id.timeDisplay);
		mPickTime = (Button) findViewById(R.id.pickTime);
		array_spinner=new String[3];
		array_spinner[0]="Personal";
		array_spinner[1]="Work";
		array_spinner[2]="Home";
		Calendar cal = new GregorianCalendar();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		sdate=(+ year + "-" + (month+1)+ "-" + day);	
		mPickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		updateDisplay();

		// capture our View elements


		// add a click listener to the button
		mPickTime.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
			}
		});

		// get the current time
		final Calendar c1 = Calendar.getInstance();
		mHour = c1.get(Calendar.HOUR_OF_DAY);
		mMinute = c1.get(Calendar.MINUTE);


		// display the current date
		updateDisplay1();
		final LayoutInflater inflater = (LayoutInflater)
		this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		s= (Spinner) findViewById(R.id.category);
		adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, array_spinner);
		s.setAdapter(adapter);

		search.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{	
				list.setVisibility(View.VISIBLE);
				showList();
			}
		});

		ddone.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) {

				tname=ename.getText().toString();
				tdesp=edesp.getText().toString();
				if((tcname==null) &&(tcnum==null))
				{
					tcname=ecname.getText().toString();
					tcnum=ecnum.getText().toString();

				}

				date=(+ (mYear) + "-" +(mMonth+1)+ "-" +mDay);
				time=(String) mTimeDisplay.getText();
				cat = s.getSelectedItemPosition();
				type=1;
				//radio button
				if(rb1.isChecked()==true)
				{
					ans="High";
					System.out.println("ans..."+ans);
					
				}
				if(rb2.isChecked()==true)
				{
					ans="Med";
					
				}
				if(rb3.isChecked()==true)
				{
					ans="Low";
					
				}
				insertContact(tcname, tcnum);
				Cursor c=db.getconid(tcname, tcnum);
				while(c.moveToNext())
				{
					cid=c.getInt(0);
				}


				System.out.println(tname+","+tdesp+","+type+","+ans+","+cat+","+sdate+","+date+","+time);
				insertTask();
				check(date);
				Intent alarmIntent = new Intent(Calltask.this, MyAlarmService.class);
				Bundle bun=new Bundle();
				bun.putString("task",tname);
				alarmIntent.putExtras(bun);
				PendingIntent pendingAlarmIntent = PendingIntent.getBroadcast(Calltask.this, 0,alarmIntent, 0);
				Calendar AlarmCal = Calendar.getInstance();
				AlarmCal.setTimeInMillis(System.currentTimeMillis());
				AlarmCal.set(Calendar.HOUR_OF_DAY,mHour);  // set user selection
				AlarmCal.set(Calendar.MINUTE,mMinute);        // set user selection
				AlarmCal.set(Calendar.SECOND, 0);
				AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
				alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
						AlarmCal.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES,
						pendingAlarmIntent);
			}
			
		});



	}
	/*
  	type :func
  	name:showList
  	param:none
	return type:void
	date:29-06-2011
  	purpose:to show list of contacts from phonebook
  */
	protected void showList() 
	{	if(srchname==null)
	{
		Toast t=Toast.makeText(getBaseContext(),"Please enter name to search",Toast.LENGTH_LONG);
		t.show();

	}
	srchname = esrch.getText().toString();
	System.out.println("sch for"+srchname);
	if (srchname != null && srchname.length()>0)
	{
		Uri lkup = Uri.withAppendedPath(People.CONTENT_FILTER_URI,srchname);        
		String results = "";
		Cursor idCursor = getContentResolver().query(lkup, null, null, null, null);
		while (idCursor.moveToNext())
		{
			long lid = idCursor.getLong(idCursor.getColumnIndex(People._ID));
			//String key = idCursor.getString(idCursor.getColumnIndex(People.LOOKUP_KEY));
			String name = idCursor.getString(idCursor.getColumnIndex(People.DISPLAY_NAME));
			String num =  idCursor.getString(idCursor.getColumnIndex(People.NUMBER));
			//Log.d(LOG_TAG, "search: "+lid+"name: "+name+ "nummm..."+num); 
			results+=("\n"+"Name:"+name+" ID:"+lid);
			res.add(name);
			list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,res));
			list.setTextFilterEnabled(true);
			list.setOnItemClickListener(this);
		}
		idCursor.close();
		
	}
	
	}
	/*
  	type :func
  	name:check
  	param:date
	return type:void
	date:29-06-2011
  	purpose:shows alert box with an call icon to initiate call  */
	private void check(String date) 
	{
		Cursor c =db.callalert(date);
		while(c.moveToNext())
		{
			cname=c.getString(0);
			cnum=c.getString(1);
			System.out.println("cnmae...:-"+cname+"cnum...."+cnum);
		}

		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		alertbox.setTitle("Call "+cname);
		alertbox.setNeutralButton("ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1)
			{
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:" + cnum));
				startActivity(intent);
			}
		});
		alertbox.show();
	}


	/*
  	type :func
  	name:insertContact
  	param:contact name,contact num
	return type:void
	date:29-06-2011
  	purpose:to save contact in task list  */
	protected void insertContact(String tcname2, String tcnum2)
	{
		db.insertcontact(tcname2, tcnum2);

	}


	/*
  	type :func
  	name:insertTask
  	param:none
	return type:void
	date:29-06-2011
  	purpose:save task in database
  */
	protected void insertTask() 
	{	String own="self";
		db.Inserttask(own,tname, tdesp,type, ans, cat, sdate, date, time,cid,lid);
		Toast t=Toast.makeText(getBaseContext(),"addedd Successfully",Toast.LENGTH_LONG);
		t.show();
	}

	private void updateDisplay1() {
		if(mHour>=12)
		{
			mHour=(mHour-12);
			tm="PM";
		}else
		{
			tm="AM";
		}
		mTimeDisplay.setText(new StringBuilder()
		.append(pad(mHour)).append(":")
		.append(pad(mMinute)).append(tm));

	}

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);


	}
	private void updateDisplay() 
	{
		// updates the date in the TextView

		mDateDisplay.setText(
				new StringBuilder()
				// Month is 0 based so add 1
				.append(mDay).append("-")
				.append(mMonth + 1).append("-")
				.append(mYear).append(" "));

	}

	private DatePickerDialog.OnDateSetListener mDateSetListener =
		new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, 
				int monthOfYear, int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};

	protected Dialog onCreateDialog(int id)
	{
		switch (id) 
		{
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this,
					mDateSetListener,
					mYear, mMonth, mDay);

		case TIME_DIALOG_ID: return new TimePickerDialog(this,
				mTimeSetListener, mHour, mMinute, false);


		}
		return null;
	}

	private TimePickerDialog.OnTimeSetListener mTimeSetListener =
		new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			updateDisplay1();
			/*Intent alarmIntent = new Intent(Calltask.this, MyAlarmService.class);
		      //alarmIntent.putExtra("nel.example.alarms1","My message");
		      PendingIntent pendingAlarmIntent = PendingIntent.getBroadcast(Calltask.this, 0,alarmIntent, 0);
		      Calendar AlarmCal = Calendar.getInstance();
		      AlarmCal.setTimeInMillis(System.currentTimeMillis());
		      AlarmCal.set(Calendar.HOUR_OF_DAY,hourOfDay);  // set user selection
		      AlarmCal.set(Calendar.MINUTE,minute);        // set user selection
		      AlarmCal.set(Calendar.SECOND, 0);
		      AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		      alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
		      AlarmCal.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES,
		              pendingAlarmIntent);*/
		}
	};
//to get contact number of a selected contact
	public void onItemClick(AdapterView<?> a, View v, int position, long id)
	{
		System.out.println("Position..."+position);
		tcname= (String) a.getItemAtPosition(position);
		//db.insertcall(callid,name);
		System.out.println("Position..."+position+" name===="+tcname);
		Uri lkup = Uri.withAppendedPath(People.CONTENT_FILTER_URI,tcname);
		Cursor idCursor = getContentResolver().query(lkup, null, null, null, null);
		while (idCursor.moveToNext())
		{
			tcnum =  idCursor.getString(idCursor.getColumnIndex(People.NUMBER));
			//tcnum=(int)Integer.parseInt(tcnum1);
			System.out.println("number...."+tcnum);
		}
		idCursor.close();


	}



}
