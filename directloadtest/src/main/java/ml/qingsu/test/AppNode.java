package ml.qingsu.test;

public class AppNode {
	private String app_name;
	private String app_image;
	private String app_size;
	private String app_introduction;
	private String app_sites;
	public String getApp_sites() {
		return this.app_sites;
	}
	public void setApp_sites(String app_sites) {
		this.app_sites = app_sites;
	}
	public AppNode(String app_name, String app_image, String app_size,
			String app_introduction, String app_sites) {
		this.app_name = app_name;
		this.app_image = app_image;
		this.app_size = app_size;
		this.app_introduction = app_introduction;
		this.app_sites = app_sites;
	}
	public String getApp_image() {
		return this.app_image;
	}
	public void setApp_image(String app_image) {
		this.app_image = app_image;
	}
	public String getApp_name() {
		return this.app_name;
	}
	public void setApp_name(String appname) {
		app_name = appname;
	}
	public String getApp_size() {
		return this.app_size;
	}
	public void setApp_size(String app_size) {
		this.app_size = app_size;
	}
	public String getApp_introduction() {
		return this.app_introduction;
	}
	public void setApp_introduction(String app_introduction) {
		this.app_introduction = app_introduction;
	}
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append(this.app_name +",");
		
		sb.append(this.app_size +",");
		sb.append(this.app_introduction +",");
		sb.append(this.app_sites);
		return sb.toString();
	}	
}
