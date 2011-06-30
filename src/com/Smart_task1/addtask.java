package com.Smart_task1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

public class addtask extends Activity {
	private Button todo,tocall,togo,clist;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.add1);
		todo=(Button)findViewById(R.id.todo);
		tocall=(Button)findViewById(R.id.tocall);
		togo=(Button)findViewById(R.id.togo);
		clist=(Button)findViewById(R.id.clist);
		todo.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick (View v)
			{ 
				dotask();

			}


		});
		tocall.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick (View v)
			{ 
				calltask();

			}


		});
		togo.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick (View v)
			{ 
				gotask();

			}            


		});
		clist.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick (View v)
			{ 
				cltask();

			}


		});

	}
	/*
  	type :func
  	name:cltask
  	param:none
	return type:void
	date:29-06-2011
  	purpose:to start new activity for creating call list
  */
	protected void cltask()
	{

		Intent i2 = new Intent(this,CallList.class);     
		startActivity(i2);

	}

	/*
  	type :func
  	name:gotask
  	param:none
	return type:void
	date:29-06-2011
  	purpose:to start new activity for creating togo type of tasks
  */
	protected void gotask() {
		Intent i2 = new Intent(this,Gotask.class);     
		startActivity(i2);
	}

	/*
  	type :func
  	name:calltask
  	param:none
	return type:void
	date:29-06-2011
  	purpose:to start new activity for creating calltask
  */
	protected void calltask() {
		Intent i2 = new Intent(this,Calltask.class);     
		startActivity(i2);

	}

	protected void dotask() {
		Intent i2 = new Intent(this,Dtask.class);     
		startActivity(i2);
	}
}