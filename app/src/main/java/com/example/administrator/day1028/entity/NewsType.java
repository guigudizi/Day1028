package com.example.administrator.day1028.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Administrator
 * @version $Rev$
 * @time 2016/10/29 11:35
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */

public class NewsType implements Serializable {
    public ArrayList<SubName> tList;

    public NewsType(ArrayList<SubName> tList) {
        this.tList = tList;
    }


    public static class SubName implements Serializable {
        public String tid, tname;

        public SubName(String tid, String tname) {
            this.tid = tid;
            this.tname = tname;
        }
    }
}
