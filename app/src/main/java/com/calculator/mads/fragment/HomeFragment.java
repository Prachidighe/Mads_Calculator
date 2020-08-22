package com.calculator.mads.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.calculator.mads.App;
import com.calculator.mads.databinding.FragmentHomeBinding;
import com.calculator.mads.model.CalculationHistory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Stack;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding fragmentHomeBinding;
    private char[] calculationOperations = {'*', '+', '/', '-'};
    private int[] operatorsRank = {1, 2, 3, 4};  // 1 is highest and 4 is lowest
    private boolean clearCalculation = false;
    private DatabaseReference mDatabase;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater);
        mDatabase = FirebaseDatabase.getInstance().getReference("calculations");

        fragmentHomeBinding.button1.setOnClickListener(v -> {
            setCalculationBox("1");
        });

        fragmentHomeBinding.button2.setOnClickListener(v -> {
            setCalculationBox("2");
        });

        fragmentHomeBinding.button3.setOnClickListener(v -> {
            setCalculationBox("3");
        });

        fragmentHomeBinding.button4.setOnClickListener(v -> {
            setCalculationBox("4");
        });

        fragmentHomeBinding.button5.setOnClickListener(v -> {
            setCalculationBox("5");
        });

        fragmentHomeBinding.button6.setOnClickListener(v -> {
            setCalculationBox("6");
        });

        fragmentHomeBinding.button7.setOnClickListener(v -> {
            setCalculationBox("7");
        });

        fragmentHomeBinding.button8.setOnClickListener(v -> {
            setCalculationBox("8");
        });

        fragmentHomeBinding.button9.setOnClickListener(v -> {
            setCalculationBox("9");
        });

        fragmentHomeBinding.button0.setOnClickListener(v -> {
            setCalculationBox("0");
        });

        fragmentHomeBinding.buttonadd.setOnClickListener(v -> {
            fragmentHomeBinding.etDisplay.setText(fragmentHomeBinding.etDisplay.getText() + " + ");
        });

        fragmentHomeBinding.buttonsub.setOnClickListener(v -> {
            fragmentHomeBinding.etDisplay.setText(fragmentHomeBinding.etDisplay.getText() + " - ");
        });

        fragmentHomeBinding.buttonmul.setOnClickListener(v -> {
            fragmentHomeBinding.etDisplay.setText(fragmentHomeBinding.etDisplay.getText() + " * ");
        });

        fragmentHomeBinding.buttondiv.setOnClickListener(v -> {
            fragmentHomeBinding.etDisplay.setText(fragmentHomeBinding.etDisplay.getText() + " / ");
        });

        fragmentHomeBinding.buttoneql.setOnClickListener(v -> {
            clearCalculation = true;
            String expression = fragmentHomeBinding.etDisplay.getText().toString();
            if (evaluate(expression) != -0) {
                fragmentHomeBinding.etDisplay.setText(evaluate(expression) + "");
                HashMap<String, String> values = new HashMap<>();
                values.put("expression", expression);
                values.put("answer", fragmentHomeBinding.etDisplay.getText().toString());

                String userId = mDatabase.push().getKey();
                // pushing value to db
                CalculationHistory calculationHistory = new CalculationHistory(
                        values.get("expression"), values.get("answer"));
                mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(userId)
                        .setValue(calculationHistory);

                //  saveInHistory(expression, fragmentHomeBinding.etDisplay.getText().toString());
            } else {
                fragmentHomeBinding.etDisplay.setText("invalid");
            }
        });

        fragmentHomeBinding.buttonDel.setOnClickListener(v -> {
            String s1 = fragmentHomeBinding.etDisplay.getText().toString();
            if (s1.length() > 0)
                fragmentHomeBinding.etDisplay.setText(s1.substring(0, s1.length() - 1));
        });

        fragmentHomeBinding.btnClear.setOnClickListener(view -> fragmentHomeBinding.etDisplay.setText(""));

        return fragmentHomeBinding.getRoot();
    }

    private void setCalculationBox(String value) {
        if (clearCalculation) {
            fragmentHomeBinding.etDisplay.setText("");
            this.clearCalculation = false;
        }
        fragmentHomeBinding.etDisplay.setText(fragmentHomeBinding.etDisplay.getText() + value);
    }

    private void saveInHistory(String expression, String answer) {
        HashMap<String, String> values = new HashMap<>();
        values.put("expression", expression);
        values.put("answer", answer);

        App.calculationsList.add(values);
    }

    private float evaluate(String expression) {
        try {
            char[] tokens = expression.toCharArray();
            Stack<Float> numberValues = new Stack<>();
            Stack<Character> operationValues = new Stack<>();

            for (int i = 0; i < tokens.length; i++) {
                // is space is found skip to next index
                if (tokens[i] == ' ') {
                    continue;
                }

                if (!checkIsOperator(tokens[i])) {
                    StringBuilder s = new StringBuilder();
                    while (i < tokens.length && tokens[i] != ' ' && !checkIsOperator(tokens[i])) {
                        s.append(tokens[i++]);
                    }
                    numberValues.push(Float.parseFloat(s.toString()));
                } else if (checkIsOperator(tokens[i])) {
                    // if its operator list has one value then check the ranking
                    while (!operationValues.empty() && hasPrecedence(tokens[i], operationValues.peek())) {
                        // if the precedence value is greater than the next operator values is popped and
                        // calculation is performed.
                        numberValues.push(calculate(operationValues.pop(), numberValues.pop(), numberValues.pop()));
                    }
                    operationValues.push(tokens[i]);
                }
            }
            while (!operationValues.empty()) {
                numberValues.push(calculate(operationValues.pop(), numberValues.pop(), numberValues.pop()));
            }
            return numberValues.pop();
        } catch (Exception ee) {
            return -0;
        }
    }

    private boolean checkIsOperator(char operator) {
        for (int i = 0; i < calculationOperations.length; i++) {
            if (operator == calculationOperations[i]) {
                // if operator matches the list item return true.
                return true;
            }
        }
        return false;
    }

    private boolean hasPrecedence(char operator1, char operator2) {
        //check the ranking of the operators
        int op1p = getPrecedence(operator1);
        int op2p = getPrecedence(operator2);
        if (op2p > op1p){
            return false;
        }
        return true;
        //return op2p <= op1p;
    }

    public int getPrecedence(char c) {
        int i;
        for (i = 0; i < calculationOperations.length; i++) {
            if (c == calculationOperations[i])
                break;
        }
        return operatorsRank[i];
    }

    public float calculate(char op, float b, float a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
        }
        return 0;
    }
}
