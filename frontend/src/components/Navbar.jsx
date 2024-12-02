import { Button, Container, Nav, Navbar } from "react-bootstrap";
import Logo from '../assets/Logo.png'; 
import { Link, useNavigate } from "react-router-dom";
import { ArrowLeftStartOnRectangleIcon } from '@heroicons/react/24/outline';
import { ShoppingCartIcon } from '@heroicons/react/24/outline';
import { useState, useContext } from 'react'; 
import Cart from './ShoppingCart';
import { CartContext } from '../context/cart.jsx';

function TicketGuruNavbar() {
    const navigate = useNavigate();
    const [showModal, setShowModal] = useState(false);

    const { cartItems } = useContext(CartContext); // Hakee carItemsit CartContextista 

    const handleLogout = () => {
        navigate('/');
    };

    const toggle = () => {
        setShowModal(!showModal);
    };

    return (
        <>
            <Navbar className="bg-body-tertiary ms-0" data-bs-theme="dark">
                <Container fluid>
                    <Navbar.Brand href="#home">
                        <img
                            alt=""
                            src={Logo}
                            width="30"
                            height="30"
                            className="d-inline-block align-top"
                        />{' '}
                        TicketGuru
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="me-auto">
                            <Nav.Link as={Link} to="/home">Home</Nav.Link>
                            <Nav.Link as={Link} to="/events">Events and Sales</Nav.Link>
                            <Nav.Link as={Link} to="/sales-reports">Reports</Nav.Link>
                            
   <Nav.Link as={Link} to="/user-dashboard">UserDasboard</Nav.Link>

                        </Nav>
                        {!showModal && (
                            <button
                                className="px-4 py-2 bg-gray-800 text-white text-xs font-bold uppercase rounded hover:bg-gray-700 focus:outline-none focus:bg-gray-700 me-3"
                                onClick={toggle}
                            >
                                Cart ({cartItems.length})
                            </button>
                        )}
                        <Button onClick={handleLogout} variant="outline-danger" className="d-flex align-items-center">
                            Logout
                            <ArrowLeftStartOnRectangleIcon width={20} height={20} className="ms-2" />
                        </Button>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
            {/* Render Cart modal */}
            {showModal && <Cart showModal={showModal} toggle={toggle} />}
        </>
    );
}

export default TicketGuruNavbar;