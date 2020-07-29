package com.shirleywbi.hnefatafl.ui.howToPlay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.shirleywbi.hnefatafl.R
import kotlinx.android.synthetic.main.howtoplay.*
import kotlinx.android.synthetic.main.howtoplayfragment.*

class HowToPlayFragment: Fragment {

    private var titleResource: Int
    private var textResource: Int
    private var drawableResources: List<Int>?

    constructor(titleResource: Int, textResource: Int, drawableResources: List<Int>? = null) {
        this.titleResource = titleResource
        this.textResource = textResource
        this.drawableResources = drawableResources
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.howtoplayfragment, container, false)
        view.findViewById<TextView>(R.id.htp_title).text = getString(titleResource)
        view.findViewById<TextView>(R.id.htp_text).text = getString(textResource)
        if (!drawableResources.isNullOrEmpty()) {
            // TODO: Add more than one image -- Currently only works for one image
            for (drawableResource: Int in drawableResources!!) {
                view.findViewById<ImageView>(R.id.htp_image).setImageResource(drawableResource)
            }
        }
        view.findViewById<ImageView>(R.id.htp_close_btn).setOnClickListener{
            activity?.finish()
        }
        return view
    }
}