package entity;
public class ConfigEntity {
	
	private String patchInputFolder;
	private String patchOutputFolder;
	private String patchErorFolder;
	private int upgrade_time;
	
	
	public String getpatchInputFolder() {
		return patchInputFolder;
	}
	public void setpatchInputFolder(String patchInputFolder) {
		this.patchInputFolder = patchInputFolder;
	}
	public String getpatchOutputFolder() {
		return patchOutputFolder;
	}
	public void setpatchOutputFolder(String patchOutputFolder) {
		this.patchOutputFolder = patchOutputFolder;
	}
	public String getpatchErorFolder() {
		return patchErorFolder;
	}
	public void setpatchErorFolder(String patchErorFolder) {
		this.patchErorFolder = patchErorFolder;
	}
	public int getUpgrade_time() {
		return upgrade_time;
	}
	public void setUpgrade_time(int upgrade_time) {
		this.upgrade_time = upgrade_time;
	}
	
}