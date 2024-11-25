import { Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import '../css/LandingPage.css';

const LandingPage = () => {
    const navigate = useNavigate()
    const handleSellTicket = () => {
        navigate("/sell-ticket")
    }

    const handleManageEvents = () => {
        navigate("/events")
    };

    const handleShowReports = () => {
        navigate("/sales-reports")
    };

    return (
        <div className="landing-page">
            <h1>Welcome to TicketGuru!</h1>
            <p>Your ticket management system</p>
            <div className="button-container">
                <div className="button" onClick={handleSellTicket}>
                    <span>Sales</span>
                </div>
                <div className="button" onClick={handleManageEvents}>
                    <span>Events</span>
                </div>
                <div className="button" onClick={handleShowReports}>
                    <span>Reports </span>
                </div>
            </div>
        </div>
    )
}

export default LandingPage;