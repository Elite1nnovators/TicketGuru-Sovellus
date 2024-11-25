import { useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { useAuth } from './AuthContext';
import { useNavigate } from 'react-router-dom';

const SellTicket = () => {
  const { auth } = useAuth();
  const [selectedEventId, setSelectedEventId] = useState(''); // TODO Decide whether to look for events here, or in LandingPage
  const [quantity, setQuantity] = useState('');
  const [ticketType, setTicketType] = useState('Basic');
  const [responseMessage, setResponseMessage] = useState('');
  const [orderId, setOrderId] = useState(null);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const payload = {
      selectedEventId: parseInt(selectedEventId, 10),
      quantity: parseInt(quantity, 10),
      ticketType,
    };

    const authHeader = `Basic ${btoa(`${auth.username}:${auth.password}`)}`;

    try {
      const response = await fetch('https://ticket-guru-sovellus-git-elite-innovators-ticketguru2.2.rahtiapp.fi/api/sell', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: authHeader,
        },
        body: JSON.stringify(payload),
      });

      if (response.ok) {
        const data = await response.json();
        setOrderId(data.orderId);
        setResponseMessage(
          `Ticket sold successfully! Order ID: ${data.orderId}`
        );
      } else {
        const error = await response.json();
        setResponseMessage(`Error: ${error.message}`);
      }
    } catch (err) {
      console.log(err);
      setResponseMessage('An error occurred while selling the ticket.');
    }
  };

  const handlePrint = () => {
    if (orderId) {
      navigate(`/print-tickets/${orderId}`);
    }
  };

  return (
    <div className="container">
      <h1>Sell Ticket</h1>
      <Form onSubmit={handleSubmit}>
        <Form.Group controlId="formEventId">
          <Form.Label>Event ID</Form.Label>
          <Form.Control
            type="number"
            placeholder="Enter Event ID"
            value={selectedEventId}
            onChange={(e) => setSelectedEventId(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group controlId="formQuantity">
          <Form.Label>Quantity</Form.Label>
          <Form.Control
            type="number"
            placeholder="Enter Quantity"
            value={quantity}
            onChange={(e) => setQuantity(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group controlId="formTicketType">
          <Form.Label>Ticket Type</Form.Label>
          <Form.Control
            as="select"
            value={ticketType}
            onChange={(e) => setTicketType(e.target.value)}>
            <option value="Basic">Basic</option>
            <option value="VIP">VIP</option>
          </Form.Control>
        </Form.Group>

        <Button variant="primary" type="submit" className="mt-3">
          Sell Ticket
        </Button>
      </Form>
      {responseMessage && <p className="mt-3">{responseMessage}</p>}
      {orderId && (
        <Button variant="secondary" className="mt-3" onClick={handlePrint}>
          Print Tickets
        </Button>
      )}
    </div>
  );
};

export default SellTicket;
