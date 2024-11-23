import { BrowserRouter as Router, Routes, Route, useLocation } from 'react-router-dom';
import LoginForm from './components/LoginForm';
import LandingPage from './components/LandingPage';
import SellTicket from './components/SellTicket';
import PrintTickets from './components/PrintTickets';
import Navbar from './components/Navbar';

function App() {
  return (
    <Router>
    <ConditionalNavbar />
      <Routes>
        <Route path="/" element={<LoginForm />} />
        <Route path="/home" element={<LandingPage />} />
        <Route path="/sell-ticket" element={<SellTicket />} />
        <Route path="/print-tickets/:orderId" element={<PrintTickets />} />
      </Routes>
    </Router>
  );
}

function ConditionalNavbar() {
  const location = useLocation();

  return location.pathname === "/" ? null : <Navbar />;
}

export default App;
