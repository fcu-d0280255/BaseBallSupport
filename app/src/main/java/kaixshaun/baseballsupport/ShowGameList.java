package kaixshaun.baseballsupport;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShowGameList extends AppCompatActivity {

    ListView listView;
    String[] list,names;
    ArrayAdapter<String> listAdapter;
    BaseballDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_game_list);

        db = new BaseballDB(this);

        Cursor c = db.selectgame();
        list = new String[c.getCount()];
        names = c.getColumnNames();



        c.moveToFirst();

        for(int i = 0; i < c.getCount(); i++){

            list[i] = c.getString(c.getColumnIndex(names[0]));
            c.moveToNext();
        }



        listView = (ListView)findViewById(R.id.GameList);
        listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(listAdapter);
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/
    }
}
