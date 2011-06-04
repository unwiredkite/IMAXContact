package com.unwiredkite.IMaxContact;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.unwiredkite.db.DBHelper;
import android.net.Uri;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class IMaxContact extends Activity {
	
	GridView gv_buttom_menu;
	ListView lv_userList;
	SimpleAdapter adapter;
	
	boolean listItemEditable = true;
	
	private long ��һ�ε��;
	private long �ڶ��ε��;
	private long ���ε�����;
	private Calendar ϵͳʱ��;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.main);
        
        loadUserList();
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(keyCode == KeyEvent.KEYCODE_MENU) {
			
			if(gv_buttom_menu == null) {
				loadButtomMenu();
			}
			
			if(gv_buttom_menu.getVisibility() == View.GONE) {
				gv_buttom_menu.setVisibility(View.VISIBLE);
			} else {
				gv_buttom_menu.setVisibility(View.GONE);
			}
		}
		
		return super.onKeyDown(keyCode, event);
	}

	private void loadUserList() {
		lv_userList = (ListView)this.findViewById(R.id.lv_userlist);
		ArrayList<HashMap<String, Object>> data = DBHelper.getInstance(this).getUserList();
		adapter = new SimpleAdapter(this, 
				data, 
				R.layout.imc_listitem, 
				new String[]{"name"}, 
				new int[]{R.id.tv_showname});
		lv_userList.setAdapter(adapter);
		
		//������Ŀ��ִ�в���绰����
		lv_userList.setOnItemLongClickListener(new OnItemLongClickListener(){

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				@SuppressWarnings("unchecked")
				HashMap<String, Object> map = (HashMap<String, Object>)arg0.getItemAtPosition(arg2);

				//��requestCodeΪ3ʱ���������ת��DetailActivityҳ��
				//startActivityForResult(intent, 3);
				
				String number = map.get("mobilephone").toString();
				Intent intentdial = new Intent("android.intent.action.CALL", Uri.parse("tel:" + number));
				startActivity(intentdial);
				
				return true;
			}
			
		});
		
		// �������������Ҫ�Ľ�	
		lv_userList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ϵͳʱ�� = Calendar.getInstance();
				if(��һ�ε�� == 0l) {
					��һ�ε�� = ϵͳʱ��.getTimeInMillis();
					���ε����� = 0l;
				} else if(�ڶ��ε�� == 0l) {
					�ڶ��ε�� = ϵͳʱ��.getTimeInMillis();
					���ε����� = �ڶ��ε�� - ��һ�ε��;
				}
				
				if(���ε����� > 0l && ���ε����� < 500l) {
					��һ�ε�� = 0l;
					�ڶ��ε�� = 0l;
					@SuppressWarnings("unchecked")
					HashMap<String, Object> map = (HashMap<String, Object>)arg0.getItemAtPosition(arg2);
					Intent intent = new Intent(IMaxContact.this, DetailActivity.class);
					intent.putExtra("usermap", map);
					//��requestCodeΪ3ʱ���������ת��DetailActivityҳ��
					startActivityForResult(intent, 3);
				} else if(���ε����� >= 500l) {
					��һ�ε�� = �ڶ��ε��;
					�ڶ��ε�� = 0l;
				}
			}
		});
	}

	private void loadButtomMenu() {
		gv_buttom_menu = (GridView)this.findViewById(R.id.gv_buttom_menu);
		gv_buttom_menu.setBackgroundResource(R.drawable.channelgallery_bg);
		gv_buttom_menu.setNumColumns(1);
		gv_buttom_menu.setGravity(Gravity.CENTER);
		gv_buttom_menu.setVerticalSpacing(10);
		gv_buttom_menu.setHorizontalSpacing(10);
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("itemImage", R.drawable.menu_new_user);
		map.put("itemText", "����");
		data.add(map);
		
		adapter = new SimpleAdapter(this, data, R.layout.imc_itemmenu, new String[]{"itemImage", "itemText"}, new int[]{R.id.item_image, R.id.item_text});
		
		gv_buttom_menu.setAdapter(adapter);
		
		gv_buttom_menu.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(arg2 == 0) {
					Intent intent = new Intent(IMaxContact.this, AddNewActivity.class);
					gv_buttom_menu.setVisibility(View.GONE);
					//startActivity(intent);
					// 0 �������������ת����ӽ���
					startActivityForResult(intent, 0);
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == 0) {
			if(resultCode == 1) {
				// �����û��ɹ�
				ArrayList<HashMap<String, Object>> userdata = DBHelper.getInstance(this).getUserList();
				adapter = new SimpleAdapter(this, 
						userdata, 
						R.layout.imc_listitem, 
						new String[]{"name"}, 
						new int[]{R.id.tv_showname});
				lv_userList.setAdapter(adapter);
			}	
			if(resultCode == 2) {
				// �����û�ʧ�ܣ���ˢ��
			}
		}
		if(requestCode == 3) {
			if(resultCode == 1 || resultCode == 3) {
				ArrayList<HashMap<String, Object>> userdata = DBHelper.getInstance(this).getUserList();
				adapter = new SimpleAdapter(this, 
						userdata, 
						R.layout.imc_listitem, 
						new String[]{"name"}, 
						new int[]{R.id.tv_showname});
				lv_userList.setAdapter(adapter);
			}
		}
	}
}


















