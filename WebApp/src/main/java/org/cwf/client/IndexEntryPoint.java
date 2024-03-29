/*
 * indexEntryPoint.java
 *
 * Created on November 26, 2009, 11:39 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.cwf.client;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.core.XDOM;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Padding;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout.VBoxLayoutAlign;
import com.google.gwt.user.client.Window;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import org.cwf.client.controllers.CommentController;
import org.cwf.client.controllers.EditNewWaterPointController;
import org.cwf.client.controllers.EditWaterPointController;
import org.cwf.client.controllers.FunctionalityParamController;
import org.cwf.client.controllers.HomeController;
import org.cwf.client.controllers.LoginController;
import org.cwf.client.controllers.ProblemHistoryController;
import org.cwf.client.controllers.ReportsController;
import org.cwf.client.controllers.TicketDetailsController;
import org.cwf.client.service.AssessmentClientServiceAsync;
import org.cwf.client.service.InspectionClientServiceAsync;
import org.cwf.client.service.ProblemServiceAsync;
import org.cwf.client.service.ReportServiceAsync;
import org.cwf.client.service.SettingServiceAsync;
import org.cwf.client.service.UserServiceAsync;
import org.cwf.client.service.WaterPointServiceAsync;
import org.cwf.client.service.YawlServiceAsync;
import org.cwf.client.utils.ProgressIndicator;

/**
 *
 * @author Prince
 */
public class IndexEntryPoint implements EntryPoint, Refreshable {

    final AppMessages appMessages = GWT.create(AppMessages.class);
    public static final String VIEWPORT = "viewport";
    public static final String PORTAL = "portal";
    public static final String LOGGED_IN_USER_NAME = "loggedInUser";
    
    // services
    ProblemServiceAsync ticketSmsService;
    WaterPointServiceAsync waterPointService;
    InspectionClientServiceAsync inspectionService;
    AssessmentClientServiceAsync assessmentService;
    UserServiceAsync userService;
    YawlServiceAsync yawlService;
    SettingServiceAsync settingService;
	ReportServiceAsync reportsService;
    // top level UI components
    private Viewport viewport;
    private Portal portal;
    // user dependent UI components
    private Text userBanner;
	private static HandlerRegistration windowClosingRegistration;

    @Override
    public void onModuleLoad() {
        /* Install an UncaughtExceptionHandler which will
         * produce <code>FATAL</code> log messages
         */
        GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

            @Override
            public void onUncaughtException(final Throwable tracepoint) {
                GWT.log("Uncaught Exception", tracepoint);
                MessageBox.alert(appMessages.error(), appMessages.pleaseTryAgainLater(tracepoint.getMessage()), null);
                ProgressIndicator.hideProgressBar();
            }
        });
        System.out.println("================ starting ");
	addWindowClosingHandler();
        ticketSmsService = ProblemServiceAsync.Util.getInstance();
        waterPointService = WaterPointServiceAsync.Util.getInstance();
        inspectionService = InspectionClientServiceAsync.Util.getInstance();
        assessmentService = AssessmentClientServiceAsync.Util.getInstance();
        yawlService = YawlServiceAsync.Util.getInstance();
        userService = UserServiceAsync.Util.getInstance();
        settingService = SettingServiceAsync.Util.getInstance();
		reportsService = ReportServiceAsync.Util.getInstance();
        initializeUi();
        RootPanel.get().setStylePrimaryName("body");
        LoginController controller = new LoginController(userService);

        Dispatcher dispatcher = Dispatcher.get();
        dispatcher.addController(controller);
        dispatcher.addController(new HomeController(ticketSmsService,waterPointService,yawlService,settingService,reportsService));
        dispatcher.addController(new EditWaterPointController(waterPointService,inspectionService));
        dispatcher.addController(new TicketDetailsController(ticketSmsService,inspectionService,assessmentService));
        dispatcher.addController(new CommentController(ticketSmsService));
        dispatcher.addController(new ProblemHistoryController(ticketSmsService,assessmentService));
        dispatcher.addController(new EditNewWaterPointController(waterPointService, settingService));
		dispatcher.addController(new ReportsController(reportsService));
		dispatcher.addController(new FunctionalityParamController(reportsService));

        RefreshablePublisher publisher = RefreshablePublisher.get();
        publisher.subscribe(RefreshableEvent.Type.NAME_CHANGE, this);
        AppEvent event = new AppEvent(LoginController.LOGIN);
        dispatcher.dispatch(event);
    }

    public void initializeUi() {
        viewport = new Viewport();
        viewport.setLayout(new BorderLayout());
//        viewport.addStyleName("emit-viewport");
        createNorth();
        createPortal();
        createDisclaimer();
        // registry serves as a global context
        Registry.register(VIEWPORT, viewport);
        Registry.register(PORTAL, portal);

        RootPanel.get().add(viewport);
    }

    @Override
    public void refresh(RefreshableEvent event) {
        GWT.log("Name change event for ");
    }

    public void createNorth() {

        HorizontalPanel northPanel = new HorizontalPanel();
        northPanel.setTableWidth("100%");
        northPanel.setBorders(false);

        TableData logoTableData = new TableData();
        logoTableData.setHorizontalAlign(HorizontalAlignment.LEFT);
        logoTableData.setVerticalAlign(VerticalAlignment.TOP);
        logoTableData.setWidth("250");
        Image logo = new Image(appMessages.logo());
        logo.setTitle(appMessages.title());

        logo.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                com.google.gwt.user.client.Window.open(appMessages.logoUrl(), "name", "features");
            }
        });

        northPanel.add(logo, logoTableData);

        TableData userBannerTableData = new TableData();
        userBannerTableData.setHorizontalAlign(HorizontalAlignment.CENTER);
        userBannerTableData.setVerticalAlign(VerticalAlignment.MIDDLE);
        userBanner = new Text("ss");
        userBanner.setStyleName("userBanner");
        northPanel.add(userBanner, userBannerTableData);


        Button logout = new Button(appMessages.logout());
        logout.addListener(Events.Select, new Listener<ButtonEvent>() {

            @Override
            public void handleEvent(ButtonEvent be) {
		windowClosingRegistration.removeHandler();
		Window.Location.replace(GWT.getHostPageBaseURL() + "j_spring_security_logout");
//		XDOM.reload();
            }
        });
        FlexTable ft = new FlexTable();
        ft.setBorderWidth(0);
	Button refreshBtn = new Button(appMessages.refresh());
	refreshBtn.addListener(Events.Select, new Listener<ButtonEvent>(){

	    @Override
	    public void handleEvent(ButtonEvent be) {
                RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.RELOAD, ""));
	    }
	});
	ft.setWidget(0, 0, refreshBtn);
        ft.setWidget(0, 1, logout);
        TableData buttonsTableData = new TableData();
        buttonsTableData.setHorizontalAlign(HorizontalAlignment.RIGHT);
        buttonsTableData.setVerticalAlign(VerticalAlignment.MIDDLE);
        buttonsTableData.setWidth("200");
        buttonsTableData.setHeight("30");
        northPanel.add(ft, buttonsTableData);

        BorderLayoutData data = new BorderLayoutData(LayoutRegion.NORTH,50);
        data.setMargins(new Margins(10, 40, 10, 14));
        viewport.add(northPanel, data);
    }

    public void createPortal() {
        LayoutContainer center = new LayoutContainer();
        center.setLayout(new FitLayout());

        portal = new Portal(1);
        portal.setSpacing(3);
        portal.setColumnWidth(0, .99);
        center.add(portal);

        BorderLayoutData data = new BorderLayoutData(LayoutRegion.CENTER);
        data.setMargins(new Margins(1, 5, 1, 5));

        viewport.add(center, data);
    }

    private void createDisclaimer() {
        final LayoutContainer lc = new LayoutContainer();
        lc.setBorders(false);
        lc.setBounds(1, 1, 1, 1);
        lc.setHeight(10);

        VBoxLayout layout = new VBoxLayout();
        layout.setPadding(new Padding(0));
        layout.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
        lc.setLayout(layout);

        lc.add(new Text(""));

        StringBuilder sb = new StringBuilder();
        sb.append("<table border=0 cellspacing=10><tr><td>");
        sb.append(appMessages.disclaimer());
        sb.append("</td><td>");
        sb.append("<a href=\"#\" onclick=\"window.open('http://www.cell-life.org');\" title=\"Cell-Life : http://www.cell-life.org' style='cursor:hand;\">");
        sb.append("<img width=\"40\" src=\"images/m4w/snvlogo.png\" title=\"SNV\" style=\"cursor:hand;\"/>");
//		sb.append("<img width=\"40\" height=\"34\" src=\"images/m4w/wateraidlogo.png\" title=\"Water Aid\" style=\"cursor:hand;\"/>");
        sb.append("</a>");
        sb.append("</td>");

        sb.append("<td valign=middle>");
        sb.append("<a href=\"#\" onclick=\"window.open('http://www.openxdata.org');\" title=\"OpenXData : http://www.openxdata.org' style='cursor:hand;\">");
        sb.append("<img  src=\"images/m4w/wateraidlogo.png\" valign=middle title=\"Makerere University\" style=\"cursor:hand;\"/>");
        sb.append("</a>");
        sb.append("</td>");

//        sb.append(appMessages.and());
        sb.append("<td valign=middle>");
        sb.append("<a href=\"#\" onclick=\"window.open('http://www.openxdata.org');\" title=\"OpenXData : http://www.openxdata.org' style='cursor:hand;\">");
        sb.append("<img  src=\"images/m4w/muklogo.png\" valign=middle title=\"Makerere University\" style=\"cursor:hand;\"/>");
        sb.append("</a>");
        sb.append("</td></tr></table>");
        Html html = new Html(sb.toString());

        lc.add(html);

        BorderLayoutData data = new BorderLayoutData(LayoutRegion.SOUTH,70);
        data.setMargins(new Margins(1, 5, 20, 5));
        viewport.add(lc, data);
    }
	
	public static boolean isExplorer() {
		String test = Window.Location.getPath();
		if (test.indexOf("pages") != -1) {
			return false;
		}
		return true;
	}

	public static void addWindowClosingHandler() {
		windowClosingRegistration = Window.addWindowClosingHandler(new ClosingHandler() {
			@Override
			public void onWindowClosing(ClosingEvent event) {
				// this should handle backspaces and escape button presses
				event.setMessage("m4water");
			}
		});
	}
	public static void openWindow(String url) {
		windowClosingRegistration.removeHandler();
		GWT.log("Opening popup with URL:" + URL.encode(url));
		Window.open(URL.encode(url), "popup", "height=175,width=320");
		addWindowClosingHandler();
	}

}
