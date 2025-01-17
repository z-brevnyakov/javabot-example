package com.gosanon.javabotexample;

import com.gosanon.javabotexample.api.scenario.Scene;
import com.gosanon.javabotexample.api.scenario.Scenario;
import com.gosanon.javabotexample.api.store.IUserStateManager;
import com.gosanon.javabotexample.api.store.implementations.RuntimeStateManager;
import com.gosanon.javabotexample.transports.implementations.TestStringTransport;
import org.junit.jupiter.api.Test;

import static com.gosanon.javabotexample.main.CommonHandlers.*;


class ClassicScenarioTest {
    String startMessage = "This bot does something. Type /help for help.";
    String helpMessage = "Sorry, no help for strangers.";
    String firstSwitchMessage = "Switched to SecondState.";
    String secondSwitchMessage = "Switched to DefaultState";
    String photoURL = "https://ru.wikipedia.org/wiki/" +
            "%D0%9A%D0%BE%D1%88%D0%BA%D0%B0#/media/%D0%A4%D0%B0%D0%B9%D0%BB:Cat_poster_1.jpg";

    IUserStateManager store = new RuntimeStateManager("DefaultState");
    TestStringTransport testTransport = new TestStringTransport();

    Scenario classicScenario = new Scenario.Builder()
        .addScene(new Scene("DefaultState")
            .addCommandHandler("/start", reply(startMessage))
            .addCommandHandler("/help", reply(helpMessage))
            .addCommandHandler("/to_second_state",
                replyAndSetState(firstSwitchMessage, "SecondState"))
            .addCommandHandler("/testPhoto", ctx -> ctx.sendPhoto(photoURL))
            .addContextHandler(notAnsweredThenCopy())
        )
        .addScene(new Scene("SecondState")
            .addCommandHandler("/to_default_state",
                replyAndSetState(secondSwitchMessage, "DefaultState"))
            .addContextHandler(notAnsweredThenCopy())
        )
        .build()
        .addTransport(testTransport)
        .setUserStateManager(store)
        .init();

    @Test
    public void testWithoutUserMessages() {
        String[] userInput = {};
        String[] expectedOutput = {};
        testTransport.expectBehaviour(userInput, expectedOutput);
    }

    @Test
    public void testOnKnownCommands() {
        String[] userInput = { "/start", "/help" };
        String[] expectedOutput = {
            "This bot does something. Type /help for help.",
            "Sorry, no help for strangers."
        };
        testTransport.expectBehaviour(userInput, expectedOutput);
    }

    @Test
    public void testCopyMessageOnUnknownInput() {
        String[] userInput = { "123", "Some other text" };
        String[] expectedOutput = { "123", "Some other text" };
        testTransport.expectBehaviour(userInput, expectedOutput);
    }

    @Test
    public void testTransportSwitchesStates() {
        String[] userInput = { "/help", "/to_second_state", "/help", "/to_default_state", "/help" };
        String[] expectedOutput = { helpMessage, firstSwitchMessage, "/help", secondSwitchMessage, helpMessage };
        testTransport.expectBehaviour(userInput, expectedOutput);
    }

    @Test
    public void testPhotoSending(){
        String[] userInput = { "/testPhoto" };
        String[] expectedOutput = { "Фото отправлено, адрес url: " + photoURL, "/testPhoto" };
        testTransport.expectBehaviour(userInput, expectedOutput);
    }
}