package velociter.kumar.property

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.pdf_view_web.*

class ActivityPDFView :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pdf_view_web)
        var url=intent.getStringExtra("pdfurl")
     //   Log.e("uuuuuuuuuuuuuuuuuuuuuuuuu",""+url)
      //  pdfweb.getSettings().setJavaScriptEnabled(true);
      //  pdfweb.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url)


        var webSetting=webViewPDf!!.settings
        webSetting.javaScriptEnabled=true
        webSetting.builtInZoomControls=true
        webViewPDf!!.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url)
    }
}