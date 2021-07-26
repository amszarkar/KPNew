package velociter.kumar.property

import android.content.Context
import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.webkit.JavascriptInterface
import android.widget.Toast
import kotlinx.android.synthetic.main.ai_bot_layout.*

class AI_Bot :AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ai_bot_layout)

        val webSettings = webview!!.settings
        webSettings.javaScriptEnabled = true
       /* webSettings.useWideViewPort=true
        webSettings.loadWithOverviewMode=true

        webSettings.allowFileAccess=true
        webSettings.javaScriptCanOpenWindowsAutomatically=true*/

        var aibot=intent.getStringExtra("aibot")

        //  Case 2 .. Create your own html page...
       webview!!.loadData("<html>" +aibot+ "\n</html>", "text/html", null)






    }


}