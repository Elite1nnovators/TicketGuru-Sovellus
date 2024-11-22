import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginForm from './components/LoginForm';
import LandingPage from './components/LandingPage';
import SellTicket from './components/SellTicket';
import PrintTickets from './components/PrintTickets';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginForm />} />
        <Route path="/home" element={<LandingPage />} />
        <Route path="/sell-ticket" element={<SellTicket />} />
        <Route path="/print-tickets/:orderId" element={<PrintTickets />} />
      </Routes>
    </Router>
  );
}

export default App;
