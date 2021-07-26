package velociter.kumar.property

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_layout.view.*
import velociter.kumar.property.data.ProjectData

class AdapterMain(val context: Context,val data:ArrayList<ProjectData>) : RecyclerView.Adapter<AdapterMain.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.list_layout, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItems()
        p0.Projectname!!.text=data.get(p1).mName
        p0.projectlocation!!.text=data.get(p1).mLocation+","+data.get(p1).mCity
        Picasso
            .get()
            .load("" + data.get(p1).mImg)
            .placeholder( R.drawable.progress_animation )
            .into(p0.ProjectImage)//identifier imageView is red
        p0.ProjectImage!!.setOnClickListener{

            var intent=Intent(context,ProjectLandingActivity::class.java)
            intent.putExtra("project_id",data.get(p1).mId)
            context.startActivity(intent)
        }


    }


    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ProjectImage:ImageView? = null
        var real:RelativeLayout?=null
        var Projectname:TextView?=null
        var projectlocation:TextView?=null
        fun bindItems() {
            ProjectImage= itemView.projectImage
            Projectname=itemView.projectname
            projectlocation=itemView.projectlocation




        }
    }
}