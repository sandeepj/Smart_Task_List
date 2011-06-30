package com.Smart_task1;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper 
{

	static final String dbName="my_dd";
	static final String taskTable="Task";
	static final String locationTable="Location";
	static final String contactTable="Contacts";
	static final String calllistTable="Calllist";
	static final String callsTable="Callls";
	static final String colid="TaskID";
	static final String colname="TaskName";
	static final String coldesp="TaskDesp";
	static final String coltype="TaskType";
	static final String coltpriority="TaskPriority";
	static final String colcat="TaskCat";
	static final String colsdate="Tasksdata";
	static final String colflag="flag";
	static final String coltasko="TaskOwner";
	static final String colddate="Taskddate";
	static final String coltime="Tasktime";
	static final String colloc="loc";
	static final String colcon="con";
	static final String collocid="loc_id";
	static final String colconid="c_id";
	static final String collocname="loc_name";
	static final String collocflag="loc_flag";
	static final String collat="loc_lat";
	static final String collong="loc_long";
	static final String collocaddr="loc_addr";
	static final String colconname="con_name";
	static final String colconnum="con_num";
	static final String callid="CallistID";
	static final String colclname="clname";
	static final String colcname="cname";
	static final String colcall="call";
	int cid;
	public DatabaseHelper(Context context) {
		super(context, dbName, null,33);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{	db.execSQL("CREATE TABLE "+calllistTable+" ("+callid+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+colclname+ " TEXT)");
		db.execSQL("CREATE TABLE "+locationTable+" ("+collocid+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+collocname+ " TEXT,"+collocaddr+ " TEXT,"+collocflag+" INTEGER)");
		db.execSQL("CREATE TABLE "+contactTable+" ("+colconid+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+colconname+ " TEXT,"+colconnum+ " TEXT)");
		db.execSQL("CREATE TABLE "+taskTable+" ("+colid +" INTEGER PRIMARY KEY AUTOINCREMENT,"+coltasko+" TEXT,"+colflag+" INTEGER, "+colname+" TEXT, "
				+coldesp+" Text,"+coltype+" Integer,"+coltpriority+" TEXT, "+colcat+" INTEGER,"+colsdate+" DATE,"+colddate+" DATE,"+colloc+" INTEGER,"+coltime+" Text,"
				+colcon+" Integer,FOREIGN KEY ("+colloc+") REFERENCES "+locationTable+" ("+collocid+"),FOREIGN KEY ("+colcon+") REFERENCES "+contactTable+" ("+colconid+"));");		

		db.execSQL("CREATE TABLE "+callsTable+" ("+ colcall+ " INTEGER,"+colcname+ " TEXT,FOREIGN KEY ("+colcall+") REFERENCES "+calllistTable+" ("+callid+"))");
		}
	private SQLiteDatabase db;
	
	
	 void Addtask()
	{
		 
		 
		 SQLiteDatabase db= this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 cv.put(colname, "wash_car");
		 cv.put(coldesp,"send car to wash center");
		 cv.put(coltype,0);
		 cv.put(coltpriority,1);
		 cv.put(colcat,0);
		 cv.put(colsdate,"2011-2-15");
		 cv.put(colddate,"2011-2-16");
		 cv.put(coltime,"5:17PM");
		 cv.put(colloc,1);
		 cv.put(colcon,1);
		 db.insert(taskTable, null, cv);
				
		
	}
	 
	 void Addcontact()
	 {
		 
	 }
	int getTaskCnt()
	 {
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cur= db.rawQuery("Select * from "+taskTable, null);
		int x= cur.getCount();
		cur.close();
		return x;
	 }
	 
	 
	 Cursor gettask_today(String sdate)
	 {
		 SQLiteDatabase db=this.getReadableDatabase();
		 Cursor cur=db.rawQuery("SELECT "+colname+" ,"+coldesp+","+colddate+" from "+taskTable+" where "+colddate+" = '"+sdate+"'",new String [] {});
		 
		 return cur;
	 }
	 

	 Cursor getAllloc()
	 {
		 SQLiteDatabase db=this.getReadableDatabase();
		 Cursor cur=db.rawQuery("SELECT * from "+locationTable,new String [] {});
		/* while(cur.moveToNext())
		 {
			 int lid=cur.getInt(0);
			 String lname=cur.getString(1);
			 String laddr=cur.getString(2);
			 System.out.print("lid=="+lid+" lname="+lname+" laddr=="+laddr);
			
		 }
		 */
		 return cur;
		 
	 }
	 
	 public void Inserttask(String own,String tname,String tdesp,int type,String pr,int cat,String sdate,String date,String time,int cid,int lid)
		{	 
			SQLiteDatabase db= this.getWritableDatabase();
			ContentValues cv=new ContentValues();
			cv.put(coltasko,"--");
			cv.put(colname,tname);
			cv.put(coldesp,tdesp);
			cv.put(coltype,type);
			cv.put(coltpriority,pr);
			cv.put(colcat,cat);
			cv.put(colsdate,sdate);
			cv.put(colddate,date);
			cv.put(coltime,time);
			cv.put(colloc,lid);
			cv.put(colcon,cid);
			cv.put(colflag,0);
			db.insert(taskTable,null,cv);



		}
	 public int updateTask(int id, String tname, String tdesp, int type, String pr, int cat, String sdate, String date, String time, int cid2, int lid)
	 {
		 SQLiteDatabase db=this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 	 cv.put(colname,tname);
			 cv.put(coldesp,tdesp);
			 cv.put(coltype,type);
			 cv.put(coltpriority,pr);
			 cv.put(colcat,cat);
			 cv.put(colsdate,sdate);
			 cv.put(colddate,date);
			 cv.put(coltime,time);
			 cv.put(colloc,lid);
			 cv.put(colcon,cid2);
		 return db.update(taskTable, cv,colid+"="+id, new String []{});
		 
	 }
	 void insertcontact(String cname,String cnum)
	 {
		 SQLiteDatabase db= this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 cv.put(colconname,cname);
		 cv.put(colconnum,cnum);
		db.insert(contactTable,null,cv);
		 
	 }
	 
	 void insertlocn(String lname,String laddr)
	 {
		 SQLiteDatabase db= this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 cv.put(collocname,lname);
		 cv.put(collocaddr,laddr);
		 cv.put(collocflag,0);
		 db.insert(locationTable,null,cv);
		
	 }
	public Cursor getconid(String cname,String cnum)
	 {
		 SQLiteDatabase db=this.getReadableDatabase();
		 Cursor cur=db.rawQuery("SELECT "+colconid+" from "+contactTable+" Where "+colconname+"='"+cname+"'"+
		 		" and "+colconnum+" ='"+cnum+"'",new String [] {});
		 
		 return cur;
	 }


	

	public Cursor getlocid(String tlname,String tladdr)
	{ 
		SQLiteDatabase db=this.getReadableDatabase();
	 Cursor cur=db.rawQuery("SELECT "+collocid+" from "+locationTable+" Where "+collocname+"='"+tlname+
			 "'and "+collocaddr+"='"+tladdr+"'",new String [] {});
	 return cur;
	 }

	public Cursor callalert(String date)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		 Cursor cur=db.rawQuery("SELECT "+colcon+" from "+taskTable+" where "+colddate
				 +" = '"+date+"'",new String [] {});
		 while(cur.moveToNext())
			{
			 cid=cur.getInt(0);
			}
		 System.out.println("ciddd"+cid);
	 Cursor c = db.rawQuery("SELECT "+colconname+" ,"+colconnum+" from "+contactTable+" where "+colconid
				 +" = "+cid+" ",new String [] {});
		
		 return c;
	}

	public Cursor get_task_home() 
	{
			SQLiteDatabase db=this.getReadableDatabase();
			 Cursor cur=db.rawQuery("SELECT "+colname+" ,"+coldesp+","+colddate+" from "+taskTable+" where "+colcat+" = 2",new String [] {});
		 return cur;
		
	
	}
	

	public Cursor get_task_work() 
	{
			SQLiteDatabase db=this.getReadableDatabase();
			 Cursor cur=db.rawQuery("SELECT "+colname+" ,"+coldesp+","+colddate+" from "+taskTable+" where "+colcat+" = 1",new String [] {});
		 return cur;
		
	
	}
	

	public Cursor get_task_personal() 
	{
			SQLiteDatabase db=this.getReadableDatabase();
			 Cursor cur=db.rawQuery("SELECT "+colname+" ,"+coldesp+","+colddate+" from "+taskTable+" where "+colcat+" = 0",new String [] {});
		 return cur;
		
	
	}

	public Cursor getDetails(int id) 
	{
		SQLiteDatabase db=this.getReadableDatabase();
		 Cursor cur=db.rawQuery("SELECT "+colname+","+coldesp+","+colcat+","+coltpriority+","+colddate+","+coltime+","+colcon+","+colloc+" from "+taskTable+" where "+colid+" ="+id,new String [] {});
	 return cur;
	
		
	}

	public void insercalllist(String listname)
	{
		SQLiteDatabase db= this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 cv.put(colclname,listname);
		 db.insert(calllistTable,null,cv);
		
	}

	public void insertcall(int id,String name) 
	{
		SQLiteDatabase db= this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 cv.put(colcall,id);
		 cv.put(colcname,name);
		 db.insert(callsTable,null,cv);
	
	}

	public Cursor getcallid(String listname) 
	{

		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur=db.rawQuery("SELECT "+callid+" from "+calllistTable+" Where "+colclname+"='"+listname+"'",new String [] {});
		return cur;
		
	}

	public Cursor getcalllist()
	{
		SQLiteDatabase db=this.getReadableDatabase();
		 Cursor cur=db.rawQuery("SELECT * from "+calllistTable,new String [] {});
		 return cur;
	}

	public Cursor getcallnamelist(int callid2)
	{
	SQLiteDatabase db=this.getReadableDatabase();
	 Cursor cur=db.rawQuery("SELECT "+colcname+" from "+callsTable+" Where "+colcall+"="+callid2,new String [] {});
	 return cur;


	}

	public Cursor gettaskid(String ans)
	{

		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur=db.rawQuery("SELECT "+colid+","+coltype+" from "+taskTable+" where "+colname+"='"+ans+"'",new String [] {});
		return cur;
	}

	public void deletetask(int id)
	{
		SQLiteDatabase db= this.getWritableDatabase();
		db.delete(taskTable,colid+"="+id, new String []{} );

	}

	public Cursor getcdetails(int cid2)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur=db.rawQuery("SELECT "+colconname+","+colconnum+" from "+contactTable+" Where "+colconid+"="+cid2,new String [] {});
		return cur;

		
	}

	public Cursor getldetails(int lid) 
	{
	
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur=db.rawQuery("SELECT "+collocname+","+collocaddr+" from "+locationTable+" Where "+collocid+"="+lid,new String [] {});
		return cur;

	}

	public int updatecontact(int cid2, String tcname2, String tcnum2) 
	{
		SQLiteDatabase db= this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 cv.put(colconname,tcname2);
		 cv.put(colconnum,tcnum2);
		 return db.update(contactTable, cv,colconid+"="+cid2, new String []{});
	}

	public int updatelocn(int lid, String tlname2, String tladdr2) 
	{
		 SQLiteDatabase db= this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 cv.put(collocname,tlname2);
		 cv.put(collocaddr,tladdr2);
		 return db.update(locationTable, cv,collocid+"="+lid, new String []{});
		
	}

	public Cursor gettask_upcoming(String sdate)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur=db.rawQuery("SELECT "+colname+" ,"+coldesp+","+colddate+" from "+taskTable+" where "+colddate+" > '"+sdate+"'",new String [] {});
		return cur;
	}

	public Cursor gettask_overdue(String sdate)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur=db.rawQuery("SELECT "+colname+" ,"+coldesp+","+colddate+" from "+taskTable+" where "+colddate+" < '"+sdate+"'",new String [] {});
		return cur;
	}
	public Cursor gettask()
	{
		//String tname,town,tdesp,tprio,tsdate,tddate,ttime,con,loc,ttype,tcat;
		SQLiteDatabase db=this.getReadableDatabase();
		 Cursor c=db.rawQuery("SELECT "+coltasko+","+colname+","+coldesp+","+coltype+","+coltpriority+","+colcat+","+colsdate+","+colddate+","+coltime+","+colcon+","+colloc+" from "+taskTable,new String [] {});
		 return c;
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		
		
		/*db.execSQL("DROP TABLE IF EXISTS "+taskTable);
		db.execSQL("DROP TABLE IF EXISTS "+locationTable);
		db.execSQL("DROP TABLE IF EXISTS "+contactTable);
		
		onCreate(db);*/
	}  

	
		public Cursor getFlag()
		{
			SQLiteDatabase db=this.getReadableDatabase();
			 Cursor c=db.rawQuery("SELECT "+colflag+","+colid+" from "+taskTable,new String [] {});
			 return c;
		}

		public int updateflag(int tId) {
			SQLiteDatabase db= this.getWritableDatabase();
			 ContentValues cv=new ContentValues();
			 cv.put(colflag,"1");
			 return db.update(taskTable, cv,colid+"="+tId, new String []{});
			
		}

		public Cursor getFlagl() {
			SQLiteDatabase db=this.getReadableDatabase();
			 Cursor c=db.rawQuery("SELECT "+collocflag+","+collocid+" from "+locationTable,new String [] {});
			 
			 return c;
		}

		public int updateflagl(int lId)
		{
			SQLiteDatabase db= this.getWritableDatabase();
			 ContentValues cv=new ContentValues();
			 cv.put(collocflag,"1");
			 return db.update(locationTable, cv,collocid+"="+lId, new String []{});
			
		}
	}

