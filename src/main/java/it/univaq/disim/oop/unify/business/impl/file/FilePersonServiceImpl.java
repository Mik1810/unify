package it.univaq.disim.oop.unify.business.impl.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import it.univaq.disim.oop.unify.business.PersonService;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.business.exceptions.PersonNotFoundException;
import it.univaq.disim.oop.unify.domain.Administrator;
import it.univaq.disim.oop.unify.domain.Person;
import it.univaq.disim.oop.unify.domain.User;

public class FilePersonServiceImpl implements PersonService {

	private String peopleFileName;

	public FilePersonServiceImpl(String peopleFileName) {
		this.peopleFileName = peopleFileName;

	}

	@Override
	public Set<User> getAllUsers() throws BusinessException {

		Set<User> users = new HashSet<>();

		try {
			FileData fileData = FileUtility.readAllRows(peopleFileName);
			for (String[] columns : fileData.getRows()) {
				if ("user".equals(columns[3])) {
					User user = new User();
					user.setId(Integer.parseInt(columns[0]));
					user.setUsername(columns[1]);
					user.setPassword(columns[2]);
					users.add(user);
				}
			}
			if(users.isEmpty()) throw new BusinessException("The list of users is empty!");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return users;
	}

	@Override
	public Set<Administrator> getAllAdministrator() throws BusinessException {

		Set<Administrator> administrators = new HashSet<>();

		try {
			FileData fileData = FileUtility.readAllRows(peopleFileName);
			for (String[] columns : fileData.getRows()) {
				if ("admin".equals(columns[3])) {
					Administrator administrator = new Administrator();
					administrator.setId(0);
					administrator.setUsername(columns[1]);
					administrator.setPassword(columns[2]);
					administrators.add(administrator);
				}
			}
			if(administrators.isEmpty()) throw new BusinessException("The list of admins is empty!");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return administrators;
	}

	@Override
	public void deleteUser(User theUser) throws BusinessException {

		// Delete the row from the file

		try {
			FileData fileData = FileUtility.readAllRows(peopleFileName);
			try (PrintWriter writer = new PrintWriter(new File(peopleFileName))) {
				writer.println(fileData.getCounter());
				for (String[] rows : fileData.getRows()) {
					if (Integer.parseInt(rows[0]) != theUser.getId()) {
						writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
					}
				}

			}
		} catch (IOException e) {
			throw new BusinessException("Unable to delete the user!");
		}

	}
	
	@Override
	public Person authenticate(String username, String password) throws PersonNotFoundException, BusinessException {

		try {
			FileData fileData = FileUtility.readAllRows(peopleFileName);
			for (String[] columns : fileData.getRows()) {
				if (columns[1].equals(username) && columns[2].equals(password)) {
					Person person = null;
					switch (columns[3]) {
					case "admin":
						person = new Administrator();
						break;
					case "user":
						person = new User();
						break;
					default:
						break;
					}
					if (person != null) {
						person.setId(Integer.parseInt(columns[0]));
						person.setUsername(username);
						person.setPassword(password);

					} else {
						throw new BusinessException("Error reading the users file");
					}
					return person;
				}
			}
			throw new PersonNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public void modifyUsername(User user, String username) throws BusinessException {
		try {
			FileData fileData = FileUtility.readAllRows(peopleFileName);
			try (PrintWriter writer = new PrintWriter(new File(peopleFileName))) {
				writer.println(fileData.getCounter());
				for (String[] rows : fileData.getRows()) {
					if (Integer.parseInt(rows[0]) == user.getId()) {
						StringBuilder row = new StringBuilder();
						row.append(user.getId());
						row.append(FileUtility.COLUMN_SEPARATOR);
						row.append(username);
						row.append(FileUtility.COLUMN_SEPARATOR);
						row.append(user.getPassword());
						row.append(FileUtility.COLUMN_SEPARATOR);
						row.append("user");
						writer.println(row.toString());
					} else {
						writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
					}
				}

			}
		} catch (IOException e) {
			throw new BusinessException("Unable to set the new username!");
		}

	}

	@Override
	public void modifyPassword(User user, String password) throws BusinessException {
		try {
			FileData fileData = FileUtility.readAllRows(peopleFileName);
			try (PrintWriter writer = new PrintWriter(new File(peopleFileName))) {
				writer.println(fileData.getCounter());
				for (String[] rows : fileData.getRows()) {
					if (Integer.parseInt(rows[0]) == user.getId()) {
						StringBuilder row = new StringBuilder();
						row.append(user.getId());
						row.append(FileUtility.COLUMN_SEPARATOR);
						row.append(user.getUsername());
						row.append(FileUtility.COLUMN_SEPARATOR);
						row.append(password);
						row.append(FileUtility.COLUMN_SEPARATOR);
						row.append("user");
						writer.println(row.toString());
					} else {
						writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
					}
				}

			}
		} catch (IOException e) {
			throw new BusinessException("Unable to set the new password!");
		}

	}

	@Override
	public Person register(String username, String password) throws BusinessException {
		
		/* Controls: if the username is equals to "admin"
		 * 			 if the username already exists
		 */
		
		User user = new User();
		
		try {
			if("admin".equals(username)) throw new BusinessException("You cannot create a user with this username!");
			FileData fileData = FileUtility.readAllRows(peopleFileName);
			for (String[] columns : fileData.getRows()) {
				if (columns[1].equals(username)) {
					throw new BusinessException("This user already exists!");
				}
			}
			
			//Here I have to write down the new user
			try (PrintWriter writer = new PrintWriter(new File(peopleFileName))) {
				long counter = fileData.getCounter();
				writer.println((counter + 1));
				for (String[] rows : fileData.getRows()) {
					writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
				}
				StringBuilder row = new StringBuilder();
				// 2,alessia,alessia28,user
				row.append(counter);
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append(username);
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append(password);
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append("user");
				writer.println(row.toString());
				
				user.setId((int)counter);
				user.setUsername(username);
				user.setPassword(password);
				
			}
		} catch(IOException e){
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public String getUsername(Person person) throws BusinessException {
		if(person == null) throw new BusinessException();
		try {
			FileData fileData = FileUtility.readAllRows(peopleFileName);
			for (String[] columns : fileData.getRows()) {
				if (person.getId() == Integer.parseInt(columns[0])) {
					return columns[1];
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new PersonNotFoundException();
	}

	@Override
	public String getPassword(Person person) throws BusinessException {
		if(person == null) throw new BusinessException();
		try {
			FileData fileData = FileUtility.readAllRows(peopleFileName);
			for (String[] columns : fileData.getRows()) {
				if (person.getId() == Integer.parseInt(columns[0])) {
					return columns[2];
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new PersonNotFoundException();
	}

	@Override
	public void modifyAccount(User user, String username, String password) throws BusinessException {
		try {
			FileData fileData = FileUtility.readAllRows(peopleFileName);
			try (PrintWriter writer = new PrintWriter(new File(peopleFileName))) {
				writer.println(fileData.getCounter());
				for (String[] rows : fileData.getRows()) {
					if (Integer.parseInt(rows[0]) == user.getId()) {
						StringBuilder row = new StringBuilder();
						row.append(user.getId());
						row.append(FileUtility.COLUMN_SEPARATOR);
						row.append(username);
						row.append(FileUtility.COLUMN_SEPARATOR);
						row.append(password);
						row.append(FileUtility.COLUMN_SEPARATOR);
						row.append("user");
						writer.println(row.toString());
					} else {
						writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
					}
				}
			}
		} catch (IOException e) {
			throw new BusinessException("Unable to set the new password!");
		}
	}
}