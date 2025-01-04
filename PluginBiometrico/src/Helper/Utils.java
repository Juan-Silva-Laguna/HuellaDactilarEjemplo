/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 *
 * @author Mauricio Herrera
 */
public class Utils {

    public Utils() {
    }

    public final String srnPc() {
        long timestamp = (new Date().getTime() / 1000);
        StringBuilder temp = new StringBuilder();
        String leter = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int sizeWord = leter.length();
        Random rdn = new Random();
        for (int i = 0; i < 7; i++) {
            char c = leter.charAt(rdn.nextInt(sizeWord - 1));
            temp.append(c);
        }
        temp.append(timestamp);
        return temp.toString();
    }

    public final void generateUniqueId() {
        FileOutputStream fos = null;
        try {
            File archivo = new File("config.properties");
            fos = new FileOutputStream(archivo);
            Properties archivoProp = new Properties();
            archivoProp.setProperty("uniqueId", srnPc());
            archivoProp.store(fos, "Unico Id");
        } catch (IOException e) {
            System.err.println("Eror generando Unico Id " + e);
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                System.out.println("Helper.Utils.generateUniqueId " + e);
            }
        }
    }

    public final String getUniqueId() throws FileNotFoundException, IOException {
        File archivo = new File("config.properties");
        if (!archivo.exists()) {
            generateUniqueId();
        }
        String uniqueId;
        try (FileInputStream fis = new FileInputStream(archivo)) {
            Properties arcPro = new Properties();
            arcPro.load(fis);
            uniqueId = arcPro.getProperty("uniqueId");
        }
        return uniqueId;
    }

    public final void goToURL(String URL) {
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                try {
                    java.net.URI uri = new java.net.URI(URL);
                    desktop.browse(uri);
                } catch (IOException | URISyntaxException e) {
                    System.out.println("error obteniendo la url " + e);
                }
            }
        }
    }

}
