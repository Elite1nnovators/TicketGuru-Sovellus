// ShoppingCart.jsx
import React, { useContext } from 'react';
import { CartContext } from '../context/cart';
import '../css/ShoppingCart.css';  // Import the CSS file

const ShoppingCart = ({ showModal, toggle }) => {
  const { cartItems, addToCart, removeFromCart, clearCart, getCartTotal } = useContext(CartContext);

  if (!showModal) return null;

  return (
    <div className="shopping-cart-overlay">
      <div className="shopping-cart-container">
        <button className="close-button" onClick={toggle}>
          &times;
        </button>
        <h1 className="cart-header">Shopping Cart</h1>
        <div className="cart-items">
          {cartItems.length > 0 ? (
            cartItems.map(item => (
              <div key={`${item.eventId}-${item.ticketTypeId}`} className="cart-item">
                <div className="item-details">
                  <h4 className="item-title">{item.title}</h4>
                  <p className="item-ids">
                    <strong>Event ID:</strong> {item.eventId} | <strong>Ticket Type ID:</strong> {item.ticketTypeId}
                  </p>
                  <p className="item-price">
                    Item price {item.price}€ 
                  </p>
                  <p className="item-quantity">
                    Quantity {item.quantity}
                  </p>
                </div>
                <div className="item-controls">
                  <button className="control-button" onClick={() => addToCart(item)}>
                    +
                  </button>
                  <button className="control-button" onClick={() => removeFromCart(item)}>
                    -
                  </button>
                </div>
              </div>
            ))
          ) : (
            <p className="empty-cart-message">Your cart is empty</p>
          )}
        </div>
        {cartItems.length > 0 && (
          <div className="cart-footer">
            <h2 className="cart-total">Total: {getCartTotal()}€</h2>
            <button className="clear-cart-button" onClick={clearCart}>
              Clear Cart
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default ShoppingCart;
