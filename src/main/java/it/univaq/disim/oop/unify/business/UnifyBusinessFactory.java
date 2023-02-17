package it.univaq.disim.oop.unify.business;

import it.univaq.disim.oop.unify.business.impl.file.FileUnifyBusinessFactoryImpl;
import it.univaq.disim.oop.unify.business.impl.ram.RAMUnifyBusinessFactoryImpl;

public abstract class UnifyBusinessFactory {

	//private static UnifyBusinessFactory factory = new RAMUnifyBusinessFactoryImpl();
	private static UnifyBusinessFactory factory = new FileUnifyBusinessFactoryImpl();

	public static UnifyBusinessFactory getInstance() {
		return factory;
	}

	public abstract PersonService getPersonService();

	public abstract SongService getSongService();

	public abstract ArtistService getArtistService();
	
	public abstract PlaylistService getPlaylistService();
	
	public abstract PhotoService getPhotoService();
}
