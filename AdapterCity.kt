package velociter.kumar.property

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class AdapterCity(var context: Context): RecyclerView.Adapter<AdapterCity.ViewHolderCity>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderCity {
        val v = LayoutInflater.from(context).inflate(R.layout.city_view, p0, false)
        return AdapterCity.ViewHolderCity(v)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(p0: ViewHolderCity, p1: Int) {
        p0.bindItems()
    }


    class ViewHolderCity (itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindItems() {

        }
    }
}