package dev.kropla.connection.servlet.utils;

/**
 * @author m
 *
 */
public class ServletParams {
	private String parameterName;
	private Object parameterValue;
	private String parameterType;
	public String getParameterName() {
		return parameterName;
	}
	
	public ServletParams(String pName, Object pValue, String pType) {
		setParameterName(pName);
		setParameterType(pType);
		setParameterValue(pValue);
	}


	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public Object getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(Object parameterValue) {
		this.parameterValue = parameterValue;
	}
	public String getParameterType() {
		return parameterType;
	}
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	@Override
	public String toString() {
		return "ServletParams [parameterName=" + parameterName
				+ ", parameterValue=" + parameterValue + ", parameterType="
				+ parameterType + "]";
	}
	
}
