package ru.alexsas.mywardrobe_kt.screens.main.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import ru.alexsas.mywardrobe_kt.R
import ru.alexsas.mywardrobe_kt.databinding.FragmentLoginBinding


class LoginFragment : Fragment(R.layout.fragment_login) {


    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var mBinding: FragmentLoginBinding

    private lateinit var mAuth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentLoginBinding.bind(view)

        //Buttons
        mBinding.logInButton.setOnClickListener{
            val email: String = mBinding.emailEditText.text.toString()
            val password: String = mBinding.passwordEditText.text.toString()
            signInEmail(email, password)
        }

        mBinding.signInButton.setOnClickListener{ googlesignIn() }

        mBinding.passwordEditText.setOnKeyListener{ _, _, _ ->
            if (mBinding.passwordEditText.text.toString().isNotEmpty()) {
                mBinding.passwordTextInput.error = null
            }
            false
        }

        mBinding.tvForgotPassword.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        mBinding.tvToRegister.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
    }

    private fun googlesignIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    Toast.makeText(context, "Google sign in good", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_tabsFragment)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Snackbar.make(
                        mBinding.loginlayout,
                        "Authentication Failed.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
    }


    private fun validateForm(): Boolean {
        var valid = true
        var email: String = mBinding.emailEditText.text.toString()
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
        return valid
    }

    private fun signInEmail(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }
        Log.d(TAG, "Validate OK! $email $password")
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    Toast.makeText(
                        context, "Authentication Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_loginFragment_to_tabsFragment)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        context, """
     There is no such account, please check
     your details
     """.trimIndent(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}
