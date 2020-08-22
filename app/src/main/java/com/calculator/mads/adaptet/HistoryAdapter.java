package com.calculator.mads.adaptet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.calculator.mads.model.CalculationHistory;
import com.calculator.mads.databinding.ItemCalculationBinding;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    public Context mContext;
    private ArrayList<CalculationHistory> calculationList = new ArrayList<>();
    public int selectedPosition = -1;

    public HistoryAdapter(ArrayList<CalculationHistory> calculationList, Context context) {
        this.mContext = context;
        this.calculationList = calculationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemCalculationBinding itemCalculationBinding = ItemCalculationBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemCalculationBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(calculationList.get(position), mContext);
    }

    @Override
    public int getItemCount() {
        return calculationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemCalculationBinding itemCalculationBinding;

        public ViewHolder(ItemCalculationBinding itemCalculationBinding) {
            super(itemCalculationBinding.getRoot());
            this.itemCalculationBinding = itemCalculationBinding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(CalculationHistory calculationHistory, Context mContext) {
           itemCalculationBinding.tvQuestion.setText("Question: " +calculationHistory.getExpression());
           itemCalculationBinding.tvAnswer.setText("Answer: " +calculationHistory.getAnswer());
        }
    }
}