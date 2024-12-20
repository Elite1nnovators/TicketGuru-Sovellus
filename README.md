# TicketGuru-Sovellus

[TicketGuru backlog -linkki](https://docs.google.com/spreadsheets/d/1MQNqwOzjuIXldOeYIx_NevCTvQeL70HyKikxyzmMKN8/edit?gid=0#gid=0)

# Sisällysluettelo

1. **[Johdanto](#johdanto)**

2. **[Järjestelmän määrittely](#järjestelmän-määrittely)**
   - [Käyttäjäryhmät (roolit)](#käyttäjäryhmät-roolit)
   - [Käyttötapaukset ja käyttäjätarinat](#käyttötapaukset-ja-käyttäjätarinat)

3. **[Käyttöliittymä](#käyttöliittymä)**
   - [Käyttöliittymäkaavio](#käyttöliittymäkaavio)
   - [Käyttöliittymän näkymät](#käyttöliittymän-näkymät)

4. **[Tietokanta](#tietokanta)**
   - [Tietokantakaavio](#tietokantakaavio)
   - [Tietohakemisto](#tietohakemisto)

5. **[REST API dokumentaatio](#rest-api-dokumentaatio)**
   - [Myyntitapahtumien API-dokumentaatio](#myyntitapahtumien-api-dokumentaatio)
   - [Lippujen (Ticket) API-pyynnöt](#lippujen-ticket-api-pyynnöt)
   - [Lipputyyppien (TicketType) API-pyynnöt](#lipputyyppien-tickettype-api-pyynnöt)

6. **[Autentikaatio](#autentikaatio)**
   - [Perustason autentikointi](#perustason-autentikointi-basic-authentication)

7. **[Testaus](#testaus)**
   - [Database Access Layer](#database-access-layer)
     - [Yhteyden toimivuus (DAL ↔ RDBMS)](#yhteyden-toimivuus-dal-rdbms)
     - [SQL-kyselyjen oikeellisuus](#sql-kyselyjen-oikeellisuus)
     - [SQL-kyselyjen suorituskyky](#sql-kyselyjen-suorituskyky)
     - [Transaktioiden hallinta](#transaktioiden-hallinta)
     - [Tietokannan konsistenssi ja eheys](#tietokannan-konsistenssi-ja-eheys)
     - [Skalautuvuus](#skalautuvuus)


# Johdanto
Projektin tavoitteena on luoda lipunmyyntijärjestelmä (TicketGuru) lipputoimistolle, joka myy lippuja erilaisiin tapahtumiin omassa myyntipisteessään. Toistaiseksi myynti tapahtuu vain paikan päällä lipputoimistossa, mutta myöhemmin mahdollisesti myös verkkokaupan kautta. Järjestelmän tarkoituksena on, että lipputoimisto pystyy lipunmyynnin lisäksi hallinnoimaan ja tarkastelemaan myyntitapahtumia.

Järjestelmä näyttää eri tapahtumiin tarjolla olevat lipputyypit ja niiden hinnat. Jokaisen myyntitapahtuman yhteydessä lippuihin tulostuu tarkastuskoodi. Sen avulla lippu voidaan merkitä käytetyksi ovella, kun asiakas tulee tapahtumaan. Lipputoimiston myyntipisteessä myydyt liput tulee voida tulostaa paperisena asiakkaille. Lipputoimiston asiakkaana on tapahtumajärjestäjiä, joille pystytään tapahtumakohtaisesti luomaan järjestelmästä myyntiraportteja. Raporteista nähdään kokonaismyynti myydyistä lipuista lipputyypeittäin sekä tarkempi erottelu jokaisesta erillisestä myyntitapahtumasta. 

Järjestelmä toteutetaan Spring Boot -menetelmän avulla Java-ohjelmointikieltä käyttäen. Järjestelmän palvelinratkaisuina pyritään käyttämään teknologiaa, joka mahdollistaa järjestelmän luotettavuuden, tietoturvallisen tiedonkäsittelyn ja sujuvan käytettävyyden sen kaikille käyttäjille. Järjestelmän frontend on toteutettu Reactilla Vite -projektina.

Käyttöliittymä on suunniteltu ensisijaisesti käytettäväksi pöytätietokoneilla ja läppäreillä, joita lipputoimistossa on käytössä. Kuitenkin lippujen tarkastuksen yhteydessä järjestelmää tulisi voida käyttää myös puhelimen avulla, jotta lippujen tarkastaminen on sujuvampaa.

</br>

# Järjestelmän määrittely

Järjestelmän määrittelyssä tarkastellaan TicketGuru-sovellusta käyttäjän näkökulmasta. Tämä osio keskittyy kuvaamaan, millaisia käyttäjäryhmiä (rooleja) sovelluksessa on ja millaisia toimintoja kullakin roolilla on käytettävissään. Lisäksi tässä osiossa kuvataan käyttäjätarinat, joiden avulla selvitetään käyttäjien tarpeet ja odotukset. Tavoitteena on varmistaa, että sovellus täyttää käyttäjien ja organisaation vaatimukset, ja tarjoaa sujuvan, tehokkaan ja turvallisen käyttökokemuksen kaikille osapuolille.

## Käyttäjäryhmät (roolit)
<details>
<summary> Lipputoimiston myyjä (salesperson)</summary>


### Lipputoimiston myyjä (salesperson)
- Pystyy tarkastelemaan eri tapahtumien lippuja, niiden niiden lipputyyppejä ja hintoja.
- Voi myydä asiakkaalle lipun ja tulostaa sen.
- Voi tarkastella lippujen myyntiraportteja.
- Pääsee näkemään omat kirjaumistietonsa User Dashboard -sivulta sekä muokkaamaan siellä omia tietojaan.
- RAJOITUKSET: Ei pysty muokkaamaan tapahtumien tietoja eikä poistamaan tapahtumia. Ei pysty muokkaamaan tai poistamaan muita käyttäjiä tai myyjiä järjestelmässä.
</details>

<details>
<summary> Järjestelmän pääkäyttäjä (admin)</summary>

### Järjestelmän pääkäyttäjä (admin)
- Lipputoimiston henkilökuntaa
- Pystyy käyttämään samoja toimintoja kuten myyjätkin.
- Lisäksi pystyy lisäämään uusia tapahtumia, muokkaamaan tapahtumia ja poistamaan niitä.
- Hallinnoi järjestelmän käyttäjien käyttöoikeuksia järjestelmään User Dashboad -sivulla. Pystyy lisäämään/muokkaamaan/poistamaan käyttäjätietoja.
</details>


## Käyttötapaukset ja käyttäjätarinat

### Lipputoimiston myyjä

<details>
<summary> Lipputoimiston myyjä- käyttötapaukset</summary>
</br>

- **Käyttötapaus 1: Tapahtumien tarkastelu**
    - **Tavoite:** Myyjä haluaa tarkastella tapahtumien lippuja, niiden lipputyyppejä ja hintoja.
    - **Toimet:** Myyjä kirjatutuu järjestelmään ja siirtyy pääsivulta Events -painikkeen kautta tapahtumasivulle. Myyjä näkee sivulla eri tapahtumat ja niiden tiedot. Hän voi myös hakea tiettyä tapahtumaa hakutoiminnon avulla.
    - **Tulos:** Myyjä saa näkyviin tiedot tapahtumista ja voi tarkastella niiden tietoja.

    - **Käyttäjätarinat:**
        - **Myyjänä haluan nähdä kaikki tulevat tapahtumat**, jotta voin kertoa asiakkaalle tietoa tapahtumasta ajankohdan, sijainnin ja kuvauksen.
        - **Myyjänä haluan tarkastella tapahtumiin liittyviä lippuja** ja niiden määriä.

- **Käyttötapaus 2: Lipun myynti**
    - **Tavoite:** Myyjä haluaa myydä lipun asiakkaalle.
    - **Toimet:** Myyjä on Events -sivulla, josta hän halutun tapahtuman kohdalta painaa "Sell Ticket" -painiketta. Myyjä valitsee halutun lippumäärän sekä lipputyypin. Kun kaikki valinnat on tehty, painamalla "Sell Ticket" -painiketta saadaan liput ja niiden QR-koodit näkyviin. Myyjä tulostaa liput asiakkaalle paperisena.
    - **Tulos:** Asiakas saa lipun ja järjestelmä tallentaa myyntitapahtuman (OrderId) tiedot. Lippujen määrä vähenee järjestelmässä myytyjen lippujen määrän mukaisesti.

    - **Käyttäjätarinat:**
        - **Myyjänä haluan valita ja myydä lippuja** tapahtumaan niin, että voin helposti valita tapahtuman, lipputyypin (esim. aikuinen, lapsi, eläkeläinen) ja halutun määrän, jotta voin tarjota asiakkaalle sujuvan ostokokemuksen.
        - **Myyjänä haluan tulostaa myymäni liput** niin, että asiakas voi käyttää niitä tapahtumassa ilman erillisiä toimenpiteitä.

- **Käyttötapaus 3: Myyntiraporttien tarkastelu**
    - **Tavoite:** Myyjä haluaa tarkastella lippujen myyntimääriä tai myyntitapahtumia tapahtumakohtaisesti tai hakea tiettyä myyntitapahtumaa OrderId:n mukaan.
    - **Toimet:** Myyjä kirjautuu järjestelmään ja siirtyy pääsivulta "Reports" -painikkeen kautta raporttisivulle. Valitsemalla "Show Sales by Events" hän näkee tapahtumakohtaisesti lippujen myyntimäärät ja myyntisummat. Halutun tapahtuman kohdalta painamalla "Show Details" -painiketta hän pääsee näkemään tarkemmat myyntitapahtumien mukaiset tiedot myynneistä. Myyjä voi myös hakea OrderId:n mukaisesti vain tietyn myyntitapahtuman tiedot "Search by OrderId" -hakutoiminnon avulla.
    - **Tulos:** Myyjä saa näkyviin myyntitiedot tapahtumittain tai tietyn OrderId:n mukaan haetut myyntitapahtuman tiedot.

    - **Käyttäjätarinat:**
        - **Myyjänä haluan tarkastella myyntiraporttia** niin, että näen myydyt liput (tyypin ja määrän) ja kokonaissumman nopeasti yhdellä silmäyksellä.

- **Käyttötapaus 4: Käyttäjätietojen tarkastelu ja muokkaus**
    - **Tavoite:** Myyjä haluaa tarkastella tai muokata omia käyttäjätietojaan.
    - **Toimet:** Myyjä kirjautuu järjestelmään, valitsee etusivulta "Users" -painikkeen. Sivulla olevasta "Profile" -välilehdeltä hän näkee omat käyttäjätietonsa. "Edit Users" -välilehdeltä hän pystyy muokkaamaan omia käyttäjätietojaan, kuten vaihtamaan salasanan.
    - **Tulos:** Myyjä saa näkyviin omat käyttäjätietonsa ja pystyy tarvittaessa vaihtamaan salasanansa.


</details>


### Järjestelmän pääkäyttäjä

<details>
<summary> Järjestelmän pääkäyttäjä- käyttötapaukset </summary>
</br>

- **Käyttötapaus 1: Tapahtumien tarkastelu**
  - Toimii samoin kuin myyjän kohdalla.

- **Käyttötapaus 2: Lipun myynti**
  - Toimii samoin kuin myyjän kohdalla.

- **Käyttötapaus 3: Myyntiraporttien tarkastelu**
  - Toimii samoin kuin myyjän kohdalla.

- **Käyttötapaus 4: Tapahtuman lisääminen**
    - **Tavoite:** Pääkäyttäjä haluaa lisätä uuden tapahtuman järjestelmään.
    - **Toimet:** Pääkäyttäjä siirtyy pääsivulta "Events" -painikkeen kautta tapahtumasivulle. Hän valitsee sieltä "Add Event", joka jälkeen syöttää uuden tapahtuman tiedot avautuvaan ikkunaan (nimi, päivämäärä ja kellonaika, osoitetiedot, kuvaus, lipputyypit, hinnat, lippumäärät) ja tallentaa tiedot painamalla "Save".
    - **Tulos:** Uusi tapahtuma tulee näkyviin "Events" sivulle ja myytävissä olevat liput tapahtumaan on tallennettu järjestelmään. Lisättyyn tapahtumaan voi nyt myydä lippuja.
    
    - **Käyttäjätarinat:**
        - **Yrityksenä haluan luoda ja hallita** tapahtumia yksinkertaisesti, jotta voin lisätä uusia tapahtumia, määrittää lipputyypit, hinnat ja määrät tehokkaasti.

- **Käyttötapaus 5: Tapahtuman muokkaaminen tai poistaminen**
    - **Tavoite:** Pääkäyttäjä haluaa muokata järjestelmässä olevan tapahtuman tietoja tai poistaa koko tapahtuman (voi poistaa vain, jos lippuja ei ole myyty tapahtumaan).
    - **Toimet:** Pääkäyttäjä siirtyy pääsivulta "Events" -painikkeen kautta tapahtumasivulle. Hän valitsee halutun tapahtuman kohdalta "Edit", jolloin voi tehdä halutut muutokset tapahtuman tietoihin ja lopuksi painamalla "Save" muutokset tallentuvat. Tapahtuman voi poistaa kokonaan painamalla tapahtuman kohdalta "Delete".
    - **Tulos:** Tapahtuman tiedot päivittyvät järjestelmään tai tapahtuma poistuu kokonaan järjestelmästä. Jos tapahtumaan on jo myyty lippuja, poisto ei ole mahdollista.

    - **Käyttäjätarinat:**
        - **Käyttäjänä haluan hallinnoida tapahtumia** niin, että voin lisätä, muokata ja poistaa tapahtumia, sekä hallita niihin liittyviä lippuja ja niiden määriä.

- **Käyttötapaus 6: Uuden myyjän lisääminen järjestelmään**
    - **Tavoite:** Pääkäyttäjä haluaa lisätä uuden myyjän (salesperson) järjestelmään.
    - **Toimet:** Pääkäyttäjä siirtyy pääsivulta "User Dashboard" -sivulle. "Add Salesperson" välilehdeltä hän voi lisätä uuden myyjän (salesperson) antamalla tarvittavat tiedot (etunimi, sukunimi ja puhelin) ja lopuksi painamalla "Add Salesperson" painiketta.
    - **Tulos:** Uusi myyjä on lisätty järjestelmään.

- **Käyttötapaus 7: Uuden käyttäjätilin luominen myyjälle**
    - **Tavoite:** Pääkäyttäjä haluaa luoda järjestelmässä olevalle myyjälle käyttäjätilin järjestelmään.
    - **Toimet:** Pääkäyttäjä siirtyy pääsivulta "User Dashboard" -sivulle. "Add User" välilehdeltä hän valitsee halutun myyjän, valitsee hänelle roolin (user/admin) ja salasanan käyttäjälle. Järjestelmä muodostaa automaattisesti generoidun käyttäjätunnuksen.
    - **Tulos:** Uusi käyttäjätili on luotu myyjälle. Myyjä voi kirjautua järjestelmään ja käyttää sitä määritettyjen oikeuksiensa mukaisesti.

    - **Käyttäjätarinat:**
        - **Kehittäjänä haluan, että sovellus käyttää vahvoja salausmenetelmiä**, jotta asiakkaiden ja myyjien henkilökohtaiset tiedot ovat turvattuja.

- **Käyttötapaus 8: Myyjän tai käyttäjän tietojen muokkaaminen**
    - **Tavoite:** Pääkäyttäjä haluaa muokata myyjän tai järjestelmän käyttäjän tietoja.
    - **Toimet:** Pääkäyttäjä siirtyy pääsivulta "User Dashboard" -sivulle. "Edit Salespersons" -välilehdeltä hän pääsee valitsemaan myyjän, jonka tietoja haluaa muokata ja voi tehdä halutut muokkaukset. "Update Salesperson" -painikkeella muutokset tallentuvat. "Edit Users" -välilehdeltä pääsee muokkaamaan tietyn järjestelmän käyttäjän tietoja (esim. vaihtamaan salasanan tai roolin). "Update user" painikkeen kautta tehdyt muutokset tallentuvat.
    - **Tulos:** Uusi myyjä on lisätty järjestelmään.

- **Käyttötapaus 9: Myyjän tai käyttäjän poistaminen järjestelmästä**
    - **Tavoite:** Pääkäyttäjä haluaa poistaa myyjän tai järjestelmän käyttäjän kokonaan järjestelmästä.
    - **Toimet:** Pääkäyttäjä siirtyy pääsivulta "User Dashboard" -sivulle. "Edit Salespersons" -välilehdeltä hän pääsee valitsemaan myyjän ja pystyy poistamaan hänet painamalla "Delete Salesperson". "Edit Users" -välilehdeltä voi poistaa halutun käyttäjän valitsemalla "Delete user".
    - **Tulos:** Myyjän tai käyttäjän tiedot poistuvat järjestelmästä.

</details>


## Käyttäjätarinat

<details>
<summary>  Lista käyttäjätarinoista   </summary>
</br>

- **Myyjänä haluan valita ja myydä lippuja** tapahtumaan niin, että voin helposti valita tapahtuman, lipputyypin (esim. aikuinen, lapsi, eläkeläinen) ja halutun määrän, jotta voin tarjota asiakkaalle sujuvan ostokokemuksen.

- **Myyjänä haluan nähdä kaikki tulevat tapahtumat**, jotta voin kertoa asiakkaalle tietoa tapahtumasta ajankohdan, sijainnin ja kuvauksen. 

- **Myyjänä haluan tarkastella myyntiraporttia** niin, että näen myydyt liput (tyypin ja määrän) ja kokonaissumman nopeasti yhdellä silmäyksellä.

- **Käyttäjänä haluan hallinnoida tapahtumia** niin, että voin lisätä, muokata ja poistaa tapahtumia, sekä hallita niihin liittyviä lippuja ja niiden määriä.

- **Myyjänä haluan tarkastella tapahtumiin liittyviä lippuja** ja niiden määriä.

- **Myyjänä haluan tulostaa myymäni liput** niin, että asiakas voi käyttää niitä tapahtumassa ilman erillisiä toimenpiteitä.

- **Yrityksenä haluan pystyä näkemään lipunmyynnin trendejä** ajan mittaan, jotta voin suunnitella tulevia tapahtumia ja optimoida lippujen hinnoittelua.

- **Yrityksenä haluan luoda ja hallita** tapahtumia yksinkertaisesti, jotta voin lisätä uusia tapahtumia, määrittää lipputyypit, hinnat ja määrät tehokkaasti.

- **Käyttäjänä haluan sovelluksen latautuvan nopeasti**, jotta voin käyttää sitä sujuvasti ilman pitkiä odotusaikoja.

- **Myyjänä haluan olla varma**, että asiakkaiden henkilökohtaiset tiedot ovat turvattuja.

- **Kehittäjänä haluan, että sovellus käyttää vahvoja salausmenetelmiä**, jotta asiakkaiden ja myyjien henkilökohtaiset tiedot ovat turvattuja.

- **Käyttäjänä haluan, että sovelluksen käyttöliittymä on selkeä ja intuitiivinen**, josta löytyy tarvittavat tiedot ja toiminnot vaivattomasti.

- **Käyttäjänä haluan, että sovelluksen toimintoja on helppo käyttää** ja ne toimivat odottamallani tavalla.

- **Kehittäjänä haluan laatia ja suorittaa testitapaukset** sovelluksen kriittisille toiminnoille, jotta voin varmistaa perustoiminnallisuuden toimivuuden ennen julkaisua.

- **Kehittäjänä haluan laatia ja suorittaa testitapaukset sovelluksen ei kriittisille toiminnoille**, jotta voin varmistaa myös toissijaisten toiminnallisuuksien toimivuuden ennen julkaisua.

- **Kehittäjänä haluan seurata ja raportoida sovelluksen virheitä ja bugeja**, jotta ne voidaan korjata nopeasti ja parantaa sovelluksen laatua.

</details>

</br>


# Käyttöliittymä

**Käyttöliittymän URL: https://ticketguru-sovellus-elite-innovators-ticketguru2.2.rahtiapp.fi**

Käyttöliittymässä tarvittavat toiminnot on koottu selkeästi eri sivuille, joihin pääsee siirtymään pääsivulta. Lisäksi sivun yläreunan navigointipalkista pääsee samoihin toimintoihin kuin pääsivun painikkeista. Tässä osiossa kuvataan eri sivujen näkymät ja miten niissä liikutaan.</summary>

### Käyttöliittymän näkymät

<details>
<summary> Login -sivu</summary>
</br>

- **Miksi:** Järjestelmää käyttämään pääsevät vain henkilöt, jotka on määritelty järjestelmän käyttäjiksi ja on siihen tarvittavat käyttäjänimi ja salasana.
- **Mitä:** Login -sivulle ohjaudutaan käyttöliittymän URL-osoitteella.
- **Siirtymiset:** Onnistuneen kirjautumisen jälkeen ohjaudutaan etusivulle.

![Login-sivu](images/login_nakyma.png)

</details>

<details>
<summary> Etusivu </summary>
</br>

- **Miksi:** Etusivu toimii pääsivuna, josta käyttäjä pääsee käyttämään järjestelmän eri toimintoja.
- **Mitä:** Sivulla on linkit: **events**, **reports** ja **users**.
- **Siirtymiset:** 
    - **Events**: siirtytään tapahtumien listaukseen ja lipunmyyntiin. 
    - **Reports**: siirtytään tarkastelmaan myyntiraportteja.
    - **Users**: siirtytään käyttäjähallintaan. 

![Etusivu](images/etusivu_nakyma.png)

</details>

<details>

<summary> Events </summary>
</br>

- **Miksi:** Täällä käyttäjä voi tarkastella ja hallita tapahtumia, kuten lisätä, muokata ja poistaa niitä.
- **Mitä:** Näkymässä on lista tapahtumista, lomake uusille tapahtumille ja työkalut tapahtumien muokkaamiseen.
- **Siirtymiset:** 
    - **Sell Ticket**: tapahtuman kohdalla olevasta linkistä siirtytään kyseisen tapahtuman lipunmyyntisivulle.
    - **Add Event**: siirtytään lomakkeelle, jossa voidaan luoda uusi tapahtuma.
    - **Edit**: siirrytään lomakkeelle, jossa pääsee muokkaamaan tapahtuman tietoja.
    - **Delete**: voidaan poistaa tapahtuma, jos siihen ei ole myyty lippuja.  


      <details>
      <summary> Sell Ticket </summary>
      </br>

      - **Miksi:** Täällä käyttäjä voi myydä lippuja tiettyyn tapahtumaan.
      - **Mitä:** Näkymässä käyttäjä voi valita lipputyypin ja lippumäärän. "Sell Ticket" painikkeesta muodostetaan myyntitapahtuma ja edetään näkymään, jossa nähdään liput ja niiden QR-koodit. 
      - **Siirtymiset:** 
          - **Sell Ticket**: Käyttäjä siirtyy sivulle, jossa näkyy myydyt liput, niiden lippukoodit ja QR-koodit. Sivulta liput voi tulostaa asiakkaalle.
      </details>

![Events](images/events_nakyma.png)

</details>


<details>
<summary> Reports </summary>
</br>

- **Miksi:** Täällä käyttäjä voi tarkastella myyntiraportteja ja saada kokonaiskuvan myynnistä.
- **Mitä:** Raporttisivulla voidaan tarkastella myytyjä lippumääriä ja myyntisummia tapahtumittain. Lisäksi voidaan myös hakea tietyn myyntitapahtuman tiedot OrderId:n avulla.
- **Siirtymiset:** 
    - **Show Sales by Events**: Painiketta painamalla saadaan näkyviin lipunmyyntitiedot tapahtumittain listattuna.
    - **Search by order ID**: Hakutoiminnon avulla sivulla näytetään haetun myyntitapahtuman tiedot.

![Reports](images/reports_nakyma.png)

</details>


<details>
<summary> Users </summary>
</br>

- **Miksi:** Sivulla voidaan tarkastella ja hallita myyjien ja käyttäjien käyttäjätietoja järjestelmässä.
- **Mitä:** Käyttäjä voi tarkastella omia kirjautumistietojaan. Pääkäyttäjä pystyy lisäämään uusia myyjiä ja luomaan heille käyttäjätilin järjestelmään. Käyttäjä pystyy muokkaamaan omia tietojaan ja pääkäyttäjä pystyy muokkaamaan ja poistamaan myyjiä ja järjestelmän käyttäjiä. 
- **Siirtymiset:** 
    - **Profile**: Välilehdellä näkyy kirjautuneen käyttäjän tiedot.
    - **Add Salesperson**: Välilehdellä voidaan lisätä uusi myyjä (salesperson).
    - **Add User**: Välilehdellä voidaan luoda myyjälle käyttäjätili järjestelmään.
    - **Edit Users**: Välilehdellä voidaan muokata käyttäjien tietoja.
    - **Edit Salesperson**: Välilehdellä voidaan muokata myyjien tietoja.

![Users](images/users_nakyma.png)

</details>



### Käyttöliittymäkaavio
- **Kaavio:** [Käyttöliittymäkaavio -linkki](https://docs.google.com/spreadsheets/d/1MQNqwOzjuIXldOeYIx_NevCTvQeL70HyKikxyzmMKN8/edit?gid=643351026#gid=643351026)
- **Miksi:** Käyttöliittymäkaavio näyttää, miten eri näkymät liittyvät toisiinsa ja miten käyttäjä navigoi niiden välillä. Tätä käyttöliittymäkaavion suunnitelmaa on käytetty lähtökohtana lopullisten näkymien toteutuksessa.
</details>
</br>

# Tietokanta

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
| salespersonId     | int FK           | Viittaus myyjään (SalesPerson-taulu).                   |
| orderDate         | date             | Tilauksen päivämäärä.                                   |

</details>
</br>

### SalesPerson (Myyjä)

<details>
<summary>Myyjien tiedot sisältävä taulu, jossa säilytetään tietoa lipputoimiston työntekijöistä. Yksi myyjä voi käsitellä useita tilauksia.</summary>
</br>

| Kenttä            | Tyyppi           | Kuvaus                                                  |
| ------------------|------------------| --------------------------------------------------------|
| salesPersonId     | int (AN) PK      | Myyjän yksilöllinen tunniste.                           |
| lastName          | varchar(30)      | Myyjän sukunimi.                                        |
| firstName         | varchar(30)      | Myyjän etunimi.                                         |
| phone             | varchar(30)      | Myyjän puhelinnumero.                                   |

</details>
</br>

[Linkki tietokantakaavioon](https://docs.google.com/spreadsheets/d/1MQNqwOzjuIXldOeYIx_NevCTvQeL70HyKikxyzmMKN8/edit?gid=1081752884#gid=1081752884)
</details>

---

# Tekninen kuvaus

**Yleiskuvaus järjestelmästä**  
TicketGuru on lipunmyyntijärjestelmä, jossa voidaan hallita tapahtumia, lipputyyppejä, myydä lippuja, tulostaa lippukoodeja sekä hallinnoida käyttäjiä ja myyjiä (salesperson). Järjestelmä koostuu kahdesta pääosasta:

1. **Backend-palvelu** (Spring Boot -sovellus, Java):  
   - Palvelee REST-rajapintojen kautta pyyntöjä frontendiltä.  
   - Vastaa tapahtumien, lippujen, tilausten, käyttäjä- ja myyjätilien hallinnasta sekä tietokantayhteyksistä.  
   - Sisältää tietomallin (Entity-luokat), palvelukerrokset (Service), repositoriot (Repository) ja kontrollerit (Controller).  
   - Tietokanta: PostgreSQL (tuotanto- ja testiympäristöissä) tai H2 (kehitysympäristössä).

2. **Frontend-sovellus** (React):  
   - Tarjoaa käyttöliittymän mm. tapahtumien selailuun ja lipunmyyntiin.  
   - Kommunikoi backendin kanssa REST API -päätepisteiden kautta.

**Käyttöympäristö**  
- Backend:  
  - Ajetaan yleensä erillisenä palveluna (esim. Java-toteutus Spring Boot -sovelluspalvelimella).  
  - Tuotantoympäristössä voi pyöriä esimerkiksi Kubernetes-klusterissa (Rahti).  
  - Käyttää suoraa TCP-yhteyttä tietokantaan (PostgreSQL) ja tarjoaa HTTP(S)-rajapinnan frontendille.  
- Frontend:  
  - Ajetaan typillisesti esim. Node.js:llä käännettynä staattisena sivustona, joka voidaan palvella Nginx:llä tai vastaavalla web-palvelimella.  
  - Selainklientti käyttää HTTPS-pyyntöjä backend-rajapintaan.

**Tietoliikenne ja yhteydet**  
- Selain (käyttäjän asiakaslaite) lähettää HTTP/HTTPS-pyyntöjä frontendissä pyörivään React-sovellukseen.  
- React-sovellus lähettää REST-pyynnöt backendin julkistamiin HTTP(S)-endpointteihin.  
- Backend-yhteys tietokantaan (PostgreSQL/H2) tapahtuu JDBC/SQL-tasolla.

Tietovirtojen hahmottelua voi tehdä esimerkiksi dynaamisilla sekvenssikaavioilla (esim. tietokannan päivitys tilauksia luotaessa) tai data flow -diagrammilla:

```
[User Browser]
       |
       v (HTTPS)
 [Frontend (React)] 
       |
       v (HTTPS/HTTP)
   [Backend (Spring Boot)]
       |
       v (JDBC/SSL)
    [PostgreSQL DB]
```

**Palvelintoteutuksen yleiskuvaus**  
- Sovellus on toteutettu Spring Boot -ympäristössä (Java 11+).  
- Tietokantana PostgreSQL (prod/rahti) tai H2 (dev).  
- Hibernate + JPA käytössä tietokantakerroksessa.  
- Sovellus voidaan paketoida jar:ksi ja ajaa komennolla `java -jar TicketGuru-Sovellus.jar`.  
- Rahti-ympäristössä käytetään sovellusprofiilia `rahti`, joka lukee `application-rahti.properties` -tiedostoa ja käyttää ympäristömuuttujia tietokantayhteyden määrittelyyn.  
- Kehitysympäristössä käytetään `application-dev.properties`, joka hyödyntää H2-muistitietokantaa.

**Teknologiat**  
- Backend: Spring Boot, Java, JPA/Hibernate, Spring Security  
- Frontend: React, Bootstrap  
- Tietokanta: PostgreSQL (prod), H2 (dev)

**Rajapintojen kuvaukset**  
Keskeiset rajapinnat ovat REST-tyyppisiä. Backend altistaa mm. seuraavanlaisia REST-päätepisteitä:

- `GET /events` - Hakee kaikki tapahtumat  
- `POST /events` - Luo uuden tapahtuman  
- `GET /orders` - Hakee kaikki tilaukset  
- `POST /orders` - Luo uuden tilauksen  
- `POST /api/sell` - Myy lippuja tiettyyn tapahtumaan  
- `GET /api/print-tickets/{orderId}` - Hakee lipputunnisteet tulostettavaksi  
- `POST /api/auth/login` - Kirjautuminen  
- `GET /api/users/me` - Hakee kirjautuneen käyttäjän tiedot

Esimerkki REST-kutsusta lippujen tulostamiseen:  
```
GET /api/print-tickets/123
Response: { "ticketCodes": ["code1", "code2", ...] }
```

**Turvallisuus**  
- Backend käyttää Spring Securityä perustuen käyttäjänimien ja salasanojen hallintaan (Basic Auth).  
- Rajapintoja on suojattu roolipohjaisilla oikeuksilla (USER/ADMIN/SALESPERSON).
- Tietokantayhteydet voidaan suojata TLS:llä tuotantoympäristössä.  
- Frontend-ohjelma ei tallenna salasanoja selkokielisenä.  
- Salausalgoritmeina bcrypt password-hashauksen yhteydessä.

**Ohjelmointikäytännöt**  
- Ohjelmakoodi on pyritty jäsentämään selkeisiin paketteihin: `domain` entiteeteille, `service` palvelulogiikalle, `repository` tiedonhakurajapinnoille, `web` kontrollerit.  
- Luokat, metodit ja muuttujat on nimetty kuvaavasti (esimerkiksi `EventService`, `TicketRepository`, `SellTicketsDto`).  
- Koodissa on Javadoc- ja kommenttikäytäntöjä selkeyttämään tarkoituksia.  
- Duplikaation välttämiseksi on käytetty mm. Mapstructia entiteettien ja DTO:iden väliseen muunnokseen, jotta looginen koodi pysyy yhdenmukaisena eikä toisteta samoja muunnoksia useassa paikassa.


# Asennusohjeet

**Kehitysympäristö**  
1. Asenna Java (esim. AdoptOpenJDK 11), Node.js ja npm.  
2. Kloonaa projektin lähdekoodi Git-reposta.  
3. Backend:  
   - Mene backend-hakemistoon (`./TicketGuru-Sovellus`)  
   - Aja `mvn clean install` luodaksesi jar-paketin.  
   - Kehitystilassa käytetään `application-dev.properties` -asetuksia.  
   - Käynnistä `mvn spring-boot:run -Dspring-boot.run.profiles=dev` tai `java -jar target/TicketGuru-Sovellus-*.jar --spring.profiles.active=dev`.
4. Frontend:  
   - Mene frontend-hakemistoon (`./TicketGuru-Sovellus/frontend`)  
   - Aja `npm install` asentaaksesi riippuvuudet.  
   - Aja `npm run dev` käynnistääksesi kehityspalvelimen (localhost:5173).  

**Tuotantoympäristö**  
1. Määritä tietokantayhteydet ympäristömuuttujina (esim. `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`) tai käytä `application-prod.properties` -tiedostoa.  
2. Rakenna backend-sovellus `mvn clean install` ja aja `java -jar target/TicketGuru-Sovellus-*.jar --spring.profiles.active=prod`.  
3. Varmista että PostgreSQL-tietokanta on asennettu ja käytössä. Luo tarvittaessa tietokantakäyttäjä ja tietokanta:  
   ```
   CREATE DATABASE ticketguru;
   CREATE USER ticketuser WITH PASSWORD 'ticketpassword';
   GRANT ALL PRIVILEGES ON DATABASE ticketguru TO ticketuser;
   ```
4. Yhdistä backend annettuihin ympäristömuuttujiin. Palvelin käynnistyy ja hibernaten `update` strategia luo/päivittää taulut automaattisesti.  
5. Frontend voidaan rakentaa komennolla `npm run build` ja sitten palvella tuotannossa esim. Nginx:llä. Määritä Nginx konfiguraatio ohjaamaan `/api`-pyynnöt backend-palvelimelle.

**Rahti-ympäristö**  
- Aseta Rahti-palvelussa tarvittavat environment-muuttujat (`POSTGRESQL_SERVICE_HOST`, `POSTGRESQL_SERVICE_PORT`, `POSTGRESQL_DATABASE`, `POSTGRESQL_USER`, `POSTGRESQL_PASSWORD`).
- Käynnistä sovellus `--spring.profiles.active=rahti` profiililla.  
- Rahti-lokaatio (OpenShift/Kubernetes) hoitaa podien hallinnan.


# Käynnistys- ja käyttöohje

**Käynnistys kehitysympäristössä:**  
- Backend käynnistyy osoitteessa `http://localhost:8080` (dev-profiili).  
- Frontend käynnistyy osoitteessa `http://localhost:5173`.

**Kirjautuminen**  
- Sovellukseen voi kirjautua oletusarvoisella admin-käyttäjällä:  
  - Käyttäjätunnus: `admin`  
  - Salasana: `admin`  
- Kehitysympäristössä löytyy myös käyttäjä `user`:  
  - Käyttäjätunnus: `user`  
  - Salasana: `user`  
- Nämä tunnukset on määritelty `UserConfiguration`-luokassa ja tallennettu tietokantaan sovelluksen käynnistyessä.

**Käyttö**  
1. Mene sovelluksen frontend-osoitteeseen (esim. `http://localhost:5173`).  
2. Kirjaudu sisään admin-/user-tunnuksilla.  
3. Päävalikosta pääsee mm. tapahtumien hallintaan, lippujen myyntiin, raporttien katseluun ja käyttäjien hallintaan.  
4. Myytäessä lippuja (esim. `Sell Ticket`) valitse tapahtuma, lipputyyppi ja kappalemäärä.  
5. Kun tilaus on luotu, liput voi tulostaa /katsoa `Print Tickets` -sivulta.

Mikäli käytät tuotanto- tai rahtiympäristöä, korvaa `localhost:8080` ja `localhost:5173` tuotantoympäristön palvelinosoitteilla. Kirjautumiseen ja testitunnuksiin voi päivitysten jälkeen tarvita erilaisia tunnuksia, jotka on hyvä dokumentoida sisäisesti.

---

## REST API dokumentaatio

**Base URL: sovellus on julkaistu Rahti2 palvelussa ja API pyynnöt käytettävissä https://ticket-guru-sovellus-git-elite-innovators-ticketguru2.2.rahtiapp.fi**


<details>
<summary>  Lisää tapahtuma (POST)</summary>

* Metodi: POST
* Polku: /events

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
* Polku: /events/{id}
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
  "salespersonFirstName": "Peter",
  "salespersonLastName": "Smith",
  "orderDate": "2024-10-18T16:29:01.067+00:00"
}
```

#### Virhekoodit:

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
    "salespersonFirstName": "Peter",
    "salespersonLastName": "Smith",
    "orderDate": "2024-10-18T16:29:01.067+00:00"
  },
  {
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

Voit päivittää `salespersonId`, mutta **`orderDetails`-kenttää ei voi muokata** tilauksen luomisen jälkeen.

*Esimerkki*:

```json
{
  "salespersonId": 1
}
```

#### Paluukoodi:

- **200 OK**

#### Vastaus:

```json
{
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

Voit päivittää `salespersonId`.

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

  - `salespersonId` (pakollinen): Myyjän tunniste.
  - `orderDetails` (pakollinen tilauksen luomisessa): Lista tilauksen yksityiskohdista.
  - `orderId`: Tilauksen tunniste (vastauksessa).
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

</details>


## Lippujen (Ticket) API-pyynnöt

<details><summary>CORS ominaisuudet</summary>

Tähän sovellukseen on määritetty CORS-säännöt seuraavasti:
- Endpoint `/tickets/event/**` on julkisesti saatavilla, eikä se vaadi autentikointia. Tämä endpoint sallii kaikki alkuperät (`*`) ja seuraavat metodit: `GET` ja `PATCH`.
- Kaikki muut `TicketController`-endpointit ovat suojattuja ja vaativat käyttäjäroolin (`ADMIN` tai `SALESPERSON`). Näitä ei ole avattu julkisesti CORS-säännöissä.
- Lisätietoja CORS-määrityksistä löytyy sovelluksen lähdekoodista `SecurityConfig`-luokasta.
</details>

<details> <summary>Hae kaikki liput (GET)</summary>

* **Metodi**: GET
* **Polku**: `/tickets`
* **Käyttöoikeudet**: ADMIN, SALESPERSON
* **Paluukoodit**:
 - 200 OK - Kutsu onnistui, liput löytyivät.
 - 401 Unauthorized - Käyttäjä ei ole kirjautunut sisään tai käyttäjätunnus/salasana on virheellinen.
 - 403 Forbidden - Käyttäjällä ei ole riittäviä käyttöoikeuksia.

Vastaus:

```json
[
    {
        "id": 4,
        "ticketCode": "a9e94359-b259-4a90-ada7-b03b7dfd2a00",
        "valid": true
    },
    {
        "id": 5,
        "ticketCode": "abdb4c97-c258-49e5-85e5-6c9b4df1cb36",
        "valid": true
    },
    {
        "id": 6,
        "ticketCode": "6c51d8f2-59c3-4780-9b22-2d6cd974d9d9",
        "valid": true
    }
]
```
</details>

<details> <summary>Hae lippu ID:n perusteella (GET)</summary>

* **Metodi**: GET
* **Polku**: `/tickets/{id}`
* **Käyttöoikeudet**: ADMIN, SALESPERSON
* **Paluukoodit**:
 - 200 OK - Kutsu onnistui, lippu löytyi.
 - 400 Bad Request - Id ei ole kelvollinen (esim. väärässä muodossa).
 - 404 Not Found - Lippua ei löydy annetulla Id:llä.
 - 401 Unauthorized - Käyttäjä ei ole kirjautunut sisään tai käyttäjätunnus/salasana on virheellinen.
 - 403 Forbidden - Käyttäjällä ei ole riittäviä käyttöoikeuksia.

#### **Vastaus**

```json
{
        "id": 6,
        "ticketCode": "6c51d8f2-59c3-4780-9b22-2d6cd974d9d9",
        "valid": true
    }
```
</details>

<details> <summary>Hae liput/lippu tapahtuman mukaan (GET)</summary>

* **Metodi**: GET
* **Polku**: `/tickets/event/{eventId}`
* **Käyttöoikeudet**: Julkinen (Ei vaadi autentikointia)
* **Pyyntöparamtrit**:
  - `ticketCode` (valinnainen query-parametri): Syötettäessä, palautetaan vain kyseisen lipun tiedot
* **Paluukoodit**:
 - 200 OK - Kutsu onnistui, lippu löytyi.
 - 400 Bad Request - Tapahtuman id ei ole kelvollinen (esim. väärässä muodossa).
 - 404 Not Found - Tapahtumaa tai lippua ei löydy annetuilla tiedoilla.

#### Vastaus:

```json
[
    {
        "id": 4,
        "ticketCode": "a9e94359-b259-4a90-ada7-b03b7dfd2a00",
        "valid": true
    },
    {
        "id": 5,
        "ticketCode": "abdb4c97-c258-49e5-85e5-6c9b4df1cb36",
        "valid": true
    },
    {
        "id": 7,
        "ticketCode": "08d99549-1009-4b5a-90e4-3b60f7ee5d56",
        "valid": true
    },
    {
        "id": 8,
        "ticketCode": "60c19382-d3b9-4511-b28f-8eaa7c2eba61",
        "valid": true
    },
    {
        "id": 10,
        "ticketCode": "7b0bbe91-ef65-4758-80de-373c7e23146e",
        "valid": true
    }
]
```
</details>

<details> <summary>Merkitse lippu käytetyksi (PATCH)</summary>

* **Metodi**: PATCH
* **Polku**: `/tickets/event/{eventId}`
* **Käyttöoikeudet**: Julkinen (ei vaadi autentikointia)
* **Pyyntöparametrit**:
  - `eventId` (pakollinen): Tapahtuman id, jonka lippua halutaan päivittää.
  - `ticketCode` (pakollinen): Lippukoodi, jonka tietoja halutaan päivittää.

#### Pyynnön runko:

```json
{}
```

* **Paluukoodit**:
 - 200 OK - Kutsu onnistui, lipun tiedot päivitettiin ja lippu merkittiin käytetyksi (`isValid` on asetettu automaattisesti `false`).	
 - 400 Bad Request - Pyyntö on puutteellinen tai lippu on jo käytetty.
 - 404 Not Found - Tapahtumaa ei löydy ID:llä tai lippua ei löydy annetulla lippukoodilla.

#### Vastaus:

```json
{
        "id": 4,
        "ticketCode": "a9e94359-b259-4a90-ada7-b03b7dfd2a00",
        "valid": false
    }
```
</details>

<details> <summary>Hae liput tilauksen mukaan (GET)</summary>

* **Metodi**: GET
* **Polku**: `/tickets/order/{orderId}`
* **Käyttöoikeudet**: ADMIN, SALESPERSON
* **Paluukoodit**:
 - 200 OK - Kutsu onnistui, lippu löytyi.
 - 400 Bad Request - Tilauksen id ei ole kelvollinen (esim. väärässä muodossa).
 - 404 Not Found - Lippua ei löydy annetulla tilauseb id:llä.
 - 401 Unauthorized - Käyttäjä ei ole kirjautunut sisään tai käyttäjätunnus/salasana on virheellinen.
 - 403 Forbidden - Käyttäjällä ei ole riittäviä käyttöoikeuksia.

#### Vastaus:

```json
[
    {
        "id": 4,
        "ticketCode": "a9e94359-b259-4a90-ada7-b03b7dfd2a00",
        "valid": true
    },
    {
        "id": 5,
        "ticketCode": "abdb4c97-c258-49e5-85e5-6c9b4df1cb36",
        "valid": true
    },
    {
        "id": 6,
        "ticketCode": "6c51d8f2-59c3-4780-9b22-2d6cd974d9d9",
        "valid": true
    }
]
```
</details>

## Lipputyyppien (TicketType) API-pyynnöt

<details><summary>Hae kaikki lipputyypit (GET)</summary>

* **Metodi**: GET
* **Polku**: `/tickettypes`
* **Käyttöoikeudet**: ADMIN
* **Paluukoodit**:
  - 200 OK - Kutsu onnistui, lipputyypit löytyivät.
  - 401 Unauthorized - Käyttäjä ei ole kirjautunut sisään tai käyttäjätunnus/salasana on virheellinen.
  - 403 Forbidden - Käyttäjällä ei ole riittäviä käyttöoikeuksia

#### Vastaus
```json
  [
    {
        "id": 1,
        "name": "VIP"
    },
    {
        "id": 2,
        "name": "Standard"
    }
]
```
</details>

<details><summary>Hae lipputyyppi ID:n perusteella (GET)</summary>

* **Metodi**: GET
* **Polku**: `/tickettypes/{id}`
* **Käyttöoikeudet**: ADMIN
* **Paluukoodit**:
  - 200 OK - Kutsu onnistui, lipputyypit löytyivät.
  - 400 Bad Request - ID ei ole kevlollinen.
  - 404 Not Found - Lipputyyppiä ei löydy annetulla ID:llä.
  - 401 Unauthorized - Käyttäjä ei ole kirjautunut sisään tai käyttäjätunnus/salasana on virheellinen.
  - 403 Forbidden - Käyttäjällä ei ole riittäviä käyttöoikeuksia

#### Vastaus:
```json
    {
        "id": 1,
        "name": "VIP"
    }
```
</details>

<details><summary>Luo uusi lipputyyppi (POST)</summary>

* **Metodi**: POST
* **Polku**: `/tickettypes`
* **Käyttöoikeudet**: ADMIN
* **Pyyntöparametrit**:
  - **name** (pakollinen): Lipputyypin nimi

* **Pyynnön runko**:
```json
{
  "name": "Aikuinen"
}
```

* **Paluukoodit**:
  - 201 Created - Kutsu onnistui, uusi lipputyyppi luotiin.
  - 404 Bad Request - Pyyntö on puutteellinen tai lipputyyppi on jo olemassa samalla nimellä.
  - 401 Unauthorized - Käyttäjä ei ole kirjautunut sisään tai käyttäjätunnus/salasana on virheellinen.
  - 403 Forbidden - Käyttäjällä ei ole riittäviä käyttöoikeuksia.

**Vastaus**:
```json
    {
        "id": 3,
        "name": "Aikuinen"
    }
```
</details>

<details><summary>Päivitä lipputyyppi (PUT)</summary>

* **Metodi**: PUT
* **Polku**: `/tickettypes/{id}`
* **Käyttöoikeudet**: ADMIN
* **Pyyntöparametrit**:
  - **id** (pakollinen): Lipputyypin ID, jota halutaan päivittää.
  - **name** (pakollinen): Lipputyypin uusi nimi.

* **Pyynnön runko**:
```json
{
  "name": "Aikuinen K-18"
}
```

* **Paluukoodit**:
  - 200 OK - Kutsu onnistui, lipputyyppi päivitettiin.
  - 404 Bad Request - Pyyntö on puutteellinen tai lipputyyppi on jo olemassa samalla nimellä.
  - 404 Not Found - Lipputyyppiä ei löydy annetulla ID:llä.
  - 401 Unauthorized - Käyttäjä ei ole kirjautunut sisään tai käyttäjätunnus/salasana on virheellinen.
  - 403 Forbidden - Käyttäjällä ei ole riittäviä käyttöoikeuksia.

**Vastaus**:
```json
    {
        "id": 3,
        "name": "Aikuinen K-18"
    }
```
</details>


# Autentikaatio

## Autentikointiprosessi
<details>
<summary> Autentikointiprosessi </summary>
<br/>

TicketGuru-sovelluksessa käytetään perinteistä käyttäjätunnus-salasana -autentikaatiota. 

## Perustason autentikointi (Basic Authentication)

Perustason autentikointi on määritetty Spring Boot -sovelluksessa käyttäen SecurityFilterChain-luokkaa. Autentikointi tapahtuu HTTP-pyyntöjen yhteydessä, joissa käyttäjätunnus ja salasana lähetetään base64-koodattuna Authorization-otsikossa. Tämä mahdollistaa käyttäjän todennuksen, ennen kuin he saavat pääsyn sovelluksen API-pyyntöihin.
</details>

<details><summary>Turvallisuuskonfiguraation selitys</summary>

Sovelluksen turvallisuuskonfiguraatio on määritelty `SecurityConfig`-luokassa, joka hallitsee autentikoinnin ja valtuutuksen sääntöjä. Tämä luokka käyttää Spring Security -kirjastoa, joka tarjoaa joustavan ja tehokkaan tavan hallita käyttäjien pääsyä sovellukseen.
</details>

<details><summary>Turvallisuuskonfiguraation Ominaisuudet</summary>

1. **Autentikointi**: `SecurityConfig` määrittelee, että kaikki API-pyynnöt vaativat käyttäjän tunnistamista. Tämä tapahtuu perustason autentikoinnin (Basic Authentication) avulla, jossa käyttäjätunnus ja salasana lähetetään base64-koodattuna HTTP-otsikossa. Autentikointi käsitellään `AuthenticationManager` -beanin avulla, joka vastaa käyttäjien autentikaatiosta. Tämä bean on määritelty seuraavasti:
```java
  @Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
}
```
`AuthenticationManager` -bean käyttää `AuthenticationConfiguration` -luokkaa, joka määrittelee autentikaatiomekanismin. Se varmistaa, että vain oikeutetut käyttäjät pääsevät käyttämään sovelluksen toimintoja.

2. **Käyttöoikeudet**: Luokassa määritellään myös, mitkä käyttäjäroolit voivat käyttää mitäkin sovelluksen toimintoja. Esimerkiksi:
   - Admin-käyttäjät voivat käyttää kaikkia päätepisteitä.
   - User-käyttäjät saavat vain rajoitetun pääsyn myyntitoimintoihin.

3. **CSRF-suojaus**: CSRF-suojauksen tarkastukset on toistaiseksi poistettu käytöstä testauksen helpottamiseksi.

4. **Virheiden käsittely**: Turvallisuuskonfiguraatio sisältää myös säännöt siitä, miten autentikointi- ja valtuutusvirheitä käsitellään. Jos käyttäjä ei pysty tunnistautumaan oikein tai ei omaa tarvittavia käyttöoikeuksia, sovellus palauttaa asianmukaiset virhekoodit.
</details>

<details><summary>Esimerkki SecurityConfig-luokasta</summary>

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
            .anyRequest().authenticated()  // Kaikki API-pyynnöt vaativat autentikoinnin
            .and()
        .httpBasic()                       // Perustason autentikointi
        .and()
        .csrf().disable();                 // CSRF-suojaus poistettu testauksen helpottamiseksi

    return http.build();
}
```

Tämä rakenne varmistaa, että vain oikeutetut käyttäjät voivat käyttää sovelluksen eri toimintoja, ja se parantaa tietoturvaa koko sovelluksessa.
</details>

<details><summary>Endpoint-yhteenveto</summary>

Sovelluksessa on useita API-päätepisteitä, jotka tarjoavat erilaisia toimintoja. Jokaiselle päätepisteelle on määritelty käyttöoikeudet, jotka perustuvat käyttäjärooleihin. Alla on luettelo keskeisistä päätepisteistä ja niiden vaatimista käyttöoikeuksista:

### TicketController

| Päätepiste                        | Kuvaus                                         | Vaadittu rooli         |
|-----------------------------------|------------------------------------------------|------------------------|
| `GET /tickets`                   | Hakee kaikki liput.                          | **SALESPERSON**, **ADMIN**  |
| `GET /tickets/{id}`              | Hakee lipun ID:n perusteella.                | **SALESPERSON**, **ADMIN**  |
| `GET /tickets/event/{eventId}`   | Hakee liput tietyn tapahtuman perusteella.   | **SALESPERSON**, **ADMIN**  |
| `GET /tickets/order/{orderId}`   | Hakee liput tietyn tilauksen perusteella.    | **SALESPERSON**, **ADMIN**  |

### OrderController

| Päätepiste                        | Kuvaus                                         | Vaadittu rooli         |
|-----------------------------------|------------------------------------------------|------------------------|
| `GET /orders`                    | Hakee kaikki tilaukset.                       | **SALESPERSON**, **ADMIN**  |
| `GET /orders/{orderId}`          | Hakee tilauksen ID:n perusteella.             | **SALESPERSON**, **ADMIN**  |
| `POST /orders`                   | Luo uuden tilauksen.                          | **SALESPERSON**, **ADMIN**  |
| `PUT /orders/{orderId}`          | Muokkaa olemassa olevaa tilausta.             | **SALESPERSON**, **ADMIN**  |
| `PATCH /orders/{orderId}`        | Päivittää osia olemassa olevasta tilauksesta. | **SALESPERSON**, **ADMIN**  |
| `DELETE /orders/{orderId}`       | Poistaa tilauksen.                            | **ADMIN**              |


### EventController

| Päätepiste                        | Kuvaus                                         | Vaadittu rooli         |
|-----------------------------------|------------------------------------------------|------------------------|
| `GET /events`                    | Hakee kaikki tapahtumat.                      | **SALESPERSON**, **ADMIN**  |
| `GET /events/{eventId}`          | Hakee tapahtuman ID:n perusteella.            | **SALESPERSON**, **ADMIN**  |
| `POST /events`                   | Luo uuden tapahtuman.                          | **ADMIN**              |
| `PUT /events/{eventId}`          | Muokkaa olemassa olevaa tapahtumaa.           | **ADMIN**              |
| `PATCH /events/{eventId}`        | Päivittää osia olemassa olevasta tapahtumasta. | **ADMIN**              |
| `DELETE /events/{eventId}`       | Poistaa tapahtuman.                            | **ADMIN**              |
| `GET /events/search`             | Hakee tapahtumat kaupungin perusteella.       | **SALESPERSON**, **ADMIN**  |

### TicketTypeController

| Päätepiste                            | Kuvaus                                         | Vaadittu rooli         |
|---------------------------------------|------------------------------------------------|------------------------|
| `GET /tickettypes`                    | Hakee kaikki lipputyypit.                      | **ADMIN**              |
| `GET /tickettypes/id}`                | Hakee lipputyypin ID:n perusteella.            | **ADMIN**              |
| `POST /tickettypes`                   | Luo uuden lipputyypin.                         | **ADMIN**              |
| `PUT /tickettypes/{id}`               | Muokkaa olemassa olevaa lipputyyppiä.          | **ADMIN**              |

</details>

<details></summary>Käyttäjätiedot</summary>

Jokainen sovelluksen käyttäjä tallennetaan tietokantaan `User` ja `Salesperson` -entiteetteinä. Käyttäjätunnukset ja salasanat tallennetaan tietokantaan seuraavasti:

### User (Käyttäjä)
- **userId**: Käyttäjän yksilöllinen ID.
- **username**: Käyttäjän yksilöllinen käyttäjätunnus.
- **passwordHash**: Salasanan hajautusarvo.
- **role**: Käyttäjän rooli (ADMIN tai USER)

### Salesperson (Myyjä)
- **salespersonId**: Myyjän yksilöllinen ID.
- **firstName**: Myyjän etunimi.
- **lastName**: Myyjän sukunimi
- **phone**: Myyjän puhelinnumero.
- **orders**: Lista myyjän tekemistä tilauksista.

### Käyttäjät
* Kehitysvaiheessa luodut valmiit käyttäjät:
- **Admin** (role = ADMIN)
  - **Käyttäjätunnus**: `admin`
  - **Salasana**: `admin` (hajautettuna)
  - **Rooli**: `ADMIN`

- **User** (role = USER)
  - **Käyttäjätunnus**: `user`
  - **Salasana**: `user` (hajautettuna)
  - **Rooli**: `USER`

  #### Käyttäjien luominen ja roolien määrittäminen

  Admin-tason käyttäjät voivat luoda uusia käyttäjiä ja myyjiä sovellukseen sekä määrittää heille roolit. Tämä tarkoittaa, että jokaisella käyttäjällä on omat käyttäjätunnuksensa ja salasanansa, jotka tallennetaan turvallisesti tietokantaan.

</details>

<details><summary>Salasanan tallennus</summary>

Salasana tallennetaan tietokantaan hajautettuna, eli se ei ole selkokielinen. Tämä toteutetaan hyödyntäen Java Spring Security -komponentteja, jotka varmistavat salasanan turvallisen tallennuksen ja tarkastamisen.

```java
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String hashedAdminPassword = encoder.encode("admin");
String hashedSalespersonPassword = encoder.encode("salesperson");
```

</details>

<details><summary>Käyttäjäroolit ja pääsy (Authorization Policies)</summary>

Sovelluksessa on kaksi pääasiallista käyttäjäroolia, jotka määrittävät käyttäjien pääsyoikeudet:

- **Admin**: Käyttäjät, joilla on `role`-arvo `ADMIN`. Admin tasoisilla käyttäjillä on täysi pääsy kaikkiin sovelluksen toimintoihin ja API-pyyntöihin, mukaan lukien:
  - Käyttäjien hallinta (luonti, muokkaus, poisto).
  - Tapahtumien luominen ja hallinta.
  - Kaikkien varausten tarkastelu ja hallinta.
  - Sovelluksen asetusten muokkaaminen.

- **Salesperson**: Käyttäjät, joilla on `role`-arvo `USER`. User tasoisilla käyttäjillä on rajoitetut oikeudet, joilla voivat:
  - Myydä lippuja olemassa oleviin tapahtumiin.
  - Tarkastella omia myyntitietojaan.

## Käyttöoikeudet

Käyttöoikeudet on määritelty seuraavasti:

- Kaikki API-pyynnöt vaativat autentikoinnin.
- Admin-käyttäjät voivat käyttää kaikkia sovelluksen päätepisteitä, kun taas Salesperson-käyttäjät saavat vain rajoitetun pääsyn.
- CSRF-suojauksen (Cross-Site Request Forgery) tarkastukset on poistettu käytöstä testauksen helpottamiseksi, mutta tuotantoympäristössä suositellaan sen käyttämistä.

Tämä rakenne varmistaa, että vain oikeutetut käyttäjät voivat käyttää sovelluksen eri toimintoja, mikä parantaa tietoturvaa ja käyttäjäkokemusta.

</details>

<details><summary>Virheenkäsittely ja autentikointivirheet</summary>

## Virheenkäsittely

Sovelluksessa on otettu käyttöön virheenkäsittely autentikoinnin ja käyttöoikeuksien osalta. Virhetilanteissa käyttäjät saavat selkeät ja informatiiviset vastaukset, jotka auttavat heitä ymmärtämään, mitä on tapahtunut ja miten edetä.

### Autentikointivirheet

- **Virheellinen käyttäjätunnus tai salasana**: Mikäli käyttäjä syöttää virheelliset käyttäjätunnukset tai salasanat, sovellus palauttaa `401 Unauthorized` -vastauksen. Tämä tarkoittaa, että käyttäjän on tarkistettava syöttämänsä tiedot ja yritettävä uudelleen.

- **Metodi:** GET
  - **Polku:** /tickets
  - **Otsikot:**
    ```
    Authorization: Basic base64(username:password)  // Syötä virheellinen käyttäjätunnus ja salasana
    ```
  - **Paluukoodi:** `401 Unauthorized`
  - **Vastaus:**
    ```json
    {
        "error": "Unauthorized",
        "message": "Invalid username or password."
    }
    ```

### Käyttöoikeusvirheet

- **Käyttöoikeus kielletään**: Jos käyttäjä yrittää käyttää päätepistettä, johon hänellä ei ole oikeuksia (esimerkiksi **Salesperson**-käyttäjä yrittää luoda uuden tapahtuman), sovellus palauttaa `403 Forbidden` -vastauksen. Tämä viestii käyttäjälle, että hänellä ei ole riittäviä oikeuksia kyseisen toiminnon suorittamiseen.

- **Metodi:** POST
  - **Polku:** /events
  - **Otsikot:**
    ```
    Authorization: Basic base64(salesperson:correct_password)  // Salesperson käyttäjätunnus
    ```
  - **Paluukoodi:** `403 Forbidden`
  - **Vastaus:**
    ```json
    {
        "error": "Forbidden",
        "message": "You do not have sufficient permissions for this action."
    }
    ```

Virheiden käsittelyssä pyritään antamaan käyttäjille mahdollisimman paljon tietoa ongelman syystä, jotta he voivat korjata virheet ja jatkaa sovelluksen käyttöä.

</details>

---

# Testaus

<details><summary>Database Access Layer</summary>

### Yhteyden toimivuus (DAL ↔ RDBMS)
- **Testattava:** Onko tietokantayhteys määritetty oikein? Toimiiko yhteys kaikissa käyttöolosuhteissa?
- **Suoritetut testit:**
  - **testDatabaseConnection**
    - Testaa, että tietokantayhteys on voimassa ja toimii. Yhteys tarkistetaan kutsumalla `connection.isValid(2)`.
    - **Tulos:** Testi varmistaa, että tietokantayhteys toimii odotetusti.
  - **testDatabaseConnectionFailure**
    - Testaa, että tietokantayhteys epäonnistuu virheellisiä asetuksia käyttäen (esim. virheellinen URL).
    - **Tulos:** Testi varmistaa, että sovellus käsittelee epäonnistuneet yhteydet oikein heittämällä `SQLException`.

---

### SQL-kyselyjen oikeellisuus
- **Testattava:** Tuottavatko SQL-kyselyt oikeita ja odotettuja tuloksia?
- **Suoritetut testit:**
  - Testataan repositoriotesteissä.

---

### SQL-kyselyjen suorituskyky
- **Testattava:** Kuinka hyvin kyselyt suoriutuvat erilaisilla tietomäärillä? Mitä tapahtuu suurilla datamäärillä?
- **Suoritetut testit:**
  - **testQueryPerformance**
    - Testaa, että `orderRepository.findAll()` suoritetaan alle sekunnissa.
    - **Tulos:** Suorituskyky mitataan ja varmistetaan, että kysely ei ylitä hyväksyttävää kestoa.

---

### Transaktioiden hallinta
- **Testattava:** Toimivatko transaktiot oikein? Käsitelläänkö virhetilanteet oikein?
- **Suoritetut testit:**
  - Testataan repositoriotesteissä.

---

### Tietokannan konsistenssi ja eheys
- **Testattava:** Säilyvätkö tietokannan rajoitteet (esim. viite-eheys, uniikkius)?
- **Suoritetut testit:**
  - Testataan repositoriotesteissä.

---
</details>

<details><summary> REST API -testaus </summary>

### Reitityksen testaus (RestApiTests)
- **Testattava:** Testataan eri reittien osalta, ohjautuuko reititys oikein ja onko palautunut vastaus odotetunlainen. Lisäksi testataan tilannetta, että reittiä ei ole olemassa.
- **Suoritetut testit:** 
  - **testGetEventByIdRoute**
    - Suoritetaan MockMvc:tä käyttäen GET -pyyntö reitille `/events/1` 
    - Tarkistetaan, että status on `200 OK`
    - Tarkistetaan, että pyyntö ohjautuu metodiin `getEventById`
    - Varmistetaan, että JSON-vastauksessa `eventId` on 1.
    - **Tulos:** Testi varmistaa, että yksittäisen tapahtuman haku toimii odotetulla tavalla.
  - **testGetOrdersRoute**
    - Suoritetaan MockMvc:tä käyttäen GET -pyyntö reitille `/orders` (otettu huomioon reitille vaadittava autentikointi)
    - Tarkistetaan, että status on `200 OK`
    - Tarkistetaan, että pyyntö ohjautuu metodiin `getAllOrders`
    - Tarkistetaan, että JSON-vastauksena on lista tapahtumista.
    - **Tulos:** Testi varmistaa, että yksittäisen tapahtuman haku toimii odotetulla tavalla.
  - **testInvalidRoute**
    - Suoritetaan GET -pyyntö reitille `/invalid-route`
    - Varmistetaan, että vastauksen status on 404 Not Found
    - **Tulos:** Testi varmistaa, että virheellinen pyyntö käsitellään oikein.
  - **testGetTicketsEventStatus**
    - Luodaan ensin mockattuja lippuja tapahtumaan, jonka id on 1
    - Suoritetaan MockMvc:tä käyttäen GET -pyyntö reitille `/tickets/event/1` 
    - Tarkistetaan, että vastauksen status on `200 OK`
    - Tarkistetaan, että vastauksena on JSON-muotoinen lista, jossa jokaisella lipulla on `ticketCode` ja `valid` -kentät
    - **Tulos:** Testi varmistaa, että tapahtumakohtaiset liput palautetaan oikein.
  - **testCreateOrderRoute**
    - Suoritetaan MockMvc:tä käyttäen POST -pyyntö reitille `/orders` sisältäen tilauksen tiedot JSON-muodossa.
    - Tarkistetaan, että vastauksen status on `201 Created`
    - Tarkistetaan, että pyyntö ohjautuu metodiin `newOrder`
    - **Tulos:** Testi varmistaa, että uuden tilauksen luominen toimii odotetusti.

  ---

  </details>

<details><summary>CLIENT-sivujen testaus</summary>

### Index-sivu (ClientIndexTest)
- **Testattava:** Clientin index-sivu.
- **Suoritetut testit:** 
  - **testIndexPageContainsWelcomeText**
    - Suoritetaan MockMvc:tä käyttäen GET -pyyntö reitille `/index`. 
    - Tarkistetaan, että status on `200 OK`
    - Tarkistetaan, että palautettu HTML-sivu sisältää tekstin "Welcome to TicketGuru!".
    - **Tulos:** Testi varmistaa, että clientin index-sivu näyttää oikean tervetulotekstin.
  - **testIndexPageIsNotEmpty**
    - Suoritetaan MockMvc:tä käyttäen GET -pyyntö reitille `/index`. 
    - Tarkistetaan, että status on `200 OK`
    - Tarkistetaan, että pyyntö ohjautuu metodiin `getAllOrders`
    - Tarkistetaan, että vastauksen sisältö ei ole tyhjä merkkijono.
    - **Tulos:** Testi varmistaa, että clientin index-sivu palauttaa sisältöä

  ---

### Ticketdashboard -sivu (ClientTicketdashboardTest)
- **Testattava:** Clientin Ticketdashboard-sivu.
- **Suoritetut testit:** 
  - **testTicketdashboardLoads**
    - Suoritetaan MockMvc:tä käyttäen GET -pyyntö reitille `/ticketdashboard` (otettu huomioon reitille vaadittava autentikointi). 
    - Tarkistetaan, että status on `200 OK`
    - Varmistetaan, että sisältö html-muotoista.
    - Tarkistetaan, että palautettu sisältö sisältää seuraavat tekstit: "Sell tickets", "Event Name", Ticket Quantity" ja "Select the ticket Type".
    - **Tulos:** Testi varmistaa, että clientin Ticketdashboard-sivu latautuu oikein ja näyttää oikeat elementit.
  - **testTicketDashboardPageContainsSellButton**
    - Suoritetaan MockMvc:tä käyttäen GET -pyyntö reitille `/ticketdashboard` (otettu huomioon reitille vaadittava autentikointi). 
    - Tarkistetaan, että status on `200 OK`
    - Tarkistetaan, että palautettu sisältö sisältää tekstin "SELL".
    - **Tulos:** Testi varmistaa, että Ticketdashboard-sivulla on tarvittava painike lippujen myyntiin.
  - **testTicketsSoldSuccessfully**
    - Suoritetaan HTTP POST -pyyntö reitille `/sell` (otettu huomioon reitille vaadittava autentikointi). 
    - Annetaan seuraavat parametrit: Tapahtuman ID: 1, Lippujen määrä: 5, Lipputyyppi: VIP
    - Varmistetaan, että vastauksen status on uudelleenohjaus (3xx Redirection).
    - Varmistetaan, että käyttäjä ohjataan `/ticketdashboard` -sivulle.
    - Varmistetaan, että myynti on onnistunut (sisältää `success` -attribuutin)
    - **Tulos:** Testi varmistaa, että lippujen myynti toimii odotetusti ja käyttäjä saa onnistumisviestin.
  - **testNotEnoughTickets**
    - Suoritetaan HTTP POST -pyyntö reitille `/sell` (otettu huomioon reitille vaadittava autentikointi). 
    - Annetaan seuraavat parametrit: Tapahtuman ID: 1, Lippujen määrä: 999, Lipputyyppi: VIP
    - Varmistetaan, että vastauksen status on uudelleenohjaus (3xx Redirection).
    - Varmistetaan, että käyttäjä ohjataan `/ticketdashboard` -sivulle.
    - Tarkistetaan, että `error` -attribuutti lisätään flash-viestiin ja sen sisältö on "Not enough tickets available.".
    - **Tulos:** Testi varmistaa, että sovellus palauttaa virheviestin, jos lippuja ei ole riittävästi.

  ---
  </details>

 <details><summary> Entiteettien testaus (OrderValidationTest ja OrderEntityTest)</summary>

  - **Testattava**: Order entiteetti.
  - **Suoritetut testit**:
    - **whenSalespersonIsNull_thenValidationFailure**
      - Testataan tilauksen `salesperson`-kentän validointia, kun arvo on `null`.
      - Suoritetaan validointi ja tarkastetaan, että:
        - Validointivirheitä on 1.
        - Virheviesti on: *"Order: Salesperson is required for the order".*
      - **Tulos**: Testi varmistaa, että tilauksen luominen ilman myyjää ei onnistu.
    - **whenOrderDetailsIsEmpty_thenValidationFailure**
      - Testataan tilauksen `orderDetails`-kenttää, kun lista on tyhjä.
      - Suoritetaan validointi ja tarkastetaan, että:
        - Validointivirheitä on 1.
        - Virheviesti on: *"Order: Order must have at least one order detail"*.
      - **Tulos**: Testi varmistaa, että tilausta ei voi luoda ilman vähintään yhtä `orderDetail`-tietoa.
    - **whenTicketsIsEmpty_thenValidationFailure**
      - Testataan tilauksen `tickets`-kenttää, kun lista on tyhjä.
      - Suoritetaan validointi ja tarkastetaan, että:
        - Validointivirheitä on 1.
        - Virheviesti on: *"Order: Tickets must contain at least one ticket"*.
      - **Tulos**: Testi varmistaa, että tilausta ei voi luoda ilman lippuja.
    - **whenOrderDateIsInFuture_thenValidationFailure**
      - Testataan tilauksen `orderDate`-kenttää, kun päivämäärä on tulevaisuudessa.
      - Suoritetaan validointi ja tarkastetaan, että:
        - Validointivirheitä on 1.
        - Virheviesti on: *"Order date cannot be in the future"*.
      - **Tulos**: Testi varmistaa, että tilauksen päivämäärä ei voi olla tulevaisuudessa.
    - **whenAllFieldsAreInvalid_thenValidationFailure**
      - Testataan tilannetta, jossa kaikki `Order`-objektin kentät ovat virheellisiä:
        - `salesperson` on null.
        - `orderDate` on tulevaisuudessa.
        - `tickets` on tyhjä.
        - `orderDetails` on tyhjä.
      - Suoritetaan validointi ja tarkastetaan, että:
        - Validointivirheitä on yhteensä 4.
      - **Tulos**: Testi varmistaa, että virheellisesti täytetty tilaus ei läpäise validointia.
    - **testOrderFieldInitalization**
      - Testataan `Order`-objektin kenttien alustusta:
        - Luodaan uusi `Order`-instanssi ja asetetaan sille kentät.
        - Tarkistetaan:
          - `orderId` on aluksi `null`.
          - `orderDetails`, `tickets`, `salesperson` ja  `orderDate` eivät ole `null`.
          - `orderDetails` ja `tickets` ovat tyhjiä listoja.
        - **Tulos**: Testi varmistaa, että `Order`-objektin kentät alustetaan oikein.
    - **testOrderEntityRelationships**
      - Testataan `Order`-entiteetin relaatioita muihin entiteetteihin:
        - Luodaan `Order`-, `OrderDetails`-, `Salesperson`- ja `Ticket`-objektit.
        - Asetetaan kuuluviksi kyseiseen tilaukseen.
        - Tarkistetaan:
          - `orderDetails`-listassa on yksi elementti.
          - `tickets`-listassa on yksi elementti.
          - `OrderDetails` ja `Ticket` on linkitetty oikein Order-entiteettiin.
        - **Tulos**: Testi varmistaa, että `Order`-entiteetin suhteet muihin entiteetteihin toimivat odotetusti.
    - **testOrderLinking**
      - Testataan entiteetin käyttäytymistä mockatussa persistenssikontekstissa:
        - Luodaan mockattu `EntityManager` ja määritellään sen palauttavan `Order`.
        - Tarkistetaan:
          - `Order` löytyy mockatusta `EntityManager`ista.
          - `Salesperson` on linkitetty oikein palautettuun `Order`-instanssiin.
        - **Tulos**: Testi varmistaa, että `Order`-entiteetti käyttäytyy odotetusti persistenssikontekstissa.

    ---
    </details>

<details><summary>ORM testaus (ORMIntegrationTest ja ORMPerformanceTest)</summary>

  - **Testattava**: Entiteettien ja tietokannan välinen ORM-yhdistäminen, sovelluksen tietokantakyselyiden suorituskyky ja Hibernate-statistiikka.
  - **Suoritetut testit**:
    - **testTicketTypeEntityMapping**
      - Testataan `TicketType`-entiteetin ja tietokannan välistä mappingia.
        - Luodaan uusi `TicketType`-instanssi nimellä "VIP".
        - Tallennetaan entiteetti tietokantaan.
        - Haetaan tallennettu entiteetti `TicketTypeRepository`n avulla.
        - Tarkistetaan:
          - Tallennettu entiteetti löytyy tietokannasta.
          - Haetun entiteetin nimi vastaa tallennettua nimeä.
          - Entiteetin ID ei ole `null`.
      - **Tulos**: Testi varmistaa, että `TicketType`-entiteetti on yhdistetty tietokantaan oikein ja että tallennus- ja hakuprosessi toimivat odotetusti.
    - **testEventTicketTypePerformance**
      - Suoritetaan `EventTicketTypeRepository.findAll()` ja mitataan suoritusaika.
      - Tarkistetaan, että kyselyn kesto on alle 0,1 sekuntia.
      - **Tulos**: Testi varmistaa, että `EventTicketType`-kyselyt toimivat nopeasti.
    - **testEventPerformance**
      - Suoritetaan `EventRepository.findAll()` ja mitataan suoritusaika.
      - Tarkistetaan, että kyselyn kesto on alle 0,1 sekuntia.
      - **Tulos**: Testi varmistaa, että `Event`-kyselyt toimivat nopeasti.
    - **testOrderWithHibernateStatistics**
      - Otetaan Hibernate-statistiikka käyttöön `SessionFactory`ssa.
      - Suoritetaan `OrderRepository.findAll()` ja tarkistetaan suoritettujen kyselyiden määrä.
      - Varmistetaan, että kyselyiden määrä on alle 2.
      - **Tulos**: Testi varmistaa, että `Order`-entiteetin nouto suoritetaan tehokkaasti ilman ylimääräisiä kyselyitä.
    - **testTicketPerformanceWithHibernateStatistics**
      - Otetaan Hibernate-statistiikka käyttöön `SessionFactory`ssa.
      - Suoritetaan `TicketRepository.findAll()` ja tarkistetaan suoritettujen kyselyiden määrä.
      - Varmistetaan, että kyselyiden määrä on alle 2.
      - **Tulos**: Testi varmistaa, että `Ticket`-entiteetin nouto suoritetaan tehokkaasti ilman ylimääräisiä kyselyitä.

      </details>



