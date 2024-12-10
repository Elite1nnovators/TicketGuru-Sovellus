import React, { useContext, useState, useEffect } from "react";
import { Button, Card, Form, InputGroup, Container, Row, Col } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import api from "./api";
import EditEvent from "./EditEvent";
import AddEvent from "./AddEvent";

const EventSearch = () => {
  const [events, setEvents] = useState([]);
  const [filteredEvents, setFilteredEvents] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");// Access cart context
  const navigate = useNavigate(); // For navigation

  useEffect(() => {
    fetchEvents();
  }, []);

  const fetchEvents = async () => {
    try {
      const response = await api.get("/events");
      setEvents(response.data);
      setFilteredEvents(response.data);
    } catch (error) {
      console.error("Error fetching events:", error);
    }
  };

  const handleDeleteEvent = async (eventId) => {
    try {
      await api.delete(`/events/${eventId}`);
      setEvents((prev) => prev.filter((event) => event.eventId !== eventId));
      setFilteredEvents((prev) => prev.filter((event) => event.eventId !== eventId));
      alert("Event deleted successfully.");
    } catch (error) {
      console.error("Error deleting event:", error);
      alert("Failed to delete event.");
    }
  };

  const handleSearch = (e) => {
    const value = e.target.value.toLowerCase();
    setSearchTerm(value);
    setFilteredEvents(
      events.filter(
        (event) =>
          event.eventName.toLowerCase().includes(value) ||
          event.eventCity.toLowerCase().includes(value) ||
          event.eventDescription.toLowerCase().includes(value)
      )
    );
  };

  const handleSellTicket = (event) => {
    try {
      console.log("Navigating to sell ticket for:", event);
      if (event) {
        navigate(`/sell-ticket/${event.eventId}`);
      } else {
        alert("Event details not found!");
      }
    } catch (error) {
      console.error("Error in handleSellTicket:", error);
    }
  };

  return (
    <Container>
      <h1 className="text-center my-4">Event Search</h1>
      <AddEvent refreshEvents={fetchEvents} />
      <InputGroup className="mb-4">
        <Form.Control
          type="text"
          placeholder="Search for events..."
          value={searchTerm}
          onChange={handleSearch}
        />
      </InputGroup>
      <Row className="g-4">
        {filteredEvents.map((event) => (
          <Col key={event.eventId} sm={12} md={6} lg={4}>
            <Card>
              <Card.Body>
                <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                  <Card.Title>{event.eventName}</Card.Title>
                  <div style={{ display: "flex", alignItems: "center" }}>
                    <EditEvent event={event} refreshEvents={fetchEvents} />
                    <Button
                      variant="danger"
                      size="sm"
                      className="d-flex align-items-center ms-2"
                      onClick={() => handleDeleteEvent(event.eventId)}
                    >
                      Delete
                    </Button>
                  </div>
                </div>
                <Card.Subtitle className="mb-2 text-muted">
                  {new Date(event.eventDate).toLocaleDateString()} | {event.eventCity}
                </Card.Subtitle>
                <Card.Text>{event.eventDescription}</Card.Text>
                <Card.Text>
                  <strong>Address:</strong> {event.eventAddress}
                  <button
                    style={{
                      display: "block",
                      marginTop: "20px",
                      padding: "10px 20px",
                      backgroundColor: "#007BFF",
                      color: "#fff",
                      border: "none",
                      borderRadius: "5px",
                      cursor: "pointer",
                      textAlign: "center",
                    }}
                    onClick={() => handleSellTicket(event)}
                  >
                    Sell Ticket
                  </button>
                </Card.Text>
                {event.eventTicketTypes?.length > 0 && (
                  <div>
                    <h5>Tickets</h5>
                    {event.eventTicketTypes.map((ticket) => (
                      <div
                        key={ticket.id}
                        className="d-flex justify-content-between align-items-center mb-2"
                      >
                        <span>
                          {ticket.ticketTypeName} - {ticket.price}€ |{" "}
                          <strong>{ticket.ticketsInStock} available</strong>
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
    </Container>
  );
};

export default EventSearch;
