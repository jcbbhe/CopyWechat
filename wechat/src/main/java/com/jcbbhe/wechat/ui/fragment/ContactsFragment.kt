package com.jcbbhe.wechat.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jcbbhe.wechat.R
import com.jcbbhe.wechat.bean.UserBean
import com.jcbbhe.wechat.http.HttpTool
import com.jcbbhe.wechat.tool.GreenDaoUtils
import com.jcbbhe.wechat.tool.RecyclerViewDecoration
import com.jcbbhe.wechat.tool.Tools
import com.jcbbhe.wechat.ui.activity.ChatRoomActivity
import kotterknife.bindView
import retrofit2.Call
import retrofit2.Response

/**
 * Created by jcbbhe on 17/1/1.
 */

class ContactsFragment : Fragment(), View.OnClickListener {

    var friendsResList: List<UserBean>? = null
    val friendsRecyclerView: RecyclerView by bindView(R.id.friends_recyclerview)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userDao = GreenDaoUtils.getSingleTon(context).getmDaoSession().userBeanDao
        friendsResList = userDao.loadAll()
        HttpTool.Gson().getFriendList().enqueue(object : HttpTool.Callback<List<UserBean>>() {
            override fun onResponse(call: Call<List<UserBean>>?, response: Response<List<UserBean>>?) {
                for (item in response!!.body()) {
                    userDao.insertOrReplace(item)
                }
                friendsResList = userDao.loadAll()
                friendsRecyclerView.adapter = friendsAdapter()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_contacts, null)
    }

    override fun onStart() {
        super.onStart()
        friendsRecyclerView.adapter = friendsAdapter()
        friendsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        friendsRecyclerView.addItemDecoration(RecyclerViewDecoration(context, 1))
    }

    inner class friendsAdapter : RecyclerView.Adapter<friendsAdapter.ViewHolder>() {

        val inflater: LayoutInflater = LayoutInflater.from(context)

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val fiendHeadPhoto: ImageView by bindView(R.id.friend_head_photo)
            val friendName: TextView by bindView(R.id.friend_name)
        }

        override fun getItemCount(): Int {
            return friendsResList!!.count()
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val view = inflater.inflate(R.layout.recyclerview_item_contacts, parent, false)
            view.setOnClickListener(this@ContactsFragment)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            val user = friendsResList!![position]
            holder?.itemView?.tag = user.buserid
            holder?.friendName?.text = user.username
            Glide.with(context).load(Tools.getImagePath(user.headphoto)).into(holder?.fiendHeadPhoto)
        }
    }

    override fun onClick(v: View?) {
        val intent = Intent(context, ChatRoomActivity::class.java)
        intent.putExtra("userid", v?.tag as Long)
        startActivity(intent)
    }
}