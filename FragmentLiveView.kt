package velociter.kumar.property

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_play_video.view.*

class FragmentLiveView : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val Rootview = inflater!!.inflate(R.layout.activity_play_video, null, false)
        Rootview.img_arrowback.setOnClickListener { activity!!.onBackPressed() }
        var webSetting=Rootview.webView!!.settings
        webSetting.javaScriptEnabled=true
        webSetting.builtInZoomControls=true
        var liveStreem=this.arguments!!.getString("liveview","No data Avalable")
        Rootview.webView!!.loadUrl(liveStreem)
        return Rootview
    }
}