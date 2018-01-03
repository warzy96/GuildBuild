Za pokretanje u MySQL Workbench u prozoru query pokrenes skriptu iz guildbuild_modfied.
Modified je zato jer sam maknuo foreign key u korisniku i promjenio atribut "nazivCilja" 
koji je bio u relaciji "Podcilj" u "nazivPodcilja".
Daljnje modifikacije se naprave na modelu (.mwb), model se spremi i onda export -> forward
engineer i samo slijedi wizard.
Ako ne zeli spremiti datoteku samo copy to clipboard i rucno se napravi .sql file.
Za ponovno postavljanje baze samo opet u prozoru query pokrenes najnoviju skriptu i to je to.

DatabaseConnection (u javi) se u pravilu ne bi trebao mijenjati.
Njega samo koristimo da dobijemo connection na bazu i to je to.