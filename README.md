# Ohjelmistotekniikka harjoitustyö

## Poker Hand Replayer

Sovelluksen avulla käyttäjä voi käydä jälkikäteen läpi tekstipohjaisia standardimuotoisia käsihistorioita graafisessa muodossa vaihe vaiheelta ja jättää kommentteja ja muistiinpanoja haluamiinsa vaiheisiin jälkianalyysiä varten.

## Sovelluksen suorittaminen

Sovelluksen voi suorittaa juurihakemistossa ajamalla ensin
```
cd PokerHandReplayer
```
ja sen jälkeen

```
mvn compile exec:java -Dexec.mainClass=pokerhandreplayer.PokerHandReplayer
```
## Testaus

PokerHandReplayer -hajemistossa:
```
mvn test
```

Jacoco -raportin generointi: (testauskattavuus tällä hetkellä huono, ohjelman seuraavassa versiossa se tulee olemaan kattavampi)
```
mvn test jacoco:report
```

Checkstyle -raportin generointi:
```
mvn jxr:jxr checkstyle:checkstyle
```
## Suoritettavan jarin generointi

PokerHandReplayer -hajemistossa: (ohjelman tämänhetkisestä versio olettaa [esimerkkitiedoston](https://github.com/gitblast/ot-harjoitustyo/blob/master/PokerHandReplayer/handhistory.txt) juurihakemistosta)
```
mvn package
```
## Sovelluslogiikasta

Sovelluslogiikka on kesken, tässä vaiheessa käsihistoria haetaan [esimerkkitiedostosta](https://github.com/gitblast/ot-harjoitustyo/blob/master/PokerHandReplayer/handhistory.txt), myöhemmin käyttäjä voi importata sen tekstitiedostosta tai copy-pasteamalla.

Tällä hetkellä sovellus tunnistaa pelaajat, pelaajien lähtömerkkitilanteen sekä lähtökortit. Sovellus luo jokaisen toimintavuoron jälkeen uuden pelitilan, joka sisältää pelaajien sen hetkiset merkkitilanteet, informaation siitä miten pelaaja käyttää vuoronsa (bet, call, raise, fold, post blind/ante, bring in), mahdollisen panostuksen sekä potin koon. Ohjelman seuraava versio tunnistaa myös kesken käden jaetut lisäkortit. Käden päätteeksi sovellus muodostaa yhteenvedon, jossa näkyy kunkin pelaajan kädessä voittama tai häviämä summa.

Käyttäjä voi selata pelitiloja eteen tai taaksepäin vaihe vaiheelta, tällä hetkellä kuitenkin vain tekstikäyttöliittymän avulla. Graafinen käyttöliittymä tulossa myöhemmin.

[Pakkaus/luokkakaavio](https://github.com/gitblast/ot-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

[Tuntikirjanpito](https://github.com/gitblast/ot-harjoitustyo/blob/master/dokumentointi/tuntikirjanpito.md)

[Laskarit](https://github.com/gitblast/ot-harjoitustyo/tree/master/laskarit)

[Vaatimusmäärittely](https://github.com/gitblast/ot-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md)
