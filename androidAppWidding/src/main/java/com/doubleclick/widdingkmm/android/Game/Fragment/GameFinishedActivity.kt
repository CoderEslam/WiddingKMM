package com.doubleclick.widdingkmm.android.Game.Fragment

import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.databinding.ActivityGameFinishedBinding
import kotlinx.android.synthetic.main.activity_game_finished.view.*
import java.util.*
import kotlin.collections.ArrayList


class GameFinishedActivity : AppCompatActivity() {

    private lateinit var array: ArrayList<String>

    private lateinit var binding: ActivityGameFinishedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameFinishedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        array = intent.extras!!.getStringArrayList("array") as ArrayList<String>
        Log.e("GameFinishedActivity", "onCreate: $array")
        val images = arrayOf(
            R.drawable.hakimi,
            R.drawable.hennery,
            R.drawable.iman_bani,
            R.drawable.jeneffer,
            R.drawable.justen_bieber,
            R.drawable.ladygaga,
            R.drawable.manal,
            R.drawable.messi,
            R.drawable.tamer_hosenu
        )
        val locales = arrayOf(
            "Hamidou",
            "Sarao",
            "Sbai",
            "Cielo",
            "Zohore",
            "Riad Palace",
            "Janna Palace"
        )
        val Catering = arrayOf(
            "Sarao",
            "Afrah Tarek Ceuta",
            "Ajanif Traiteur",
            "Ahmad Traiteur",
            "Al barakate Traiteur",
            "Oum Alae El Mhassani",
            "Essetti Traiteur",
            "Kawtarita Agadour"
        )

        val maquiladoras = arrayOf(
            "Mariam",
            "Ziana rim",
            "Faristyle brid",
            "Umimis",
            "Karima",
            "Ihsan Elegance",
            "Charifa Lahlah",
            "Chaimae"
        )

        val fotografos = arrayOf(
            "Ester",
            "Flores de Farida",
            "Lidia Sánchez",
            "Alberto Mur",
            "Barnosiservices",
            "M.I fotógrafos",
            "Bilal fotógrafías",
            "Fs photography 20"
        )

        val grupo_de_musica = arrayOf(
            "Dakka Marrakchia Ceuta",
            "Dakka platinium",
            "Dj percusion",
            "Abdelilah Chriif",
            "Adata dudu",
            "Hadra tetouania",
            "Ochestra Anwar",
            "Dj Bila"
        )
        val r = Random()
        val result: Int = r.nextInt(6)
        val r2 = Random()
        val result2: Int = r2.nextInt(6)
        binding.randomImage.setImageDrawable(resources.getDrawable(images[result]))
        binding.Locales.setText(locales[result] + " o " + locales[result2])
        binding.Catering.setText(Catering[result] + " o " + Catering[result2])
        binding.Maquilladoras.setText(maquiladoras[result] + " o " + maquiladoras[result2])
        binding.Fotografos.setText(fotografos[result] + " o " + fotografos[result2])
        binding.GrupoDeMusica.setText(grupo_de_musica[result] + " o " + grupo_de_musica[result2])
    }
}