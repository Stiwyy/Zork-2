package ch.bbw.zork;import java.awt.image.AreaAveragingScaleFilter;import java.util.*;public class Game {    private Parser parser;    private Room currentRoom;    private Room outside, lab, tavern, gblock, office, toilette;    private Item hammer, key, anvil, broken_chair, pen;    private HashMap<Integer, Room> roomhistory;    private List<Room> rooms;    public Game() {        items = new ArrayList<>();        parser = new Parser(System.in);        // Create all the rooms and link their exits together.        outside = new Room("outside G block on Peninsula campus");        lab = new Room("lab, a lecture theatre in A block" );        tavern = new Room("the Seahorse Tavern (the campus pub)");        gblock = new Room("the G Block");        office = new Room("the computing admin office");        toilette = new Room("the Toilette");        roomhistory = new HashMap<>();        rooms = Arrays.asList(outside, lab, tavern, gblock, office, toilette);        // initialise room exits        outside.put(null, lab, gblock, tavern);        lab.put(null, null, null, outside);        tavern.put(null, outside, null, null);        gblock.put(outside, office, null, toilette);        office.put(null, null, null, gblock);        toilette.put(null, gblock, null, null);        currentRoom = outside; // start game outside        Item();        items.add(hammer);        items.add(key);        items.add(anvil);        items.add(broken_chair);        items.add(pen);    }    public void play() {        printWelcome();        // Enter the main command loop.  Here we repeatedly read commands and        // execute them until the game is over.        boolean finished = false;        while (!finished) {            Command command = parser.get(); // reads a command            finished = processCommand(command);        }        System.out.println("Thank you for playing. Good bye.");    }    private void printWelcome() {        System.out.println();        System.out.println("Welcome to Zork!");        System.out.println("Zork is a simple adventure game.");        System.out.println("Type 'help' if you need help.");        System.out.println();        System.out.println(currentRoom.longDescription());        roomhistory.put(0, currentRoom);    }    private boolean processCommand(Command command) {        if (command.isUnknown()) {            System.out.println("I don't know what you mean...");            return false;        }        String commandWord = command.getCommandWord();        if (commandWord.equals("help")) {            printHelp();        } else if (commandWord.equals("go")) {            goRoom(command);            if (currentRoom == office) {                System.out.println("You found the Office!");                return true;            }        } else if (commandWord.equals("quit")) {            if (command.hasSecondWord()) System.out.println("Quit what?");            else return true; // signal that we want to quit        } else if (commandWord.equals("back")) {            if (roomhistory.size() > 0) {                int lastRoomIndex = roomhistory.size() - 1;                goBack(roomhistory.get(lastRoomIndex));                roomhistory.remove(lastRoomIndex); // remove the last room from history after going back                System.out.println("You are now in your last room.");            } else {                System.out.println("No previous room to go back to.");            }        } else if (commandWord.equals("map")) {            map();        }        return false;    }    /* implementations of user commands:     */    private void printHelp() {        System.out.println("You are lost. You are alone. You wander");        System.out.println("around at Monash Uni, Peninsula Campus.");        System.out.println();        System.out.println("Your command words are:");        System.out.println(parser.showCommands());    }    private void goRoom(Command command) {        // if there is no second word, we don't know where to go...        if (!command.hasSecondWord()) {            System.out.println("Go where?");        } else {            String direction = command.getSecondWord();            // Try to leave current room.            Room nextRoom = currentRoom.nextRoom(direction);            if (nextRoom == null) System.out.println("There is no door!");            else {                roomhistory.put(roomhistory.size(), currentRoom);                currentRoom = nextRoom;                System.out.println(currentRoom.longDescription());            }        }    }    private void goBack(Room room) {        if (room != null) {            currentRoom = room;            System.out.println(currentRoom.longDescription());        } else {            System.out.println("No previous room found.");        }    }    private void map() {        for (Room room : rooms) {            if (room == currentRoom) {                System.out.println("-- \n" + room.shortDescription() + "  <---");            } else {                System.out.println("-- \n" + room.shortDescription());            }        }    }}