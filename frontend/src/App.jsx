import {
  BrowserRouter as Router,
  Routes,
  Route,
  useLocation,
} from 'react-router-dom';
import LoginForm from './components/LoginForm';
import LandingPage from './components/LandingPage';
import SellTicket from './components/SellTicket';
import PrintTickets from './components/PrintTickets';
import Navbar from './components/Navbar';
import SalesReports from './components/SalesReports';
import EventSearch from './components/EventSearch';
import ShoppingCart from './components/ShoppingCart';
import UserDashboard from './components/UserDashvoard';

function App() {
  return (
    <Router>
      <ConditionalNavbar />
      <Routes>
        <Route path="/" element={<LoginForm />} />
        <Route path="/home" element={<LandingPage />} />
        <Route path="/sell-ticket/:eventId" element={<SellTicket />} />
        <Route path="/print-tickets/:orderId" element={<PrintTickets />} />
        <Route path="/sales-reports" element={<SalesReports />} />
        <Route path="/events" element={<EventSearch />} />
        <Route path="/shopping-cart" element={<ShoppingCart />} />
        <Route path="/user-dashboard" element={<UserDashboard />} />
      </Routes>
    </Router>
  );
}

function ConditionalNavbar() {
  const location = useLocation();

  return location.pathname === '/' ? null : <Navbar />;
}

export default App;
