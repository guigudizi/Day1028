package com.example.administrator.day1028.commom;

/**
 * @author Administrator
 * @version $Rev$
 * @time 2016/10/29 10:57
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */

public class CommonUrls {
    private static final String BASE_URL = "http://c.m.163.com/";

    public static final String NEWS_TYPEURL = BASE_URL + "nc/topicset/android/subscribe/manage/listspecial.html";

    public static String getUrl(String tid) {
        return BASE_URL + "nc/article/list/" + tid + "/0-20.html";
    }

    public static String getNewsContUrl(String docId){
        return BASE_URL+"nc/article/"+docId+"/full.html";
    }
}
