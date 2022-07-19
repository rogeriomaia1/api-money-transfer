package cvc.com.error;

public class ErrorDefaultParameter{
	
	private String field;
	private Object parameter;

	public ErrorDefaultParameter(String field, Object parameter) {
		this.field  = field;
		this.parameter = parameter;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getParameter() {
		return parameter;
	}

	public void setParameter(Object parameter) {
		this.parameter = parameter;
	}
	
}
