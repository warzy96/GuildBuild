package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import hr.fer.amigosi.guildbuild.DAO.LikDAO;
import hr.fer.amigosi.guildbuild.entities.LikEntity;

public class MyCharactersActivity extends AppCompatActivity {
    private String nickName;
    private LinearLayout characterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_characters);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        nickName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);

        characterList = (LinearLayout) findViewById(R.id.characterList);

        PopulateCharacterList populateCharacterList = new PopulateCharacterList();
        populateCharacterList.execute();
    }



    private class PopulateCharacterList extends AsyncTask<Void,Void, List<LikEntity>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            characterList.removeAllViews();
        }

        @Override
        protected List<LikEntity> doInBackground(Void... voids) {
            try {
                LikDAO likDAO = new LikDAO();
                List<LikEntity> likEntityList = likDAO.getAllCharactersForUser(nickName);
                return likEntityList;
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    LikDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<LikEntity> likEntityList) {
            if(likEntityList.isEmpty() || likEntityList==null) {
                Toast.makeText(MyCharactersActivity.this, "No characters to show", Toast.LENGTH_SHORT).show();
                finish();
            }
            for(LikEntity likEntity : likEntityList) {
                TextView textView = new TextView(MyCharactersActivity.this);
                textView.setText(likEntity.getImeLika());
                textView.setTextSize(30);
                textView.setTextColor(Color.WHITE);
                textView.setFocusable(false);
                TextView textView2 = new TextView(MyCharactersActivity.this);
                textView2.setText("LEVEL: " + likEntity.getLevel() + "\nCLASS: " + likEntity.getSifraKlase() + "\nCRAFTING SKILLS: " + likEntity.getCraftingSkills());
                textView2.setTextSize(15);
                textView2.setTextColor(Color.WHITE);
                textView2.setFocusable(false);
                View v = new View(MyCharactersActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
                lp.setMargins(0, 20, 0, 20);
                v.setLayoutParams(lp);
                v.setBackgroundColor(Color.parseColor("#B3B3B3"));
                characterList.addView(textView);
                characterList.addView(textView2);
                characterList.addView(v);
            }
        }
    }
}
