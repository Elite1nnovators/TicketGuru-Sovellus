import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Logo from '../assets/Logo.png'; 
import { Button, Form, Alert, Container, Row, Col } from 'react-bootstrap';
import { useAuth } from './AuthContext';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const { setAuth } = useAuth()
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        try {
            const response = await axios.post(
                'http://localhost:8080/api/auth/login',
                { username, password },
                { withCredentials: true } 
            );

            if (response.status === 200) {
                setAuth({ username, password })
                navigate('/home');
            }
        } catch (err) {
            console.log(err)
            setError('Invalid username or password');
        }
    };

    return (
        <div  className="d-flex justify-content-center align-items-center" style={{ height: '100vh'}}>
            <Container>
            <Row className="justify-content-center">
                <Col md={6}>
                    <img src={Logo} className='mb-3 d-block mx-auto p-3 vw-30' />
                    <h3 className="text-center mb-4 fs-2 fw-bold">Login</h3>
                    {error && <Alert variant="danger">{error}</Alert>}
                    <Form onSubmit={handleSubmit} className='d-flex flex-column align-items-center justify-content-center'>
                        <Form.Group className="mb-3" controlId="formUsername">
                            <Form.Label>Username</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Enter username"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                required
                            />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control
                                type="password"
                                placeholder="Password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                            />
                        </Form.Group>
                        <Button  variant="primary" type="submit">
                            Login
                        </Button>
                    </Form>
                </Col>
            </Row>
        </Container>
        </div>
        
    );
};

export default Login;
