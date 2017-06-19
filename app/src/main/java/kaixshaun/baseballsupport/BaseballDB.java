package kaixshaun.baseballsupport;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;


public class BaseballDB extends SQLiteOpenHelper{

    SQLiteDatabase db;

    public static final String CREATE_Game_TABLE = "CREATE TABLE IF NOT EXISTS Game(_id INTEGER PRIMARY KEY AUTOINCREMENT,GameID TEXT NOT NULL,GameName TEXT,HomeTeamID TEXT NOT NULL,AwayTeamID TEXT NOT NULL,HomeScore INTEGER,AwayScore INTEGER)";
    public static final String CREATE_Team_TABLE = "CREATE TABLE IF NOT EXISTS Team(_id INTEGER PRIMARY KEY AUTOINCREMENT,TeamID,TeamName);";
    public static final String CREATE_Inning_TABLE = "CREATE TABLE IF NOT EXISTS Inning(_id INTEGER PRIMARY KEY AUTOINCREMENT,GameID,Inning,TopHalf,BottonHalf);";
    public static final String CREATE_BattingOrder_TABLE = "CREATE TABLE IF NOT EXISTS BattingOrder(_id INTEGER PRIMARY KEY AUTOINCREMENT,GameID,TeamID,Back,Order,Rule);";
    public static final String CREATE_Teammate_TABLE = "CREATE TABLE IF NOT EXISTS Teammate(_id INTEGER PRIMARY KEY AUTOINCREMENT,GameID,TeamID,Back,S_B);";
    public static final String CREATE_Record_TABLE = "CREATE TABLE IF NOT EXISTS Record(_id INTEGER PRIMARY KEY AUTOINCREMENT,GameID,TeamID,Back,Inning,Round,Order,Situation,Flyto,Out,RBI,Notes)";
    public static final String CREATE_FinalData_TABLE = "CREATE TABLE IF NOT EXISTS FinalData(_id INTEGER PRIMARY KEY AUTOINCREMENT,GameID,TeamID,Back,Order,rule,PA,BA,OBP,Hit,Walk,Error,RBI)";
    public static final String CREATE_TeamRecord_TABLE = "CREATE TABLE IF NOT EXISTS TeamRecord(_id INTEGER PRIMARY KEY AUTOINCREMENT,GameID,TeamID,TotalHit,TotalWalk,TotalError)";


    public BaseballDB(Context context) {
        super( context, "baseball.db", null, 4);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_Game_TABLE);
        db.execSQL(CREATE_Team_TABLE);
        db.execSQL(CREATE_Inning_TABLE);
        db.execSQL(CREATE_BattingOrder_TABLE);
        db.execSQL(CREATE_Teammate_TABLE);
        db.execSQL(CREATE_Record_TABLE);
        db.execSQL(CREATE_FinalData_TABLE);
        db.execSQL(CREATE_TeamRecord_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS Game");
        db.execSQL(" DROP TABLE IF EXISTS Team");
        db.execSQL(" DROP TABLE IF EXISTS Inning");
        db.execSQL(" DROP TABLE IF EXISTS BattingOrder");
        db.execSQL(" DROP TABLE IF EXISTS Teammate");
        db.execSQL(" DROP TABLE IF EXISTS Record");
        db.execSQL(" DROP TABLE IF EXISTS FinalData");
        db.execSQL(" DROP TABLE IF EXISTS TeamRecord ");

        onCreate(db);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (db == null || !db.isOpen()) {
            db = new BaseballDB(context, "baseball.db", null, 4).getWritableDatabase();
        }
        return db;
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
/*
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

        Cursor c = db.rawQuery("select * from Game WHERE GameID = '"+ gameid+"'", null);
        String awayteamID =  c.getString(c.getColumnIndex("AwayTeamID"));
        Log.d("awayteamID",awayteamID);
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

*/


}