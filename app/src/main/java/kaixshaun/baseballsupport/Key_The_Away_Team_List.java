package kaixshaun.baseballsupport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Key_The_Away_Team_List extends AppCompatActivity {

    BaseballDB db = new BaseballDB();
    //先功方守位變數
    Spinner a_t_d_l_1,a_t_d_l_2,a_t_d_l_3,a_t_d_l_4,a_t_d_l_5,a_t_d_l_6;
    Spinner a_t_d_l_7,a_t_d_l_8,a_t_d_l_9,a_t_d_l_10,a_t_d_l_11;
    EditText a_t_b_n_1,a_t_b_n_2,a_t_b_n_3,a_t_b_n_4,a_t_b_n_5,a_t_b_n_6;
    EditText a_t_b_n_7,a_t_b_n_8,a_t_b_n_9,a_t_b_n_10,a_t_b_n_11;
    EditText a_t_name;

    Button key_a_t_bench_l,key_t_h_t_l,store_t_a_t_l,cancel_t_a_t_l,start_t_g_at;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_the_away_team_list);

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

        a_t_name = (EditText)findViewById(R.id.away_team_name);

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

        //跳去後攻隊員名單的按鈕功能
        key_t_h_t_l = (Button)findViewById(R.id.key_the_home_team_list_btn);
        key_t_h_t_l.setOnClickListener(goto_key_the_home_team_list);

        //跳去輸入後攻候補名單功能
        key_a_t_bench_l = (Button)findViewById(R.id.key_the_away_team_bench_list_btn);
        key_a_t_bench_l.setOnClickListener(goto_key_the_away_team_bench_list);

        store_t_a_t_l = (Button)findViewById(R.id.store_the_away_team_list_btn);
        store_t_a_t_l.setOnClickListener(store_the_away_team_list);

        start_t_g_at = (Button)findViewById(R.id.start_the_game_at_btn);
        start_t_g_at.setOnClickListener(testaaaaa);
    }

    private View.OnClickListener testaaaaa = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            intent.setClass(Key_The_Away_Team_List.this,TestDB.class);
            startActivity(intent);
        }
    };

    public String setgameid(){

        Intent intent = getIntent();
        String GameID = intent.getStringExtra("GameID");
        return GameID;
    }
    private View.OnClickListener store_the_away_team_list = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String awayteamname = a_t_name.getText().toString();
            String awayteamid = db.insertAwayteamname(setgameid(),awayteamname);
            db.insertBattingorder(setgameid(),awayteamid,Integer.getInteger(a_t_b_n_1.getText().toString()),1,a_t_d_l_1.getSelectedItemPosition());
            db.insertBattingorder(setgameid(),awayteamid,Integer.getInteger(a_t_b_n_2.getText().toString()),2,a_t_d_l_2.getSelectedItemPosition());
            db.insertBattingorder(setgameid(),awayteamid,Integer.getInteger(a_t_b_n_3.getText().toString()),3,a_t_d_l_3.getSelectedItemPosition());
            db.insertBattingorder(setgameid(),awayteamid,Integer.getInteger(a_t_b_n_4.getText().toString()),4,a_t_d_l_4.getSelectedItemPosition());
            db.insertBattingorder(setgameid(),awayteamid,Integer.getInteger(a_t_b_n_5.getText().toString()),5,a_t_d_l_5.getSelectedItemPosition());
            db.insertBattingorder(setgameid(),awayteamid,Integer.getInteger(a_t_b_n_6.getText().toString()),6,a_t_d_l_6.getSelectedItemPosition());
            db.insertBattingorder(setgameid(),awayteamid,Integer.getInteger(a_t_b_n_7.getText().toString()),7,a_t_d_l_7.getSelectedItemPosition());
            db.insertBattingorder(setgameid(),awayteamid,Integer.getInteger(a_t_b_n_8.getText().toString()),8,a_t_d_l_8.getSelectedItemPosition());
            db.insertBattingorder(setgameid(),awayteamid,Integer.getInteger(a_t_b_n_9.getText().toString()),9,a_t_d_l_9.getSelectedItemPosition());
            db.insertBattingorder(setgameid(),awayteamid,Integer.getInteger(a_t_b_n_10.getText().toString()),10,a_t_d_l_10.getSelectedItemPosition());
            db.insertBattingorder(setgameid(),awayteamid,Integer.getInteger(a_t_b_n_11.getText().toString()),11,a_t_d_l_11.getSelectedItemPosition());
        }
    };
    private View.OnClickListener goto_key_the_home_team_list = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            intent.setClass(Key_The_Away_Team_List.this,Key_The_Home_Team_List.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener goto_key_the_away_team_bench_list = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            intent.setClass(Key_The_Away_Team_List.this,Away_Team_Bench_List.class);
            startActivity(intent);
        }
    };
}
