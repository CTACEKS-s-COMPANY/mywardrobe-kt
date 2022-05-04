package ru.alexsas.mywardrobe.screens.main.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import ru.alexsas.mywardrobe.R;
import ru.alexsas.mywardrobe.databinding.FragmentForgotPasswordBinding;


public class ForgotPasswordFragment extends Fragment {


    FirebaseAuth mAuth;
    FragmentForgotPasswordBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        mBinding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mBinding.emailEditText.getText().toString();
                if (validateemail()) {
                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("RRR", "Email sent.");
                                        Toast.makeText(getContext(), "Email send. Check your mail",
                                                Toast.LENGTH_SHORT).show();
                                        Navigation.findNavController(view).popBackStack();
                                    } else {
                                        Log.d("RRR", "Email don't send");
                                        Toast.makeText(getContext(), "Error with your email. Check on correct",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    private boolean validateemail() {
        boolean valid = true;

        String email = mBinding.emailEditText.getText().toString();
        if (TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mBinding.emailTextInput.setError(getString(R.string.email_error_msg));
            valid = false;
        } else {
            mBinding.emailTextInput.setError(null);
        }
        return valid;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}