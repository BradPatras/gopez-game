package com.mygdx.gopez.android.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mygdx.gopez.android.R;
public class OnboardingFragment extends Fragment {

    final Handler dismissHandler = new Handler();
    Listener listener;
    public OnboardingFragment() {
        // Required empty public constructor
    }


    public static OnboardingFragment newInstance(OnboardingFragment.Listener listener) {
        OnboardingFragment fragment = new OnboardingFragment();
        fragment.listener = listener;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dismissHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onboardingFinished();
            }
        }, 3000);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding, container, false);
    }

    public interface Listener{
        void onboardingFinished();
    }

}
