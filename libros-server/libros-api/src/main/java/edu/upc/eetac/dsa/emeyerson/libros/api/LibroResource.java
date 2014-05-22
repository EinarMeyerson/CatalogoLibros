package edu.upc.eetac.dsa.emeyerson.libros.api;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import edu.upc.eetac.dsa.emeyerson.libros.api.MediaType;
//import edu.upc.eetac.dsa.emeyerson.libros.api.links.LibrosAPILinkBuilder;
//import edu.upc.eetac.dsa.emeyerson.libros.api.links.Link;
import edu.upc.eetac.dsa.emeyerson.libros.model.Libro;
import edu.upc.eetac.dsa.emeyerson.libros.model.LibroCollection;
import edu.upc.eetac.dsa.emeyerson.libros.model.LibrosRootAPI;
import edu.upc.eetac.dsa.emeyerson.libros.model.Resena;
import edu.upc.eetac.dsa.emeyerson.libros.model.ResenaCollection;

@Path("/libros")
public class LibroResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	private Libro getLibroFromDatabase(int idlibro) {
		Libro libro = new Libro();
	 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(buildGetLibroByIdQuery());

			stmt.setInt(1, idlibro);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				libro.setIdlibro(rs.getInt("idlibro"));
				libro.setTitulo(rs.getString("titulo"));
				libro.setAutor(rs.getString("autor"));
				libro.setEdicion(rs.getInt("edicion"));
				libro.setEditorial(rs.getString("editorial"));
				libro.setLengua(rs.getString("lengua"));
				libro.setFecha_edicion(rs.getDate("fecha_edicion"));
				libro.setFecha_impresion(rs.getDate("fecha_impresion"));
//				libro.setLastModified(rs.getTimestamp("last_modified")
//						.getTime());
				
//				List<Resena> resenas = new ArrayList<Resena>();	
//				stmt = conn.prepareStatement(buildGetResenadeLibroQuery());
//				stmt.setInt(1, libro.getIdlibro());
//				ResultSet rs2 = stmt.executeQuery();
//				
//				while (rs2.next()) {
//
//					Resena resena = new Resena();
//					resena.setIdlibro(rs2.getInt("idlibro"));
//					System.out.println(""+resena.getIdlibro());
//					resena.setIdresena(rs2.getInt("idresena"));
//					System.out.println(""+resena.getIdresena());
//					resena.setName(rs2.getString("name"));
//					System.out.println(resena.getName());
//					resena.setUsername(rs2.getString("username"));
//					resena.setTexto(rs2.getString("texto"));
//					System.out.println(resena.getTexto());
//					resena.setFecha_creacion(rs2.getDate("fecha_creacion"));
//					System.out.println("antes de añadir al list");
//					resenas.add(resena);
//					System.out.println("despues de añadir al list");
//
//					
//				}
//				libro.setResenas(resenas);
			} else {
				throw new NotFoundException("There's no libro with idlibro="
						+ idlibro);
			}
	 
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	 
		return libro;
	}
	
	@Context
	private UriInfo uriInfo;

	LibrosRootAPI root = new LibrosRootAPI();
	String rel = null;
	@Context
	private SecurityContext security;
	
	@GET
	@Produces(MediaType.LIBROS_API_LIBRO_COLLECTION)
	public LibroCollection getLibros(@QueryParam("before") long before, @QueryParam("after") long after) {
		LibroCollection libros = new LibroCollection();
	 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
//		Statement stmt = null;
		PreparedStatement stmt = null;
		try {
//			boolean updateFromLast = after > 0;
//			stmt = conn.prepareStatement(buildGetLibrosQuery(updateFromLast));
//			if (updateFromLast) {
//				stmt.setTimestamp(1, new Timestamp(after));
//			} else {
//				if (before > 0)
//					stmt.setTimestamp(1, new Timestamp(before));
//				else
//					stmt.setTimestamp(1, null);
//				length = (length <= 0) ? 20 : length;
//				stmt.setInt(2, length);
			//}
			//sql = "SELECT * FROM libros";
			//stmt = conn.createStatement();
			stmt = conn.prepareStatement(buildGetLibrosQuery());
			ResultSet rs = stmt.executeQuery();
//			boolean first = true;
//			long oldestTimestamp = 0;
					
			while (rs.next()) {
				Libro libro = new Libro();
				
				libro.setIdlibro(rs.getInt("idlibro"));
				libro.setTitulo(rs.getString("titulo"));
				libro.setAutor(rs.getString("autor"));
				libro.setEditorial(rs.getString("editorial"));
				libro.setLengua(rs.getString("lengua"));
				libro.setEdicion(rs.getInt("edicion"));
				libro.setFecha_edicion(rs.getDate("fecha_edicion"));
				libro.setFecha_impresion(rs.getDate("fecha_impresion"));
							
				
				libros.add(libro);
			}
				
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		
	 
		return libros;
	}
	
	private String buildGetLibrosQuery() {
		
		return "select * from libros";
	}
	
	private String buildGetResenadeLibroQuery() {
		
		return "SELECT resenas.*, users.name FROM users INNER JOIN resenas ON(idlibro= ? and users.username=resenas.username) ";
	}

	@GET
	@Path("/search")
	@Produces(MediaType.LIBROS_API_LIBRO_COLLECTION)
	public LibroCollection GetSearchLibros(@QueryParam("titulo") String titulo,
			@QueryParam("autor") String autor) {
		LibroCollection libros = new LibroCollection();
	 
		Connection conn = null;
		
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(buildSearchLibrosQuery(autor, titulo));
			if (autor==null) {
				stmt.setString(1, '%'+titulo+'%');
				//length = (length <= 0) ? 20 : length;
				//stmt.setInt(2, length);
			} 
			else if (titulo==null) {
				stmt.setString(1, '%'+autor+'%');				
//				length = (length <= 0) ? 20 : length;
//				stmt.setInt(2, length);
			} 
			else {
				stmt.setString(1, '%'+titulo+'%');
				stmt.setString(2,'%'+ autor+'%');
//				length = (length <= 0) ? 20 : length;
//				stmt.setInt(3, length);
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Libro libro = new Libro();
				
				libro.setIdlibro(rs.getInt("idlibro"));
				libro.setTitulo(rs.getString("titulo"));
				libro.setAutor(rs.getString("autor"));
				libro.setEditorial(rs.getString("editorial"));
				libro.setLengua(rs.getString("lengua"));
				libro.setEdicion(rs.getInt("edicion"));
				libro.setFecha_edicion(rs.getDate("fecha_edicion"));
				libro.setFecha_impresion(rs.getDate("fecha_impresion"));
				
				libros.add(libro);
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		
	 
		return libros;
	}
	
	private String buildSearchLibrosQuery(String autor, String titulo) {
		
		if ((autor==null) && (titulo==null))
			throw new BadRequestException("El titulo y el autor no pueden estar vacios");
		else if (titulo==null)
			return "select * from libros where autor like ?";
		else if (autor==null)
			return "select * from libros where tiulo like ?";
		else 
			return"select * from libros where titulo like ? and autor like ?";
	}
	

	@GET
	@Path("/{idlibro}")
	@Produces(MediaType.LIBROS_API_LIBRO)
	public Libro getLibroById(@PathParam("idlibro") String idlibro,
			@Context Request request) {
		// Create CacheControl
		//CacheControl cc = new CacheControl();
		
		Libro libro= getLibroFromDatabase(Integer.parseInt(idlibro));
		// Calculate the ETag on last modified date of user resource
		//EntityTag eTag = new EntityTag(Long.toString(libro.getLastModified()));
	 
		// Verify if it matched with etag available in http request
		//Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);
	 
		// If ETag matches the rb will be non-null;
		// Use the rb to return the response without any further processing
//		if (rb != null) {
//			return rb.cacheControl(cc).tag(eTag).build();
//		}
//	 
		// If rb is null then either it is first time request; or resource is
		// modified
		// Get the updated representation and return with Etag attached to it
		//rb = Response.ok(libro).cacheControl(cc).tag(eTag);
	 
		return libro;
	}
	
	private String buildGetLibroByIdQuery() {
		return "select * from libros where idlibro=?";
	}
	
//	@GET
//	@Path("/{idlibro}")
//	@Produces(MediaType.LIBROS_API_LIBRO)
//	public Libro getLibro(@PathParam("idlibro") int idlibro,
//			@Context Request req) {
//
//		Libro libro = new Libro();
//
//		Connection conn = null;
//		Statement stmt = null;
//		try {
//			conn = ds.getConnection();
//		} catch (SQLException e) {
//			throw new ServiceUnavailableException(e.getMessage());
//		}
//
//		try {
//			stmt = conn.createStatement();
//			String sql = "SELECT * FROM libros WHERE idlibro='" + idlibro + "'";
//			ResultSet rs = stmt.executeQuery(sql);
//
//			if (rs.next()) {
//
//				libro.setIdlibro(rs.getInt("idlibro"));
//				libro.setTitulo(rs.getString("titulo"));
//				libro.setAutor(rs.getString("autor"));
//				libro.setEditorial(rs.getString("editorial"));
//				libro.setLengua(rs.getString("lengua"));
//				libro.setEdicion(rs.getInt("edicion"));
//				libro.setFecha_edicion(rs.getDate("fecha_edicion"));
//				libro.setFecha_impresion(rs.getDate("fecha_impresion"));
////				libro.add(LibrosAPILinkBuilder.buildURILibroId(uriInfo,
////						rs.getString("idlibro"), rel));
//
//			}
//
//			else
//				throw new LibroNotFoundException();
//
//		} catch (SQLException e) {
//			throw new InternalServerException(e.getMessage());
//		}
//
//		finally {
//			try {
//				stmt.close();
//				conn.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//		return libro;	
//		
//	}
	
	
	@POST
	@Consumes(MediaType.LIBROS_API_LIBRO)
	@Produces(MediaType.LIBROS_API_LIBRO)
	public Libro createLibro(Libro libro) {
		validateLibro(libro);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			String sql = buildInsertLibro();
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, libro.getTitulo());
			stmt.setString(2, libro.getAutor());
			stmt.setString(3, libro.getLengua());
			stmt.setInt(4, libro.getEdicion());
			stmt.setDate(5, libro.getFecha_edicion());
			stmt.setDate(6, libro.getFecha_impresion());
			stmt.setString(7, libro.getEditorial());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int idlibro = rs.getInt(1);

				libro = getLibroFromDatabase(idlibro);
			} else {
				throw new BadRequestException("Ha pasado algo inesperado busca la solucion");
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return libro;
	}
	private void validateLibro(Libro libro) {
		if (libro.getAutor() == null)
			throw new BadRequestException("Autor can't be null.");
		else if (libro.getAutor().length() > 100)
			throw new BadRequestException("Autor can't be greater than 100 characters.");
		else if (libro.getLengua() == null)
			throw new BadRequestException("Lengua can't be null.");
		else if (libro.getLengua().length() > 100)
			throw new BadRequestException("Lengua can't be greater than 100 characters.");
		else if (libro.getTitulo() == null)
			throw new BadRequestException("Titulo can't be null.");
		else if (libro.getTitulo().length() > 100)
			throw new BadRequestException("Titulo can't be greater than 100 characters.");
		else if (libro.getEditorial() == null)
			throw new BadRequestException("Editorial can't be null.");
		else if (libro.getEditorial().length() > 10)
			throw new BadRequestException("Editorial can't be greater than 100 characters.");
		}
	private String buildInsertLibro() {
		return "insert into libros (titulo, autor, lengua, edicion, fecha_edicion, fecha_impresion, editorial) values(?, ?, ?, ?, ?, ?, ?)";
	}

	@DELETE
	@Path("/{stingid}")
	public void deleteLibro(@PathParam("stingid") int stingid) {
		//validateUser(stingid);
		if (security.isUserInRole("registered")) {

			{
				throw new NotAllowedException();
			}
		} else {
			Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServerErrorException("Could not connect to the database",
						Response.Status.SERVICE_UNAVAILABLE);
			}
		 
			PreparedStatement stmt = null;
			try {
				String sql = buildDeleteSting();
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, stingid);
		 
				int rows = stmt.executeUpdate();
				if (rows == 0)				
					throw new NotFoundException("There's no libro with idlibro="
						+ stingid);// Deleting inexistent sting
			} catch (SQLException e) {
				throw new ServerErrorException(e.getMessage(),
						Response.Status.INTERNAL_SERVER_ERROR);
			} finally {
				try {
					if (stmt != null)
						stmt.close();
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	 
	private String buildDeleteSting() {
		return "delete from libros where idlibro=?";
	}
	
	@PUT
	@Path("/{idlibro}")
	@Consumes(MediaType.LIBROS_API_LIBRO)
	@Produces(MediaType.LIBROS_API_LIBRO)
	public Libro updateLibro(@PathParam("idlibro") int idlibro, Libro libro) {
		//validateUpdateLibro(libro);
		if (security.isUserInRole("registered")) {

			{
				throw new NotAllowedException();
			}
		} else {
			Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServerErrorException("Could not connect to the database",
						Response.Status.SERVICE_UNAVAILABLE);
			}
	
			PreparedStatement stmt = null;
			try {
				String sql = buildUpdateLibro();

				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "'"+libro.getAutor()+"'");
				stmt.setString(2, "'"+libro.getTitulo()+"'");
				stmt.setString(3, "'"+libro.getLengua()+"'");
				stmt.setInt(4, libro.getEdicion());
				stmt.setDate(5, libro.getFecha_edicion());
				stmt.setDate(6, libro.getFecha_impresion());
				stmt.setString(7, "'"+libro.getEditorial()+"'");
				stmt.setInt(8, idlibro);
				stmt.executeUpdate();

				int rows = stmt.executeUpdate();
				if (rows == 1)
					libro = getLibroFromDatabase(idlibro);
				else {
					throw new NotFoundException("There's no sting with stingid="
							+ idlibro);
				}
	
			} catch (SQLException e) {
				throw new ServerErrorException(e.getMessage(),
						Response.Status.INTERNAL_SERVER_ERROR);
			} finally {
				try {
					if (stmt != null)
						stmt.close();
					conn.close();
				} catch (SQLException e) {
					
				}
			}
	
			return libro;
		}
	}

//	private void validateUpdateSting(Sting sting) {
//		
//		if (sting.getSubject()!=null && sting.getSubject().length() > 100)
//			throw new BadRequestException(
//					"Subject can't be greater than 100 characters.");
//		if (sting.getContent()!=null && sting.getContent().length() > 500)
//			throw new BadRequestException(
//					"Content can't be greater than 500 characters.");
//	}
	 
	private String buildUpdateLibro() {
		System.out.println("entramos en la query");
		return "update libros set autor=ifnull(?, autor), titulo=ifnull(?, titulo), lengua=ifnull(?, lengua), edicion=ifnull(?, edicion), fecha_edicion=ifnull(?, fecha_edicion), fecha_impresion=ifnull(?, fecha_impresion), editorial=ifnull(?, editorial) where idlibro=?";
	}
	
//	private void validateUser(String stingid) {
//		Sting currentSting = getStingFromDatabase(stingid);
//		if (!security.getUserPrincipal().getName()
//				.equals(currentSting.getUsername()))
//			throw new ForbiddenException(
//					"You are not allowed to modify this sting.");
//	}

}
