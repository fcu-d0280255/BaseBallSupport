package kaixshaun.baseballsupport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartPage extends AppCompatActivity {

    Button newgame,loadgame,recordgame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        newgame = (Button)findViewById(R.id.new_game_btn);
        loadgame = (Button)findViewById(R.id.load_game_btn);
        recordgame = (Button)findViewById(R.id.record_game_btn);

        newgame.setOnClickListener(goto_newgame);
    }
    private View.OnClickListener goto_newgame= new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(StartPage.this,NewGameName.class);
            startActivity(intent);
        }
    };
}
