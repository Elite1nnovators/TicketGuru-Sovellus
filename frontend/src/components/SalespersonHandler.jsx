import { useEffect, useState } from "react";
import api from "./api"; // Import the custom API instance
import { Container, Card, Button, Form, Alert } from "react-bootstrap";

const SalespersonHandler = () => {
  const [salespersonList, setSalespersonList] = useState([]);
  const [selectedSalesperson, setSelectedSalesperson] = useState(null);
  const [editedSalesperson, setEditedSalesperson] = useState({
    firstName: "",
    lastName: "",
    phone: "",
  });
  const [error, setError] = useState("");

  const fetchSalespersons = async () => {
    try {
      const response = await api.get("/api/salespersons");
      setSalespersonList(response.data);
    } catch (error) {
      console.error("Error fetching salespersons:", error);
    }
  };

  const handleSalespersonSelect = (salespersonId) => {
    console.log("Selected Salesperson ID:", salespersonId);
    const salesperson = salespersonList.find((salesperson) => salesperson.salespersonId === salespersonId);
    setSelectedSalesperson(salesperson || null);
    console.log("Selected Salesperson:", salesperson);
    setEditedSalesperson({
      firstName: salesperson?.firstName || "",
      lastName: salesperson?.lastName || "",
      phone: salesperson?.phone || "",
    });
    setError(""); 
  };

  

  const handleSalespersonEdit = async (e) => {
    e.preventDefault();
    if (!selectedSalesperson) {
      setError("Please select a Salesperson first.");
      return;
    }

    
    try {
      const updatedSalesperson = {
        firstName: editedSalesperson?.firstName || selectedSalesperson.firstName,
        lastName: editedSalesperson?.lastName || selectedSalesperson.lastName,
        phone: editedSalesperson?.phone || selectedSalesperson.phone,
      };
      console.log("Updated Salesperson: ", updatedSalesperson);
      await api.patch(`/api/salespersons/edit/${selectedSalesperson.salespersonId}`, updatedSalesperson);
      alert("Salesperson updated successfully!");
      fetchSalespersons();
      setError("");
    } catch (error) {
      console.error("Error editing Salesperson:", error);
    }
  };

  const handleSalespersonDelete = async () => {
    if (!selectedSalesperson) {
      alert("Please select a Salesperson first.");
      return;
    }

    const confirmDelete = window.confirm(
      `Are you sure you want to delete ${selectedSalesperson.firstName} ${selectedSalesperson.lastName}?`
    );
    if (!confirmDelete) return;

    try {
      await api.delete(`/api/salespersons/delete/${selectedSalesperson.salespersonId}`);
      alert("User deleted successfully!");
      setSelectedSalesperson(null);
      fetchSalespersons();
    } catch (error) {
      console.error("Error deleting user:", error);
    }
  };

  useEffect(() => {
    fetchSalespersons();
  }, []);

  return (
    <Container>
      <Card>
        <Card.Header>
          <h3>User Handler</h3>
        </Card.Header>
        <Card.Body>
          {error && <Alert variant="danger">{error}</Alert>}
          <Form.Group>
            <Form.Label >Select User</Form.Label>
            <Form.Select
               value={selectedSalesperson?.salespersonId || ""}
               onChange={(e) => {
                 const selectedId = parseInt(e.target.value, 10);
                 handleSalespersonSelect(selectedId); 
               }}
            >
              <option value="" className="text-secondary" disabled>Select Salesperson</option>
              {salespersonList.map((salesperson) => (
                <option
                  key={
                    salesperson.salespersonId ||
                    salesperson.firstName || 
                    salesperson.lastName 
                  }
                  value={salesperson.salespersonId}
                >
                  {salesperson.firstName + " " + salesperson.lastName}
                </option>
              ))}
            </Form.Select>
          </Form.Group>

          {selectedSalesperson && (
            <Form onSubmit={handleSalespersonEdit} className="mt-4">
              <Form.Group>
                <Form.Label>First Name</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter First Name"
                  value={editedSalesperson.firstName}
                  onChange={(e) =>
                    setEditedSalesperson({ ...editedSalesperson, firstName: e.target.value })
                  }
                />
              </Form.Group>
              <Form.Group>
                <Form.Label>Last Name</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter Last Name"
                  value={editedSalesperson.lastName}
                  onChange={(e) =>
                    setEditedSalesperson({ ...editedSalesperson, lastName: e.target.value })
                  }
                />
              </Form.Group>
              <Form.Group>
                <Form.Label>Phone</Form.Label>
                <Form.Control
                  type="number"
                  placeholder="Enter Phone Number"
                  value={editedSalesperson.phone}
                  onChange={(e) =>
                    setEditedSalesperson({ ...editedSalesperson, phone: e.target.value })
                  }
                />
              </Form.Group>
              <Button variant="primary" type="submit" className="mt-3">
                Update Salesperson
              </Button>
              <Button
                variant="danger"
                className="mt-3 ms-3"
                onClick={handleSalespersonDelete}
              >
                Delete Salesperson
              </Button>
            </Form>
          )}
        </Card.Body>
      </Card>
    </Container>
  );
};

export default SalespersonHandler;
