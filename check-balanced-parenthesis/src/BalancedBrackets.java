import java.util.Scanner;
import java.util.Stack;

public class BalancedBrackets
{

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        while(scanner.hasNextLine()) {
            String s = scanner.nextLine();
            System.out.println(checkParenthesisBalance(s) ? "YES" : "NO");
        }

        scanner.close();
    }

    static boolean cp(String expression)
    {

        Stack<Character> s = new Stack<Character>();

        for (char c : expression.toCharArray())
        {
            if (c == '{')
                s.push('}');
            else if (c == '[')
                s.push(']');
            else if (c == '(')
                s.push(')');
            else
            {
                if (s.isEmpty())
                {
                    return false;
                }

                char top = s.pop();
                if (c != top)
                    return false;
            }
        }
        return s.empty();
    }

    static boolean checkParenthesisBalance(String s)
    {
        Stack<Character> stack = new Stack<Character>();
        for (char c : s.toCharArray())
        {
            if (c == '(' || c == '[' || c == '{')
            {
                stack.push(c);
            }
            else
            {
                if (c != ')' && c != '}' && c != ']' )
                {
                    continue;
                }

                if (stack.isEmpty())
                {
                    return false;
                }

                char top = stack.pop();
                if (!((c == ')' && top == '(') || (c == ']' && top == '[') || (c == '}' && top == '{')))
                {
                    return false;
                }

            }
        }
        return stack.isEmpty();
    }
}
