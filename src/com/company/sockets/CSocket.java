package com.company.sockets;


import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

// класс активного сокета
public class CSocket {

    // метод запуска клиента
    // вход: ip + порт сервера


    @NotNull
    public static String choice() {
        int x = (int) (Math.random() * ((5) + 1));
        if (x == 1) {
            return "exit";
        } else return "give me a quote";
    }

    public static void startClient(String serverIpStr, int serverPort) {

        // ресурсы
        Socket client = null;           // сокет клиента
        BufferedReader in = null;       // поток чтения
        PrintWriter out = null;
        String msg;
        // блок работы
        try {
            // 1. создаем сокет клиента + подключение к серверу
            client = new Socket(serverIpStr, serverPort);
            System.out.println("Client created and connected to remote server");
            // если указываем данные в конструкторе, то подключение происходит сразу
            // иначе необходимо использовать метод connect();

            String clientMsg;
//                msg = "You connected, connection time: " + LocalDateTime.now();
            while (true) {
                clientMsg = choice();

                out = new PrintWriter(
                        new BufferedWriter(
                                new OutputStreamWriter(client.getOutputStream())
                        ),
                        true               // ???????
                );
                out.println(clientMsg);                   // отправим сообщение серверу

                // 2. читаем сообщение от сервера
                in = new BufferedReader(
                        new InputStreamReader(client.getInputStream())
                );
                msg = in.readLine();
                if (!clientMsg.equals("exit")) {
                    System.out.println("client : Server ask " + msg);
                } else {
                    System.out.println("client: Ending client ...");
                    break;}
                }
        } catch (Exception ex) {
            System.out.println("client: Something wrong: " + ex.getMessage());
        } finally {
            try {
                if (client != null && !client.isClosed()) {                      // проверка и закрытие сокета клиента
                    client.close();
                }
            } catch (Exception e) {
                System.out.println("Что-то случилось в блоке finally: " + e.getMessage());
            }
        }

    }
}
