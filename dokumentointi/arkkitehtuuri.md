## Käyttöliittymä

Käyttöliittymä sisältää kaksi näkymää, käden importtaus -näkymän sekä itse replayer -näkymän. Molemmat on toteutettu omana Scene -olionaan. Replayerin "liikkuvat" osat on toteutettu yhdistelemällä StackPaneja ja Paneja sopivasti elementtejä siirrellen.

## Sovelluslogiikka

Sovellukselle syötetään standardimuotoinen tekstitiedosto tai copy pastetaan käsihistoria, jonka sovellus muuttaa useiden vaiheiden kautta Replay -olioksi, joka on lista GameState -olioita. Käyttäjä voi selata GameStateja yksi kerrallaan eteen tai taaksepäin, ja jättää kommentteja jokaiseen stateen erikseen. 

## Tietojen pysyväistallennus

Käyttäjä voi tallentaa käsihistorian kommentteineen tekstitiedostoon. Tiedosto sopii ohjelmalle tietokantaa paremmin siksi, että tekstitiedostoja on helppo välittää eteenpäin käsien vertaistarkastelua ajatellen.

## Ohjelman rakenne:

![pakkauskaavio](https://raw.githubusercontent.com/gitblast/ot-harjoitustyo/master/dokumentointi/pakkaus.png)

## Ohjelman käyttäminen sekvenssikaaviona:

![sekvenssikaavio](https://github.com/gitblast/ot-harjoitustyo/blob/master/dokumentointi/sekvenssi.png)
