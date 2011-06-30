package com.Smart_task1;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;



public class open extends Activity 
{	


	private Button sync,add_btn,today,upcomming,overdue,home,personal,work,callist;

	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.open);
		add_btn=(Button)findViewById(R.id.add_btn);
		sync=(Button)findViewById(R.id.sync);
		today=(Button)findViewById(R.id.today);
		upcomming=(Button)findViewById(R.id.upcoming);
		overdue=(Button)findViewById(R.id.overdue); 
		home=(Button)findViewById(R.id.home); 
		work=(Button)findViewById(R.id.work); 
		// personal=(Button)findViewById(R.id.personal);
		callist=(Button)findViewById(R.id.callist); 
		sync.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick (View v)
			{ 
				syncdb();

			}


		});
		add_btn.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick (View v)
			{ 
				addtask();

			}


		});


		today.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
		{ 
			today();

		}


		});


		upcomming.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
		{ 
			upcoming();

		}


		});
		overdue.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
		{ 
			overdue();

		}


		});
		home.setOnClickListener(new Button.OnClickListener() 
		{ 
			public void onClick (View v)
			{ 
				home();

			}


		});
		/*personal.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
			   personal();

			}


		});*/
		work.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
		{ 
			work();

		}


		});
		callist.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
		{ 
			callist();

		}


		});

	}
	/*
	  	type :func
	  	name:open
	  	param:none
     	return type:void
     	date:29-06-2011
	  	purpose:to start new activity that does synching
	 */
	protected void syncdb() {
		Intent newActivity = new Intent(this,Sync.class);     
		startActivity(newActivity);	
	}
	/*
	  	type :func
	  	name:upcoming
	  	param:none
  		return type:void
  		date:29-06-2011
	  	purpose:to start new activity to view upcoming tasks
	 */
	protected void upcoming() 
	{
		Intent i = new Intent(this,task_upcoming.class);     
		startActivity(i);

	}
	/*
  	type :func
  	name:overdue
  	param:none
	return type:void
	date:29-06-2011
  	purpose:to start new activity t0 view overdue tasks
	 */
	protected void overdue() 
	{
		Intent i1 = new Intent(this,task_overdue.class);     
		startActivity(i1);
	}
	/*
  	type :func
  	name:home
    param:none
	return type:void
	date:29-06-2011
  	purpose:to start new activity that view tasks under home category
	 */
	protected void home()
	{
		Intent i = new Intent(this,task_home.class);     
		startActivity(i);

	}
	/*protected void personal() {
		Intent newActivity = new Intent(this,task_per.class);     
        startActivity(newActivity);


	}*/
	/*
  	type :func
  	name:work
  	param:none
	return type:void
	date:29-06-2011
  	purpose:to start new activity that view tasks under work category
	 */
	protected void work() {
		Intent i1 = new Intent(this,task_work.class);     
		startActivity(i1);

	}
	/*
  	type :func
  	name:callist
  	param:none
	return type:void
	date:29-06-2011
  	purpose:to start new activity that shows call lists */
	protected void callist() {
		Intent i2 = new Intent(this,My_list.class);     
		startActivity(i2);

	}

	/*
  	type :func
  	name:today
  	param:none
	return type:void
	date:29-06-2011
  	purpose:to start new activity to view todays tasks
	 */

	protected void today() {
		Intent newActivity = new Intent(this,task_today.class);     
		startActivity(newActivity);

	}

	/*
	  	type :func
	  	name:addtask
	  	param:none
		return type:void
		date:29-06-2011
	  	purpose:to start new activity for addding new task
	 */

	protected void addtask() 
	{
		Intent newActivity = new Intent(this,addtask.class);     
		startActivity(newActivity);			
	}


}










