/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FIngerUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 *
 * @author Mauricio Herrera
 */
public class finger_temp {

    private String serial;
    private String huella;
    private String imageHuella;
    private String texto;
    private String statusPlantilla;
    private String documento;
    private String nombre;
    private String dedo;
    private String option;

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String SERVER_PATH = "http://localhost/hulla/DemoFingerPrint/Model/UsuarioRestApi.php";

    public finger_temp() {
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getHuella() {
        return huella;
    }

    public void setHuella(String huella) {
        this.huella = huella;
    }

    public String getImageHuella() {
        return imageHuella;
    }

    public void setImageHuella(String imageHuella) {
        this.imageHuella = imageHuella;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getStatusPlantilla() {
        return statusPlantilla;
    }

    public void setStatusPlantilla(String statusPlantilla) {
        this.statusPlantilla = statusPlantilla;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDedo() {
        return dedo;
    }

    public void setDedo(String dedo) {
        this.dedo = dedo;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public boolean asociarHuella(String data) {
        boolean r = false;
        try {
            URL url = new URL(SERVER_PATH);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestProperty("User-Agent", USER_AGENT);
            httpCon.setRequestProperty("Acept", "*/*");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("POST");

            try (OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream())) {
                out.write(data);
            }

            BufferedReader response = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
            String str = response.readLine();
            while (str != null) {
                System.err.println(str);
                str = response.readLine();
            }
            r = true;
        } catch (IOException e) {
            System.out.println("Error guardando Huella " + e.getMessage());
        }
        return r;
    }

    public boolean actualizarHuella(String data) {
        boolean respuesta = false;
        try {
            URL url = new URL(SERVER_PATH);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestProperty("User-Agent", USER_AGENT);
            httpCon.setRequestProperty("Acept", "*/*");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");

            try (OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream())) {
                out.write(data);
            }

            BufferedReader response = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));

            String r = response.readLine();
            while (r != null) {
                System.out.println(r);
                r = response.readLine();
            }

            respuesta = true;
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
        }
        return respuesta;
    }

    public String listaHuellas(String serial) throws UnsupportedEncodingException, MalformedURLException, IOException {
        StringBuilder stb = new StringBuilder(SERVER_PATH);
        stb.append("?token=");
        stb.append(URLEncoder.encode(serial, "UTF-8"));

        URL url = new URL(stb.toString());

        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

        httpCon.setRequestProperty("User-Agent", USER_AGENT);
        httpCon.setRequestProperty("Acept-Charset", "UTF-8");
        httpCon.setRequestMethod("GET");

        System.out.print("Response Code: " + httpCon.getResponseCode());
        System.out.print("Response Code: " + httpCon.getResponseMessage());

        StringBuilder respuesta;

        try (BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()))) {
            String linea;
            respuesta = new StringBuilder();

            while ((linea = in.readLine()) != null) {
                respuesta.append(linea);
            }
        }

        return respuesta.toString();

    }

}
