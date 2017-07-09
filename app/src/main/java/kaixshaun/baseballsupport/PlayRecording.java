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

    TextView InningView, OutView, ScoreView, BackView, RuleView, No_View;
    Spinner FlytoView, BaseView, B_EView, SituationView, DiedwayView, KillView, GetScoreView;
    Button NextBtn, StoreBtn, FinishBtn;

    int inning, out, awayscore = 0, homescore = 0, back, rule, awayteamno = 0, hometeamno = 0, order, flyto, rbi, gameout = 0, inninghalf = 0;
    Cursor awayteamorder, hometeamorder;
    String showInning, gameid, awayteamid, hometeamid, teamid, situation;
    int[] awayteamback = new int[10], awayteamrule = new int[10], hometeamback = new int[10], hometeamrule = new int[10];
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

        inning = inninghalf / 2 + 1;
        teamid = awayteamid;
        showInning = inning + "上";
        InningView.setText(showInning);
        OutView.setText(gameout+"");
        ScoreView.setText(awayscore + " - " + homescore);
        BackView.setText(awayteamback[awayteamno]+"");
        RuleView.setText(turnrule(awayteamrule[awayteamno]));
        No_View.setText((awayteamno + 1)+"" );

        back = awayteamback[awayteamno];
        rule = awayteamrule[awayteamno];
        order = awayteamno;


    }

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

    private int getspinnerint(Spinner temp) {

        return Integer.parseInt(temp.getSelectedItem().toString());
    }

    private void setid() {

        Intent intent = getIntent();
        gameid = intent.getStringExtra(Home_Team_Bench_List.GameID);
        awayteamid = intent.getStringExtra(Home_Team_Bench_List.AwayTeamID);
        hometeamid = intent.getStringExtra(Home_Team_Bench_List.HomeTeamID);
    }

    private void setorder() {

        awayteamorder = db.selestorder(gameid, awayteamid);
        hometeamorder = db.selestorder(gameid, awayteamid);
        awayteamorder.moveToFirst();
        names = awayteamorder.getColumnNames();
        for (int i = 0; i < awayteamorder.getCount(); i++) {

            awayteamback[i] = awayteamorder.getInt(awayteamorder.getColumnIndex(names[3]));
            awayteamrule[i] = awayteamorder.getInt(awayteamorder.getColumnIndex(names[5]));
            awayteamorder.moveToNext();
        }

        hometeamorder.moveToFirst();
        names = hometeamorder.getColumnNames();
        for (int i = 0; i < hometeamorder.getCount(); i++) {

            hometeamback[i] = hometeamorder.getInt(hometeamorder.getColumnIndex(names[3]));
            hometeamrule[i] = hometeamorder.getInt(hometeamorder.getColumnIndex(names[5]));
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

        final String[] getscore = {"0", "1", "2", "3", "4"};
        GetScoreView = (Spinner) findViewById(R.id.GetScore);
        ArrayAdapter<String> getscorelist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, getscore);
        GetScoreView.setAdapter(getscorelist);

        final String[] flyto = {"無", "投手", "捕手", "一壘手", "二壘手", "三壘手", "游擊手", "左外野手", "中外野手", "右外野手", "自由手"};
        FlytoView = (Spinner) findViewById(R.id.Flyto);
        ArrayAdapter<String> flytolist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, flyto);
        FlytoView.setAdapter(flytolist);

        final String[] onbase = {"無", "1", "2", "3", "全壘打"};
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

        final String[] diedway = {"無", "刺殺", "阻殺", "界K", "揮K", "看K"};
        DiedwayView = (Spinner) findViewById(R.id.Diedway);
        ArrayAdapter<String> diedwaylist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, diedway);
        DiedwayView.setAdapter(diedwaylist);

        final String[] kill = {"0", "1", "2", "3"};
        KillView = (Spinner) findViewById(R.id.Kill);
        ArrayAdapter<String> killlist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, kill);
        KillView.setAdapter(killlist);

        NextBtn = (Button) findViewById(R.id.Next);
        NextBtn.setOnClickListener(nextmember);

        StoreBtn = (Button) findViewById(R.id.Store);
        StoreBtn.setOnClickListener(storerecording);

        FinishBtn = (Button) findViewById(R.id.Finish);
        FinishBtn.setOnClickListener(finishgame);
    }

    private View.OnClickListener nextmember = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (inninghalf % 2 == 0) {

                awayteamno++;
                if (awayteamno >= 10) {
                    awayteamno = 0;
                }
                teamid = awayteamid;
                inning = inninghalf/2+1;
                showInning = inning + " 上";
                InningView.setText(showInning);
                OutView.setText(gameout+"");
                ScoreView.setText(awayscore + " - " + homescore);
                BackView.setText(awayteamback[awayteamno]+"");
                RuleView.setText(turnrule(awayteamrule[awayteamno]));
                No_View.setText((awayteamno + 1)+"");

                back = awayteamback[awayteamno];
                rule = awayteamrule[awayteamno];
                order = awayteamno + 1;

            } else {

                if (hometeamno >= 10) {
                    hometeamno = 0;
                }
                teamid = hometeamid;
                inning = inninghalf/2+1;
                showInning = inning + " 下";
                InningView.setText(showInning);
                OutView.setText(gameout+"");
                ScoreView.setText(awayscore + " - " + homescore);
                BackView.setText(hometeamback[hometeamno]+"");
                RuleView.setText(turnrule(hometeamrule[hometeamno]));
                No_View.setText((hometeamno + 1)+"");

                back = hometeamback[hometeamno];
                rule = hometeamrule[hometeamno];
                order = hometeamno + 1;
                hometeamno++;
            }

        }

    };

    private View.OnClickListener storerecording = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getspinnerstring(DiedwayView) != "無") {

                    situation = "D";
                    out = getspinnerint(KillView);
                    flyto = turnflyto(getspinnerstring(FlytoView));
                    rbi = getspinnerint(GetScoreView);

                } else {

                    situation = getspinnerstring(BaseView) + getspinnerstring(B_EView);
                    out = getspinnerint(KillView);
                    if (getspinnerstring(BaseView) != "全壘打")
                        flyto = turnflyto(getspinnerstring(FlytoView));
                    else
                        flyto = 0;
                    rbi = getspinnerint(GetScoreView);
                }
                db.insertRecord(gameid, teamid, back, inning, 0, order, situation, flyto, out, rbi, "");

                gameout = gameout + getspinnerint(KillView);

                if (gameout >= 3) {

                    inninghalf++;
                    gameout = 0;
                }

            }
        };

        private View.OnClickListener finishgame = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        };
    }
