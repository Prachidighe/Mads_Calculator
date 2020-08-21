package com.calculator.mads.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.calculator.mads.databinding.FragmentHomeBinding;

import java.util.Stack;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding fragmentHomeBinding;
    private boolean isPressedOnce = false;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater);


        fragmentHomeBinding.button1.setOnClickListener(v -> {
            isPressedOnce = false;
            fragmentHomeBinding.etDisplay.setText(fragmentHomeBinding.etDisplay.getText() + "1");
        });

        fragmentHomeBinding.button2.setOnClickListener(v -> {
            isPressedOnce = false;
            fragmentHomeBinding.etDisplay.setText(fragmentHomeBinding.etDisplay.getText() + "2");
        });

        fragmentHomeBinding.button3.setOnClickListener(v -> {
            isPressedOnce = false;
            fragmentHomeBinding.etDisplay.setText(fragmentHomeBinding.etDisplay.getText() + "3");
        });

        fragmentHomeBinding.button4.setOnClickListener(v -> {
            isPressedOnce = false;
            fragmentHomeBinding.etDisplay.setText(fragmentHomeBinding.etDisplay.getText() + "4");
        });

        fragmentHomeBinding.button5.setOnClickListener(v -> {
            isPressedOnce = false;
            fragmentHomeBinding.etDisplay.setText(fragmentHomeBinding.etDisplay.getText() + "5");
        });

        fragmentHomeBinding.button6.setOnClickListener(v -> {
            isPressedOnce = false;
            fragmentHomeBinding.etDisplay.setText(fragmentHomeBinding.etDisplay.getText() + "6");
        });

        fragmentHomeBinding.button7.setOnClickListener(v -> {
            isPressedOnce = false;
            fragmentHomeBinding.etDisplay.setText(fragmentHomeBinding.etDisplay.getText() + "7");
        });

        fragmentHomeBinding.button8.setOnClickListener(v -> {
            isPressedOnce = false;
            fragmentHomeBinding.etDisplay.setText(fragmentHomeBinding.etDisplay.getText() + "8");
        });

        fragmentHomeBinding.button9.setOnClickListener(v -> {
            isPressedOnce = false;
            fragmentHomeBinding.etDisplay.setText(fragmentHomeBinding.etDisplay.getText() + "9");
        });

        fragmentHomeBinding.button0.setOnClickListener(v ->
        {
            isPressedOnce = false;
            fragmentHomeBinding.etDisplay.setText(fragmentHomeBinding.etDisplay.getText() + "0");
        });

        fragmentHomeBinding.buttonadd.setOnClickListener(v -> {
            if (!isPressedOnce) {
                fragmentHomeBinding.etDisplay.setText(fragmentHomeBinding.etDisplay.getText() + " + ");
            }
            isPressedOnce = true;

        });

        fragmentHomeBinding.buttonsub.setOnClickListener(v -> {
            if (!isPressedOnce) {
                fragmentHomeBinding.etDisplay.setText(fragmentHomeBinding.etDisplay.getText() + " - ");
            }
            isPressedOnce = true;
        });

        fragmentHomeBinding.buttonmul.setOnClickListener(v -> {
            if (!isPressedOnce) {
                fragmentHomeBinding.etDisplay.setText(fragmentHomeBinding.etDisplay.getText() + " * ");
            }
            isPressedOnce = true;
        });

        fragmentHomeBinding.buttondiv.setOnClickListener(v -> {
            if (!isPressedOnce) {
                fragmentHomeBinding.etDisplay.setText(fragmentHomeBinding.etDisplay.getText() + " / ");
            }
            isPressedOnce = true;
        });


        fragmentHomeBinding.buttoneql.setOnClickListener(v -> {
            String exp = fragmentHomeBinding.etDisplay.getText().toString();
            int i = 0;
            i = exp.indexOf('%');
            if (i != -1) {
                float n1 = Float.parseFloat(exp.substring(0, i - 1));
                float n2 = Float.parseFloat(exp.substring(i + 2));
                float res = (n1 / 100) * n2;
                fragmentHomeBinding.etDisplay.setText(res + "");
            } else {
                if (evaluate(exp) != -1)
                    fragmentHomeBinding.etDisplay.setText(evaluate(exp) + "");
                else fragmentHomeBinding.etDisplay.setText("invalid");
            }
        });

        fragmentHomeBinding.buttonDel.setOnClickListener(v -> fragmentHomeBinding.etDisplay.setText(""));

        return fragmentHomeBinding.getRoot();
    }

    char[] operations = {'*', '/', '+', '-'};
    int[] precedence = {0, 0, 1, 1, 2, 2, 3, 3};

    private float evaluate(String exp) {
        try {
            char[] tokens = exp.toCharArray();
            Stack<Float> values = new Stack<>();
            Stack<Character> ops = new Stack<>();

            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i] == ' ')
                    continue;

                if (!isOperator(tokens[i])) {
                    StringBuilder s = new StringBuilder();
                    while (i < tokens.length && tokens[i] != ' ' && !isOperator(tokens[i]))
                        s.append(tokens[i++]);
                    values.push(Float.parseFloat(s.toString()));
                } else if (isOperator(tokens[i])) {
                    while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                        values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                    ops.push(tokens[i]);
                }
            }
            while (!ops.empty())
                values.push(applyOp(ops.pop(), values.pop(), values.pop()));

            return values.pop();
        } catch (Exception ee) {
            return -1;
        }
    }

    public boolean isOperator(char t) {
        int i;
        for (i = 0; i < operations.length; i++) {
            if (t == operations[i])
                return true;
        }
        return false;
    }

    public boolean hasPrecedence(char op1, char op2) {
        int op1p = getPrecedence(op1);
        int op2p = getPrecedence(op2);
        return op2p <= op1p;
    }

    public float applyOp(char op, float b, float a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            case '^':
                return (float) Math.pow(a, b);
        }
        return 0;
    }

    public int getPrecedence(char c) {
        int i;
        for (i = 0; i < operations.length; i++) {
            if (c == operations[i])
                break;
        }
        return precedence[i];
    }

}
