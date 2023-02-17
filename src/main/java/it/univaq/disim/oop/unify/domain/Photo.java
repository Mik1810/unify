package it.univaq.disim.oop.unify.domain;

public class Photo {

	private byte[] content;
	
	public Photo(byte[] content) {
		this.content = content;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
}