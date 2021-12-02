package com.gosanon.javabotexample.main;

import com.gosanon.javabotexample.api.transports.ITransport;
import com.gosanon.javabotexample.api.transports.implementations.TgTransport;

public class Main {
    public static void main(String[] args) {
        // init
        String TOKEN = System.getenv("JAVABOT_TOKEN_TG");
        System.out.println(TOKEN);

        String startMessage = String.join("\n"
            ,"Пишите /quiz *число вопросов* для игры в викторину."
            , ""
            , "Подробнее: /help");

        String helpMessage = String.join("\n"
            , "Бот находится в разработке. Список команд:"
            , ""
            , "/quiz число вопросов - запускает мини-викторину со случайными вопросами на английском."
            , ""
            , "Вопросы бывают разной сложности и оцениваются разным числом очков."
            , "Пишите ответ и в конце викторины узнаете, сколько очков Вы заработали."
            , ""
            , "При желании можете писать боту любые сообщения - он просто перешлёт их обратно Вам.");



        ITransport tgBot = new TgTransport(TOKEN);

        System.out.println("PROGRAM STARTED!");

        tgBot
            .addCommandHandler("/start", ctx -> ctx.reply(startMessage))
            .addCommandHandler("/help", ctx -> ctx.reply(helpMessage))

            .addContextHandler("Copy message if not answered by commands",
                ctx -> ctx.notYetReplied()
                    ? ctx.reply(ctx.newMessage.getMessageText())
                    : ctx
            )

            .startBot();

        /*

        // TEST
        var a = QuestionsProvider.nextQuestion();
        System.out.println(a.Question);
        System.out.println(a.Answer);
        System.out.println(a.Value);

        */
    }
}
