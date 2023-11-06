import java.util.Scanner;
import java.util.Stack;

public class InfixToPostfixPrefix {
   private static final int MAX = 100;
   private char[] stack = new char[MAX];
   private char[] infix;
   private char[] postfix = new char[MAX];
   private char[] prefix = new char[MAX];
   private int top = -1;

   public static void main(String[] args) {
      InfixToPostfixPrefix converter = new InfixToPostfixPrefix();
      converter.inputExpression();
      converter.inToPost();
      converter.inToPrefix();
      converter.printPostfix();
      converter.printPrefix();
   }

   public void inputExpression() {
      Scanner scanner = new Scanner(System.in);
      System.out.print("Enter infix expression: ");
      infix = scanner.nextLine().toCharArray();
   }

   public void inToPost() {
      int j = 0;
      char next;
      char symbol;

      for (int i = 0; i < infix.length; i++) {
         symbol = infix[i];

            if (!space(symbol)) {
               switch (symbol) {
                  case '(':
                     push(symbol);
                     break;
                  case ')':
                     while ((next = pop()) != '(')
                        postfix[j++] = next;
                     break;
                  case '+':
                  case '-':
                  case '*':
                  case '/':
                  case '^':
                     while (!isEmpty() && precedence(stack[top]) >= precedence(symbol))
                        postfix[j++] = pop();
                        push(symbol);
                     break;
                  default:
                     postfix[j++] = symbol;
               }
            }
      }
         while (!isEmpty())
            postfix[j++] = pop();
         postfix[j] = '\0';
   }

   public void inToPrefix() {
      StringBuilder infixExpression = new StringBuilder();
      for (char c : infix) {
         if (c != '\0') {
            infixExpression.append(c);
         }
      }
      String prefixExpression = InfixToPrefix.infixToPrefix(infixExpression.toString());
      prefix = prefixExpression.toCharArray();
    }

   public void printPostfix() {
      System.out.print("Postfix expression: ");
      for (int i = 0; postfix[i] != '\0'; i++) {
         System.out.print(postfix[i]);
      }
         System.out.println();
   }

   public void printPrefix() {
      System.out.print("Prefix expression: ");
      for (int i = 0; i < prefix.length && prefix[i] != '\0'; i++) {
         System.out.print(prefix[i]);
      }
      System.out.println();
   }

   public void push(char c) {
      if (top == MAX - 1) {
         System.out.println("Stack Overflow");
         System.exit(1);
      }
      top++;
      stack[top] = c;
   }

   public char pop() {
      char c;
      if (top == -1) {
         System.out.println("Stack Underflow");
         System.exit(1);
      }
      c = stack[top];
      top--;
      return c;
   }

   public boolean isEmpty() {
      return top == -1;
   }

   public boolean space(char c) {
      return c == ' ' || c == '\t';
   }

   public int precedence(char symbol) {
      switch (symbol) {
         case '^':
            return 3;
         case '/':
         case '*':
            return 2;
         case '+':
         case '-':
            return 1;
         default:
            return 0;
      }
   }

   public static class InfixToPrefix {
      public static String infixToPrefix(String infixExpression) {
         StringBuilder prefix = new StringBuilder();
         Stack<Character> stack = new Stack<>();

         for (int i = infixExpression.length() - 1; i >= 0; i--) {
            char symbol = infixExpression.charAt(i);

            if (Character.isDigit(symbol) || Character.isLetter(symbol)) {
               prefix.insert(0, symbol);
            } else if (symbol == ')') {
               stack.push(symbol);
            } else if (symbol == '(') {
               while (!stack.isEmpty() && stack.peek() != ')') {
                  prefix.insert(0, stack.pop());
               }
                  stack.pop(); // Pop the '('
            } else {
               while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(symbol)) {
                  prefix.insert(0, stack.pop());
               }
                  stack.push(symbol);
            }
         }
            while (!stack.isEmpty()) {
               prefix.insert(0, stack.pop());
            }
            return prefix.toString();
      }

      public static int precedence(char symbol) {
         switch (symbol) {
            case '^':
               return 3;
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
   }
}