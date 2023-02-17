package it.univaq.disim.oop.unify.musicplayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {

	private static MusicPlayer instance = new MusicPlayer();

	private Media media;
	private MediaPlayer mediaPlayer;

	private PlayerStatus status = PlayerStatus.NOT_RUNNING;
	private PlayingMode mode = PlayingMode.NORMAL;

	private int counter;
	private Random random;
	private List<File> songs = new ArrayList<>();

	private MusicPlayer() {
	}

	public static MusicPlayer getInstance() {
		return instance;
	}

	protected MediaPlayer getPlayer() {
		return mediaPlayer;
	}

	protected Media getMedia() {
		return media;
	}

	protected PlayerStatus getPlayerStatus() {
		return status;
	}

	protected PlayingMode getPlayingMode() {
		return mode;
	}

	protected int getCounter() {
		return counter;
	}

	protected void createMedia() {
		status = PlayerStatus.RUNNING;
		media = new Media(songs.get(counter).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
	}

	protected void playSong() {

		mediaPlayer.play();
		status = PlayerStatus.PLAYING;
	}

	protected void pauseSong() {

		mediaPlayer.pause();
		status = PlayerStatus.RUNNING;
	}

	public void resetThePlayer() {
		if (status != PlayerStatus.NOT_RUNNING) {
			mediaPlayer.stop();
			status = PlayerStatus.NOT_RUNNING;
		}
	}

	public void exit() {
		if (status != PlayerStatus.NOT_RUNNING) {
			mediaPlayer.stop();
			counter = 0;
			status = PlayerStatus.NOT_RUNNING;
		}
	}

	protected void previousSong() {

		mediaPlayer.stop();

		switch (mode) {

		case SHUFFLING:
			// To avoid to loop here infinitely in case of a single song
			if (songs.size() != 1) {
				int index = counter;
				while (counter == index) {
					// To avoid to play the current song
					counter = random.nextInt(songs.size());
				}
				break;
			}

		case NORMAL:
			if (counter == 0) {
				counter = songs.size() - 1;
			} else if (counter > 0) {
				counter--;
			}
			break;

		case REPEATING:
			break;
		}
		createMedia();
		mediaPlayer.play();
		status = PlayerStatus.PLAYING;

	}

	protected void nextSong() {

		mediaPlayer.stop();

		switch (mode) {

		case SHUFFLING:
			// To avoid to loop here infinitely in case of a single song
			if (songs.size() != 1) {
				int index = counter;
				while (counter == index) {
					// To avoid to play the current song
					counter = random.nextInt(songs.size());
				}
				break;
			}

		case NORMAL:
			if (counter == songs.size() - 1) {
				counter = 0;
			} else if (counter != songs.size() - 1) {
				counter++;
			}
			break;

		case REPEATING:
			break;
		}
		createMedia();
		mediaPlayer.play();
		status = PlayerStatus.PLAYING;
	}

	protected void shuffleList(Random random) {
		if (mode != PlayingMode.SHUFFLING) {
			mode = PlayingMode.SHUFFLING;
			this.random = random;
		} else {
			mode = PlayingMode.NORMAL;
		}
	}

	protected void repeatSong() {
		if (mode != PlayingMode.REPEATING) {
			mode = PlayingMode.REPEATING;
		} else {
			mode = PlayingMode.NORMAL;
		}
	}

	// Stream of bytes to build the tempfile and then play the songs
	public void queueSongs(Set<Song> importedSongs) throws BusinessException {

		List<File> songToBePlayed = new ArrayList<>();

		try {
			for (Song song : importedSongs) {
				File tempFile = File.createTempFile("tempFile", ".mp3");

				FileOutputStream fos = new FileOutputStream(tempFile);
				fos.write(song.getContent());
				fos.close();

				songToBePlayed.add(tempFile);
			}

			songs = songToBePlayed;
			counter = 0;
			mode = PlayingMode.NORMAL;
			if (status != PlayerStatus.NOT_RUNNING) {
				resetThePlayer();
			}
			createMedia();

		} catch (IOException e) {
			throw new BusinessException("Unable to load songs");
		}
	}
}