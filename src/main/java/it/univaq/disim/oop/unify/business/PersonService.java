package it.univaq.disim.oop.unify.business;

import java.util.Set;

import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.business.exceptions.PersonNotFoundException;
import it.univaq.disim.oop.unify.domain.Administrator;
import it.univaq.disim.oop.unify.domain.Person;
import it.univaq.disim.oop.unify.domain.User;

public interface PersonService {
	
	String getUsername(Person person) throws BusinessException;
	
	String getPassword(Person person) throws BusinessException;
	
	Set<User> getAllUsers() throws BusinessException;
	
	Set<Administrator> getAllAdministrator() throws BusinessException;
	
	void deleteUser(User user) throws BusinessException;
	
	void modifyUsername(User user, String username) throws BusinessException;
	
	void modifyPassword(User user, String password) throws BusinessException;
	
	//for concurrency issues in writing the file
	void modifyAccount(User user, String username, String password) throws BusinessException;
		
	Person authenticate(String username, String password) throws PersonNotFoundException, BusinessException;
	
	Person register(String username, String password) throws BusinessException;

}