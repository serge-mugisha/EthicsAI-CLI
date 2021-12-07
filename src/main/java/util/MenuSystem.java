package util;

public class MenuSystem {
    /*
     * Generates a menu as a String of text with operating
     * system appropriate line terminators used to place options
     * on separate lines.
     */
    public String optionsList() {
        String menu = "\n \n Application output --> Hello professor, please choose an operation: \n";
        menu += "\t 1) View all students \n";
        menu += "\t 2) View specific student by ID \n";
        menu += "\t 3) Add Student \n";
        menu += "\t 4) Update a Student by ID \n";
        menu += "\t 5) Delete student by ID \n";
        menu += "\t 6) Display Class Statistics \n";
        menu += "\t 7) Exit the Program \n \n";
        menu += "User input -->";
        return menu;
    }
}
