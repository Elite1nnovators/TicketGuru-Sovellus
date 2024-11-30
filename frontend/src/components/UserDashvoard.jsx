import { useEffect, useState } from "react";
import api from "./api"; // Import the custom API instance


import {
  Container,
  Card,
  Button,
  Form,
  Row,
  Col,
  Table,
} from "react-bootstrap";

const UserDashboard = () => {
  const [userDetails, setUserDetails] = useState({});

  const [salespersonList, setSalespersonList] = useState([]);
  const [newSalesperson, setNewSalesperson] = useState({
    firstName: "",
    lastName: "",
    phone: "",
  });
  const [newUser, setNewUser] = useState({
    username: "",
    password: "",
    role: "USER",
    salesperson: "",
  });

  const fetchUserDetails = async () => {
    
    
    try {
      const response = await api.get("/api/users/me"); // Use api instead of axios
      setUserDetails(response.data);
      
    } catch (error) {
      console.error("Error fetching user details:", error);
    }
  };

  const fetchSalespersons = async () => {
    try {
      const response = await api.get("/api/salespersons"); // Use api instead of axios
      setSalespersonList(response.data);
    } catch (error) {
      console.error("Error fetching salespersons:", error);
    }
  };

  const handleSalespersonCreate = async (e) => {
    e.preventDefault();
    try {
      await api.post("/api/add/salesperson", newSalesperson); // Use api instead of axios
      alert("Salesperson added successfully!");
      setNewSalesperson({ firstName: "", lastName: "", phone: "" });
      fetchSalespersons();
    } catch (error) {
      console.error("Error creating salesperson:", error);
    }
  };

  const handleUserCreate = async (e) => {
    e.preventDefault();
    try {
      const selectedSalesperson = salespersonList.find(
        (sp) => sp.salespersonId === Number(newUser.salesperson.salespersonId)
      );
      const username =
        selectedSalesperson.firstName.slice(0, 3) +
        selectedSalesperson.lastName.slice(0, 3);
      const requestData = { ...newUser, username };
      await api.post("/api/add/user", requestData); // Use api instead of axios
      console.log(requestData);
      alert("User created successfully!");
      setNewUser({
        username: "",
        password: "",
        role: "USER",
        salesperson: "",
      });
    } catch (error) {
      console.error("Error creating user:", error);
    }
  };

  useEffect(() => {
    fetchUserDetails();
    fetchSalespersons();

  }, []);

  return (
    <Container className="mt-5">
      <h1 className="text-center mb-4">User Dashboard</h1>

      {/* User Details */}
      <Card className="mb-4">
        <Card.Header className="bg-primary text-white">
          Your Details
        </Card.Header>
        <Card.Body>
          {userDetails ? (
            <Table bordered>
              <tbody>
                <tr>
                  <td>
                    <strong>Username:</strong>
                  </td>
                  <td>{userDetails.username || "N/A"}</td>
                </tr>
                <tr>
                  <td>
                    <strong>Role:</strong>
                  </td>
                  <td>{userDetails.role || "N/A"}</td>
                </tr>
                <tr>
                  <td>
                    <strong>Assigned Salesperson:</strong>
                  </td>
                  <td>
                    {userDetails.firstName || "N/A"}{" "}
                    {userDetails.lastName || ""}
                  </td>
                </tr>
              </tbody>
            </Table>
          ) : (
            <p>Loading user details...</p>
          )}
        </Card.Body>
      </Card>

      {/* Add Salesperson */}
      <Card className="mb-4">
        <Card.Header className="bg-success text-white">
          Create a New Salesperson
        </Card.Header>
        <Card.Body>
          <Form onSubmit={handleSalespersonCreate}>
            <Form.Group className="mb-3" controlId="firstName">
              <Form.Label>First Name</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter first name"
                value={newSalesperson.firstName}
                onChange={(e) =>
                  setNewSalesperson({
                    ...newSalesperson,
                    firstName: e.target.value,
                  })
                }
              />
            </Form.Group>

            <Form.Group className="mb-3" controlId="lastName">
              <Form.Label>Last Name</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter last name"
                value={newSalesperson.lastName}
                onChange={(e) =>
                  setNewSalesperson({
                    ...newSalesperson,
                    lastName: e.target.value,
                  })
                }
              />
            </Form.Group>

            <Form.Group className="mb-3" controlId="phone">
              <Form.Label>Phone</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter phone number"
                value={newSalesperson.phone}
                onChange={(e) =>
                  setNewSalesperson({
                    ...newSalesperson,
                    phone: e.target.value,
                  })
                }
              />
            </Form.Group>

            <Button variant="success" type="submit">
              Add Salesperson
            </Button>
          </Form>
        </Card.Body>
      </Card>

      {/* Create User */}
      <Card className="mb-4">
        <Card.Header className="bg-info text-white">
          Create a New User
        </Card.Header>
        <Card.Body>
          <Form onSubmit={handleUserCreate}>
            <Row className="mb-3">
              <Col md={6}>
                <Form.Group controlId="username">
                  <Form.Label>Username</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Auto-generated from salesperson"
                    value={newUser.username}
                    readOnly
                  />
                </Form.Group>
              </Col>
              <Col md={6}>
                <Form.Group controlId="password">
                  <Form.Label>Password</Form.Label>
                  <Form.Control
                    type="password"
                    placeholder="Enter password"
                    value={newUser.password}
                    onChange={(e) =>
                      setNewUser({ ...newUser, password: e.target.value })
                    }
                  />
                </Form.Group>
              </Col>
            </Row>

            <Row className="mb-3">
              <Col md={6}>
                <Form.Group controlId="role">
                  <Form.Label>Role</Form.Label>
                  <Form.Select
                    value={newUser.role}
                    onChange={(e) =>
                      setNewUser({ ...newUser, role: e.target.value })
                    }
                  >
                    <option value="">Select Role</option>
                    <option value="ADMIN">Admin</option>
                    <option value="USER">User</option>
                  </Form.Select>
                </Form.Group>
              </Col>
              <Col md={6}>
                <Form.Group controlId="salesperson">
                  <Form.Label>Salesperson</Form.Label>
                  <Form.Select
                    value={newUser.salesperson.salespersonId}
                    onChange={(e) =>
                      setNewUser({ ...newUser, salesperson: {salespersonId: e.target.value }  })
                    }
                  >
                    <option value="">Select Salesperson</option>
                    {salespersonList.map((sp) => (
                      <option key={sp.salespersonId} value={sp.salespersonId}>
                        {sp.firstName} {sp.lastName}
                      </option>
                    ))}
                  </Form.Select>
                </Form.Group>
              </Col>
            </Row>

            <Button variant="info" type="submit">
              Create User
            </Button>
          </Form>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default UserDashboard;
