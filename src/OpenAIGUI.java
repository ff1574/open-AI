import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class OpenAIGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("OpenAI - Honors Program - Franko Fister");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a text field to take input
        JTextField textField = new JTextField(40);

        // Create a text area to display generated responses, style it
        JTextArea textArea = new JTextArea(30, 40);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        // Create a temperature slider
        JSlider temperatureSlider = new JSlider(1, 10, 7);
        temperatureSlider.setMajorTickSpacing(1);
        temperatureSlider.setPaintTicks(true);
        temperatureSlider.setPaintLabels(true);
        temperatureSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    // Set the temperature value based on the slider position
                    double temperature = source.getValue() / 10.0;
                    // Update the temperature value in the openAI method
                    // (add the following line to the method: data.put("temperature", temperature);)
                    openAITemperature = temperature;
                }
            }
        });

        // Create a max_tokens slider
        JSlider maxTokensSlider = new JSlider(0, 4000, 1000);
        maxTokensSlider.setMajorTickSpacing(1000);
        maxTokensSlider.setPaintTicks(true);
        maxTokensSlider.setPaintLabels(true);
        maxTokensSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    // Set the max_tokens value based on the slider position
                    int maxTokens = source.getValue();
                    // Update the max_tokens value in the openAI method
                    // (add the following line to the method: data.put("max_tokens", maxTokens);)
                    openAIMaxTokens = maxTokens;
                }
            }
        });

        // Create a button to prompt openAI
        JButton button = new JButton("Ask AI");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = textField.getText();
                    String output = openAI(input);

                    // Display the generated response in a text area
                    textArea.setText(output);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(textArea);

        // Add the text field, button, and text area to the frame
        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(button);

        panel.add(new JLabel("Temperature:"));
        panel.add(temperatureSlider);
        panel.add(new JLabel("Max Tokens:"));
        panel.add(maxTokensSlider);

        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Display the frame
        frame.pack();
        frame.setVisible(true);
    }

    private static double openAITemperature = 0.7;
    private static int openAIMaxTokens = 2000;

    // Method that takes a string which it will send to openAI and returns answer
    public static String openAI(String text) throws Exception {
        // This creates a URL object representing the OpenAI API endpoint and opens a
        // connection to it using the HttpURLConnection class
        String url = "https://api.openai.com/v1/completions";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        // POST is one of the methods of getting data from a form in HTML/PHP
        con.setRequestMethod("POST");

        // Sets two request headers: "Content-Type" to "application/json" and
        // "Authorization" to an API key
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer [INSERT API KEY HERE]");

        // This creates a new JSONObject and sets several properties, including the
        // OpenAI model to use,
        // the prompt to generate a response for, and the maximum number of tokens to
        // generate
        JSONObject data = new JSONObject();

        if (openAIMaxTokens == 0)
            openAIMaxTokens = 1;

        data.put("model", "text-davinci-003");
        data.put("prompt", text);
        data.put("max_tokens", openAIMaxTokens);
        data.put("temperature", openAITemperature);

        // doOutput: true indicates that this request will return data
        con.setDoOutput(true);
        // Parsing data through as a String
        con.getOutputStream().write(data.toString().getBytes());

        // This creates a new JSONObject from the response string, retrieves the
        // "choices" JSONArray,
        // retrieves the first JSONObject in the array, and retrieves the "text"
        // property as a String.
        // This String is then returned
        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines().reduce((a, b) -> a + b)
                .get();

        return new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text");
    }
}