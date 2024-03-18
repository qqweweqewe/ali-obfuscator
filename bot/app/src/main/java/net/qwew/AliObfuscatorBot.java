package net.qwew;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class AliObfuscatorBot extends TelegramLongPollingBot {

    public AliObfuscatorBot() {
        super(new DefaultBotOptions(), AliBotToken.token);
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
        Message message = update.getMessage();
        String text = message.getText();

        SendMessage response = new SendMessage();
        response.setChatId(message.getChatId().toString());
        response.setText("Hello, " + message.getFrom().getFirstName() + "! You sent: " + text);

        try {
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
