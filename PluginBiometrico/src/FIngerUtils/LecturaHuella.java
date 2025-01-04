/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FIngerUtils;

import Helper.Utils;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

/**
 *
 * @author Mauricio Herrera
 */
public final class LecturaHuella extends javax.swing.JFrame {

    FileInputStream fis;
    int longitudBytes;
    // objetos propios de las librerias del lector
    private final DPFPCapture lector = DPFPGlobal.getCaptureFactory().createCapture();
    private final DPFPEnrollment reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private final DPFPVerification verificador = DPFPGlobal.getVerificationFactory().createVerification();
    private DPFPTemplate template;
    private static final String TEMPLATE_PROPERTY = "template";
    public DPFPFeatureSet featuresVerification;
    private finger_temp fingerTemp;
    private Image imageHuella;
    private String texto;
    private String statusCapture;
    private String serial;
    private String documento;
    private String nombre;
    private String dedo;
    private String mensaje = "";

    public LecturaHuella() throws AWTException {
        initComponents(); 
        setIconImage(new ImageIcon(getClass().getResource("/fingerUtils/Fingerprint.png")).getImage());
        Robot r = new Robot();
        int tamX = getWidth();
        int tamY = getHeight();
        int maxX = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int maxY = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        r.mouseMove(maxX + 250 - tamX, maxY - tamY + 10);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 264, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LecturaHuella.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LecturaHuella.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LecturaHuella.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LecturaHuella.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new LecturaHuella().setVisible(true);
                } catch (AWTException ex) {
                    System.out.println("Erro " + ex.getMessage());
                }
            }
        });
    }

    public void stop() {
        lector.stopCapture();
    }

    public void Iniciar() {
        lector.addDataListener(new DPFPDataAdapter() {
            @Override
            public void dataAcquired(final DPFPDataEvent e) {
                SwingUtilities.invokeLater(() -> {
                    try {
                        setTexto("La Huella dactilar ha sido capturada");
                        ProcesarCaptura(e.getSample());
                        identificarHuella();
                        reclutador.clear();
                    } catch (IOException ex) {
                    } catch (Exception ex) {
                        Logger.getLogger(LecturaHuella.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        });

        lector.addReaderStatusListener(new DPFPReaderStatusAdapter() {
            @Override
            public void readerConnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        setTexto("El sensor esta activado o conectado..");
                    }
                });
            }

            @Override
            public void readerDisconnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        setTexto("El sensor esta desactivado o no conectado..");
                    }
                });
            }
        });

        lector.addSensorListener(new DPFPSensorAdapter() {

            @Override
            public void fingerTouched(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        setTexto("El dedo ha sido colocado sobre el lector.!");
                    }
                });
            }

            @Override
            public void fingerGone(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        setTexto("El dedo ha sido quitado del lector.!");
                    }
                });
            }

        });

        lector.addErrorListener(new DPFPErrorAdapter() {

            public void errorReader(final DPFPErrorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        setTexto("Ocurrio un erro con el lector..!");
                    }
                });
            }
        });

    }

    private void setTexto(String texto) {
        this.texto = texto;
    }

    private void ProcesarCaptura(DPFPSample sample) throws IOException, Exception {
        featuresVerification = extraerCaracteristicasHuella(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
        if (featuresVerification != null) {
            try {
                reclutador.addFeatures(featuresVerification);
                Image image = CrearImagenHuella(sample);
                setImageHuella(image);
            } catch (DPFPImageQualityException e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                switch (reclutador.getTemplateStatus()) {
                    case TEMPLATE_STATUS_READY:
                        System.out.println("Plantilla ok");
                        break;
                    case TEMPLATE_STATUS_FAILED:
                        reclutador.clear();
                        stop();
                        setStatusCapture();
                        setTemplate(null);
                        System.out.println("La plantilla no pudo ser creada");
                        start();
                        break;
                }
            }
        }
    }

    //metodo Identificar huella
    private void identificarHuella() {
        try {
            mensaje = "El usuario no existe";
            getFingerTemp();
            Utils u = new Utils();
            //poner este condigo en un bucle
            String rs = fingerTemp.listaHuellas(u.getUniqueId());

           
            JsonParser parser = new JsonParser();
            JsonArray list = parser.parse(rs).getAsJsonArray();

            for (JsonElement jsonElement : list) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate();
                byte[] templateBuffer = Base64.getDecoder().decode(jsonObject.get("huella").getAsString());

                referenceTemplate.deserialize(templateBuffer);
                DPFPVerificationResult resultado = verificador.verify(featuresVerification, referenceTemplate);

                if (resultado.isVerified()) {
                    mensaje = "Usiario Verificado";
                    serial = jsonObject.get("pc_serial").getAsString();
                    documento = jsonObject.get("documento").getAsString();
                    nombre = jsonObject.get("nombre_completo").getAsString();
                    dedo = jsonObject.get("nombre_dedo").getAsString();
                    break;
                }
            }
            fingerTemp = null;
            getFingerTemp();
            fingerTemp.setSerial(u.getUniqueId());
            fingerTemp.setImageHuella(getEncodeImage(getImageHuella()));
            fingerTemp.setTexto(getTexto());
            fingerTemp.setStatusPlantilla(mensaje);
            fingerTemp.setDedo(dedo);
            fingerTemp.setOption("verificar");

            if (mensaje.contains("no existe")) {
                fingerTemp.setNombre("------");
                fingerTemp.setDocumento("----");
            } else {
                fingerTemp.setNombre(nombre);
                fingerTemp.setDocumento(documento);
            }

            String object = new Gson().toJson(fingerTemp);
            fingerTemp.actualizarHuella(object);
            fingerTemp = null;

        } catch (JsonSyntaxException | IOException | IllegalArgumentException e) {
        }
    }

    private DPFPFeatureSet extraerCaracteristicasHuella(DPFPSample sample, DPFPDataPurpose dpfpDataPurpose) {
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        try {
            return extractor.createFeatureSet(sample, dpfpDataPurpose);
        } catch (DPFPImageQualityException e) {
            System.out.println("error generando caracteristicas: " + e.getMessage());
            return null;
        }
    }

    private Image CrearImagenHuella(DPFPSample sample) {
        return DPFPGlobal.getSampleConversionFactory().createImage(sample);
    }

    private void setImageHuella(Image image) {
        this.imageHuella = image;
    }

    private finger_temp getFingerTemp() {
        if (fingerTemp == null) {
            fingerTemp = new finger_temp();
        }
        return fingerTemp;
    }

    private String getEncodeImage(Image imageHuella) {
        ImageIcon icon = new ImageIcon(imageHuella);
        BufferedImage image = new BufferedImage(450, 500, BufferedImage.TYPE_INT_RGB);
        byte[] imageInByte = null;
        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(imageHuella, 0, 0, icon.getImageObserver());
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
        } catch (IOException e) {
            System.out.println("error al crear la imagen " + e.getMessage());
        }
        return Base64.getEncoder().encodeToString(imageInByte);
    }

    private Image getImageHuella() {
        return imageHuella;
    }

    private String getTexto() {
        return texto;
    }

    private String getStatusCapture() {
        return statusCapture;
    }

    private void setStatusCapture() {
        this.statusCapture = "Muestras Restantes: " + reclutador.getFeaturesNeeded();
    }

    private void setTemplate(DPFPTemplate template) {
        DPFPTemplate old = this.template;
        this.template = template;
        firePropertyChange(TEMPLATE_PROPERTY, old, template);
    }

    public void start() {
        lector.startCapture();
        setTexto("Utilizando el lector de huella dactilar");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
