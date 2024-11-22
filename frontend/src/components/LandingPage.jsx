import { Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
const LandingPage = () => {
    const navigate = useNavigate()
    const handleSellTicket = () => {
        navigate("/sell-ticket")
    }
    return (
        <div>
            <h1>Welcome to TicketGuru!</h1>
            <p>Your ticket management system</p>
            <Button onClick={handleSellTicket}>Sell ticket</Button>
        </div>
    )
}

export default LandingPage;