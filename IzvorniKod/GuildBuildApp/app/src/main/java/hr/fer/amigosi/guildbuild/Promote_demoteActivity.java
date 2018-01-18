package hr.fer.amigosi.guildbuild;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import hr.fer.amigosi.guildbuild.DAO.CehDAO;
import hr.fer.amigosi.guildbuild.DAO.RangDAO;
import hr.fer.amigosi.guildbuild.DAO.UserDAO;
import hr.fer.amigosi.guildbuild.entities.KorisnikEntity;

public class Promote_demoteActivity extends AppCompatActivity {
    private LinearLayout userListLayout;
    private Integer sifraCeha;
    private String nadimak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_owner_functions);
        userListLayout = findViewById(R.id.usersToChangeRankLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        sifraCeha = intent.getIntExtra(MainActivity.EXTRA_MESSAGE2, 0);
        nadimak = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        new PopulateGuildMembers().execute("");
    }


    private class PopulateGuildMembers extends AsyncTask<String,String, List<KorisnikEntity>> {
        KorisnikEntity korisnik;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            userListLayout.removeAllViews();
        }

        @Override
        protected List<KorisnikEntity> doInBackground(String... strings) {
            try {
                CehDAO cehDAO = new CehDAO();
                List<KorisnikEntity> korisnikEntityList = cehDAO
                        .getGuildMembersWithoutCurrentMember(sifraCeha.toString(), nadimak);
                UserDAO userDAO = new UserDAO();
                korisnik = userDAO.getUser(nadimak);
                return korisnikEntityList;
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    CehDAO.close();
                    UserDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<KorisnikEntity> korisnikEntityList) {
            if(korisnikEntityList.size() == 1 || korisnikEntityList.size() == 0) {
                Toast.makeText(Promote_demoteActivity.this, "No members to show...", Toast.LENGTH_SHORT).show();
                finish();
            }
            for(KorisnikEntity korisnikEntity : korisnikEntityList) {
                if(korisnikEntity.getRang().equals(RangConstants.leader)) continue;
                TextView textView = new TextView(Promote_demoteActivity.this);
                textView.setText(korisnikEntity.getNadimak()+ ", " + korisnikEntity.getRang());
                textView.setTextSize(35);
                textView.setTextColor(Color.WHITE);
                if(korisnikEntity.getRang().equals(RangConstants.member)) {
                    textView.setOnClickListener(view -> {
                        AlertDialog.Builder alertDialog = new AlertDialog
                                .Builder(Promote_demoteActivity.this);
                        alertDialog.setTitle("Are you sure you want to promote "
                                + korisnikEntity.getNadimak() + " to coordinator?");
                        alertDialog.setCancelable(false);
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new UpdateRank().execute(korisnikEntity.getNadimak(), RangConstants.coordinator);
                            }
                        });
                        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        alertDialog.show();
                    });
                }
                else if(korisnikEntity.getRang().equals(RangConstants.coordinator)) {
                    textView.setOnClickListener(view -> {
                        AlertDialog.Builder alertDialog = new AlertDialog
                                .Builder(Promote_demoteActivity.this);
                        alertDialog.setTitle("Are you sure you want to demote "
                                + korisnikEntity.getNadimak() + " to member?");
                        alertDialog.setCancelable(false);
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new UpdateRank().execute(korisnikEntity.getNadimak(), RangConstants.member);
                            }
                        });
                        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        alertDialog.show();
                    });
                }
                userListLayout.addView(textView);
                }
        }
    }


    public class UpdateRank extends AsyncTask<String, Void, String> {
        boolean success = false;
        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            try {
                RangDAO rangDAO = new RangDAO();
                rangDAO.updateUserRank(strings[0], strings[1], sifraCeha.toString());
                result = "Successful";
                success = true;
            }
            catch (Exception e) {
                result = e.getMessage();
            }
            finally {
                try {
                    UserDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            new PopulateGuildMembers().execute("");
        }
    }


}
