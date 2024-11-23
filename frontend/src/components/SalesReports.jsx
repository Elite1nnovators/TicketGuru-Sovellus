import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth } from './AuthContext';

const SalesReports = () => {
  const { auth } = useAuth();  
  const [orders, setOrders] = useState([]);  
  const [events, setEvents] = useState([]);  
  const [orderId, setOrderId] = useState('');  
  const [selectedOrder, setSelectedOrder] = useState(null);  
  const [responseMessage, setResponseMessage] = useState('');  
  const [loading, setLoading] = useState(false);  
  const [showAllOrders, setShowAllOrders] = useState(false);  

  // Haetaan kaikki tilaukset
  const fetchOrders = async () => {
    setLoading(true);
    try {
      const response = await axios.get('http://localhost:8080/orders', {
        headers: {
          Authorization: `Basic ${btoa(auth.username + ':' + auth.password)}`
        }
      });
      setOrders(response.data);
      setResponseMessage('');
    } catch (error) {
      console.error('Error fetching orders:', error);
      setResponseMessage('Failed to load orders.');
    } finally {
      setLoading(false);
    }
  };

  // Haetaan kaikki tapahtumat
  const fetchEvents = async () => {
    try {
      const response = await axios.get('http://localhost:8080/events', {
        headers: {
          Authorization: `Basic ${btoa(auth.username + ':' + auth.password)}`
        }
      });
      setEvents(response.data);
    } catch (error) {
      console.error('Error fetching events:', error);
      setResponseMessage('Failed to load events.');
    }
  };

  // Yhdistetään eventTicketTypeId tapahtumiin
  const getEventName = (eventTicketTypeId) => {
    for (const event of events) {
      const matchingTicketType = event.eventTicketTypes.find(ticketType => ticketType.id === eventTicketTypeId);
      if (matchingTicketType) {
        return event.eventName;
      }
    }
    return 'Unknown Event';
  };

  // Lasketaan kokonaishinta
  const calculateTotalPrice = (orderDetails) => {
    return orderDetails.reduce((total, detail) => {
      return total + detail.unitPrice * detail.quantity;
    }, 0);
  };

  // Haetaan käyttäjän syöttämän OrderId:n mukainen tilaus
  const handleOrderIdSubmit = (e) => {
    e.preventDefault();
    const selected = orders.find(order => order.orderId.toString() === orderId);
    if (selected) {
      setSelectedOrder(selected);
      setResponseMessage('');
    } else {
      setSelectedOrder(null);
      setResponseMessage(`No order found with ID: ${orderId}`);
    }
  };

  // Käynnistetään tilaukset ja tapahtumat komponentin alussa
  useEffect(() => {
    fetchOrders();
    fetchEvents();
  }, []);

  return (
    <div style={{ fontFamily: 'Arial, sans-serif', maxWidth: '1000px', margin: '0 auto' }}>
      <h1 style={{ textAlign: 'left', color: '#333', marginBottom: '20px', marginTop: '20px' }}>Sales Report</h1>

      {loading && <p style={{ textAlign: 'center', color: '#666' }}>Loading data...</p>}

      {responseMessage && <p style={{ textAlign: 'center', color: 'red' }}>{responseMessage}</p>}

      {/* Show All Orders -painike */}
      <button
        onClick={() => setShowAllOrders(!showAllOrders)}
        style={{
          padding: '5px 10px',
          backgroundColor: '#333',
          color: '#fff',
          border: 'none',
          borderRadius: '4px',
          cursor: 'pointer',
        }}
      >
        {showAllOrders ? 'Hide All Orders' : 'Show All Orders'}
      </button>

      {/* Hakukenttä */}
      <form onSubmit={handleOrderIdSubmit} style={{ marginBottom: '20px', marginTop: '20px',textAlign: 'left' }}>
        <label htmlFor="orderId" style={{ marginRight: '10px' }}>Search by Order ID:</label>
        <input
          id="orderId"
          type="text"
          value={orderId}
          onChange={(e) => setOrderId(e.target.value)}
          style={{ padding: '5px', border: '1px solid #ccc', borderRadius: '4px', width: '100px', }}
        />
        <button
          type="submit"
          style={{
            marginLeft: '10px',
            padding: '5px 10px',
            backgroundColor: '#333',
            color: '#fff',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer',
          }}
        >
          Search
        </button>
      </form>

      {/* Yksittäinen tilaus */}
      <div style={{ backgroundColor: '#f8f8f8', padding: '20px', borderRadius: '8px' }}>
        {selectedOrder && (
          <div>
            <p style={{ marginBottom: '10px' }}><strong>Order ID:</strong> {selectedOrder.orderId}</p>
            <p style={{ marginBottom: '10px' }}><strong>Order Date:</strong> {new Date(selectedOrder.orderDate).toLocaleString()}</p>
            <p style={{ marginBottom: '20px' }}><strong>Salesperson:</strong> {`${selectedOrder.salespersonFirstName} ${selectedOrder.salespersonLastName}`}</p>

            <h3 style={{ marginBottom: '10px', color: '#333' }}>Order Details</h3>
            <table
              border="1"
              style={{
                borderCollapse: 'collapse',
                width: '100%',
                maxWidth: '1000px',
                textAlign: 'left',
                marginBottom: '20px',
                backgroundColor: '#fff',
              }}
            >
              <thead>
                <tr>
                  <th style={{ backgroundColor: '#d3d3d3', color: '#000', textAlign: 'left', padding: '10px' }}>Event</th>
                  <th style={{ backgroundColor: '#d3d3d3', color: '#000', textAlign: 'left', padding: '10px' }}>Ticket Type ID</th>
                  <th style={{ backgroundColor: '#d3d3d3', color: '#000', textAlign: 'left', padding: '10px' }}>Quantity</th>
                  <th style={{ backgroundColor: '#d3d3d3', color: '#000', textAlign: 'left', padding: '10px' }}>Unit Price</th>
                  <th style={{ backgroundColor: '#d3d3d3', color: '#000', textAlign: 'left', padding: '10px' }}>Total Price</th>
                </tr>
              </thead>
              <tbody>
                {selectedOrder.orderDetails.map((detail, index) => {
                  const eventName = getEventName(detail.eventTicketTypeId);
                  const totalPrice = detail.unitPrice * detail.quantity;
                  return (
                    <tr key={index}>
                      <td style={{ padding: '8px', lineHeight: '1.6' }}>{eventName}</td>
                      <td style={{ padding: '8px', lineHeight: '1.6' }}>{detail.eventTicketTypeId}</td>
                      <td style={{ padding: '8px', lineHeight: '1.6' }}>{detail.quantity}</td>
                      <td style={{ padding: '8px', lineHeight: '1.6' }}>{detail.unitPrice.toFixed(2)} €</td>
                      <td style={{ padding: '8px', lineHeight: '1.6' }}>{totalPrice.toFixed(2)} €</td>
                    </tr>
                  );
                })}
              </tbody>
            </table>

            <h4 style={{ color: '#333', textAlign: 'right' }}>
              Total Price: {calculateTotalPrice(selectedOrder.orderDetails).toFixed(2)} €
            </h4>
          </div>
        )}
      </div>

      {/* Lista kaikista tilauksista */}
      {showAllOrders && (
        <div style={{ backgroundColor: '#f8f8f8', padding: '20px', borderRadius: '8px' }}>
          <h3>All Orders</h3>
          <table
            border="1"
            style={{
              borderCollapse: 'collapse',
              width: '100%',
              maxWidth: '1000px',
              textAlign: 'left',
              marginBottom: '20px',
              backgroundColor: '#fff',
            }}
          >
            <thead>
              <tr>
                <th style={{ backgroundColor: '#d3d3d3', color: '#000', padding: '10px' }}>Order ID</th>
                <th style={{ backgroundColor: '#d3d3d3', color: '#000', padding: '10px' }}>Order Date</th>
                <th style={{ backgroundColor: '#d3d3d3', color: '#000', padding: '10px' }}>Salesperson</th>
                <th style={{ backgroundColor: '#d3d3d3', color: '#000', padding: '10px' }}>Event</th>
                <th style={{ backgroundColor: '#d3d3d3', color: '#000', padding: '10px' }}>Ticket Type ID</th>
                <th style={{ backgroundColor: '#d3d3d3', color: '#000', padding: '10px' }}>Quantity</th>
                <th style={{ backgroundColor: '#d3d3d3', color: '#000', padding: '10px' }}>Unit Price</th>
                <th style={{ backgroundColor: '#d3d3d3', color: '#000', padding: '10px' }}>Total Price</th>
              </tr>
            </thead>
            <tbody>
              {orders.map((order) =>
                order.orderDetails.map((detail, index) => {
                  const eventName = getEventName(detail.eventTicketTypeId);
                  const totalPrice = detail.unitPrice * detail.quantity;
                  return (
                    <tr key={`${order.orderId}-${index}`}>
                      <td style={{ padding: '8px', lineHeight: '1.6' }}>{order.orderId}</td>
                      <td style={{ padding: '8px', lineHeight: '1.6' }}>{new Date(order.orderDate).toLocaleString()}</td>
                      <td style={{ padding: '8px', lineHeight: '1.6' }}>{`${order.salespersonFirstName} ${order.salespersonLastName}`}</td>
                      <td style={{ padding: '8px', lineHeight: '1.6' }}>{eventName}</td>
                      <td style={{ padding: '8px', lineHeight: '1.6' }}>{detail.eventTicketTypeId}</td>
                      <td style={{ padding: '8px', lineHeight: '1.6' }}>{detail.quantity}</td>
                      <td style={{ padding: '8px', lineHeight: '1.6' }}>{detail.unitPrice.toFixed(2)} €</td>
                      <td style={{ padding: '8px', lineHeight: '1.6' }}>{totalPrice.toFixed(2)} €</td>
                    </tr>
                  );
                })
              )}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default SalesReports;




