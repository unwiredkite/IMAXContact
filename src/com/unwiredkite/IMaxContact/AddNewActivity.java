package com.unwiredkite.IMaxContact;

import com.unwiredkite.db.DBHelper;
import com.unwiredkite.entity.User;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewActivity extends Activity {	
	EditText et_name;
	EditText et_mobilePhone;
	
	Button btn_save;
	Button btn_return;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imc_addnewitem);
		
		// ��ֹ������Զ�����
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		initEditTextBox();
		
		btn_save.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = et_name.getText().toString();
				if(name.equals("")) {
					Toast.makeText(AddNewActivity.this, "��������Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				
				String mobilePhone = et_mobilePhone.getText().toString();

				User user = new User();
				user.name = name;
				user.mobilePhone = mobilePhone;

				if(DBHelper.getInstance(AddNewActivity.this).isExist(user) == true) {
					Toast.makeText(AddNewActivity.this, "����", Toast.LENGTH_SHORT).show();
				} else {
					// Save user to database
					long success = DBHelper.getInstance(AddNewActivity.this).save(user);
					if(success != -1) {
						Toast.makeText(AddNewActivity.this, "��ӳɹ�", Toast.LENGTH_SHORT).show();
						// ���ӳɹ�������������
						setResult(1);
						finish();
					} else {
						Toast.makeText(AddNewActivity.this, "���ʧ��", Toast.LENGTH_SHORT).show();
						// ����ʧ�ܣ�����������
						setResult(2);
						finish();
					}
				}
			}
		});
		
		btn_return.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setResult(2);
				finish();
			}
		});
	}
	
	private void initEditTextBox() {
		// TODO Auto-generated method stub
		et_name = (EditText)this.findViewById(R.id.et_name);
		et_mobilePhone = (EditText)this.findViewById(R.id.et_mobilephone);
		
		btn_save = (Button)this.findViewById(R.id.btn_save);
		btn_return = (Button)this.findViewById(R.id.btn_return);
	}
}










