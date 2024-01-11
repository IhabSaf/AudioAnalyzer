import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class NumbersToMusicXML {
    private static int[] sequence;
    public static void main(String[] args) {
//        int[] sequence = {4, 2, 4, 5, 0, 7,5,2,3,5,6,7,8,9,3,1,2,3,4,5,6,4,34,55,63,72,37,19,22,41,31,35,64,18,25,11,76,23,2,1};

        String filePath = "MusicNotes.txt";

        try {
            Scanner scanner = new Scanner(new File(filePath));

            // Determine the number of values in the file or allocate an array of a fixed size
            int numberOfValues = 150;

            // Create an int array to store the values
            sequence = new int[numberOfValues];

            int index = 0;

            while (scanner.hasNextInt() && index < numberOfValues) {
                sequence[index] = scanner.nextInt();
                index++;
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


            try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.xml"))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
            writer.newLine();
            writer.write("<!DOCTYPE score-partwise PUBLIC");
            writer.newLine();
            writer.write("  \"-//Recordare//DTD MusicXML 3.1 Partwise//EN\"");
            writer.newLine();
            writer.write("  \"http://www.musicxml.org/dtds/partwise.dtd\">");
            writer.newLine();
            writer.write("<score-partwise version=\"3.1\">");
            writer.newLine();
            writer.write("  <part-list>");
            writer.newLine();
            writer.write("    <score-part id=\"P1\">");
            writer.newLine();
            writer.write("      <part-name>Music</part-name>");
            writer.newLine();
            writer.write("    </score-part>");
            writer.newLine();
            writer.write("  </part-list>");
            writer.newLine();
            writer.write("  <part id=\"P1\">");
            writer.newLine();
            writer.write("    <measure number=\"1\">");
            writer.newLine();

            int duration = 1; // Initial duration (quarter note)

            for (int noteValue : sequence) {
                writer.write("      <note>");
                writer.newLine();
                writer.write("        <pitch>");
                writer.newLine();
                writer.write("          <step>" + getStep(noteValue) + "</step>");
                writer.newLine();
                writer.write("          <octave>4</octave>");
                writer.newLine();
                writer.write("        </pitch>");
                writer.newLine();
                writer.write("        <duration>" + duration + "</duration>");
                writer.newLine();
                writer.write("        <type>quarter</type>");
                writer.newLine();
                writer.write("      </note>");
                writer.newLine();

                // Adjust duration based on noteValue (0 to 7)
                duration = 1 / (int) Math.pow(2, noteValue);
            }

            writer.write("    </measure>");
            writer.newLine();
            writer.write("  </part>");
            writer.newLine();
            writer.write("</score-partwise>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to get the step (note name) based on the noteValue
    private static String getStep(int noteValue) {
        String[] steps = {"C", "D", "E", "F", "G", "A", "B"};
        return steps[noteValue % 7];
    }
}
