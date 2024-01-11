import java.util.HashMap;
import java.util.Map;

public class NoteToValue {
    private static final Map<String, Integer> noteToValue = new HashMap<>();

    static {
        noteToValue.put("C4", 60);
        noteToValue.put("C#4", 61);
        noteToValue.put("D4", 62);
        noteToValue.put("D#4", 63);
        noteToValue.put("E4", 64);
        noteToValue.put("F4", 65);
        noteToValue.put("F#4", 66);
        noteToValue.put("G4", 67);
        noteToValue.put("G#4", 68);
        noteToValue.put("A4", 69);
        noteToValue.put("A#4", 70);
        noteToValue.put("B4", 71);
        noteToValue.put("C5", 72);
        noteToValue.put("C#5", 73);
        noteToValue.put("D5", 74);
        noteToValue.put("D#5", 75);
        noteToValue.put("E5", 76);
        noteToValue.put("F5", 77);
        noteToValue.put("F#5", 78);
        noteToValue.put("G5", 79);
        noteToValue.put("G#5", 80);
        noteToValue.put("A5", 81);
        noteToValue.put("A#5", 82);
        noteToValue.put("B5", 83);
    }

    public static int get(String note) {
        Integer value = noteToValue.get(note);
        if (value != null) {
            return value;
        } else {
            throw new IllegalArgumentException("Note not found: " + note);
        }
    }
}
