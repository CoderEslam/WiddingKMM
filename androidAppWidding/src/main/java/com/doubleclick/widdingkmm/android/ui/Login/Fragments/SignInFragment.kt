package com.doubleclick.widdingkmm.android.ui.Login.Fragments

import android.app.Application
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.Navigation
import com.doubleclick.widdingkmm.android.HomeActivity
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.Views.fabfilter.main.MainActivity
import com.doubleclick.widdingkmm.android.utils.isNetworkConnected
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_in.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LogInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignInFragment : Fragment() {

    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText;
    private lateinit var signin: Button
    private lateinit var idontHaveAccount: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        idontHaveAccount = view.findViewById(R.id.idontHaveAccount);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        signin = view.findViewById(R.id.signin);
        progressBar = view.findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();
        idontHaveAccount.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(SignInFragmentDirections.actionSigninFragmentToSignUpFragment())
        }

        signin.setOnClickListener {
            signIn(email.text.toString().trim(), password.text.toString().trim())
        }


    }

    private fun signIn(email: String, password: String) {
        if (notEmpty(email, password) && isNetworkConnected(requireActivity())) {
            progressBar.visibility = View.VISIBLE
            Log.e("EMIAL", email + " " + password);
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(requireActivity(), HomeActivity::class.java))
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun notEmpty(email: String, password: String): Boolean {
        return email != "" && password != "";
    }


}