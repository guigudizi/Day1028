package com.example.administrator.day1028.manager;

import com.example.administrator.day1028.entity.NetEase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version $Rev$
 * @time 2016/11/1 16:07
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */

public class DBmanager {
    private static DBmanager db;

    private List<NetEase> list;

    private DBmanager() {
        list=new ArrayList<>();
    }

    public static DBmanager getDb() {
        if (db == null) {
            db = new DBmanager();
        }
        return db;
    }

}
