package com.mikhadyuk.scholarshipcalculator.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnection {
    private static ServerConnection instance;

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 8080;

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private ServerConnection() {
    }

    public static ServerConnection getInstance() {
        if (instance == null) {
            instance = new ServerConnection();
            instance.connect();
        }
        return instance;
    }

    private void connect() {
        try {
            socket = new Socket(ADDRESS, PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            instance = null;
            outputStream = null;
            inputStream = null;
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        instance = null;

        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ex) {
            }
        }

        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException ex) {
            }
        }

        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException ex) {
            }
        }
    }

    public void send(Object object) throws IOException {
        if (outputStream != null) {
            outputStream.writeObject(object);
            outputStream.flush();
            outputStream.reset();
        }
    }

    public Object receive() {
        Object object = null;
        if (inputStream != null) {
            try {
                object = inputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    public boolean isConnected() {
        return instance != null;
    }
}
