package com.jcbbhe.common.tool

/**
 * Created by jcbbhe on 17/1/3.
 */
object EndeCode {

    /**
     * String的字符串转换成unicode的String
     * @param String strText 全角字符串
     * *
     * @return String 每个unicode之间无分隔符
     * *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun strToUnicode(strText: String): String {
        var c: Char
        val str = StringBuilder()
        var intAsc: Int
        var strHex: String
        for (i in 0..strText.length - 1) {
            c = strText[i]
            intAsc = c.toInt()
            strHex = Integer.toHexString(intAsc)
            if (intAsc > 128)
                str.append("\\u" + strHex)
            else
            // 低位在前面补00
                str.append("\\u00" + strHex)
        }
        return str.toString()
    }

    /**
     * unicode的String转换成String的字符串
     * @param String hex 16进制值字符串 （一个unicode为2byte）
     * *
     * @return String 全角字符串
     */
    fun unicodeToString(hex: String): String {
        val t = hex.length / 6
        val str = StringBuilder()
        for (i in 0..t - 1) {
            val s = hex.substring(i * 6, (i + 1) * 6)
            // 高位需要补上00再转
            val s1 = s.substring(2, 4) + "00"
            // 低位直接转
            val s2 = s.substring(4)
            // 将16进制的string转为int
            val n = Integer.valueOf(s1, 16)!! + Integer.valueOf(s2, 16)!!
            // 将int转换为字符
            val chars = Character.toChars(n)
            str.append(String(chars))
        }
        return str.toString()
    }



}