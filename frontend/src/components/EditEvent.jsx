import { useEffect, useState } from "react";
import EventForm from "./EventForm";
import { Button } from 'react-bootstrap';


// TÄMÄ ON KESKEN, EI TOIMI!!
export default function EditEvent({ selectedEvent, editEvent }) {
    const [show, setShow] = useState(false);
    const [event, setEvent] = useState(selectedEvent);

    // Muokkaa lipputyyppiä
    const handleTicketTypeChange = (index, e) => {
        const { name, value } = e.target;
        const updatedTicketTypes = [...event.eventTicketTypes];
        if (name === "ticketTypeName") {
            updatedTicketTypes[index].ticketType = {
                ...updatedTicketTypes[index].ticketType,
                name: value,
            };
        } else {
            updatedTicketTypes[index][name] = value;
        }
        setEvent((prevEvent) => ({
            ...prevEvent,
            eventTicketTypes: updatedTicketTypes,
        }));
    };

    const handleAddTicketType = () => {
        setEvent((prevEvent) => ({
            ...prevEvent,
            eventTicketTypes: [
                ...prevEvent.eventTicketTypes,
                { ticketType: { id: "", name: "" }, price: 0, ticketsInStock: 0 },
            ],
        }));
    };

    // Päivitä tapahtuma
    const handleSave = () => {
        editEvent(selectedEvent.eventId, event);
        handleClose();
    };

    // Päivitä lomakkeen kentät
    const handleChange = (e) => {
        const { name, value } = e.target;
        setEvent((prevEvent) => ({
            ...prevEvent,
            [name]: value,
        }));
    };

    const handleShow = () => {
        setShow(true);
    };
    const handleClose = () => {
        setShow(false);
    };

    useEffect(() => {
        setEvent(selectedEvent);
    }, [selectedEvent]);

    return (
        <>
            <div className="d-grid gap-2 mb-4">
                <Button onClick={handleShow}>Edit</Button>
            </div>
            <EventForm
                show={show}
                event={event}
                handleChange={handleChange}
                handleTicketTypeChange={handleTicketTypeChange}
                handleAddTicketType={handleAddTicketType}
                handleSave={handleSave}
                handleClose={handleClose}
            />
        </>
    );
}