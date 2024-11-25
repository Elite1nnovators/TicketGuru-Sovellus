import { useAuth } from './AuthContext'; 
import React, { useState, useEffect } from "react";

const EventSearch = () => {
  const { auth } = useAuth();
  const [events, setEvents] = useState([]);
  const [filteredEvents, setFilteredEvents] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Fetch events from the API
  useEffect(() => {
    const fetchEvents = async () => {
      const authHeader = `Basic ${btoa(`${auth.username}:${auth.password}`)}`;
      try {
        const response = await fetch("https://ticket-guru-sovellus-git-elite-innovators-ticketguru2.2.rahtiapp.fievents", {
          method: 'GET',
          headers: {
            'Authorization': authHeader,
            'Content-Type': 'application/json', // Optional, depending on your server requirements
          },
        });

        if (!response.ok) {
          throw new Error("Failed to fetch events");
        }

        const data = await response.json();
        // Filter only upcoming events
        const upcomingEvents = data.filter(
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

  // Handle search term changes
  const handleSearch = (event) => {
    const value = event.target.value.toLowerCase();
    setSearchTerm(value);

    const filtered = events.filter((event) =>
      event.eventId.toString().includes(value) || // Match eventId
      event.eventName.toLowerCase().includes(value) ||
      event.eventAddress.toLowerCase().includes(value) ||
      event.eventCity.toLowerCase().includes(value)
    );

    setFilteredEvents(filtered);
  };

  if (loading) return <p>Loading events...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div>
      <h1>Upcoming Events</h1>
      <input
        type="text"
        placeholder="Search events..."
        value={searchTerm}
        onChange={handleSearch}
        style={{ padding: "10px", marginBottom: "20px", width: "100%" }}
      />
      <ul>
        {filteredEvents.map((event, index) => (
          <li key={index} style={{ marginBottom: "20px", borderBottom: "1px solid #ccc", paddingBottom: "15px" }}>
            <h2>{event.eventName}</h2>
            <p><strong>ID Number:</strong> {event.eventId}</p>
            <p><strong>Date:</strong> {new Date(event.eventDate).toLocaleDateString("en-GB")}</p>
            <p><strong>Address:</strong> {event.eventAddress}</p>
            <p><strong>City:</strong> {event.eventCity}</p>
            {event.eventDescription && <p><strong>Description:</strong> {event.eventDescription}</p>}
            {event.eventTicketTypes && event.eventTicketTypes.length > 0 && (
              <div>
                <h3>Ticket Types:</h3>
                <ul>
                  {event.eventTicketTypes.map((ticketType) => (
                    <li key={ticketType.id}>
                      <p><strong>Ticket ID:</strong> {ticketType.id}</p>
                      <p><strong>Price:</strong> ${ticketType.price.toFixed(2)}</p>
                      <p><strong>Tickets in Stock:</strong> {ticketType.ticketsInStock}</p>
                    </li>
                  ))}
                </ul>
              </div>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default EventSearch;
