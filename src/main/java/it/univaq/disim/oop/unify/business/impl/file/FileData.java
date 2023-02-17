package it.univaq.disim.oop.unify.business.impl.file;

import java.util.List;

public class FileData {

	private long contatore;
	private List<String[]> righe;
	
	public long getCounter() {
		return contatore;
	}
	public void setCounter(long contatore) {
		this.contatore = contatore;
	}
	public List<String[]> getRows() {
		return righe;
	}
	public void setRows(List<String[]> righe) {
		this.righe = righe;
	}
}
