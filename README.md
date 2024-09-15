# TicketGuru-Sovellus

[TicketGuru backlog sekä käyttöliittymäkaavio -linkki](https://docs.google.com/spreadsheets/d/1MQNqwOzjuIXldOeYIx_NevCTvQeL70HyKikxyzmMKN8/edit?gid=0#gid=0)

# Johdanto

Projektin tavoitteena on luoda lipunmyyntijärjestelmä (TicketGuru) lipputoimistolle, joka myy lippuja erilaisiin tapahtumiin omassa myyntipisteessään. Toistaiseksi myynti tapahtuu vain paikan päällä lipputoimistossa, mutta myöhemmin mahdollisesti myös verkkokaupan kautta. Järjestelmän tarkoituksena on, että lipputoimisto pystyy lipunmyynnin lisäksi hallinnoimaan ja tarkastelemaan myyntitapahtumia.

Järjestelmä näyttää eri tapahtumiin tarjolla olevat lipputyypit ja niiden hinnat. Jokaisen myyntitapahtuman yhteydessä lippuihin tulostuu tarkastuskoodi. Sen avulla lippu voidaan merkitä käytetyksi ovella, kun asiakas tulee tapahtumaan. Lipputoimiston myyntipisteessä myydyt liput tulee voida tulostaa paperisena asiakkaille. Lipputoimiston asiakkaana on tapahtumajärjestäjiä, joille pystytään tapahtumakohtaisesti luomaan järjestelmästä myyntiraportteja. Raporteista nähdään kokonaismyynti myydyistä lipuista lipputyypeittäin sekä tarkempi erottelu jokaisesta erillisestä myyntitapahtumasta. 

Järjestelmä toteutetaan Spring Boot -menetelmän avulla Java-ohjelmointikieltä käyttäen. Järjestelmän palvelinratkaisuina pyritään käyttämään teknologiaa, joka mahdollistaa järjestelmän luotettavuuden, tietoturvallisen tiedonkäsittelyn ja sujuvan käytettävyyden sen kaikille käyttäjille. 

Käyttöliittymä on suunniteltu ensisijaisesti käytettäväksi pöytätietokoneilla ja läppäreillä, joita lipputoimistossa on käytössä. Kuitenkin lippujen tarkastuksen yhteydessä järjestelmää tulisi voida käyttää myös puhelimen avulla, jotta lippujen tarkastaminen on sujuvampaa.


# Järjestelmän määrittely

Järjestelmän määrittelyssä tarkastellaan TicketGuru-sovellusta käyttäjän näkökulmasta. Tämä osio keskittyy kuvaamaan, millaisia käyttäjäryhmiä (rooleja) sovelluksessa on ja millaisia toimintoja kullakin roolilla on käytettävissään. Lisäksi määrittelyssä käsitellään käyttäjätarinoita, jotka kuvaavat, miten käyttäjät vuorovaikuttavat sovelluksen kanssa ja mitkä ovat heidän tarpeensa ja odotuksensa. Tavoitteena on varmistaa, että sovellus täyttää käyttäjien ja organisaation vaatimukset, ja tarjoaa sujuvan, tehokkaan ja turvallisen käyttökokemuksen kaikille osapuolille.

## Käyttäjäryhmät (roolit)

### Lipputoimiston myyjä
- Pystyy tarkastelemaan eri tapahtumien lippuja, niiden tyyppejä ja hintoja.
- Voi myydä asiakkaalle lipun ja tulostaa sen.
- Voi tarkastella myymiensä lippujen myyntiraportteja.
- Ei pysty muokkaamaan tapahtumiin kirjattuja lipputietoja tai hintoja.

### Järjestelmän pääkäyttäjä
- Lipputoimiston henkilökuntaa.
- Pystyy käyttämään kaikkia järjestelmän ominaisuuksia (lisäys, muokkaus, poisto).
- Voi tarkastella kaikkien tapahtumien myyntiraportteja.
- Hallinnoi järjestelmän käyttäjien käyttöoikeuksia järjestelmään.

### Tapahtumajärjestäjä
- Pystyy tarkastelemaan omien tapahtumiensa lipputietoja.
- Voi luoda ja tulostaa myyntiraportteja omista tapahtumista.

### Asiakas
- Voi ostaa lippuja Lipputoimiston myyntipisteestä.
- Näkee ostamansa lipun tiedot (tapahtuma, lipputyyppi, hinta, tarkistuskoodi). Lipussa näkyy myös ostopäivämäärä ja aika.

## Käyttötapaukset ja käyttäjätarinat

### Lipputoimiston myyjä
- **Käyttötapaus 1: Tapahtumien tarkastelu**
  - **Tavoite:** Myyjä haluaa tarkastella tapahtumien lippuja, niiden tyyppejä ja hintoja.
  - **Toimet:** Myyjä navigoi järjestelmään, valitsee tarkasteltavan tapahtuman ja katsoo sen tiedot, kuten lipputyypit ja lippujen hinnat.
  - **Tulos:** Myyjä saa näkyviin valitun tapahtuman tiedot ja voi tarkastella sen ominaisuuksia.

- **Käyttötapaus 2: Lipun myynti**
    - **Tavoite:**Myyjä haluaa myydä lipun asiakkaalle.
    - **Toimet:** Myyjä valitsee tapahtuman, valitsee lipputyypin, syöttää asiakkaan tiedot, hyväksyy maksun ja tulostaa lipun.
    - **Tulos:** Asiakas saa lipun ja järjestelmä tallentaa myyntitapahtuman tiedot.

- **Käyttötapaus 3: Myyntiraporttien tarkastelu**
    - **Tavoite:** Myyjä haluaa tarkastella myymiään lippuja.
    - **Toimet:** Myyjä kirjautuu järjestelmään, valitsee ajanjakson ja tarkastelee raporttia omista myynneistään.
    - **Tulos:** Myyjä saa näkyviin raportin myymistään lipuista.

### Järjestelmän pääkäyttäjä
- **Käyttötapaus 1: Tapahtuman luominen**
    - **Tavoite:** Pääkäyttäjä haluaa lisätä uuden tapahtuman järjestelmään.
    - **Toimet:** Pääkäyttäjä syöttää tapahtuman tiedot (nimi, päivämäärä, lipputyypit, hinnat) ja tallentaa tiedot.
    - **Tulos:** Uusi tapahtuma on näkyvissä järjestelmässä ja myytävissä liput on määritelty.

- **Käyttötapaus 2: Käyttäjien hallinta**
    - **Tavoite:** Pääkäyttäjä haluaa lisätä tai poistaa käyttäjän järjestelmässä.
    - **Toimet:** Pääkäyttäjä luo uuden käyttäjätilin, määrittää roolin ja käyttöoikeudet tai poistaa käyttäjätilin käyttöoikeudet.
    - **Tulos:** Uusi käyttäjä on lisätty/poistettu ja voi käyttää järjestelmää määritettyjen oikeuksien mukaisesti.

- **Käyttötapaus 3: Tapahtumien myyntiraporttien tarkastelu**
    - **Tavoite:** Pääkäyttäjä haluaa tarkastella kaikkien tapahtumien myyntiraportteja.
    - **Toimet:** Pääkäyttäjä kirjautuu järjestelmään, valitsee tarkasteltavat tapahtumat ja aikajaksot, ja tarkastelee myyntiraportteja.
    - **Tulos:** Pääkäyttäjä saa näkyviin yhteenvedon myynnistä kaikkien tapahtumien osalta ja voi analysoida myyntitietoja.

### Tapahtumajärjestäjä
- **Käyttötapaus 1: Myyntiraportin luominen**
    - **Tavoite:** Tapahtumajärjestäjä haluaa tarkastella myyntiraporttia omasta tapahtumastaan.
    - **Toimet:** Tapahtumajärjestäjä valitsee tapahtuman ja aikajakson, luo raportin ja tulostaa sen.
    - **Tulos:** Tapahtumajärjestäjä saa raportin tapahtuman myynnistä.

- **Käyttötapaus 2: Myyntiraporttien tarkastelu**
    - **Tavoite:** Tapahtumajärjestäjä haluaa tarkastella myymiään lippuja.
    - **Toimet:** Tapahtumajärjestäjä kirjautuu järjestelmään, valitsee ajanjakson ja tarkastelee raporttia omista myynneistään.
    - **Tulos:** Tapahtumajärjestäjä saa näkyviin raportin myymistään lipuista.

### Asiakas
- **Käyttötapaus 1: Lipun ostaminen**
    - **Tavoite:** Asiakas haluaa ostaa lipun tapahtumaan.
    - **Toimet:** Asiakas valitsee tapahtuman, valitsee lipputyypin, maksaa lipun ja saa tulostetun lipun.
    - **Tulos:** Asiakas saa lipun ja voi tarkistaa sen tiedot.

- **Käyttötapaus 2: Lippujen tarkastelu**
    - **Tavoite:** Asiakas haluaa tarkastella ostamansa lipun tietoja.
    - **Toimet:** Asiakas kirjautuu järjestelmään, valitsee lipun ja tarkastelee sen tietoja, kuten tapahtuman, lipputyypin, hinnan ja tarkistuskoodin.
    - **Tulos:** Asiakas näkee lipun tiedot ja voi varmistaa lipun oikeellisuuden.

## Käyttäjätarinat

- **Käyttäjänä haluan ostaa liput tapahtumaan** niin, että voin valita tapahtuman, lipputyypin (aikuinen, lapsi, eläkeläinen jne.) ja ostaa haluamani määrän lippuja helposti. Tavoitteenani on sujuva ostokokemus ilman ongelmia.

- **Käyttäjänä haluan nähdä kaikki tulevat tapahtumat** niin, että voin valita tapahtuman ajan, paikan ja kuvauksen perusteella.

- **Käyttäjänä haluan tarkastella myyntiraporttia** niin, että näen myydyt liput (tyypin ja määrän) ja kokonaissumman nopeasti yhdellä silmäyksellä.

- **Käyttäjänä haluan hallinnoida tapahtumia** niin, että voin lisätä, muokata ja poistaa tapahtumia, sekä hallita niihin liittyviä lippuja ja niiden määriä.

- **Käyttäjänä haluan tulostaa ostamani liput** niin, että voin käyttää niitä tapahtumassa ilman erillisiä toimenpiteitä.

- **Yrityksenä haluan pystyä näkemään lipunmyynnin trendejä** ajan mittaan, jotta voin suunnitella tulevia tapahtumia ja optimoida lippujen hinnoittelua.

- **Yrityksenä haluan luoda ja hallita tapahtumia** yksinkertaisesti, jotta voin lisätä uusia tapahtumia, määrittää lipputyypit, hinnat ja määrät tehokkaasti.

- **Yrityksenä haluan varmistaa, että sovellus toimii hyvin eri laitteilla**, jotta voin hallita tapahtumia ja tarkastella raportteja sekä työpöydältä että mobiililaitteilta.

- **Käyttäjänä haluan sovelluksen latautuvan nopeasti**, jotta voin käyttää sitä sujuvasti ilman pitkiä odotusaikoja.

- **Käyttäjänä haluan, että tietoni ovat suojattuja** ja että sovellus käyttää vahvoja salausmenetelmiä, jotta henkilökohtaiset tietoni ovat turvassa.

- **Yrityksenä haluan integroivan sovelluksen useisiin maksujärjestelmiin**, jotta asiakkaat voivat maksaa liput haluamallaan tavalla.

- **Käyttäjänä haluan sovelluksen olevan helppokäyttöinen ja intuitiivinen**, jotta voin löytää tarvittavat tiedot ja toiminnot vaivattomasti.

- **Kehittäjänä haluan laatia testitapaukset eri sovelluksen toiminnoille**, jotta voimme varmistaa, että kaikki osat toimivat oikein ennen julkaisua.

- **Kehittäjänä haluan seurata ja raportoida sovelluksen virheitä ja bugeja**, jotta ne voidaan korjata nopeasti ja parantaa sovelluksen laatua.

## Yksityiskohtaiset vaatimukset

- **Lippujen tulostaminen:** Liput tulostetaan standardikokoiselle paperille, ja lipussa on mukana QR-koodi tai viivakoodi tarkistamista varten.
- **Tietoturva:** Käyttäjän tiedot salataan ja tallennetaan turvallisesti.

## Käyttöliittymävaatimukset

- **Myyjän käyttöliittymä:** Yksinkertainen ja selkeä käyttöliittymä, jossa on helppo navigoida tapahtumien ja lippujen välillä.
- **Raporttien tarkastelu:** Raportit esitetään visuaalisesti ymmärrettävällä tavalla, kuten taulukoina tai kaavioina.

## Yhteenveto ja rajaukset

- **Sisältyvät toiminnot:** Lipun myynti, lippujen tulostaminen, myyntiraportit, käyttäjien hallinta.
- **Ei sisälly:** Verkkokauppatoiminnot (tulevaisuudessa mahdollisesti).


# Käyttöliittymä

Käyttöliittymässä on useita tärkeitä näkymiä, joiden avulla käyttäjät voivat suorittaa tarvitsemansa toiminnot. Tässä kuvataan nämä päänäkymät ja miten niissä liikutaan.

### Etusivu
- **Miksi:** Etusivu toimii pääsivuna, josta käyttäjä pääsee kaikkiin tärkeimpiin osiin sovelluksessa.
- **Mitä:** Sivulla on linkit tapahtumien hallintaan, lipunmyyntiin ja raporttien tarkasteluun.
- **Siirtymiset:** Etusivulta käyttäjä pääsee helposti muihin näkymiin ja takaisin.

### Tapahtumien hallinta
- **Miksi:** Täällä käyttäjä voi hallita tapahtumia, kuten lisätä, muokata ja poistaa niitä.
- **Mitä:** Näkymässä on lista tapahtumista, lomake uusille tapahtumille ja työkalut tapahtumien muokkaamiseen.
- **Siirtymiset:** Käyttäjä voi siirtyä etusivulta tapahtumien hallintaan ja takaisin etusivulle.

### Lipunmyyntinäkymä
- **Miksi:** Tämä on myyjien työskentelynäkymä, jossa he voivat myydä lippuja asiakkaille.
- **Mitä:** Näkymässä valitaan tapahtuma, lipputyyppi, syötetään asiakastiedot ja maksetaan liput.
- **Siirtymiset:** Etusivulta käyttäjä pääsee lipunmyyntiin ja takaisin. Lipunmyyntitapahtumasta voi siirtyä myös myyntiraporttiin.

### Myyntiraportit
- **Miksi:** Täällä käyttäjä voi tarkastella myyntiraportteja ja saada kokonaiskuvan myynnistä.
- **Mitä:** Näkymässä on raporttilistat, suodatusvaihtoehdot ja yksityiskohtaiset myyntitiedot.
- **Siirtymiset:** Raporttien tarkastelusta voi palata etusivulle.

### Asiakasnäkymä
- **Miksi:** Asiakkaat voivat tarkastella ostamiaan lippuja ja tapahtumatietoja.
- **Mitä:** Näkymässä näkyvät ostetut liput, tapahtumatiedot ja tarkistuskoodi.
- **Siirtymiset:** Asiakas voi siirtyä asiakasnäkymään etusivulta ja palata takaisin etusivulle.

### Käyttöliittymäkaavio
- **Kaavio:** [Käyttöliittymäkaavio -linkki](https://docs.google.com/spreadsheets/d/1MQNqwOzjuIXldOeYIx_NevCTvQeL70HyKikxyzmMKN8/edit?gid=643351026#gid=643351026)
- **Miksi:** Käyttöliittymäkaavio näyttää, miten eri näkymät liittyvät toisiinsa ja miten käyttäjä navigoi niiden välillä.