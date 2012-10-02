package org.m4w.workflow.mobile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.openxdata.db.util.Persistent;

/**
 *
 * @author kay
 */
public class Problem implements Persistent {

	private String waterpointId;
	private String problem;
	private String number = "000";

	public Problem() {
	}

	public Problem(String waterpointId, String problem) {
		this.waterpointId = waterpointId;
		this.problem = problem;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		return number;
	}
	
	

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public void setWaterpointId(String waterpointId) {
		this.waterpointId = waterpointId;
	}

	public String getProblem() {
		return problem;
	}

	public String getWaterpointId() {
		return waterpointId;
	}

	public void write(DataOutputStream stream) throws IOException {
		stream.writeUTF(waterpointId);
		stream.writeUTF(problem);
		stream.writeUTF(number);
	}

	public void read(DataInputStream stream) throws IOException, InstantiationException, IllegalAccessException {
		waterpointId = stream.readUTF();
		problem = stream.readUTF();
		number = stream.readUTF();
	}
}
