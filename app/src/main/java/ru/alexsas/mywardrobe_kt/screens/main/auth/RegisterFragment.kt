package ru.alexsas.mywardrobe_kt.screens.main.auth

import android.content.ContentValues
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import ru.alexsas.mywardrobe_kt.R
import ru.alexsas.mywardrobe_kt.databinding.FragmentRegisterBinding

class RegisterFragment: Fragment(R.layout.fragment_register) {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mBinding: FragmentRegisterBinding




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentRegisterBinding.bind(view)
        //Buttons
        mBinding.registerButton.setOnClickListener(View.OnClickListener {
            val email: String = mBinding.emailEditText.getText().toString()
            val password: String = mBinding.passwordEditText.getText().toString()
            val reply_password: String = mBinding.retryPasswordEditText.getText().toString()
            createAccount(email, password, reply_password)
        })
        mBinding.cancelButton.setOnClickListener(View.OnClickListener { view ->
            findNavController(
                view
            ).popBackStack()
        })
        mAuth = FirebaseAuth.getInstance()
    }


    private fun validateForm(): Boolean {
        var valid = true
        val email: String = mBinding.emailEditText.getText().toString()
        if (TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mBinding.emailTextInput.setError(getString(R.string.email_error_msg))
            valid = false
        } else {
            mBinding.emailTextInput.setError(null)
        }
        val password: String = mBinding.passwordEditText.getText().toString()
        if (TextUtils.isEmpty(password) || password.length <= 8) {
            mBinding.passwordTextInput.setError(getString(R.string.password_error_msg))
            valid = false
        } else {
            mBinding.passwordTextInput.setError(null)
        }
        val retry_password: String = mBinding.retryPasswordEditText.getText().toString()
        if (password != retry_password) {
            mBinding.retryPasswordTextInput.setError(getString(R.string.retry_password_error_msg))
            valid = false
        } else {
            mBinding.retryPasswordTextInput.setError(null)
        }
        return valid
    }

    private fun createAccount(email: String, password: String, retry_passrord: String) {
        Log.d(ContentValues.TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                requireActivity()
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    Toast.makeText(
                        context, "Account successfully created!",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_registerFragment_to_tabsFragment);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context, "Account creation failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        mBinding = null
    }

}