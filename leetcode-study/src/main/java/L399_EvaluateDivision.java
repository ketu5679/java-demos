import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class L399_EvaluateDivision {
    static class Node {
        String k;
        double v;
        Map<String, Node> next = new HashMap<>();
        public Node(String k, double v) {
            this.k = k;
            this.v = v;
        }
    }
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Node> m = new HashMap<>();
        for(int i = 0; i< values.length; i++) {
            String a = equations.get(i).get(0);
            String b = equations.get(i).get(1);
            if (m.containsKey(a)) {
                Node nodeb = new Node(b, values[i]);
                m.get(a).next.put(b, nodeb) ;
                m.put(b, nodeb);
            } else {
                if (m.containsKey(b)) {
                    Node nodea = new Node(a, 1.0);
                    m.put(a, nodea);
                    nodea.next.put(b, m.get(b));
                } else {
                    Node nodea = new Node(a, -1.0);
                    m.put(a, nodea);
                    Node nodeb = new Node(b, values[i]);
                    m.put(b, nodeb);
                    nodea.next.put(b, nodeb);
                }
            }
        }

        double[] res = new double[queries.size()];
        for (int i = 0; i< queries.size(); i++) {
            String l = queries.get(i).get(0);
            String r = queries.get(i).get(1);
            res[i] = -1.0;

            if (m.containsKey(l)) {
                if (m.get(l).next.containsKey(r)) {
                    res[i] = m.get(l).next.get(r).v;
                    continue;
                }
                Map<String, Node> cur = m;
                double tmp = 1;
                while (true) {
                    for (Map.Entry<String, Node> node : m.get(l).next.entrySet()) {
                        if (node.getValue().next.containsKey(l)) {

                        }
                    }
                    if (cur.get(l).next.containsKey(r)) {
                        res[i] = m.get(l).next.get(r).v;
                        break;
                    } else {

                    }
                }

            }
        }
        return res;
    }
}
