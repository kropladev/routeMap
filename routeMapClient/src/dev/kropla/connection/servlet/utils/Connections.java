package dev.kropla.connection.servlet.utils;

import java.util.List;
public interface Connections {
    
	public abstract Object postServlet(String servletContextName, List<ServletParams> params,@SuppressWarnings("rawtypes") Class objectClass ) throws ServletConnectException;
	
}
