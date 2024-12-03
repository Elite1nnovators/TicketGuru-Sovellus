import { useEffect, useState } from "react";
import EventForm from "./EventForm";
import { Button } from 'react-bootstrap';
import { PencilIcon } from '@heroicons/react/24/outline';
import api from './api';



export default function EditEvent({ event, editEvent }) {
    const [show, setShow] = useState(false);
    const [editedEvent, setEditedEvent] = useState(event);

    // Muokkaa lipputyyppiä
    const handleTicketTypeChange = (index, e) => {
        const { name, value } = e.target;
    const updatedTicketTypes = [...editedEvent.eventTicketTypes];

    if (name === "ticketTypeName") {
        updatedTicketTypes[index].ticketTypeName = value;
    } else {
        updatedTicketTypes[index][name] = value;
    }

    setEditedEvent((prevEvent) => ({
        ...prevEvent,
        eventTicketTypes: updatedTicketTypes,
    }));
    };



    const handleAddTicketType = () => {
        setEditedEvent((prevEvent) => ({
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




    // Päivitä tapahtuma
    const handleSave = async () => {
        try {
            // Tallenna uudet lipputyypit, jos niitä on
            const savedTicketTypes = await Promise.all(
                editedEvent.eventTicketTypes.map(async (ticket) => {
                    if (!ticket.ticketType || !ticket.ticketType.id) {
                        // Tallenna uusi lipputyyppi
                        const response = await api.post('/tickettypes', { name: ticket.ticketTypeName });
                        return response.data; // Palautetaan tallennettu lipputyyppi
                    }
                    return ticket.ticketType; // Käytä olemassa olevaa
                })
            );
    
            // Muodosta tapahtuma tallennettavaksi
            const formattedEvent = {
                ...editedEvent,
                eventTicketTypes: editedEvent.eventTicketTypes.map((ticket, index) => ({
                    ticketType: {
                        id: savedTicketTypes[index].id,
                        name: savedTicketTypes[index].name,
                    },
                    price: ticket.price,
                    ticketsInStock: ticket.ticketsInStock,
                })),
            };
    
            // Käytä editEvent-funktiota tallennukseen
            await editEvent(formattedEvent);
            handleClose();
        } catch (error) {
            console.error("Error adding event:", error);
        }
    };



    // Päivitä lomakkeen kentät
    const handleChange = (e) => {
        const { name, value } = e.target;
        setEditedEvent((prevEvent) => ({
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
        if (event && event.eventTicketTypes) {
            setEditedEvent(event);
        }
    }, [event]);

    return (
        <>
            <Button variant='primary' size='sm' className='d-flex align-items-center' onClick={handleShow}>Edit
                  <PencilIcon width={15} height={15} className='ms-2' />
                </Button>
            <EventForm
                show={show}
                event={editedEvent}
                handleChange={handleChange}
                handleTicketTypeChange={handleTicketTypeChange}
                handleAddTicketType={handleAddTicketType}
                handleSave={handleSave}
                handleClose={handleClose}
            />
        </>
    );
}