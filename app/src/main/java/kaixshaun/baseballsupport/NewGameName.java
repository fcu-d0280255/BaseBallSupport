package kaixshaun.baseballsupport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        gamename = (EditText)findViewById(R.id.game_name);
        enter = (Button)findViewById(R.id.enter_btn);
        cancel = (Button)findViewById(R.id.cancel_btn);
        db = new BaseballDB(this);
        enter.setOnClickListener(goto_key_the_away_list);
        cancel.setOnClickListener(cancelgame);

    }

    private View.OnClickListener goto_key_the_away_list = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();

            //換頁面的參數
            intent.setClass(NewGameName.this,Key_The_Away_Team_List.class);

            //得到gameid
            String gameName = gamename.getText().toString();
            Log.v("test",gameName);
            gameid = db.insertGamename(gameName);
            Log.v("test",gameid);

            //傳gameid給別的activity
            intent.putExtra(GameID, gameid);
            if("".equals(gamename.getText().toString().trim())){

                Toast toast = Toast.makeText(NewGameName.this,"請輸入比賽名稱",Toast.LENGTH_LONG);
                toast.show();
            }
            else
                startActivity(intent);
        }
    };

    private  View.OnClickListener cancelgame =new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            NewGameName.this.finish();
        }
    };
}
