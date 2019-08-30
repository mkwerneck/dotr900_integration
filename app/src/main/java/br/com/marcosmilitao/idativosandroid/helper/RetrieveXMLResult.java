package br.com.marcosmilitao.idativosandroid.helper;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.*;
import java.net.*;

import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by marcoswerneck on 18/12/17.
 */

public class RetrieveXMLResult extends AsyncTask {

    public ArrayList<XR400ReadTags> arrayDeTags = new ArrayList<XR400ReadTags>();
    private String ip;

    public RetrieveXMLResult(String ip){
        this.ip = ip;
    }

    @Override
    protected Object doInBackground(Object[] objects){

        try {

            String lastEvent = null;

            URL getTagsURL = new URL("http://" + ip + "/cgi-bin/dataProxy?oper=queryEvents");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(getInputStream(getTagsURL), "UTF_8");

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equalsIgnoreCase("Tag")) {
                        XR400ReadTags xr400tag = new XR400ReadTags();
                        for (int i = 0; i < xpp.getAttributeCount(); i++) {
                            switch (xpp.getAttributeName(i)) {
                                case "raw":
                                    xr400tag.setTagid(xpp.getAttributeValue(i));
                                    break;
                                case "event":
                                    lastEvent = xpp.getAttributeValue(i);
                                    break;
                                default:
                                    break;
                            }
                        }

                        if (lastEvent.equals("0")){
                            arrayDeTags.add(xr400tag);
                        }
                    }
                }
                eventType = xpp.next();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return arrayDeTags;
    }

    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    public ArrayList<XR400ReadTags> ArraydeTags()
    {
        return arrayDeTags;
    }

    public void ClearBuffer() throws Exception{
        URL cleanBufferURL = new URL("http://" + ip + "/cgi_bin/dataProxy?");
        HttpURLConnection con = (HttpURLConnection) cleanBufferURL.openConnection();

        //adicionando cabeçalho no request
        con.setRequestMethod("POST");

        String urlParametros = "oper=purgeAllTags&clean=1&events=1";

        //Enviar request POST
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParametros);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();

        Log.d("HTTPRESPONSE", response.toString());
    }

}
