import java.util.*;
public class L205_LsomorphicStrings {
    public boolean isIsomorphic(String s, String t) {
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();

        Map<Character, Integer> sLastIndexMap = new HashMap<>();
        Map<Character, Integer> tLastIndexMap = new HashMap<>();
        for (int i = 0; i < sChars.length; i++) {
            if (sLastIndexMap.containsKey(sChars[i])
                    && !sLastIndexMap.get(sChars[i]).equals(tLastIndexMap.get(tChars[i]))) {
                return false;
            } else if(tLastIndexMap.containsKey(tChars[i])
                    && !tLastIndexMap.get(tChars[i]).equals(sLastIndexMap.get(sChars[i]))) {
                return false;
            }
            sLastIndexMap.put(sChars[i], i);
            tLastIndexMap.put(tChars[i], i);
        }

        return true;
    }

    public static void main(String[] args) {
        boolean isomorphic = new L205_LsomorphicStrings().isIsomorphic("egg", "add");
        System.out.println(isomorphic);
    }
}
