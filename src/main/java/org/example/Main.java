package org.example;

import org.example.view.LoginFrame;
import org.example.controller.LoginController;
import javax.swing.*;

public class Main {
     static void main(String[] args) {
        LoginFrame loginFrame = new LoginFrame();
        new LoginController(loginFrame);

        loginFrame.setVisible(true);
    }
}
