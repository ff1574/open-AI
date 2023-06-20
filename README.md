# OpenAIGUI
This Java program provides a graphical user interface (GUI) for interacting with the OpenAI API. It allows you to ask questions or provide prompts to the OpenAI model and receive generated responses.

## Prerequisites
* Java Development Kit (JDK)
* OpenAI API key (to be inserted in the code)
## Getting Started
* Clone or download the repository.
* Insert your OpenAI API key in the code: [INSERT API KEY HERE].
* Build and run the program using a Java IDE or command line.
## Usage
* The GUI window will appear with a text field, a button, and a text area.
* Enter your question or prompt in the text field.
* Adjust the temperature and max tokens sliders to customize the response generation.
* Click the "Ask AI" button to send your input to the OpenAI API and retrieve the generated response.
* The generated response will be displayed in the text area.
## GUI Components
Text Field: Enter your question or prompt in this field.
Button: Click this button to send your input and receive the generated response.
Text Area: Displays the generated responses. It supports scrolling and word wrapping.
Slider Controls
Temperature Slider: Adjusts the temperature value for response generation. Higher values (closer to 10) result in more random responses, while lower values (closer to 1) make the responses more focused and deterministic.
Max Tokens Slider: Controls the maximum number of tokens to generate in the response. Moving the slider changes the max_tokens value, limiting the length of the generated text.
## Customization
You can modify the default values for openAITemperature and openAIMaxTokens at the beginning of the OpenAIGUI class to change the initial settings for temperature and maximum tokens.
The OpenAI model used in this program is set to "text-davinci-003". If desired, you can change the model by modifying the value of the model field in the openAI method.
Note: It is important to adhere to the OpenAI usage policy and terms while using this program.
