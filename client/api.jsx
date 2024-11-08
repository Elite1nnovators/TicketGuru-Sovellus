import axios from 'axios';

const api = axios.create({
  baseURL: "https://ticket-guruver2-bugivelhot-ticketguru.2.rahtiapp.fi/api",
  auth: {
    username: "user",
    password: "user"
  }
})

export default api