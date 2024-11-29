import React, { useState, useEffect } from 'react';
import api from './api';

const SalesReports = () => {
  const [orders, setOrders] = useState([]);
  const [events, setEvents] = useState([]);
  const [orderId, setOrderId] = useState('');
  const [selectedOrder, setSelectedOrder] = useState(null);
  const [responseMessage, setResponseMessage] = useState('');
  const [loading, setLoading] = useState(false);
  const [eventSummaries, setEventSummaries] = useState([]);
  const [showEventSummaries, setShowEventSummaries] = useState(false);
  const [detailedEventId, setDetailedEventId] = useState(null);

  // Hae kaikki myynnit
  const fetchOrders = async () => {
    setLoading(true);
    try {
      const response = await api.get('/orders');
      setOrders(response.data);
      setResponseMessage('');
    } catch (error) {
      console.error('Error fetching orders:', error);
      setResponseMessage('Failed to load orders.');
    } finally {
      setLoading(false);
    }
  };

  // Hae kaikki tapahtumat
  const fetchEvents = async () => {
    try {
      const response = await api.get('/events');
      setEvents(response.data);
    } catch (error) {
      console.error('Error fetching events:', error);
      setResponseMessage('Failed to load events.');
    }
  };

  // Tapahtumakohtaiset myynnit
  const calculateEventSummaries = () => {
    const summaries = events.map((event) => {
      const eventOrders = orders.filter((order) =>
        order.orderDetails.some(
          (detail) =>
            getEventDetails(detail.eventTicketTypeId).eventName ===
            event.eventName
        )
      );

      const ticketTypeTotals = {};
      let totalRevenue = 0;
      let totalTicketsSold = 0;

      eventOrders.forEach((order) =>
        order.orderDetails.forEach((detail) => {
          const { ticketTypeName } = getEventDetails(detail.eventTicketTypeId);
          if (!ticketTypeTotals[ticketTypeName]) {
            ticketTypeTotals[ticketTypeName] = { revenue: 0, quantity: 0 };
          }
          ticketTypeTotals[ticketTypeName].revenue +=
            detail.unitPrice * detail.quantity;
          ticketTypeTotals[ticketTypeName].quantity += detail.quantity;

          totalRevenue += detail.unitPrice * detail.quantity;
          totalTicketsSold += detail.quantity;
        })
      );

      return {
        eventId: event.eventId,
        eventName: event.eventName,
        eventOrders,
        ticketTypeTotals,
        totalRevenue,
        totalTicketsSold,
      };
    });

    setEventSummaries(summaries);
  };


  // Haetaan tapahtuma- ja lipputyyppien tiedot
  const getEventDetails = (eventTicketTypeId) => {
    for (const event of events) {
      const matchingTicketType = event.eventTicketTypes.find(
        (ticketType) => ticketType.id === eventTicketTypeId
      );
      if (matchingTicketType) {
        return {
          eventName: event.eventName,
          ticketTypeName: matchingTicketType.ticketTypeName,
        };
      }
    }
    return {
      eventName: 'Unknown Event',
      ticketTypeName: 'Unknown Ticket Type',
    };
  };

  

  // OrderId:llä haku
  const handleOrderIdSubmit = (e) => {
    e.preventDefault();
    const selected = orders.find(
      (order) => order.orderId.toString() === orderId
    );
    if (selected) {
      setSelectedOrder(selected);
      setResponseMessage('');
    } else {
      setSelectedOrder(null);
      setResponseMessage(`No order found with ID: ${orderId}`);
    }
    setShowEventSummaries(false);
  };

  // Myyntikoosteen näyttäminen
  const toggleEventSummaries = () => {
    if (showEventSummaries) {
      setShowEventSummaries(false);
    } else {
      calculateEventSummaries();
      setSelectedOrder(null);
      setResponseMessage('');
      setShowEventSummaries(true);
      setDetailedEventId(null);
    }
  };

  const handleShowEventDetails = (eventId) => {
    setDetailedEventId(eventId === detailedEventId ? null : eventId);
  };

  useEffect(() => {
    fetchOrders();
    fetchEvents();
  }, []);


  // Tyylit
  const styles = {
    container: {
      fontFamily: 'Arial, sans-serif',
      maxWidth: '1000px',
      margin: '0 auto',
    },
    header: {
      textAlign: 'left',
      color: '#333',
      marginBottom: '20px',
      marginTop: '20px',
    },
    button: {
      padding: '5px 10px',
      backgroundColor: '#333',
      color: '#fff',
      border: 'none',
      borderRadius: '4px',
      cursor: 'pointer',
      margin: '5px 0',
    },
    table: {
      borderCollapse: 'collapse',
      width: '100%',
      marginBottom: '20px',
      backgroundColor: '#fff',
    },
    tableHeader: {
      backgroundColor: '#d3d3d3',
      color: '#000',
      textAlign: 'left',
      padding: '10px',
    },
    tableCell: {
      padding: '8px',
      lineHeight: '1.6',
    },
    eventContainer: {
      marginBottom: '20px',
      padding: '10px',
      border: '1px solid #ddd',
      borderRadius: '5px',
      backgroundColor: '#f9f9f9',
    },
    eventHeader: {
      display: 'flex',
      flexDirection: 'column',
      justifyContent: 'flex-start',
      alignItems: 'flex-start',
      paddingBottom: '10px',
    },
    ticketDetails: {
      marginTop: '10px',
    },
    detailsContainer: {
      marginTop: '10px',
    },
    errorMessage: {
      textAlign: 'center',
      color: 'red',
    },
    summaryText: {
      fontSize: '14px',
      color: '#555',
      marginTop: '10px',
    },
    eventSummary: {
      display: 'flex',
      justifyContent: 'space-between',
      marginBottom: '15px',
    },
    eventSalesContainer: {
      marginTop: '10px',
      display: 'flex',
      justifyContent: 'space-between',
      alignItems: 'center',
    },
    ticketSummary: {
      width: '48%',
    },
    
  };

  return (
    <div style={styles.container}>
      <h1 style={styles.header}>Sales Reports</h1>

      {responseMessage && (
        <p style={styles.errorMessage}>{responseMessage}</p>
      )}

      <div>
        <button style={styles.button} onClick={toggleEventSummaries}>
          {showEventSummaries ? 'Hide Sales by Events' : 'Show Sales by Events'}
        </button>
      </div>

      <form onSubmit={handleOrderIdSubmit} style={{ marginTop: '20px', marginBottom: '40px' }}>
        <label htmlFor="orderId" style={{ marginRight: '10px' }}>
          Search by Order ID:
        </label>
        <input
          id="orderId"
          type="text"
          value={orderId}
          onChange={(e) => setOrderId(e.target.value)}
          style={{
            padding: '5px',
            border: '1px solid #ccc',
            borderRadius: '4px',
            marginRight: '10px',
          }}
        />
        <button type="submit" style={styles.button}>
          Search
        </button>
      </form>

      {showEventSummaries &&
        eventSummaries.map((summary) => (
          <div style={styles.eventContainer} key={summary.eventId}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '60px', marginBottom: '10px' }}>
              <h3 style={{ margin: 0 }}>{summary.eventName}</h3>
              <span style={{ fontSize: '18px', color: '#9c27b0', fontWeight: 'bold' }}>
                Total Sales: {summary.totalRevenue.toFixed(2)} €
              </span>
            </div>

            <div style={styles.ticketDetails}>
            
            <table style={{ ...styles.table, width: '50%' }}>
              <thead>
                <tr>
                  <th style={{ ...styles.tableHeader, padding: '5px' }}>Ticket Type</th>
                  <th style={{ ...styles.tableHeader, padding: '5px' }}>Sales (€)</th>
                  <th style={{ ...styles.tableHeader, padding: '5px' }}>Tickets Sold</th>
                </tr>
              </thead>
              <tbody>
                {Object.keys(summary.ticketTypeTotals).map((ticketType) => (
                  <tr key={ticketType}>
                    <td style={{ ...styles.tableCell, padding: '5px' }}>{ticketType}</td>
                    <td style={{ ...styles.tableCell, padding: '5px' }}>
                      {summary.ticketTypeTotals[ticketType].revenue.toFixed(2)} €
                    </td>
                    <td style={{ ...styles.tableCell, padding: '5px' }}>
                      {summary.ticketTypeTotals[ticketType].quantity}
                    </td>
                  </tr>
                ))}
                <tr>
                  <td style={{ ...styles.tableCell, padding: '8px' }}><strong>Total Sales</strong></td>
                  <td style={{ ...styles.tableCell, padding: '8px' }}>
                    <strong>{summary.totalRevenue.toFixed(2)} €</strong>
                  </td>
                  <td style={{ ...styles.tableCell, padding: '8px' }}>
                    <strong>{summary.totalTicketsSold}</strong>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <button
                style={styles.button}
                onClick={() => handleShowEventDetails(summary.eventId)}
              >
                {detailedEventId === summary.eventId
                  ? 'Hide Details'
                  : 'Show Details'}
              </button>

            {detailedEventId === summary.eventId && (
              <div style={styles.detailsContainer}>
                <h4>Detailed Sales</h4>
                <table style={styles.table}>
                  <thead>
                    <tr>
                      <th style={styles.tableHeader}>Order ID</th>
                      <th style={styles.tableHeader}>Order Date</th>
                      <th style={styles.tableHeader}>Salesperson</th>
                      <th style={styles.tableHeader}>Ticket Type</th>
                      <th style={styles.tableHeader}>Quantity</th>
                      <th style={styles.tableHeader}>Unit Price</th>
                      <th style={styles.tableHeader}>Total Price</th>
                    </tr>
                  </thead>
                  <tbody>
                    {summary.eventOrders.map((order) =>
                      order.orderDetails.map((detail, index) => {
                        const { ticketTypeName } = getEventDetails(detail.eventTicketTypeId);
                        return (
                          <tr key={`${order.orderId}-${index}`}>
                            <td style={styles.tableCell}>{order.orderId}</td>
                            <td style={styles.tableCell}>
                              {new Date(order.orderDate).toLocaleDateString()}
                            </td>
                            <td style={styles.tableCell}>
                              {order.salespersonFirstName} {order.salespersonLastName}
                            </td>
                            <td style={styles.tableCell}>{ticketTypeName}</td>
                            <td style={styles.tableCell}>{detail.quantity}</td>
                            <td style={styles.tableCell}>
                              {detail.unitPrice.toFixed(2)} €
                            </td>
                            <td style={styles.tableCell}>
                              {(detail.quantity * detail.unitPrice).toFixed(2)} €
                            </td>
                          </tr>
                        );
                      })
                    )}
                  </tbody>
                </table>
              </div>
            )}
          </div>
        ))}
      
      {selectedOrder && (
        <div style={styles.eventContainer}>
          <h3>Order Details</h3>
          <table style={styles.table}>
            <thead>
              <tr>
                <th style={styles.tableHeader}>Order ID</th>
                <th style={styles.tableHeader}>Order Date</th>
                <th style={styles.tableHeader}>Salesperson</th>
                <th style={styles.tableHeader}>Ticket Type</th>
                <th style={styles.tableHeader}>Quantity</th>
                <th style={styles.tableHeader}>Unit Price</th>
                <th style={styles.tableHeader}>Total Price</th>
              </tr>
            </thead>
            <tbody>
              {selectedOrder.orderDetails.map((detail, index) => {
                const { ticketTypeName, eventName } = getEventDetails(detail.eventTicketTypeId);
                return (
                  <tr key={`${selectedOrder.orderId}-${index}`}>
                    <td style={styles.tableCell}>{selectedOrder.orderId}</td>
                    <td style={styles.tableCell}>
                      {new Date(selectedOrder.orderDate).toLocaleDateString()}
                    </td>
                    <td style={styles.tableCell}>
                      {selectedOrder.salespersonFirstName} {selectedOrder.salespersonLastName}
                    </td>
                    <td style={styles.tableCell}>{ticketTypeName}</td>
                    <td style={styles.tableCell}>{detail.quantity}</td>
                    <td style={styles.tableCell}>
                      {detail.unitPrice.toFixed(2)} €
                    </td>
                    <td style={styles.tableCell}>
                      {(detail.quantity * detail.unitPrice).toFixed(2)} €
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default SalesReports;
























