package com.example.memo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	MemoDTO mt = new MemoDTO();
	EditText et;
	private SQLiteDatabase mDatabase;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 처음 화면을 만들 때

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button deletebtn = (Button)findViewById(R.id.delete);
		Button savebtn = (Button)findViewById(R.id.save);
		Button loadbtn = (Button)findViewById(R.id.load);

		deletebtn.setOnClickListener(l);
		savebtn.setOnClickListener(l);
		loadbtn.setOnClickListener(l);
		
		Intent intent = getIntent();

		et = (EditText)findViewById(R.id.edit);

		// 목록에서 가져온 text와 순서를 객체에 저장. (새글은 비어있고, 순서는 마지막꺼)
		mt.setData(intent.getStringExtra("data"));
		mt.setId(intent.getIntExtra("id", 0));

		et.setText(mt.getData());

		//Toast.makeText(this, ""+memo+option, Toast.LENGTH_SHORT).show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	View.OnClickListener l = new View.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//setResult의 코드번호는 ListActivity에서 onActivityResult의 resultCode와 연결된다.
			if(v.getId()==R.id.delete){
				mDatabase = openOrCreateDatabase("memotable.db",
						SQLiteDatabase.CREATE_IF_NECESSARY, null);
				mDatabase.delete("memotable", "id = ?", new String[]{String.valueOf(mt.getId())});
				mDatabase.close();

				finish();
			}else if(v.getId()==R.id.save){
				// 저장시 현재 텍스트박스에 들어있는 데이터와, 날짜를 객체에 저장
				mt.setData(et.getText().toString());
				mt.setDate("2015-06-21");

				mDatabase = openOrCreateDatabase("memotable.db",
						SQLiteDatabase.CREATE_IF_NECESSARY, null);
				ContentValues values = new ContentValues();
				values.put("data", mt.getData());
				values.put("date", mt.getDate());

				mDatabase.insert("memotable", null, values);
				mDatabase.close();

				finish(); // DB 저장후 닫기만.
				
			}else if(v.getId()==R.id.load){
				et.setText(mt.getData());
			}
		}};

	private void deleteMemo(){

	}

}
