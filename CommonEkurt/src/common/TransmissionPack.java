package common;

import java.io.Serializable;

public class TransmissionPack implements Serializable{
	private static final long serialVersionUID = 1L;
	private Mission mission;
	private Response response;
	private Object information;

	public TransmissionPack(Mission mission, Response response, Object information) {

		this.mission = mission;
		this.response = response;
		this.information = information;
	}

	public Mission getMission() {
		return mission;
	}

	public void setMission(Mission mission) {
		this.mission = mission;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public Object getInformation() {
		return information;
	}

	public void setInformation(Object information) {
		this.information = information;
	}
	@Override
	public String toString() {
		return "You try to : " +getMission()+"  "+ "The mission is : "+getResponse();
	}
}
