import { useEffect, useState } from "react";
import api from "./api"; // Import the custom API instance
import { Container, Card, Button, Form, Alert } from "react-bootstrap";

const UserHandler = () => {
  const [userList, setUserList] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [editedUser, setEditedUser] = useState({
    username: "",
    password: "",
    confirmPassword: "",
    role: "USER",
  });
  const [error, setError] = useState("");

  const fetchUsers = async () => {
    try {
      const response = await api.get("/api/users");
      const newResponse = response.data.filter(user => user.userId !== 1 && user.userId !== 2);
      setUserList(newResponse);
      console.log("Fetched Users:", newResponse);
      
    } catch (error) {
      console.error("Error fetching users:", error);
    }
  };

  const handleUserSelect = (userId) => {
    console.log("Selected User ID:", userId);
    const user = userList.find((user) => user.userId === userId);
    setSelectedUser(user || null);
    console.log("Selected User:", user);
    setEditedUser({
      username: user?.username || "",
      password: "",
      confirmPassword: "",
      role: user?.role || "USER",
    });
    setError(""); 
  };

  const isValidPassword = (password) =>
    /^(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{5,30}$/.test(password);

  const handleUserEdit = async (e) => {
    e.preventDefault();
    if (!selectedUser) {
      setError("Please select a user first.");
      return;
    }

    if (editedUser.password && !isValidPassword(editedUser.password)) {
      setError(
        "Password must be 5-30 characters, include one number, and one uppercase letter."
      );
      return;
    }

    if (editedUser.password !== editedUser.confirmPassword) {
      setError("Passwords do not match.");
      return;
    }

    try {
      const updatedUser = {
        username: editedUser?.username || selectedUser.username,
        password: editedUser?.password || selectedUser.password, 
        role: editedUser?.role || selectedUser.role,
      };
      console.log("Updated User: ", updatedUser);
      await api.patch(`/api/users/edit/${selectedUser.userId}`, updatedUser);
      alert("User updated successfully!");
      fetchUsers();
      setError("");
    } catch (error) {
      console.error("Error editing user:", error);
    }
  };

  const handleUserDelete = async () => {
    if (!selectedUser) {
      alert("Please select a user first.");
      return;
    }

    const confirmDelete = window.confirm(
      `Are you sure you want to delete ${selectedUser.username}?`
    );
    if (!confirmDelete) return;

    try {
      await api.delete(`/api/users/delete/${selectedUser.userId}`);
      alert("User deleted successfully!");
      setSelectedUser(null);
      fetchUsers();
    } catch (error) {
      console.error("Error deleting user:", error);
    }
  };

  useEffect(() => {
    fetchUsers();
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
               value={selectedUser?.id || ""}
               onChange={(e) => {
                 const selectedId = parseInt(e.target.value, 10);
                 handleUserSelect(selectedId); 
               }}
            >
              <option value="" className="text-secondary" disabled>Select User</option>
              {userList.map((user) => (
                <option
                  key={
                    user.userId ||
                    user.username ||
                    user.salesperson?.salespersonId
                  }
                  value={user.userId}
                >
                  {user.username}
                </option>
              ))}
            </Form.Select>
          </Form.Group>

          {selectedUser && (
            <Form onSubmit={handleUserEdit} className="mt-4">
              <Form.Group>
                <Form.Label>Username</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter username"
                  value={editedUser.username}
                  onChange={(e) =>
                    setEditedUser({ ...editedUser, username: e.target.value })
                  }
                />
              </Form.Group>
              <Form.Group>
                <Form.Label>Password</Form.Label>
                <Form.Control
                  type="password"
                  placeholder="Enter new password"
                  value={editedUser.password}
                  onChange={(e) =>
                    setEditedUser({ ...editedUser, password: e.target.value })
                  }
                />
              </Form.Group>
              <Form.Group>
                <Form.Label>Confirm Password</Form.Label>
                <Form.Control
                  type="password"
                  placeholder="Confirm new password"
                  value={editedUser.confirmPassword}
                  onChange={(e) =>
                    setEditedUser({
                      ...editedUser,
                      confirmPassword: e.target.value,
                    })
                  }
                />
              </Form.Group>
              <Form.Group>
                <Form.Label>Role</Form.Label>
                <Form.Select
                  value={editedUser.role}
                  onChange={(e) =>
                    setEditedUser({ ...editedUser, role: e.target.value })
                  }
                >
                  <option value="USER">User</option>
                  <option value="ADMIN">Admin</option>
                </Form.Select>
              </Form.Group>
              <Button variant="primary" type="submit" className="mt-3">
                Update User
              </Button>
              <Button
                variant="danger"
                className="mt-3 ms-3"
                onClick={handleUserDelete}
              >
                Delete User
              </Button>
            </Form>
          )}
        </Card.Body>
      </Card>
    </Container>
  );
};

export default UserHandler;
