package com.unwiredkite.IMaxContact;

import java.util.HashMap;

import com.unwiredkite.db.DBHelper;
import com.unwiredkite.entity.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DetailActivity extends Activity {
	
	EditText et_name;
	EditText et_mobilePhone;
	Button btn_delet;
	Button btn_modify;
	Button btn_return;
	//ImageButton btn_img;
	
	HashMap<String, Object> map;
	
	boolean flag = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imc_detail);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		initEditTextBox();
		setEditTextDisable();
		Intent intent = getIntent();
		
		map = (HashMap<String, Object>)intent.getSerializableExtra("usermap");
		displayData();
		
		btn_modify.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(flag)
				{
					
					String name = et_name.getText().toString();
					if(name.equals("")) {
						Toast.makeText(DetailActivity.this, "姓名不能为空", Toast.LENGTH_LONG).show();
						return;
					}
					
					String mobilePhone = et_mobilePhone.getText().toString();
					//int imageId = images[imagePosition];//此处有问题，待查找
					
					User user = new User();
					user.name = name;
					user.mobilePhone = mobilePhone;


					// Save user to database
					long success = DBHelper.getInstance(DetailActivity.this).modify(user);
					if(success != -1) {
						Toast.makeText(DetailActivity.this, "修改成功", Toast.LENGTH_LONG).show();
						// 增加成功，返回主界面
						setResult(1);
						finish();
					} else {
						Toast.makeText(DetailActivity.this, "修改失败", Toast.LENGTH_LONG).show();
						// 增加失败，返回主界面
						setResult(2);
						finish();
					}
					
					
					flag = false;
					btn_modify.setText("修改");
					setEditTextDisable();
				}
				else {
					flag = true;
					btn_modify.setText("保存");
					setEditTextEnable();
				}
			}
		});
		
		btn_delet.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String name = et_name.getText().toString();
				if(name.equals("")) {
					Toast.makeText(DetailActivity.this, "姓名不能为空", Toast.LENGTH_LONG).show();
					return;
				}
				
				String mobilePhone = et_mobilePhone.getText().toString();
				
				User user = new User();
				user.name = name;
				user.mobilePhone = mobilePhone;

				DBHelper.getInstance(DetailActivity.this).delete(user);

				//删除成功，code3
				setResult(3);
				finish();
			}
		});
	}
	
	private void displayData() {
		et_name.setText(String.valueOf(map.get("name")));
		et_mobilePhone.setText(String.valueOf(map.get("mobilephone")));	
		//btn_img.setImageResource((int) map.get("image"));
		//btn_img.setImageResource(Integer.parseInt(String.valueOf(map.get("imageid"))));
	}

	private void initEditTextBox() {
		// TODO Auto-generated method stub
		et_name = (EditText)this.findViewById(R.id.et_name);
		et_mobilePhone = (EditText)this.findViewById(R.id.et_mobilephone);
		btn_delet = (Button)this.findViewById(R.id.btn_delet);
		btn_modify = (Button)this.findViewById(R.id.btn_modify);
		btn_return = (Button)this.findViewById(R.id.btn_return);
		
		//btn_img = (ImageButton)this.findViewById(R.id.btn_img);
	}
	
	private void setEditTextDisable() {
		et_name.setEnabled(false);
		et_mobilePhone.setEnabled(false);
		//btn_img.setEnabled(false);
	}
	
	private void setEditTextEnable() {
		et_name.setEnabled(true);
		et_mobilePhone.setEnabled(true);
		//btn_img.setEnabled(true);
	}
}















