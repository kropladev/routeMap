package dev.kropla.connection.servlet.utils;

import org.apache.http.StatusLine;

public class ServletConnectException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String messageAdd;
	private StatusLine statusLine;
	private String url;
	private String servletName;
	public String getMessageAdd() {
		return messageAdd;
	}
	public void setMessageAdd(String messageAdd) {
		this.messageAdd = messageAdd;
	}
	public StatusLine getStatusLine() {
		return statusLine;
	}
	public void setStatusLine(StatusLine statusLine) {
		this.statusLine = statusLine;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getServletName() {
		return servletName;
	}
	public void setServletName(String servletName) {
		this.servletName = servletName;
	}
}
