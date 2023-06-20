
// Necessary imports
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

// Class that will handle everything needed to prompt openAI
public class OpenAI {

    // Method that takes a string which it will send to openAI and returns answer
    public static void openAI(String text) throws Exception {

        // This creates a URL object representing the OpenAI API endpoint and opens a
        // connection to it using the HttpURLConnection class
        String url = "https://api.openai.com/v1/completions";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        // POST is one of the methods of getting data from a form in HTML/PHP
        con.setRequestMethod("POST");

        // Sets two request headers: "Content-Type" to "application/json" and
        // "Authorization" to an API key
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer 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");

        // This creates a new JSONObject and sets several properties, including the
        // OpenAI model to use,
        // the prompt to generate a response for, and the maximum number of tokens to
        // generate
        JSONObject data = new JSONObject();
        data.put("model", "text-davinci-003");
        data.put("prompt", text);
        data.put("max_tokens", 4000);
        data.put("temperature", 0.7);

        // doOutput: true indicates that this request will return data
        con.setDoOutput(true);
        // Parsing data through as a String
        con.getOutputStream().write(data.toString().getBytes());

        // This creates a new JSONObject from the response string, retrieves the
        // "choices" JSONArray,
        // retrieves the first JSONObject in the array, and retrieves the "text"
        // property as a String.
        // This String is then printed to the console
        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines().reduce((a, b) -> a + b)
                .get();

        System.out.println(new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text"));
    }

    public static void main(String[] args) throws Exception {
        // Calling the method created above
        openAI("Hello, how are you?");
    }
}