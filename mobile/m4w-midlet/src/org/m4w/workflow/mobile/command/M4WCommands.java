package org.m4w.workflow.mobile.command;

import javax.microedition.lcdui.Command;

public class M4WCommands {

        public static Command mainMenuCmd = new Command("Home", Command.BACK, 0);
        public static Command inspctCmd = new Command("Inspect Water", Command.SCREEN, 0);
        public static Command dldInspection = new Command("Update Inspections", Command.SCREEN, 0);
	public static Command reportProblem = new Command("Report Problem", Command.OK, 0);
}
