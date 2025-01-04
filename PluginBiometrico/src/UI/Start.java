/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Helper.Utils;
import java.awt.AWTException;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Mauricio Herrera
 */
public class Start {

    public static long timestamp = (new Date().getTime() / 1000);
    public static String srn;
    public static Timer timer = new Timer();

    public static void main(String[] args) {
        try {
            Utils u = new Utils();
            srn = Base64.getEncoder().encodeToString(u.getUniqueId().getBytes());
            u.goToURL("http://localhost/hulla/DemoFingerPrint/index.php?token=" + srn);
            TimerTask tarea = new TimerTask() {
                @Override
                public void run() {
                    try {
                        HabilitarLector hbs = new HabilitarLector();
                        timestamp = hbs.sendGet(timestamp, srn);
                    } catch (AWTException | IOException e) {
                        System.out.println("Error habilitando el sensor " + e.getMessage());
                    }
                }
            };
            timer.schedule(tarea, 0, 1000);
        } catch (IOException e) {
            System.out.println("Ocurrio un error habilitando el sensor " + e.getMessage());
        }
    }
}
