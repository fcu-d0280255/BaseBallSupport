package kaixshaun.baseballsupport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class PlayRecording extends AppCompatActivity {

    TextView InningView,OutView,ScoreView,BackView,RuleView;
    EditText GetScoreView;
    Spinner FlytoView,BaseView,B_EView,SituationView,DiedwayView,KillView;
    Button NextBtn;

    int inning,out = 0,awayscore = 0,homescore = 0,back;
    String showInning,topHalf = "上",bottonHalf = "下";
    BaseballDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_recording);

        declare();
        db = new BaseballDB(this);

        showInning = inning + topHalf;
        while (out < 3){

            InningView.setText(showInning);
            OutView.setText(out);
            ScoreView.setText(awayscore+" - "+homescore);
            BackView.setText();
            RuleView.setText();
            out = out + Integer.parseInt(KillView.getSelectedItem().toString());
        }


    }

    private void declare(){

        InningView = (TextView)findViewById(R.id.Inning);
        OutView = (TextView)findViewById(R.id.Out);
        ScoreView = (TextView)findViewById(R.id.Score);
        BackView = (TextView)findViewById(R.id.Back);
        RuleView = (TextView)findViewById(R.id.Rule);
        GetScoreView = (EditText)findViewById(R.id.GetScore);

        final String[] flyto = {"無","投手","捕手","一壘手","二壘手","三壘手","游擊手","左外野手","中外野手","右外野手","自由手","指名打擊"};
        FlytoView = (Spinner)findViewById(R.id.Flyto);
        ArrayAdapter<String> flytolist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, flyto);
        FlytoView.setAdapter(flytolist);

        final String[] onbase = {"無","1","2","3","全壘打"};
        BaseView = (Spinner)findViewById(R.id.Base);
        ArrayAdapter<String> onbaselist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, onbase);
        BaseView.setAdapter(onbaselist);

        final String[] b_e = {"B","E"};
        B_EView = (Spinner)findViewById(R.id.B_E);
        ArrayAdapter<String> b_elist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, b_e);
        B_EView.setAdapter(b_elist);

        final String[] situation = {"...","^"};
        SituationView = (Spinner)findViewById(R.id.Situation);
        ArrayAdapter<String> situationlist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, situation);
        SituationView.setAdapter(situationlist);

        final String[] diedway = {"無","刺殺","阻殺","界K","揮K","看K"};
        DiedwayView = (Spinner)findViewById(R.id.Diedway);
        ArrayAdapter<String> diedwaylist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, diedway);
        DiedwayView.setAdapter(diedwaylist);

        final String[] kill = {"0","1","2","3"};
        KillView = (Spinner)findViewById(R.id.Kill);
        ArrayAdapter<String> killlist = new ArrayAdapter<String>(PlayRecording.this, android.R.layout.simple_spinner_dropdown_item, kill);
        KillView.setAdapter(killlist);

        NextBtn = (Button)findViewById(R.id.Next);
    }
}
