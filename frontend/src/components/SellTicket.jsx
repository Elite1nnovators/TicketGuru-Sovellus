import { useState, useEffect } from 'react';
import { Button, Form, Spinner, Alert } from 'react-bootstrap';
import api from './api';
import { useNavigate, useParams } from 'react-router-dom';

const SellTicket = () => {
  const { eventId } = useParams();
  const [quantity, setQuantity] = useState('');
  const [ticketType, setTicketType] = useState('');
  const [ticketTypes, setTicketTypes] = useState([]);
  const [eventName, setEventName] = useState(''); // State for event name
  const [responseMessage, setResponseMessage] = useState('');
  const [orderId, setOrderId] = useState(null);
  const [soldAtDoor, setSoldAtDoor] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchEventDetails = async () => {
      try {
        const response = await api.get(`/events/${eventId}`);
        const event = response.data;

        // Set event name and ticket types
        setEventName(event.eventName);
        const fetchedTicketTypes = event.eventTicketTypes.map(
          (ett) => ett.ticketTypeName
        );
        setTicketTypes(fetchedTicketTypes);
        setTicketType(fetchedTicketTypes[0] || '');
        setLoading(false);
      } catch (err) {
        console.error('Error fetching event data:', err);
        setError('Failed to load event data. Please try again later.');
        setLoading(false);
      }
    };

    fetchEventDetails();
  }, [eventId]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const payload = {
      selectedEventId: parseInt(eventId, 10),
      quantity: parseInt(quantity, 10),
      ticketType,
      soldAtDoor,
    };

    try {
      const response = await api.post('/api/sell', payload);
      setOrderId(response.data.orderId);
      setResponseMessage(
        `Ticket sold successfully! Order ID: ${response.data.orderId}`
      );
    } catch (err) {
      console.error(err);
      const errorMsg =
        err.response?.data ||
        'An unexpected error occurred while selling the ticket.';
      setResponseMessage(`Error: ${errorMsg}`);
    }
  };

  const handlePrint = () => {
    if (orderId) {
      navigate(`/print-tickets/${orderId}`);
    }
  };

  if (loading) {
    return (
      <div className="container text-center">
        <Spinner animation="border" role="status" className="mt-5">
          <span className="visually-hidden">Loading...</span>
        </Spinner>
        <p>Loading event details...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="container mt-5">
        <Alert variant="danger">{error}</Alert>
      </div>
    );
  }

  return (
    <div className="container">
      <h1>
        Sell Ticket for Event: {eventName} (ID: {eventId})
      </h1>
      <Form onSubmit={handleSubmit}>
        <Form.Group controlId="formQuantity" className="mt-3">
          <Form.Label>Quantity</Form.Label>
          <Form.Control
            type="number"
            placeholder="Enter Quantity"
            value={quantity}
            onChange={(e) => setQuantity(e.target.value)}
            required
            min="1"
          />
        </Form.Group>

        <Form.Group controlId="formTicketType" className="mt-3">
          <Form.Label>Ticket Type</Form.Label>
          <Form.Control
            as="select"
            value={ticketType}
            onChange={(e) => setTicketType(e.target.value)}
            required>
            {ticketTypes.length > 0 ? (
              ticketTypes.map((type, index) => (
                <option key={index} value={type}>
                  {type}
                </option>
              ))
            ) : (
              <option value="" disabled>
                No ticket types available
              </option>
            )}
          </Form.Control>
        </Form.Group>
        <Form.Group controlId="formSoldAtDoor" className="mt-3">
          <Form.Label>Sold at Door?</Form.Label>
          <Form.Control
            as="select"
            value={soldAtDoor ? 'Y' : 'N'}
            onChange={(e) => setSoldAtDoor(e.target.value === 'Y')}
            required>
            <option value="N">No</option>
            <option value="Y">Yes</option>
          </Form.Control>
        </Form.Group>

        <Button variant="primary" type="submit" className="mt-4">
          Sell Ticket
        </Button>
      </Form>

      {responseMessage && (
        <Alert variant={orderId ? 'success' : 'danger'} className="mt-4">
          {responseMessage}
        </Alert>
      )}

      {orderId && (
        <Button variant="secondary" className="mt-3" onClick={handlePrint}>
          Print Tickets
        </Button>
      )}
    </div>
  );
};

export default SellTicket;
