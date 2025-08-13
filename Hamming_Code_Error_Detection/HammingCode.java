package Hamming_Code_Error_Detection;

import java.util.HashMap;
import java.util.Map;

public class HammingCode {

    private static Map<Integer, String> expectedParityMap = new HashMap<>();

    private static int getMaxParityBitValue(int dataLength) {
        System.out.println("Getting the parity bit Size (r)...");
        int r = (int) (Math.log(dataLength + 1) / Math.log(2));

        // Check if equation is saisfied
        int LHS = (int) Math.pow(2, r);
        int RHS = r + dataLength + 1;
        if (LHS >= RHS) {
            return r;
        }
        return r + 1;
    }

    public static void calculateExpectedParityMap(String data) {
        System.out.println("Calculating the expected Parity Bit values...");
        int parityBitSize = getMaxParityBitValue(data.length());
        int gapper = 1;
        for (int i = 0; i < parityBitSize; i++) { // Calculating each parity one by one
            int indexOfParity = (int) Math.pow(2, i);
            int expectedParityBit = 0;
            for (int j = indexOfParity - 1; j < data.length(); j += indexOfParity + gapper) { // Check the databits
                int m = j;
                while (m <= j + indexOfParity - 1) {
                    if (m == indexOfParity - 1) {
                        m += 1;
                        continue;
                    }
                    if (data.charAt(j) == '1') {
                        expectedParityBit = expectedParityBit == 1 ? 0 : 1;
                    }
                    m += 1;
                }
            }
            expectedParityMap.put(indexOfParity, Integer.toString(expectedParityBit));
            gapper += indexOfParity;
        }
    }

    public static String correctData(String data) {
        System.out.println("Starting error detection and correction process...");
        for (Map.Entry<Integer, String> entry : expectedParityMap.entrySet()) {
            int parityBitPosition = entry.getKey();
            int expectedParityBit = Integer.parseInt(entry.getValue());
            int actualParityBit = data.charAt(parityBitPosition - 1);

            if (expectedParityBit != actualParityBit) {
                System.out.println(
                        "Error detected at parity bit position: " + parityBitPosition + "\nCorrecting the value...");
                data = data.substring(0, parityBitPosition - 1) + (actualParityBit == 1 ? 0 : 1)
                        + data.substring(parityBitPosition);
            }
        }
        return data;
    }
}
