package audio_edit;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;



public class AudioEditor {
    String filepath;

    public AudioEditor(String filepath) {
        this.filepath = filepath;
    }

    public String editAudioFile() {
        try {
            File file = new File(this.filepath);
            
            
            String new_filename = "test_" + UUID.randomUUID() + ".wav";

            // if mp3, convert to wav
            if (file.getName().substring(file.getName().length() - 4).equals(".mp3")) {
                System.out.println("mp3 detected");
                ProcessBuilder processBuilder = new ProcessBuilder(
                        System.getProperty("user.dir") + "\\ffmpeg\\ffmpeg", "-i", file.getAbsolutePath(), System.getProperty("user.dir") + "\\" + new_filename);
                processBuilder.redirectErrorStream(true);
                try {
                    Process process = processBuilder.start();
                    int exitCode = process.waitFor();
                    if (exitCode != 0) {
                        return "Failed to convert to wav - FFmpeg returned error code " + exitCode;
                    }
                } catch (InterruptedException e) {
                    return "Failed to convert to wav - " + e.getMessage();
                }
                file = new File(System.getProperty("user.dir") + "\\" + new_filename);
            }

            byte[] bytes = Files.readAllBytes(file.toPath());
            byte[] wavOutout = formatAudioToWav(bytes, TARGET_FORMAT);
            
            Path path = Paths.get(System.getProperty("user.dir") + "\\" + new_filename);
            Files.write(path, wavOutout);

        } catch (IOException e) {
            return "Failed to edit file - " + e.getMessage();
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            return "Failed to edit file - " + e.getMessage();
        }

        return "";
    }

    private static final AudioFormat TARGET_FORMAT = new AudioFormat(
        8000,
        8,
        1,
        true,
        false
    );

    public byte[] formatAudioToWav(final byte[] audioFileContent,
                               final AudioFormat audioFormat) throws
                                                                       IOException,
                                                                       UnsupportedAudioFileException {
        try (
            final AudioInputStream originalAudioStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(audioFileContent));
            final AudioInputStream formattedAudioStream = AudioSystem.getAudioInputStream(audioFormat, originalAudioStream);
            final ByteArrayOutputStream convertedOutputStream = new ByteArrayOutputStream()
        ) {
            // Calculate the frame length based on the new format's frame size and sample rate
            long frameLength = (long) (audioFileContent.length / audioFormat.getFrameSize());

            // Create a new audio stream with the correct frame length
            final AudioInputStream lengthAdjustedAudioStream = new AudioInputStream(formattedAudioStream, audioFormat, frameLength);

            // Write the audio stream to the output as WAVE
            AudioSystem.write(lengthAdjustedAudioStream, AudioFileFormat.Type.WAVE, convertedOutputStream);

            return convertedOutputStream.toByteArray();
        }
    }
}