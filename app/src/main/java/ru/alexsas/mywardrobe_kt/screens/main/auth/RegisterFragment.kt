package ru.alexsas.mywardrobe_kt.screens.main.auth

import android.content.ContentValues
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import ru.alexsas.mywardrobe_kt.R
import ru.alexsas.mywardrobe_kt.databinding.FragmentRegisterBinding

@Suppress("NAME_SHADOWING")
class RegisterFragment: Fragment(R.layout.fragment_register) {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mBinding: FragmentRegisterBinding




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentRegisterBinding.bind(view)
        //Buttons
        mBinding.registerButton.setOnClickListener {
            val email: String = mBinding.emailEditText.text.toString()
            val password: String = mBinding.passwordEditText.text.toString()
            mBinding.retryPasswordEditText.text.toString()
            createAccount(email, password)
        }
        mBinding.cancelButton.setOnClickListener { view ->
            findNavController(
                view
            ).popBackStack()
        }
        mAuth = FirebaseAuth.getInstance()
    }


    private fun validateForm(): Boolean {
        var valid = true
        val email: String = mBinding.emailEditText.text.toString()
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mBinding.emailTextInput.error = getString(R.string.email_error_msg)
            valid = false
        } else {
            mBinding.emailTextInput.error = null
        }
        val password: String = mBinding.passwordEditText.text.toString()
        if (TextUtils.isEmpty(password) || password.length <= 8) {
            mBinding.passwordTextInput.error = getString(R.string.password_error_msg)
            valid = false
        } else {
            mBinding.passwordTextInput.error = null
        }
        val retry_password: String = mBinding.retryPasswordEditText.text.toString()
        if (password != retry_password) {
            mBinding.retryPasswordTextInput.error = getString(R.string.retry_password_error_msg)
            valid = false
        } else {
            mBinding.retryPasswordTextInput.error = null
        }
        return valid
    }

    private fun createAccount(email: String, password: String) {
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
                    findNavController().navigate(R.id.action_registerFragment_to_tabsFragment)
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

}