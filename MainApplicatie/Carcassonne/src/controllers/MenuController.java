package controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import views.MenuView;

public class MenuController {
	static File handleidingDoc = new File("Handleiding.html");

	public static void openHandleiding() {
		MenuView.handleidingButton.setOnAction(e -> {
			try {
				Desktop.getDesktop().browse(handleidingDoc.toURI());
			} catch (IOException e1) {
				System.out.println("Handleiding niet gevonden!");
			}});
		
	}
}
