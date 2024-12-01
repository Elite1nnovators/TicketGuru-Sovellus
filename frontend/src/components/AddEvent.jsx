import { useState } from "react";
import { Button } from 'react-bootstrap';
import api from './api';
import EventForm from "./EventForm";

export default function AddEvent( {addEvent} ) {
    const [event, setEvent] = useState( {
        eventName: "",
        eventDate: "",
        eventAddress: "",
        eventCity: "",
        eventDescription: "",
        eventTicketTypes: [
            {
                ticketType: {
                    id: "",
                    name: ""
                },
                price: 0,
                ticketsInStock: 0,
            },
        ],
    });

    const handleChange = (event) => {
        const { name, value } = event.target;
        setEvent((prevEvent) => ({
            ...prevEvent,
            [name]: value,
        }));
    };

    const handleTicketTypeChange = (index, e) => {
        const { name, value } = e.target;
        const updatedTicketTypes = [...event.eventTicketTypes];
        if (name === "ticketTypeName") {
            updatedTicketTypes[index].ticketType.name = value;
        } else if (name === "ticketTypeId") {
            updatedTicketTypes[index].ticketType.id = value;
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
                {
                    ticketType: {
                        id: "",
                        name: ""
                    },
                    price: 0,
                    ticketsInStock: 0,
                },
            ],
        }));
    };

    const handleSave = async () => {
        try {
            // Jos lipputyyppi on uusi, tallenna se ensin
            const savedTicketTypes = await Promise.all(
                event.eventTicketTypes.map(async (ticket) => {
                    if (!ticket.ticketType.id) {
                        // Tallenna uusi lipputyyppi
                        const response = await api.post('/tickettypes', { name: ticket.ticketType.name });
                        return response.data;  // Palautetaan tallennettu lipputyyppi
                    }
                    return { id: ticket.ticketType.id, name: ticket.ticketType.name };  // Jos ID on jo, käytetään sitä
                })
            );
    
    // Muodostetaan tapahtuma, joka viittaa tallennettuihin lipputyyppeihin
    const formattedEvent = {
                eventName: event.eventName,
                eventDate: new Date(event.eventDate).toISOString(),
                eventAddress: event.eventAddress,
                eventCity: event.eventCity,
                eventDescription: event.eventDescription,
                eventTicketTypes: event.eventTicketTypes.map((ticket, index) => ({
                    ticketType: {
                        id: savedTicketTypes[index].id,
                        name: savedTicketTypes[index].name,
                    },
                    price: ticket.price,
                    ticketsInStock: ticket.ticketsInStock,
                })),
            };
    
            // Lähetetään tapahtuma
            await addEvent(formattedEvent);
            handleClose();
            resetEvent();
        } catch (error) {
            console.error("Error adding event:", error);
        }
    };

    const resetEvent = () => {
        setEvent({ // Lomakkeen nollaus
            eventName: "",
            eventDate: "",
            eventAddress: "",
            eventCity: "",
            eventDescription: "",
            eventTicketTypes: [
                {
                    ticketType: {
                        id: "",
                        name: ""
                    },
                    price: 0,
                    ticketsInStock: 0,
                },
            ],
        });
    };

    const [show, setShow] = useState(false);


    const handleShow = () => {
        setShow(true);
    };
    const handleClose = () => {
        setShow(false);
    };

    return (
        <>
        <div className="d-grid gap-2 mb-4">
        <Button onClick={handleShow} variant='outline-secondary' size='lg'>Add Event</Button>
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