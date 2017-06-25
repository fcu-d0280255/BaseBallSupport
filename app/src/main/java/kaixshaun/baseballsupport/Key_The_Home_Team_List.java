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
import android.widget.Toast;

public class Key_The_Home_Team_List extends AppCompatActivity {

    public static final String HomeTeamID = "HomeTeamID";
    public static final String AwayteamID = "AwayTeamID";
    public static final String GameID = "GameID";
    BaseballDB db;
    private String gameid, awayteamid, hometeamid;

    //後攻方守位變數
    EditText h_t_b_n_1, h_t_b_n_2, h_t_b_n_3, h_t_b_n_4, h_t_b_n_5, h_t_b_n_6;
    EditText h_t_b_n_7, h_t_b_n_8, h_t_b_n_9, h_t_b_n_10, h_t_b_n_11, a_t_name;
    Spinner h_t_d_l_1, h_t_d_l_2, h_t_d_l_3, h_t_d_l_4, h_t_d_l_5, h_t_d_l_6;
    Spinner h_t_d_l_7, h_t_d_l_8, h_t_d_l_9, h_t_d_l_10, h_t_d_l_11;

    Button key_h_t_bench_l, store_t_h_t_l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key__the__home__team__list);

        gameid = Key_The_Home_Team_List.this.setgameid();
        db = new BaseballDB(this);
        Log.v("GameID", gameid);
        a_t_name = (EditText) findViewById(R.id.away_team_name);
        declare();

        //跳去輸入後攻候補名單功能
        key_h_t_bench_l = (Button) findViewById(R.id.key_the_home_team_bench_list_btn);
        key_h_t_bench_l.setOnClickListener(goto_key_the_home_team_bench_list);
    }


    private View.OnClickListener goto_key_the_home_team_bench_list = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            intent.putExtra(AwayteamID, awayteamid);
            intent.putExtra(HomeTeamID, hometeamid)
            intent.putExtra(GameID, gameid);
            intent.setClass(Key_The_Away_Team_List.this, Away_Team_Bench_List.class);
            if (judgment()) {

                Toast toast = Toast.makeText(Key_The_Away_Team_List.this, "請填好名單並儲存!!", Toast.LENGTH_LONG);
                toast.show();
            } else {

                startActivity(intent);
                Key_The_Away_Team_List.this.finish();
            }
        }
    };

    //將守位轉換成數字
    public int turnrule(String rule) {

        switch (rule) {

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
            case "指名打擊":
                return 11;
            default:
                return 0;
        }
    }

    //得到比賽ID
    private String setgameid() {

        Intent intent = getIntent();
        String temp = intent.getStringExtra(Away_Team_Bench_List.GameID);
        return temp;
    }

    private String setawayteamid() {

        Intent intent = getIntent();
        String temp = intent.getStringExtra(Key_The_Away_Team_List.AwayTeamID);
        return temp;
    }

    //轉換背號
    private int turnback(EditText back) {

        return Integer.parseInt(back.getText().toString());
    }

    //得到守備位置
    private int getrule(Spinner rule) {

        return turnrule(rule.getSelectedItem().toString());
    }

    //整理宣告
    private void declare() {

        h_t_b_n_1 = (EditText) findViewById(R.id.home_team_back_number_1);
        h_t_b_n_2 = (EditText) findViewById(R.id.home_team_back_number_2);
        h_t_b_n_3 = (EditText) findViewById(R.id.home_team_back_number_3);
        h_t_b_n_4 = (EditText) findViewById(R.id.home_team_back_number_4);
        h_t_b_n_5 = (EditText) findViewById(R.id.home_team_back_number_5);
        h_t_b_n_6 = (EditText) findViewById(R.id.home_team_back_number_6);
        h_t_b_n_7 = (EditText) findViewById(R.id.home_team_back_number_7);
        h_t_b_n_8 = (EditText) findViewById(R.id.home_team_back_number_8);
        h_t_b_n_9 = (EditText) findViewById(R.id.home_team_back_number_9);
        h_t_b_n_10 = (EditText) findViewById(R.id.home_team_back_number_10);
        h_t_b_n_11 = (EditText) findViewById(R.id.home_team_back_number_11);

        final String[] defense = {"捕手", "投手", "一壘手", "二壘手", "游擊手", "三壘手", "自由手", "右外野手", "中外野手", "左外野手", "指名打擊", "無"};
        h_t_d_l_1 = (Spinner) findViewById(R.id.home_team_defense_location_1);
        h_t_d_l_2 = (Spinner) findViewById(R.id.home_team_defense_location_2);
        h_t_d_l_3 = (Spinner) findViewById(R.id.home_team_defense_location_3);
        h_t_d_l_4 = (Spinner) findViewById(R.id.home_team_defense_location_4);
        h_t_d_l_5 = (Spinner) findViewById(R.id.home_team_defense_location_5);
        h_t_d_l_6 = (Spinner) findViewById(R.id.home_team_defense_location_6);
        h_t_d_l_7 = (Spinner) findViewById(R.id.home_team_defense_location_7);
        h_t_d_l_8 = (Spinner) findViewById(R.id.home_team_defense_location_8);
        h_t_d_l_9 = (Spinner) findViewById(R.id.home_team_defense_location_9);
        h_t_d_l_10 = (Spinner) findViewById(R.id.home_team_defense_location_10);
        h_t_d_l_11 = (Spinner) findViewById(R.id.home_team_defense_location_11);
        ArrayAdapter<String> defenselist = new ArrayAdapter<String>(Key_The_Home_Team_List.this, android.R.layout.simple_spinner_dropdown_item, defense);
        h_t_d_l_1.setAdapter(defenselist);
        h_t_d_l_2.setAdapter(defenselist);
        h_t_d_l_3.setAdapter(defenselist);
        h_t_d_l_4.setAdapter(defenselist);
        h_t_d_l_5.setAdapter(defenselist);
        h_t_d_l_6.setAdapter(defenselist);
        h_t_d_l_7.setAdapter(defenselist);
        h_t_d_l_8.setAdapter(defenselist);
        h_t_d_l_9.setAdapter(defenselist);
        h_t_d_l_10.setAdapter(defenselist);
        h_t_d_l_11.setAdapter(defenselist);
    }
}

