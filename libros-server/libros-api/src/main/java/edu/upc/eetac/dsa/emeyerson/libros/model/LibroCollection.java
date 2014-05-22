package edu.upc.eetac.dsa.emeyerson.libros.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.emeyerson.libros.api.LibroResource;
import edu.upc.eetac.dsa.emeyerson.libros.api.MediaType;
public class LibroCollection {
		@InjectLinks({
			@InjectLink(resource = LibroResource.class, style = Style.ABSOLUTE, rel = "create-libro", title = "Create libro", type = MediaType.LIBROS_API_LIBRO)})
	private List<Link> links;
	private List<Libro> libros;
	public void add(Libro libro) {
		libros.add(libro);
	}
	public LibroCollection() {
		super();
		libros = new ArrayList<>();
	}
	public List<Libro> getLibros() {
		return libros;
	}

	public void setLibros(List<Libro> libros) {
		this.libros = libros;
	}
	
	public void add(Link link) {
		links.add(link);
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
}
