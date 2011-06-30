package com.Smart_task1;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;



public class Smart_task1 extends Activity 
{	
	
	
	 private Button open;
	
	 @Override
	 public void onCreate(Bundle icicle)
	 {
		 super.onCreate(icicle);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);  
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 setContentView(R.layout.main);
		 open=(Button)findViewById(R.id.open);
		
		 open.setOnClickListener(new Button.OnClickListener() 
			{
				 public void onClick (View v)
				{ 
				   start();
					
				}

			
			});
		 
	 
	 
	
	
	 }
	 /*
	  type :func
	  name:start
	  param:
      return type:
      date:29-06-2011
	  purpose:to start new activity
	  */
	 protected void start() {
		 Intent newActivity = new Intent(this,open.class);     
         startActivity(newActivity);	
	}
	
	 }
	
	

	
	
	
	
	
	

  