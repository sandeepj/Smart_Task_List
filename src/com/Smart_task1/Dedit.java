package com.Smart_task1;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Spannable;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;


public class Dedit extends Activity 
{
	DatabaseHelper db;
	ArrayAdapter adapter,adapter2,adapter3,adapter4;
	private TextView mDateDisplay,mTimeDisplay;
	private int mHour;
	private int mMinute,cat;
	private RadioButton rb1,rb2,rb3;
	private Button mPickDate,tocall,selected,mPickTime,ddone,dcancel,back;
	static final int TIME_DIALOG_ID = 0;
	String date,time,pr;
	Spinner s ;
	private int mYear;
	private int mMonth;
	private int mDay;
	//PopupWindow popupWindow,pw1;
	LinearLayout layout;
	TextView t1,t2,tv;
	EditText ename,edesp;
	LayoutParams params;
	LinearLayout mainLayout;
	int type,pr1,id;
	String sdate;
	String tname,tdesp,ans,tm;
	static final int DATE_DIALOG_ID = 1;
	protected static RadioGroup rg;

	private String array_spinner[];

	@Override
	public void onCreate(Bundle savedInstanceState)
	{	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);  
	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

	setContentView(R.layout.todo);
	ename=(EditText)findViewById(R.id.ename);
	edesp=(EditText)findViewById(R.id.etdescp);
	mTimeDisplay = (TextView) findViewById(R.id.timeDisplay);
	mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
	Bundle bun=getIntent().getExtras();
	id =bun.getInt("tid");
	System.out.println("deditid==="+id);
	db=new DatabaseHelper(this);
	Cursor c=db.getDetails(id);
	while(c.moveToNext())
	{
		ename.setText(c.getString(0));
		edesp.setText(c.getString(1));
		cat=c.getInt(2);
		pr= (c.getString(3));
		// String pr1=c.getString(3);
		System.out.println("priority...."+c.getString(3));
		mDateDisplay.setText(c.getString(4));
		mTimeDisplay.setText(c.getString(5));
	}

	rg = (RadioGroup) findViewById(R.id.rg);
	rb1=(RadioButton)findViewById(R.id.rhigh);
	rb2=(RadioButton)findViewById(R.id.rmed);
	rb3=(RadioButton)findViewById(R.id.rlow);

	mTimeDisplay = (TextView) findViewById(R.id.timeDisplay);
	mPickTime = (Button) findViewById(R.id.pickTime);
	array_spinner=new String[3];
	array_spinner[0]="Personal";
	array_spinner[1]="Work";
	array_spinner[2]="Home";
	mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
	mPickDate = (Button) findViewById(R.id.pickDate);
	ddone=(Button)findViewById(R.id.ddone);

	dcancel=(Button)findViewById(R.id.dcancel);
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
	final Calendar c1 = Calendar.getInstance();
	mYear = c1.get(Calendar.YEAR);
	mMonth = c1.get(Calendar.MONTH);
	mDay = c1.get(Calendar.DAY_OF_MONTH);
	updateDisplay();

	// capture our View elements


	// add a click listener to the button
	mPickTime.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			showDialog(TIME_DIALOG_ID);
		}
	});

	// get the current time
	final Calendar c11 = Calendar.getInstance();
	mHour = c11.get(Calendar.HOUR_OF_DAY);
	mMinute = c11.get(Calendar.MINUTE);


	// display the current date
	updateDisplay1();
	final LayoutInflater inflater = (LayoutInflater)
	this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	s= (Spinner) findViewById(R.id.category);
	adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, array_spinner);
	s.setAdapter(adapter);

	ddone.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			ename=(EditText)findViewById(R.id.ename);
			edesp=(EditText)findViewById(R.id.etdescp);
			tname=ename.getText().toString();
			System.out.println("tname:--"+tname);
			tdesp=edesp.getText().toString();
			date=(+ (mYear) + "-" +(mMonth+1)+ "-" +mDay);
			time=(String) mTimeDisplay.getText();
			cat = s.getSelectedItemPosition();
			type=0;
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


			System.out.println(tname+","+tdesp+","+type+","+ans+","+cat+","+sdate+","+date+","+time);
			updateTask(id);

		}

	});



	}
	/*
  	type :func
  	name:updateTask
  	param:task id
	return type:void
	date:29-06-2011
  	purpose:to update the edited task
	 */
	protected void updateTask(int id) 
	{
		int cid=0;
		int lid=0;
		db.updateTask(id,tname, tdesp,type, ans, cat, sdate, date, time,cid,lid);
		Toast t=Toast.makeText(getBaseContext(),"addedd Successfully",Toast.LENGTH_LONG);
		t.show();
	}
	//update date and time


	private void updateDisplay1() {
		if(mHour>12)
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
	// the callback received when the user "sets" the date in the dialog
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
		}
	};


}

