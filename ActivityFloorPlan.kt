package velociter.kumar.property

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.lay_flore_plan.*

class ActivityFloorPlan :AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_flore_plan)
        var tital=intent.getStringExtra("tital")
        txt_tital.text=tital
        var list: ArrayList<String> = ArrayList()
        list =intent.getStringArrayListExtra("floorplan")!!
        recyclerFlorePlan !!.layoutManager = GridLayoutManager(this,2, LinearLayout.VERTICAL, false)
        var adpter = AdapterFlorePlan(this,list)
        recyclerFlorePlan!!.adapter = adpter


        img_arrowback.setOnClickListener { onBackPressed() }
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }
}