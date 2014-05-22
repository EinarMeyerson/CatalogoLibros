package edu.upc.eetac.dsa.emeyerson.libros.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.emeyerson.libros.api.LibroResource;
import edu.upc.eetac.dsa.emeyerson.libros.api.MediaType;

//import edu.upc.eetac.dsa.emeyerson.libros.api.links.Link;

public class ResenaCollection {
	@InjectLinks({
		@InjectLink(resource = LibroResource.class, style = Style.ABSOLUTE, rel = "libros", title = "Latest libros", type = MediaType.LIBROS_API_LIBRO_COLLECTION)})
	private List<Link> links;
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
	private List<Resena> resenas = new ArrayList<Resena>();

//	private List<Link> links = new ArrayList<Link>();

	public void add(Resena resena) {
		resenas.add(resena);
	}

	public List<Resena> getResenas() {
		return resenas;
	}

	public void setResenas(List<Resena> resenas) {
		this.resenas = resenas;
	}

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
