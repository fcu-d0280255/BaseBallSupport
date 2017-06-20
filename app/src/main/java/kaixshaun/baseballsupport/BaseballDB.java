package kaixshaun.baseballsupport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;


public class BaseballDB {

    private SQLiteDatabase db;
    public MyDBHelper myDBHelper;

    public BaseballDB(Context context){

       myDBHelper = new MyDBHelper(context);
        db =myDBHelper.getWritableDatabase();
    }



    public void close() {

        db.close();
    }
    //產生亂數編號
    public String CreatePassWord(){
        int[] word = new int[8];
        int mod;

        for(int i = 0; i < 8; i++){

            mod = (int)((Math.random()*7)%3);
            if(mod ==1){    //數字
                word[i]=(int)((Math.random()*10) + 48);
            }else if(mod ==2){  //大寫英文
                word[i] = (char)((Math.random()*26) + 65);
            }else{    //小寫英文
                word[i] = (char)((Math.random()*26) + 97);
            }
        }
        StringBuffer newPassword = new StringBuffer();
        for(int j = 0; j < 8; j++){
            newPassword.append((char)word[j]);
        }
        String rePassword = new String(newPassword);
        return rePassword;
    }

    //public void close() {
    //    db.close();
    //}

    //輸入新的賽局名稱
    public  String insertGamename(String gamename) {

        String gameid = new String(this.CreatePassWord());
        String hometeamid = new String(this.CreatePassWord());
        String awayteamid = new String(this.CreatePassWord());


        ContentValues cv = new ContentValues();

        cv.put("GameID",gameid);
        cv.put("GameName",gamename);
        cv.put("HomeTeamID",hometeamid);
        cv.put("AwayTeamID",awayteamid);
        cv.put("HomeScore",0);
        cv.put("AwayScore",0);

        db.insert("Game", null, cv);

        return gameid;
    }

    // 輸入新的後攻方隊名
    public String insertHometeamname(String teamname, String gameid) {

        Cursor c = db.rawQuery("select * from Game WHERE GameID =  '"+ gameid+"'", null);
        String hometeamID = c.getString(c.getColumnIndex("HomeTeamID"));

        ContentValues cv = new ContentValues();
        cv.put("TeamID", hometeamID);
        cv.put("TeamName",teamname);
        db.insert("Team", null, cv);

        return hometeamID;
    }

    // 輸入新的先攻方隊名
    public String insertAwayteamname(String gameid, String teamname) {

        Cursor c = db.rawQuery("select AwayTeamID from Game WHERE GameID = '"+ gameid+ "'", null);
        c.moveToFirst();
        String awayteamID = c.toString();
        ContentValues cv = new ContentValues();
        cv.put("TeamID", awayteamID);
        cv.put("TeamName",teamname);
        db.insert("Team", null, cv);

        return awayteamID;
    }

    //輸入隊員名單
    public void insertTeammate(String gameid, String teamid, int back, String s_b){

        ContentValues cv = new ContentValues();
        cv.put("GameID", gameid);
        cv.put("TeamID",teamid);
        cv.put("Back", back);
        cv.put("S_B", s_b);
        db.insert("Teammate", null, cv);
    }

    //輸入先發名單
    public void insertBattingorder(String gameid, String teamid, int back, int order, int rule){

        ContentValues cv = new ContentValues();
        cv.put("GameID", gameid);
        cv.put("TeamID", teamid);
        cv.put("Back", back);
        cv.put("Order", order);
        cv.put("Rule", rule);
        db.insert("BattingOrder", null, cv);
    }

    //輸入打擊紀錄
    public void insertRecord(String gameid, String teamid, int back, int inning, int round, int order, String situation, int flyto, int out, int rbi,String notes){

        ContentValues cv = new ContentValues();
        cv.put("GameID", gameid);
        cv.put("TeamID", teamid);
        cv.put("Back", back);
        cv.put("Inning", inning);
        cv.put("Round", round);
        cv.put("Order", order);
        cv.put("Situation", situation);
        cv.put("Flyto", flyto);
        cv.put("Out", out);
        cv.put("RBI", rbi);
        cv.put("Notes", notes);
        db.insert("Record", null, cv);
    }

    public Cursor selestorder(String gameid){

        Cursor c = db.rawQuery("select * from BattingOrder where GameID = '"+gameid+"'",null);

        return c;
    }




}