package kaixshaun.baseballsupport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Key_The_Home_Team_List extends AppCompatActivity {

    //後攻方守位變數
    Spinner h_t_d_l_1,h_t_d_l_2,h_t_d_l_3,h_t_d_l_4,h_t_d_l_5,h_t_d_l_6;
    Spinner h_t_d_l_7,h_t_d_l_8,h_t_d_l_9,h_t_d_l_10,h_t_d_l_11;

    Button key_h_t_bench_l,key_t_a_t_l,store_t_h_t_l,cancel_t_h_t_l,start_t_g_ht;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key__the__home__team__list);

        final String[] defense = {"捕手","投手","一壘手","二壘手","游擊手","三壘手","自由手","右外野手","中外野手","左外野手","指名打擊","無"};
        h_t_d_l_1 = (Spinner)findViewById(R.id.home_team_defense_location_1);
        h_t_d_l_2 = (Spinner)findViewById(R.id.home_team_defense_location_2);
        h_t_d_l_3 = (Spinner)findViewById(R.id.home_team_defense_location_3);
        h_t_d_l_4 = (Spinner)findViewById(R.id.home_team_defense_location_4);
        h_t_d_l_5 = (Spinner)findViewById(R.id.home_team_defense_location_5);
        h_t_d_l_6 = (Spinner)findViewById(R.id.home_team_defense_location_6);
        h_t_d_l_7 = (Spinner)findViewById(R.id.home_team_defense_location_7);
        h_t_d_l_8 = (Spinner)findViewById(R.id.home_team_defense_location_8);
        h_t_d_l_9 = (Spinner)findViewById(R.id.home_team_defense_location_9);
        h_t_d_l_10 = (Spinner)findViewById(R.id.home_team_defense_location_10);
        h_t_d_l_11 = (Spinner)findViewById(R.id.home_team_defense_location_11);
        ArrayAdapter<String> defenselist = new ArrayAdapter<String>(Key_The_Home_Team_List.this,android.R.layout.simple_spinner_dropdown_item,defense);
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

        //跳去先攻隊員名單的按鈕功能
        key_t_a_t_l = (Button)findViewById(R.id.key_the_away_team_list_btn);
        key_t_a_t_l.setOnClickListener(goto_key_the_away_team_list);

        //跳去輸入後攻候補名單功能
        key_h_t_bench_l = (Button)findViewById(R.id.key_the_home_team_bench_list_btn);
        key_h_t_bench_l.setOnClickListener(goto_key_the_home_team_bench_list);
    }
    private View.OnClickListener goto_key_the_away_team_list = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            intent.setClass(Key_The_Home_Team_List.this,Key_The_Away_Team_List.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener goto_key_the_home_team_bench_list = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            intent.setClass(Key_The_Home_Team_List.this,Home_Team_Bench_List.class);
            startActivity(intent);
        }
    };
}
