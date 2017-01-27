package com.jcbbhe.wechat.ui.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.jcbbhe.common.bean.ChatToolbarEmoticons
import com.jcbbhe.common.bean.VoiceRecorderFile
import com.jcbbhe.common.widget.ChatMessageToolbar
import com.jcbbhe.doctor.bean.ChatToolbarPlus
import com.jcbbhe.wechat.Constant
import com.jcbbhe.wechat.R
import com.jcbbhe.wechat.Utils.RxBus
import com.jcbbhe.wechat.tool.EMessage
import kotterknife.bindView
import rx.Observable
import java.util.*


class ChatRoomActivity : BaseActivity(), ChatMessageToolbar.OnSendButtonClickListener,
        ChatMessageToolbar.OnVoiceRecorderStopListener, ChatMessageToolbar.OnPlusItemClickListener {

    val toolbar: Toolbar by bindView(R.id.chat_room_toolbar)
    val chatToolbar: ChatMessageToolbar by bindView(R.id.chat_room_chat_toolbar)
    val messageList: RecyclerView by bindView(R.id.user_message_list)
    var messageResList: List<EMMessage>? = null
    var userid = ""
    var messageAdapter: RecyclerView.Adapter<messageListAdapter.ViewHolder>? = null
    private var messageSendSuccess: Observable<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)
        userid = intent.getLongExtra("userid", 0).toString()
        toolbar.title = userid
        setSupportActionBar(toolbar)

        val conversation = EMClient.getInstance().chatManager().getConversation(userid)
        messageResList = conversation?.allMessages



        messageSendSuccess = RxBus.get().register(Constant.SUBSCRIBE_MESSAGE_SEND_SUCCESS)
        messageSendSuccess?.subscribe {
            messageResList = conversation.allMessages
            runOnUiThread { messageAdapter?.notifyDataSetChanged() }
        }

        val plusResources = ArrayList<ChatToolbarPlus>()
        plusResources.add(ChatToolbarPlus("图片", ContextCompat.getDrawable(this, R.drawable.send_img_icon)))
        plusResources.add(ChatToolbarPlus("语音", ContextCompat.getDrawable(this, R.drawable.send_voice_icon)))
        plusResources.add(ChatToolbarPlus("视频", ContextCompat.getDrawable(this, R.drawable.send_video_icon)))
        plusResources.add(ChatToolbarPlus("位置", ContextCompat.getDrawable(this, R.drawable.send_location_icon)))


        val emoticonResources = ArrayList<ChatToolbarEmoticons>()
//        emoticonResources.add(ChatToolbarEmoticons(ContextCompat.getDrawable(this, R.drawable.ic_add_white_24dp), null))
//        emoticonResources.add(ChatToolbarEmoticons(ContextCompat.getDrawable(this, R.drawable.ic_add_white_24dp), null))
//        emoticonResources.add(ChatToolbarEmoticons(ContextCompat.getDrawable(this, R.drawable.ic_add_white_24dp), null))
//        emoticonResources.add(ChatToolbarEmoticons(ContextCompat.getDrawable(this, R.drawable.ic_add_white_24dp), null))

        chatToolbar.emoticonResources = emoticonResources        //表情资源
        chatToolbar.plusResources = plusResources                //更多模块资源
        chatToolbar.sendButtonClickListener = this
        chatToolbar.voicePath = Constant.Application_AudioPath   //录音文件保存位置
        chatToolbar.VoiceRecorderStop = this                     //录音完成回掉
        chatToolbar.plusItemClickListener = this

        if (messageResList != null) {
            messageAdapter = messageListAdapter()
            messageList.adapter = messageAdapter
            messageList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.get().unregister(Constant.SUBSCRIBE_MESSAGE_SEND_SUCCESS, messageSendSuccess!!)
    }

    /**
     * 点击发送按钮监听
     */
    override fun OnSendButtonClick(text: Editable) {
        EMessage.sendText(text.toString(), userid)
    }

    /**
     * 录音完成监听
     */
    override fun OnVoiceRecorderStop(f: VoiceRecorderFile) {
        EMessage.sendVoice(f.filePath!!, "", f.fileLength!!, userid)
    }

    /**
     * 更多功能点击回调
     */
    override fun OnPlusItemClick(s: String) {
        when (s) {
            "0" -> ""
            "1" -> ""
            "2" -> ""
            "3" -> ""
        }

    }

    inner class messageListAdapter : RecyclerView.Adapter<messageListAdapter.ViewHolder>() {
        inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val messageText: TextView by bindView(R.id.message_text)
        }

        override fun getItemCount(): Int {
            return messageResList!!.count()
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.recyclerview_item_message_text, null, true)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            val message = messageResList!![position]
            when (message.type) {
                EMMessage.Type.TXT -> {
                    holder?.messageText?.text = (message.body as EMTextMessageBody).message
                }
                EMMessage.Type.IMAGE -> {

                }

            }

        }

    }

}
