import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'; // For navigation
import api from './api';

const EventSearch = () => {
  const [events, setEvents] = useState([]);
  const [filteredEvents, setFilteredEvents] = useState([]);
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const navigate = useNavigate(); 

  // hakee tapahtumat API:sta
  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const response = await api.get('/events');
        const upcomingEvents = response.data.filter(
          (event) => new Date(event.eventDate) > new Date()
        );
        setEvents(upcomingEvents);
        setFilteredEvents(upcomingEvents);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchEvents();
  }, []);

  // Käsittelee haun
  const handleSearch = (event) => {
    const value = event.target.value.toLowerCase();
    setSearchTerm(value);
    const filtered = events.filter(
      (event) =>
        event.eventId.toString().includes(value) ||
        event.eventName.toLowerCase().includes(value) ||
        event.eventAddress.toLowerCase().includes(value) ||
        event.eventCity.toLowerCase().includes(value)
    );
    setFilteredEvents(filtered);
  };

  // Käsittelee tapahtuman valinnan
  const handleEventSelect = (event) => {
    const selectedEventId = event.target.value;
    const eventDetails = events.find(
      (event) => event.eventId.toString() === selectedEventId
    );
    setSelectedEvent(eventDetails || null);
  };

  // Navigoi oikeaan sell end-pointiin
  const handleSellTicket = () => {
    if (selectedEvent) {
      navigate(`/sell-ticket/${selectedEvent.eventId}`);
    }
  };

  if (loading) return <p>Loading events...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div style={styles.container}>
      <h1 style={styles.header}>Event Search</h1>
      {/* Haku */}
      <input
        type="text"
        placeholder="Search events..."
        value={searchTerm}
        onChange={handleSearch}
        style={styles.searchInput}
      />
      {/* Dropdown valinta eventille */}
      <select
        style={styles.dropdown}
        onChange={handleEventSelect}
        defaultValue=""
      >
        <option value="" disabled>
          Select an event
        </option>
        {filteredEvents.map((event) => (
          <option key={event.eventId} value={event.eventId}>
            {event.eventName}
          </option>
        ))}
      </select>

      {/* Valitun eventin tiedot*/}
      {selectedEvent ? (
        <div style={styles.eventDetails}>
          <EventItem event={selectedEvent} />
          <button style={styles.sellTicketButton} onClick={handleSellTicket}>
            Sell Ticket
          </button>
        </div>
      ) : (
        <p style={styles.placeholderText}>No event selected.</p>
      )}
    </div>
  );
};

const EventItem = ({ event }) => (
  <div style={styles.eventItem}>
    <h2 style={styles.eventName}>{event.eventName}</h2>
    <p>
      <strong>ID Number:</strong> {event.eventId}
    </p>
    <p>
      <strong>Date:</strong>{' '}
      {new Date(event.eventDate).toLocaleDateString('en-GB')}
    </p>
    <p>
      <strong>Address:</strong> {event.eventAddress}
    </p>
    <p>
      <strong>City:</strong> {event.eventCity}
    </p>
    {event.eventDescription && (
      <p>
        <strong>Description:</strong> {event.eventDescription}
      </p>
    )}
    {event.eventTicketTypes && event.eventTicketTypes.length > 0 && (
      <TicketList ticketTypes={event.eventTicketTypes} />
    )}
  </div>
);

const TicketList = ({ ticketTypes }) => (
  <div style={styles.ticketSection}>
    <h3>Ticket Types:</h3>
    <ul style={styles.ticketList}>
      {ticketTypes.map((ticketType) => (
        <li key={ticketType.id} style={styles.ticketItem}>
          <p>
            <strong>Ticket Type Name:</strong> {ticketType.ticketTypeName}
          </p>
          <p>
            <strong>Price:</strong> ${ticketType.price.toFixed(2)}
          </p>
          <p>
            <strong>Tickets in Stock:</strong> {ticketType.ticketsInStock}
          </p>
        </li>
      ))}
    </ul>
  </div>
);

const styles = {
  container: {
    padding: '20px',
    fontFamily: 'Arial, sans-serif',
  },
  header: {
    textAlign: 'center',
    marginBottom: '20px',
  },
  searchInput: {
    padding: '10px',
    width: '100%',
    marginBottom: '20px',
    border: '1px solid #ccc',
    borderRadius: '5px',
  },
  dropdown: {
    padding: '10px',
    width: '100%',
    marginBottom: '20px',
    border: '1px solid #ccc',
    borderRadius: '5px',
  },
  placeholderText: {
    textAlign: 'center',
    color: '#888',
    fontStyle: 'italic',
  },
  eventDetails: {
    marginTop: '20px',
  },
  eventItem: {
    padding: '15px',
    border: '1px solid #eee',
    borderRadius: '8px',
    boxShadow: '0 2px 5px rgba(0, 0, 0, 0.1)',
  },
  eventName: {
    margin: '0 0 10px',
    color: '#333',
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
  ticketSection: {
    marginTop: '15px',
    paddingTop: '10px',
    borderTop: '1px solid #ddd',
  },
  ticketList: {
    listStyle: 'none',
    padding: 0,
    margin: 0,
  },
  ticketItem: {
    marginBottom: '10px',
  },
};

export default EventSearch;
