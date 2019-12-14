# Käyttöohje

Lataa ensin [replayer.jar](https://github.com/gitblast/ot-harjoitustyo/releases/download/loppupalautus/replayer.jar), ja lisää sen juureen hakemisto [assets](https://github.com/gitblast/ot-harjoitustyo/tree/master/PokerHandReplayer/assets).

## Konfigurointi

Ohjelma olettaa, että sen juuresta löytyy käynnistettäessä hakemisto [assets](https://github.com/gitblast/ot-harjoitustyo/tree/master/PokerHandReplayer/assets), joka pitää sisällään kuvatiedoston jokaista pelikorttia kohden.

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla
```
java -jar replayer.jar
```
## Käsihistorian importtaus

Ohjelman käynnistyessä aukeaa ikkuna, jossa käyttäjä voi valita joko tekstitiedoston, joka sisältää käsihistorian, tai copy-pasteta käsihistorian suoraan tekstikenttään. Ohjelma kääntää käden automaattisesti. Esimerkkikäsiä ohjelman testaamiseen löytyy esimerkiksi hakemistosta [examples](https://github.com/gitblast/ot-harjoitustyo/tree/master/PokerHandReplayer/examples).

## Replayn selaaminen eteen- ja taaksepäin

Käsihistoriaa voi importtauksen jälkeen eteen ja taaksepäin vasemmasta alakulmasta löytyvillä nuolipainikkeilla.

## Kommenttien muokkaaminen ja lisääminen

Käyttäjä näkee pelitilanteeseen mahdollisesti asetetut kommentit nuolinäppäinten oikealla puolella. Painamalla "edit comment" -näppäintä käyttäjä pääsee muokkaamaan tilanteen kommenttia, ja sen jälkeen "save comment" -painikkeella tallentamaan uuden kommentin.

## Käden tallennus kommentteineen

Vasemmasta yläkulmasta löytyvä "save hand" -painike tallentaa käden kommentteineen käyttäjän valitsemaan hakemistoon. Käsi tallennetaan .txt -tiedostona, jotta se olisi helppoa jakaa eteenpäin kommentteineen muille ohjelman käyttäjille. Tämä tekee käsien analysoinnista yhdessä helpompaa.
