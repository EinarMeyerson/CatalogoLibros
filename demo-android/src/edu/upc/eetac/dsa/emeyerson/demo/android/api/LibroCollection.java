package edu.upc.eetac.dsa.emeyerson.demo.android.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.upc.eetac.dsa.emeyerson.demo.android.api.Link;

//import javax.ws.rs.core.Link;

//import edu.upc.eetac.dsa.emeyerson.libros.api.links.Link;

public class LibroCollection {
//		@InjectLinks({
//			@InjectLink(resource = StingResource.class, style = Style.ABSOLUTE, rel = "create-sting", title = "Create sting", type = MediaType.BEETER_API_STING),
//			@InjectLink(value = "/stings?before={before}", style = Style.ABSOLUTE, rel = "previous", title = "Previous stings", type = MediaType.BEETER_API_STING_COLLECTION, bindings = { @Binding(name = "before", value = "${instance.oldestTimestamp}") }),
//			@InjectLink(value = "/stings?after={after}", style = Style.ABSOLUTE, rel = "current", title = "Newest stings", type = MediaType.BEETER_API_STING_COLLECTION, bindings = { @Binding(name = "after", value = "${instance.newestTimestamp}") }) })
//	private List<Link> links;
//	private long newestTimestamp;
//	private long oldestTimestamp;
	private List<Libro> libros = new ArrayList<Libro>();
	private Map<String, Link> links = new HashMap<String, Link>();

//	private List<Link> links = new ArrayList<Link>();

	public void add(Libro libro) {
		libros.add(libro);
	}

	public List<Libro> getLibros() {
		return libros;
	}
	public Map<String, Link> getLinks() {
		return links;
	}
 

	public void setLibros(List<Libro> libros) {
		this.libros = libros;
	}
	
//	public long getNewestTimestamp() {
//		return newestTimestamp;
//	}
// 
//	public void setNewestTimestamp(long newestTimestamp) {
//		this.newestTimestamp = newestTimestamp;
//	}
// 
//	public long getOldestTimestamp() {
//		return oldestTimestamp;
//	}
// 
//	public void setOldestTimestamp(long oldestTimestamp) {
//		this.oldestTimestamp = oldestTimestamp;
//	}

//	public void add(Link link) {
//		links.add(link);
//	}
//
//	public List<Link> getLinks() {
//		return links;
//	}
//
//	public void setLinks(List<Link> links) {
//		this.links = links;
//	}
}
