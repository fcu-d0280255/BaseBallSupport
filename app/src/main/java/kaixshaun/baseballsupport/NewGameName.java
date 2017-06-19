package kaixshaun.baseballsupport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewGameName extends AppCompatActivity {
    public static final String GameID = "GameID";
    BaseballDB db;
    EditText gamename;
    Button enter,cancel;
    String gameid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game_name);

        db = new BaseballDB(NewGameName.this);
        gamename = (EditText)findViewById(R.id.game_name);
        enter = (Button)findViewById(R.id.enter_btn);
        cancel = (Button)findViewById(R.id.cancel_btn);

        enter.setOnClickListener(goto_key_the_away_list);

    }

    private View.OnClickListener goto_key_the_away_list = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            Intent intent = new Intent();
            intent.setClass(NewGameName.this,Key_The_Away_Team_List.class);
            //gameid = gamename.getText().toString();
            String gameName = gamename.getText().toString();
            Log.v("test",gameName);
            gameid = db.insertGamename(gameName);
            Log.v("test",gameid);
            //intent.putExtra(GameID, gameid);
            startActivity(intent);
        }
    };
}
