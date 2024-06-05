/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author thushan
 */
public class MultiThreadedServer {

    private static final int PORT = 8080;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/AIS-R-DB";
    private static final String USER = "root";
    private static final String PASS = "password";

    public static void main(String[] args) {
        try ( ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            ExecutorService executorService = Executors.newFixedThreadPool(10);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                executorService.execute(new ClientHandler(socket));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {

        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try ( ObjectInputStream input = new ObjectInputStream(socket.getInputStream());  ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());  Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

                String action = input.readUTF();
                switch (action) {
                    case "insertApplicant":
//                        handleInsertApplicant(input, output, conn);
                        break;
                    case "insertStaff":
//                        handleInsertStaff(input, output, conn);
                        break;
                    default:
                        output.writeUTF("Invalid action");
                        output.flush();
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
