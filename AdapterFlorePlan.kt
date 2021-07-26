package velociter.kumar.property

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import kotlinx.android.synthetic.main.lay_pdf_view.view.*

class AdapterFlorePlan(var context: Context,var listofpdf:ArrayList<String>) :RecyclerView.Adapter<AdapterFlorePlan.ViewHolderFlorePlan>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderFlorePlan {
        val v = LayoutInflater.from(context).inflate(R.layout.lay_pdf_view, p0, false)
        return AdapterFlorePlan.ViewHolderFlorePlan(v)
    }

    override fun getItemCount(): Int {
        return listofpdf.size
    }

    override fun onBindViewHolder(p0: ViewHolderFlorePlan, p1: Int) {
            p0.bindItems()
            var nu=p1+1
            p0.number!!.text="("+nu.toString()+")"
            p0.pd!!.setOnClickListener {

                openNewTabWindow("http://docs.google.com/gview?embedded=true&url=" + listofpdf.get(p1))




            }
    }


    class ViewHolderFlorePlan(itemView: View): RecyclerView.ViewHolder(itemView) {
        var pd: ImageView? = null
        var number:TextView?=null
        fun bindItems() {
            pd=itemView.pdfView
            number=itemView.txtNumber

        }
    }

    fun openNewTabWindow(urls: String) {
        val uris = Uri.parse(urls)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intents.putExtras(b)
        context.startActivity(intents)
    }
}