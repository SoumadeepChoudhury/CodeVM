package Hamming_Code_Error_Detection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static int toBinary(char character) {
        return Integer.parseInt(Integer.toBinaryString(character));
    }

    public static String getBinaryString(String inputData) {
        String binaryString = "";
        for (int i = 0; i < inputData.length(); i++) {
            binaryString += toBinary(inputData.charAt(i));
        }
        return binaryString;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your text: ");
        String inputData = scanner.nextLine();
        scanner.close();

        String binaryString = getBinaryString(inputData);

        try {
            // Send to Server
            Socket socketObj = new Socket("localhost", 1234);

            DataOutputStream dataOutputStream = new DataOutputStream(socketObj.getOutputStream());
            dataOutputStream.writeUTF(binaryString);

            // Receive from Server
            String receivedData = "";
            DataInputStream dataInputStream = new DataInputStream(socketObj.getInputStream());

            receivedData = dataInputStream.readUTF();

            // check error using Hamming Code Error Detection
            System.out.println(
                    "received from Server: " + receivedData
                            + "\nStarting Hamming Code Error Detection Method in Client...");
            HammingCode.calculateExpectedParityMap(receivedData);
            String correctedData = HammingCode.correctData(receivedData);
            System.out.println("Corrected Data: " + correctedData);

            socketObj.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
