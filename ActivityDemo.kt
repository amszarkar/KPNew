package velociter.kumar.property

import android.os.Bundle
import android.text.Html
import android.view.View
import kotlinx.android.synthetic.main.activity_demo.*

class ActivityDemo : BaseActivity () {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        val received: String = intent.getStringExtra("input")
        txtTital.text=received
        if (received.equals("about project")){
            val aboutData:String=intent.getStringExtra("aboutData")
            txtAminities.text= Html.fromHtml(aboutData)
            titalAminity.visibility=View.GONE
            titalSpeci.visibility=View.GONE

        }
        else if (received.equals("amenities")){
            if (intent.getStringExtra("amenities1").equals("")){
                titalAminity.visibility=View.GONE
            }
            val amenitiesData1:String=intent.getStringExtra("amenities1")
            val amenitiesData2:String=intent.getStringExtra("amenities2")
            txtAminities.text=Html.fromHtml(amenitiesData1)
            txtAminities2.text=Html.fromHtml(amenitiesData2)

        }

        img_arrowback.setOnClickListener { onBackPressed() }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}