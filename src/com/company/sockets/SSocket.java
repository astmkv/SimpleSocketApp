package com.company.sockets;


import com.company.QuoteGen;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

// Класс пассивного сокета
public class SSocket {

    // метод работы сервера
    // вход: ip сервера, порт
    public static void startServer(String ip, int port) {
        System.out.println("Starting server on " + ip + ":" + port + " ...");


        // ресурсы: здесь объявим переменные для дальнейшей работы
        ServerSocket server = null;                          // сокет сервера
        Socket remoteClient = null;                          // сокет клиента
        PrintWriter out = null;                              // поток записи данных в сокет клиента (отправка сообщения)
        String msg;
        BufferedReader in = null;


        // блок работы
        try {
            // описание работы сервера, последовательность действий на стороне сервера

            // 1. создаем сокет для сервера
            server = new ServerSocket(port, 50, InetAddress.getByName(ip));  // объект сокета для сервера:
            System.out.println("Сервер создан");
            // (int port) - конструктор только с портом, ip - локальный
            // (int port, int backlog) - backlog - кол-во допустимых соединений (по-умолчанию: 50)
            // (int port, int backlog, @Nullable InetAddress bindAddr) - bindAddr - ip-адрес - объект InetAddress

            // 2. выполним подключение к клиенту
            // (начнем ожидать подключение)

            System.out.println("Waiting incoming connections ...");
            remoteClient = server.accept();
            // remoteClient - объект, который представляет собой удаленную точку клиента
            // метод accept() возвращает сокет подключенного клиента: remoteClient
            // для дальнейшей работы с конкретным клиентом используется его сокет
            System.out.println("Connected client: " + remoteClient.getInetAddress() + ": " + remoteClient.getPort());

            // 3. отправим сообщение подключившемуся клиенту
            while (true) {
                String serverMsg;
                in = new BufferedReader(
                        new InputStreamReader(remoteClient.getInputStream())
                );
                msg = in.readLine();
                System.out.println("Client ask: " + msg);

                if (!msg.equals("exit")) {
                    serverMsg = QuoteGen.getQuote();
                } else {
                    serverMsg = "End of communication ...";
                    break;
                }
                out = new PrintWriter(
                        new BufferedWriter(
                                new OutputStreamWriter(remoteClient.getOutputStream())
                        ),
                        true               // ???????
                );
                out.println(serverMsg);                   // отправим сообщение клиенту

            }
        } catch (Exception e) {
            System.out.println("Что-то случилось: " + e.getMessage());
        } finally {
            // закрытие соединений, потоков, чистка буфера:

            try {
                if (server != null && !server.isClosed()) {             // проверка существования и "НЕ закрытия" сервера
                    server.close();                                     // закрытие сервера
                }

                if (remoteClient != null && !remoteClient.isClosed()) {  // проверка существования и "НЕ закрытия" сокета клиента
                    remoteClient.close();                               // закрытие сокета клиента
                }

                if (out != null) {                                        // проверка существования потока
                    out.close();                                        // закрытие потока: также закрываются все вложенные потоки
                }
            } catch (Exception e) {
                System.out.println("Что-то случилось в блоке finally: " + e.getMessage());
            }
        }
    }
}
