package kaixshaun.baseballsupport;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class ShowGameList extends AppCompatActivity {

    ListView listView;
    String[] templist,list,names;
    ArrayAdapter<String> listAdapter;
    BaseballDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_game_list);

        db = new BaseballDB(this);

        Cursor c = db.selectteamname();
        templist = new String[c.getCount()];
        names = c.getColumnNames();

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++){

            templist[i] = c.getString(c.getColumnIndex(names[0]));
            c.moveToNext();
        }

        //刪除重複隊名
        Set<String> StringSet = new HashSet<String>();
        for (String element : templist) {
            StringSet.add(element);
        }
        list = new String[StringSet.size()];
        Object[] tempArray = StringSet.toArray();
        for (int i = 0; i < tempArray.length; i++) {
            list[i] = (String) tempArray[i];
        }



        listView = (ListView)findViewById(R.id.GameList);
        listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
