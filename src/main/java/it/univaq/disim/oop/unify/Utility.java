package it.univaq.disim.oop.unify;

import java.io.File;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Utility {

	public static final String MUSIC_REPOSITORY = "src" + File.separator + "main" + File.separator + "resources"
			+ File.separator + "music" + File.separator;

	public static final String MP3_SUFFIX = ".mp3";

	public static final String IMAGE_ARTIST_PATH = "src" + File.separator + "main" + File.separator + "resources"
			+ File.separator + "images" + File.separator + "artists" + File.separator;

	public static final String IMAGE_SONG_PATH = "src" + File.separator + "main" + File.separator + "resources"
			+ File.separator + "images" + File.separator + "songs" + File.separator;
	public static final String IMAGE_ARTIST_SUFFIX = ".jpg";
	public static final String IMAGE_SONG_SUFFIX = " Cover.jpg";

	public static final String BIOGRAPHY_PATH = "src" + File.separator + "main" + File.separator + "resources"
			+ File.separator + "data" + File.separator + "biographies" + File.separator;

	public static final String BIOGRAPHY_SUFFIX = "Bio.txt";
	public static final String LYRICS_PATH = "src" + File.separator + "main" + File.separator + "resources"
			+ File.separator + "data" + File.separator + "lyrics" + File.separator;
	public static final String LYRICS_SUFFIX = ".txt";
	public static final String ICON_PATH = "scenes" + File.separator + "icons" + File.separator;
	
	public static void roundHomeImageView(ImageView imageView) {
		Rectangle rectangle = new Rectangle(0, 0, 150, 150);
		rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
		imageView.setClip(rectangle);
	}
	
	public static void roundSongImageView(ImageView imageView) {
		Rectangle rectangle = new Rectangle(0, 0, 200, 200);
		rectangle.setArcWidth(30);
        rectangle.setArcHeight(30);
		imageView.setClip(rectangle);
	}
}
