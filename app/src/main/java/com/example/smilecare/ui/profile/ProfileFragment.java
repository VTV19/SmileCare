package com.example.smilecare.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.smilecare.User;
import com.example.smilecare.databinding.FragmentProfileBinding;
import com.example.smilecare.ui.authentication.LoginActivity;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    ProfileViewModel viewModel;

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textViewEmail = binding.textViewEmail;
        final TextView textViewName = binding.textViewName;
        final TextView textViewLastName = binding.textViewLastName;
        final TextView textViewTreatmentMethod = binding.textViewTreatmentMethod;
        final Button buttonSupport = binding.buttonSupport;
        final Button buttonLogout = binding.buttonLogout;

        buttonSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(inflater.getContext(),
                        "По всем вопросам обращаться на почту: tanya.volkova.2017@yandex.ru",
                        Toast.LENGTH_SHORT).show();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.logout();
            }
        });

        observeViewModel(inflater);

        viewModel.getCurrentUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                textViewEmail.setText(user.getEmail());
                textViewName.setText(user.getName());
                textViewLastName.setText(user.getLastName());
                textViewTreatmentMethod.setText(user.getMethod());
            }
        });


        return root;
    }

    private void observeViewModel(@NonNull LayoutInflater inflater) {
        viewModel.getUser().observe((LifecycleOwner) this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser == null) {
                    Intent intent = LoginActivity.newIntent(inflater.getContext());
                    startActivity(intent);
                    requireActivity().finish();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}