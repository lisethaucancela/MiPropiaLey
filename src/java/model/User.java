/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Media;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import java.io.File;

/**
 *
 * @author foqc
 */
public class User {

    public static void main(String[] args) {

        try {

            Facebook facebook = new FacebookFactory().getInstance();
            String userid = null;

            String query = "SELECT uid ,name FROM user WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 = me()) AND is_app_user=1";

            JSONArray jsonArray = facebook.executeFQL(query);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject;
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                    System.out.println(jsonObject);
                    userid = jsonObject.getString("uid");
                    System.out.println(userid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Media m1 = new Media(new File("image loc."));
            String postid = facebook.postPhoto("userid of friend", m1);
            System.out.println(postid);

            facebook.postStatusMessage("userid of friend.", "Hello...");

        } catch (FacebookException e) {

            e.printStackTrace();
        }

    }

}
