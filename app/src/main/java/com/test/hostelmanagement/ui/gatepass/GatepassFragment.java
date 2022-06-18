package com.test.hostelmanagement.ui.gatepass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.test.hostelmanagement.databinding.FragmentGatepassBinding;

public class GatepassFragment extends Fragment {

    private FragmentGatepassBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        com.test.hostelmanagement.ui.gatepass.GatepassViewModel gatepassViewModel =
                new ViewModelProvider(this).get(GatepassViewModel.class);

        binding = FragmentGatepassBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        gatepassViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}