package kaixshaun.baseballsupport;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PlayRecording extends AppCompatActivity {

    TextView InningView,OutView,ScoreView,BackView,RuleView,No_View;
    Spinner FlytoView,BaseView,B_EView,SituationView,DiedwayView,KillView,GetScoreView;
    Button NextBtn,StoreBtn,FinishBtn;

    int inning,out = 0,awayscore = 0,homescore = 0,back,rule,awayteamno = 0,hometeamno = 0,order,flyto,rbi;
    Cursor awayteamorder,hometeamorder;
    String showInning,gameid,awayteamid,hometeamid,teamid,situation;
    int[] awayteamback,awayteamrule,hometeamback,hometeamrule;
    String[] names;
    BaseballDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_recording);

        declare();
        db = new BaseballDB(this);
        setid();
        setorder();

        teamid = awayteamid;
        showInning = inning + "上";
        InningView.setText(showInning);
        OutView.setText(out);
        ScoreView.setText(awayscore+" - "+homescore);
        BackView.setText(awayteamback[awayteamno]);
        RuleView.setText(awayteamrule[awayteamno]);
        No_View.setText(awayteamno);

        back = awayteamback[awayteamno];
        rule = awayteamrule[awayteamno];
        order = awayteamno;
        if (getspinnerstring(DiedwayView) != "無") {

            situation = "D";
            out = getspinnerint(KillView);
            flyto = turnflyto(getspinnerstring(FlytoView));
            rbi = getspinnerint(GetScoreView);
        }
        else {

            situation = getspinnerstring(BaseView)+getspinnerstring(B_EView);
            out = getspinnerint(KillView);
            if(getspinnerstring(BaseView)!="全壘打")
                flyto = turnflyto(getspinnerstring(FlytoView));
            else
                flyto = 0;


            rbi = getspinnerint(GetScoreView);
        }


    }

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

    private String getspinnerstring(Spinner temp) {

        return temp.getSelectedItem().toString();
    }

    private int getspinnerint(Spinner temp){

        return Integer.parseInt(temp.getSelectedItem().toString());
    }

    private void setid(){

        Intent intent = getIntent();
        gameid = intent.getStringExtra(Home_Team_Bench_List.GameID);
        awayteamid = intent.getStringExtra(Home_Team_Bench_List.AwayTeamID);
        hometeamid = intent.getStringExtra(Home_Team_Bench_List.HomeTeamID);
    }

    private void setorder(){

        awayteamorder = db.selestorder(gameid,awayteamid);
        hometeamorder = db.selestorder(gameid,awayteamid);
        awayteamorder.moveToFirst();
        names = awayteamorder.getColumnNames();
        for (int i = 0; i < awayteamorder.getCount(); i++){

            awayteamback[i] = Integer.parseInt(awayteamorder.getString(awayteamorder.getColumnIndex(names[3])));
            awayteamrule[i] = Integer.parseInt(awayteamorder.getString(awayteamorder.getColumnIndex(names[5])));
            awayteamorder.moveToNext();
        }

        hometeamorder.moveToFirst();
        names = hometeamorder.getColumnNames();
        for(int i = 0; i < hometeamorder.getCount(); i++){

            hometeamback[i] = Integer.parseInt(hometeamorder.getString(hometeamorder.getColumnIndex(names[3])));
            hometeamrule[i] = Integer.parseInt(hometeamorder.getString(hometeamorder.getColumnIndex(names[5])));
            hometeamorder.moveToNext();
        }
    }



    private void declare(){

        InningView = (TextView)findViewById(R.id.Inning);
        OutView = (TextView)findViewById(R.id.Out);
        ScoreView = (TextView)findViewById(R.id.Score);
        BackView = (TextView)findViewById(R.id.Back);
        RuleView = (TextView)findViewById(R.id.Rule);
        No_View = (TextView)findViewById(R.id.No_);

        final String[] getscore = {"0","1","2","3","4"};
        GetScoreView = (Spinner)findViewById(R.id.GetScore);
        ArrayAdapter<String> getscorelist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, getscore);
        GetScoreView.setAdapter(getscorelist);

        final String[] flyto = {"無","投手","捕手","一壘手","二壘手","三壘手","游擊手","左外野手","中外野手","右外野手","自由手"};
        FlytoView = (Spinner)findViewById(R.id.Flyto);
        ArrayAdapter<String> flytolist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, flyto);
        FlytoView.setAdapter(flytolist);

        final String[] onbase = {"無","1","2","3","全壘打"};
        BaseView = (Spinner)findViewById(R.id.Base);
        ArrayAdapter<String> onbaselist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, onbase);
        BaseView.setAdapter(onbaselist);

        final String[] b_e = {"B","E"};
        B_EView = (Spinner)findViewById(R.id.B_E);
        ArrayAdapter<String> b_elist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, b_e);
        B_EView.setAdapter(b_elist);

        final String[] situation = {"...","^"};
        SituationView = (Spinner)findViewById(R.id.Situation);
        ArrayAdapter<String> situationlist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, situation);
        SituationView.setAdapter(situationlist);

        final String[] diedway = {"無","刺殺","阻殺","界K","揮K","看K"};
        DiedwayView = (Spinner)findViewById(R.id.Diedway);
        ArrayAdapter<String> diedwaylist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, diedway);
        DiedwayView.setAdapter(diedwaylist);

        final String[] kill = {"0","1","2","3"};
        KillView = (Spinner)findViewById(R.id.Kill);
        ArrayAdapter<String> killlist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, kill);
        KillView.setAdapter(killlist);

        NextBtn = (Button)findViewById(R.id.Next);
        NextBtn.setOnClickListener(nextmember);

        StoreBtn = (Button)findViewById(R.id.Store);
        StoreBtn.setOnClickListener(storerecording);

        FinishBtn = (Button)findViewById(R.id.Finish);
        FinishBtn.setOnClickListener(finishgame);
    }
    private View.OnClickListener nextmember = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


        }
    };

    private View.OnClickListener storerecording = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            db.insertRecord(gameid,teamid,back,inning,0,order,);

        }
    };

    private View.OnClickListener finishgame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


        }
    };
}
