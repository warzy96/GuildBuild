package hr.fer.amigosi.guildbuildservice.REST;
import android.content.Intent;
import android.util.Log;

import java.io.Console;

import hr.fer.amigosi.guildbuildservice.ApplicationDatabase_Impl;
import hr.fer.amigosi.guildbuildservice.Main;
import hr.fer.amigosi.guildbuildservice.dao.CehEntityDao_Impl;
import hr.fer.amigosi.guildbuildservice.entities.CehEntity;
import hr.fer.amigosi.guildbuildservice.entities.IgraEntity;
import hr.fer.amigosi.guildbuildservice.entities.KorisnikEntity;

import static hr.fer.amigosi.guildbuildservice.Main.adb;

/**
 * Created by ivan_varga on 29/12/2017.
 */

public class RestService {

    public static void main(String[] args) {
        KorisnikEntity korisnik1 = new KorisnikEntity();
        korisnik1.setEmail("blabla@gmail.com");
        korisnik1.setLozinka("ttakoje");
        korisnik1.setNadimak("Test");
        korisnik1.setStatusRegistracije(false);
        korisnik1.setSifraCeha(2);

        CehEntity ceh1 = new CehEntity();
        ceh1.setSifraCeha(1);
        ceh1.setSifraIgre(10);

        CehEntity ceh2 = new CehEntity();
        ceh2.setSifraCeha(2);
        ceh2.setSifraIgre(10);

        IgraEntity igra = new IgraEntity();
        igra.setSifraIgre(10);
        KorisnikEntity korisnik2 = new KorisnikEntity();
        korisnik2.setEmail("blablaa@gmail.com");
        korisnik2.setLozinka("takoje");
        korisnik2.setNadimak("Test1");
        korisnik2.setStatusRegistracije(true);
        korisnik2.setSifraCeha(2);

        KorisnikEntity korisnik3 = new KorisnikEntity();
        korisnik3.setEmail("blablaa1@gmail.com");
        korisnik3.setLozinka("takoje");
        korisnik3.setNadimak("Test2");
        korisnik3.setStatusRegistracije(true);
        korisnik3.setSifraCeha(2);

        if(adb != null) {
            adb.cehEntityDao().insertCeh(ceh1);
            adb.cehEntityDao().insertCeh(ceh2);
            adb.igraEntityDao().insertIgra(igra);
            adb.korisnikEntityDao().insertUser(korisnik1);
            adb.korisnikEntityDao().insertUser(korisnik2);
            adb.korisnikEntityDao().insertUser(korisnik3);
        }
        else System.out.println("adb is null");
    }
}
