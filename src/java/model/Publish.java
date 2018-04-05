/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.PostUpdate;
import facebook4j.PrivacyBuilder;
import facebook4j.PrivacyParameter;
import facebook4j.PrivacyType;
import facebook4j.User;
import facebook4j.auth.AccessToken;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import ley.entidades.cLey;

/**
 *
 * @author foqc
 */
public class Publish {

    public static void publicar(cLey p) throws FacebookException, ParseException, FileNotFoundException {
        ConfigureFacebook conFace = new ConfigureFacebook();
        conFace.loadParams();

        PrivacyParameter privacy = new PrivacyBuilder().setValue(PrivacyType.ALL_FRIENDS).build();
        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(ConfigureFacebook.getAppId(), ConfigureFacebook.getAppSecret());
        facebook.setOAuthAccessToken(new AccessToken(ConfigureFacebook.getAccessToken(), null));

        if (!fechaActualAniadidaAntesFechaDada(ConfigureFacebook.getFechaAExpirar(), 5)) {//fecha actual sumado los dias sigue siendo menor que la fecha de expiracion
            System.out.println("Actualizando token...");
            conFace.saveParamChanges(ConfigureFacebook.getAccessToken(), facebook);//estiendo el tiempo de vida del token de acceso
        }

        System.out.println("Mis datos");
        try {
            User user = facebook.getMe();
            System.out.println("Mi nombre: " + user.getName());            
            System.out.print(ConfigureFacebook.getAccessToken());
            PostUpdate post = new PostUpdate(new URL("http://www.espoch.edu.ec"))
                    .name(p.getTitulo()).caption(p.getSituacion())
                    .description(p.getSolucion())
                    .message(p.getTitulo());
            //String postId = facebook.postFeed("coopnadech",post);
            String postId = facebook.postFeed(post);
            //facebook.likePost(postId);
            //facebook.likePost(post.getCaption());
        } catch (FacebookException | MalformedURLException e) {
            System.err.println("Error: " + e.getMessage());

        }
    }

    public static Date stringToDate(String date) throws ParseException {
        // trabaja con formato fecha 1988-05-08
        //System.out.println("fecha original " + date + " fecha nueva "+ date1);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        java.util.Date newDate = format.parse(date);
        return newDate;
    }

    public static Boolean fechaActualAniadidaAntesFechaDada(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendarActual = Calendar.getInstance();
        calendar.setTime(fecha);
        calendarActual.add(Calendar.DAY_OF_YEAR, dias);
        return calendarActual.getTime().before(calendar.getTime());
    }

    public static Date sumarRestarDiasFecha(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos

    }

    public static String formateDate(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());
        return formattedDate;
    }
}
