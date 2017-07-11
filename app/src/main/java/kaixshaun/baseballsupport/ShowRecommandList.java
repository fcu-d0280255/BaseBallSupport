package kaixshaun.baseballsupport;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class ShowRecommandList extends AppCompatActivity {

    TextView teamname,recommandlist;
    Spinner gamenumber;
    Button taken;
    String Stringteam;
    BaseballDB db;
    int Intnumber,Intgames;
    String [] Arraytotalgames,Arrayteamid,Arraygameid;
    int[] Arrayback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recommand_list);

        db = new BaseballDB(this);

        gamenumber = (Spinner) findViewById(R.id.Games);
        teamname = (TextView)findViewById(R.id.TeamName);
        recommandlist = (TextView)findViewById(R.id.OrderList);
        taken = (Button)findViewById(R.id.button);
        taken.setOnClickListener(setlist);

        setteam();

        Cursor c = db.selectteamid(Stringteam);
        layoutset(c);
    }

    private View.OnClickListener setlist = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intgames = Integer.parseInt(gamenumber.getSelectedItem().toString());
            Arrayteamid = new String[Intgames];
            Arraygameid = new String[Intgames];

            //取得那幾場的teamid
            Cursor teamid_c = db.selectteamid(Stringteam);
            String [] names = teamid_c.getColumnNames();
            teamid_c.moveToFirst();
            for(int i = 0; i < Intgames; i++){
                Arrayteamid[i] = teamid_c.getString(teamid_c.getColumnIndex(names[0]));
            }

            //取得那幾場的gameid
            for(int i = 0; i < Intgames; i++) {

                Cursor temp_c = db.selectgameid(Arrayteamid[i]);
                String[] tempnames = temp_c.getColumnNames();
                temp_c.moveToFirst();
                Arraygameid[i] = temp_c.getString(temp_c.getColumnIndex(tempnames[0]));
            }

            //取得那幾場的背號
            int[] tempback = new int[Intgames*10];
            int index = 0;
            for (int i = 0; i < Intgames; i++){

                Cursor tempback_c = db.selestorder(Arraygameid[i],Arrayteamid[i]);
                String[] tempnames = tempback_c.getColumnNames();
                tempback_c.moveToFirst();
                for(int j = index; j < index+10; j++){

                    tempback[j] = tempback_c.getInt(tempback_c.getColumnIndex(tempnames[3]));
                    tempback_c.moveToNext();
                }
            }

            //刪除重複背號
            Set<Integer> intSet = new HashSet<Integer>();
            for (int element : tempback) {
                intSet.add(element);
            }
            Arrayback = new int[intSet.size()];
            Object[] tempArray = intSet.toArray();
            for (int i = 0; i < tempArray.length; i++) {
                Arrayback[i] = (int) tempArray[i];
            }
            for(int i = 0; i < Arrayback.length; i++ )
                Log.v(i+"=>",Arrayback[i]+"");

            //計算球員數值
            float[] Arrayba = new float[Arrayback.length];
            float[] Arrayobp = new float[Arrayback.length];
            int[] Arrayrbi = new int[Arrayback.length];
            float tempba,tempobp;
            int temprbi,times;
            for(int i = 0; i< Arrayback.length; i++){

                tempba=0;   tempobp=0;  temprbi=0;  times=0;

                for(int j = 0; j < Intgames; j++){

                    Cursor tempfinaldata_c = db.selectfinalrecord(Arraygameid[j],Arrayteamid[j],Arrayback[i]);
                    String[] tempfinaldatanames = tempfinaldata_c.getColumnNames();
                    tempfinaldata_c.moveToFirst();
                    if(tempfinaldata_c.getCount() > 0){

                        tempba = tempba + tempfinaldata_c.getFloat(tempfinaldata_c.getColumnIndex(tempfinaldatanames[7]));
                        tempobp = tempobp + tempfinaldata_c.getFloat(tempfinaldata_c.getColumnIndex(tempfinaldatanames[8]));
                        temprbi = temprbi + tempfinaldata_c.getInt(tempfinaldata_c.getColumnIndex(tempfinaldatanames[12]));
                        times++;
                    }

                }

                if(times == 0){

                    tempba = 0;
                    tempobp = 0;
                    temprbi = 0;
                }else{

                    tempba = tempba/times;
                    tempobp = tempobp/times;
                }
                Arrayba[i] = tempba;
                Arrayobp[i] = tempobp;
                Arrayrbi[i] = temprbi;
            }




        }
    };

    private void setteam(){

        Intent intent = getIntent();
        Stringteam = intent.getStringExtra(ShowGameList.Choice);
        teamname.setText("隊名: "+Stringteam);
    }

    private void layoutset(Cursor c){

        Intnumber = c.getCount();
        Arraytotalgames = new String[Intnumber];
        for(int i = 0; i < Intnumber; i++ )
            Arraytotalgames[i] = i+1+"";
        ArrayAdapter<String> adapterlist = new ArrayAdapter<String>(ShowRecommandList.this,android.R.layout.simple_spinner_dropdown_item,Arraytotalgames);
        gamenumber.setAdapter(adapterlist);
    }


}

