/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.auth.AccessToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;
import static model.Publish.formateDate;
import static model.Publish.stringToDate;
import static model.Publish.sumarRestarDiasFecha;

/**
 *
 * @author foqc
 */
public class ConfigureFacebook {

    private static Date fechaAExpirar;
    private static String appId;
    private static String appSecret;
    private static String accessToken; //"EAAOybki0aU0BABF92GnAZBzFZA5EgIHdLVpCJABZCF62LZAPeKl3pKOB0ZB1VMTTZCFxbflDSHvW8zNR64eZBl0Jjy7sRUooZBNqZCJa1I8HBJv41ohuc7nCAepvU4LtiTR6gM4JkKB4nBeti9WlptobhGSWQmbUXNTiZBTBkbJ4lhsAZDZD";

    public static Date getFechaAExpirar() {
        return fechaAExpirar;
    }

    public static void setFechaAExpirar(Date fechaAExpirar) {
        ConfigureFacebook.fechaAExpirar = fechaAExpirar;
    }

    public static String getAppId() {
        return appId;
    }

    public static void setAppId(String appId) {
        ConfigureFacebook.appId = appId;
    }

    public static String getAppSecret() {
        return appSecret;
    }

    public static void setAppSecret(String appSecret) {
        ConfigureFacebook.appSecret = appSecret;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        ConfigureFacebook.accessToken = accessToken;
    }

    public void loadParams() throws ParseException {
        Properties props = new Properties();
        InputStream is = null;

        // First try loading from the current directory
        try {
            if (is == null) {
                // Try loading from classpath
                is = getClass().getResourceAsStream("facebook.properties");
            }
            // Try loading properties from the file (if found)
            props.load(is);
        } catch (Exception e) {
        }

        appId = props.getProperty("appId");
        appSecret = props.getProperty("appSecret");
        accessToken = props.getProperty("extendedToken");
        fechaAExpirar = stringToDate(props.getProperty("dateExpired"));
    }

    public void saveParamChanges(String oldToken, Facebook facebook) throws FileNotFoundException, FacebookException, ParseException {
        Properties props = new Properties();
        // Crear el objeto archivo
        File archivo = new File(this.getClass().getResource("facebook.properties").getFile());
        //Crear el objeto properties
        FileOutputStream output = new FileOutputStream(archivo.toString().replace("\\", "/"));

        try {
            AccessToken at = facebook.extendTokenExpiration(accessToken);
            accessToken = at.getToken();
            System.out.println("nuevo token: " + accessToken);
            props.setProperty("dateExpired", formateDate(new Date(), 63));
            props.setProperty("appId", appId);
            props.setProperty("appSecret", appSecret);
            props.setProperty("shortLivedToken", oldToken);
            props.setProperty("extendedToken", accessToken);

            // save properties to project root folder
            //props.store(output, "Fichero de Propiedades de la Web");
            props.store(output, "un comentario");
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    loadParams();
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
