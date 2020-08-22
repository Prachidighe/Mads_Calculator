package com.calculator.mads.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.calculator.mads.adaptet.HistoryAdapter;
import com.calculator.mads.databinding.FragmentHistoryBinding;
import com.calculator.mads.model.CalculationHistory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHistoryBinding fragmentHistoryBinding = FragmentHistoryBinding.inflate(inflater);

        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("calculations").child(currentUser);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<CalculationHistory> calculationHistoryArrayList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    CalculationHistory artist = postSnapshot.getValue(CalculationHistory.class);
                    calculationHistoryArrayList.add(artist);
                }

                if (dataSnapshot.getChildrenCount() == calculationHistoryArrayList.size()) {
                    fragmentHistoryBinding.rvCalculationsHistory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    fragmentHistoryBinding.rvCalculationsHistory.setAdapter(new HistoryAdapter(calculationHistoryArrayList, getActivity()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return fragmentHistoryBinding.getRoot();
    }
}
