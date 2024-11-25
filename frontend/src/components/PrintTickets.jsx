import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useAuth } from './AuthContext';
import { QRCodeSVG } from 'qrcode.react';
import api from './api';

const PrintTickets = () => {
  const { orderId } = useParams();
  const { auth } = useAuth();
  const [ticketCodes, setTicketCodes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchTicketCodes = async () => {
      try {
        const response = await api.get(`/api/print-tickets/${orderId}`);
        setTicketCodes(response.data.ticketCodes);
      } catch (err) {
        console.log(err);
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
