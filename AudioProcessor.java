import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioProcessor {
    public static void main(String[] args) {
        File audioFile = new File("RecordAudio.wav");

        try {

            // Open het audiobestand
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Bepaal een buffergrootte
            int bufferSize = 4096;
            byte[] audioData = new byte[bufferSize];
            int bytesRead;

            // Lees de audiogegevens in de array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while ((bytesRead = audioStream.read(audioData, 0, bufferSize)) != -1) {
                byteArrayOutputStream.write(audioData, 0, bytesRead);
            }

            byte[] audioDataArray = byteArrayOutputStream.toByteArray();

            System.out.println("Audiogegevens gelezen: " + audioDataArray.length + " bytes");

            // Voer Fourier-transformatie uit met Apache Commons Math
            double[] audioSamples = new double[audioDataArray.length / 2];  // 2 wegen de stero geluid

            for (int i = 0; i < audioSamples.length; i++) {
                audioSamples[i] = (double)((audioDataArray[i * 2] & 0xFF) | (audioDataArray[i * 2 + 1] << 8)) / 32768.0;
            }

            // Calculate the next power of 2 for the length of audioSamples
            int powerOf2Length = 1;
            while (powerOf2Length < audioSamples.length) {
                powerOf2Length *= 2;
            }

            // Pad the audio data with zeros to the next power of 2
            double[] paddedAudioSamples = new double[powerOf2Length];
            System.arraycopy(audioSamples, 0, paddedAudioSamples, 0, audioSamples.length);

            // Voer de Fourier-transformatie uit op het opgevulde audioSamples
            FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
            Complex[] complexSamples = transformer.transform(paddedAudioSamples, TransformType.FORWARD);
            int[] positiveIntSamples = new int[complexSamples.length];
            for (int i = 0; i < complexSamples.length; i++) {
                complexSamples[i] = Complex.valueOf(complexSamples[i].abs()); // Ensure the value is positive
            }
            System.out.println(positiveIntSamples.length);

            // Save the complexSamples to a text file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("complexSamples.txt"))) {
                for (Complex complex : complexSamples) {
                    writer.write(complex.getReal() + " " + complex.getImaginary() + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            // Nu heb je de frequentiegegevens in de complexSamples-array en kun je verdere verwerking uitvoeren.

            // Sluit het audiobestand
            audioStream.close();
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }


    }
}
