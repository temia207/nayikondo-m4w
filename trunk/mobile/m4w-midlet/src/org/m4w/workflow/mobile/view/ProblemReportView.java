package org.m4w.workflow.mobile.view;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

public class ProblemReportView extends Form {

	private Display display;
	private TextField txtWaterpointId, txtProblem;

	public ProblemReportView(Display display) {
		super("Report Waterpoint Problem");
		this.display = display;
		txtWaterpointId = new TextField("Waterpoint Number", null, 50, TextField.ANY);
		txtProblem = new TextField("Problem", null, 500, TextField.ANY);
		init();
	}

	private void init() {
		append(txtWaterpointId);
		append(txtProblem);
	}

	public TextField getTxtWaterpointId() {
		return txtWaterpointId;
	}

	public TextField getTxtProblem() {
		return txtProblem;
	}

	public void show() {
		display.setCurrent(this);
	}

	public void clear() {
		txtProblem.setString("");
		txtWaterpointId.setString("");
	}
}
