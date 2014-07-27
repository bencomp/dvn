package edu.harvard.iq.dvn.core.study;

public class FileMapEntry {
	
	private FileMetadata fmd;
	private DataTable dataTable;

	public FileMetadata getFileMetadata() {
		return fmd;
	}

	public void setFileMetadata(FileMetadata fmd) {
		this.fmd = fmd;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}
	
}
