import { useEffect, useState } from "react";
import EventForm from "./EventForm";
import { Button } from "react-bootstrap";
import { PencilIcon } from "@heroicons/react/24/outline";
import api from "./api";

export default function EditEvent({ event, refreshEvents }) {
  const [show, setShow] = useState(false);
  const [editedEvent, setEditedEvent] = useState({ ...event });
  const [ticketTypesToDelete, setTicketTypesToDelete] = useState([]);

  // Initialize editedEvent when the component receives a new event prop
  useEffect(() => {
    setEditedEvent({ ...event });
    setTicketTypesToDelete([]);
  }, [event]);

  // Update event fields
  const handleChange = (e) => {
    const { name, value } = e.target;
    setEditedEvent((prev) => ({ ...prev, [name]: value }));
  };

  // Handle ticket type changes
  const handleTicketTypeChange = (index, e) => {
    const { name, value } = e.target;
    const updatedTicketTypes = [...editedEvent.eventTicketTypes];
    updatedTicketTypes[index][name] = value;
    console.log("upadted ticketTypes", updatedTicketTypes);
    setEditedEvent((prev) => ({
      ...prev,
      eventTicketTypes: updatedTicketTypes,
    }));
  };

  const handleAddTicketType = () => {
    setEditedEvent((prev) => ({
      ...prev,
      eventTicketTypes: [
        ...prev.eventTicketTypes,
        { id: null, ticketTypeName: "", price: 0, ticketsInStock: 0 },
      ],
    }));
  };

  const handleDeleteTicketType = (index) => {
    const updatedTicketTypes = [...editedEvent.eventTicketTypes];
    const [removedTicketType] = updatedTicketTypes.splice(index, 1);

    if (removedTicketType.id) {
      setTicketTypesToDelete((prev) => [...prev, removedTicketType.id]);
    }

    setEditedEvent((prev) => ({
      ...prev,
      eventTicketTypes: updatedTicketTypes,
    }));
  };

  const handleSave = async () => {
    try {

      const patchData = {};
      for (const key in editedEvent) {
        if (key !== "eventTicketTypes" && editedEvent[key] !== event[key]) {
          patchData[key] = editedEvent[key];
        }
      }

      if (Object.keys(patchData).length > 0) {
        await api.patch(`/events/${event.eventId}`, patchData);
      }


      for (const ticket of editedEvent.eventTicketTypes) {
        if (!ticket.id) {

          const price = Number(ticket.price);
          const ticketsInStock = Number(ticket.ticketsInStock);
          const payload = {
            eventId: event.eventId,
            ticketTypeName: ticket.ticketTypeName,
            price: price,
            ticketsInStock: ticketsInStock,
          };
          console.log("Payload for new ticket type:", payload);

          await api.post("/api/eventtickettypes", payload);
        } else {
          console.log("Updating price and ticketsInStock:", {
            price: ticket.price,
            ticketsInStock: ticket.ticketsInStock,
          });

          await api.patch(`/api/eventtickettypes/${ticket.id}`, {
            price: Number(ticket.price),
            ticketsInStock: Number(ticket.ticketsInStock),
          });
        }
      }

      for (const ticketId of ticketTypesToDelete) {
        console.log("Deleting ticket type with ID:", ticketId);
        await api.delete(`/api/eventtickettypes/${ticketId}`);
      }

      alert("Event updated successfully!");
      refreshEvents();
      handleClose();
    } catch (error) {
      console.error("Error updating event:", error);
      alert("Error updating event.");
    }
  };

  const handleShow = () => setShow(true);
  const handleClose = () => setShow(false);

  return (
    <>
      <Button variant="primary" size="sm" onClick={handleShow}>
        Edit
        <PencilIcon width={15} height={15} />
      </Button>
      <EventForm
        show={show}
        event={editedEvent}
        handleChange={handleChange}
        handleTicketTypeChange={handleTicketTypeChange}
        handleAddTicketType={handleAddTicketType}
        handleDeleteTicketType={handleDeleteTicketType}
        onSave={handleSave}
        onClose={handleClose}
      />
    </>
  );
}
