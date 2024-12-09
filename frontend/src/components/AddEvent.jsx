import { useState } from "react";
import { Button } from "react-bootstrap";
import api from "./api";
import EventForm from "./EventForm";

export default function AddEvent({ refreshEvents }) {
  const [show, setShow] = useState(false);
  const [newEvent, setNewEvent] = useState({
    eventName: "",
    eventDate: "",
    eventAddress: "",
    eventCity: "",
    eventDescription: "",
    eventTicketTypes: [
      {
        ticketTypeName: "",
        price: 0,
        ticketsInStock: 0,
      },
    ],
  });

  const handleEventChange = (e) => {
    const { name, value } = e.target;
    setNewEvent((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleTicketTypeChange = (index, e) => {
    const { name, value } = e.target;
    const updatedTicketTypes = [...newEvent.eventTicketTypes];
    updatedTicketTypes[index][name] = value;
    setNewEvent((prev) => ({ ...prev, eventTicketTypes: updatedTicketTypes }));
  };

  const handleAddTicketType = () => {
    setNewEvent((prev) => ({
      ...prev,
      eventTicketTypes: [
        ...prev.eventTicketTypes,
        { ticketTypeName: "", price: 0, ticketsInStock: 0 },
      ],
    }));
  };

  const handleDeleteTicketType = (index) => {
    const updatedTicketTypes = newEvent.eventTicketTypes.filter(
      (_, i) => i !== index
    );
    setNewEvent((prev) => ({ ...prev, eventTicketTypes: updatedTicketTypes }));
  };

  const handleSave = async () => {
    try {
      // Step 1: Create the event
      const eventPayload = {
        eventName: newEvent.eventName,
        eventDate: newEvent.eventDate,
        eventAddress: newEvent.eventAddress,
        eventCity: newEvent.eventCity,
        eventDescription: newEvent.eventDescription,
      };
  
      const eventResponse = await api.post("/events", eventPayload);
      const createdEvent = eventResponse.data;
      console.log("Created Event:", createdEvent);
  
      // Step 2: Add ticket types
      for (const ticket of newEvent.eventTicketTypes) {
        const ticketPayload = {
          eventId: createdEvent.eventId,
          ticketTypeName: ticket.ticketTypeName,
          price: Number(ticket.price),
          ticketsInStock: Number(ticket.ticketsInStock),
        };
  
        const ticketResponse = await api.post("/api/eventtickettypes", ticketPayload);
        console.log("Created Ticket Type:", ticketResponse.data);
      }
  
      alert("Event and ticket types added successfully!");
      refreshEvents();
      handleClose();
    } catch (error) {
      console.error("Error adding event or ticket types:", error);
      alert("Failed to add event or ticket types.");
    }
  };
  

  const handleClose = () => {
    setShow(false);
    setNewEvent({
      eventName: "",
      eventDate: "",
      eventAddress: "",
      eventCity: "",
      eventDescription: "",
      eventTicketTypes: [
        {
          ticketTypeName: "",
          price: 0,
          ticketsInStock: 0,
        },
      ],
    });
  };

  const handleShow = () => setShow(true);

  return (
    <>
      <Button onClick={handleShow} variant="outline-secondary" size="lg">
        Add Event
      </Button>
      <EventForm
        show={show}
        event={newEvent}
        handleChange={handleEventChange}
        handleTicketTypeChange={handleTicketTypeChange}
        handleAddTicketType={handleAddTicketType}
        handleDeleteTicketType={handleDeleteTicketType}
        onSave={handleSave}
        onClose={handleClose}
      />
    </>
  );
}
