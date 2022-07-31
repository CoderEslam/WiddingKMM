package com.doubleclick.widdingkmm.android.ui.Login.Fragments

import android.app.Application
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.Navigation
import com.doubleclick.widdingkmm.android.MainActivity
import com.doubleclick.widdingkmm.android.Model.Constants
import com.doubleclick.widdingkmm.android.R

import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.fragment_sign_up.*


/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {

    private lateinit var iHaveAccount: TextView
    private lateinit var username: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var signup: Button
    private lateinit var auth: FirebaseAuth;
    private lateinit var reference: DatabaseReference
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reference = FirebaseDatabase.getInstance().reference;
        auth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        iHaveAccount = view.findViewById(R.id.iHaveAccount);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        username = view.findViewById(R.id.username);
        signup = view.findViewById(R.id.signup);
        progressBar = view.findViewById(R.id.progressBar);
        iHaveAccount.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(SignUpFragmentDirections.actionSignUpFragmentToSigninFragment())
        }

        signup.setOnClickListener {
            SignUp(
                email.text.toString().trim(),
                password.text.toString().trim(),
                username.text.toString().trim()
            )
        }
    }

    private fun SignUp(email: String, password: String, username: String) {
        if (notEmpity(email, password, username) && isNetworkConnected()) {
            progressBar.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val map: HashMap<String, Any> = HashMap();
                    map["name"] = username;
                    map["email"] = email;
                    map["password"] = password;
                    map["phone"] = "";
                    map["image"] = "";
                    map["cover"] = ""
                    map["description"] = ""
                    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val id = auth.currentUser!!.uid.toString();
                            map["token"] = task.result.toString()
                            map["id"] = id
                            reference.child(Constants.DBUsers).child(id).updateChildren(map)
                            startActivity(Intent(requireActivity(), MainActivity::class.java))
                            progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun notEmpity(email: String, password: String, username: String): Boolean {
        return email != "" && password != "" && username != ""
    }

    fun isNetworkConnected(): Boolean {
        val connectivityManager =
            requireActivity().getSystemService(Application.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }


}