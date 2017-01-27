package com.jcbbhe.common.widget

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.media.MediaRecorder
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.jcbbhe.common.R
import com.jcbbhe.common.bean.ChatToolbarEmoticons
import com.jcbbhe.common.bean.VoiceRecorderFile
import com.jcbbhe.common.tool.RecycleViewDivider
import com.jcbbhe.doctor.bean.ChatToolbarPlus
import kotterknife.bindView
import java.util.*


/**
 * Created by jcbbhe on 17/1/1.
 */
class ChatMessageToolbar : LinearLayout {

    //获取相应控件，定义为private，保护参数不被外部修改
    private val voiceTextChangeButton: ImageButton by bindView(R.id.chat_message_voice_text)
    private val messageEdittext: EditText by bindView(R.id.chat_message_content)
    private val emojiButton: ImageButton by bindView(R.id.chat_message_emoji)
    private val plusButton: ImageButton by bindView(R.id.chat_message_plus)
    private val sendButton: Button by bindView(R.id.chat_message_send)
    private val voiceButton: Button by bindView(R.id.chat_message_voice)
    private val emojiBlock: LinearLayout by bindView(R.id.chat_message_emoji_block)
    private val plusBlock: RecyclerView by bindView(R.id.chat_message_plus_block)
    private val emoticonBar: RecyclerView by bindView(R.id.chat_message_emoticon_icon)
    private val emoticonContent: ViewPager by bindView(R.id.chat_message_emoticon_content)
    private var mRecorder: MediaRecorder? = null


    //定义相应的监听事件
    var sendButtonClickListener: OnSendButtonClickListener? = null
    var plusItemClickListener: OnPlusItemClickListener? = null
    var VoiceRecorderStop: OnVoiceRecorderStopListener? = null

    //定义emojiBlock,plusBlock的数据源
    var plusResources: List<ChatToolbarPlus>? = null
    var emoticonResources: ArrayList<ChatToolbarEmoticons>? = null
        set(value) {
//            value?.add(0, ChatToolbarEmoticons(ContextCompat.getDrawable(context, R.drawable.ic_chat_message_toolbar_emoji_36dp), null))
            field = value
        }
    var voicePath: String? = null
    private var voiceAllPath: String? = null
    private var im: InputMethodManager? = null
    private var inputMode = 0
    private var startRecorderTime: Int? = null
    private var endRecorderTime: Int? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.widget_chat_message_toolbar, this, true)
        messageEdittext.addTextChangedListener(object : TextWatcher {
            /**
             * 通过判断Edittext的输入情况，来处理是否显示发送按钮
             */
            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s)) {
                    plusButton.visibility = View.VISIBLE
                    sendButton.visibility = View.GONE
                } else if (!TextUtils.isEmpty(s)) {
                    plusButton.visibility = View.GONE
                    sendButton.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        //当文本输入框被点击的时候隐藏emojiBlock,plusBlock
        messageEdittext.setOnClickListener {
            emojiBlock.visibility = View.GONE
            plusBlock.visibility = View.GONE
            im?.showSoftInput(messageEdittext, InputMethodManager.SHOW_FORCED)
        }

        voiceTextChangeButton.setOnClickListener { imageButton(R.id.chat_message_voice_text) }
        emojiButton.setOnClickListener { imageButton(R.id.chat_message_emoji) }
        plusButton.setOnClickListener { imageButton(R.id.chat_message_plus) }
        sendButton.setOnClickListener { imageButton(R.id.chat_message_send) }

        val voiceDialog: AlertDialog = AlertDialog.Builder(context, R.style.MyCommonDialog).create()
        voiceDialog.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        voiceDialog.setMessage("录音中……")
        voiceButton.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                startRecorder()
                voiceDialog.show()
            } else if (motionEvent.action == MotionEvent.ACTION_UP) {
                stopRecorder()
                VoiceRecorderStop?.OnVoiceRecorderStop(VoiceRecorderFile(voiceAllPath, 10f, endRecorderTime!!.minus(startRecorderTime!!)))
                voiceDialog.dismiss()
            }
            false
        }
        plusBlock.layoutManager = GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
        emoticonBar.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        emoticonBar.addItemDecoration(RecycleViewDivider(context!!, GridLayoutManager.HORIZONTAL))
        im = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    /**
     * 语音，表情，更多功能处理
     * 1.切换语音和文字输入
     * 2.打开表情输入模块
     * 3.打开更多功能模块
     */
    private fun imageButton(resId: Int) {
        emojiBlock.visibility = View.GONE
        plusBlock.visibility = View.GONE
        when (resId) {
            R.id.chat_message_voice_text -> {
                if (inputMode == 0) {
                    im?.hideSoftInputFromWindow(messageEdittext.windowToken, 0)
                    voiceButton.visibility = View.VISIBLE
                    messageEdittext.visibility = View.GONE
                    inputMode = 1
                } else {
                    im?.showSoftInput(messageEdittext, InputMethodManager.SHOW_FORCED)
                    voiceButton.visibility = View.GONE
                    messageEdittext.visibility = View.VISIBLE
                    messageEdittext.requestFocus()
                    inputMode = 0
                }
            }
            R.id.chat_message_emoji -> {
                im?.hideSoftInputFromWindow(messageEdittext.windowToken, 0)
                emojiBlock.visibility = if (emojiBlock.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                emoticonBar.adapter = emoticonBarAdapter(context, emoticonResources!!)
                emoticonContent.adapter = emoticonContentAdapter(context, emoticonResources!!)
            }
            R.id.chat_message_plus -> {
                im?.hideSoftInputFromWindow(messageEdittext.windowToken, 0)
                if (plusBlock.visibility == View.VISIBLE) {
                    plusBlock.visibility = View.GONE
                } else {
                    plusBlock.adapter = plusBlockAdapter(context, plusResources!!, plusItemClickListener)
                    plusBlock.visibility = View.VISIBLE
                }
            }
            R.id.chat_message_send -> {
                sendButtonClickListener!!.OnSendButtonClick(messageEdittext.text)
                messageEdittext.text.clear()
            }
        }
    }

    /**
     * 更多功能列表适配器
     */
    private class plusBlockAdapter(context: Context?, plusResources: List<ChatToolbarPlus>, p: OnPlusItemClickListener?) :
            RecyclerView.Adapter<plusBlockAdapter.ViewHolder>(), OnClickListener {
        val adapterResources = plusResources
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val picl: OnPlusItemClickListener? = p

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView by bindView(R.id.chat_message_plus_drawable)
            val textView: TextView by bindView(R.id.chat_message_plus_text)
        }

        override fun getItemCount(): Int {
            return adapterResources.size
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val rootView = inflater.inflate(R.layout.widget_chat_message_toolbar_plus, null, false)
            rootView.setOnClickListener(this)
            return ViewHolder(rootView)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            val resItem = adapterResources[position]
            holder?.imageView?.background = resItem.itemImageDrawable
            holder?.textView?.text = resItem.itemText
            holder?.itemView?.tag = if (resItem.key == null) position else resItem.key
        }

        override fun onClick(v: View?) {
            picl?.OnPlusItemClick(v?.tag.toString())
        }
    }

    /**
     * 表情模块，item的适配器
     */
    private class emoticonBarAdapter(context: Context?, r: List<ChatToolbarEmoticons>) :
            RecyclerView.Adapter<emoticonBarAdapter.ViewHolder>(), OnClickListener {
        val adapterResources = r
        val inflater: LayoutInflater = LayoutInflater.from(context)
        var checkedView: View? = null

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val icon: ImageView by bindView(R.id.emoticonbar_icon)
        }

        override fun getItemCount(): Int {
            return adapterResources.size
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val view = inflater.inflate(R.layout.widget_chat_message_toolbar_emoticonbar, parent, false)
            view.setOnClickListener(this)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
//            holder?.icon?.setImageDrawable(adapterResources[position].icon)
//            holder?.itemView?.tag = position
        }

        override fun onClick(v: View?) {
            checkedView?.setBackgroundColor(Color.BLACK)
            checkedView = v
            Log.i("viewid", v?.tag.toString())
            v?.setBackgroundColor(Color.RED)
        }
    }

    /**
     * 表情模块适配器
     */
    private class emoticonContentAdapter(c: Context, r: List<ChatToolbarEmoticons>) : PagerAdapter() {
        val emoticonResources = r
        val context = c
        val inflater = LayoutInflater.from(context)
        override fun getCount(): Int {
            return emoticonResources.size
        }

        override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
            return view == `object`
        }

        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            val view = inflater.inflate(R.layout.widget_chat_message_toolbar_emoticoncontent, null)
            val recyclerView = view.findViewById(R.id.emoticoncontent_item) as RecyclerView
            recyclerView.layoutManager = GridLayoutManager(context, 5, GridLayoutManager.VERTICAL, false)
//            val recyclerView = RecyclerView(context)
//            val lp = ViewGroup.LayoutParams(context,null)
//            lp.width = ViewGroup.LayoutParams.MATCH_PARENT
//            lp.height = ViewGroup.LayoutParams.MATCH_PARENT
//            recyclerView.layoutParams = lp
            recyclerView.adapter = emoticonBarAdapter(context, emoticonResources)
            container?.addView(view)
            return view
        }

    }

    interface OnSendButtonClickListener {
        fun OnSendButtonClick(text: Editable)
    }

    interface OnPlusItemClickListener {
        fun OnPlusItemClick(s: String)
    }

    /**
     * 停止录音的回掉方法
     */
    interface OnVoiceRecorderStopListener {
        fun OnVoiceRecorderStop(f: VoiceRecorderFile)
    }

    /**
     * 开始后台录音
     */
    private fun startRecorder() {
        startRecorderTime = System.currentTimeMillis().toInt()
        voiceAllPath = voicePath + startRecorderTime + ".mp3"
        mRecorder = MediaRecorder()
        mRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mRecorder?.setOutputFile(voiceAllPath)
        mRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mRecorder?.prepare()
        mRecorder?.start()
    }

    /**
     * 停止后台录音
     */
    private fun stopRecorder() {
        endRecorderTime = System.currentTimeMillis().toInt()
        mRecorder?.stop()
        mRecorder?.release()
    }


}

