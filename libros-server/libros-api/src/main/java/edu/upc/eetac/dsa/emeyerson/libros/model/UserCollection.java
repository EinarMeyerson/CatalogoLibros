package edu.upc.eetac.dsa.emeyerson.libros.model;

import java.util.ArrayList;
import java.util.List;

//import edu.upc.eetac.dsa.emeyerson.libros.api.links.Link;

public class UserCollection {

	private List<User> users = new ArrayList<User>();

	public void add(User user) {
		users.add(user);
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
