
public class PhoneBookSingleton {
    private static PhoneBook instance;

    public static PhoneBook getInstance() {
        if (instance == null) {
            instance = new PhoneBook();
        }
        return instance;
    }

}