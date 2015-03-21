package com.team.cafebeside.screenMappers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.team.cafebeside.R;
import com.team.cafebeside.configs.ServerConnector;
import com.team.cafebeside.networkEngine.AsyncResponse;
import com.team.cafebeside.networkEngine.AsyncWorker;
import com.team.cafebeside.workers.SharedPrefSingleton;

public class FoodItem extends Activity implements AsyncResponse {
	private Button b_ordr,_decrease,_increase;
	private TextView fnm,fcat,fprice,_value;
	private EditText spInst;
	private static String f_id,f_price,unm,_stringVal,_instructions;
    private static int _counter = 1;
	public ProgressDialog progress;
	private AsyncWorker mAsyncWorker = new AsyncWorker(this);
	private final String _DB_NAME = "CafeBeside.db";
	private SQLiteDatabase db = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mAsyncWorker.delegate = this;
		setContentView(R.layout.add_cart);
		SharedPrefSingleton shpref;
		shpref = SharedPrefSingleton.getInstance();
		shpref.init(getApplicationContext());
		unm = shpref.getLoggedInUserPreference("email");
		
		db = openOrCreateDatabase(_DB_NAME,SQLiteDatabase.CREATE_IF_NECESSARY, null);

        db.setVersion(3);
		db.execSQL("CREATE TABLE IF NOT EXISTS orders(oEmail VARCHAR(30),oDate VARCHAR(12),oFoodid VARCHAR(6),oItmName VARCHAR(30),oCat VARCHAR(30),oQuantity VARCHAR(6),oFprice VARCHAR(10),oInst VARCHAR(100),sTotal VARCHAR(10),PRIMARY KEY(oFoodid))");

        
		_decrease = (Button) findViewById(R.id.minus);
        _increase = (Button) findViewById(R.id.plus);
        _value = (TextView) findViewById(R.id.quanity);
        spInst	=	(EditText) findViewById(R.id.spInstructions);       
		Intent i = getIntent();
		Bundle extras = i.getExtras();
		f_id = extras.getString("TAG_FOODID");
		final String f_name = extras.getString("TAG_FNAME");
		final String f_cat = extras.getString("TAG_FCATID");
		f_price = extras.getString("TAG_FPRICE");		
		fnm = (TextView)findViewById(R.id.lbl1);
		fnm.setText(f_name);
		fcat = (TextView)findViewById(R.id.lbl3);
		fcat.setText(f_cat);		
		fprice = (TextView)findViewById(R.id.lbl2);
		fprice.setText(f_price);			
		b_ordr = (Button)findViewById(R.id.btn_ordr); 
        _decrease.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d("src", "Decreasing value...");
                _counter--;
                _stringVal = Integer.toString(_counter);
                if(_counter<1){
                    _value.setText(1);
                }else{
                    _value.setText(_stringVal);
                }
            }
        });

        _increase.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d("src", "Increasing value...");
                _counter++;
                _stringVal = Integer.toString(_counter);
                _value.setText(_stringVal);
            }
        });	
		b_ordr.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				_instructions = spInst.getText().toString();
				int grandTotal = Integer.parseInt(f_price) * Integer.parseInt(_stringVal);
				String gtotal = String.valueOf(grandTotal);

				Calendar c = Calendar.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String formattedDate = df.format(c.getTime());				
				//Toast.makeText(getApplicationContext(),"You Order FoodId: "+ f_id +"\n Your Email: "+ unm +"\n Price: "+f_price, Toast.LENGTH_LONG).show();
				Log.d("Food Item ID : ",f_id);
				Log.d("USER EMAIL : ",unm);
				Log.d("PRICE : ",f_price);
				Log.d("Quantity:",_stringVal);
				Log.d("Instructions:",_instructions);
				Log.d("Category Name",f_cat);
				Log.d("Food Name:",f_name);
				Log.d("Grand Total Int:",gtotal);			
				Log.d("Todays Date :",formattedDate);
				
				ContentValues insertValues = new ContentValues();
				insertValues.put("oEmail",unm);
				insertValues.put("oDate",formattedDate);
				insertValues.put("oFoodid",f_id);
				insertValues.put("oItmName",f_name);
				insertValues.put("oCat",f_cat);
				insertValues.put("oQuantity",_stringVal);
				insertValues.put("oFprice",f_price);
				insertValues.put("oInst",_instructions);
				insertValues.put("sTotal",gtotal);
				db.insert("orders", null, insertValues);

					Intent checkout	=	new Intent(getApplicationContext(),MyOrders.class);
					startActivity(checkout);	
					finish();
			}
		});	
		
	}
		


	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.menu_about) {
	        Toast.makeText(getApplicationContext(), "You Clicked About Menu!", Toast.LENGTH_LONG).show();
	        Log.d("Click","Clicked Action Bar Icon");
			return true;
		}
		else if(id== R.id.logout){	
			//Toast.makeText(getApplicationContext(), "You clicked logout button", Toast.LENGTH_LONG).show();
			//mlogout();
			try{
				JSONObject mObject = new JSONObject();
				mObject.put("email", unm);
				mAsyncWorker = new AsyncWorker(getApplicationContext());
				mAsyncWorker.delegate=FoodItem.this;
				mAsyncWorker.execute(ServerConnector.LOGOUT,mObject.toString());
				}
				catch(Exception ex){
					
				}

			
			
			 
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
/*	public void mlogout(){
		try{
		SharedPrefSingleton shb;
		shb = SharedPrefSingleton.getInstance();
		shb.init(getApplicationContext());
		unm = shb.getLoggedInUserPreference("email");
		JSONObject mObject = new JSONObject();
		mObject.put("email", unm);
		mAsyncWorker = new AsyncWorker(getApplicationContext());
		mAsyncWorker.delegate=FoodItem.this;
		mAsyncWorker.execute(ServerConnector.LOGOUT,mObject.toString());
		}
		catch(Exception ex){
			
		}
	}*/



	private void showAlert(String title, String message) {
		new AlertDialog.Builder(this)
				.setTitle(title)
				.setMessage(message)
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// DO ANY CAFEBESIDE OPERATION
							}
						})

				.setIcon(android.R.drawable.ic_dialog_alert).show();
	}

	@Override
	public void processFinish(String output) {
		// TODO Auto-generated method stub
		if(!output.trim().equals("Success")){
			showAlert("Alert", "Logout Failed");
		}
		else{
			SharedPrefSingleton.getInstance().init(getApplicationContext());
			SharedPrefSingleton.getInstance().writePreference("isLoggedIn", false);
			SharedPrefSingleton.getInstance().writeSPreference("email", null);
			Intent homeIntent	=	new Intent(this,LoginPage.class);
			startActivity(homeIntent);
			finish();
		}
	}	
	
	
	
}