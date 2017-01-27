package com.jcbbhe.wechat.ui.activity

import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Images.Thumbnails.MICRO_KIND
import android.provider.MediaStore.Video.Media
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jcbbhe.wechat.R
import com.jcbbhe.wechat.Wechat.Companion.context
import com.jcbbhe.wechat.bean.ImageItem
import com.jcbbhe.wechat.tool.RecyclerViewDecoration
import kotterknife.bindView
import java.io.File
import java.util.*


class PhotoViewActivity : AppCompatActivity() {

    val photoView: RecyclerView by bindView(R.id.photo_view)

    private val imageItems = ArrayList<ImageItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_view)


        val projection = arrayOf(Media._ID, Media.DATA)
        val sortOrder = Media._ID + " desc"
        val cursor = contentResolver.query(Media.EXTERNAL_CONTENT_URI, projection, "", null, sortOrder)
        if (cursor.moveToFirst()) {
            do {
                val imageId = cursor.getInt(cursor.getColumnIndex(Media._ID))
                val imagePath = cursor.getString(cursor.getColumnIndex(Media.DATA))
                val imageItem = ImageItem(imageId, imagePath, 0)
                imageItems.add(imageItem)
            } while (cursor.moveToNext())
        }

        Log.i("imageItems", imageItems.count().toString())
        photoView.layoutManager = GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false)
        photoView.adapter = adapter()
        photoView.addItemDecoration(RecyclerViewDecoration(context, 2))

    }


    inner class adapter : RecyclerView.Adapter<adapter.ViewHolder>() {
        inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val photoviewItem: ImageView by bindView(R.id.photoview_item)
        }

        override fun getItemCount(): Int {
            return imageItems!!.count()
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.recyclerview_item_photoview, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            val imageItem = imageItems!![position]
            Glide.with(context)
                    .load(Uri.fromFile(File(imageItem.imagePath)))
                    .crossFade()
                    .centerCrop()
                    .into(holder?.photoviewItem)
        }
    }


}
