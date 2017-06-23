package kaixshaun.baseballsupport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Key_The_Away_Team_List extends AppCompatActivity {

    public static final String GameID = "GameID";
    public static final String AwayTeamID = "AwayTeamID";
    BaseballDB db;
    private String gameid ;
    private String awayteamid ;

    //先功方守位變數
    private Spinner a_t_d_l_1,a_t_d_l_2,a_t_d_l_3,a_t_d_l_4,a_t_d_l_5,a_t_d_l_6;
    private Spinner a_t_d_l_7,a_t_d_l_8,a_t_d_l_9,a_t_d_l_10,a_t_d_l_11;

    private EditText a_t_b_n_1,a_t_b_n_2,a_t_b_n_3,a_t_b_n_4,a_t_b_n_5,a_t_b_n_6;
    private EditText a_t_b_n_7,a_t_b_n_8,a_t_b_n_9,a_t_b_n_10,a_t_b_n_11;
    private EditText a_t_name;

    private Button key_a_t_bench_l,key_t_h_t_l,store_t_a_t_l,cancel_t_a_t_l,start_t_g_at;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_the_away_team_list);

        gameid = this.setgameid();
        db = new BaseballDB(this);
        Log.v("GameID",gameid);

        a_t_name = (EditText)findViewById(R.id.away_team_name);

        declare();

        //跳去後攻隊員名單的按鈕功能
        key_t_h_t_l = (Button)findViewById(R.id.key_the_home_team_list_btn);
        key_t_h_t_l.setOnClickListener(goto_key_the_home_team_list);

        //跳去輸入後攻候補名單功能
        key_a_t_bench_l = (Button)findViewById(R.id.key_the_away_team_bench_list_btn);
        key_a_t_bench_l.setOnClickListener(goto_key_the_away_team_bench_list);

        //儲存名單按鈕
        store_t_a_t_l = (Button)findViewById(R.id.store_the_away_team_list_btn);
        store_t_a_t_l.setOnClickListener(store_the_away_team_list);

        //開始遊比賽按鈕
        start_t_g_at = (Button)findViewById(R.id.start_the_game_at_btn);
        start_t_g_at.setOnClickListener(testaaaaa);

        //取消按鈕
        cancel_t_a_t_l = (Button)findViewById(R.id.cancel_the_away_team_list_btn);
        cancel_t_a_t_l.setOnClickListener(cancel_the_away_team_list);
    }

    private View.OnClickListener testaaaaa = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            intent.putExtra(GameID, gameid);
            intent.putExtra(AwayTeamID, awayteamid);
            intent.setClass(Key_The_Away_Team_List.this,TestDB.class);
            startActivity(intent);
        }
    };


    private View.OnClickListener store_the_away_team_list = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if( awayteamid != null){

                if(db.deleteStartingOrder(gameid,awayteamid))
                    Log.v("delete","successful");
                else
                    Log.v("delete","fail");

            }

            String awayteamname = a_t_name.getText().toString();
            awayteamid = db.insertAwayteamname(gameid,awayteamname);

            settingorder();
        }
    };

    private View.OnClickListener cancel_the_away_team_list = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            if( awayteamid != null){

                if(db.deleteStartingOrder(gameid,awayteamid))
                    Log.v("delete","successful");
                else
                    Log.v("delete","fail");
            }
            intent.setClass(Key_The_Away_Team_List.this,StartPage.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener goto_key_the_home_team_list = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            intent.putExtra(GameID, gameid);
            intent.setClass(Key_The_Away_Team_List.this,Key_The_Home_Team_List.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener goto_key_the_away_team_bench_list = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            intent.putExtra(GameID, gameid);
            intent.putExtra(AwayTeamID, awayteamid);
            intent.setClass(Key_The_Away_Team_List.this,Away_Team_Bench_List.class);
            startActivity(intent);
        }
    };

    //整理宣告
    private void declare(){

        a_t_b_n_1 = (EditText)findViewById(R.id.away_team_back_number_1);
        a_t_b_n_2 = (EditText)findViewById(R.id.away_team_back_number_2);
        a_t_b_n_3 = (EditText)findViewById(R.id.away_team_back_number_3);
        a_t_b_n_4 = (EditText)findViewById(R.id.away_team_back_number_4);
        a_t_b_n_5 = (EditText)findViewById(R.id.away_team_back_number_5);
        a_t_b_n_6 = (EditText)findViewById(R.id.away_team_back_number_6);
        a_t_b_n_7 = (EditText)findViewById(R.id.away_team_back_number_7);
        a_t_b_n_8 = (EditText)findViewById(R.id.away_team_back_number_8);
        a_t_b_n_9 = (EditText)findViewById(R.id.away_team_back_number_9);
        a_t_b_n_10 = (EditText)findViewById(R.id.away_team_back_number_10);
        a_t_b_n_11 = (EditText)findViewById(R.id.away_team_back_number_11);


        final String[] defense = {"無","投手","捕手","一壘手","二壘手","三壘手","游擊手","左外野手","中外野手","右外野手","自由手","指名打擊"};
        a_t_d_l_1 = (Spinner)findViewById(R.id.away_team_defense_location_1);
        a_t_d_l_2 = (Spinner)findViewById(R.id.away_team_defense_location_2);
        a_t_d_l_3 = (Spinner)findViewById(R.id.away_team_defense_location_3);
        a_t_d_l_4 = (Spinner)findViewById(R.id.away_team_defense_location_4);
        a_t_d_l_5 = (Spinner)findViewById(R.id.away_team_defense_location_5);
        a_t_d_l_6 = (Spinner)findViewById(R.id.away_team_defense_location_6);
        a_t_d_l_7 = (Spinner)findViewById(R.id.away_team_defense_location_7);
        a_t_d_l_8 = (Spinner)findViewById(R.id.away_team_defense_location_8);
        a_t_d_l_9 = (Spinner)findViewById(R.id.away_team_defense_location_9);
        a_t_d_l_10 = (Spinner)findViewById(R.id.away_team_defense_location_10);
        a_t_d_l_11 = (Spinner)findViewById(R.id.away_team_defense_location_11);
        ArrayAdapter<String> defenselist = new ArrayAdapter<String>(Key_The_Away_Team_List.this,android.R.layout.simple_spinner_dropdown_item,defense);
        a_t_d_l_1.setAdapter(defenselist);
        a_t_d_l_2.setAdapter(defenselist);
        a_t_d_l_3.setAdapter(defenselist);
        a_t_d_l_4.setAdapter(defenselist);
        a_t_d_l_5.setAdapter(defenselist);
        a_t_d_l_6.setAdapter(defenselist);
        a_t_d_l_7.setAdapter(defenselist);
        a_t_d_l_8.setAdapter(defenselist);
        a_t_d_l_9.setAdapter(defenselist);
        a_t_d_l_10.setAdapter(defenselist);
        a_t_d_l_11.setAdapter(defenselist);
    }

    //將守位轉換成數字
    public int turnrule(String rule){

        switch (rule){

            case "投手":return 1;
            case "捕手":return 2;
            case "一壘手":return 3;
            case "二壘手":return 4;
            case "三壘手":return 5;
            case "游擊手":return 6;
            case "左外野手":return 7;
            case "中外野手":return 8;
            case "右外野手":return 9;
            case "自由手":return 10;
            case "指名打擊":return 11;
            default:return 0;
        }
    }

    //得到比賽ID
    private String setgameid(){

        Intent intent = getIntent();
        String temp = intent.getStringExtra(NewGameName.GameID);
        return temp;
    }

    //轉換背號
    private int turnback(EditText back){

        return Integer.parseInt(back.getText().toString());
    }

    //得到守備位置
    private int getrule(Spinner rule){

        return turnrule(rule.getSelectedItem().toString());
    }

    //將先發名單與球員名單儲入資料庫
    private void settingorder(){

        db.insertBattingorder(gameid,awayteamid,turnback(a_t_b_n_1),1,getrule(a_t_d_l_1));
        db.insertTeammate(gameid,awayteamid,turnback(a_t_b_n_1),"S");
        db.insertBattingorder(gameid,awayteamid,turnback(a_t_b_n_2), 2,getrule(a_t_d_l_2));
        db.insertTeammate(gameid,awayteamid,turnback(a_t_b_n_2),"S");
        db.insertBattingorder(gameid,awayteamid,turnback(a_t_b_n_3), 3,getrule(a_t_d_l_3));
        db.insertTeammate(gameid,awayteamid,turnback(a_t_b_n_3),"S");
        db.insertBattingorder(gameid,awayteamid,turnback(a_t_b_n_4), 4,getrule(a_t_d_l_4));
        db.insertTeammate(gameid,awayteamid,turnback(a_t_b_n_4),"S");
        db.insertBattingorder(gameid,awayteamid,turnback(a_t_b_n_5), 5,getrule(a_t_d_l_5));
        db.insertTeammate(gameid,awayteamid,turnback(a_t_b_n_5),"S");
        db.insertBattingorder(gameid,awayteamid,turnback(a_t_b_n_6), 6,getrule(a_t_d_l_6));
        db.insertTeammate(gameid,awayteamid,turnback(a_t_b_n_6),"S");
        db.insertBattingorder(gameid,awayteamid,turnback(a_t_b_n_7), 7,getrule(a_t_d_l_7));
        db.insertTeammate(gameid,awayteamid,turnback(a_t_b_n_7),"S");
        db.insertBattingorder(gameid,awayteamid,turnback(a_t_b_n_8), 8,getrule(a_t_d_l_8));
        db.insertTeammate(gameid,awayteamid,turnback(a_t_b_n_8),"S");
        db.insertBattingorder(gameid,awayteamid,turnback(a_t_b_n_9), 9,getrule(a_t_d_l_9));
        db.insertTeammate(gameid,awayteamid,turnback(a_t_b_n_9),"S");

        db.insertBattingorder(gameid,awayteamid,turnback(a_t_b_n_10), 10,getrule(a_t_d_l_10));
        db.insertTeammate(gameid,awayteamid,turnback(a_t_b_n_10),"S");
        if(getrule(a_t_d_l_11) != 0) {
            db.insertBattingorder(gameid, awayteamid, turnback(a_t_b_n_11), 11, getrule(a_t_d_l_11));
            db.insertTeammate(gameid,awayteamid,turnback(a_t_b_n_11),"S");
        }

    }
}
