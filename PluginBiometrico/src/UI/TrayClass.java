/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import javax.swing.ImageIcon;

/**
 *
 * @author Mauricio Herrera
 */
public class TrayClass {

    static TrayIcon trayIcon;

    public static void show() {
        if (trayIcon == null) {
            if (!SystemTray.isSupported()) {
                System.exit(0);
            }
            trayIcon = new TrayIcon(createIcon("/FIngerUtils/Fingerprint.png", "Icon"));
            trayIcon.setToolTip("Sensor Biometrico");
            final SystemTray tray = SystemTray.getSystemTray();

            final PopupMenu menu = new PopupMenu();

            MenuItem close = new MenuItem("Cerrar");

            close.addActionListener((e) -> {
                System.exit(0);
            });

            menu.add(close);
            trayIcon.setPopupMenu(menu);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.out.println("Error " + e);
            }

        }
    }

    private static Image createIcon(String imagen, String icon) {
        Image imageIcon = new javax.swing.ImageIcon(TrayClass.class.getResource(imagen)).getImage();
        return (new ImageIcon(imageIcon, icon)).getImage();
    }

}
