package net.qwew;

import java.io.IOException;
import java.util.HashMap;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AliObfuscatorBot extends TelegramLongPollingBot {

    private HashMap<Long, Integer> messageIdsMap = new HashMap<>();

    public AliObfuscatorBot() {
        super(new DefaultBotOptions(), AliBotToken.token);
    }

    private String obfuscate(String message) {
        
        OkHttpClient client = new OkHttpClient();

        // Create a Request object
        String body = "{\"prompt\": \"" + message + "\"}";

        Request request = new Request.Builder()
                .url("http://localhost:8080/api/v1/obfuscate")
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        // Create a Call object
        Call call = client.newCall(request);

        // Execute the request and receive the response
        try{
            System.out.println(body);
            return call.execute().body().string();
        }
        catch(IOException e) {
            return "{\"error\": \"" + e.toString() + "\"}";
        }
    }

    @Override
    public String getBotToken() {
        return AliBotToken.token;
    }

    @Override
    public String getBotUsername() {
        return "AliObfuscatorBot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        
        SendMessage message = new SendMessage();

        message.setChatId(update.getMessage().getChatId());
        message.setText("doing th e funi...");

        try {
            Message sentMessage = execute(message);
            long chatId = update.getMessage().getChatId();
            int messageId = sentMessage.getMessageId();

            messageIdsMap.put(chatId, messageId);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        EditMessageText editedMessage = new EditMessageText();
        
        //parsing json response
        Gson gson = new Gson();
        HashMap<String, Object> obfuscated = gson.fromJson(obfuscate(update.getMessage().getText()), HashMap.class);
        System.out.println(obfuscated);

        editedMessage.setMessageId(messageIdsMap.get(update.getMessage().getChatId()));
        editedMessage.setChatId(update.getMessage().getChatId());
        editedMessage.setText((String) obfuscated.get("obfuscated"));

        try {
            execute(editedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
