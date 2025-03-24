import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Checker {
    private static final double LOAD_FACTOR_THRESHOLD = 0.7;
    private static int size = 800000; // The initial size for the large dictionary with 400k+ words
    private static String[] table = new String[size];
    private static int numberElements = 0;
    private static boolean quadraticProbing = false; 

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        readDictionary("Dictionary.txt");
        long endTime = System.currentTimeMillis();
        System.out.println("Time performance to read in a dictionary using Linear probing " + (endTime - startTime) + " milliseconds");

        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Please pick an option:");
            System.out.println("Please type 1 to check spelling of a word");
            System.out.println("Please type 2 to add a new word to the dictionary");
            System.out.println("Please type 3 to remove a word from the dictionary");
            System.out.println("Please type 4 to display load factor");
            System.out.println("Please type 5 to spell check a document");
            System.out.println("Please type 6 to toggle between linear probing and quadratic probing"); // Toggle between linear and quadratic probing to compare performance
            System.out.println("Please type 7 to exit the program");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Please enter the word to spell check:");
                    String word = (input.nextLine()).toLowerCase();
                    if (isSpeltCorrect(word)) {
                        System.out.println("The spelling is correct!");
                    } else {
                        System.out.println("The spelling is incorrect!");
                        System.out.println("Here are some suggestions:");
                        for (String suggestion : getSuggestions(word)) {
                            System.out.println(suggestion);
                        }
                    }
                    break;

                case 2:
                    System.out.println("Please enter the word to add:");
                    String wordAdd = (input.nextLine()).toLowerCase();
                    addWord(wordAdd);
                    System.out.println("The word has been added.");
                    break;

                case 3:
                    System.out.println("Please enter the word to remove:");
                    String wordRemove = (input.nextLine()).toLowerCase();
                    removeWord(wordRemove);
                    System.out.println("The word has been removed.");
                    break;

                case 4:
                    showLoadFactor();
                    break;

                case 5:
                    System.out.println("Please enter a filename to spell check:");
                    String filename = input.nextLine();
                    checkDocument(filename);
                    break;

                case 6:
                    quadraticProbing = !quadraticProbing;
                    System.out.println("Collision resolution set to: " +
                        (quadraticProbing ? "Quadratic Probing" : "Linear Probing"));
                    table = new String[size];
                    numberElements = 0;
                    long startTime1 = System.currentTimeMillis();
                    readDictionary("Dictionary.txt");
                    long endTime1 = System.currentTimeMillis();
                    System.out.println("Time performance to read in a dictionary using "+ (quadraticProbing ? "Quadratic Probing " : "Linear Probing ") + (endTime1 - startTime1) + " milliseconds");
                    break;

                case 7:
                    System.out.println("Exiting program.");
                    input.close();
                    return;

                default:
                    System.out.println("Invalid input choice. Please try again.");
            }
        }
    }
    // Reads in the dictionary
    private static void readDictionary(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNext()) {
            addWord(scanner.next());
        }
        scanner.close();
    }

    // Hashs code using a polynomial hash function
    private static int hashingFunction(String word) {
        int value = 0;
        int p = 37;
        for (char c : word.toCharArray()) {
            value = (p * value + c) % size;
        }
        return value;
    }

    // Adds words to hash table and resize table accordingly
    private static void addWord(String word) {
        if ((double) numberElements / size >= LOAD_FACTOR_THRESHOLD) {
            resizeTable();
        }
        int index = findIndex(word);
        if (table[index] == null) {
            table[index] = word;
            numberElements++;
        }
    }

    // Removes a word from the hash table
    private static void removeWord(String word) {
        int index = findIndex(word);
        if (table[index] != null && table[index].equals(word)) {
            table[index] = null;
            numberElements--;
        }
    }

    // Checks if a word is correctly spelled
    private static boolean isSpeltCorrect(String word) {
        int index = findIndex(word);
        return table[index] != null && table[index].equals(word);
    }

    // Finds an available index to store using open adressing.
    private static int findIndex(String word) {
        int hash = hashingFunction(word);
        int increment = 1;

        while (table[hash] != null && !table[hash].equals(word)) {
            if (quadraticProbing) {
                hash = (hash + increment * increment) % size;
            } else {
                hash = (hash + increment) % size;
            }
            increment++;
        }
        return hash;
    }

    // Resizes the table by doubling its size when load factor threshold is exceeded
    private static void resizeTable() {
        int previousSize = size;
        size *= 2; //resizes the table
        String[] previousTable = table;
        table = new String[size];
        numberElements = 0;

        for (int i = 0; i < previousSize; i++) {
            if (previousTable[i] != null) {
                addWord(previousTable[i]);
            }
        }
    }

    // Shows the load factor
    private static void showLoadFactor() {
        double loadFactor = (double) numberElements / size;
        System.out.println("The load factor is: " + loadFactor);
    }

    // Generate suggestions for the incorrectly spelt word. (I limited it to 5 suggestions)
    private static ArrayList<String> getSuggestions(String word) {
    ArrayList<String> suggestions = new ArrayList<>();
    String firstTwoLetters = word.substring(0, Math.min(2, word.length()));
    int wordLen = word.length();

    for (String dictionaryWord : table) {
        if (dictionaryWord != null && dictionaryWord.startsWith(firstTwoLetters) && (((dictionaryWord.length() - wordLen) == 1) || ((dictionaryWord.length() - wordLen) == 0) || ((dictionaryWord.length() - wordLen) == -1))) {
            suggestions.add(dictionaryWord);
        if (suggestions.size() >= 5) {  // Limit to 5 suggestions
            break;
        }
        }
    }
    return suggestions;
}


    // Checks an entire document for incorrect spellings and reccommends suggestions for each incorrect word.
    private static void checkDocument(String filename) {
        try {
            Scanner fileScanner = new Scanner(new File(filename));
            while (fileScanner.hasNext()) {
                String word = (fileScanner.next()).toLowerCase();
                if (!isSpeltCorrect(word)) {
                    System.out.println("An Incorrect word found: " + word);
                    System.out.println("Here are suggestions for next word:");
                    for (String suggestion : getSuggestions(word)) {
                        System.out.println(suggestion);
                    }
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File to check not found: " + filename);
        }
    }
}
