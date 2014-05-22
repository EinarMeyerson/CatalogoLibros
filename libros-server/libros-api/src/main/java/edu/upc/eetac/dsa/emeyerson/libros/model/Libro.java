package edu.upc.eetac.dsa.emeyerson.libros.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.emeyerson.libros.api.MediaType;
import edu.upc.eetac.dsa.emeyerson.libros.api.LibroResource;
import edu.upc.eetac.dsa.emeyerson.libros.api.ResenaResource;
import edu.upc.eetac.dsa.emeyerson.libros.model.Resena;

public class Libro {
	@InjectLinks({
		@InjectLink(resource = LibroResource.class, style = Style.ABSOLUTE, rel = "libros", title = "Latest libros", type = MediaType.LIBROS_API_LIBRO_COLLECTION),
		@InjectLink(resource = LibroResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "Libro", type = MediaType.LIBROS_API_LIBRO, method = "getLibroById", bindings = @Binding(name = "idlibro", value = "${instance.idlibro}"))})
	private List<Link> links;
	private String titulo;
	private String autor;
	private String lengua;
	private int edicion;
	private Date fecha_edicion;
	private Date fecha_impresion;
	private String editorial;
	private int idlibro;
	@InjectLinks({
		@InjectLink(resource = ResenaResource.class, style = Style.ABSOLUTE, rel = "resenas-libro", title = "Resenas del libro", type = MediaType.LIBROS_API_RESENA_COLLECTION, method = "getReseñas", bindings = @Binding(name = "idlibro", value = "${instance.idlibro}"))})
	private List<Link> resenas;
	public String getAutor() {
		return autor;
	}

	public List<Link> getResenas() {
		return resenas;
	}

	public void setResenas(List<Link> resenas) {
		this.resenas = resenas;
	}
	
//	public void add(Resena resena) {
//	resenas.add(resena);
//	}
//	
	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getEdicion() {
		return edicion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getLengua() {
		return lengua;
	}

	public void setLengua(String lengua) {
		this.lengua = lengua;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public Date getFecha_edicion() {
		return fecha_edicion;
	}

	public void setEdicion(int edicion) {
		this.edicion = edicion;
	}

	public void setFecha_edicion(Date fecha_edicion) {
		this.fecha_edicion = fecha_edicion;
	}

	public Date getFecha_impresion() {
		return fecha_impresion;
	}

	public void setFecha_impresion(Date fecha_impresion) {
		this.fecha_impresion = fecha_impresion;

	}

	public int getIdlibro() {
		return idlibro;
	}

	public void setIdlibro(int idlibro) {
		
		this.idlibro = idlibro;
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
