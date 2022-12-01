package common;

import java.io.Serializable;

public class Transaction implements Serializable{
	private static final long serialVersionUID = 1L;
	private Action action;
	private Response response;
	private Object data;

	public Transaction(Action mission, Response response, Object information) {

		this.action = mission;
		this.response = response;
		this.data = information;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action mission) {
		this.action = mission;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object information) {
		this.data = information;
	}
	@Override
	public String toString() {
		return "Client action : " +getAction()+"  "+ "The action status : "+getResponse();
	}
}
