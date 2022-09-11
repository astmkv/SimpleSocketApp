package com.company;

import com.company.sockets.CSocket;
import com.company.sockets.SSocket;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        // потоки сервера и клиента
        Thread serverThread = new Thread(() -> SSocket.startServer("127.0.0.1", 1024));
        Thread clientThread = new Thread(() -> CSocket.startClient("127.0.0.1", 1024));

        // запустим: сначала сервер
        serverThread.start();
        Thread.sleep(1000);
        clientThread.start();

        // ожидаем
        serverThread.join();
        clientThread.join();


    }
}
// клиент не подключается, сервер в крутится в постоянном ожидании