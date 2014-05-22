package edu.upc.eetac.dsa.emeyerson.libros.model;

import java.util.ArrayList;
import java.util.List;

//import edu.upc.eetac.dsa.emeyerson.libros.api.links.Link;

public class User {

	private String username;
	private String email;
	private String name;

//	private List<Link> links = new ArrayList<Link>();

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
