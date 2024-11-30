import { useState } from "react";
import { Button } from 'react-bootstrap';
import api from './api';

export default function AddEvent( {addEvent} ) {
    const [event, setEvent] = useState( {
        eventName: "",
        eventDate: "",
        eventAddress: "",
        eventCity: "",
        eventDescription: "",
        eventTicketTypes: [
            {
                selectedTicketTypeId: "",
                ticketTypeName: "",
                price: 0,
                ticketsInStock: 0,
                tickets: [],
            },
    ],
    });

    const [ticketTypes, setTicketTypes] = useState([]);

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
        updatedTicketTypes[index] = {
            ...updatedTicketTypes[index],
            [name]: value, // Päivitetään vain muuttuva kenttä
        };
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
                { selectedTicketTypeId: "", ticketTypeName: "", price: 0, ticketsInStock: 0, tickets: [] },
            ],
        }));
    };

    const handleSave = async () => {
        try {
            // Jos lipputyyppi on uusi, tallenna se ensin
            const savedTicketTypes = await Promise.all(
                event.eventTicketTypes.map(async (ticket) => {
                    if (!ticket.selectedTicketTypeId) {
                        // Tallenna uusi lipputyyppi
                        const response = await api.post('/tickettypes', { name: ticket.ticketTypeName });
                        return response.data;  // Palautetaan tallennettu lipputyyppi
                    }
                    return { id: ticket.selectedTicketTypeId };  // Jos ID on jo, käytetään sitä
                })
            );
    
            // Muodostetaan tapahtuma, joka viittaa tallennettuihin lipputyyppeihin
            const formattedEvent = {
                ...event,
                eventDate: new Date(event.eventDate).toISOString(),
                eventTicketTypes: event.eventTicketTypes.map((ticket, index) => ({
                    ticketType: savedTicketTypes[index],  // Käytetään tallennettua lipputyyppiä
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
                    selectedTicketTypeId: "",
                    ticketTypeName: "",
                    price: 0,
                    ticketsInStock: 0,
                    tickets: [],
                },
            ],
        });
    };

    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);

    return (
        <>
        <div className="d-grid gap-2 mb-4">
        <Button onClick={handleShow} variant='outline-secondary' size='lg'>Add Event</Button>
        </div>
        <div className={`modal fade ${show ? "show d-block" : ""}`} tabIndex="-1" role="dialog">
            <div className="modal-dialog modal-dialog-centered" role="document">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">New Event</h5>
                        <Button className="close ms-auto" variant="danger" onClick={handleClose}><span>&times;</span></Button>
                    </div>
                    <div className="modal-body">
                        { /* Lomakekentät */}
                        <div className="form-group">
                            <label>Event Name</label>
                            <input 
                                type="text" 
                                className="form-control" 
                                name="eventName" 
                                value={event.eventName} 
                                onChange={handleChange} 
                                required 
                            />
                        </div>
                        <div className="form-group">
                            <label>Event Date & Start time</label>
                            <input 
                                type="datetime-local" 
                                className="form-control" 
                                name="eventDate" 
                                value={event.eventDate} 
                                onChange={handleChange} 
                                required 
                            />
                        </div>
                        <div className="form-group">
                            <label>Event Address</label>
                            <input 
                                type="text" 
                                className="form-control" 
                                name="eventAddress" 
                                value={event.eventAddress} 
                                onChange={handleChange} 
                                required 
                            />
                        </div>
                        <div className="form-group">
                            <label>Event City</label>
                            <input 
                                type="text" 
                                className="form-control" 
                                name="eventCity" 
                                value={event.eventCity} 
                                onChange={handleChange} 
                                required 
                            />
                        </div>
                        <div className="form-group">
                            <label>Event Description</label>
                            <textarea 
                                className="form-control" 
                                name="eventDescription" 
                                value={event.eventDescription} 
                                onChange={handleChange} 
                                required>
                            </textarea>
                        </div>

                        {/* Lipputyyppien kentät */}
                        <div className="form-group">
                        <label>Ticket Types</label>
                                {event.eventTicketTypes.map((ticket, index) => (
                                    <div key={index}>
                                        <div className="form-group">
                                        <label>Ticket Type Name</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            name="ticketTypeName"
                                            value={ticket.ticketTypeName}
                                            onChange={(e) => handleTicketTypeChange(index, e)}
                                            required
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label>Ticket Type Price</label>
                                        <input 
                                            type="number" 
                                            className="form-control" 
                                            name="price" 
                                            value={ticket.price} onChange={(e) => handleTicketTypeChange(index, e)} 
                                            required 
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label>Amount of that type of tickets</label>
                                        <input 
                                            type="number" 
                                            className="form-control" 
                                            name="ticketsInStock" 
                                            value={ticket.ticketsInStock} 
                                            onChange={(e) => handleTicketTypeChange(index, e)} 
                                            required 
                                        />
                                    </div>
                                </div>
                            ))}
                            <Button variant="secondary" onClick={handleAddTicketType}>
                                    + More Ticket Types
                                </Button>
                        </div>
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" onClick={handleClose}>Close</button>
                        <button type="button" className="btn btn-primary" onClick={handleSave}>Save</button>
                    </div>
                </div>
            </div>
        </div>
        
        </>
    );
}