package kaixshaun.baseballsupport;

import android.content.Intent;
import android.database.Cursor;
import android.drm.DrmStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PlayRecording extends AppCompatActivity {

    public static final String HomeTeamID = "HomeTeamID";
    public static final String AwayTeamID = "AwayTeamID";
    public static final String GameID = "GameID";

    TextView InningView, OutView, ScoreView, BackView, RuleView, No_View,ODListView;
    Spinner FlytoView, BaseView, B_EView, SituationView, DiedwayView, KillView, GetScoreView;
    Button NextBtn, FinishBtn;

    int inning, out, awayscore = 0, homescore = 0, back, rule, awayteamno = 0, hometeamno = 0, order, flyto, rbi, gameout = 0, inninghalf = 0;

    Cursor awayteamorder, hometeamorder;
    String showInning, gameid, awayteamid, hometeamid, teamid, situation,base;
    int[] awayteamback = new int[10], awayteamrule = new int[10], hometeamback = new int[10], hometeamrule = new int[10];
    String[] names;
    BaseballDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_recording);

        declare();                                       //     宣告
        db = new BaseballDB(this);                      //  創建DB
        setid();                                        //  取得ID
        setorder();                                     //  將DB裡的名單存進陣列

        inning = inninghalf / 2 + 1;                    //  設定局數
        teamid = awayteamid;                           //   設定teamid 存進DB時用
        showInning = inning + "上";
        InningView.setText(showInning);                              //     設定版面資訊
        OutView.setText(gameout+"");                                 //
        ScoreView.setText(awayscore + " - " + homescore);             //
        BackView.setText(awayteamback[awayteamno]+"");             //
        RuleView.setText(turnrule(awayteamrule[awayteamno]));       //
        No_View.setText((awayteamno + 1)+"" );                      //

        back = awayteamback[awayteamno];
        rule = awayteamrule[awayteamno];
        order = awayteamno+1;


    }

    //將DB裡的守位轉換成字串中文
    public String turnrule(int temp){

        switch (temp) {

            case 1:
                return "投手";
            case 2:
                return "捕手";
            case 3:
                return "一壘手";
            case 4:
                return "二壘手";
            case 5:
                return "三壘手";
            case 6:
                return "游擊手";
            case 7:
                return "左外野手";
            case 8:
                return "中外野手";
            case 9:
                return "右外野手";
            case 10:
                return "自由手";
            default:
                return "指定打擊";
        }
    }

    //將落點轉成整數型態存入DB
    public int turnflyto(String temp) {

        switch (temp) {

            case "投手":
                return 1;
            case "捕手":
                return 2;
            case "一壘手":
                return 3;
            case "二壘手":
                return 4;
            case "三壘手":
                return 5;
            case "游擊手":
                return 6;
            case "左外野手":
                return 7;
            case "中外野手":
                return 8;
            case "右外野手":
                return 9;
            case "自由手":
                return 10;
            default:
                return 0;
        }
    }

    //從spinner取得字串
    private String getspinnerstring(Spinner temp) {

        return temp.getSelectedItem().toString();
    }

    //從spinner取得整數
    private int getspinnerint(Spinner temp) {

        return Integer.parseInt(temp.getSelectedItem().toString());
    }

    private void setid() {

        Intent intent = getIntent();
        gameid = intent.getStringExtra(Key_The_Home_Team_List.GameID);
        awayteamid = intent.getStringExtra(Key_The_Home_Team_List.AwayTeamID);
        hometeamid = intent.getStringExtra(Key_The_Home_Team_List.HomeTeamID);
    }

    private void setorder() {

        awayteamorder = db.selestorder(gameid, awayteamid);
        hometeamorder = db.selestorder(gameid, hometeamid);


        awayteamorder.moveToFirst();
        names = awayteamorder.getColumnNames();
        for (int i = 0; i < awayteamorder.getCount(); i++) {

            awayteamback[i] = awayteamorder.getInt(awayteamorder.getColumnIndex(names[3]));
            awayteamrule[i] = awayteamorder.getInt(awayteamorder.getColumnIndex(names[5]));
            Log.v ("awayteamback"+i+"=>",awayteamorder.getInt(awayteamorder.getColumnIndex(names[4]))+"");
            awayteamorder.moveToNext();
        }


        hometeamorder.moveToFirst();
        names = hometeamorder.getColumnNames();
        for (int i = 0; i < hometeamorder.getCount(); i++) {

            hometeamback[i] = hometeamorder.getInt(hometeamorder.getColumnIndex(names[3]));
            hometeamrule[i] = hometeamorder.getInt(hometeamorder.getColumnIndex(names[5]));
            Log.v ("hometeamback"+i+"=>",hometeamorder.getInt(hometeamorder.getColumnIndex(names[4]))+"");
            hometeamorder.moveToNext();
        }
    }


    private void declare() {

        InningView = (TextView) findViewById(R.id.Inning);
        OutView = (TextView) findViewById(R.id.Out);
        ScoreView = (TextView) findViewById(R.id.Score);
        BackView = (TextView) findViewById(R.id.Back);
        RuleView = (TextView) findViewById(R.id.Rule);
        No_View = (TextView) findViewById(R.id.No_);
        ODListView = (TextView) findViewById(R.id.ODList);

        final String[] getscore = {"0", "1", "2", "3", "4"};
        GetScoreView = (Spinner) findViewById(R.id.GetScore);
        ArrayAdapter<String> getscorelist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, getscore);
        GetScoreView.setAdapter(getscorelist);

        final String[] flyto = {"無", "投手", "捕手", "一壘手", "二壘手", "三壘手", "游擊手", "左外野手", "中外野手", "右外野手", "自由手"};
        FlytoView = (Spinner) findViewById(R.id.Flyto);
        ArrayAdapter<String> flytolist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, flyto);
        FlytoView.setAdapter(flytolist);

        final String[] onbase = {"無", "保送", "1", "2", "3", "全壘打"};
        BaseView = (Spinner) findViewById(R.id.Base);
        ArrayAdapter<String> onbaselist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, onbase);
        BaseView.setAdapter(onbaselist);

        final String[] b_e = {"B", "E"};
        B_EView = (Spinner) findViewById(R.id.B_E);
        ArrayAdapter<String> b_elist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, b_e);
        B_EView.setAdapter(b_elist);

        final String[] situation = {"...", "^"};
        SituationView = (Spinner) findViewById(R.id.Situation);
        ArrayAdapter<String> situationlist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, situation);
        SituationView.setAdapter(situationlist);

        final String[] diedway = {"無", "刺殺", "阻殺", "接殺", "K"};
        DiedwayView = (Spinner) findViewById(R.id.Diedway);
        ArrayAdapter<String> diedwaylist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, diedway);
        DiedwayView.setAdapter(diedwaylist);

        final String[] kill = {"0", "1", "2", "3"};
        KillView = (Spinner) findViewById(R.id.Kill);
        ArrayAdapter<String> killlist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, kill);
        KillView.setAdapter(killlist);

        NextBtn = (Button) findViewById(R.id.Next);
        NextBtn.setOnClickListener(nextmember);


        FinishBtn = (Button) findViewById(R.id.Finish);
        FinishBtn.setOnClickListener(finishgame);
    }

    private View.OnClickListener nextmember = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            boolean check = true;
            //儲存進DB時打擊狀況設定
            if (getspinnerstring(DiedwayView) != "無" && getspinnerstring(DiedwayView) != "K") {         //  當打者上壘"沒上壘"時的狀況設定

                base = "無";
                situation = "D";

                out = getspinnerint(KillView);
                flyto = turnflyto(getspinnerstring(FlytoView));
                rbi = getspinnerint(GetScoreView);

            }else if(getspinnerstring(DiedwayView) != "無" && getspinnerstring(DiedwayView) == "K"){

                base = "無";
                situation = "K";

                out = 1;
                flyto = 0;
                rbi = 0;


            } else if(getspinnerstring(DiedwayView) == "無" && getspinnerstring(BaseView) == "保送"){

                base = "無";
                situation = "保送";
                out = 0;
                flyto = 0;
                rbi = getspinnerint(GetScoreView);

            } else if(getspinnerstring(DiedwayView) == "無" && (getspinnerstring(BaseView) == "1" || getspinnerstring(BaseView) == "2" || getspinnerstring(BaseView) == "3" || getspinnerstring(BaseView) == "全壘打")){
                //  當打者"上壘"時的狀況設定

                base = getspinnerstring(BaseView);
                situation = getspinnerstring(B_EView);
                out = getspinnerint(KillView);
                if (getspinnerstring(BaseView) == "全壘打") {
                    flyto = 0;
                    situation = "B";
                    out = 0;
                }
                else
                    flyto = turnflyto(getspinnerstring(FlytoView));
                rbi = getspinnerint(GetScoreView);
            }
            else{

                Toast toast = Toast.makeText(PlayRecording.this,"輸入錯誤請重填!!",Toast.LENGTH_LONG);
                toast.show();
                check = false;
            }

           if(check) {

               //  將打擊紀錄存進DB
               db.insertRecord(gameid, teamid, back, inning, 0, order, base, situation, flyto, out, rbi, "");
               checkdata(gameid,teamid);

               //  紀錄半局的出局數
               gameout = gameout + out;

               //  當出局數為3時轉換半局
               if (gameout >= 3) {

                   inninghalf++;
                   gameout = 0;
                   rbi = 0;
               }

               //  判斷上下半局
               if (inninghalf % 2 == 0) {  //  上半局

                   awayscore = awayscore + rbi;    //  計算分數
                   awayteamno++;   //  下位打者

                   if (awayteamno >= 10) { //  當棒次超過10換回第一棒
                       awayteamno = 0;
                   }

                   teamid = awayteamid;
                   inning = inninghalf / 2 + 1;
                   showInning = inning + " 上";
                   InningView.setText(showInning);
                   OutView.setText(gameout + "");
                   ScoreView.setText(awayscore + " - " + homescore);
                   BackView.setText(awayteamback[awayteamno] + "");
                   RuleView.setText(turnrule(awayteamrule[awayteamno]));
                   No_View.setText((awayteamno + 1) + "");

                   back = awayteamback[awayteamno];
                   rule = awayteamrule[awayteamno];
                   order = awayteamno + 1;

               } else {    //  下半局

                   homescore = homescore + rbi;
                   if (hometeamno >= 10) {
                       hometeamno = 0;
                   }
                   teamid = hometeamid;
                   inning = inninghalf / 2 + 1;
                   showInning = inning + " 下";
                   InningView.setText(showInning);
                   OutView.setText(gameout + "");
                   ScoreView.setText(awayscore + " - " + homescore);
                   BackView.setText(hometeamback[hometeamno] + "");
                   RuleView.setText(turnrule(hometeamrule[hometeamno]));
                   No_View.setText((hometeamno + 1) + "");

                   back = hometeamback[hometeamno];
                   rule = hometeamrule[hometeamno];
                   order = hometeamno + 1;
                   hometeamno++;
               }
           }
        }

    };


    private View.OnClickListener finishgame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            db.updatescore(gameid,awayscore,homescore);
            Intent intent = new Intent();
            intent.putExtra(AwayTeamID, awayteamid);
            intent.putExtra(HomeTeamID, hometeamid);
            intent.putExtra(GameID, gameid);
            intent.setClass(PlayRecording.this,GameFinalData.class);
            startActivity(intent);
            PlayRecording.this.finish();
        }
    };

    public void checkdata(String gameid,String teamid){

        String temp="";
        Cursor c = db.selectrecording(gameid,teamid);


        c.moveToFirst();
        String[] debugnames = c.getColumnNames();
        for(int i = 0; i < c.getCount(); i ++){

            temp = temp+c.getInt(c.getColumnIndex(debugnames[3])) + " 號  " +
                    c.getInt(c.getColumnIndex(debugnames[4])) + " 上  " +
                    c.getInt(c.getColumnIndex(debugnames[6])) + " 棒  " +
                    c.getString(c.getColumnIndex(debugnames[8])) + "  飛去" +
                    c.getInt(c.getColumnIndex(debugnames[9])) + "   " +
                    c.getInt(c.getColumnIndex(debugnames[10])) + " out   得" +
                    c.getInt(c.getColumnIndex(debugnames[11])) + " 分\n";
            c.moveToNext();
        }
        Log.v("Debug",temp);
    }

    public void InputG2Data(String gameid,String awayteamid,String hometeamid){

        db.insertRecord(gameid,hometeamid,7,1,0,1,"1","E",6,0,0,"");
        db.insertRecord(gameid,hometeamid,7,3,0,1,"無","D",5,1,0,"");
        db.insertRecord(gameid,hometeamid,7,5,0,1,"3","B",10,0,1,"");
        db.insertRecord(gameid,hometeamid,15,1,0,2,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,15,3,0,2,"無","D",5,1,0,"");
        db.insertRecord(gameid,hometeamid,15,5,0,2,"無","D",6,1,0,"");
        db.insertRecord(gameid,hometeamid,29,1,0,3,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,29,3,0,3,"無","D",6,1,0,"");
        db.insertRecord(gameid,hometeamid,6,1,0,4,"1","B",8,0,0,"");
        db.insertRecord(gameid,hometeamid,6,4,0,4,"全壘打","B",0,0,1,"");
        db.insertRecord(gameid,hometeamid,4,1,0,5,"無","D",1,2,0,"");
        db.insertRecord(gameid,hometeamid,4,4,0,5,"無","D",4,1,0,"");
        db.insertRecord(gameid,hometeamid,23,2,0,6,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,23,4,0,6,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,51,2,0,7,"1","B",7,0,0,"");
        db.insertRecord(gameid,hometeamid,51,4,0,7,"無","D",6,1,0,"");
        db.insertRecord(gameid,hometeamid,49,2,0,8,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,49,5,0,8,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,8,2,0,9,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,8,5,0,9,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,31,2,0,10,"無","D",3,1,0,"");
        db.insertRecord(gameid,hometeamid,31,5,0,10,"無","D",10,1,0,"");

        db.insertRecord(gameid,awayteamid,25,1,0,1,"無","D",4,1,0,"");
        db.insertRecord(gameid,awayteamid,25,4,0,1,"1","E",4,0,0,"");
        db.insertRecord(gameid,awayteamid,25,6,0,1,"無","D",5,1,0,"");
        db.insertRecord(gameid,awayteamid,10,1,0,2,"無","D",3,1,0,"");
        db.insertRecord(gameid,awayteamid,10,4,0,2,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,10,6,0,2,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,3,1,0,3,"無","D",1,1,0,"");
        db.insertRecord(gameid,awayteamid,3,4,0,3,"無","D",8,1,0,"");
        db.insertRecord(gameid,awayteamid,3,6,0,3,"無","D",7,1,0,"");
        db.insertRecord(gameid,awayteamid,16,2,0,4,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,16,4,0,4,"無","保送",0,0,0,"");
        db.insertRecord(gameid,awayteamid,5,2,0,5,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,5,4,0,5,"無","D",2,1,0,"");
        db.insertRecord(gameid,awayteamid,36,2,0,6,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,36,5,0,6,"2","E",8,0,0,"");
        db.insertRecord(gameid,awayteamid,11,3,0,7,"無","D",7,1,0,"");
        db.insertRecord(gameid,awayteamid,11,5,0,7,"1","B",4,0,1,"");
        db.insertRecord(gameid,awayteamid,1,3,0,8,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,1,5,0,8,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,8,3,0,9,"無","D",10,1,0,"");
        db.insertRecord(gameid,awayteamid,8,5,0,9,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,14,4,0,10,"無","保送",0,0,0,"");
        db.insertRecord(gameid,awayteamid,14,5,0,10,"無","D",6,1,0,"");

        db.updatescore(gameid,1,2);
    }

    public void InputG1Data(String gameid,String awayteamid,String hometeamid) {

        db.insertRecord(gameid,awayteamid,7,1,0,1,"無","保送",0,0,0,"");
        db.insertRecord(gameid,awayteamid,7,2,0,1,"2","B",6,0,2,"");
        db.insertRecord(gameid,awayteamid,7,3,0,1,"1","B",6,0,2,"");
        db.insertRecord(gameid,awayteamid,7,6,0,1,"全壘打","B",0,0,1,"");
        db.insertRecord(gameid,awayteamid,15,1,0,2,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,15,2,0,2,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,15,3,0,2,"1","B",6,0,0,"");
        db.insertRecord(gameid,awayteamid,15,6,0,2,"無","D",6,1,0,"");
        db.insertRecord(gameid,awayteamid,29,1,0,3,"1","B",6,0,0,"");
        db.insertRecord(gameid,awayteamid,29,2,0,3,"1","E",6,0,0,"");
        db.insertRecord(gameid,awayteamid,29,3,0,3,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,29,6,0,3,"1","B",6,0,0,"");
        db.insertRecord(gameid,awayteamid,6,1,0,4,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,6,2,0,4,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,6,4,0,4,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,6,6,0,4,"全壘打","B",0,0,2,"");
        db.insertRecord(gameid,awayteamid,4,1,0,5,"1","B",4,0,0,"");
        db.insertRecord(gameid,awayteamid,4,3,0,5,"2","B",4,0,0,"");
        db.insertRecord(gameid,awayteamid,4,4,0,5,"無","D",4,1,0,"");
        db.insertRecord(gameid,awayteamid,4,6,0,5,"無","D",4,1,0,"");
        db.insertRecord(gameid,awayteamid,47,1,0,6,"無","D",4,1,0,"");
        db.insertRecord(gameid,awayteamid,47,3,0,6,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,47,4,0,6,"無","D",4,1,0,"");
        db.insertRecord(gameid,awayteamid,47,6,0,6,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,51,2,0,7,"1","E",7,0,0,"");
        db.insertRecord(gameid,awayteamid,51,3,0,7,"1","B",7,0,0,"");
        db.insertRecord(gameid,awayteamid,51,5,0,7,"無","D",7,1,0,"");
        db.insertRecord(gameid,awayteamid,60,2,0,8,"1","B",3,0,0,"");
        db.insertRecord(gameid,awayteamid,60,3,0,8,"無","保送",0,0,0,"");
        db.insertRecord(gameid,awayteamid,60,5,0,8,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,3,2,0,9,"無","保送",0,0,0,"");
        db.insertRecord(gameid,awayteamid,3,3,0,9,"無","保送",0,0,1,"");
        db.insertRecord(gameid,awayteamid,3,5,0,9,"1","B",3,0,0,"");
        db.insertRecord(gameid,awayteamid,31,2,0,10,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,31,3,0,10,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,31,5,0,10,"無","K",0,1,0,"");

        db.insertRecord(gameid,hometeamid,4,1,0,1,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,4,2,0,1,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,4,4,0,1,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,4,6,0,1,"2","B",9,0,0,"");
        db.insertRecord(gameid,hometeamid,10,1,0,2,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,10,2,0,2,"1","B",3,0,1,"");
        db.insertRecord(gameid,hometeamid,10,4,0,2,"1","B",3,0,0,"");
        db.insertRecord(gameid,hometeamid,10,6,0,2,"1","B",10,0,0,"");
        db.insertRecord(gameid,hometeamid,31,1,0,3,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,31,2,0,3,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,31,4,0,3,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,31,6,0,3,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,16,1,0,4,"1","E",4,0,0,"");
        db.insertRecord(gameid,hometeamid,16,2,0,4,"2","B",8,0,3,"");
        db.insertRecord(gameid,hometeamid,16,4,0,4,"1","B",8,0,2,"");
        db.insertRecord(gameid,hometeamid,16,6,0,4,"1","B",6,0,1,"");
        db.insertRecord(gameid,hometeamid,5,1,0,5,"1","B",8,0,2,"");
        db.insertRecord(gameid,hometeamid,5,2,0,5,"無","D",8,1,0,"");
        db.insertRecord(gameid,hometeamid,5,4,0,5,"無","D",8,1,0,"");
        db.insertRecord(gameid,hometeamid,12,1,0,6,"無","D",5,1,0,"");
        db.insertRecord(gameid,hometeamid,12,3,0,6,"無","D",6,1,0,"");
        db.insertRecord(gameid,hometeamid,12,5,0,6,"無","D",10,1,0,"");
        db.insertRecord(gameid,hometeamid,11,1,0,7,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,11,3,0,7,"無","D",7,1,0,"");
        db.insertRecord(gameid,hometeamid,11,5,0,7,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,23,2,0,8,"2","E",8,0,0,"");
        db.insertRecord(gameid,hometeamid,23,3,0,8,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,23,5,0,8,"無","D",7,1,0,"");
        db.insertRecord(gameid,hometeamid,49,2,0,9,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,49,4,0,9,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,49,5,0,9,"無","D",6,1,0,"");
        db.insertRecord(gameid,hometeamid,14,2,0,10,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,14,4,0,10,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,14,6,0,10,"無","D",7,1,0,"");

        db.updatescore(gameid,8,9);
    }

    public void InputG3Data(String gameid,String awayteamid,String hometeamid) {

        db.insertRecord(gameid,awayteamid,7,1,0,1,"全壘打","B",0,0,1,"");
        db.insertRecord(gameid,awayteamid,7,3,0,1,"無","D",6,1,0,"");
        db.insertRecord(gameid,awayteamid,7,5,0,1,"1","B",6,0,0,"");
        db.insertRecord(gameid,awayteamid,15,1,0,2,"無","D",5,1,0,"");
        db.insertRecord(gameid,awayteamid,15,3,0,2,"1","B",5,0,0,"");
        db.insertRecord(gameid,awayteamid,15,5,0,2,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,52,1,0,3,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,52,3,0,3,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,52,5,0,3,"無","D",6,1,0,"");
        db.insertRecord(gameid,awayteamid,6,1,0,4,"全壘打","B",0,0,1,"");
        db.insertRecord(gameid,awayteamid,6,3,0,4,"無","D",8,1,0,"");
        db.insertRecord(gameid,awayteamid,6,5,0,4,"全壘打","B",0,0,1,"");
        db.insertRecord(gameid,awayteamid,2,1,0,5,"無","D",4,1,0,"");
        db.insertRecord(gameid,awayteamid,2,3,0,5,"無","保送",0,0,0,"");
        db.insertRecord(gameid,awayteamid,2,5,0,5,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,47,2,0,6,"1","E",4,0,0,"");
        db.insertRecord(gameid,awayteamid,47,4,0,6,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,47,6,0,6,"無","保送",0,0,0,"");
        db.insertRecord(gameid,awayteamid,51,2,0,7,"無","保送",0,0,0,"");
        db.insertRecord(gameid,awayteamid,51,4,0,7,"無","D",6,2,0,"");
        db.insertRecord(gameid,awayteamid,51,6,0,7,"無","保送",0,0,0,"");
        db.insertRecord(gameid,awayteamid,60,2,0,8,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,60,5,0,8,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,60,6,0,8,"1","E",3,0,0,"");
        db.insertRecord(gameid,awayteamid,3,2,0,9,"無","保送",0,0,0,"");
        db.insertRecord(gameid,awayteamid,3,5,0,9,"無","保送",0,0,0,"");
        db.insertRecord(gameid,awayteamid,3,6,0,9,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,23,2,0,10,"無","D",6,2,0,"");
        db.insertRecord(gameid,awayteamid,23,5,0,10,"1","B",7,0,0,"");
        db.insertRecord(gameid,awayteamid,23,6,0,10,"無","K",0,1,0,"");

        db.insertRecord(gameid,hometeamid,25,1,0,1,"無","D",4,1,0,"");
        db.insertRecord(gameid,hometeamid,25,3,0,1,"全壘打","D",0,0,4,"");
        db.insertRecord(gameid,hometeamid,25,6,0,1,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,3,1,0,2,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,3,3,0,2,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,3,6,0,2,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,16,1,0,3,"無","D",8,1,0,"");
        db.insertRecord(gameid,hometeamid,16,3,0,3,"無","D",8,1,0,"");
        db.insertRecord(gameid,hometeamid,16,6,0,3,"無","D",8,1,0,"");
        db.insertRecord(gameid,hometeamid,14,2,0,4,"無","D",6,1,0,"");
        db.insertRecord(gameid,hometeamid,14,3,0,4,"無","D",6,1,0,"");
        db.insertRecord(gameid,hometeamid,14,6,0,4,"1","E",6,0,0,"");
        db.insertRecord(gameid,hometeamid,5,2,0,5,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,5,4,0,5,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,5,6,0,5,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,1,2,0,6,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,1,4,0,6,"無","D",4,1,0,"");
        db.insertRecord(gameid,hometeamid,11,2,0,7,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,11,4,0,7,"無","D",5,1,0,"");
        db.insertRecord(gameid,hometeamid,49,3,0,8,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,49,5,0,8,"無","D",3,1,0,"");
        db.insertRecord(gameid,hometeamid,10,3,0,9,"1","E",3,0,0,"");
        db.insertRecord(gameid,hometeamid,10,5,0,9,"無","D",7,0,0,"");
        db.insertRecord(gameid,hometeamid,12,3,0,10,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,12,5,0,10,"無","D",10,1,0,"");

        db.updatescore(gameid,3,4);
    }

    public void InputG4Data(String gameid,String awayteamid,String hometeamid) {

        db.insertRecord(gameid,hometeamid,7,1,0,1,"無","D",6,1,0,"");
        db.insertRecord(gameid,hometeamid,7,3,0,1,"無","D",6,1,0,"");
        db.insertRecord(gameid,hometeamid,7,4,0,1,"2","B",9,0,3,"");
        db.insertRecord(gameid,hometeamid,8,1,0,2,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,8,4,0,2,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,8,5,0,2,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,52,1,0,3,"無","D",6,2,0,"");
        db.insertRecord(gameid,hometeamid,52,4,0,3,"1","E",6,0,0,"");
        db.insertRecord(gameid,hometeamid,52,5,0,3,"無","D",6,1,0,"");
        db.insertRecord(gameid,hometeamid,6,2,0,4,"無","D",8,1,0,"");
        db.insertRecord(gameid,hometeamid,6,4,0,4,"全壘打","B",0,0,2,"");
        db.insertRecord(gameid,hometeamid,6,5,0,4,"無","D",8,1,0,"");
        db.insertRecord(gameid,hometeamid,47,2,0,5,"無","D",4,1,0,"");
        db.insertRecord(gameid,hometeamid,47,4,0,5,"2","B",9,0,0,"");
        db.insertRecord(gameid,hometeamid,47,6,0,5,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,29,2,0,6,"1","E",6,0,0,"");
        db.insertRecord(gameid,hometeamid,29,4,0,6,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,29,6,0,6,"無","D",6,1,0,"");
        db.insertRecord(gameid,hometeamid,4,2,0,7,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,4,4,0,7,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,4,6,0,7,"1","E",4,0,0,"");
        db.insertRecord(gameid,hometeamid,31,3,0,8,"1","E",3,0,0,"");
        db.insertRecord(gameid,hometeamid,31,4,0,8,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,31,6,0,8,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,3,3,0,9,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,3,4,0,9,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,3,6,0,9,"全壘打","B",0,0,3,"");
        db.insertRecord(gameid,hometeamid,49,3,0,10,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,49,4,0,10,"無","保送",0,0,1,"");

        db.insertRecord(gameid,awayteamid,25,1,0,1 ,"無","D",4,1,0,"");
        db.insertRecord(gameid,awayteamid,25,3,0,1 ,"1","E",4,0,0,"");
        db.insertRecord(gameid,awayteamid,25,4,0,1 ,"2","B",8,0,1,"");
        db.insertRecord(gameid,awayteamid,36,1,0,2 ,"無","D",8,1,0,"");
        db.insertRecord(gameid,awayteamid,36,3,0,2 ,"1","E",4,0,0,"");
        db.insertRecord(gameid,awayteamid,36,4,0,2 ,"無","D",10,1,0,"");
        db.insertRecord(gameid,awayteamid,16,1,0,3 ,"1","E",8,0,0,"");
        db.insertRecord(gameid,awayteamid,16,3,0,3 ,"2","B",7,0,2,"");
        db.insertRecord(gameid,awayteamid,16,4,0,3 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,8,1,0,4 ,"無","D",10,1,0,"");
        db.insertRecord(gameid,awayteamid,8,3,0,4 ,"1","B",10,0,1,"");
        db.insertRecord(gameid,awayteamid,8,5,0,4 ,"無","D",10,1,0,"");
        db.insertRecord(gameid,awayteamid,14,2,0,5 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,14,3,0,5 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,14,5,0,5 ,"無","保送",0,0,0,"");
        db.insertRecord(gameid,awayteamid,4,2,0,6 ,"全壘打","無",0,0,1,"");
        db.insertRecord(gameid,awayteamid,4,3,0,6 ,"無","D",8,1,0,"");
        db.insertRecord(gameid,awayteamid,4,5,0,6 ,"無","D",8,1,0,"");
        db.insertRecord(gameid,awayteamid,5,2,0,7 ,"無","D",8,1,0,"");
        db.insertRecord(gameid,awayteamid,5,3,0,7 ,"無","D",8,1,0,"");
        db.insertRecord(gameid,awayteamid,5,5,0,7 ,"2","E",8,0,0,"");
        db.insertRecord(gameid,awayteamid,31,2,0,8 ,"1","B",6,0,0,"");
        db.insertRecord(gameid,awayteamid,31,4,0,8 ,"無","D",6,1,0,"");
        db.insertRecord(gameid,awayteamid,31,5,0,8 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,23,2,0,9 ,"1","B",6,0,0,"");
        db.insertRecord(gameid,awayteamid,23,4,0,9 ,"1","E",6,0,0,"");
        db.insertRecord(gameid,awayteamid,12,2,0,10 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,12,4,0,10 ,"無","保送",0,0,0,"");

        db.updatescore(gameid,5,8);
    }

    public void InputG5Data(String gameid,String awayteamid,String hometeamid) {

        db.insertRecord(gameid,awayteamid,7,1,0,1 ,"無","D",6,1,0,"");
        db.insertRecord(gameid,awayteamid,7,3,0,1 ,"全壘打","B",0,0,1,"");
        db.insertRecord(gameid,awayteamid,7,5,0,1 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,15,1,0,2 ,"1","E",6,0,0,"");
        db.insertRecord(gameid,awayteamid,15,3,0,2 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,15,5,0,2 ,"無","保送",0,0,0,"");
        db.insertRecord(gameid,awayteamid,29,1,0,3 ,"1","B",10,0,0,"");
        db.insertRecord(gameid,awayteamid,29,3,0,3 ,"無","D",10,1,0,"");
        db.insertRecord(gameid,awayteamid,29,5,0,3 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,6,1,0,4 ,"無","D",8,1,0,"");
        db.insertRecord(gameid,awayteamid,6,3,0,4 ,"1","E",8,0,0,"");
        db.insertRecord(gameid,awayteamid,6,6,0,4 ,"全壘打","B",0,0,1,"");
        db.insertRecord(gameid,awayteamid,4,1,0,5 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,4,3,0,5 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,4,6,0,5 ,"無","D",3,1,0,"");
        db.insertRecord(gameid,awayteamid,47,2,0,6 ,"無","D",4,1,0,"");
        db.insertRecord(gameid,awayteamid,47,4,0,6 ,"無","D",4,1,0,"");
        db.insertRecord(gameid,awayteamid,47,6,0,6 ,"無","D",4,1,0,"");
        db.insertRecord(gameid,awayteamid,51,2,0,7 ,"1","B",7,0,0,"");
        db.insertRecord(gameid,awayteamid,51,4,0,7 ,"無","D",7,1,0,"");
        db.insertRecord(gameid,awayteamid,51,6,0,7 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,60,2,0,8 ,"無","保送",0,0,0,"");
        db.insertRecord(gameid,awayteamid,60,4,0,8 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,3,2,0,9 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,3,5,0,9 ,"1","B",3,0,0,"");
        db.insertRecord(gameid,awayteamid,31,2,0,10 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,awayteamid,31,5,0,10 ,"無","K",0,1,0,"");

        db.insertRecord(gameid,hometeamid,4,1,0,1 ,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,4,3,0,1 ,"1","B",8,0,0,"");
        db.insertRecord(gameid,hometeamid,4,5,0,1 ,"無","D",8,1,0,"");
        db.insertRecord(gameid,hometeamid,10,1,0,2 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,10,3,0,2 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,10,5,0,2 ,"無","D",3,1,0,"");
        db.insertRecord(gameid,hometeamid,31,1,0,3 ,"1","B",6,0,0,"");
        db.insertRecord(gameid,hometeamid,31,3,0,3 ,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,31,6,0,3 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,16,1,0,4 ,"無","D",8,1,0,"");
        db.insertRecord(gameid,hometeamid,16,3,0,4 ,"1","B",8,0,0,"");
        db.insertRecord(gameid,hometeamid,16,6,0,4 ,"全壘打","B",0,0,1,"");
        db.insertRecord(gameid,hometeamid,5,1,0,5 ,"無","D",8,1,0,"");
        db.insertRecord(gameid,hometeamid,5,3,0,5 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,5,6,0,5 ,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,12,2,0,6 ,"1","B",5,0,0,"");
        db.insertRecord(gameid,hometeamid,12,3,0,6 ,"無","D",5,1,0,"");
        db.insertRecord(gameid,hometeamid,12,6,0,6 ,"無","D",5,1,0,"");
        db.insertRecord(gameid,hometeamid,11,2,0,7 ,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,11,4,0,7 ,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,11,6,0,7 ,"1","B",5,0,0,"");
        db.insertRecord(gameid,hometeamid,23,2,0,8 ,"無","D",8,1,0,"");
        db.insertRecord(gameid,hometeamid,23,4,0,8 ,"無","D",8,1,0,"");
        db.insertRecord(gameid,hometeamid,23,6,0,8 ,"無","保送",0,0,0,"");
        db.insertRecord(gameid,hometeamid,49,2,0,9 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,49,4,0,9 ,"無","D",3,1,0,"");
        db.insertRecord(gameid,hometeamid,49,6,0,9 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,14,2,0,10 ,"無","K",0,1,0,"");
        db.insertRecord(gameid,hometeamid,14,4,0,10 ,"無","K",0,1,0,"");

        db.updatescore(gameid,2,1);
    }
}
