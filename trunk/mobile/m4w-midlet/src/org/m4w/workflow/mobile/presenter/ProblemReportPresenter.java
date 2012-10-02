package org.m4w.workflow.mobile.presenter;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import org.m4w.workflow.mobile.M4WDldManager;
import org.m4w.workflow.mobile.M4WMidlet;
import org.m4w.workflow.mobile.Problem;
import org.m4w.workflow.mobile.SurveyResponse;
import org.m4w.workflow.mobile.command.M4WCommands;
import org.m4w.workflow.mobile.view.ProblemReportView;
import org.openxdata.communication.ConnectionSettings;
import org.openxdata.communication.TransportLayer;
import org.openxdata.communication.TransportLayerListener;
import org.openxdata.db.util.Persistent;
import org.openxdata.util.DefaultCommands;
import org.openxdata.util.MenuText;
import org.openxdata.workflow.mobile.Factory;
import org.openxdata.workflow.mobile.command.ActionDispatcher;
import org.openxdata.workflow.mobile.command.ActionListener;
import org.openxdata.workflow.mobile.command.Viewable;
import org.openxdata.workflow.mobile.util.AlertMsgHelper;

/**
 *
 * @author kay
 */
public class ProblemReportPresenter implements TransportLayerListener, CommandListener, Viewable,ActionListener {

	private Display display;
	private ProblemReportView problemReportView;
	private Viewable prevDisp;
	private M4WDldManager dldMgr;
	private final ActionDispatcher dispatcher;

	public ProblemReportPresenter(Display display, M4WDldManager dldMgr,
		ActionDispatcher dispatcher) {
		this.display = display;
		problemReportView = new ProblemReportView(display);
		this.dldMgr = dldMgr;
		this.dispatcher = dispatcher;
		init();
	}

	private void init() {
		problemReportView.setCommandListener(this);
		problemReportView.addCommand(DefaultCommands.cmdOk);
		problemReportView.addCommand(DefaultCommands.cmdCancel);
		dispatcher.registerListener(M4WCommands.reportProblem, this);
		
		
	}

	public void uploaded(Persistent dataOutParams, Persistent dataOut) {
                try {
                        SurveyResponse response = (SurveyResponse) dataOut;
                        String message = response.isSuccess() ? "Problem Reported Succesfully" : response.getMessage();
                        AlertMsgHelper.showMsg(display, problemReportView, message);
                        if (!response.isSuccess()) {
                                return;
                        }
			clear();
                } catch (Exception ex) {
                        M4WMidlet.handleEx(problemReportView, ex);
                }
	}

	public void downloaded(Persistent prstnt, Persistent prstnt1) {
	}

	public void errorOccured(String string, Exception excptn) {
		AlertMsgHelper.showError(display, problemReportView,"An Unexpected Error Occured On The Server");
	}

	public void cancelled() {
	}

	public void updateCommunicationParams() {
		String url = ConnectionSettings.getHttpUrl();
		if (url != null) {
			Factory.getTransportLayer().setCommunicationParameter(TransportLayer.KEY_HTTP_URL, url);
		}
	}

	public void commandAction(Command cmnd, Displayable dsplbl) {
		if (cmnd == DefaultCommands.cmdCancel) {
			clear();
			prevDisp.show();
		}else
		if (cmnd == DefaultCommands.cmdOk) {
			submitProblem();
		}
	}

	public void show() {
		problemReportView.show();
	}

	public Displayable getDisplay() {
		return problemReportView;
	}

	private void submitProblem() {
		String waterPoint = problemReportView.getTxtWaterpointId().getString();
		String problemMsg = problemReportView.getTxtProblem().getString();

		if (waterPoint == null || waterPoint.length() == 0) {
			AlertMsgHelper.showError(display, problemReportView, "Please Enter The Waterpoint ID");
			return;
		}

		if (problemMsg == null || problemMsg.length() == 0) {
			AlertMsgHelper.showMsg(display, problemReportView, "Please Enter The Problem With The Waterpoint");
			return;
		}

		Problem problem = new Problem(waterPoint, problemMsg);

		dldMgr.reportProblem(problem, this);

	}

	private void clear() {
		problemReportView.clear();
	}

	public boolean handle(Command cmnd, Viewable vwbl, Object o) {
		prevDisp = vwbl;
		if(cmnd == M4WCommands.reportProblem){
			show();
		}
		return true;
	}
}
