# Ohjelmistotekniikka harjoitustyö

## Poker Hand Replayer

Sovelluksen avulla käyttäjä voi käydä jälkikäteen läpi tekstipohjaisia standardimuotoisia käsihistorioita graafisessa muodossa.

Sovelluksen voi suorittaa juurihakemistossa ajamalla:

```
cd PokerHandReplayer && mvn compile exec:java -Dexec.mainClass=pokerhandreplayer.PokerHandReplayer
```

Sovelluslogiikka on kesken, tässä vaiheessa käsihistoria haetaan esimerkkitiedostosta. 

Tällä hetkellä sovellus tunnistaa pelaajat, pelaajien merkkitilanteen ja lähtökäden, sekä osaa etsiä uudet betsikoot riveiltä. Pian sovellus muodostaa rivi riviltä uusia GameState -olioita, jotka muodostavat lopullisen "Replayn", jota käyttäjä voi selata askel askeleelta ja tehdä kommentteja jokaisen "actionin" välillä.

Graafinen käyttöliittymä tulossa myöhemmin.

[Pakkaus/luokkakaavio](https://github.com/gitblast/ot-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

[Tuntikirjanpito](https://github.com/gitblast/ot-harjoitustyo/blob/master/dokumentointi/tuntikirjanpito.md)

[Laskarit](https://github.com/gitblast/ot-harjoitustyo/tree/master/laskarit)

[Vaatimusmäärittely](https://github.com/gitblast/ot-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md)
