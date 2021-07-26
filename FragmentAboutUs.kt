package velociter.kumar.property

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class FragmentAboutUs :Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // var view=inflater!!.inflate(R.layout.fragment_offer, container, false)

        var rootView = inflater?.inflate(R.layout.fragment_about_us, container, false)





        return rootView

    }
}