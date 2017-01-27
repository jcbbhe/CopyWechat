package com.jcbbhe.wechat.bean;

import android.graphics.drawable.Drawable;
import com.jcbbhe.common.bean.ChatToolbarEmoticons;
import org.greenrobot.greendao.annotation.Entity;

/**
 * Created by jcbbhe on 17/1/6.
 */
@Entity
public class EmojiBean extends ChatToolbarEmoticons{

    class EmojiItem extends ChatToolbarEmoticons.Emoticon {
        private String name;
        private Drawable icon;
    }



}
