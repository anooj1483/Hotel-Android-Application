package com.team.cafebeside.screenMappers;

import com.team.cafebeside.R;
import com.team.cafebeside.configs.Configuration;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * @author Little Adam
 *
 */
public class HomeActivity extends Activity implements OnItemClickListener {

	static final LauncherIcon[] ICONS = {
        new LauncherIcon(R.drawable.icon_five, "Todays Menu", "icon_five.png"),
        new LauncherIcon(R.drawable.icon_four, "My Orders", "icon_four.png"),
        new LauncherIcon(R.drawable.icon_two, "My Bills", "icon_two.png"),
        new LauncherIcon(R.drawable.icon_seven, "Contact", "icon_seven.png"),
};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Configuration.IS_APP_RUNNING	=	true;

        Toast.makeText(getApplicationContext(), "Welcome To Cafe Beside.", Toast.LENGTH_SHORT).show();
        GridView gridview = (GridView) findViewById(R.id.dashboard_grid);
        gridview.setAdapter(new ImageAdapter(this));
        gridview.setOnItemClickListener(this);
        gridview.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
        }

    
    
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    	
        Toast.makeText(getApplicationContext(), "Clicked Item on position"+position, Toast.LENGTH_SHORT).show();
        switch(position){
        case 0:	Intent i1 = new Intent(this, MainMenu.class);
        		startActivity(i1);
        		break;
        case 1: Intent i2 = new Intent(this, MyOrders.class);
				startActivity(i2);	
				break;
        case 2: Intent i3 = new Intent(this, MyBills.class);
				startActivity(i3);	
				break;
        case 3: Intent i4 = new Intent(this, TodaysMenu.class);
				startActivity(i4);	
				break;		
        }
    }
    
    static class LauncherIcon {
        final String text;
        final int imgId;
        
 
        public LauncherIcon(int imgId, String text, String map) {
            super();
            this.imgId = imgId;
            this.text = text;
         
        }
 
    }
    
    
    static class ImageAdapter extends BaseAdapter {
        private Context mContext;
 
        public ImageAdapter(Context c) {
            mContext = c;
        }
        @Override
        public int getCount() {
            return ICONS.length;
        }

        @Override
        public LauncherIcon getItem(int position) {
            return null;
        }
 
        public long getItemId(int position) {
            return 0;
        }
 
        static class ViewHolder {
            public ImageView icon;
            public TextView text;
        }
 
        // Create a new ImageView for each item referenced by the Adapter
        @SuppressLint("InflateParams") @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ViewHolder holder;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) mContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
 
                v = vi.inflate(R.layout.dashboard_icon, null);
                holder = new ViewHolder();
                holder.text = (TextView) v.findViewById(R.id.dashboard_icon_text);
                holder.icon = (ImageView) v.findViewById(R.id.dashboard_icon_img);
                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }
            	
            holder.icon.setImageResource(ICONS[position].imgId);
            holder.text.setText(ICONS[position].text);
 
            return v;
        }

    }
    
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	Configuration.IS_APP_RUNNING	=	true;
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	Configuration.IS_APP_RUNNING	=	false;
    }
    
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
