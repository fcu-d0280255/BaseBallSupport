package kaixshaun.baseballsupport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartPage extends AppCompatActivity {

    Button newgame,gamelist,recordgame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        newgame = (Button)findViewById(R.id.new_game_btn);
        gamelist = (Button)findViewById(R.id.RecommendList_Btn);
        recordgame = (Button)findViewById(R.id.record_game_btn);

        newgame.setOnClickListener(goto_newgame);
        gamelist.setOnClickListener(goto_gamelist);
    }
    private View.OnClickListener goto_newgame= new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(StartPage.this,NewGameName.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener goto_gamelist= new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(StartPage.this,ShowGameList.class);
            startActivity(intent);
        }
    };
}
