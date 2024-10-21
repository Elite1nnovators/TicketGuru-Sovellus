# TicketGuru-Sovellus

[TicketGuru backlog -linkki](https://docs.google.com/spreadsheets/d/1MQNqwOzjuIXldOeYIx_NevCTvQeL70HyKikxyzmMKN8/edit?gid=0#gid=0)

# Johdanto
Projektin tavoitteena on luoda lipunmyyntijärjestelmä (TicketGuru) lipputoimistolle, joka myy lippuja erilaisiin tapahtumiin omassa myyntipisteessään. Toistaiseksi myynti tapahtuu vain paikan päällä lipputoimistossa, mutta myöhemmin mahdollisesti myös verkkokaupan kautta. Järjestelmän tarkoituksena on, että lipputoimisto pystyy lipunmyynnin lisäksi hallinnoimaan ja tarkastelemaan myyntitapahtumia.

Järjestelmä näyttää eri tapahtumiin tarjolla olevat lipputyypit ja niiden hinnat. Jokaisen myyntitapahtuman yhteydessä lippuihin tulostuu tarkastuskoodi. Sen avulla lippu voidaan merkitä käytetyksi ovella, kun asiakas tulee tapahtumaan. Lipputoimiston myyntipisteessä myydyt liput tulee voida tulostaa paperisena asiakkaille. Lipputoimiston asiakkaana on tapahtumajärjestäjiä, joille pystytään tapahtumakohtaisesti luomaan järjestelmästä myyntiraportteja. Raporteista nähdään kokonaismyynti myydyistä lipuista lipputyypeittäin sekä tarkempi erottelu jokaisesta erillisestä myyntitapahtumasta. 

Järjestelmä toteutetaan Spring Boot -menetelmän avulla Java-ohjelmointikieltä käyttäen. Järjestelmän palvelinratkaisuina pyritään käyttämään teknologiaa, joka mahdollistaa järjestelmän luotettavuuden, tietoturvallisen tiedonkäsittelyn ja sujuvan käytettävyyden sen kaikille käyttäjille. 

Käyttöliittymä on suunniteltu ensisijaisesti käytettäväksi pöytätietokoneilla ja läppäreillä, joita lipputoimistossa on käytössä. Kuitenkin lippujen tarkastuksen yhteydessä järjestelmää tulisi voida käyttää myös puhelimen avulla, jotta lippujen tarkastaminen on sujuvampaa.

# Järjestelmän määrittely

Järjestelmän määrittelyssä tarkastellaan TicketGuru-sovellusta käyttäjän näkökulmasta. Tämä osio keskittyy kuvaamaan, millaisia käyttäjäryhmiä (rooleja) sovelluksessa on ja millaisia toimintoja kullakin roolilla on käytettävissään. Lisäksi määrittelyssä käsitellään käyttäjätarinoita, jotka kuvaavat, miten käyttäjät vuorovaikuttavat sovelluksen kanssa ja mitkä ovat heidän tarpeensa ja odotuksensa. Tavoitteena on varmistaa, että sovellus täyttää käyttäjien ja organisaation vaatimukset, ja tarjoaa sujuvan, tehokkaan ja turvallisen käyttökokemuksen kaikille osapuolille.

## Käyttäjäryhmät (roolit)
<details>
<summary> Lipputoimiston myyjä</summary>


### Lipputoimiston myyjä
- Pystyy tarkastelemaan eri tapahtumien lippuja, niiden tyyppejä ja hintoja.
- Voi myydä asiakkaalle lipun ja tulostaa sen.
- Voi tarkastella myymiensä lippujen myyntiraportteja.
- Ei pysty muokkaamaan tapahtumiin kirjattuja lipputietoja tai hintoja.
</details>

<details>
<summary> Järjestelmän pääkäyttäjä</summary>

### Järjestelmän pääkäyttäjä
- Lipputoimiston henkilökuntaa.
- Pystyy käyttämään kaikkia järjestelmän ominaisuuksia (lisäys, muokkaus, poisto).
- Voi tarkastella kaikkien tapahtumien myyntiraportteja.
- Hallinnoi järjestelmän käyttäjien käyttöoikeuksia järjestelmään.
</details>

<details>
<summary> Tapahtumajärjestäjä</summary>

### Tapahtumajärjestäjä
- Pystyy tarkastelemaan omien tapahtumiensa lipputietoja.
- Voi luoda ja tulostaa myyntiraportteja omista tapahtumista.
</details>

<details>
<summary> Asiakas</summary>

### Asiakas
- Voi ostaa lippuja Lipputoimiston myyntipisteestä.
- Näkee ostamansa lipun tiedot (tapahtuma, lipputyyppi, hinta, tarkistuskoodi). Lipussa näkyy myös ostopäivämäärä ja aika.
</details>


## Käyttötapaukset ja käyttäjätarinat
<details>
<summary> Lipputoimiston myyjä </summary>

### Lipputoimiston myyjä

<details>
<summary> Lipputoimiston myyjä- käyttötapaukset</summary>
</br>

- **Käyttötapaus 1: Tapahtumien tarkastelu**
  - **Tavoite:** Myyjä haluaa tarkastella tapahtumien lippuja, niiden tyyppejä ja hintoja.
  - **Toimet:** Myyjä navigoi järjestelmään, valitsee tarkasteltavan tapahtuman ja katsoo sen tiedot, kuten lipputyypit ja lippujen hinnat.
  - **Tulos:** Myyjä saa näkyviin valitun tapahtuman tiedot ja voi tarkastella sen ominaisuuksia.

- **Käyttötapaus 2: Lipun myynti**
    - **Tavoite:** Myyjä haluaa myydä lipun asiakkaalle.
    - **Toimet:** Myyjä valitsee tapahtuman, valitsee lipputyypin, syöttää asiakkaan tiedot, hyväksyy maksun ja tulostaa lipun.
    - **Tulos:** Asiakas saa lipun ja järjestelmä tallentaa myyntitapahtuman tiedot.

- **Käyttötapaus 3: Myyntiraporttien tarkastelu**
    - **Tavoite:** Myyjä haluaa tarkastella myymiään lippuja.
    - **Toimet:** Myyjä kirjautuu järjestelmään, valitsee ajanjakson ja tarkastelee raporttia omista myynneistään.
    - **Tulos:** Myyjä saa näkyviin raportin myymistään lipuista.
</details>

</details>

### Järjestelmän pääkäyttäjä

<details>
<summary> Järjestelmän pääkäyttäjä- käyttötapaukset </summary>
</br>

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

</details>

### Tapahtumajärjestäjä

<details>
<summary> Tapahtumajärjestäjä- käyttötapaukset </summary>
</br>

- **Käyttötapaus 1: Myyntiraportin luominen**
    - **Tavoite:** Tapahtumajärjestäjä haluaa tarkastella myyntiraporttia omasta tapahtumastaan.
    - **Toimet:** Tapahtumajärjestäjä valitsee tapahtuman ja aikajakson, luo raportin ja tulostaa sen.
    - **Tulos:** Tapahtumajärjestäjä saa raportin tapahtuman myynnistä.

- **Käyttötapaus 2: Myyntiraporttien tarkastelu**
    - **Tavoite:** Tapahtumajärjestäjä haluaa tarkastella myymiään lippuja.
    - **Toimet:** Tapahtumajärjestäjä kirjautuu järjestelmään, valitsee ajanjakson ja tarkastelee raporttia omista myynneistään.
    - **Tulos:** Tapahtumajärjestäjä saa näkyviin raportin myymistään lipuista.
</details>

</details>

### Asiakas

<details>
<summary> Asiakas- käyttötapaukset</summary>
</br>

- **Käyttötapaus 1: Lipun ostaminen**
    - **Tavoite:** Asiakas haluaa ostaa lipun tapahtumaan.
    - **Toimet:** Asiakas valitsee tapahtuman, valitsee lipputyypin, maksaa lipun ja saa tulostetun lipun.
    - **Tulos:** Asiakas saa lipun ja voi tarkistaa sen tiedot.

- **Käyttötapaus 2: Lippujen tarkastelu**
    - **Tavoite:** Asiakas haluaa tarkastella ostamansa lipun tietoja.
    - **Toimet:** Asiakas kirjautuu järjestelmään, valitsee lipun ja tarkastelee sen tietoja, kuten tapahtuman, lipputyypin, hinnan ja tarkistuskoodin.
    - **Tulos:** Asiakas näkee lipun tiedot ja voi varmistaa lipun oikeellisuuden.
</details>

## Käyttäjätarinat
<details> 
    <summary> Käyttäjätarinat </summary>

<details>
<summary>  Lista käyttäjätarinoista   </summary>
</br>

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
</details>

</details>

</br>

## Yksityiskohtaiset vaatimukset

- **Lippujen tulostaminen:** Liput tulostetaan standardikokoiselle paperille, ja lipussa on mukana QR-koodi tai viivakoodi tarkistamista varten.
- **Tietoturva:** Käyttäjän tiedot salataan ja tallennetaan turvallisesti.
</details>

<details>
<summary> Käyttöliittymävaatimukset </summary>

## Käyttöliittymävaatimukset

- **Myyjän käyttöliittymä:** Yksinkertainen ja selkeä käyttöliittymä, jossa on helppo navigoida tapahtumien ja lippujen välillä.
- **Raporttien tarkastelu:** Raportit esitetään visuaalisesti ymmärrettävällä tavalla, kuten taulukoina tai kaavioina.
</details>

<details>
<summary> Yhteenveto ja rajaukset </summary>

## Yhteenveto ja rajaukset

- **Sisältyvät toiminnot:** Lipun myynti, lippujen tulostaminen, myyntiraportit, käyttäjien hallinta.
- **Ei sisälly:** Verkkokauppatoiminnot (tulevaisuudessa mahdollisesti).
</details>

</br>

# Käyttöliittymä

<details>
<summary>Käyttöliittymässä on useita tärkeitä näkymiä, joiden avulla käyttäjät voivat suorittaa tarvitsemansa toiminnot. Tässä kuvataan nämä päänäkymät ja miten niissä liikutaan.</summary>

<details>
<summary> Etusivu </summary>
</br>

- **Miksi:** Etusivu toimii pääsivuna, josta käyttäjä pääsee kaikkiin tärkeimpiin osiin sovelluksessa.
- **Mitä:** Sivulla on linkit tapahtumien hallintaan, lipunmyyntiin ja raporttien tarkasteluun.
- **Siirtymiset:** Etusivulta käyttäjä pääsee helposti muihin näkymiin ja takaisin.

</details>

<details>

<summary> Tapahtumien hallinta </summary>
</br>

- **Miksi:** Täällä käyttäjä voi hallita tapahtumia, kuten lisätä, muokata ja poistaa niitä.
- **Mitä:** Näkymässä on lista tapahtumista, lomake uusille tapahtumille ja työkalut tapahtumien muokkaamiseen.
- **Siirtymiset:** Käyttäjä voi siirtyä etusivulta tapahtumien hallintaan ja takaisin etusivulle.
</details>

<details>
<summary> Lipunmyyntinäkymä </summary>
</br>

- **Miksi:** Tämä on myyjien työskentelynäkymä, jossa he voivat myydä lippuja asiakkaille.
- **Mitä:** Näkymässä valitaan tapahtuma, lipputyyppi, syötetään asiakastiedot ja maksetaan liput.
- **Siirtymiset:** Etusivulta käyttäjä pääsee lipunmyyntiin ja takaisin. Lipunmyyntitapahtumasta voi siirtyä myös myyntiraporttiin.
</details>


<details>
<summary> Myyntiraportit </summary>
</br>

- **Miksi:** Täällä käyttäjä voi tarkastella myyntiraportteja ja saada kokonaiskuvan myynnistä.
- **Mitä:** Näkymässä on raporttilistat, suodatusvaihtoehdot ja yksityiskohtaiset myyntitiedot.
- **Siirtymiset:** Raporttien tarkastelusta voi palata etusivulle.
</details>

<details>
<summary> Asiakasnäkymä </summary>
</br>

- **Miksi:** Asiakkaat voivat tarkastella ostamiaan lippuja ja tapahtumatietoja.
- **Mitä:** Näkymässä näkyvät ostetut liput, tapahtumatiedot ja tarkistuskoodi.
- **Siirtymiset:** Asiakas voi siirtyä asiakasnäkymään etusivulta ja palata takaisin etusivulle.
</details>


### Käyttöliittymäkaavio
- **Kaavio:** [Käyttöliittymäkaavio -linkki](https://docs.google.com/spreadsheets/d/1MQNqwOzjuIXldOeYIx_NevCTvQeL70HyKikxyzmMKN8/edit?gid=643351026#gid=643351026)
- **Miksi:** Käyttöliittymäkaavio näyttää, miten eri näkymät liittyvät toisiinsa ja miten käyttäjä navigoi niiden välillä.
</details>

## Tietokanta

### Tietokantakaavio
[Tietokantakaavio -linkki](https://docs.google.com/spreadsheets/d/1MQNqwOzjuIXldOeYIx_NevCTvQeL70HyKikxyzmMKN8/edit?gid=1081752884#gid=1081752884)

### Tietohakemisto
Tämä tietohakemisto kuvaa taulujen ja niiden attribuuttien tarkoituksen sekä roolin TicketGuru-sovelluksessa.
<details>
<summary> Tietohakemisto </summary>

### Event (Tapahtuma)

<details>
<summary>Tapahtumataulu sisältää tiedot järjestettävistä tapahtumista, joihin myydään lippuja. Yksi tapahtuma voi sisältää useita lippuja ja lipputyyppejä.</summary>
</br>

| Kenttä           | Tyyppi           | Kuvaus                                                  |
| -----------------|------------------| ------------------------------------------------------- |
| eventId          | int (AN) PK      | Tapahtuman yksilöllinen tunniste.                       |
| eventName        | varchar(50)      | Tapahtuman nimi.                                        |
| eventDate        | date             | Tapahtuman päivämäärä.                                  |
| eventAddress     | varchar(50)      | Tapahtuman osoite.                                      |
| eventCity        | varchar(50)      | Kaupunki, jossa tapahtuma järjestetään.                 |
| eventDescription | varchar(50)      | Lyhyt kuvaus tapahtumasta.                              |

</details>
</br>

### TicketType (Lipputyyppi)

<details>
<summary> Lipputyyppitaulu sisältää tiedot lipun erilaisista tyypeistä. Yksi lipputyyppi voi liittyä useisiin tapahtuman lipputyyppeihin. </summary>
</br>

| Kenttä            | Tyyppi           | Kuvaus                                                  |
| ------------------|------------------| ------------------------------------------------------- |
| ticketTypeId      | int (AN) PK      | Lipputyypin yksilöllinen tunniste.                      |
| name              | varchar(30)      | Lipputyypin nimi (esim. aikuinen, lapsi).               |

</details>
</br>

### EventTicketType (Tapahtuman lipputyyppi)

<details>
<summary>Tapahtuman lipputyyppitaulu sisältää tiedot tietyn tapahtuman lipputyypeistä, niiden jäljellä olevasta lippumäärästä sekä hinnasta. Jokainen tapahtuma voi sisältää useita lipputyyppejä. Lipputyypit ovat määritetty TicketType-taulussa ja eri tapahtumat Event-taulussa.</summary>
</br>

| Kenttä            | Tyyppi           | Kuvaus                                                   |
| ------------------|------------------| -------------------------------------------------------- |
| eventTicketTypeId | int (AN) PK      | Tapahtuman lipputyypin yksilöllinen tunniste.            |
| ticketTypeId      | int FK           | Viittaus lipputyyppiin (TicketType-taulu).               |
| eventId           | int FK           | Viittaus tapahtumaan (Event-taulu).                      |
| price             | double           | Lipputyypin hinta tietyssä tapahtumassa.                 |
| ticketsInStock    | int              | Tapahtuman lipputyypin jäljellä olevien lippujen määrä.  |

</details>
</br>

### Ticket (Lippu)

<details>
<summary>Lipputaulu sisältää tiedot myydyistä lipuista tiettyihin tapahtumiin. Yksi lippu kuuluu yhteen tapahtuman lipputyyppiin. Yhdessä myyntitapahtumassa voi olla useita lippuja.</summary>
</br>

| Kenttä            | Tyyppi           | Kuvaus                                                     |
| ------------------|------------------| ---------------------------------------------------------- |
| ticketId          | int (AN) PK      | Yksittäisen lipun tunniste.                                |
| eventTicketTypeId | int FK           | Viittaus tietyn tapahtuman lipputyyppiin (EventTicketType).|
| orderId           | int FK           | Viittaus myyntitapahtumaan (Order).                        |
| ticketCode        | varchar(30)      | Lipun yksilöllinen tarkistuskoodi (QR- tai viivakoodi).    |
| isValid           | boolean          | Indikaatio siitä, onko lippu vielä voimassa.               |

</details>
</br>

### OrderDetails (Tilauksen tiedot)

<details>
<summary>Tilauksen tiedot -taulu sisältää yksityiskohtaiset tiedot siitä, kuinka monta tietyn tapahtuman lipputyypin lippua ostetaan ja mikä yhden lipun hinta on ostohetkellä. Sama tilaus (Order) voi sisältää useita eri lipputyyppejä tapahtumiin.</summary>
</br>

| Kenttä           | Tyyppi           | Kuvaus                                                  |
| -----------------|------------------| --------------------------------------------------------|
| orderDetailId    | int (AN) PK      | Tilauksen yksityiskohtien tunniste.                     |
| orderId          | int FK           | Viittaus tilaukseen (Order).                            |
| eventTicketTypeId| int FK           | Viittaus tapahtuman lipputyyppiin (EventTicketType).    |
| unitPrice        | double           | Tilauksen määrä tiettyä tapahtuman lipputyyppiä.        |
| quantity         | int              | Lipun yksikköhinta tilauksen hetkellä.                  |

</details>
</br>

### Order (Tilaus)

<details>
<summary>Tilaus -taulu sisältää tiedot tiettyyn tilaukseen liittyvästä asiakkaasta ja myyjästä sekä päivämäärästä. Yhdessä tilauksessa voi olla useita eri tapahtumien lippuihin liittyviä ostoja.</summary>
</br>

| Kenttä            | Tyyppi           | Kuvaus                                                  |
| ------------------|------------------| --------------------------------------------------------|
| orderId           | int (AN) PK      | Tilauksen yksilöllinen tunniste.                        |
| customerId        | int FK           | Viittaus asiakkaaseen (Customer-taulu).                 |
| salespersonId     | int FK           | Viittaus myyjään (SalesPerson-taulu).                   |
| orderDate         | date             | Tilauksen päivämäärä.                                   |

</details>
</br>

### Customer (Asiakas)

<details>
<summary>Asiakastaulu sisältää tiedot asiakkaista, jotka ostavat lippuja. Yhdellä asiakkaalla voi olla useita tilauksia.</summary>
</br>

| Kenttä            | Tyyppi           | Kuvaus                                                  |
| ------------------|------------------| --------------------------------------------------------|
| customerId        | int (AN) PK      | Asiakkaan yksilöllinen tunniste.                        |
| username          | varchar(30)      | Asiakkaan käyttäjätunnus.                                |
| passwordHash      | varchar(30)      | Asiakkaan salasana.                                      |
| dateOfBirth       | date             | Asiakkaan syntymäaika.                                   |
| firstName         | varchar(30)      | Asiakkaan etunimi.                                      |
| lastName          | varchar(30)      | Asiakkaan sukunimi.                                     |
| phone             | varchar(30)      | Asiakkaan puhelinnumero.                                |
| email             | varchar(30)      | Asiakkaan sähköpostiosoite.                             |
| address           | varchar(30)      | Asiakkaan osoite.                                       |
| city              | varchar(30)      | Asiakkaan asuinpaikkakunta.                             |

</details>
</br>

### SalesPerson (Myyjä)

<details>
<summary>Myyjien tiedot sisältävä taulu, jossa säilytetään tietoa lipputoimiston työntekijöistä. Yksi myyjä voi käsitellä useita tilauksia.</summary>
</br>

| Kenttä            | Tyyppi           | Kuvaus                                                  |
| ------------------|------------------| --------------------------------------------------------|
| salesPersonId     | int (AN) PK      | Myyjän yksilöllinen tunniste.                           |
| username          | varchar(30)      | Myyjän käyttäjätunnus.                                  |
| passwordHash      | varchar(30)      | Myyjän salasana.                                        |
| lastName          | varchar(30)      | Myyjän sukunimi.                                        |
| firstName         | varchar(30)      | Myyjän etunimi.                                         |
| phone             | varchar(30)      | Myyjän puhelinnumero.                                   |

</details>
</br>

[Linkki tietokantakaavioon](https://docs.google.com/spreadsheets/d/1MQNqwOzjuIXldOeYIx_NevCTvQeL70HyKikxyzmMKN8/edit?gid=1081752884#gid=1081752884)
</details>

## REST API dokumentaatio

**Base URL: kehityksen aikana käytettävä base URL on http://localhost:8080/**


<details>
<summary>  Lisää tapahtuma (POST)</summary>

* Metodi: POST
* Polku: /event

Sisältö:

```
{
            "eventName": "Concert 1",
            "eventDate": "2024-10-01T05:08:30.651+00:00",
            "eventAddress": "Event Address 1",
            "eventCity": "Helsinki",
            "eventDescription": "A great concert event",
            "eventTicketTypes": [
                {
                    "ticketType": {
                        "id": 1,
                        "name": "Aikuinen"
                    },
                    "price": 10,
                    "ticketsInStock": 40
                }
            ]
        }

```
* Paluukoodi: 201 Created

```
{
    "eventId": 1,
    "eventName": "Concert 1",
    "eventDate": "2024-10-01T05:08:30.651+00:00",
    "eventAddress": "Event Address 1",
    "eventCity": "Helsinki",
    "eventDescription": "A great concert event",
    "eventTicketTypes": [
        {
            "id": 3,
            "price": 10.0,
            "ticketsInStock": 40
        }
    ]
}
```

* Virhekoodit:
    * 400 Bad Request: Pyynnössä oli virheellisiä tietoja (esim. puuttuvat tai väärän tyyppiset kentät).
    * 403 Forbidden: Käyttäjällä ei ole oikeuksia luoda tapahtumaa.

Sisältö:
```
{}
```
</details>

<details>
<summary> Muokkaa tapahtumaa (PUT) </summary>

* Metodi: PUT
* Polku: /event/{id}
* Polkuparametri:
    * id: Muokattavan tapahtuman yksilöivä tunnus

 Sisältö:   

```
   {
    "eventName": "Concert 1",
    "eventDate": "2024-10-01T09:43:35.689+00:00",
    "eventAddress": "Event Address 1",
    "eventCity": "Helsinki",
    "eventDescription": "A great concert event",
    "eventTicketTypes": [
        {
            "id": 1,
            "ticketType": {
                "id": 1,
                "name": "Aikuinen"
            },
            "price": 20.0,
            "ticketsInStock": 50
        },
        {
            "id": 2,
            "ticketType": {
                "id": 2,
                "name": "Lapsi"
            },
            "price": 10.0,
            "ticketsInStock": 60
        },
        {
            "id": 3,
            "ticketType": {
                "id": 3,
                "name": "VIP"
            },
            "price": 100.0,
            "ticketsInStock": 15
        }
    ]
}

```
* Paluukoodi: 200 OK

```
Sisältö: 
{
    "eventId": 1,
    "eventName": "Concert 1",
    "eventDate": "2024-10-01T09:43:35.689+00:00",
    "eventAddress": "Updated Event Address 1",
    "eventCity": "Updated City",
    "eventDescription": "Updated description",
    "eventTicketTypes": [
        {
            "id": 1,
            "ticketType": {
                "id": 1,
                "name": "Aikuinen"
            },
            "price": 20.0,
            "ticketsInStock": 50
        },
        {
            "id": 2,
            "ticketType": {
                "id": 2,
                "name": "Lapsi"
            },
            "price": 10.0,
            "ticketsInStock": 60
        },
        {
            "id": 3,
            "ticketType": {
                "id": 3,
                "name": "VIP"
            },
            "price": 100.0,
            "ticketsInStock": 15
        }
    ]
}

```

* Virhekoodit:
    * 400 Bad Request: Pyynnössä oli virheellisiä tietoja.
    * 403 Forbidden: Käyttäjällä ei ole oikeuksia muokata tätä tapahtumaa.
    * 404 Not Found: Tapahtumaa annetulla id:ia ei löytynyt.

Sisältö:
```
{}
```

</details>

<details>
<summary> Hae tapahtuma (GET) </summary>

* Metodi: GET
* Polku: /events/{id}
* Polkuparametri:
    * id: Haettavan tapahtuman yksilöivä tunnus
* Paluukoodi: 200 OK

Vastaus:

```
{
    "eventId": 1,
    "eventName": "Concert 1",
    "eventDate": "2024-10-20T11:56:35.830+00:00",
    "eventAddress": "Event Address 1",
    "eventCity": "Helsinki",
    "eventDescription": "A great concert event",
    "eventTicketTypes": [
        {
            "id": 1,
            "price": 20.0,
            "ticketsInStock": 48
        },
        {
            "id": 2,
            "price": 15.0,
            "ticketsInStock": 99
        }
    ]
}

```

* Query-parametrit ovat valinnaisia. Niitä käytetään hakujen suodattamiseen:

* **location:** Palauttaa vain ne tapahtumat, jotka järjestetään määritetyssä sijainnissa.
    * Esimerkki: /events?location=Helsinki
* **date**: Palauttaa vain ne tapahtumat, jotka järjestetään määritettynä päivämääränä.
    * Esimerkki: /events?date=2024-09-28
* **location** ja date voidaan yhdistää, jotta haetaan vain tietyn sijainnin tapahtumat tiettynä päivänä.
    * Esimerkki: /events?location=Helsinki&date=2024-09-28


* Virhekoodit:
    * 404 Not Found: Tapahtumaa annetulla query -parametrilla ei löytynyt

Sisältö:

```
{}
```
</details>

<details>
<summary> Poista tapahtuma (DELETE) </summary>

* Metodi: DELETE
* Polku: /event/{id}
*  Polkuparametri:
    * id: Poistettavan tapahtuman yksilöivä tunnus
* Paluukoodi: 204 No Content
Sisältö:
```
{}
```
* Virhekoodit:
    * 404 Not Found: Tapahtumaa annetulla query -parametrilla ei löytynyt
    * 403 Forbidden: Käyttäjällä ei ole oikeuksia poistaa tätä tapahtumaa.

Sisältö:

```
{}
```
</details>

## Myyntitapahtumien API-dokumentaatio


<details>
<summary> Lisää myyntitapahtuma (POST) </summary>

### Lisää myyntitapahtuma (POST)

* **Metodi**: POST
* **Polku**: `/orders`

#### Pyynnön sisältö:

```json
{
  "customerId": 1,
  "salespersonId": 1,
  "orderDetails": [
    {
      "eventTicketTypeId": 1,
      "quantity": 2,
      "unitPrice": 20.0
    },
    {
      "eventTicketTypeId": 2,
      "quantity": 1,
      "unitPrice": 15.0
    }
  ]
}
```

#### Paluukoodi:

- **201 Created**

#### Vastaus:

```json
{
  "customerId": 1,
  "salespersonId": 1,
  "orderDetails": [
    {
      "eventTicketTypeId": 1,
      "quantity": 2,
      "unitPrice": 20.0
    },
    {
      "eventTicketTypeId": 2,
      "quantity": 1,
      "unitPrice": 15.0
    }
  ],
  "orderId": 1,
  "customerFirstName": "John",
  "customerLastName": "Doe",
  "salespersonFirstName": "Peter",
  "salespersonLastName": "Smith",
  "orderDate": "2024-10-18T16:29:01.067+00:00"
}
```

#### Virhekoodit:

- **400 Bad Request**: Pyynnössä oli virheellisiä tietoja (esim. puuttuvat tai väärän tyyppiset kentät).
  - *Esimerkki*: Puuttuva `customerId`:
    ```json
    [
      "OrderDTO: Customer ID is required"
    ]
    ```
  - *Esimerkki*: Virheellinen `quantity` (nolla tai negatiivinen):
    ```json
    {
      "error": "Order quantity must be greater than 0"
    }
    ```
- **404 Not Found**: Pyydettyä resurssia (asiakas, myyjä, tapahtuma tai lipputyyppi) ei löytynyt.
  - *Esimerkki*: Virheellinen `customerId`:
    ```json
    {
      "error": "Customer with ID X not found"
    }
    ```
- **409 Conflict**: Yritettiin tehdä tilaus lipuista, joita ei ole varastossa.
  - *Esimerkki*: Tilaus ylittää varastosaldon:
    ```json
    {
      "error": "Not enough tickets in stock"
    }
    ```

</details>

<details>
<summary> Hae myyntitapahtuma (GET)</summary>

### Hae myyntitapahtuma (GET)

* **Metodi**: GET
* **Polku**: `/orders/{orderId}`
* **Polkuparametri**:
  - `orderId`: Haettavan tilauksen yksilöivä tunnus

#### Paluukoodi:

- **200 OK**

#### Vastaus:

```json
{
  "customerId": 1,
  "salespersonId": 1,
  "orderDetails": [
    {
      "eventTicketTypeId": 1,
      "quantity": 2,
      "unitPrice": 20.0
    },
    {
      "eventTicketTypeId": 2,
      "quantity": 1,
      "unitPrice": 15.0
    }
  ],
  "orderId": 1,
  "customerFirstName": "John",
  "customerLastName": "Doe",
  "salespersonFirstName": "Peter",
  "salespersonLastName": "Smith",
  "orderDate": "2024-10-18T16:29:01.067+00:00"
}
```

#### Virhekoodit:

- **404 Not Found**: Tilausta annetulla `orderId`:llä ei löytynyt.
  - *Vastaus*:
    ```json
    {
      "error": "Order with ID 99999 not found"
    }
    ```

</details>

<details>
<summary> Hae kaikki myyntitapahtumat (GET)</summary>

### Hae kaikki myyntitapahtumat (GET)

* **Metodi**: GET
* **Polku**: `/orders`

#### Paluukoodi:

- **200 OK**

#### Vastaus:

```json
[
  {
    "customerId": 1,
    "salespersonId": 1,
    "orderDetails": [
      {
        "eventTicketTypeId": 1,
        "quantity": 2,
        "unitPrice": 20.0
      },
      {
        "eventTicketTypeId": 2,
        "quantity": 1,
        "unitPrice": 15.0
      }
    ],
    "orderId": 1,
    "customerFirstName": "John",
    "customerLastName": "Doe",
    "salespersonFirstName": "Peter",
    "salespersonLastName": "Smith",
    "orderDate": "2024-10-18T16:29:01.067+00:00"
  },
  {
    "customerId": 2,
    "salespersonId": 2,
    "orderDetails": [
      {
        "eventTicketTypeId": 1,
        "quantity": 2,
        "unitPrice": 20.0
      },
      {
        "eventTicketTypeId": 2,
        "quantity": 5,
        "unitPrice": 15.0
      }
    ],
    "orderId": 2,
    "customerFirstName": "Jane",
    "customerLastName": "Doe",
    "salespersonFirstName": "Anna",
    "salespersonLastName": "Brown",
    "orderDate": "2024-10-18T16:29:05.538+00:00"
  }
]
```

</details>

<details>
<summary> Muokkaa myyntitapahtumaa (PUT)</summary>

### Muokkaa myyntitapahtumaa (PUT)

* **Metodi**: PUT
* **Polku**: `/orders/{orderId}`
* **Polkuparametri**:
  - `orderId`: Muokattavan tilauksen yksilöivä tunnus

#### Pyynnön sisältö:

Voit päivittää `customerId` ja `salespersonId`, mutta **`orderDetails`-kenttää ei voi muokata** tilauksen luomisen jälkeen.

*Esimerkki*:

```json
{
  "customerId": 2,
  "salespersonId": 1
}
```

#### Paluukoodi:

- **200 OK**

#### Vastaus:

```json
{
  "customerId": 2,
  "salespersonId": 1,
  "orderDetails": [
    {
      "eventTicketTypeId": 1,
      "quantity": 2,
      "unitPrice": 20.0
    },
    {
      "eventTicketTypeId": 2,
      "quantity": 5,
      "unitPrice": 15.0
    }
  ],
  "orderId": 2,
  "customerFirstName": "Jane",
  "customerLastName": "Doe",
  "salespersonFirstName": "Peter",
  "salespersonLastName": "Smith",
  "orderDate": "2024-10-18T16:29:05.538+00:00"
}
```

#### Virhekoodit:

- **400 Bad Request**: Yritettiin muokata `orderDetails`-kenttää, mikä ei ole sallittua.
  - *Vastaus*:
    ```json
    {
      "error": "Modifying order details is not allowed after order creation."
    }
    ```
- **404 Not Found**: Tilausta annetulla `orderId`:llä ei löytynyt.

</details>

<details>
<summary> Päivitä myyntitapahtumaa osittain (PATCH)</summary>

### Päivitä myyntitapahtumaa osittain (PATCH)

* **Metodi**: PATCH
* **Polku**: `/orders/{orderId}`
* **Polkuparametri**:
  - `orderId`: Muokattavan tilauksen yksilöivä tunnus

#### Pyynnön sisältö:

Voit päivittää yhden tai useamman seuraavista kentistä: `customerId`, `salespersonId`.

*Esimerkki*:

```json
{
  "salespersonId": 2
}
```

#### Paluukoodi:

- **200 OK**

#### Vastaus:

```json
{
  "customerId": 2,
  "salespersonId": 2,
  "orderDetails": [
    {
      "eventTicketTypeId": 1,
      "quantity": 2,
      "unitPrice": 20.0
    },
    {
      "eventTicketTypeId": 2,
      "quantity": 5,
      "unitPrice": 15.0
    }
  ],
  "orderId": 2,
  "customerFirstName": "Jane",
  "customerLastName": "Doe",
  "salespersonFirstName": "Anna",
  "salespersonLastName": "Brown",
  "orderDate": "2024-10-18T16:29:05.538+00:00"
}
```

#### Virhekoodit:

- **400 Bad Request**: Yritettiin muokata `orderDetails`-kenttää, mikä ei ole sallittua.
  - *Vastaus*:
    ```json
    {
      "error": "Modifying order details is not allowed after order creation."
    }
    ```
- **404 Not Found**: Tilausta annetulla `orderId`:llä ei löytynyt.

</details>

<details>
<summary> Poista myyntitapahtuma (DELETE) </summary>

### Poista myyntitapahtuma (DELETE)

* **Metodi**: DELETE
* **Polku**: `/orders/{orderId}`
* **Polkuparametri**:
  - `orderId`: Poistettavan tilauksen yksilöivä tunnus

#### Paluukoodi:

- **204 No Content**

#### Virhekoodit:

- **404 Not Found**: Tilausta annetulla `orderId`:llä ei löytynyt.

</details>

<details>
<summary> Huomioita </summary>

### Huomioita

- **OrderDTO** sisältää seuraavat kentät:

  - `customerId` (pakollinen): Asiakkaan tunniste.
  - `salespersonId` (pakollinen): Myyjän tunniste.
  - `orderDetails` (pakollinen tilauksen luomisessa): Lista tilauksen yksityiskohdista.
  - `orderId`: Tilauksen tunniste (vastauksessa).
  - `customerFirstName`: Asiakkaan etunimi (vastauksessa).
  - `customerLastName`: Asiakkaan sukunimi (vastauksessa).
  - `salespersonFirstName`: Myyjän etunimi (vastauksessa).
  - `salespersonLastName`: Myyjän sukunimi (vastauksessa).
  - `orderDate`: Tilauksen päivämäärä (vastauksessa).

- **OrderDetailsDTO** sisältää seuraavat kentät:

  - `eventTicketTypeId` (pakollinen): Tapahtuman lipputyypin tunniste.
  - `quantity` (pakollinen, positiivinen kokonaisluku): Lipun määrä.
  - `unitPrice` (pakollinen): Yksikköhinta.

- **Tilauksen muokkaus**:

  - Tilauksen luomisen jälkeen **`orderDetails`-kenttää ei voi muokata**. Yritettäessä muokata sitä, saadaan virhe:
    ```json
    {
      "error": "Modifying order details is not allowed after order creation."
    }
    ```

- **Virheiden käsittely**:

  - Virheet palautetaan JSON-muodossa, jossa on `error`-kenttä ja virheviesti.
  - Validointivirheet voivat palauttaa listan virheistä:
    ```json
    [
      "OrderDTO: Customer ID is required",
      "OrderDetailsDTO: Quantity must be greater than 0"
    ]
    ```

- **Varastosaldot**:

  - Tilauksia ei voi tehdä, jos liput ovat loppuneet varastosta. Yritettäessä tehdään virhe:
    ```json
    {
      "error": "Not enough tickets in stock"
    }
    ```

- **Käyttäjäoikeudet**:

  - API ei tällä hetkellä käsittele käyttäjäoikeuksia (kuten `403 Forbidden` -virhettä). Tämä voidaan lisätä myöhemmin, jos tarvitaan.
</details>
<br/>

# Autentikaatio

<details>
<summary> Autentikointiprosessi </summary>
<br/>

TicketGuru-sovelluksessa käytetään perinteistä käyttäjätunnus-salasana -autentikaatiota. Sovelluksen käyttäjät jaetaan kahteen pääasialliseen rooliin: **asiakkaat** ja **myyjät** (Salesperson), joilla on eri oikeudet ja pääsyoikeudet sovelluksen eri toimintoihin.

## Käyttäjätiedot

Jokainen sovelluksen käyttäjä tallennetaan tietokantaan `Customer` tai `Salesperson` -entiteetteinä. Käyttäjätunnukset ja salasanat tallennetaan tietokantaan seuraavasti:

### Customer (Asiakas)
- **customerId**: Asiakkaan yksilöllinen ID
- **username**: Asiakkaan käyttäjätunnus
- **passwordHash**: Salasanan hajautusarvo (hash), joka takaa tietoturvan.
- **firstName**: Asiakkaan etunimi.
- **lastName**: Asiakkaan sukunimi.
- **phone**: Puhelinnumero.
- **email**: Sähköpostiosoite.

### Salesperson (Myyjä)
- **salespersonId**: Myyjän yksilöllinen ID.
- **username**: Myyjän käyttäjätunnus.
- **passwordHash**: Salasanan hajautusarvo.
- **isAdmin**: Boolean-arvo, joka määrittää onko myyjä järjestelmän pääkäyttäjä (admin).

### Salasanan tallennus

Salasana tallennetaan tietokantaan hajautettuna, eli se ei ole selkokielinen. Tämä toteutetaan hyödyntäen Java Spring Security -komponentteja, jotka varmistavat salasanan turvallisen tallennuksen ja tarkastamisen.

```java
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String hashedPassword = encoder.encode("salasana123");
```

</details>
