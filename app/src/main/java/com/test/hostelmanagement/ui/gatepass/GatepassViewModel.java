package com.test.hostelmanagement.ui.gatepass;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GatepassViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GatepassViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Press + to generate a gatepass");
    }

    public LiveData<String> getText() {
        return mText;
    }
}