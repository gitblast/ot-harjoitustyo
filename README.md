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

Jacoco -raportin generointi:
```
mvn test jacoco:report
```

Checkstyle -raportin generointi:
```
mvn jxr:jxr checkstyle:checkstyle
```
## Suoritettavan jarin generointi

PokerHandReplayer -hajemistossa: (ohjelma olettaa, että jar:n juurikansiosta löytyy kansio [assets](https://github.com/gitblast/ot-harjoitustyo/tree/master/PokerHandReplayer/assets) joka sisältää kuvat pelikorteista)
```
mvn package
```
## Javadocin generointi

PokerHandReplayer -hakemistossa:
```
mvn javadoc:javadoc
```

## Sovelluslogiikasta

Käyttäjä voi ladata standardimuotoisia käsihistorioita ohjelmaan joko copy-pasteamalla tai tekstitiedoston syöttämällä. Sovellus näyttää graafisen esityksen kädestä, ja mahdollistaa jokaisen vaiheen kommennoinnin sekä kommentoidun käsihistorian tallentamisen muille jakamista varten.

[Käyttöohje](https://github.com/gitblast/ot-harjoitustyo/blob/master/dokumentointi/kayttoohje.md)

[Pakkaus/luokkakaavio](https://github.com/gitblast/ot-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

[Tuntikirjanpito](https://github.com/gitblast/ot-harjoitustyo/blob/master/dokumentointi/tuntikirjanpito.md)

[Laskarit](https://github.com/gitblast/ot-harjoitustyo/tree/master/laskarit)

[Vaatimusmäärittely](https://github.com/gitblast/ot-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md)

[Arkkitehtuuri](https://github.com/gitblast/ot-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)
