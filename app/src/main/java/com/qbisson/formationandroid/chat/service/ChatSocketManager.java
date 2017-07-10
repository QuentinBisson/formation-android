package com.qbisson.formationandroid.chat.service;

import android.util.Log;

import com.qbisson.formationandroid.SettingUtils;

import java.net.URI;
import java.net.URISyntaxException;

import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatSocketManager {
    public static final String SOCKET_IO_PATH = "/chat-rest/socket.io";
    public static final String SOCKET_IO_NSP = "/2.0/ws";
    public static final String AUTH_REQUIRED = "auth_required";

    private static ChatSocketManager instance;
    private Socket socket;

    private ChatSocketManager() {
        connect();
        listen();
    }

    public static ChatSocketManager getInstance() {
        if (instance == null) {
            instance = new ChatSocketManager();
        }
        return instance;
    }

    public void connect() {
        try {
            Manager.Options options = new Manager.Options();
            options.path = SOCKET_IO_PATH;
            Manager manager = new Manager(new URI(SettingUtils.SERVICE_URL), options);
            socket = manager.socket(SOCKET_IO_NSP);
        } catch (URISyntaxException e) {
            Log.e(ChatService.class.getSimpleName(), "connectInBoundMessage: " + e.toString());
        }

        socket.connect();
    }

    private void listen() {
        socket.on(AUTH_REQUIRED, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.i(ChatService.class.getSimpleName(), AUTH_REQUIRED);
            }
        });
    }

    public void closeSocket() {
        socket.close();
    }
    //https://bitbucket.org/tommybuonomo/excilys-formation-chat/src/9c3dd6325481fe6ea1cc84fafdd791a8449dc667/app/src/main/java/excilys/formationchat/rest/websocket/ChatWebSocketManager.java?at=master&fileviewer=file-view-default
}
