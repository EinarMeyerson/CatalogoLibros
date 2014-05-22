package edu.upc.eetac.dsa.emeyerson.libros.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

//import edu.upc.eetac.dsa.emeyerson.libros.api.links.LibrosAPILinkBuilder;
import edu.upc.eetac.dsa.emeyerson.libros.model.LibrosRootAPI;

@Path("/")
public class LibrosRootAPIResource {

	@Context
	private UriInfo uriInfo;

	@GET
	@Produces(MediaType.LIBROS_API_LINK_COLLECTION)
	public LibrosRootAPI getLinks() {
		LibrosRootAPI root = new LibrosRootAPI();

//		root.add(LibrosAPILinkBuilder.buildURIRootAPI(uriInfo));
//
//		root.add(LibrosAPILinkBuilder.buildURIUsers(uriInfo, "users"));
//
//		root.add(LibrosAPILinkBuilder.buildURILibros(uriInfo, "libros"));
//
//		root.add(LibrosAPILinkBuilder.buildTemplatedURILibros(uriInfo, true,
//				true, "libros"));
//
//		root.add(LibrosAPILinkBuilder.buildTemplatedURILibros(uriInfo, false,
//				true, "libros"));
//
//		root.add(LibrosAPILinkBuilder.buildTemplatedURILibros(uriInfo, true,
//				false, "libros"));
//
//		root.add(LibrosAPILinkBuilder.buildURIResenas(uriInfo, "resenas"));
		return root;
	}
}
