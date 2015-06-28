package com.example.memo;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MemoListActivity extends ListActivity  implements OnItemClickListener{
	ListView listview ;

	ArrayList<String> mList = new ArrayList<String>();// 메모 목록
	ArrayList<MemoDTO> mtList = new ArrayList<MemoDTO>();// 메모한개
	ArrayAdapter<String> adapter;

	private SQLiteDatabase mDatabase;

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.list);
		
	    /*ArrayList<String> sample = new ArrayList<String>();*/

	    /*ArrayAdapter<String> adapter;*/
	    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, mList);
	    setListAdapter(adapter);
	    
		// 이벤트1 : 목록의 아이템하나 클릭했을때
	    listview = this.getListView();
	    listview.setOnItemClickListener(this);

		// 이벤트2 : 새글쓰기 눌렀을때
		Button writeBtn = (Button)findViewById(R.id.listwrite);
		writeBtn.setOnClickListener(l);

		// 테이블 만들고, 데이터 읽어오기.
		dropTable();
		createTable();
		loadTable();

	    /*
	    ArrayAdapter<String> adapter;
	    adapter = new ArrayAdapter<String>(this, R.layout.simple_expandable_list_item_1, sample);
	    
	    listview = (ListView)findViewById(R.id.list);
	    listview.setAdapter(adapter);
	    */
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//클릭시 이벤트
	View.OnClickListener l = new View.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getId()==R.id.listwrite){
				Intent intent = new Intent(MemoListActivity.this,MainActivity.class);

				intent.putExtra("data", "");
				intent.putExtra("id",mList.size());
	     		  
				startActivity(intent);
			}
		}
		
	};

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		// TODO Auto-generated method stub

		Intent intent = new Intent(MemoListActivity.this,MainActivity.class);

		intent.putExtra("data", ""+arg0.getItemAtPosition(position));
		intent.putExtra("id", mtList.get(position).getId());
		startActivity(intent);
		
		//Toast.makeText(v.getContext(), ""+position, Toast.LENGTH_SHORT).show();
	}


	// 목록에서 새글쓰기나 수정하기를 완료한 후 다시 목록으로 돌아왔을 때 실행할 것 (1=새글 2=수정) .
	// 근데 DB에 저장하고 리스트만 뿌리는 형태로 변경하면서 사용X
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

   	 	/*// 새글 쓰고, text를 전달받아 목록에 추가함.
		if(resultCode==1){
   	 }
		// 수정, 내용가져옴
		else if(resultCode==2){
   		//Toast.makeText(this, ""+listdata, Toast.LENGTH_SHORT).show();
   	 }*/
	}

	private void dropTable(){
		mDatabase = openOrCreateDatabase("memotable.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
		String query = "drop table memotable;";
		mDatabase.execSQL(query);
		mDatabase.close();
	}

	private void createTable() {
		// TODO Auto-generated method stub
		mDatabase = openOrCreateDatabase("memotable.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);

		String search = "select name from sqlite_master where type='table' and name='memotable';";
		Cursor c = mDatabase.rawQuery(search,null);

		Log.d("log0Count", "" + c.getCount());

		if(c.getCount()==0) {
			String sql = "create table memotable" +
					"(id integer primary key autoincrement, " +
					"data text not null, " +
					"date text not null);";
			mDatabase.execSQL(sql);
		}
		mDatabase.close();
	}

	private void loadTable(){
		String query="select id, data from memotable;";
		mDatabase = openOrCreateDatabase("memotable.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
		Cursor c = mDatabase.rawQuery(query, null);
		if(c.getCount()!=0){
			while(c.moveToNext()){
				int id = c.getInt(0);
				String data = c.getString(1);
				mtList.add(new MemoDTO(id, data));
				mList.add(data);
			}

		}
		adapter.notifyDataSetChanged();
		mDatabase.close();
	}

	//메뉴 클릭시 호출 되는 이벤트
	/*
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, ""+arg0.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
		//Toast.makeText(this, ""+sample.get(position), Toast.LENGTH_SHORT).show();
		//Toast.makeText(v.getContext(), ""+position, Toast.LENGTH_SHORT).show();
		
		Intent intent = new Intent(MemoListActivity.this,MainActivity.class);
		  
		//startActivity(intent);
		intent.putExtra("memo", ""+arg0.getItemAtPosition(position));
		intent.putExtra("option", 2);
		intent.putExtra("num", position);
		startActivityForResult(intent, 1);
		
	};
	*/
}
