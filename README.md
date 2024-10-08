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
<summary>Tapahtumataulu sisältää tiedot järjestettävistä tapahtumista, joihin myydään lippuja. Yksi tapahtuma voi sisältää useita lippuja</summary>
</br>

| Kenttä            | Tyyppi           | Kuvaus                                                  |
| ------------------|----------------  | ------------------------------------------------------- |
| Even_id           | int(AN) PK       | Tapahtuman yksilöllinen tunniste.                       |
| Event_name        | varchar(50)      | Tapahtuman nimi.                                        |
| Event_date        | date             | Tapahtuman päivämäärä.                                  |
| Event_address     | varchar(50)      | Tapahtuman osoite.                                      |
| Event_city        | varchar(50)      | Kaupunki, jossa tapahtuma järjestetään.                 |
| Event_description | varchar(50)      | Lyhyt kuvaus tapahtumasta.                              |

</details>
</br>

### Ticket (Lippu)

<details>
<summary>Lipputaulu sisältää tiedot myydyistä lipuista tiettyihin tapahtumiin.  Yksi lippu kuuluu yhteen tapahtumaan ja yhteen lipputyyppiin . Yksi lippu voi kuulua useaan tilaukseen.</summary>
</br>

| Kenttä            | Tyyppi           | Kuvaus                                                  |
| ------------------|------------------| ------------------------------------------------------- |
| Ticket_id         | int(AN) PK       | Yksittäisen lipun tunniste.                             |
| Ticket_Typeid     | int FK           | Viittaus lipun tyyppiin. (TicketType-taulu)             |
| Event_id          | int FK           | Viittaus tapahtumaan, johon lippu kuuluu. (Event-taulu) |
| TotalQuantity     | int              | Lippujen kokonaismäärä.                                 |
| TicketsInStock    | int              | Jäljellä olevien lippujen määrä varastossa.             |
| TicketCode        | varchar(30)      | Lipun tarkistuskoodi (QR- tai viivakoodi).              |
| TicketIsUsed      | boolean          | Indikaatio siitä, onko lippu käytetty.                  |

</details>
</br>

### TicketType (Lipputyyppi)

<details>
<summary> Lipputyyppitaulu sisältää tiedot lipun erilaisista hinnoista ja tyypeistä. Yksi lipputyyppi voi liittyä useisiin lippuihin. </summary>
</br>

| Kenttä            | Tyyppi           | Kuvaus                                                  |
| ------------------|------------------| ------------------------------------------------------- |
| TicketType_id     | int(AN) PK       | Lipputyypin yksilöllinen tunniste.                      |
| Type_name         | varchar(30)      | Lipputyypin nimi (esim. aikuinen, lapsi).               |
| type_price        | double           | Lipputyypin hinta.                                      |

</details>
</br>

### EventTicketType (Tapahtuman lipputyyppi)


<details>
<summary>Tapahtuman lipputyyppitaulu sisältää tiedot tapahtuman lipputyypeistä ja niiden määristä. Jokainen tapahtuma voi sisältää useita lipputyyppejä. Lipputyypit ovat määritetty TicketType-taulussa ja viitattu tähän tapahtuman lippukohtaisilla määrillä ja hinnoilla. </summary>
</br>

| Kenttä            | Tyyppi           | Kuvaus                                                  |
| ------------------|------------------| --------------------------------------------------------|
| EventTicketType_id | int(AN) PK       | Tapahtuman lipputyypin yksilöllinen tunniste.            |
| TicketType_id      | int FK           | Viittaus lipputyyppiin (TicketType-taulu).               |
| Event_id           | int FK           | Viittaus tapahtumaan (Event-taulu).                      |
| Price              | double           | Lipputyypin hinta tapahtumassa.                          |
| TotalQuantity      | int              | Tapahtuman lipputyypin lippujen kokonaismäärä.           |
| TicketsInStock     | int              | Tapahtuman lipputyypin jäljellä olevien lippujen määrä.  |

</details>
</br>

### OrderDetails (Tilauksen tiedot)

<details>
<summary>
Tilausrivien taulu sisältää yksityiskohtaiset tiedot yksittäisistä lipuista, jotka kuuluvat tilauksiin. Yksi tilaus voi sisältää useita lippuja
</summary>
</br>

| Kenttä            | Tyyppi           | Kuvaus                                                  |
| ------------------|------------------| --------------------------------------------------------|
| OrderDetail_id    | int(AN)          | Tilauksen yksityiskohtien tunniste.                     |
| Order_id          | int FK           | Viittaus tilaukseen (Order-taulu).                      |
| Ticket_id         | int FK           | Viittaus lippuun (Ticket-taulu).                        |
| UnitPrice         | double           | Lipun yksikköhinta tilauksen hetkellä.                  |

</details>
</br>

### Customer

<details>
<summary>
Asiakastaulu sisältää tiedot asiakkaista, jotka ostavat lippuja. Yhdellä asiakkaalla voi olla useita tilauksia.
</summary>
</br>


| Kenttä            | Tyyppi           | Kuvaus                                                  |
| ------------------|------------------| --------------------------------------------------------|
| Customer-id       | int(AN) PK       | Asiakkaan yksilöllinen tunniste.                        |
| Cust_lastName     | varchar(30)      | Asiakkaan sukunimi.                                     |
| Cust_firsttName   | varchar(30)      | Asiakkaan etunimi.                                      |
| Cust_phone        | varchar(30)      | Asiakkaan puhelinnumero.                                |
| Cust_email        | varchar(30)      | Asiakkaan sähköpostiosoite.                             |
| Cust_address      | varchar(30)      | Asiakkaan osoite.                                       |
| Cust_City         | varchar(30)      | Asiakkaan asuinpaikkakunta.                             |

</details>
</br>

### Order

<details>
<summary>
Tilaustaulu sisältää tiedot asiakkaiden tekemistä lippuostoista. Yksi asiakas voi tehdä useita tilauksia. Yhdessä tilauksessa voi olla useita lippuja.
</summary>
</br>


| Kenttä            | Tyyppi           | Kuvaus                                                  |
| ------------------|------------------| --------------------------------------------------------|
| Order_id          | int (AN) PK      | Tilauksen yksilöllinen tunniste.                        |
| Customer_id       | int FK           | Viittaus tilaajaan (Customer-taulu).                    |
| SalesPerson_id    | int FK           | Viittaus myyjään (SalesPerson-taulu).                   |
| OrderDate         | date             | Tilauksen päivämäärä.                                   |

</details>
</br>

### SalesPerson

<details>
<summary>
Myyjien tiedot sisältävä taulu, jossa säilytetään tietoa lipputoimiston työntekijöistä. Yksi myyjä voi käsitellä useita tilauksia.
</summary>

| Kenttä            | Tyyppi           | Kuvaus                                                  |
| ------------------|------------------| --------------------------------------------------------|
| SalesPerson_id    | int (AN) PK      | Myyjän yksilöllinen tunniste.                           |
| SalesP_lastName   | varchar(30)      | Myyjän sukunimi.                                        |
| SalesP_firstName  | varchar(30)      | Myyjän etunimi.                                         |
| SalesP_phone      | varchar(30)      | Myyjän puhelinnumero.                                   |

</details>
</br>

[Linkki tietokantakaavioon](https://docs.google.com/spreadsheets/d/1MQNqwOzjuIXldOeYIx_NevCTvQeL70HyKikxyzmMKN8/edit?gid=1081752884#gid=1081752884)


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
    "eventTicketTypes": [ {"ticketType":{ "id":1, "name": "aikuinen"}, "price": 10, "ticketsInStock": 40}]
}

```
* Paluukoodi: 201 Created

```
{
    "eventId": 5,
    "eventName": "Concert 4",
    "eventDate": "2024-10-01T05:08:30.651+00:00",
    "eventAddress": "Event Address 1",
    "eventCity": "Helsinki",
    "eventDescription": "A great concert event",
    "eventTicketTypes": [
        {
            "id": 10,
            "ticketType": {
                "id": 1,
                "name": "aikuinen"
            },
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
* Polku: /event/{id}
* Polkuparametri:
    * id: Haettavan tapahtuman yksilöivä tunnus
* Paluukoodi: 200 OK

Vastaus:

```
{
    "eventId": 1,
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
<details>
<summary>  Lisää myyntitapahtuma (POST) </summary>

* Metodi: POST
* Polku: /order

Sisältö:

```
{
    "customer": {
        "id": 1,
        "firstName": "John",
        "lastName": "Doe",
        "email": "john@example.com"
    },
    "salesPerson": {
        "id": 1,
        "firstName": "Sales",
        "lastName": "Person"
    },
    "orderDate": "2024-10-01T10:00:00.000+00:00",
    "orderDetails": [
        {
            "ticket": {
                "id": 1,
                "ticketType": {
                    "id": 1,
                    "name": "Aikuinen"
                },
                "eventTicketType": {
                    "id": 1,
                    "price": 10.0
                }
            },
            "unitPrice": 10.0,
            "quantity": 2
        },
        {
            "ticket": {
                "id": 2,
                "ticketType": {
                    "id": 2,
                    "name": "Lapsi"
                },
                "eventTicketType": {
                    "id": 2,
                    "price": 5.0
                }
            },
            "unitPrice": 5.0,
            "quantity": 3
        }
    ]
}
```

* Paluukoodi: 201 Created

```
{
    "orderId": 1,
    "customer": {
        "id": 1,
        "firstName": "John",
        "lastName": "Doe",
        "email": "john@example.com"
    },
    "salesPerson": {
        "id": 1,
        "firstName": "Sales",
        "lastName": "Person"
    },
    "orderDate": "2024-10-01T10:00:00.000+00:00",
    "orderDetails": [
        {
            "ticket": {
                "id": 1,
                "ticketType": {
                    "id": 1,
                    "name": "Aikuinen"
                },
                "eventTicketType": {
                    "id": 1,
                    "price": 10.0
                }
            },
            "unitPrice": 10.0,
            "quantity": 2
        },
        {
            "ticket": {
                "id": 2,
                "ticketType": {
                    "id": 2,
                    "name": "Lapsi"
                },
                "eventTicketType": {
                    "id": 2,
                    "price": 5.0
                }
            },
            "unitPrice": 5.0,
            "quantity": 3
        }
    ]
}
```

* Virhekoodit:
    * 400 Bad Request: Pyynnössä oli virheellisiä tietoja (esim. puuttuvat tai väärän tyyppiset kentät).
    * 404 Not Found: Pyydettyä resurssia (asiakas, myyjä, tapahtuma tai lippu) ei löytynyt.
    * 403 Forbidden: Käyttäjällä ei ole oikeuksia luoda myyntitapahtumaa.
    * 409 Conflict: Yritettiin tehdä tilaus lipuista, joita ei ole varastossa.

Sisältö:
```
{}
```
</details>

<details>
<summary> Muokkaa myyntitapahtumaa (PUT) </summary>

* Metodi: PUT
* Polku: /order/{id}
* Polkuparametri:
    * id: Muokattavan tilauksen yksilöivä tunnus

 Sisältö:   

```
{
    "customer": {
        "id": 1,
        "firstName": "John",
        "lastName": "Doe",
        "email": "john@example.com"
    },
    "salesPerson": {
        "id": 1,
        "firstName": "Sales",
        "lastName": "Person"
    },
    "orderDate": "2024-10-01T10:00:00.000+00:00",
    "orderDetails": [
        {
            "ticket": {
                "id": 1,
                "ticketType": {
                    "id": 1,
                    "name": "Aikuinen"
                },
                "eventTicketType": {
                    "id": 1,
                    "price": 10.0
                }
            },
            "unitPrice": 10.0,
            "quantity": 2
        }
    ]
}
```

* Paluukoodi: 200 OK

```
{
    "orderId": 1,
    "customer": {
        "id": 1,
        "firstName": "John",
        "lastName": "Doe",
        "email": "john@example.com"
    },
    "salesPerson": {
        "id": 1,
        "firstName": "Sales",
        "lastName": "Person"
    },
    "orderDate": "2024-10-01T10:00:00.000+00:00",
    "orderDetails": [
        {
            "ticket": {
                "id": 1,
                "ticketType": {
                    "id": 1,
                    "name": "Aikuinen"
                },
                "eventTicketType": {
                    "id": 1,
                    "price": 10.0
                }
            },
            "unitPrice": 10.0,
            "quantity": 2
        }
    ]
}
```

* Virhekoodit:
    * 400 Bad Request: Pyynnössä oli virheellisiä tietoja.
    * 403 Forbidden: Käyttäjällä ei ole oikeuksia muokata tätä tilausta.
    * 404 Not Found: Tilausta annetulla id:llä ei löytynyt.
    * 409 Conflict: Yritettiin tehdä muokkaus, joka rikkoisi varastosaldon.

Sisältö:
```
{}
```

</details>

<details>
<summary> Hae myyntitapahtuma (GET) </summary>

* Metodi: GET
* Polku: /order/{id}
* Polkuparametri:
    * id: Haettavan tilauksen yksilöivä tunnus
* Paluukoodi: 200 OK

Vastaus:

```
{
    "orderId": 1,
    "customer": {
        "id": 1,
        "firstName": "John",
        "lastName": "Doe",
        "email": "john@example.com"
    },
    "salesPerson": {
        "id": 1,
        "firstName": "Sales",
        "lastName": "Person"
    },
    "orderDate": "2024-10-01T10:00:00.000+00:00",
    "orderDetails": [
        {
            "ticket": {
                "id": 1,
                "ticketType": {
                    "id": 1,
                    "name": "Aikuinen"
                },
                "eventTicketType": {
                    "id": 1,
                    "price": 10.0
                }
            },
            "unitPrice": 10.0,
            "quantity": 2
        }
    ]
}
```

* Virhekoodit:
    * 404 Not Found: Tilausta annetulla id:llä ei löytynyt.

Sisältö:
```
{}
```
</details>


<details>
<summary> Poista myyntitapahtuma (DELETE) </summary>

* Metodi: DELETE
* Polku: /order/{id}
* Polkuparametri:
    * id: Poistettavan tilauksen yksilöivä tunnus
* Paluukoodi: 204 No Content

Sisältö:
```
{}
```

* Virhekoodit:
    * 404 Not Found: Tilausta annetulla id:llä ei löytynyt.
    * 403 Forbidden: Käyttäjällä ei ole oikeuksia poistaa tätä tilausta.

Sisältö:
```
{}
```

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
