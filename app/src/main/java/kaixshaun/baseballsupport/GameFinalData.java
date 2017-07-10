package kaixshaun.baseballsupport;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GameFinalData extends AppCompatActivity {

    TextView awayteamview,hometeamview;
    String gameid,awayteamid,hometeamid,awayteamshow = "",hometeamshow = "";
    Cursor awayteamrecording,hometeamrecording,awayteamorder,hometeamorder,temp_c;
    int back,order,rule,pa,hit,walk,error,rbi;
    float ba,obp;
    String[] names;
    BaseballDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_final_data);

        setid();

        db = new BaseballDB(this);

        awayteamorder = db.selestorder(gameid,awayteamid);
        awayteamorder.moveToFirst();
        names = awayteamorder.getColumnNames();


        back = awayteamorder.getInt(awayteamorder.getColumnIndex(names[3]));
        order = awayteamorder.getInt(awayteamorder.getColumnIndex(names[4]));
        rule = awayteamorder.getInt(awayteamorder.getColumnIndex(names[5]));

        temp_c = db.selectpa(gameid,awayteamid,back);
        pa = temp_c.getCount();

        temp_c = db.selecthit(gameid,awayteamid,back);
        hit = temp_c.getCount();
        ba = hit/pa;

        temp_c = db.selectonbase(gameid,awayteamid,back);
        obp = temp_c.getCount()/pa;

        temp_c = db.selectwalk(gameid,awayteamid,back);
        walk = temp_c.getCount();

        temp_c = db.selecterror(gameid,awayteamid,back);
        error = temp_c.getCount();

        temp_c = db.selectrbi(gameid,awayteamid,back);
        rbi = 0;
        temp_c.moveToFirst();
        names = temp_c.getColumnNames();

        for(int i = 0; i < temp_c.getCount(); i++){

            rbi = rbi + temp_c.getInt(temp_c.getColumnIndex(names[10]));
            temp_c.moveToNext();
        }

        db.insertfinaldata(gameid,awayteamid,back,order,rule,pa,ba,obp,hit,walk,error,rbi);








        awayteamview = (TextView)findViewById(R.id.awayteamrecording);
        hometeamview = (TextView)findViewById(R.id.hometeamrecording);

        awayteamrecording = db.selectrecording(gameid,awayteamid);
        hometeamrecording = db.selectrecording(gameid,hometeamid);

        awayteamrecording.moveToFirst();
        names = awayteamrecording.getColumnNames();
        for(int i = 0; i < awayteamrecording.getCount(); i ++){

            awayteamshow = awayteamshow+awayteamrecording.getInt(awayteamrecording.getColumnIndex(names[3])) + " 號  " +
                    awayteamrecording.getInt(awayteamrecording.getColumnIndex(names[4])) + " 上  " +
                    awayteamrecording.getInt(awayteamrecording.getColumnIndex(names[6])) + " 棒  " +
                    awayteamrecording.getString(awayteamrecording.getColumnIndex(names[7])) + "  飛去" +
                    awayteamrecording.getInt(awayteamrecording.getColumnIndex(names[8])) + "   " +
                    awayteamrecording.getInt(awayteamrecording.getColumnIndex(names[9])) + " out   得" +
                    awayteamrecording.getInt(awayteamrecording.getColumnIndex(names[10])) + " 分\n";
            awayteamrecording.moveToNext();
        }
        awayteamview.setText(awayteamshow);

        hometeamrecording.moveToFirst();
        names = hometeamrecording.getColumnNames();
        for(int i = 0; i < hometeamrecording.getCount(); i ++){

            hometeamshow = hometeamshow+hometeamrecording.getInt(hometeamrecording.getColumnIndex(names[3])) + " 號  " +
                    hometeamrecording.getInt(hometeamrecording.getColumnIndex(names[4])) + " 上  " +
                    hometeamrecording.getInt(hometeamrecording.getColumnIndex(names[6])) + " 棒  " +
                    hometeamrecording.getString(hometeamrecording.getColumnIndex(names[7])) + "  飛去" +
                    hometeamrecording.getInt(hometeamrecording.getColumnIndex(names[8])) + "   " +
                    hometeamrecording.getInt(hometeamrecording.getColumnIndex(names[9])) + " out   得" +
                    hometeamrecording.getInt(hometeamrecording.getColumnIndex(names[10])) + " 分\n";
            hometeamrecording.moveToNext();
        }
        hometeamview.setText(hometeamshow);


    }

    private void setid() {

        Intent intent = getIntent();
        gameid = intent.getStringExtra(PlayRecording.GameID);
        awayteamid = intent.getStringExtra(PlayRecording.AwayTeamID);
        hometeamid = intent.getStringExtra(PlayRecording.HomeTeamID);
    }
}
