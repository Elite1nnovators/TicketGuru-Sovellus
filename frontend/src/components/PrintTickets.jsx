import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useAuth } from './AuthContext'; 
import { QRCodeSVG } from 'qrcode.react'; 

const PrintTickets = () => {
    const { orderId } = useParams(); 
    const { auth } = useAuth();
    const [ticketCodes, setTicketCodes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchTicketCodes = async () => {
            const authHeader = `Basic ${btoa(`${auth.username}:${auth.password}`)}`;
            try {
                const response = await fetch(`https://ticket-guru-sovellus-git-elite-innovators-ticketguru2.2.rahtiapp.fi/api/print-tickets/${orderId}`, {
                    method: 'GET',
                    headers: {
                        'Authorization': authHeader,
                    },
                });

                if (response.ok) {
                    const data = await response.json();
                    setTicketCodes(data.ticketCodes); 
                } else {
                    const errorData = await response.json();
                    setError(`Error: ${errorData.message}`);
                }
            } catch (err) {
              console.log(err)
                setError('Failed to fetch ticket codes.');
            } finally {
                setLoading(false);
            }
        };

        fetchTicketCodes();
    }, [orderId, auth]);

    if (loading) return <p>Loading ticket codes...</p>;
    if (error) return <p>{error}</p>;

    return (
        <div className="container">
            <h1>Print Tickets</h1>
            <p>Order ID: {orderId}</p>
            <div className="d-flex flex-wrap">
                {ticketCodes.map((code, index) => (
                    <div key={index} className="m-3">
                        <QRCodeSVG value={code} size={256} />
                        <p>{code}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default PrintTickets;
