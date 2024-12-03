import { Button } from 'react-bootstrap';

export default function EventForm ({
    show,
    event,
    handleChange,
    handleTicketTypeChange,
    handleAddTicketType,
    handleSave,
    handleClose
}) {

    if (!event) {
        return <div>Loading...</div>
    };

    return (
        <div className={`modal fade ${show ? "show d-block" : ""}`} tabIndex="-1" role="dialog">
            <div className="modal-dialog modal-dialog-centered" role="document" style={{ minWidth: '800px' }}>
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">New Event</h5>
                        <Button className="close ms-auto" variant="danger" onClick={handleClose}><span>&times;</span></Button>
                    </div>
                    <div className="modal-body">
                        {/* Lomakekentät */}
                        <div className="row">
                            <div className="col-md-6">
                                <div className="form-group" >
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
                            </div>
                            <div className="col-md-6">
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
                            </div>
                        </div>

                        <div className="row">
                            <div className="col-md-6">
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
                            </div>
                            <div className="col-md-6">
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
                            </div>
                        </div>

                        <div className="form-group"  style={{ marginBottom: '30px' }}>
                            <label>Event Description</label>
                            <textarea
                                className="form-control"
                                name="eventDescription"
                                value={event.eventDescription}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        {/* Lipputyyppien kentät */}
                        <div className="form-group">
                            <label><strong>Ticket Types</strong></label>
                            {event.eventTicketTypes.map((ticket, index) => (
                                <div key={index}>
                                    <div className="row">
                                        <div className="col-md-4">
                                            <div className="form-group">
                                                <label>Ticket Type Name</label>
                                                <input
                                                    type="text"
                                                    className="form-control"
                                                    name="ticketTypeName"
                                                    value={ticket.ticketTypeName || ticket.ticketType?.name || ''}
                                                    onChange={(e) => handleTicketTypeChange(index, e)}
                                                    required
                                                />
                                            </div>
                                        </div>
                                        <div className="col-md-4">
                                            <div className="form-group">
                                                <label>Ticket Type Price</label>
                                                <input
                                                    type="number"
                                                    className="form-control"
                                                    name="price"
                                                    value={ticket.price}
                                                    onChange={(e) => handleTicketTypeChange(index, e)}
                                                    required
                                                />
                                            </div>
                                        </div>
                                        <div className="col-md-4">
                                            <div className="form-group">
                                                <label>Number of tickets</label>
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
                                    </div>
                                </div>
                            ))}
                            <Button variant="secondary" onClick={handleAddTicketType} style={{ marginTop: '10px' }}>
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
    )
};
