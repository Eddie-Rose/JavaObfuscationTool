import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;
import java.math.BigInteger;

public class DataObfuscation {

    private JSONArray finalJsonObj = null;

    public DataObfuscation(){}

    /**
     *Encrypts a Json file using a fixed encryption method
     * @param file Takes in a json file to encrypt the file
     */
    public void encryptJsonFile(File file) {

        finalJsonObj = new JSONArray();
        JSONParser parser = new JSONParser();
        System.out.println(file.getAbsolutePath());
        Reader reader = null;

        //Iterate through the json file and encrypts all the objects one by one
        try {
            reader = new FileReader(file.getAbsoluteFile());
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            for(Object o : jsonArray){
                if (o instanceof JSONObject){
                    JSONObject jsonObject = (JSONObject)o;
                    JSONObject encryptedJsonObject;
                    encryptedJsonObject = parseJsonObject(jsonObject);

                    //Add the encrypted json array to the final Json array
                    finalJsonObj.add(encryptedJsonObject);
                }
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            FileWriter writer = new FileWriter(file.getAbsoluteFile());
            writer.write(finalJsonObj.toJSONString());
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *Encypts a json object from the json array of the json file
     * @param jsonObject Takes in a Json object to be encrypted
     * @return  The encrypted Json object
     */
    private JSONObject parseJsonObject(JSONObject jsonObject){
        JSONObject encryptedJsonObject = new JSONObject();
        for (Object entry : jsonObject.entrySet()){
            Map.Entry e = (Map.Entry) entry;
            String name = e.getKey().toString();

            if (name.equals("question")){
                String value = stringEncryption(e.getValue().toString());
                name = stringEncryption(name);
                encryptedJsonObject.put(name,value);
            }
            //replies contains a Json array so the implementation has to be a bit different
            else if (name.equals("replies")){
                JSONArray encryptedArray = encryptJsonArray(e.getValue());
                name = stringEncryption(name);
                encryptedJsonObject.put(name,encryptedArray);

            }
            else if (name.equals("id")){
                String value = stringEncryption(e.getValue().toString());
                name = stringEncryption(name);
                encryptedJsonObject.put(name,value);
            }
            //The json name is unique so it will be a message that needs encrypting
            else {
                String encryptedName = stringEncryption(e.getKey().toString());
                String encryptedValue = stringEncryption(e.getValue().toString());
                encryptedJsonObject.put(encryptedName,encryptedValue);
            }
        }
        return encryptedJsonObject;
    }

    /**
     * Encrypts a json array that is made up of strings
     * @param o Json array
     * @return encrypted Json array
     */
    private JSONArray encryptJsonArray(Object o){
        JSONArray array = (JSONArray)o;
        JSONArray encryptedArray = new JSONArray();

        for(Object object: array){
            encryptedArray.add(stringEncryption(object.toString()));
        }
        return encryptedArray;
    }

    /**
     * Encryption algorithm
     * @param string String to be encrypted
     * @return encrypted string
     */
    private String stringEncryption(String string){
        /*load the image and use the bmp object to access it*/

        BufferedImage img = null;
        try{
            img = ImageIO.read(new File(System.getProperty("user.dir") + "\\src\\main\\resources\\android_icon.png"));
            String x = (System.getProperty("user.dir") + "\\src\\main\\resources\\android_icon.png");
            String xw = "2";
        } catch (IOException e){
            e.printStackTrace();
        }

        //-5586321
        /*pixel value at (105, 100) is the key*/
        Color color = new Color(img.getRGB(105,210));
        int a = color.getRGB();
        BigInteger key = BigInteger.valueOf(color.getRGB());
        //Log.i("key", "key: " + key);

        /*plain text to be encrypted*/
        String plain_text = string;
        BigInteger plainText = new BigInteger(plain_text.getBytes()); //s is the input string

        /*xor key and plain text*/
        String encode = plainText.xor(key).toString();

        BigInteger decode = new BigInteger(encode);
        String result = new String((decode.xor(key)).toByteArray());


        return encode;
    }
}
