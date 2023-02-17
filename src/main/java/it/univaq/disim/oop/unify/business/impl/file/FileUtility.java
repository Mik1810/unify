package it.univaq.disim.oop.unify.business.impl.file;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import it.univaq.disim.oop.unify.Utility;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Album;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Song;

public class FileUtility {

	public static final String COLUMN_SEPARATOR = ",";
	public static final String END_ROW = "@";
	public static int artistCounter = 1;

	// removes the space from an array of string
	public static final String[] trim(String[] s) {
		for (int i = 0; i < s.length; i++) {
			s[i] = s[i].trim();
		}
		return s;
	}

	public static FileData readAllRows(String filename) throws IOException {

		FileData result = new FileData();

		try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)))) {
			List<String[]> rows = new ArrayList<>();
			long counter = Long.parseLong(scanner.nextLine());
			result.setCounter(counter);
			while (scanner.hasNextLine()) {
				rows.add(trim(scanner.nextLine().split(COLUMN_SEPARATOR)));
			}
			result.setRows(rows);
		}

		return result;

		/*
		 * FileData result = new FileData();
		 * 
		 * try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
		 * List<String[]> rows = new ArrayList<>(); long counter =
		 * Long.parseLong(in.readLine()); result.setCounter(counter); String line =
		 * null; while ((line = in.readLine()) != null) {
		 * rows.add(trim(line.split(COLUMN_SEPARATOR))); } result.setRows(rows); }
		 * 
		 * return result;
		 */
	}
	
	//returns the name of the image
	public static String createImage(byte[] content, Object obj) throws BusinessException {
		try {
			InputStream is = new ByteArrayInputStream(content);
			BufferedImage newBi = ImageIO.read(is);
			if(obj instanceof Artist) {
				Artist artist = (Artist)obj;
				//src/main/resources/images/artists/pippo-1.jpg
				String fileName = artist.getName() + "-" + artistCounter + ".jpg";
				ImageIO.write(newBi, "jpg", new File(Utility.IMAGE_ARTIST_PATH + fileName));
				artistCounter++;
				return fileName;
			} else if (obj instanceof Album) {
				Album album = (Album)obj;
				//src/main/resources/images/songs/pluto Cover.jpg
				String fileName = album.getTitle() + " Cover.jpg";
				ImageIO.write(newBi, "jpg", new File(Utility.IMAGE_SONG_PATH + fileName));
				return fileName;
			} else {
				Song song = (Song)obj;
				//src/main/resources/images/songs/pluto Cover.jpg
				String fileName = song.getTitle() + " Cover.jpg";
				ImageIO.write(newBi, "jpg", new File(Utility.IMAGE_SONG_PATH + fileName));
				return fileName;
			}
		} catch (IOException e) {
			throw new BusinessException("Unable to create the image!");
		}
	}

	public static void deleteImage(String path) throws BusinessException {
		File toDelete = new File(path);
		if (!toDelete.delete()) {
			throw new BusinessException("Unable to delete the old photo!");
		}
	}

	public static String readTextFile(String path, int type) throws BusinessException {
		String text = "";

		// 0 is for biography, 1 is for lyrics
		try {
			if (type == 0) {
				BufferedReader reader = new BufferedReader(new FileReader(Utility.BIOGRAPHY_PATH + path));
				String line;
				while ((line = reader.readLine()) != null) {
					text = text + line;
				}
				reader.close();
			} else {
				BufferedReader reader = new BufferedReader(new FileReader(Utility.LYRICS_PATH + path));
				String line;
				while ((line = reader.readLine()) != null) {
					text = text + line + "\n";
				}
				reader.close();
			}
		} catch (FileNotFoundException e) {
			throw new BusinessException("The text file doesn't exist");
		} catch (IOException e) {
			throw new BusinessException("Unable to read the text file");
		}
		return text;
	}
	
	public static void createSongFile(Song song) throws BusinessException {
		try {
				File songFile = new File(Utility.MUSIC_REPOSITORY + song.getTitle() + Utility.MP3_SUFFIX);
				if(songFile.createNewFile()) {
					FileOutputStream fos = new FileOutputStream(songFile);
					fos.write(song.getContent());
					fos.close();
				}
		} catch (IOException e) {
			throw new BusinessException("Unable to create the song file!");
		}
	}
}
