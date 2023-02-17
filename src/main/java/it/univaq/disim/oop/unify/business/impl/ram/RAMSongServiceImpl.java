package it.univaq.disim.oop.unify.business.impl.ram;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import it.univaq.disim.oop.unify.Utility;
import it.univaq.disim.oop.unify.business.ArtistService;
import it.univaq.disim.oop.unify.business.SongService;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Album;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Genre;
import it.univaq.disim.oop.unify.domain.Photo;
import it.univaq.disim.oop.unify.domain.Song;
import javafx.scene.image.Image;

public class RAMSongServiceImpl implements SongService {

	private ArtistService artistService;
	private Set<Song> songs = new HashSet<>();
	private Set<Album> albums = new HashSet<>();
	private static int songId = 1;
	private static int albumId = 1;


	public RAMSongServiceImpl(ArtistService artistService) {

		// The texts of the songs are been cutted because of their length

		this.artistService = artistService;

		try {
			Song loveTheWayYouLie = new Song();
			loveTheWayYouLie.setTitle("Love The Way You Lie");
			loveTheWayYouLie.setId(songId++);
			loveTheWayYouLie.setGenre(Genre.RAP);
			loveTheWayYouLie.setText("Just gonna stand there and watch me burn?\r\n"
					+ "Well, that's alright, because I like the way it hurts\r\n"
					+ "Just gonna stand there and hear me cry?\r\n"
					+ "Well, that's alright, because I love the way you lie\r\n" + "I love the way you lie");
			Album recovery = new Album();
			recovery.setTitle("Recovery");
			recovery.setId(albumId++);
			recovery.setGenre(Genre.RAP);
			loveTheWayYouLie.setAlbum(recovery);

			Song rapGod = new Song();
			rapGod.setTitle("Rap God");
			rapGod.setId(songId++);
			rapGod.setGenre(Genre.RAP);
			rapGod.setText("'Cause I'm beginnin' to feel like a Rap God, Rap God\r\n"
					+ "All my people from the front to the back nod, back nod\r\n"
					+ "Now, who thinks their arms are long enough to slap box, slap box?\r\n"
					+ "Let me show you maintainin' this shit ain't that hard, that hard\r\n"
					+ "Everybody want the key and the secret to rap immortality like Ι have got");
			Album theMarshallMathersLP2 = new Album();
			theMarshallMathersLP2.setTitle("The Marshall Mathers LP 2");
			theMarshallMathersLP2.setId(albumId++);
			theMarshallMathersLP2.setGenre(Genre.RAP);
			rapGod.setAlbum(theMarshallMathersLP2);

			Song withOrWithoutYou = new Song();
			withOrWithoutYou.setTitle("With Or Without You");
			withOrWithoutYou.setId(songId++);
			withOrWithoutYou.setGenre(Genre.POP);
			withOrWithoutYou.setText("With or without you\r\n" + "With or without you, ah, ah\r\n" + "I can't live\r\n"
					+ "With or without you\r\n" + "And you give yourself away\r\n" + "And you give yourself away\r\n"
					+ "And you give\r\n" + "And you give\r\n" + "And you give yourself away");
			Album theJoshuaTree = new Album();
			theJoshuaTree.setTitle("The Joshua Tree");
			theJoshuaTree.setId(albumId++);
			theJoshuaTree.setGenre(Genre.ROCK);
			withOrWithoutYou.setAlbum(theJoshuaTree);

			Song beautifulDay = new Song();
			beautifulDay.setTitle("Beautiful Day");
			beautifulDay.setId(songId++);
			beautifulDay.setGenre(Genre.ROCK);
			beautifulDay.setText("It's a beautiful day\r\n" + "Sky falls, you feel like\r\n"
					+ "It's a beautiful day\r\n" + "Don't let it get away\r\n" + "You're on the road\r\n"
					+ "But you've got no destination\r\n" + "You're in the mud\r\n" + "In the maze of her imagination");
			Album allThatYouCantLeaveBehind = new Album();
			allThatYouCantLeaveBehind.setTitle("All That You Can't Leave Behind");
			allThatYouCantLeaveBehind.setId(albumId++);
			allThatYouCantLeaveBehind.setGenre(Genre.ROCK);
			beautifulDay.setAlbum(allThatYouCantLeaveBehind);

			Song moonlightSonata = new Song();
			moonlightSonata.setTitle("Moonlight Sonata");
			moonlightSonata.setId(songId++);
			moonlightSonata.setGenre(Genre.CLASSIC);
			moonlightSonata.setText("This song has no lyrics");

			Album beethovenBestPieces = new Album();
			beethovenBestPieces.setTitle("Beethoven - Best Pieces");
			beethovenBestPieces.setId(albumId++);
			beethovenBestPieces.setGenre(Genre.CLASSIC);
			moonlightSonata.setAlbum(beethovenBestPieces);

			Song theScientist = new Song();
			theScientist.setTitle("The Scientist");
			theScientist.setId(songId++);
			theScientist.setGenre(Genre.POP);
			theScientist.setText("Coming back as we are\r\n" + "Nobody said it was easy\r\n"
					+ "Oh, it's such a shame for us to part\r\n" + "Nobody said it was easy\r\n"
					+ "No one ever said it would be so hard\r\n" + "I'm going back to the start\r\n"
					+ "Oh ooh, ooh ooh ooh ooh\r\n" + "Ah ooh, ooh ooh ooh ooh\r\n" + "Oh ooh, ooh ooh ooh ooh\r\n"
					+ "Oh ooh, ooh ooh ooh ooh");
			Album aRushOfBloodToTheHead = new Album();
			aRushOfBloodToTheHead.setTitle("A Rush Of Blood To The Head");
			aRushOfBloodToTheHead.setId(albumId++);
			aRushOfBloodToTheHead.setGenre(Genre.POP);
			theScientist.setAlbum(aRushOfBloodToTheHead);

			Song yellow = new Song();
			yellow.setTitle("Yellow");
			yellow.setId(songId++);
			yellow.setGenre(Genre.POP);
			yellow.setText("I drew a line\r\n" + "I drew a line for you\r\n" + "Oh, what a thing to do\r\n"
					+ "And it was all yellow\r\n" + "and your skin, oh yeah, your skin and bones\r\n"
					+ "(Ooh) turn into something beautiful\r\n" + "(Ah) and you know, for you, I'd bleed myself dry\r\n"
					+ "For you, I'd bleed myself dry");
			Album yellowAlbum = new Album();
			yellowAlbum.setTitle("Yellow");
			yellowAlbum.setId(albumId++);
			yellowAlbum.setGenre(Genre.POP);
			yellow.setAlbum(yellowAlbum);

			Song pesto = new Song();
			pesto.setTitle("Pesto");
			pesto.setId(songId++);
			pesto.setGenre(Genre.INDIE);
			pesto.setText("Uè deficiente\r\n" + "Negli occhi ho una botte che perde\r\n" + "E lo sai perché\r\n"
					+ "Perché mi sono innamorato\r\n" + "Mi ero addormentato di te\r\n"
					+ "E adesso che mi lasci solo\r\n" + "Con le cose fuori al posto loro");
			Album evergreen = new Album();
			evergreen.setTitle("Evergreen");
			evergreen.setId(albumId++);
			evergreen.setGenre(Genre.INDIE);
			pesto.setAlbum(evergreen);

			Song frosinone = new Song();
			frosinone.setTitle("Frosinone");
			frosinone.setId(songId++);
			frosinone.setGenre(Genre.INDIE);
			frosinone.setText("Mangio la pizza e sono il solo sveglio\r\n" + "In tutta la città\r\n"
					+ "Bevo un bicchiere per pensare al meglio\r\n" + "Per rivivere lo stesso sbaglio\r\n"
					+ "A mezzanotte ne ho commessi un paio\r\n" + "Che ridere che fa\r\n"
					+ "Mangio la pizza e sono il solo sveglio\r\n" + "In tutta la città\r\n"
					+ "Cammino dritto fino al tuo risveglio\r\n" + "E stanotte se ci va\r\n"
					+ "Noi a questa America daremo un figlio\r\n" + "Che morirà in jihad\r\n"
					+ "Ti chiedo scusa se non è lo stesso\r\n" + "Di tanti anni fa\r\n"
					+ "Leggo il giornale e c'è papa Francesco\r\n" + "E il Frosinone in serie A");
			Album mainstream = new Album();
			mainstream.setTitle("Mainstream");
			mainstream.setId(albumId++);
			mainstream.setGenre(Genre.INDIE);
			frosinone.setAlbum(mainstream);

			Song boredToDeath = new Song();
			boredToDeath.setTitle("Bored To Death - Acoustic Version");
			boredToDeath.setId(songId++);
			boredToDeath.setGenre(Genre.PUNK);
			boredToDeath.setText("Save your breath, I'm nearly\r\n" + "Bored to death and fading fast\r\n"
					+ "Life is too short to last long\r\n" + "Back on earth, I'm broken\r\n"
					+ "Lost and cold and fading fast\r\n" + "Life is too short to last long\r\n"
					+ "There's a stranger staring at the ceiling\r\n" + "Rescuing a tiger from a tree\r\n"
					+ "The pictures in her head are always dreaming\r\n" + "Each of them means everything to me");
			Album california = new Album();
			california.setTitle("California (Deluxe Edition)");
			california.setId(albumId++);
			california.setGenre(Genre.PUNK);
			boredToDeath.setAlbum(california);

			Song adamsSong = new Song();
			adamsSong.setTitle("Adam's Song");
			adamsSong.setId(songId++);
			adamsSong.setGenre(Genre.ROCK);
			adamsSong.setText("I never thought I'd die alone\r\n" + "I laughed the loudest, who'd have known?\r\n"
					+ "I trace the cord back to the wall\r\n" + "No wonder it was never plugged in at all\r\n"
					+ "I took my time, I hurried up\r\n" + "The choice was mine, I didn't think enough\r\n"
					+ "I'm too depressed to go on\r\n" + "You'll be sorry when I'm gone");
			Album enemaOfTheState = new Album();
			enemaOfTheState.setTitle("Enema Of The State");
			enemaOfTheState.setId(albumId++);
			enemaOfTheState.setGenre(Genre.PUNK);
			adamsSong.setAlbum(enemaOfTheState);

			Song michel = new Song();
			michel.setTitle("Michel");
			michel.setId(songId++);
			michel.setGenre(Genre.SONGWRITING);
			michel.setText("Ti ricordi, Michel dei nostri pantaloni corti\r\n"
					+ "Delle tue gambe lunghe, magre e forti e della rabbia\r\n"
					+ "Che mi davano correndo tutti i giorni un po' più svelte delle mie\r\n"
					+ "Ti ricordi, Michel dei nostri soldatini morti\r\n" + "Nella difesa eroica dei bastioni\r\n"
					+ "E seppelliti in una siepe con onori militari inventati lì per lì\r\n"
					+ "Ti ricordi, Michel del banco nero in terza fila\r\n" + "Che ascoltò tutte le risate\r\n"
					+ "Di due bambini che vivevano in un sogno che non si ripeterà\r\n"
					+ "Ti ricordi, Michel? Ti ricordi, Michel?\r\n" + "Ti ricordi, Michel? Ti ricordi, Michel?");
			Album aspettandoGodot = new Album();
			aspettandoGodot.setTitle("Aspettando Godot");
			aspettandoGodot.setId(albumId++);
			aspettandoGodot.setGenre(Genre.SONGWRITING);
			michel.setAlbum(aspettandoGodot);

			Song missili = new Song();
			missili.setTitle("Missili");
			missili.setId(songId++);
			missili.setGenre(Genre.INDIE);
			missili.setText("E scusa sai ma non ci penso più\r\n" + "Mi hai fatto a pezzi l'amore\r\n"
					+ "Ma adesso non ci casco più\r\n" + "Aspetta dai, se vuoi torniamo insieme\r\n"
					+ "Oppure prendi la metro che corre veloce\r\n" + "Mi hai fatto a pezzi la voce\r\n"
					+ "E adesso non ti parlo più\r\n" + "Mi hai fatto a pezzi la voce\r\n"
					+ "E adesso non ti parlo più\r\n" + "E adesso non ti parlo più");
			Album lungolinea = new Album();
			lungolinea.setTitle("Lungolinea");
			lungolinea.setId(albumId++);
			lungolinea.setGenre(Genre.INDIE);
			missili.setAlbum(lungolinea);

			Song theWayWeGetBy = new Song();
			theWayWeGetBy.setTitle("The Way We Get By");
			theWayWeGetBy.setId(songId++);
			theWayWeGetBy.setGenre(Genre.INDIE);
			theWayWeGetBy.setText("We get high in back seats of cars\r\n" + "We break into mobile homes\r\n"
					+ "We go to sleep to shake appeal\r\n" + "Never wake up on our own\r\n"
					+ "And that's the way we get by\r\n" + "The way we get by\r\n" + "Oh, that's the way we get by\r\n"
					+ "The way we get by");

			Album killTheMoonlight = new Album();
			killTheMoonlight.setTitle("Kill the Moonlight");
			killTheMoonlight.setId(albumId++);
			killTheMoonlight.setGenre(Genre.INDIE);
			theWayWeGetBy.setAlbum(killTheMoonlight);

			Song finimondo = new Song();
			finimondo.setTitle("Finimondo");
			finimondo.setId(songId++);
			finimondo.setGenre(Genre.DANCE);
			finimondo.setText("Sto in giro\r\n" + "Ho la fine del mondo sotto il vestito (bellissimo)\r\n"
					+ "Biondino (ehi biondino)\r\n" + "Cosa vuoi? Cosa fai? Ma chi prendi in giro? (Ahahah)\r\n"
					+ "Di giorno il giro delle sette chiese\r\n" + "Di notte poi fino alle sette al club\r\n"
					+ "Il brutto giro ma del bel paese\r\n" + "Greg non dorme da due giorni fa");
			Album finimondoAlbum = new Album();
			finimondoAlbum.setTitle("Finimondo");
			finimondoAlbum.setId(albumId++);
			finimondoAlbum.setGenre(Genre.DANCE);
			finimondo.setAlbum(finimondoAlbum);

			Song lAnimaGiusta = new Song();
			lAnimaGiusta.setTitle("L'Anima Giusta");
			lAnimaGiusta.setId(songId++);
			lAnimaGiusta.setGenre(Genre.RAP);
			lAnimaGiusta.setText("Vivo giornate in delay, non so come mai, come mai\r\n"
					+ "Se non sai più chi sei non sai dove vai, dove vai\r\n"
					+ "Perdo me stesso e mi chiedo quando tornerai\r\n"
					+ "E per quanto starò qua, se non sai più chi sei\r\n"
					+ "Forse ho l'anima giusta, ma nata nel corpo sbagliato\r\n"
					+ "Matti, è un po' che ti vedo con lo sguardo spento\r\n"
					+ "E non vedo più un vero sentimento se ti guardo dentro\r\n"
					+ "E non faccio che venerare l'andamento\r\n"
					+ "Quando neanche ho il coraggio di generare un cambiamento\r\n"
					+ "Ti vedo, sai, quando ti sfasci vertebre\r\n"
					+ "Quando stai rimando, tralasci, rimandi impegni e lasci perdere\r\n"
					+ "Vedo quanto cuore hai per dare tracce agli altri\r\n"
					+ "Per un male che non muore mai e finirà per cancellarti");
			Album riproduzioneVietata = new Album();
			riproduzioneVietata.setTitle("Riproduzione Vietata");
			riproduzioneVietata.setId(albumId++);
			riproduzioneVietata.setGenre(Genre.RAP);
			lAnimaGiusta.setAlbum(riproduzioneVietata);

			for (Artist artist : artistService.getAllArtists()) {

				switch (artist.getId()) {

				case 1:
					loveTheWayYouLie.setArtist(artist);
					rapGod.setArtist(artist);
					recovery.setArtist(artist);
					theMarshallMathersLP2.setArtist(artist);
					continue;
				case 2:
					withOrWithoutYou.setArtist(artist);
					beautifulDay.setArtist(artist);
					theJoshuaTree.setArtist(artist);
					allThatYouCantLeaveBehind.setArtist(artist);
					continue;
				case 3:
					moonlightSonata.setArtist(artist);
					beethovenBestPieces.setArtist(artist);
					continue;
				case 4:
					theScientist.setArtist(artist);
					yellow.setArtist(artist);
					aRushOfBloodToTheHead.setArtist(artist);
					yellowAlbum.setArtist(artist);
					continue;
				case 5:
					pesto.setArtist(artist);
					frosinone.setArtist(artist);
					evergreen.setArtist(artist);
					mainstream.setArtist(artist);
					continue;
				case 6:
					boredToDeath.setArtist(artist);
					adamsSong.setArtist(artist);
					california.setArtist(artist);
					enemaOfTheState.setArtist(artist);
					continue;
				case 7:
					michel.setArtist(artist);
					aspettandoGodot.setArtist(artist);
					continue;
				case 8:
					missili.setArtist(artist);
					lungolinea.setArtist(artist);
					continue;
				case 9:
					theWayWeGetBy.setArtist(artist);
					killTheMoonlight.setArtist(artist);
					continue;
				case 10:
					finimondo.setArtist(artist);
					finimondoAlbum.setArtist(artist);
					continue;
				case 11:
					lAnimaGiusta.setArtist(artist);
					riproduzioneVietata.setArtist(artist);
					break;
				}

			}
			// Adding the songs in the set of all the songs
			songs.add(loveTheWayYouLie);
			songs.add(rapGod);
			songs.add(boredToDeath);
			songs.add(frosinone);
			songs.add(pesto);
			songs.add(yellow);
			songs.add(theScientist);
			songs.add(beautifulDay);
			songs.add(adamsSong);
			songs.add(moonlightSonata);
			songs.add(withOrWithoutYou);
			songs.add(michel);
			songs.add(missili);
			songs.add(theWayWeGetBy);
			songs.add(finimondo);
			songs.add(lAnimaGiusta);
			
			//Setting the byte[] of the songs
			for(Song song : songs) {
				song.setContent(Files.readAllBytes(new File(Utility.MUSIC_REPOSITORY + song.getTitle() + Utility.MP3_SUFFIX).toPath()));
			}

			// Adding the album in the set of all the album
			albums.add(enemaOfTheState);
			albums.add(mainstream);
			albums.add(evergreen);
			albums.add(yellowAlbum);
			albums.add(aRushOfBloodToTheHead);
			albums.add(allThatYouCantLeaveBehind);
			albums.add(beethovenBestPieces);
			albums.add(theJoshuaTree);
			albums.add(theMarshallMathersLP2);
			albums.add(recovery);
			albums.add(california);
			albums.add(aspettandoGodot);
			albums.add(lungolinea);
			albums.add(killTheMoonlight);
			albums.add(riproduzioneVietata);
			albums.add(finimondoAlbum);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Set<Song> findSongsFromTitle(String title) throws BusinessException {
		Set<Song> filtredSongsByName = new HashSet<>();

		if (title != null && title != "") {
			for (Song song : songs) {
				if (song.getTitle().toLowerCase().contains(title) || song.getTitle().equalsIgnoreCase(title)) {
					filtredSongsByName.add(song);
				}
			}
		} else {
			filtredSongsByName.addAll(songs);
		}
		return filtredSongsByName;
	}

	@Override
	public Set<Song> findSongsByGenre(String genre) throws BusinessException {

		Set<Song> filtredSongsByGenre = new HashSet<>();
		for (Song song : songs) {
			if (song.getGenre().name().toLowerCase().equals(genre.toLowerCase())) {
				filtredSongsByGenre.add(song);
			}
		}
		return filtredSongsByGenre;
	}

	@Override
	public String getLyricsFromSong(Song song) throws BusinessException {

		return song.getText();
	}

	@Override
	public Set<Song> getAllSongs() {

		return songs;
	}

	@Override
	public Set<Album> findAlbumFromTitle(String title) throws BusinessException {

		Set<Album> filtredAlbumFromTitle = new HashSet<>();
		if (title != null && title != "") {
			for (Album album : albums) {
				if (album.getTitle().toLowerCase().contains(title) || album.getTitle().equalsIgnoreCase(title)) {
					filtredAlbumFromTitle.add(album);
				}
			}
		} else {
			filtredAlbumFromTitle.addAll(albums);
		}
		return filtredAlbumFromTitle;
	}

	@Override
	public Set<Album> getAlbumFromArtist(Artist artist) throws BusinessException {
		Set<Album> filtredAlbumFromArtist = new HashSet<>();
		for (Album album : albums) {
			if (album.getArtist().getId() == artist.getId()) {
				filtredAlbumFromArtist.add(album);
			}
		}
		return filtredAlbumFromArtist;
	}

	@Override
	public Set<Song> getSongsFromArtist(Artist artist) throws BusinessException {
		Set<Song> filtredSongsFromArtist = new HashSet<>();
		for (Song song : songs) {
			if (song.getArtist().getId() == artist.getId()) {
				filtredSongsFromArtist.add(song);
			}
		}
		return filtredSongsFromArtist;
	}

	@Override
	public Set<Album> findAlbumFromGenre(String genre) throws BusinessException {
		Set<Album> filtredAlbumFromGenre = new HashSet<>();
		for (Album album : albums) {
			if (album.getGenre().name().toLowerCase().equals(genre.toLowerCase())) {
				filtredAlbumFromGenre.add(album);
			}
		}
		return filtredAlbumFromGenre;
	}

	@Override
	public Set<Album> getAllAlbum() {

		return albums;
	}

	@Override
	public void addAlbum(Album album) throws BusinessException {

		album.setId(albumId++);
		albums.add(album);

	}

	@Override
	public void modifyTitleOfAlbum(Album album, String title) throws BusinessException {
		album.setTitle(title);
	}

	@Override
	public void modifyGenreOfAlbum(Album album, Genre genre) throws BusinessException {
		album.setGenre(genre);
	}

	@Override
	public void modifyTitleOfSong(Song song, String title) throws BusinessException {
		song.setTitle(title);
	}

	@Override
	public void modifyGenreOfSong(Song song, Genre genre) throws BusinessException {
		song.setGenre(genre);
	}



	@Override
	public Set<Song> getSongsFromAlbum(Album album) throws BusinessException {
		Set<Song> songsFromAlbum = new HashSet<>();

		for (Song song : songs) {
			if (song.getAlbum().getId() == album.getId()) {
				songsFromAlbum.add(song);
			}
		}
		return songsFromAlbum;
	}

	@Override
	public void addSong(Song song) throws BusinessException {
		song.setId(songId++);
		songs.add(song);
		
	}
	
	@Override
	public void deleteSong(Song song) throws BusinessException {
		songs.remove(song);
		albums.remove(song.getAlbum());
		//deleteAlbum -> deleteSong -> remove returns false because the album is been already deleted
		//deleteSong -> returns true because the method deletes teh album
	}
	
	@Override
	public void deleteAlbum(Album album) throws BusinessException {
		albums.remove(album);
		
		//Search for all the songs that were in the album
		Set<Song> songsToRemove = new HashSet<>();
		for (Song song : songs) {
			if(song.getAlbum().getId() == album.getId()) {
				songsToRemove.add(song);
			}
		}
		//Iterate above the group of songs to remove them one by one
		for(Song song : songsToRemove) {
			songs.remove(song);
		}
	}

	@Override
	public void deleteDiscographyFromArtist(Artist theArtist) throws BusinessException {
		
		//Search for albums to remove
		Set<Album> albumsToRemove = new HashSet<>();
		for(Album album : albums) {
			if(album.getArtist().getId() == theArtist.getId()) {
				albumsToRemove.add(album);
			}
		}
		
		//Iterate above the group of albums to remove them one by one
		for(Album album : albumsToRemove) {
			deleteAlbum(album);
		}
	}

	@Override
	public Song getSongById(int id) throws BusinessException {
		for(Song song : songs) {
			if(song.getId() == id) return song;
		}
		throw new BusinessException("Song doesn't exist! (id: " + id + ")");
	}
}