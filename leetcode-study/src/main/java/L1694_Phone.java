import java.util.ArrayList;
import java.util.List;

public class L1694_Phone {
    public String reformatNumber(String number) {
        // 处理字符串
        number = number.replace("-", "").replace(" ", "");

        // 收集结果
        StringBuilder sb = new StringBuilder();

        int length = number.length();
        int mod = length % 3;
        /**
         * 整3个添加进去 考虑mod = 1 时 上一个3需要拆分为 2+2 前面统一处理 mod-1循环
         *
         * mod = 0 3      最后一组
         * mod = 1 2+2    最后4个
         * mod = 2 3|0 +2 最后3+2 or 0+2
         */
        for (int i = 0; i < length / 3 -1; i++) {
            sb.append(number, i * 3, 3 * i + 3);
            sb.append("-");
        }
        switch (mod){
            case 0:
                sb.append(number.substring(length - 3));
                break;
            case 1:
                sb.append(number, length - 4, length - 2);
                sb.append("-");
                sb.append(number.substring(length-2));
                break;
            case 2:
                if (length > 3) {
                    sb.append(number, length - 5, length - 2);
                    sb.append("-");
                }
                sb.append(number.substring(length-2));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(new L1694_Phone().reformatNumber("13"));
        System.out.println(new L1694_Phone().reformatNumber("132"));
        System.out.println(new L1694_Phone().reformatNumber("1324"));
        System.out.println(new L1694_Phone().reformatNumber("13245"));
        System.out.println(new L1694_Phone().reformatNumber("132467"));
        System.out.println(new L1694_Phone().reformatNumber("132 123-33-32"));
        System.out.println(new L1694_Phone().reformatNumber("132 123-33-312"));
        System.out.println(new L1694_Phone().reformatNumber("132 123-33-3333"));
    }

}
