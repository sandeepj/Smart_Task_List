package com.Smart_task1;
import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.Smart_task1.Gotask.MyLocationListener;
import com.Smart_task1.Gotask.MyLocationOverlay;
import com.Smart_task1.Gotask.ProximityIntentReceiver;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Contacts.People;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;


public class Gedit extends  MapActivity implements OnItemClickListener
{private TextView mDateDisplay,mTimeDisplay,t1,t2,tv;;
private int mHour;
private int mMinute,cat;
private RadioButton rb1,rb2,rb3;
private Button mPickDate,mPickTime,ddone,dcancel,pcont,cmap,back;
static final int TIME_DIALOG_ID = 0;
private int mYear;
private int mMonth;
private int mDay;
int type,pr1,cid=0,lid,id;
private Geocoder gc;
private double lat;
private double lon;
private List<Address> foundAdresses;
static final int DATE_DIALOG_ID = 1;
private MapView myMap; 
private static final long MINIMUM_DISTANCECHANGE_FOR_UPDATE = 1; // in Meters
private static final long MINIMUM_TIME_BETWEEN_UPDATE = 1000; // in Milliseconds
private static final long POINT_RADIUS = 3000; // in Meters
private static final long PROX_ALERT_EXPIRATION = -1; 
private static final String POINT_LATITUDE_KEY = "POINT_LATITUDE_KEY";
private static final String POINT_LONGITUDE_KEY = "POINT_LONGITUDE_KEY";
private static final String PROX_ALERT_INTENT = "com.javacodegeeks.android.lbs.ProximityAlert";

private LocationManager locationManager;
EditText ename,edesp,elname,eladdr;
LayoutParams params;
LinearLayout mainLayout;
String date,time,pr,addressInput,tm;
Spinner s ;
String sdate;
String tname,tdesp,ans,tcname,tlname,tladdr;
GeoPoint p;
DatabaseHelper db;
ArrayAdapter adapter,adapter2,adapter3,adapter4;
protected static RadioGroup rg;

private String array_spinner[];
@Override
public void onCreate(Bundle savedInstanceState)
{
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);  
	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	setContentView(R.layout.togo);
	rb1=(RadioButton)findViewById(R.id.rhigh);
	rb2=(RadioButton)findViewById(R.id.rmed);
	rb3=(RadioButton)findViewById(R.id.rlow);
	mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
	mPickDate = (Button) findViewById(R.id.pickDate);
	ddone=(Button)findViewById(R.id.gdone);
	ename=(EditText)findViewById(R.id.etname);
	edesp=(EditText)findViewById(R.id.etdescp);
	elname=(EditText)findViewById(R.id.elname);
	eladdr=(EditText)findViewById(R.id.eladdr);
	cmap=(Button)findViewById(R.id.cmap);
	dcancel=(Button)findViewById(R.id.gcancel);
	mTimeDisplay = (TextView) findViewById(R.id.timeDisplay);
	mPickTime = (Button) findViewById(R.id.pickTime);
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
		System.out.println("cid....."+cid);
		lid=c.getInt(7);
		System.out.println("lid....."+lid);
		System.out.println("priority...."+c.getString(3));
		mDateDisplay.setText(c.getString(4));
		mTimeDisplay.setText(c.getString(5));

	}
	Cursor cur=db.getcdetails(cid);
	Cursor c1=db.getldetails(lid);
	while(c1.moveToNext())
	{
		elname.setText(c1.getString(0));
		eladdr.setText(c1.getString(1));

	}
	rb1=(RadioButton)findViewById(R.id.rhigh);
	rb2=(RadioButton)findViewById(R.id.rmed);
	rb3=(RadioButton)findViewById(R.id.rlow);
	mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
	mPickDate = (Button) findViewById(R.id.pickDate);
	ddone=(Button)findViewById(R.id.gdone);
	ename=(EditText)findViewById(R.id.etname);
	edesp=(EditText)findViewById(R.id.etdescp);
	elname=(EditText)findViewById(R.id.elname);
	eladdr=(EditText)findViewById(R.id.eladdr);
	cmap=(Button)findViewById(R.id.cmap);
	dcancel=(Button)findViewById(R.id.gcancel);
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

	// add a click listener to the button
	mPickDate.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			showDialog(DATE_DIALOG_ID);
		}
	});
	// get the current date
	final Calendar c11 = Calendar.getInstance();
	mYear = c11.get(Calendar.YEAR);
	mMonth = c11.get(Calendar.MONTH);
	mDay = c11.get(Calendar.DAY_OF_MONTH);
	updateDisplay();
	// capture our View elements
	mPickTime.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			showDialog(TIME_DIALOG_ID);
		}
	});

	// get the current time
	final Calendar c111 = Calendar.getInstance();
	mHour = c111.get(Calendar.HOUR_OF_DAY);
	mMinute = c111.get(Calendar.MINUTE);
	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	// display the current date
	updateDisplay1();
	s= (Spinner) findViewById(R.id.category);
	adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, array_spinner);
	s.setAdapter(adapter);
	locationManager.requestLocationUpdates(
			LocationManager.GPS_PROVIDER, 
			MINIMUM_TIME_BETWEEN_UPDATE, 
			MINIMUM_DISTANCECHANGE_FOR_UPDATE,
			new MyLocationListener()
	);
	/*cmap.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				tname=ename.getText().toString();
				tdesp=edesp.getText().toString();
				tlname=elname.getText().toString();
				tladdr=eladdr.getText().toString();
				addressInput=tlname+" "+tladdr;
				System.out.println("address:-"+addressInput);

				date=(+ (mMonth + 1) + "-" + mDay + "-" + mYear);
				time=(+ (mHour)+ ":" + mMinute);
				cat = s.getSelectedItemPosition();
				try
				{
					foundAdresses = gc.getFromLocationName(addressInput, 5);
					showAdressResults.sendEmptyMessage(0);
					System.out.println("faddress:-"+foundAdresses);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (foundAdresses.size() == 0)
				{ // if no address found,
					// display an error
					Dialog locationError = new AlertDialog.Builder(
							Gotask.this).setIcon(0).setTitle(
							"Error").setPositiveButton(R.string.ok, null)
							.setMessage(
							"Sorry, your address doesn't exist.")
							.create();
					locationError.show();

				} else 
				{ // else display address on map
					for (int i = 0; i < foundAdresses.size(); ++i)
					{

						Address x = foundAdresses.get(i);
						lat =  (x.getLatitude() *100);
						lon = (float) x.getLongitude();
						System.out.println("lat:-"+lat+" lang:-"+lon);


					}
					navigateToLocation((lat * 1000000), (lon * 1000000),myMap);
				}


			}
		});*/
	ddone.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			System.out.println("done:---hjhjlat:-"+lat+" lang:-"+lon);
			tname=ename.getText().toString();
			tdesp=edesp.getText().toString();
			tlname=elname.getText().toString();
			tladdr=eladdr.getText().toString();
			addressInput=tlname+" "+tladdr;
			System.out.println("affress:-"+addressInput);
			date=(+ (mYear) + "-" +(mMonth+1)+ "-" +mDay);
			time=(String) mTimeDisplay.getText();
			cat = s.getSelectedItemPosition();
			type=2;
			//setting priority
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
			updateloc(tlname,tladdr);
			/*Cursor c=db.getlocid(tlname, tladdr);
				while(c.moveToNext())
				{
				 lid=c.getInt(0);
					System.out.println("lid...:-"+lid);

				}*/

			//setContentView(R.layout.view);
			//tv = (TextView) findViewById(R.id.tv2);
			// System.out.Println(tv.setText(tname+","+tdesp+","+type+","+pr+","+cat+","+sdate+","+date+","+time));
			System.out.println(tname+","+tdesp+","+type+","+ans+","+cat+","+sdate+","+date+","+time);
			//db.Inserttask(tname, tdesp,type, ans, cat, sdate, date, time);
			updateTask();
			try
			{
				foundAdresses = gc.getFromLocationName(addressInput, 5);
				System.out.println("faddress:-"+foundAdresses);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (foundAdresses.size() == 0)
			{ // if no address found,
				// display an error
				Dialog locationError = new AlertDialog.Builder(
						Gedit.this).setIcon(0).setTitle(
								"Error").setPositiveButton(R.string.ok, null)
								.setMessage(
								"Sorry, your address doesn't exist.")
								.create();
				locationError.show();

			} else 
			{ 
				for (int i = 0; i < foundAdresses.size(); ++i)
				{

					Address x = foundAdresses.get(i);
					lat = (float) x.getLatitude();
					lon = (float) x.getLongitude();
					System.out.println("done:---lat:-"+lat+" lang:-"+lon);


				}

			}

			saveProximityAlertPoint();
			view();


		}

	});



}
protected void view() {
	setContentView(R.layout.task2);

}
private Handler showAdressResults = new Handler() {
	@Override
//shows the map for the address entered on map view
	public void handleMessage(Message msg) {
		//pd.dismiss();
		MyLocationOverlay myLocationOverlay = new MyLocationOverlay();
		List<Overlay> list = myMap.getOverlays();
		list.add(myLocationOverlay);
		myMap.setSatellite(false);
		myMap.setBuiltInZoomControls(true);
		myMap.displayZoomControls(true);
		if (foundAdresses.size() == 0) { // if no address found,
			// display an error
			Dialog locationError = new AlertDialog.Builder(
					Gedit.this).setIcon(0).setTitle(
							"Error").setPositiveButton(R.string.ok, null)
							.setMessage(
							"Sorry, your address doesn't exist.")
							.create();
			locationError.show();

		} else { // else display address on map
			for (int i = 0; i < foundAdresses.size(); ++i) {
				// Save results as Longitude and Latitude
				// @todo: if more than one result, then show a
				// select-list
				Address x = foundAdresses.get(i);
				lat = (float) x.getLatitude();
				lon = (float) x.getLongitude();
				System.out.println("lat:-"+lat+" lang:-"+lon);

			}

			navigateToLocation((lat * 1000000), (lon * 1000000),myMap); // display the found address
		}

	}
};	
protected class MyLocationOverlay extends com.google.android.maps.Overlay
{
	//to display marker

	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
		Paint paint = new Paint();

		super.draw(canvas, mapView, shadow);
		// Converts lat/lng-Point to OUR coordinates on the screen.
		Point myScreenCoords = new Point();
		mapView.getProjection().toPixels(p,myScreenCoords);

		paint.setStrokeWidth(1);
		paint.setARGB(255, 255, 255, 255);
		paint.setStyle(Paint.Style.STROKE);
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.marker);
		canvas.drawBitmap(bmp, myScreenCoords.x, myScreenCoords.y, paint);
		canvas.drawText("I am here...", myScreenCoords.x, myScreenCoords.y, paint);
		return true;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event, MapView mapView) 
	{   
		//---when user lifts his finger---
		if (event.getAction() == 1) {                
			p = mapView.getProjection().fromPixels(
					(int) event.getX(),
					(int) event.getY());

			Geocoder geoCoder = new Geocoder(
					getBaseContext(), Locale.getDefault());
			try {
				List<Address> addresses = geoCoder.getFromLocation(
						p.getLatitudeE6()  / 1E6, 
						p.getLongitudeE6() / 1E6, 1);

				String add = "";
				lat=p.getLatitudeE6()  / 1E6;
				lon=p.getLongitudeE6() / 1E6;
				System.out.println("finally lat:--"+lat+"long---"+lon);
				System.out.println("address new"+addresses);
				if (addresses.size() > 0) 
				{	
					for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); i++)
					{
						/*Address x = addresses.get(i);
							lat = (float) x.getLatitude();
							lon = (float) x.getLongitude();
							System.out.println(" finally got lat:-"+lat+" lang:-"+lon);*/
						add += addresses.get(0).getAddressLine(i) + "\n";
					}
				}

				Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
			}
			catch (IOException e) {                
				e.printStackTrace();
			}   
			return true;
		}
		else                
			return false;
	}        

}

public boolean onKeyDown(int keyCode, KeyEvent event)
{
	if (keyCode == KeyEvent.KEYCODE_I) {
		myMap.getController().setZoom(myMap.getZoomLevel() + 1);
		return true;
	} else if (keyCode == KeyEvent.KEYCODE_O) {
		myMap.getController().setZoom(myMap.getZoomLevel() - 1);
		return true;
	} else if (keyCode == KeyEvent.KEYCODE_S) {
		myMap.setSatellite(true);
		return true;
	} else if (keyCode == KeyEvent.KEYCODE_M) {
		myMap.setSatellite(false);
		return true;
	}
	return false;
}

//add proximity alert for the selected location on map
private void saveProximityAlertPoint() {
	//navigateToLocation((lat * 1000000), (lon * 1000000),myMap);
	addProximityAlert(lat,lon);
}

//to move the map
public void navigateToLocation(double latitude, double longitude, MapView mv) {
	p = new GeoPoint((int) latitude, (int) longitude); // new
	// GeoPoint
	mv.displayZoomControls(true); // display Zoom (seems that it doesn't
	// work yet)
	MapController mc = mv.getController();
	mc.animateTo(p); // move map to the given point
	int zoomlevel = mv.getMaxZoomLevel(); // detect maximum zoom level
	mc.setZoom(zoomlevel - 1);
	mv.setSatellite(true); 
}
private void addProximityAlert(double latitude, double longitude) {
	System.out.println("alert lat:"+latitude+" alert long:--"+longitude);
	Intent intent = new Intent(PROX_ALERT_INTENT);
	PendingIntent proximityIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

	locationManager.addProximityAlert(
			latitude, // the latitude of the central point of the alert region
			longitude, // the longitude of the central point of the alert region
			POINT_RADIUS, // the radius of the central point of the alert region, in meters
			PROX_ALERT_EXPIRATION, // time for this proximity alert, in milliseconds, or -1 to indicate no expiration 
			proximityIntent // will be used to generate an Intent to fire when entry to or exit from the alert region is detected
	);

	IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT);  
	registerReceiver(new ProximityIntentReceiver(), filter);

}


//save the coordinates for geting an proximity alert
private void saveCoordinatesInPreferences(float lat2, float lon2)
{
	SharedPreferences prefs = this.getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);
	SharedPreferences.Editor prefsEditor = prefs.edit();
	prefsEditor.putFloat(POINT_LATITUDE_KEY, lat2);
	prefsEditor.putFloat(POINT_LONGITUDE_KEY, lon2);
	prefsEditor.commit();

}


protected void updateloc(String tlname2, String tladdr2)
{
	db.updatelocn(lid,tlname2,tladdr2);
}


//update task
protected void updateTask() 
{
	db.updateTask(id,tname, tdesp,type, ans, cat, sdate, date, time,cid,lid);
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

private static String pad(int c) 
{
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
	}
};

@Override
protected boolean isRouteDisplayed() {
	// TODO Auto-generated method stub
	return false;
}
//get the saved location
private Location retrievelocationFromPreferences() 
{
	Location location = new Location("POINT_LOCATION");
	location.setLatitude(lat);
	location.setLongitude(lon);
	System.out.println("location::----"+location);
	return location;
	/*if (location==null) {
			Toast.makeText(this, "No last known location. Aborting...", Toast.LENGTH_LONG).show();
			return;
		}*/

}

public class MyLocationListener implements LocationListener 
{
	public void onLocationChanged(Location location) 
	{
		Location pointLocation = retrievelocationFromPreferences();
		System.out.println(" pointlocation entered:---"+pointLocation+" location:--"+location);
		float distance = location.distanceTo(pointLocation);
		/*Toast.makeText(Gotask.this, 
					"Distance from Point:"+distance, Toast.LENGTH_LONG).show();*/
		System.out.println("distance:---"+distance);
	}
	public void onStatusChanged(String s, int i, Bundle b) {			
	}
	public void onProviderDisabled(String s) {
	}
	public void onProviderEnabled(String s) {			
	}
}

//setting notification for proximity alert
public class ProximityIntentReceiver extends BroadcastReceiver {

	private static final int NOTIFICATION_ID = 1000;

	@Override
	public void onReceive(Context context, Intent intent) {

		String key = LocationManager.KEY_PROXIMITY_ENTERING;

		Boolean entering = intent.getBooleanExtra(key, false);

		if (entering) {
			Log.d(getClass().getSimpleName(), "entering");
		}
		else {
			Log.d(getClass().getSimpleName(), "exiting");
		}

		NotificationManager notificationManager = 
			(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, null, 0);		

		Notification notification = createNotification();
		notification.setLatestEventInfo(context, "Proximity Alert!", "You are near "+tlname, pendingIntent);
		notificationManager.notify(NOTIFICATION_ID, notification);

	}

	private Notification createNotification() {
		Notification notification = new Notification();
		notification.icon = R.drawable.ic_menu_notifications;
		notification.when = System.currentTimeMillis();
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.ledARGB = Color.WHITE;
		notification.ledOnMS = 1500;
		notification.ledOffMS = 1500;

		return notification;
	}
}

public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	// TODO Auto-generated method stub
	
}



}

