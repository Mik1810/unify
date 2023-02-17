package it.univaq.disim.oop.unify.business.impl.ram;

import java.util.HashSet;
import java.util.Set;

import it.univaq.disim.oop.unify.business.PersonService;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.business.exceptions.PersonNotFoundException;
import it.univaq.disim.oop.unify.domain.Administrator;
import it.univaq.disim.oop.unify.domain.Person;
import it.univaq.disim.oop.unify.domain.User;

public class RAMPersonServiceImpl implements PersonService {

	/*
	 * IMPLEMENTATION: person
	 */

	private Set<User> users = new HashSet<>();
	private Set<Administrator> administrators = new HashSet<>();
	private static int id = 1;

	public RAMPersonServiceImpl() {

		Administrator admin = new Administrator();
		admin.setId(0);
		admin.setUsername("admin");
		admin.setPassword("admin");
		administrators.add(admin);

		User michael = new User();
		michael.setId(id++);
		michael.setUsername("mik");
		michael.setPassword("mik18");
		users.add(michael);

		User alessia = new User();
		alessia.setId(id++);
		alessia.setUsername("alessia");
		alessia.setPassword("alessia28");
		users.add(alessia);

		User franco = new User();
		franco.setId(id++);
		franco.setUsername("franco");
		franco.setPassword("argo29");
		users.add(franco);

		User gioia = new User();
		gioia.setId(id++);
		gioia.setUsername("gioia");
		gioia.setPassword("gio03");
		users.add(gioia);

		User presidente = new User();
		presidente.setId(id++);
		presidente.setUsername("presidente");
		presidente.setPassword("pres03");
		users.add(presidente);

		User rick = new User();
		rick.setId(id++);
		rick.setUsername("rick");
		rick.setPassword("roll");
		users.add(rick);
	}

	@Override
	public Person authenticate(String username, String password) throws PersonNotFoundException, BusinessException {
		for (Administrator administrator : administrators) {
			if ("admin".equalsIgnoreCase(username) && "admin".equalsIgnoreCase(password)) {
				return administrator;
			}
		}
		for (User user : users) {
			if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equalsIgnoreCase(password)) {
				return user;
			}
		}
		throw new PersonNotFoundException();

	}

	@Override
	public Person register(String username, String password) throws BusinessException {

		// Search if the user already exist or if the username is equal to "admin"
		if("admin".equals(username)) throw new BusinessException("You cannot create a user with this username!");
		for(User user : users) {
			if(user.getUsername().equals(username)) {
				throw new BusinessException("This user already exists!");
			}
		}
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setId(id++);
		users.add(user);

		return user;
	}

	@Override
	public Set<Administrator> getAllAdministrator() throws BusinessException {
		if(administrators.isEmpty()) throw new BusinessException("The list of admins is empty!");
		return administrators;
	}

	@Override
	public Set<User> getAllUsers() throws BusinessException {
		if(users.isEmpty()) throw new BusinessException("The list of users is empty!");
		return users;
	}

	@Override
	public void deleteUser(User theUser) throws BusinessException {
		if (theUser == null) throw new BusinessException("Unable to delete the user!");
		for (User user : users) {
			if (theUser.getId() == user.getId()) {
				users.remove(user);
				break;
			}
		}
	}

	@Override
	public void modifyUsername(User user, String username) throws BusinessException {
		if(user == null || username == "" || username == null) throw new BusinessException("Unable to set the new username!");
		user.setUsername(username);

	}

	@Override
	public void modifyPassword(User user, String password) throws BusinessException {
		if(user == null || password == "" || password == null) throw new BusinessException("Unable to set the new password!");
		user.setPassword(password);

	}

	@Override
	public String getUsername(Person person) throws BusinessException {
		if(person == null) throw new BusinessException();
		return person.getUsername();
	}

	@Override
	public String getPassword(Person person) throws BusinessException {
		if(person == null) throw new BusinessException();
		return person.getPassword();
	}

	@Override
	public void modifyAccount(User user, String username, String password) throws BusinessException {
		if(user == null || password == "" || password == null || 
		   username == "" || username == null) throw new BusinessException("Unable to set the new password!");
		user.setPassword(password);
		user.setUsername(username);
	}
}