package edu.upc.eetac.dsa.emeyerson.demo.android;

import java.text.SimpleDateFormat;

import edu.upc.eetac.dsa.emeyerson.demo.android.api.LibrosAPI;
import edu.upc.eetac.dsa.emeyerson.demo.android.api.LibrosAndroidException;
import edu.upc.eetac.dsa.emeyerson.demo.android.api.Libro;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
 
public class LibroDetailActivity extends Activity {
	private final static String TAG = LibroDetailActivity.class.getName();
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.libro_detail_layout);
		String urlLibro = (String) getIntent().getExtras().get("url");
		(new FetchStingTask()).execute(urlLibro);
	}
	
	private class FetchStingTask extends AsyncTask<String, Void, Libro> {
		private ProgressDialog pd;
	 
		@Override
		protected Libro doInBackground(String... params) {
			Libro libro = null;
			try {
				libro = LibrosAPI.getInstance(LibroDetailActivity.this).getLibro(params[0]);
			} catch (LibrosAndroidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return libro;
		}
	 
		@Override
		protected void onPostExecute(Libro result) {
			loadLibro(result);
			if (pd != null) {
				pd.dismiss();
			}
		}
	 
		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(LibroDetailActivity.this);
			pd.setTitle("Loading...");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}
	 
	}
	
	private void loadLibro(Libro libro) {
		TextView tvDetailAutor = (TextView) findViewById(R.id.tvDetailAutor);
		TextView tvDetailEdicion = (TextView) findViewById(R.id.tvDetailEdicion);
		TextView tvDetailEditorial = (TextView) findViewById(R.id.tvDetailEditorial);
		TextView tvDetailLengua = (TextView) findViewById(R.id.tvDetailLengua);
		TextView tvDetailTitulo = (TextView) findViewById(R.id.tvDetailTitulo);

	 
		tvDetailAutor.setText(libro.getAutor());
		tvDetailEdicion.setText(libro.getEdicion());
		tvDetailEditorial.setText(libro.getEditorial());
		tvDetailLengua.setText(libro.getLengua());
		tvDetailTitulo.setText(libro.getTitulo());

	}
 
}