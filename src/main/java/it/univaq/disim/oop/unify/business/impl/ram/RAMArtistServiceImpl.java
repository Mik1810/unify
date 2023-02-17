package it.univaq.disim.oop.unify.business.impl.ram;

import java.util.HashSet;
import java.util.Set;

import it.univaq.disim.oop.unify.business.ArtistService;
import it.univaq.disim.oop.unify.business.exceptions.ArtistNotFoundException;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Band;
import it.univaq.disim.oop.unify.domain.IndividualArtist;

public class RAMArtistServiceImpl implements ArtistService {

	private Set<Artist> artists = new HashSet<>();
	private static int id = 1;

	public RAMArtistServiceImpl() {

		IndividualArtist eminem = new IndividualArtist();

		eminem.setId(id++);
		eminem.setName("Eminem");
		eminem.setBiography(
				"Eminem, pseudonimo di Marshall Bruce Mathers III (St. Joseph, 17 ottobre 1972), è un rapper, "
						+ "produttore discografico e attore statunitense. È considerato uno dei migliori artisti hip hop di sempre.");

		artists.add(eminem);

		Band u2 = new Band();
		u2.setId(id++);
		u2.setName("U2");
		u2.setBiography("Gli U2 sono un gruppo musicale rock irlandese formatosi a Dublino nel 1976. "
				+ "Il gruppo è composto da Paul David Hewson, in arte Bono (cantante), David Howell "
				+ "Evans in arte The Edge (chitarrista), Adam Clayton (bassista) e Larry Mullen Jr. (batterista). "
				+ "Sono considerati una delle più grandi band musicali della storia");

		/*
		 * Creating the members of the band and set them in the Set of artist of the
		 * band We don't use the id of the single artist of the band for now, other
		 * fields are useless They aren't IndividualArtist, so I istantiate them as
		 * Artist, furthermore I don't need their informations, only the name is
		 * important
		 */

		Artist bono = new Artist();
		bono.setName("Bono");
		u2.getMembers().add(bono);
		Artist theEdge = new Artist();
		theEdge.setName("The Edge");
		u2.getMembers().add(theEdge);
		Artist clayton = new Artist();
		clayton.setName("Adam Clayton");
		u2.getMembers().add(clayton);
		Artist mullen = new Artist();
		mullen.setName("Larry Mullen Jr.");
		u2.getMembers().add(mullen);

		artists.add(u2);

		IndividualArtist beethoven = new IndividualArtist();
		beethoven.setId(id++);
		beethoven.setName("Beethoven");
		beethoven.setBiography("Ludwig van Beethoven nacque il 16 dicembre 1770 a Vienna ed è stato un compositore,"
				+ " pianista e direttore d'orchestra tedesco."
				+ " Figura cruciale della musica colta occidentale, fu l'ultimo rappresentante di rilievo del "
				+ "classicismo viennese ed è considerato uno dei maggiori e più influenti compositori di tutti i "
				+ "tempi. Annoverato tra i massimi geni della storia della musica, nonostante la sordità (ipoacusia) "
				+ "che lo colpì prima ancora di aver compiuto i trent'anni, egli continuò a comporre, dirigere e suonare, "
				+ "lasciando una produzione musicale fondamentale, straordinaria per forza espressiva e capacità evocativa.");

		artists.add(beethoven);

		Band coldplay = new Band();
		coldplay.setId(id++);
		coldplay.setName("Coldplay");
		coldplay.setBiography("I Coldplay sono un gruppo musicale britannico formatosi a Londra nel 1997. "
				+ "La band è composta da Chris Martin (voce, pianoforte, chitarra acustica), Jonny Buckland (chitarra elettrica),"
				+ " Guy Berryman (basso) e Will Champion (batteria). I quattro musicisti si conobbero alla University College di Londra,"
				+ "dove fondarono il gruppo.");

		Artist martin = new Artist();
		martin.setName("Chris Martin");
		coldplay.getMembers().add(martin);
		Artist buckland = new Artist();
		buckland.setName("Jonny Buckland");
		coldplay.getMembers().add(buckland);
		Artist berryman = new Artist();
		berryman.setName("Guy Berryman");
		coldplay.getMembers().add(berryman);
		Artist champion = new Artist();
		champion.setName("Will Champion");
		coldplay.getMembers().add(champion);

		artists.add(coldplay);

		IndividualArtist calcutta = new IndividualArtist();
		calcutta.setId(id++);
		calcutta.setName("Calcutta");
		calcutta.setBiography(
				"Calcutta, pseudonimo di Edoardo D'Erme (Latina, 19 aprile 1989), è un cantautore italiano. Attivo come "
						+ "musicista in diversi gruppi musicali locali fin dal 2007, nel 2009 fonda il duo musicale Calcutta. In seguito "
						+ "all'abbandono dell'altro membro, Marco Crypta, Calcutta mantiene il nome, scelto casualmente e lo adotta come nome d'arte.");

		artists.add(calcutta);

		Band blink182 = new Band();
		blink182.setId(id++);
		blink182.setName("blink-182");
		blink182.setBiography(
				"I blink-182 sono un gruppo musicale pop punk statunitense, formatosi nel 1992 a Poway, California, una città"
						+ " a nord di San Diego. Sono apprezzati per le particolari e coinvolgenti melodie pop punk così come per il loro umorismo."
						+ " I primi lavori sono caratterizzati da toni goliardici, riff di chitarra e melodie orecchiabili, che ispireranno diverse"
						+ " band pop punk nate in seguito.");

		Artist skiba = new Artist();
		skiba.setName("Matt Skiba");
		blink182.getMembers().add(skiba);
		Artist hoppus = new Artist();
		hoppus.setName("Mark Hoppus");
		blink182.getMembers().add(hoppus);
		Artist barker = new Artist();
		barker.setName("Travis Barker");
		coldplay.getMembers().add(barker);

		artists.add(blink182);

		IndividualArtist claudioLolli = new IndividualArtist();
		claudioLolli.setId(id++);
		claudioLolli.setName("Claudio Lolli");
		claudioLolli.setBiography(
				"Claudio Lolli (Bologna, 28 marzo 1950 – Bologna, 17 agosto 2018) è stato un cantautore, insegnante e "
						+ "scrittore italiano. È considerato uno fra i cantautori più impegnati. Oltre a temi politici, Lolli ha saputo trattare "
						+ "nell'arco di una trentina d'anni, incidendo una ventina di album, i più profondi temi dell'essere umano, quali la "
						+ "desolazione e la crisi, e problematiche sociali e culturali. Oltre a essere un cantautore, Lolli è stato anche uno "
						+ "scrittore e poeta, e, dagli anni ottanta, professore liceale.");

		artists.add(claudioLolli);

		IndividualArtist frahQuintale = new IndividualArtist();
		frahQuintale.setId(id++);
		frahQuintale.setName("Frah Quintale");
		frahQuintale.setBiography(
				"Frah Quintale, pseudonimo di Francesco Servidei (Brescia, 27 dicembre 1989), è un cantautore e rapper "
						+ "italiano. Nato a Brescia nel 1989, si appassiona alla musica nel 2004, a quindici anni, per poi iniziare il suo percorso "
						+ "musicale nel duo dei Fratelli Quintale, formatosi nel 2006, che vanta cinque album ancora all'attivo. Nel 2016, dopo "
						+ "aver lasciato il duo, pubblica la sua prima canzone da solista Colpa del vino e l'EP 2004.");

		artists.add(frahQuintale);

		Band spoon = new Band();
		spoon.setName("Spoon");
		spoon.setId(id++);
		spoon.setBiography(
				"Gli Spoon sono un gruppo indie rock statunitense proveniente da Austin (Texas), esponenti della musica underground. "
						+ "Formatosi nel 1994 dall'incontro tra il cantante Britt Daniel e il batterista Jim Eno durante una festa a Kimbolton Avenue, il "
						+ "gruppo scelse il nome Spoon in onore di una band tedesca degli anni settanta, i Can, il cui maggiore singolo era appunto Spoon, "
						+ "brano anche colonna sonora del film Das Messer.");

		Artist brittDaniel = new Artist();
		brittDaniel.setName("Britt Daniel");
		spoon.getMembers().add(brittDaniel);
		Artist jimEno = new Artist();
		jimEno.setName("Jim Eno");
		spoon.getMembers().add(jimEno);
		Artist alexFischel = new Artist();
		alexFischel.setName("Alex Fischel");
		spoon.getMembers().add(alexFischel);
		Artist gerardoLarios = new Artist();
		gerardoLarios.setName("Gerardo Larios");
		spoon.getMembers().add(gerardoLarios);

		artists.add(spoon);

		IndividualArtist missKeta = new IndividualArtist();
		missKeta.setName("Miss Keta");
		missKeta.setId(id++);
		missKeta.setBiography(
				"Myss Keta, pseudonimo reso graficamente come M¥SS KETA, è una cantautrice e rapper italiana. Il personaggio di Myss "
						+ "Keta nasce ad agosto 2013 da un'idea del collettivo Motel Forlanini, interessato a cogliere lo Zeitgeist della cultura underground "
						+ "di Milano.");

		artists.add(missKeta);

		IndividualArtist mattak = new IndividualArtist();
		mattak.setName("Mattak");
		mattak.setId(id++);
		mattak.setBiography(
				"Mattia Falcone, in arte “Mattak”, nasce il 19 settembre 1994 a Lugano (Ticino) ed è un rapper svizzero italiano. È considerato"
						+ " il rapper più influente attuale nella Svizzera italiana, nonché uno dei pochi che è riuscito a farsi sentire fuori dal territorio elvetico."
						+ " All’età di 10 anni si avvicina alla musica rap ascoltando Eminem, Ludacris e Fabri Fibra e a 15 anni comincia a fare freestyle, "
						+ "improvvisando rime con gli amici.");

		artists.add(mattak);
	}

	@Override
	public Set<Artist> getAllArtists() throws BusinessException {
		if(artists.isEmpty()) throw new BusinessException("The list of artists is empty!");
		return artists;
	}

	@Override
	public Set<Artist> findArtistsByName(String name) throws ArtistNotFoundException {

		Set<Artist> filtredArtists = new HashSet<>();
		if (name != null && name != "") {
			for (Artist artist : artists) {
				if (artist.getName().toLowerCase().contains(name.toLowerCase())) {
					filtredArtists.add(artist);
					
					/* it returns a list of artist that have the name field in their name
					 * es: if i search "A" it return a Set with the artist mozart, calcutta and
					 * coldplay
					 */
				}
			}
			if(filtredArtists.isEmpty()) throw new ArtistNotFoundException();
		} else {
			filtredArtists.addAll(artists);
		}
		return filtredArtists;
	}

	@Override
	public String getBiographyFromArtist(Artist artist) throws BusinessException {
		if(artist == null) throw new BusinessException("Invalid artist!");
		return artist.getBiography();
	}

	@Override
	public void addIndividualArtist(IndividualArtist artist) throws BusinessException {
		if(artist == null) throw new BusinessException("Invalid artist!");
		artist.setId(id++);
		artists.add(artist);
	}

	@Override
	public Band addBand(Band band) throws BusinessException {
		if(band == null) throw new BusinessException("Invalid artist!");
		band.setId(id++);
		artists.add(band);
		return band;
	}

	@Override
	public void addMembersToABand(Band band, String name) throws BusinessException {
		if(band == null) throw new BusinessException("Invalid artist!");
		Artist artist = new Artist();
		artist.setName(name);
		band.getMembers().add(artist);

	}

	@Override
	public void deleteArtist(Artist artist) throws BusinessException {
		if(artist == null) throw new BusinessException("Invalid artist!");
		artists.remove(artist);		
	}

	@Override
	public void changeName(Artist artist, String name) throws BusinessException {
		if(artist == null) throw new BusinessException("Invalid artist!");
		artist.setName(name);
	}

}