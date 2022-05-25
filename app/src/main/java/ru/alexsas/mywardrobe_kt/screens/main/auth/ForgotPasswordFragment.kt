package ru.alexsas.mywardrobe_kt.screens.main.auth

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import ru.alexsas.mywardrobe_kt.R
import ru.alexsas.mywardrobe_kt.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment: Fragment(R.layout.fragment_forgot_password) {

    private lateinit var mBinding: FragmentForgotPasswordBinding

    private lateinit var mAuth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentForgotPasswordBinding.bind(view)
        mBinding.cancelButton.setOnClickListener{findNavController().popBackStack() }

        mBinding.submitButton.setOnClickListener{
            val email: String = mBinding.emailEditText.text.toString()
            if (validateemail()) {
                mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("RRR", "Email sent.")
                            Toast.makeText(context, "Email send. Check your mail", Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        } else {
                            Log.d("RRR", "Email don't send")
                            Toast.makeText(context, "Error with your email. Check on correct", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        mAuth = FirebaseAuth.getInstance()
    }

    private fun validateemail(): Boolean {
        var valid = true
        val email: String = mBinding.emailEditText.text.toString()
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mBinding.emailTextInput.error = getString(R.string.email_error_msg)
            valid = false
        } else {
            mBinding.emailTextInput.error = null
        }
        return valid
    }

}