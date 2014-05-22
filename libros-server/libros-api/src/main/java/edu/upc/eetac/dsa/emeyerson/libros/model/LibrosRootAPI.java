package edu.upc.eetac.dsa.emeyerson.libros.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.emeyerson.libros.api.LibrosRootAPIResource;
import edu.upc.eetac.dsa.emeyerson.libros.api.MediaType;
import edu.upc.eetac.dsa.emeyerson.libros.api.LibroResource;

//import edu.upc.eetac.dsa.emeyerson.libros.api.links.Link;


//Faltan los injecLinks
public class LibrosRootAPI {

	@InjectLinks({
		@InjectLink(resource = LibrosRootAPIResource.class, style = Style.ABSOLUTE, rel = "self bookmark home", title = "Beeter Root API", method = "getRootAPI"),
		@InjectLink(resource = LibroResource.class, style = Style.ABSOLUTE, rel = "libros", title = "Latest libros", type = MediaType.LIBROS_API_LIBRO_COLLECTION),
		@InjectLink(resource = LibroResource.class, style = Style.ABSOLUTE, rel = "create-libros", title = "Latest libros", type = MediaType.LIBROS_API_LIBRO) })
	private List<Link> links;

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
