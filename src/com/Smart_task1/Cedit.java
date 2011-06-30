package com.Smart_task1;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.text.Spannable;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;


public class Cedit extends Activity  implements OnItemClickListener
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
	String date,time,pr;
	Spinner s ;
	private int mYear;
	private int mMonth;
	private int mDay;
	
	LinearLayout layout;
	EditText ename,edesp,ecname,ecnum,esrch;
	LayoutParams params;
	LinearLayout mainLayout;
	int type,pr1,cid,lid=0,id;
	String sdate,srchname;;
	ListView list;
	String tname,tdesp,ans,tcname,cname,cnum,scname,snum,tcnum,tm;
	static final int DATE_DIALOG_ID = 1;
	protected static RadioGroup rg;

	private String array_spinner[];
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tocall);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mTimeDisplay = (TextView) findViewById(R.id.timeDisplay);
		mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
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
		list.setVisibility(View.GONE);
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
			cid=c.getInt(6);
			System.out.println("cid.....edit"+cid);
			// String pr1=c.getString(3);
			System.out.println("priority...."+c.getString(3));
			mDateDisplay.setText(c.getString(4));
			mTimeDisplay.setText(c.getString(5));
		}
		Cursor cur=db.getcdetails(cid);
		while(cur.moveToNext())
		{
			ecname.setText(cur.getString(0));
			int num=cur.getInt(1);
			System.out.println("number..edit"+num);
			ecnum.setText(""+num);

		}

		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
		mPickDate = (Button) findViewById(R.id.pickDate);
		ddone=(Button)findViewById(R.id.done);
		dcancel=(Button)findViewById(R.id.cancel);
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

		search.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{	list.setVisibility(View.VISIBLE);
			showList();
			}
		});
		ddone.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				tname=ename.getText().toString();
				tdesp=edesp.getText().toString();
				tcname=ecname.getText().toString();
				tcnum=ecnum.getText().toString();
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
				updateContact(tcname, tcnum);
				System.out.println(tname+","+tdesp+","+type+","+ans+","+cat+","+sdate+","+date+","+time);
				updateTask(id);



			}

		});



	}
	protected void showList() 
	{		
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
			// tv1.setText(results);
		}
		//else tv2.setText("Enter a name to search for!");
		//startActivityForResult(new Intent(Intent.ACTION_PICK, People.CONTENT_URI),0);
	}
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
			public void onClick(DialogInterface arg0, int arg1) {

				Intent intent = new Intent(Intent.ACTION_CALL);

				intent.setData(Uri.parse("tel:" + cnum));
				startActivity(intent);



				//Toast.makeText(getApplicationContext(), "'OK' button clicked", Toast.LENGTH_LONG).show();
			}
		});
		alertbox.show();
	}


//update contact
	protected void updateContact(String tcname2, String tcnum2)
	{
		db.updatecontact(cid,tcname2, tcnum2);

	}

	protected void show() {
		/*Intent i = new Intent(this,Contacts.class);     
        startActivity(i);
		 */

	}
	//save updated task
	protected void updateTask(int id) 
	{
		db.updateTask(id,tname, tdesp,type, ans, cat, sdate, date, time,cid,lid);
		Toast t=Toast.makeText(getBaseContext(),"Task Updated",Toast.LENGTH_LONG);
		t.show();
	}

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
				.append(mMonth + 1).append("-")
				.append(mDay).append("-")
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
		}
	};

	public void onItemClick(AdapterView<?> a, View v, int position, long id)
	{
		System.out.println("Position..."+position);
		tcname= (String) a.getItemAtPosition(position);
		//db.insertcall(callid,name);
		System.out.println("Position..."+position+" name===="+tcname);
		Uri lkup = Uri.withAppendedPath(People.CONTENT_FILTER_URI,ans);
		Cursor idCursor = getContentResolver().query(lkup, null, null, null, null);
		while (idCursor.moveToNext())
		{
			tcnum =  idCursor.getString(idCursor.getColumnIndex(People.NUMBER));
			System.out.println("number...."+tcnum);
		}
		idCursor.close();


	}


}

