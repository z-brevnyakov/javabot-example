package com.gosanon.javabotexample.transports.implementations;

import com.gosanon.javabotexample.transports.CommonTransport;
import com.gosanon.javabotexample.transports.ContextHandler;
import com.gosanon.javabotexample.transports.EventContext;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;

import static java.lang.Integer.parseInt;

public class TgTransport extends CommonTransport {
    // На рамке монитора виднелись красные капли вишнёвого сока. Джава проиграла сегодня.
    // Но мы оба знали. Хоть бой и закончен, но это лишь начало войны. Жестокой. Но неизбежной.
    TelegramBot bot;

    public TgTransport(String TOKEN) {
        bot = new TelegramBot(TOKEN);
    }

    protected void initBot(ContextHandler finalHandler) {
        // INIT BOT
        System.out.println("BOT STARTED!");
        bot.setUpdatesListener(updateList -> {
            updateList.forEach(update -> finalHandler.apply(new EventContext(this, update)));
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    public void sendMessage(String targetId, String messageText) {
        bot.execute(new SendMessage(parseInt(targetId), messageText));
    }
}
