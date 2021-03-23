import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapTest {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        Map<String, String> linkedMap = new LinkedHashMap<>();
        linkedMap.put("ff", "ff");
        linkedMap.get("ff");

    }
}
