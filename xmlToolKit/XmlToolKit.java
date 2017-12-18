package com.cherrypicks.tonywong.myapplication.xmlTools;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Xml;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Tony Wong on 12/12/2017.
 */
public class XmlToolKit {
    InputStream is;
    Activity activity;
    //Map<String, Map<String, String>> dictionaryArray;
    //Map<String, String> attributeArray;

    public void Initialize(String fileName, Activity activity){
        AssetManager assetManager = activity.getAssets();
        XmlPullParser pullParser = Xml.newPullParser();
        JSONObject jsonObj = null;

        try {
            is = assetManager.open(fileName); //讀取檔案
            pullParser.setInput(is, "utf-8");  //設定語系
        }catch (IOException e) {

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        if (is != null){
            XmlToJson xmlToJson = new XmlToJson.Builder(is, null).build();
            jsonObj = xmlToJson.toJson();
            JsonToXml jsonToXml = new JsonToXml.Builder(jsonObj).build();
            // Converts to a simple XML String
            String xmlString = jsonToXml.toString();

            // Converts to a formatted XML String
            int indentationSize = 3;
            String formattedXml = jsonToXml.toFormattedString(indentationSize);
            try {
                FileOutputStream outputStream = new FileOutputStream("/sdcard/"+ fileName);
                outputStream.write(formattedXml.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void Open(String fileName) throws FileNotFoundException {
        int i= 0;
        XmlPullParser pullParser = Xml.newPullParser();
        //因為將test.xml放在/assets之下，所以必須以讀取檔案的方式來處理
        AssetManager assetManager = activity.getAssets();
        //*Don't* hardcode "/sdcard"
        File sdcard = Environment.getExternalStorageDirectory();

        //Get the text file
        File file = new File(sdcard,fileName);
        FileInputStream fileInputStream = new FileInputStream(file);

        try
        {
            //is = assetManager.open("test.xml"); //讀取檔案
            pullParser.setInput(fileInputStream , "utf-8");  //設定語系
            //利用eventType來判斷目前分析到XML是哪一個部份
            int eventType = pullParser.getEventType();
            //XmlPullParser.END_DOCUMENT表示已經完成分析XML
            while(eventType != XmlPullParser.END_DOCUMENT)
            {
                i++;
                //XmlPullParser.START_TAG表示目前分析到的是XML的Tag，如<title>
                if (eventType == XmlPullParser.START_TAG) {
                    String name = pullParser.getName();
                    //Print the tag to the textView
                    //tv01.setText(tv01.getText() + ", " + name);
                }
                //XmlPullParser.TEXT表示目前分析到的是XML Tag的值，如：台南美食吃不完
                if (eventType== XmlPullParser.TEXT) {
                    String value = pullParser.getText();
                    // Print tag's content to the textView
                    //tv02.setText(tv02.getText() + ", " + value);
                }
                //分析下一個XML Tag
                eventType = pullParser.next();
            }
        } catch (IOException e) {
            //Print the tag to the textView
            //tv02.setText(e.toString());
        } catch (XmlPullParserException e) {
            // Print tag's content to the textView
            //tv02.setText(e.toString());
        }
    }


    public void Create(String fileName, JSONObject saveData){
        JsonToXml jsonToXml = new JsonToXml.Builder(saveData).build();
        // Converts to a simple XML String
        String xmlString = jsonToXml.toString();

        // Converts to a formatted XML String
        int indentationSize = 3;
        String formattedXml = jsonToXml.toFormattedString(indentationSize);

        File file = new File(activity.getFilesDir(), fileName);

        try {
            FileOutputStream outputStream = new FileOutputStream("/sdcard/"+ fileName);
            outputStream.write(formattedXml.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Save(String fileName, JSONObject saveData){
        JsonToXml jsonToXml = new JsonToXml.Builder(saveData).build();
        // Converts to a simple XML String
        String xmlString = jsonToXml.toString();

        // Converts to a formatted XML String
        int indentationSize = 3;
        String formattedXml = jsonToXml.toFormattedString(indentationSize);

        try {
            FileOutputStream outputStream = new FileOutputStream("/sdcard/"+ fileName);
            outputStream.write(formattedXml.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject ReturnJsonObj(){
        JSONObject jsonObj = null;
        if (is != null){
            XmlToJson xmlToJson = new XmlToJson.Builder(is, null).build();
            jsonObj = xmlToJson.toJson();
            return jsonObj;
        }

        return jsonObj;
    }


}
