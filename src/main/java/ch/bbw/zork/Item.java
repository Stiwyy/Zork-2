package ch.bbw.zork;

public class Item {
    private String name;
    private String description;
    private Room room;

    public Item(String name, String description, Room room) {
        this.name = name;
        this.description = description;
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Room getRoom() {
        return room;
    }
}
