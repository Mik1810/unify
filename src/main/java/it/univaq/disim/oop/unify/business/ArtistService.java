package it.univaq.disim.oop.unify.business;

import java.util.Set;

import it.univaq.disim.oop.unify.business.exceptions.ArtistNotFoundException;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Band;
import it.univaq.disim.oop.unify.domain.IndividualArtist;

public interface ArtistService {

	Set<Artist> getAllArtists() throws BusinessException;

	Set<Artist> findArtistsByName(String name) throws ArtistNotFoundException;

	String getBiographyFromArtist(Artist artist) throws BusinessException;

	void addIndividualArtist(IndividualArtist artist) throws BusinessException;

	Band addBand(Band band) throws BusinessException;

	void addMembersToABand(Band band, String name) throws BusinessException;

	void deleteArtist(Artist artist) throws BusinessException;

	void changeName(Artist artist, String name) throws BusinessException;
}