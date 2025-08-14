package Hamming_Code_Error_Detection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server Started at port: 1234");
            Socket socketObj = serverSocket.accept();

            // Accept from Client
            DataInputStream dataInputStream = new DataInputStream(socketObj.getInputStream());
            String receivedData = dataInputStream.readUTF();

            // Perform Hamming Code Error Detection
            System.out.println(
                    "Received from client: " + receivedData
                            + "\nStarting Hamming Code Error Detection Method in Server...");
            HammingCode.calculateExpectedParityMap(receivedData);
            String correctedData = HammingCode.correctData(receivedData);
            System.out.println("Corrected Data: " + correctedData);

            // Send Back to Client
            DataOutputStream dataOutputStream = new DataOutputStream(socketObj.getOutputStream());
            dataOutputStream.writeUTF(receivedData);
            System.out.print("Sent to Client");

            socketObj.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
