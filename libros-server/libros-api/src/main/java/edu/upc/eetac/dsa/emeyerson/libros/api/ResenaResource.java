package edu.upc.eetac.dsa.emeyerson.libros.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;






//import edu.upc.eetac.dsa.emeyerson.libros.api.links.LibrosAPILinkBuilder;
//import edu.upc.eetac.dsa.emeyerson.libros.api.links.Link;
import edu.upc.eetac.dsa.emeyerson.libros.model.*;

@Path("/libros/{idlibro}/resenas")
public class ResenaResource {

	@Context
	private SecurityContext security;

	@Context
	private UriInfo uriInfo;

	LibrosRootAPI root = new LibrosRootAPI();
	String rel = null;

	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	private Resena getResenaFromDatabase(int idresena) {
		Resena resena = new Resena();
	 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(buildGetResenaByIdQuery());

			stmt.setInt(1, idresena);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				resena.setIdlibro(rs.getInt("idlibro"));
				resena.setIdresena(rs.getInt("idresena"));
				resena.setName(rs.getString("name"));
				resena.setUsername(rs.getString("username"));
				resena.setTexto(rs.getString("texto"));
				resena.setFecha_creacion(rs.getDate("fecha_creacion"));
//				libro.setLastModified(rs.getTimestamp("last_modified")
//						.getTime());
			} else {
				throw new NotFoundException("There's no libro with idlibro="
						+ idresena);
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
	 
		return resena;
	}
	
	private String buildGetResenaByIdQuery() {
		return "select resenas.*, users.name from users inner join resenas on(idresena=? and users.username=resenas.username) ";
	}
	

	@GET
	@Produces(MediaType.LIBROS_API_RESENA_COLLECTION)
	public ResenaCollection getReseñas(@PathParam("idlibro") int idlibro) {
		ResenaCollection resenas = new ResenaCollection();

		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}

		try {

//			ResultSet rs = stmt.executeQuery(sql);
			stmt = conn.prepareStatement(buildGetResenasQuery());
			stmt.setInt(1, idlibro);
			ResultSet rs = stmt.executeQuery();
			if (rs.next() == false) {
				throw new BadRequestException("Reseña no encontrada");

			}

			else {
				rs.previous();
				while (rs.next()) {
					Resena resena = new Resena();
					resena.setIdlibro(rs.getInt("idlibro"));
					resena.setIdresena(rs.getInt("idresena"));
					resena.setName(rs.getString("name"));
					resena.setUsername(rs.getString("username"));
					resena.setTexto(rs.getString("texto"));
					resena.setFecha_creacion(rs.getDate("fecha_creacion"));

//					resena.add(LibrosAPILinkBuilder.buildURIResena(uriInfo,
//							rs.getString("idresena"), rs.getString("idlibro"),
//							rel));
//
//					List<Link> links = new ArrayList<Link>();
//					links.add(LibrosAPILinkBuilder.buildURIResenas(uriInfo,
//							rs.getString("idlibro"), rel));
//
//					resenas.setLinks(links);

					resenas.add(resena);
				}
			}
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		}

		finally {
			try {
				stmt.close();
				conn.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resenas;
	}
	
	private String buildGetResenasQuery() {
		
		return "SELECT resenas.*, users.name FROM users INNER JOIN resenas ON(idlibro= ? and users.username=resenas.username) ";
	}


	@POST
	@Consumes(MediaType.LIBROS_API_RESENA)
	@Produces(MediaType.LIBROS_API_RESENA)
	public Resena createResena(@PathParam("idlibro") int idlibro,
			Resena resena) {
		String username = security.getUserPrincipal().getName();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}

		try {
			stmt = conn.prepareStatement(buildGetLibroByIdQuery());		
			stmt.setInt(1, idlibro);
			rs = stmt.executeQuery();
			if (rs.first() == false) {
				throw new BadRequestException("Libro no encontrado");
			}

			else {

				try {
					stmt = conn.prepareStatement(buildComprobarSiYaHanHechoResena());
					stmt.setInt(1, idlibro);
					stmt.setString(2, username);
					rs = stmt.executeQuery();
					if (rs.next() == true) {
						throw new NotAllowedException();
						// EL usuario ya tiene una reseña para el libro
					}

					else {
						try {
							String sql = builInsertarResena();
							stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
									
							stmt.setInt(1, idlibro);
							stmt.setString(2, username);
							stmt.setString(3, resena.getTexto());
							stmt.executeUpdate();
							rs = stmt.getGeneratedKeys();

							if (rs.next()) {

								int idresena= rs.getInt(1);

								resena = getResenaFromDatabase(idresena);
								}
							else
								throw new BadRequestException("Reseña no encontrada");
						} catch (SQLException e) {
							throw new InternalServerException(e.getMessage());
						} finally {
							try {
								stmt.close();
								conn.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}

				catch (SQLException e) {
					throw new InternalServerException(e.getMessage());
				}
			}
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		}

		return resena;
	}
	
	private String buildGetLibroByIdQuery() {
		return "select * from libros where idlibro=?";
	}

	private String buildComprobarSiYaHanHechoResena(){
		return "SELECT * FROM resenas WHERE idlibro=? and username=?";
	}
	
	private String builInsertarResena(){
		
		return "INSERT INTO resenas (idlibro,username,texto) VALUES (?, ?, ?)";
	}
	
	
	@DELETE
	@Path("/{idresena}")
	public void deleteResena(@PathParam("idresena") int idresena) {
		String username;
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql;
		ResultSet rs;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}

		try {
			stmt = conn.prepareStatement(buildGetResenaByIdQuery());
			stmt.setInt(1, idresena);
			rs = stmt.executeQuery();

			if (rs.next() == false) {
				throw new BadRequestException("Reseña no encontrada");

			} else {
				username = rs.getString("username");

				if (security.getUserPrincipal().getName().equals(username)) {

					try {
						stmt = conn.prepareStatement(buildDeleteLibroByIdQuery());
						stmt.setInt(1, idresena);
						stmt.setString(2, username);

						stmt.executeUpdate();
					} catch (SQLException e) {
						throw new InternalServerException(e.getMessage());
					} finally {
						try {
							stmt.close();
							conn.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}

				else {
					throw new NotAllowedException();
				}
			}

		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());

		}
	}

	private String buildDeleteLibroByIdQuery() {
		return "DELETE FROM resenas WHERE idresena=? and username=?";
	}

	
	@PUT
	@Path("/{idresena}")
	@Consumes(MediaType.LIBROS_API_RESENA)
	@Produces(MediaType.LIBROS_API_RESENA)
	public Resena updateResena(@PathParam("idresena") int idresena,
			Resena resena) {

		String username;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}

		try {
			stmt = conn.prepareStatement(buildGetResenaByIdQuery());
			stmt.setInt(1, idresena);
			rs = stmt.executeQuery();
			rs.next();
			//Cogemos el valor de Usuario para ver si esta en poder de actualizar la reseña;
			username = rs.getString("username");

			if (security.getUserPrincipal().getName().equals(username)) {
				try {
					stmt = conn.prepareStatement(buildUpdateResenaByIdQuery());
					stmt.setString(1, resena.getTexto());
					stmt.setInt(2, idresena);
					//update,Statement.RETURN_GENERATED_KEYS
					stmt.executeUpdate();
					//rs = stmt.getGeneratedKeys();
					int rows = stmt.executeUpdate();
					if (rows == 1)
						resena = getResenaFromDatabase(idresena);
					else 
						throw new NotFoundException("There's no resena with idresena="+ idresena);
				} catch (SQLException e) {
					throw new InternalServerException(e.getMessage());
				} finally {
					try {
						stmt.close();
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}

			else {
				throw new NotAllowedException();

			}
		} catch (SQLException e) {
			throw new BadRequestException("Reseña no encontrada");
		}

		return resena;

	}
	
	private String buildUpdateResenaByIdQuery() {
		
		return "UPDATE resenas SET texto =? WHERE idresena=?";
	}

}
