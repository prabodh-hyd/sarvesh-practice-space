package in.prabodh.Service;
import in.prabodh.model.Slot;
import java.util.*;

public class TimetableManager {
    public static Map<String, TreeMap<Integer, Slot>> timetable = new HashMap<>();

    public TimetableManager() {

    }

    public void addSlot(String day, int time, Slot slot) {
        // Check if the day already has slots
        if (!timetable.containsKey(day)) {
            timetable.put(day, new TreeMap<>());
        }

        // Check for collision
        TreeMap<Integer, Slot> dayTimetable = timetable.get(day);
        if (dayTimetable.containsKey(time)) {
            // Handle collision
            System.out.println("Slot collision at " + time + " on " + day);
            // You can choose to overwrite the existing slot, throw an exception, or handle it in any other way
        } else {
            dayTimetable.put(time, slot);
        }
    }

    public void removeSlot(String day, int time) {
        if (timetable.containsKey(day)) {
            TreeMap<Integer, Slot> dayTimetable = timetable.get(day);
            dayTimetable.remove(time);
        }
    }

    // Other methods for accessing and manipulating the timetable
}
