package velociter.kumar.property

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.gallary_view.view.*

class AdapterGallery (var context:Context , var galleryImages:ArrayList<String>):RecyclerView.Adapter<AdapterGallery.ViewHolderGallery>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderGallery {
        val v = LayoutInflater.from(context).inflate(R.layout.gallary_view, p0, false)
        return AdapterGallery.ViewHolderGallery(v)
    }

    override fun getItemCount(): Int {
       return galleryImages.size
    }

    override fun onBindViewHolder(p0: ViewHolderGallery, p1: Int) {
        p0.bindItems()
        Picasso.get().load(galleryImages.get(p1)).into(p0.GalleryImage)
        p0.GalleryImage!!.setOnClickListener{
            val mBuilder = AlertDialog.Builder(context)
            val mView = LayoutInflater.from(context).inflate(R.layout.dialog_custom_layout, null)
            val photoView = mView.findViewById<PhotoView>(R.id.imageView)

            Picasso.get().load(galleryImages.get(p1)).into(photoView)


            mBuilder.setView(mView)
            val mDialog = mBuilder.create()
            val animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
            photoView.startAnimation(animation)
            mDialog.show()
        }
    }


    class ViewHolderGallery (itemView: View): RecyclerView.ViewHolder(itemView) {
        var GalleryImage: ImageView? = null
        fun bindItems() {
            GalleryImage= itemView.galleryImage

        }
    }
}