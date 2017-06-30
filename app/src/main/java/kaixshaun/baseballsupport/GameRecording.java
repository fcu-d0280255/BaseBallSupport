package kaixshaun.baseballsupport;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GameRecording extends AppCompatActivity {

    BaseballDB db;
    TextView atv,atmv,htv,htmv;
    String gameid,awayteamid,hometeamid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_recording);

        db = new BaseballDB(this);

        atv = (TextView)findViewById(R.id.awayteamview);
        atmv = (TextView)findViewById(R.id.awayteamateview);
        htv = (TextView)findViewById(R.id.hometeamview);
        htmv = (TextView)findViewById(R.id.awayteamateview);

        Intent intent = getIntent();
        gameid = intent.getStringExtra(Home_Team_Bench_List.GameID);
        awayteamid = intent.getStringExtra(Home_Team_Bench_List.AwayTeamID);
        hometeamid = intent.getStringExtra(Home_Team_Bench_List.HomeTeamID);

        Cursor awayteamname_c = db.selsectteam(awayteamid);
        awayteamname_c.moveToFirst();
        Cursor hometeamname_c = db.selsectteam(hometeamid);
        hometeamname_c.moveToFirst();
        Cursor awayteamorder_c = db.selestorder(gameid,awayteamid);
        awayteamorder_c.moveToFirst();
        Cursor hometeamorder_c = db.selestorder(gameid,hometeamid);
        hometeamorder_c.moveToFirst();
        Cursor awayteammate_c = db.selectteamate(gameid,awayteamid);
        awayteammate_c.moveToFirst();
        Cursor hometeammate_c = db.selectteamate(gameid,hometeamid);
        hometeammate_c.moveToFirst();

        String atvtemp = null, atmvtemp = null, htvtemp = null, htmvtemp = null;
        String [] names = awayteamname_c.getColumnNames();

        atvtemp = awayteamname_c.getString(awayteamname_c.getColumnIndex(names[2])) + "\n";

        names = awayteamorder_c.getColumnNames();

        for(int i = 0 ; i < awayteamorder_c.getCount(); i++){

            atvtemp = atvtemp + awayteamorder_c.getString(awayteamorder_c.getColumnIndex(names[4])) +" "
                    +  awayteamorder_c.getString(awayteamorder_c.getColumnIndex(names[3])) +" "
                    +  awayteamorder_c.getString(awayteamorder_c.getColumnIndex(names[5])) +"\n";
            awayteamorder_c.moveToNext();
        }
        atv.setText(atvtemp);

        /*names = awayteammate_c.getColumnNames();

        for(int i = 0; i < awayteammate_c.getCount(); i++){

            atmvtemp = atvtemp + awayteammate_c.getString(awayteammate_c.getColumnIndex(names[2])) + " "
                    + awayteammate_c.getString(awayteammate_c.getColumnIndex(names[3])) + "\n";
        }
        atmv.setText(atmvtemp);

        names = hometeamname_c.getColumnNames();

        htvtemp = hometeamname_c.getString(hometeamname_c.getColumnIndex(names[1])) + "\n";

        names = hometeamorder_c.getColumnNames();

        for(int i = 0 ; i < hometeamorder_c.getCount(); i++){

            htvtemp = htvtemp + hometeamorder_c.getString(hometeamorder_c.getColumnIndex(names[3])) +" "
                    +  hometeamorder_c.getString(hometeamorder_c.getColumnIndex(names[2])) +" "
                    +  hometeamorder_c.getString(hometeamorder_c.getColumnIndex(names[4])) +"\n";
            hometeamorder_c.moveToNext();
        }
        htv.setText(htvtemp);

        names = hometeammate_c.getColumnNames();

        for(int i = 0; i < hometeammate_c.getCount(); i++){

            htmvtemp = htvtemp + hometeammate_c.getString(hometeammate_c.getColumnIndex(names[2])) + " "
                    + hometeammate_c.getString(hometeammate_c.getColumnIndex(names[3])) + "\n";
        }
        htmv.setText(htmvtemp);*/


    }
}
