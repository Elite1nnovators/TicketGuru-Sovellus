import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginForm from './components/LoginForm';
import LandingPage from './components/LandingPage'; 

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<LoginForm />} />
                <Route path="/home" element={<LandingPage />} />
            </Routes>
        </Router>
    );
}

export default App;