package it.univaq.disim.oop.unify.business.impl.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

import it.univaq.disim.oop.unify.Utility;
import it.univaq.disim.oop.unify.business.ArtistService;
import it.univaq.disim.oop.unify.business.exceptions.ArtistNotFoundException;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Band;
import it.univaq.disim.oop.unify.domain.IndividualArtist;
import it.univaq.disim.oop.unify.domain.Photo;

public class FileArtistServiceImpl implements ArtistService {

	private String artistFileName;

	public FileArtistServiceImpl(String artistFileName) {
		this.artistFileName = artistFileName;

	}

	@Override
	public Set<Artist> getAllArtists() throws BusinessException {

		Set<Artist> result = new HashSet<>();

		// 1,Eminem,Eminem-1.jpg,Eminem-2.jpg,EminemBio.txt,INDIVIDUAL
		try {
			FileData fileData = FileUtility.readAllRows(artistFileName);
			for (String[] columns : fileData.getRows()) {
				if ("INDIVIDUAL".equals(columns[5])) {
					IndividualArtist individualArtist = new IndividualArtist();
					individualArtist.setId(Integer.parseInt(columns[0]));
					individualArtist.setName(columns[1]);
					individualArtist.getPhotos().add(
							new Photo(Files.readAllBytes(new File(Utility.IMAGE_ARTIST_PATH + columns[2]).toPath())));
					individualArtist.getPhotos().add(
							new Photo(Files.readAllBytes(new File(Utility.IMAGE_ARTIST_PATH + columns[3]).toPath())));
					individualArtist.setBiography(getBiographyFromArtist(individualArtist));
					result.add(individualArtist);
				} else if ("BAND".equals(columns[5])) {
					Band band = new Band();
					band.setId(Integer.parseInt(columns[0]));
					band.setName(columns[1]);
					band.getPhotos().add(
							new Photo(Files.readAllBytes(new File(Utility.IMAGE_ARTIST_PATH + columns[2]).toPath())));
					band.getPhotos().add(
							new Photo(Files.readAllBytes(new File(Utility.IMAGE_ARTIST_PATH + columns[3]).toPath())));
					band.setBiography(getBiographyFromArtist(band));

					// 2,U2,U2-1.jpg,U2-2.jpg,U2Bio.txt,BAND,Bono,The Edge,Adam Clayton,Larry Mullen
					// Jr.,@
					for (int i = 6; !("@".equals(columns[i])); i++) {
						Artist member = new Artist();
						member.setName(columns[i]);
						band.getMembers().add(member);
					}
					result.add(band);
				}
			}
			if (result.isEmpty())
				throw new BusinessException("The list of artists is empty!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Set<Artist> findArtistsByName(String name) throws ArtistNotFoundException {

		Set<Artist> result = new HashSet<>();
		try {
			if (name != null && name != "") {

				FileData fileData = FileUtility.readAllRows(artistFileName);
				for (String[] columns : fileData.getRows()) {

					// 1,Eminem,Eminem-1.jpg,Eminem-2.jpg,EminemBio.txt,INDIVIDUAL
					if (columns[1].toLowerCase().contains(name) || columns[1].equalsIgnoreCase(name)) {
						if ("INDIVIDUAL".equals(columns[5])) {
							IndividualArtist individualArtist = new IndividualArtist();
							individualArtist.setId(Integer.parseInt(columns[0]));
							individualArtist.setName(columns[1]);
							individualArtist.getPhotos().add(new Photo(
									Files.readAllBytes(new File(Utility.IMAGE_ARTIST_PATH + columns[2]).toPath())));
							individualArtist.getPhotos().add(new Photo(
									Files.readAllBytes(new File(Utility.IMAGE_ARTIST_PATH + columns[3]).toPath())));
							individualArtist.setBiography(getBiographyFromArtist(individualArtist));
							result.add(individualArtist);

						}
						if ("BAND".equals(columns[5])) {
							Band band = new Band();
							band.setId(Integer.parseInt(columns[0]));
							band.setName(columns[1]);
							band.getPhotos().add(new Photo(
									Files.readAllBytes(new File(Utility.IMAGE_ARTIST_PATH + columns[2]).toPath())));
							band.getPhotos().add(new Photo(
									Files.readAllBytes(new File(Utility.IMAGE_ARTIST_PATH + columns[3]).toPath())));
							band.setBiography(getBiographyFromArtist(band));
							result.add(band);
						}
					}
				}

			} else {
				result.addAll(getAllArtists());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getBiographyFromArtist(Artist artist) throws BusinessException {
		if(artist == null) throw new BusinessException("Invalid artist!");
		String biography = "";
		try {
			FileData fileData = FileUtility.readAllRows(artistFileName);
			for (String[] columns : fileData.getRows()) {
				// 1,Eminem,Eminem-1.jpg,Eminem-2.jpg,EminemBio.txt,INDIVIDUAL
				if (artist.getId() == Integer.parseInt(columns[0])) {
					biography = FileUtility.readTextFile(columns[4], 0);
				}
			}
			return biography;
		} catch (IOException e) {
			throw new BusinessException("Unable to read the artist files!");
		}
	}

	@Override
	public void addIndividualArtist(IndividualArtist artist) throws BusinessException {
		
		if(artist == null) throw new BusinessException("Invalid artist!");
		try {
			FileData fileData = FileUtility.readAllRows(artistFileName);
			try (PrintWriter writer = new PrintWriter(new File(artistFileName))) {
				long counter = fileData.getCounter();
				writer.println((counter + 1));
				for (String[] rows : fileData.getRows()) {
					writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
				}
				StringBuilder row = new StringBuilder();
				// 1,Eminem,Eminem-1.jpg,Eminem-2.jpg,EminemBio.txt,INDIVIDUAL
				row.append(counter);
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append(artist.getName());
				row.append(FileUtility.COLUMN_SEPARATOR);


				for(Photo photo : artist.getPhotos()) {
					row.append(FileUtility.createImage(photo.getContent(),artist));
					row.append(FileUtility.COLUMN_SEPARATOR);
				}
				row.append(artist.getName() + Utility.BIOGRAPHY_SUFFIX);
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append("INDIVIDUAL");
				writer.println(row.toString());

				// I have to create the biography file
				createBiographyFile(artist);

			}
		} catch (IOException e) {
			throw new BusinessException("Unable to create a new artist!");
		}

	}

	@Override
	public Band addBand(Band band) throws BusinessException {
		if(band == null) throw new BusinessException("Invalid artist!");
		try {
			FileData fileData = FileUtility.readAllRows(artistFileName);
			try (PrintWriter writer = new PrintWriter(new File(artistFileName))) {
				long counter = fileData.getCounter();
				writer.println((counter + 1));
				for (String[] rows : fileData.getRows()) {
					writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
				}
				band.setId((int)counter);
				StringBuilder row = new StringBuilder();
				// 2,U2,U2-1.jpg,U2-2.jpg,BAND,Bono,The Edge,Adam Clayton,Larry Mullen Jr.,@
				row.append(counter);
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append(band.getName());
				row.append(FileUtility.COLUMN_SEPARATOR);

				// TODO: Can I pass the next lines to photoService?
				for(Photo photo : band.getPhotos()) {
					row.append(FileUtility.createImage(photo.getContent(),band));
					row.append(FileUtility.COLUMN_SEPARATOR);
				}
				row.append(band.getName() + Utility.BIOGRAPHY_SUFFIX);
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append("BAND");
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append("@");
				writer.print(row.toString());
				// I have to create the biography file
				createBiographyFile(band);
			}
		} catch (IOException e) {
			throw new BusinessException("Unable to create a new artist!");
		}
		return band;
	}

	@Override
	public void addMembersToABand(Band band, String name) throws BusinessException {
		if(band == null) throw new BusinessException("Invalid artist!");
		try {
			FileData fileData = FileUtility.readAllRows(artistFileName);
			try (PrintWriter writer = new PrintWriter(new File(artistFileName))) {
				writer.println(fileData.getCounter());
				for (String[] rows : fileData.getRows()) {
					if (Integer.parseInt(rows[0]) == band.getId()) {
						StringBuilder line = new StringBuilder();
						for (int i = 0; i < rows.length - 1; i++) {
							line.append(rows[i]);
							line.append(FileUtility.COLUMN_SEPARATOR);
						}
						line.append(name);
						line.append(FileUtility.COLUMN_SEPARATOR);
						line.append("@");
						writer.println(line.toString());
					} else {
						writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
					}
				}
			}
		} catch (IOException e) {
			throw new BusinessException("Unable to add members to the band!");
		}
	}

	@Override
	public void deleteArtist(Artist artist) throws BusinessException {
		if(artist == null) throw new BusinessException("Invalid artist!");
		try {
			FileData fileData = FileUtility.readAllRows(artistFileName);
			try (PrintWriter writer = new PrintWriter(new File(artistFileName))) {
				writer.println(fileData.getCounter());
				for (String[] rows : fileData.getRows()) {
					if (artist.getId() != Integer.parseInt(rows[0])) {
						writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
					}
				}
			}
		} catch (IOException e) {
			throw new BusinessException("Unable to delete the artist!");
		}
	}

	@Override
	public void changeName(Artist artist, String name) throws BusinessException {
		if(artist == null) throw new BusinessException("Invalid artist!");
		try {
			FileData fileData = FileUtility.readAllRows(artistFileName);
			try (PrintWriter writer = new PrintWriter(new File(artistFileName))) {
				writer.println(fileData.getCounter());
				// 1,Eminem,Eminem-1.jpg,Eminem-2.jpg,EminemBio.txt,INDIVIDUAL
				for (String[] rows : fileData.getRows()) {
					if (Integer.parseInt(rows[0]) == artist.getId()) {

						rows[1] = name;

						// To change the bio file name
						File file = new File(Utility.BIOGRAPHY_PATH + artist.getName() + Utility.BIOGRAPHY_SUFFIX);
						File file2 = new File(Utility.BIOGRAPHY_PATH + name + Utility.BIOGRAPHY_SUFFIX);
						file.renameTo(file2);

					}
					writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
				}

			}
		} catch (IOException e) {
			throw new BusinessException("Unable to modify the name of the artist!");
		}

	}

	private void createBiographyFile(Artist artist) throws IOException, BusinessException {
		File biographyFile = new File(Utility.BIOGRAPHY_PATH + artist.getName() + Utility.BIOGRAPHY_SUFFIX);
		if (biographyFile.createNewFile()) {
			FileWriter myWriter = new FileWriter(biographyFile);
			myWriter.write(artist.getBiography());
			myWriter.close();
		} else throw new BusinessException("Unable to create the biography file!");
	}
}
