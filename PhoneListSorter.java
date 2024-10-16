import java.util.*;
public class PhoneListSorter {
    public static HashMap<String, ArrayList<String>> sortPhoneList(HashMap<String, ArrayList<String>> phoneList) {
        ArrayList<Map.Entry<String, ArrayList<String>>> list = new ArrayList<>(phoneList.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, ArrayList<String>>>() {
            @Override
            public int compare(Map.Entry<String, ArrayList<String>> o1, Map.Entry<String, ArrayList<String>> o2) {
                return o1.getKey().compareToIgnoreCase(o2.getKey());
            }
        });

        HashMap<String, ArrayList<String>> sortedPhoneList = new LinkedHashMap<>();
        for (Map.Entry<String, ArrayList<String>> entry : list) {
            sortedPhoneList.put(entry.getKey(), entry.getValue());
        }

        return sortedPhoneList;
    }
}
