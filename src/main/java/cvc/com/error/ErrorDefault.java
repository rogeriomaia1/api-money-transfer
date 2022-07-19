package cvc.com.error;

import java.util.List;

public class ErrorDefault {

	private int code;
	private String time;
	private String message;
	private List<ErrorDefaultParameter> listErrorParameter;
	
	
	public ErrorDefault(int code,  String time, String message,  List<ErrorDefaultParameter> listErrorParameter) {
		this.code = code;
		this.time = time;
		this.message = message;
		this.listErrorParameter = listErrorParameter;
	}

	public int getCode() {
		return code;
	}
			
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public List<ErrorDefaultParameter> getListErrorParameter() {
		return listErrorParameter;
	}

	public void setListErrorParameter(List<ErrorDefaultParameter> listErrorParameter) {
		this.listErrorParameter = listErrorParameter;
	}

}
