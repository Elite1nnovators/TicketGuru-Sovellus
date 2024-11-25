import axios from 'axios';

//const BASE_URL = 'https://ticket-guru-sovellus-git-elite-innovators-ticketguru2.2.rahtiapp.fi';
const BASE_URL = 'http://localhost:8080';

const api = axios.create({
  baseURL: BASE_URL,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use(
  (config) => {
    const auth = JSON.parse(localStorage.getItem('auth'));
    if (auth && auth.username && auth.password) {
      config.headers['Authorization'] = `Basic ${btoa(
        `${auth.username}:${auth.password}`
      )}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

api.interceptors.response.use(
  (response) => response,
  (error) => {
    return Promise.reject(error);
  }
);

export default api;
