import { Button, Form, Modal, Table } from "react-bootstrap";

export default function EventForm({
  event,
  show,
  handleChange,
  handleTicketTypeChange,
  handleAddTicketType,
  handleDeleteTicketType,
  onSave,
  onClose,
}) {
  return (
    <Modal show={show} onHide={onClose}>
      <Modal.Header closeButton>
        <Modal.Title>{event.eventId ? "Edit Event" : "Add Event"}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group>
            <Form.Label>Event Name</Form.Label>
            <Form.Control
              type="text"
              name="eventName"
              value={event.eventName || ""}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group>
            <Form.Label>Event Date</Form.Label>
            <Form.Control
              type="datetime-local"
              name="eventDate"
              value={event.eventDate || ""}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group>
            <Form.Label>Event Address</Form.Label>
            <Form.Control
              type="text"
              name="eventAddress"
              value={event.eventAddress || ""}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group>
            <Form.Label>Event City</Form.Label>
            <Form.Control
              type="text"
              name="eventCity"
              value={event.eventCity || ""}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group>
            <Form.Label>Event Description</Form.Label>
            <Form.Control
              as="textarea"
              name="eventDescription"
              value={event.eventDescription || ""}
              onChange={handleChange}
            />
          </Form.Group>

          <h5>Ticket Types</h5>
          <Table striped bordered>
            <thead>
              <tr>
                <th>Name</th>
                <th>Price</th>
                <th>Stock</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {event.eventTicketTypes.map((ticket, index) => (
                <tr key={index}>
                  <td>
                    <Form.Control
                      type="text"
                      name="ticketTypeName"
                      value={ticket.ticketTypeName || ""}
                      onChange={(e) => handleTicketTypeChange(index, e)}
                    />
                  </td>
                  <td>
                    <Form.Control
                      type="number"
                      name="price"
                      value={ticket.price || ""}
                      onChange={(e) => handleTicketTypeChange(index, e)}
                    />
                  </td>
                  <td>
                    <Form.Control
                      type="number"
                      name="ticketsInStock"
                      value={ticket.ticketsInStock || ""}
                      onChange={(e) => handleTicketTypeChange(index, e)}
                    />
                  </td>
                  <td>
                    <Button
                      variant="danger"
                      onClick={() => handleDeleteTicketType(index)}
                    >
                      Delete
                    </Button>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
          <Button onClick={handleAddTicketType}>Add Ticket Type</Button>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onClose}>
          Close
        </Button>
        <Button variant="primary" onClick={onSave}>
          Save Changes
        </Button>
      </Modal.Footer>
    </Modal>
  );
}
