import React, { useContext, useState, useEffect } from 'react';
import { Button, Card, Form, InputGroup, Container, Row, Col } from 'react-bootstrap';
import api from './api';
import { useNavigate } from 'react-router-dom';
import AddEvent from './AddEvent';
import EditEvent from './EditEvent';
import { CreditCardIcon, TrashIcon } from '@heroicons/react/24/outline';


const EventSearch = () => {
  const [events, setEvents] = useState([]);
  const [filteredEvents, setFilteredEvents] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');

  
const navigate = useNavigate(); 

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const response = await api.get('/events');
        setEvents(response.data);
        setFilteredEvents(response.data);
      } catch (error) {
        console.error('Error fetching events:', error);
      }
    };
    fetchEvents();
  }, []);

  const handleSearch = (e) => {
    const value = e.target.value.toLowerCase();
    setSearchTerm(value);
    const filtered = events.filter(event =>
      event.eventName.toLowerCase().includes(value) ||
      event.eventCity.toLowerCase().includes(value) ||
      event.eventDescription.toLowerCase().includes(value)
    );
    setFilteredEvents(filtered);
  };

  // Luo uusi tapahtuma
  const addEvent = async (event) => {
    console.log("Event structure:", JSON.stringify(event, null, 2));
    console.log(event);
    try {
      // luo uuden tapahtuman
      const response = await api.post('/events', event);
      console.log("Event added:", response);
  
      // Lisäämisen jälkeen hakee kaikki tapahtumat
      const updatedEvents = await api.get('/events');
      setEvents(updatedEvents.data);
      setFilteredEvents(updatedEvents.data);  // Päivittää myös filtterin
    } catch (error) {
      console.error("Error adding event:", error.response ? error.response.data : error);
      alert("Error adding event: " + (error.response ? error.response.data.message : error.message));
    }
  };

  // Käsittele muokkaus
  const editEvent = async (eventId, updatedEvent) => {
    try {
      const response = await api.put(`/events/${eventId}`, updatedEvent);
      console.log("Event updated:", response);
      await fetchEvents();
    } catch {
      console.error("Error updating event:", error.response ? error.response.data : error);
    }
  }

  //Käsittele poisto
  const handleDeleteEvent = async () => {
    if (selectedEvent) {
      const confirmed = window.confirm(`Are you sure you want to delete event ${selectedEvent.eventName}?`);
      if (confirmed) {
        try {
          await api.delete(`/events/${selectedEvent.eventId}`);
          setSelectedEvent(null); // Tyhjennä valittu tapahtuma
          await fetchEvents();
          alert('Event deleted successfully');
        } catch (error) {
          console.error('Error deleting event:', error);
          alert('Error deleting event');
        }
      }
    }
  };


  // Navigoi oikeaan sell end-pointiin
  const handleSellTicket = (event) => {
    try {
      console.log('Navigating to sell ticket for:', event);
      if (event) {
        navigate(`/sell-ticket/${event.eventId}`);
      } else {
        alert('Event details not found!');
      }
    } catch (error) {
      console.error('Error in handleSellTicket:', error);
    }
  };


  return (
    <Container>
      <h1 className="text-center my-4">Event Search</h1>
      
      <AddEvent addEvent={addEvent} />

      <InputGroup className="mb-4">
        <Form.Control
          type="text"
          placeholder="Search for events..."
          value={searchTerm}
          onChange={handleSearch}
        />
      </InputGroup>
      <Row className="g-4">
        {filteredEvents.map(event => (
          <Col key={event.eventId} sm={12} md={6} lg={4}>
            <Card>
              <Card.Body>
                <Card.Title>{event.eventName}</Card.Title>
                <Card.Subtitle className="mb-2 text-muted">
                  {new Date(event.eventDate).toLocaleDateString()} | {event.eventCity}
                </Card.Subtitle>
                <Card.Text>{event.eventDescription}</Card.Text>
                <Card.Text>
                  <strong>Address:</strong> {event.eventAddress}
                 <button style={styles.sellTicketButton} onClick={() => handleSellTicket(event)}>
              Sell Ticket
                  </button>
                  <button style={styles.deleteEventButton} onClick={handleDeleteEvent} className="d-flex align-items-center">
          <TrashIcon width={20} height={20} className='me-2' />
            Delete
          </button>
                </Card.Text>
                {event.eventTicketTypes.length > 0 && (
                  <div>
                    <h5>Tickets</h5>
                    {event.eventTicketTypes.map(ticket => (
                      <div key={ticket.id} className="d-flex justify-content-between align-items-center mb-2">
                        <span>
                          {ticket.ticketTypeName} - {ticket.price}€ | <strong>{ticket.ticketsInStock} available</strong>
                        </span>                        
                      </div>
                    ))}
                  </div>
                )}
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
      {filteredEvents.length === 0 && (
        <p className="text-center">No events found. Try a different search term.</p>
      )}
    </Container>
  );
};

const styles = {
  deleteEventButton: {
    display: 'block',
    marginTop: '20px',
    padding: '10px 20px',
    backgroundColor: '#007BFF',
    color: '#fff',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
    textAlign: 'center',
  },
  sellTicketButton: {
    display: 'block',
    marginTop: '20px',
    padding: '10px 20px',
    backgroundColor: '#007BFF',
    color: '#fff',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
    textAlign: 'center',
  },
}

export default EventSearch;
