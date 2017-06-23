package kaixshaun.baseballsupport;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TestDB extends AppCompatActivity {

    BaseballDB db;
    TextView textView;
    public String gameid;
    public String awayteamid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db);

        textView = (TextView)findViewById(R.id.testtextView);
        db = new BaseballDB(this);
        setid();
        
        Cursor c =db.selestorder(gameid);
        String [] names = c.getColumnNames();
        c.moveToFirst();
        String temp = null;
        for (int i = 0; i <c.getCount();i++) {

           temp =  c.getInt(c.getColumnIndex(names[3])) + "\n"+temp;

            c.moveToNext();
        }
        textView.setText(temp);



    }

    public void setid(){

        Intent intent = getIntent();
        gameid = intent.getStringExtra(Key_The_Away_Team_List.GameID);
        awayteamid = intent.getStringExtra(Key_The_Away_Team_List.AwayTeamID);
    }
}
