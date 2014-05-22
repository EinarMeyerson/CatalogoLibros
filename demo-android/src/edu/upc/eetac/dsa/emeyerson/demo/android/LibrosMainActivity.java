package edu.upc.eetac.dsa.emeyerson.demo.android;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;

import edu.upc.eetac.dsa.emeyerson.demo.android.api.LibrosAPI;
import edu.upc.eetac.dsa.emeyerson.demo.android.api.LibrosAndroidException;
import edu.upc.eetac.dsa.emeyerson.demo.android.api.Libro;
import edu.upc.eetac.dsa.emeyerson.demo.android.api.LibroCollection;
import edu.upc.eetac.dsa.emeyerson.demo.android.api.LibroAdapter;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LibrosMainActivity extends ListActivity {
	private final static String TAG = LibrosMainActivity.class.toString();
//	private static final String[] items = { "lorem", "ipsum", "dolor", "sit",
//			"amet", "consectetuer", "adipiscing", "elit", "morbi", "vel",
//			"ligula", "vitae", "arcu", "aliquet", "mollis", "etiam", "vel",
//			"erat", "placerat", "ante", "porttitor", "sodales", "pellentesque",
//			"augue", "purus" };
//	private ArrayAdapter<String> adapter;
 
	private ArrayList<Libro> LibrosList;
	private LibroAdapter adapter ;
	
	private class FetchLibrosTask extends AsyncTask<Void, Void, LibroCollection> {
		private ProgressDialog pd;

		@Override
		protected LibroCollection doInBackground(Void... params) {
			LibroCollection libros = null;
			try {
				libros = LibrosAPI.getInstance(LibrosMainActivity.this).getLibros();
			} catch (LibrosAndroidException e) {
				e.printStackTrace();
			}
			return libros;
		}

		@Override
		protected void onPostExecute(LibroCollection result) {
			addLibros(result);
			if (pd != null) {
				pd.dismiss();
			}
		}

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(LibrosMainActivity.this);
			pd.setTitle("Searching...");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}

	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.beeter_actions, menu);
//		getMenuInflater().inflate(R., menu);
//		return true;
//	}
//	 
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.miWrite:
//			Intent intent = new Intent(this, WriteResenaActivity.class);
//			startActivity(intent);
//			return true;
//	 
//		default:
//			return super.onOptionsItemSelected(item);
//		}
//	 
//	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.libros_layout);
		SharedPreferences prefs = getSharedPreferences("libros-profile",
				Context.MODE_PRIVATE);
		final String username = prefs.getString("username", null);
		final String password = prefs.getString("password", null);
	 
		Authenticator.setDefault(new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password
						.toCharArray());
			}
		});
		Log.d(TAG, "authenticated with " + username + ":" + password);
	 
		LibrosList = new ArrayList<Libro>();
		adapter = new LibroAdapter(this, LibrosList);
		setListAdapter(adapter);
		(new FetchLibrosTask()).execute();
	}
	
	private void addLibros(LibroCollection libros){
		LibrosList.addAll(libros.getLibros());
		adapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Libro libro = LibrosList.get(position);
		Log.d(TAG, libro.getLinks().get("self").getTarget());
		
		Intent intent = new Intent(this, LibroDetailActivity.class);
		intent.putExtra("url", libro.getLinks().get("self").getTarget());
		startActivity(intent);
	}
	
}
