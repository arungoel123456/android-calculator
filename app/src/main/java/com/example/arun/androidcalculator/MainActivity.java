package com.example.arun.androidcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    static int Prec(char ch)
    {
        switch (ch)
        {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;
        }
        return -1;
    }

    // The main method that converts given infix expression
    // to postfix expression.
    static String infixToPostfix(String exp)
    {
        // initializing empty String for result
        String result = new String("");

        // initializing empty stack
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i<exp.length(); ++i)
        {
            char c = exp.charAt(i);

            // If the scanned character is an operand, add it to output.
            if (Character.isLetterOrDigit(c))
                result += c;

                // If the scanned character is an '(', push it to the stack.
            else if (c == '(')
                stack.push(c);

                //  If the scanned character is an ')', pop and output from the stack
                // until an '(' is encountered.
            else if (c == ')')
            {
                while (!stack.isEmpty() && stack.peek() != '(')
                    result += stack.pop();

                if (!stack.isEmpty() && stack.peek() != '(')
                    return "Invalid Expression"; // invalid expression
                else
                    stack.pop();
            }
            else // an operator is encountered
            {
                while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek()))
                    result += stack.pop();
                stack.push(c);
            }

        }

        // pop all the operators from the stack
        while (!stack.isEmpty())
            result += stack.pop();

        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView input = findViewById(R.id.input);
         TextView result = findViewById(R.id.result);
        Button b1 = findViewById(R.id.one);
        b1.setOnClickListener(this);
        Button b2 = findViewById(R.id.two);
        b2.setOnClickListener(this);
        Button b3 = findViewById(R.id.three);
        b3.setOnClickListener(this);
        Button b4 = findViewById(R.id.four);
        b4.setOnClickListener(this);
        Button b5 = findViewById(R.id.five);
        b5.setOnClickListener(this);
        Button b6 = findViewById(R.id.plus);
        b6.setOnClickListener(this);
        Button b7 = findViewById(R.id.minus);
        b7.setOnClickListener(this);
        Button b8 = findViewById(R.id.equal);
        b8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String val=input.getText().toString();
                String value=infixToPostfix(val);
                Stack<Integer> s = new Stack<Integer>();
                for(int i=0;i<value.length();i++)
                {
                    if(value.charAt(i)!='+' && value.charAt(i)!='-'&& value.charAt(i)!='*'&& value.charAt(i)!='/')
                    {
                        int num = 0;
                        int j = i;
                        while (value.charAt(j)!= '+' && value.charAt(j)!= '-'  && j < value.length()) {
                            num = num * 10 + (value.charAt(j) - '0');
                            j++;
                        }
                        i=j-1;
                        s.push(num);
                    }
                    else
                    {
                        int a=s.pop();
                        int b = s.pop();
                        if(value.charAt(i)=='+')
                        {
                            s.push(b+a);
                        }
                        else if(value.charAt(i)=='-')
                        {
                            s.push(b-a);
                        }
                        else if(value.charAt(i)=='*')
                        {
                            s.push(b*a);
                        }
                        else
                        {
                            s.push(b/a) ;
                        }
                    }
                }
                int ans=s.peek();
                TextView result = findViewById(R.id.result);
                result.setText(ans);
            }
        });
    }



    @Override
    public  void onClick(View v) {
        Button b=(Button)v;
        String text = b.getText().toString();
        TextView input = findViewById(R.id.input);
        String value= input.getText().toString();
        value=value+text;
        input.setText(value);

    }
}
