package edu.upc.eetac.dsa.emeyerson.demo.android.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.upc.eetac.dsa.emeyerson.demo.android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LibroAdapter extends BaseAdapter {

	private ArrayList<Libro> data;
	private LayoutInflater inflater;
	
	private static class ViewHolder {
		TextView tvTitulo;
		TextView tvAutor;

	}
	
	public LibroAdapter(Context context, ArrayList<Libro> data) {
		super();
		inflater = LayoutInflater.from(context);
		this.data = data;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}
	 
	@Override
	public Object getItem(int position) {
		return data.get(position);
	}
	 
	@Override
	public long getItemId(int position) {
		return Long.parseLong(Integer.toString(((Libro)getItem(position)).getIdlibro()));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_row_libro, null);
			viewHolder = new ViewHolder();
			viewHolder.tvTitulo = (TextView) convertView
					.findViewById(R.id.tvTitulo);
			viewHolder.tvAutor = (TextView) convertView
					.findViewById(R.id.tvAutor);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String titulo = data.get(position).getTitulo();
		String autor = data.get(position).getAutor();

		viewHolder.tvTitulo.setText(titulo);
		viewHolder.tvAutor.setText(autor);
		return convertView;
	}

}
