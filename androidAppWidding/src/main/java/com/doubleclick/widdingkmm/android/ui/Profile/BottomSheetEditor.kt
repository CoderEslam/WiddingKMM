package com.doubleclick.widdingkmm.android.ui.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.doubleclick.widdingkmm.android.Model.Constants
import com.doubleclick.widdingkmm.android.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created By Eslam Ghazy on 7/29/2022
 */
class BottomSheetEditor(val userId: String, val typeChange: String) : BottomSheetDialogFragment() {


    private lateinit var type: EditText
    private lateinit var send: ImageView;
    private lateinit var reference: DatabaseReference
//    private var typeChange: String = ""
//    private var userId: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reference = FirebaseDatabase.getInstance().reference;
        return LayoutInflater.from(requireContext())
            .inflate(R.layout.eidt_profile, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        type = view.findViewById(R.id.type);
        send = view.findViewById(R.id.send);

        send.setOnClickListener {
            if (!type.text.equals("")) {
                val map: HashMap<String, Any> = HashMap();
                map["" + typeChange] = type.text.toString();
                reference.child(Constants.DBUsers).child(userId)
                    .updateChildren(map).addOnCompleteListener {
                        type.setText("");
                    }
            }
        }
    }
}