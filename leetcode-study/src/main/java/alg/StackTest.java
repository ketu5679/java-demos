package alg;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class StackTest {
    /**
     * https://www.ixigua.com/6932631151181824524?id=6917524794283917837&logTag=7337c895b6d408501cf7
     * 栈，
     * eg: 加减乘除计算器
     * @param args
     */
    public static void main(String[] args) {
        Deque<Character> stack = new ArrayDeque<>();
        String res = "1+(6+3)/3";
        List<Character> chars = new ArrayList<>();
        Integer isContainLeftBrackets = 0;
        for (char c : res.toCharArray()) {
            if (Character.isSpaceChar(c)) continue;
            // 1. 所有的数字直接输出
            if (Character.isDigit(c)) {
                chars.add(c);
                // 3. 所有的左括号都要入栈
            } else if (isLeftBrackets(c)) {
                isContainLeftBrackets++;
                stack.add(c);
                // 5. 若是右括号，栈不断出栈，直到碰到左括号
            } else if (isRightBrackets(c)) {
                Character pop;
                while (!stack.isEmpty() && (!isLeftBrackets(pop = stack.removeLast()))) {
                    chars.add(pop);
                }
                isContainLeftBrackets--;
                // 4. 若栈内包含左括号，运算符都要入栈
            } else if (isContainLeftBrackets > 0 && isOperator(c)) {
                stack.add(c);
                //2. 运算符优先级高于栈内要入栈（或栈空）。
            }  else if (isLargeThanExists(stack, c)) {
                stack.add(c);
            } else {
                // 否则， 从堆栈中弹出所有优先级更高或一样的运算符（或直到括号），
                // 再将当前的入栈
                Character pop;
                while (!stack.isEmpty() && !isBrackets(pop = stack.removeLast()) && getLevel(pop) >= getLevel(c)) {
                    chars.add(pop);
                }
                stack.add(c);
            }
        }
        while(!stack.isEmpty()) {
            chars.add(stack.removeLast());
        }
        System.out.println(chars.toString());
        // 凡是数字就压栈
        // 凡是运算符就出栈两次 计算 压栈
        Deque<Long> resultStack = new ArrayDeque<>();
        for (Character aChar : chars) {
            if(Character.isDigit(aChar)) {
                resultStack.add(Long.valueOf(aChar.toString()));
            } else if (isOperator(aChar) && resultStack.size() >=2) {
                resultStack.add(calc(aChar, resultStack.removeLast(), resultStack.removeLast()));
            }
        }
        System.out.println(resultStack.removeLast());
    }

    public static Long calc(Character operator, Long right, Long left) {
        System.out.println("calc " + " " + left + " " + operator + " " + right);
        switch (operator) {
            case '+':
                return left + right;
            case '-':
                return left - right;
            case '*':
                return left * right;
            case '/':
                return left / right;
            default:
                return 0L;
        }
    }

    public static boolean isLargeThanExists(Deque<Character> stack, Character c) {
        if (stack.isEmpty()) {
            return true;
        }
        Character exists = stack.removeLast();
        stack.add(exists);
        return getLevel(c) > getLevel(exists);
    }

    public static boolean isOperator(Character c) {
        return getLevel(c) > 0;
    }

    public static Integer getLevel(Character c) {
        switch (c) {
            case '*':
            case '/':
                return 2;
            case '+':
            case '-':
                return 1;
            default:
                return 0;
        }
    }

    public static boolean isBrackets(Character c) {
        return isLeftBrackets(c) || isRightBrackets(c);
    }

    public static boolean isLeftBrackets(Character c) {
        return c.equals('(');
    }

    public static boolean isRightBrackets(Character c) {
        return c.equals(')');
    }

}
