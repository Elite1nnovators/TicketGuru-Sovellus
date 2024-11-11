 // Hakee käyttäjätunnuksen ja salasanan sessionStoragesta
 const username = sessionStorage.getItem('username');
 const password = sessionStorage.getItem('password');

 // Tarkistaa, onko käyttäjätiedot olemassa
 if (!username || !password) {
   // Jos tietoja ei ole, ohjataan käyttäjä takaisin login-sivulle
   window.location.href = '/bugivelhot';
 }

 // Funktio, joka luo Authorization-headerin perusautentikointia varten
 function getAuthHeader() {
   const credentials = `${username}:${password}`;
   const encodedCredentials = btoa(credentials); // Base64-koodaus
   return {
     'Authorization': `Basic ${encodedCredentials}`
   };
 }

 // Muuttuja lipun tiedoille
 let ticketData = null;

 // Funktio lipun hakemiseen
 async function fetchTicket() {
   const code = document.getElementById('ticketCode').value;
   console.log("Fetching ticket with code:", code);

   const response = await fetch(`https://ticket-guruver2-bugivelhot-ticketguru.2.rahtiapp.fi/api/liput?koodi=${code}`, {
     headers: getAuthHeader()  // Lisää autentikointitiedot headeriin
   });

   if (!response.ok) {
     document.getElementById('ticketResult').textContent = 'Error: Failed to fetch the ticket.';
     return;
   }

   const data = await response.json();
   if (data && Object.keys(data).length > 0) {
     ticketData = data;  // Tallenna lipun tiedot muuttujaan
     document.getElementById('ticketResult').textContent = `Ticket found: ${JSON.stringify(data, null, 2)}`;
   } else {
     document.getElementById('ticketResult').textContent = 'No ticket found with the provided code.';
   }
 }

 // Funktio lipun merkitsemiseen käytetyksi
 async function markTicketAsUsed() {
   if (!ticketData) {
     document.getElementById('useTicketResult').textContent = 'Error: No ticket data found. Please fetch a ticket first.';
     return;
   }

   // Hakee lipun id:n tallennetusta data:sta, käyttäen kenttää "lippuId"
   const ticketId = ticketData.lippuId;
   console.log("Marking ticket as used with ticket ID:", ticketId);

   const response = await fetch(`https://ticket-guruver2-bugivelhot-ticketguru.2.rahtiapp.fi/api/liput/${ticketId}`, {
     method: 'PATCH',
     headers: {
       'Content-Type': 'application/json',
       ...getAuthHeader()  // Lisää autentikointitiedot headeriin
     },
     body: JSON.stringify({ tila: 0 })  // Merkkaa lippu käytetyksi
   });

   console.log("Response status:", response.status);  
   console.log("Response headers:", response.headers);

   if (response.ok) {
     const result = await response.json();
     document.getElementById('useTicketResult').textContent = JSON.stringify(result, null, 2);
   } else {
     document.getElementById('useTicketResult').textContent = `Error: ${response.statusText}`;
   }
 }