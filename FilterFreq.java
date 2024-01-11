import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FilterFreq {
    public static void main(String[] args) {
        try {
            List<Integer> extractedNumbers = extractNumbers("complexSamples.txt");
            for (int number : extractedNumbers) {
//                System.out.println(number);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> extractNumbers(String fileName) throws IOException {
        List<Integer> extractedNumbers = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            if (parts.length >= 2) {
                double firstNumber = Double.parseDouble(parts[0]);
                int truncatedNumber = (int) firstNumber;
                extractedNumbers.add(truncatedNumber);
            }
        }


        reader.close();

        List<Integer> firstTwoDigits = new ArrayList<>();

        for (Integer num : extractedNumbers) {
            int firstTwo = num / 100; // Extracts the first two digits
            if(firstTwo < 13 ){
            firstTwoDigits.add(firstTwo);}
        }

        for (Integer digit : firstTwoDigits) {
            System.out.println(digit);
        }

        // Save the result to MusicNots.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("MusicNotes.txt"))) {
            for (Integer digit : firstTwoDigits) {
                writer.write(Integer.toString(digit));
                writer.newLine(); // Add a new line for each number
            }
            System.out.println("Results saved to MusicNots.txt.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }

        return extractedNumbers;
    }
}
