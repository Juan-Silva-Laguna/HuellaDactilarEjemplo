/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import FIngerUtils.CapturarHuella;
import FIngerUtils.GetCapturarHuella;
import FIngerUtils.GetLecturaHuella;
import FIngerUtils.LecturaHuella;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 *
 * @author Mauricio Herrera
 */
public class HabilitarLector {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String SERVER_PATH = "http://localhost/hulla/DemoFingerPrint/Model/HabilitarSensor.php";
    private static CapturarHuella frmCapturaHuella;
    private static LecturaHuella frmLecturaHuella;

    public HabilitarLector() {

    }

    public long sendGet(long d, String srn) throws UnsupportedEncodingException, MalformedURLException, IOException, AWTException {
        long timestamp = d;

        StringBuilder stringBuilder = new StringBuilder(SERVER_PATH);
        stringBuilder.append("?timestamp=");
        stringBuilder.append(URLEncoder.encode("" + d, "UTF-8"));
        stringBuilder.append("&token=").append(srn);

        URL obj = new URL(stringBuilder.toString());

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Charset", "UTF-8");
        System.out.println("Response Message : " + con.getResponseMessage());
        int responseCode = con.getResponseCode();

        StringBuilder respuesta;

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        respuesta = new StringBuilder();
        while ((line = in.readLine()) != null) {
            respuesta.append(line);
        }
        con.disconnect();

        if (responseCode == 200) {
            JsonParser parser = new JsonParser();
            JsonObject objJson = parser.parse(respuesta.toString()).getAsJsonObject();
            timestamp = objJson.get("fecha_creacion").getAsLong();
                       
            if (objJson.get("opc").getAsString().equals("capturar")) {
                frmLecturaHuella = GetLecturaHuella.getLecturarHuella();
                frmLecturaHuella.stop();
                frmLecturaHuella.dispose();
                GetLecturaHuella.setLecturarHuella();
                frmCapturaHuella = GetCapturarHuella.getCapturarHuella();
                frmCapturaHuella.stop();
                frmCapturaHuella.Iniciar();
                frmCapturaHuella.start();
            }

            if (objJson.get("opc").getAsString().equals("leer")) {
                frmCapturaHuella = GetCapturarHuella.getCapturarHuella();
                frmCapturaHuella.stop();
                frmCapturaHuella.dispose();
                GetCapturarHuella.setCapturarHuella();
                frmLecturaHuella = GetLecturaHuella.getLecturarHuella();
                frmLecturaHuella.stop();
                frmLecturaHuella.Iniciar();
                frmLecturaHuella.start();
            }
        }

        return timestamp;

    }

}
