package ru.alexsas.mywardrobe_kt.screens.main.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import ru.alexsas.mywardrobe_kt.databinding.FragmentLoginBinding
import ru.alexsas.mywardrobe_kt.R


class LoginFragment : Fragment() {
    private var mAuth: FirebaseAuth? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var mBinding: FragmentLoginBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return mBinding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Buttons
        mBinding.logInButton.setOnClickListener(View.OnClickListener {
            val email: String = mBinding.emailEditText.getText().toString()
            val password: String = mBinding.passwordEditText.getText().toString()
            signIn(email, password)
        })
        mBinding.signInButton.setOnClickListener(View.OnClickListener { googlesignIn() })
        mBinding.passwordEditText.setOnKeyListener(View.OnKeyListener { view, i, keyEvent ->
            if (mBinding.passwordEditText.getText().toString().length() >= 1) {
                mBinding.passwordTextInput.setError(null)
            }
            false
        })
        mBinding.tvForgotPassword.setOnClickListener(View.OnClickListener {
            Navigation.findNavController(
                view
            ).navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        })
        mBinding.tvToRegister.setOnClickListener(View.OnClickListener {
            Navigation.findNavController(
                view
            ).navigate(R.id.action_loginFragment_to_registerFragment)
        })
        val gso: GoogleSignInOptions = Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
    }

    private fun googlesignIn() {
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId())
                firebaseAuthWithGoogle(account.getIdToken())
                Toast.makeText(
                    context, "Google sign in good",
                    Toast.LENGTH_SHORT
                ).show()
                Navigation.findNavController(view)
                    .navigate(R.id.action_loginFragment_to_tabsFragment)
            } catch (e: ApiException) {
                // Google Sign In failed
                Log.w(
                    TAG,
                    """
                    There is no such account, please check
                    your details
                    """.trimIndent(), e
                )
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity(), object : OnCompleteListener<AuthResult?>() {
                fun onComplete(task: Task<AuthResult?>) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException())
                        Snackbar.make(
                            mBinding.loginlayout,
                            "Authentication Failed.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            })
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
        return valid
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }
        Log.d(TAG, "Validate OK! $email $password")
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity(), object : OnCompleteListener<AuthResult?>() {
                fun onComplete(task: Task<AuthResult?>) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        Toast.makeText(
                            context, "Authentication Successful",
                            Toast.LENGTH_SHORT
                        ).show()
                        Navigation.findNavController(view)
                            .navigate(R.id.action_loginFragment_to_tabsFragment)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException())
                        Toast.makeText(
                            context, """
     There is no such account, please check
     your details
     """.trimIndent(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    companion object {
        private const val RC_SIGN_IN = 9001
        private const val TAG = "RRR"
    }
}