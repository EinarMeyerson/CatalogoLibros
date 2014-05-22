package edu.upc.eetac.dsa.emeyerson.demo.android.api;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.Map;
import java.util.Properties;
 



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 



import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
 
public class LibrosAPI {
	private final static String TAG = LibrosAPI.class.getName();
	private static LibrosAPI instance = null;
	private URL url;
 
	private LibrosRootAPI rootAPI = null;
 
	private LibrosAPI(Context context) throws IOException,
			LibrosAndroidException {
		super();
 
		AssetManager assetManager = context.getAssets();
		Properties config = new Properties();
		config.load(assetManager.open("config.properties"));
		String serverAddress = config.getProperty("server.address");
		String serverPort = config.getProperty("server.port");
		url = new URL("http://" + serverAddress + ":" + serverPort
				+ "/beeter-api");
 
		Log.d("LINKS", url.toString());
		getRootAPI();
	}
 
	public final static LibrosAPI getInstance(Context context)
			throws LibrosAndroidException {
		if (instance == null)
			try {
				instance = new LibrosAPI(context);
			} catch (IOException e) {
				throw new LibrosAndroidException(
						"Can't load configuration file");
			}
		return instance;
	}
 
	private void getRootAPI() throws LibrosAndroidException {
		Log.d(TAG, "getRootAPI()");
		rootAPI = new LibrosRootAPI();
		HttpURLConnection urlConnection = null;
		try {
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoInput(true);
			urlConnection.connect();
		} catch (IOException e) {
			throw new LibrosAndroidException(
					"Can't connect to Beeter API Web Service");
		}
 
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
 
			JSONObject jsonObject = new JSONObject(sb.toString());
			JSONArray jsonLinks = jsonObject.getJSONArray("links");
			parseLinks(jsonLinks, rootAPI.getLinks());
		} catch (IOException e) {
			throw new LibrosAndroidException(
					"Can't get response from Beeter API Web Service");
		} catch (JSONException e) {
			throw new LibrosAndroidException("Error parsing Beeter Root API");
		}
 
	}
 
	public LibroCollection getLibros() throws LibrosAndroidException {
		Log.d(TAG, "getStings()");
		LibroCollection libros = new LibroCollection();
 
		HttpURLConnection urlConnection = null;
		try {
			urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
					.get("libros").getTarget()).openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoInput(true);
			urlConnection.connect();
		} catch (IOException e) {
			throw new LibrosAndroidException(
					"Can't connect to Beeter API Web Service");
		}
 
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
 
			JSONObject jsonObject = new JSONObject(sb.toString());
			JSONArray jsonLinks = jsonObject.getJSONArray("links");
			parseLinks(jsonLinks, libros.getLinks());
 
//			libros.setNewestTimestamp(jsonObject.getLong("newestTimestamp"));
//			libros.setOldestTimestamp(jsonObject.getLong("oldestTimestamp"));
			JSONArray jsonLibros = jsonObject.getJSONArray("libros");
			for (int i = 0; i < jsonLibros.length(); i++) {
				Libro libro = new Libro();
				JSONObject jsonLibro = jsonLibros.getJSONObject(i);
				libro.setAutor(jsonLibro.getString("autor"));
				libro.setTitulo(jsonLibro.getString("titulo"));
				libro.setIdlibro(jsonLibro.getInt("idlibro"));
				libro.setEdicion(jsonLibro.getInt("edicion"));
				libro.setEditorial(jsonLibro.getString("editorial"));
				libro.setFecha_edicion(Date.valueOf(jsonLibro.getString("fecha_edicion")));
				libro.setFecha_impresion(Date.valueOf(jsonLibro.getString("fecha_impresion")));
				libro.setLengua(jsonLibro.getString("lengua"));
				jsonLinks = jsonLibro.getJSONArray("links");
				parseLinks(jsonLinks, libro.getLinks());
				libros.getLibros().add(libro);
			}
		} catch (IOException e) {
			throw new LibrosAndroidException(
					"Can't get response from Beeter API Web Service");
		} catch (JSONException e) {
			throw new LibrosAndroidException("Error parsing Beeter Root API");
		}
 
		return libros;
	}
	
	public Libro getLibro(String urlLibro) throws LibrosAndroidException {
		Libro libro = new Libro();
	 
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(urlLibro);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoInput(true);
			urlConnection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			JSONObject jsonLibro = new JSONObject(sb.toString());
			libro.setAutor(jsonLibro.getString("autor"));
			libro.setTitulo(jsonLibro.getString("titulo"));
			libro.setIdlibro(jsonLibro.getInt("idlibro"));
			libro.setEdicion(jsonLibro.getInt("edicion"));
			libro.setEditorial(jsonLibro.getString("editorial"));
			libro.setFecha_edicion(Date.valueOf(jsonLibro.getString("fecha_edicion")));
			libro.setFecha_impresion(Date.valueOf(jsonLibro.getString("fecha_impresion")));
			libro.setLengua(jsonLibro.getString("lengua"));
			
			JSONArray jsonLinks = jsonLibro.getJSONArray("links");
			parseLinks(jsonLinks, libro.getLinks());
			
		} catch (MalformedURLException e) {
			Log.e(TAG, e.getMessage(), e);
			throw new LibrosAndroidException("Bad libro url");
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
			throw new LibrosAndroidException("Exception when getting the libro");
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
			throw new LibrosAndroidException("Exception parsing response");
		}
	 
		return libro;
	}
	
	
//	public Sting createSting(String subject, String content) throws LibrosAndroidException {
//		Sting sting = new Sting();
//		sting.setSubject(subject);
//		sting.setContent(content);
//		HttpURLConnection urlConnection = null;
//		try {
//			JSONObject jsonSting = createJsonSting(sting);
//			URL urlPostStings = new URL(rootAPI.getLinks().get("create-stings")
//					.getTarget());
//			urlConnection = (HttpURLConnection) urlPostStings.openConnection();
//			urlConnection.setRequestProperty("Accept",
//					MediaType.BEETER_API_STING);
//			urlConnection.setRequestProperty("Content-Type",
//					MediaType.BEETER_API_STING);
//			urlConnection.setRequestMethod("POST");
//			urlConnection.setDoInput(true);
//			urlConnection.setDoOutput(true);
//			urlConnection.connect();
//			PrintWriter writer = new PrintWriter(
//					urlConnection.getOutputStream());
//			writer.println(jsonSting.toString());
//			writer.close();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(
//					urlConnection.getInputStream()));
//			StringBuilder sb = new StringBuilder();
//			String line = null;
//			while ((line = reader.readLine()) != null) {
//				sb.append(line);
//			}
//			jsonSting = new JSONObject(sb.toString());
//	 
//			sting.setAuthor(jsonSting.getString("author"));
//			sting.setId(jsonSting.getString("id"));
//			sting.setLastModified(jsonSting.getLong("lastModified"));
//			sting.setSubject(jsonSting.getString("subject"));
//			sting.setContent(jsonSting.getString("content"));
//			sting.setUsername(jsonSting.getString("username"));
//			JSONArray jsonLinks = jsonSting.getJSONArray("links");
//			parseLinks(jsonLinks, sting.getLinks());
//		} catch (JSONException e) {
//			Log.e(TAG, e.getMessage(), e);
//			throw new LibrosAndroidException("Error parsing response");
//		} catch (IOException e) {
//			Log.e(TAG, e.getMessage(), e);
//			throw new LibrosAndroidException("Error getting response");
//		} finally {
//			if (urlConnection != null)
//				urlConnection.disconnect();
//		}
//		return sting;
//	}
//	 
//	private JSONObject createJsonSting(Sting sting) throws JSONException {
//		JSONObject jsonSting = new JSONObject();
//		jsonSting.put("subject", sting.getSubject());
//		jsonSting.put("content", sting.getContent());
//	 
//		return jsonSting;
//	}
// 
	private void parseLinks(JSONArray jsonLinks, Map<String, Link> map)
			throws LibrosAndroidException, JSONException {
		for (int i = 0; i < jsonLinks.length(); i++) {
			Link link = SimpleLinkHeaderParser
					.parseLink(jsonLinks.getString(i));
			String rel = link.getParameters().get("rel");
			String rels[] = rel.split("\\s");
			for (String s : rels)
				map.put(s, link);
		}
	}
}
