
import { createContext, useState, useEffect } from 'react';

export const CartContext = createContext();

export const CartProvider = ({ children }) => {
  const [cartItems, setCartItems] = useState(
    localStorage.getItem('cartItems') ? JSON.parse(localStorage.getItem('cartItems')) : []
  );

  const addToCart = (item) => {
    const isItemInCart = cartItems.find(
      (cartItem) => cartItem.eventId === item.eventId && cartItem.ticketTypeId === item.ticketTypeId
    );

    if (isItemInCart) {
      setCartItems(
        cartItems.map((cartItem) =>
          cartItem.eventId === item.eventId && cartItem.ticketTypeId === item.ticketTypeId
            ? { ...cartItem, quantity: cartItem.quantity + 1 }
            : cartItem
        )
      );
    } else {
      setCartItems([...cartItems, { ...item, quantity: 1 }]);
    }
  };

  const removeFromCart = (item) => {
    const isItemInCart = cartItems.find(
      (cartItem) => cartItem.eventId === item.eventId && cartItem.ticketTypeId === item.ticketTypeId
    );

    if (isItemInCart.quantity === 1) {
      setCartItems(cartItems.filter(
        (cartItem) => !(cartItem.eventId === item.eventId && cartItem.ticketTypeId === item.ticketTypeId)
      ));
    } else {
      setCartItems(
        cartItems.map((cartItem) =>
          cartItem.eventId === item.eventId && cartItem.ticketTypeId === item.ticketTypeId
            ? { ...cartItem, quantity: cartItem.quantity - 1 }
            : cartItem
        )
      );
    }
  };

  const clearCart = () => setCartItems([]);

  const getCartTotal = () => cartItems.reduce((total, item) => total + item.price * item.quantity, 0);

  useEffect(() => {
    localStorage.setItem('cartItems', JSON.stringify(cartItems));
  }, [cartItems]);

  return (
    <CartContext.Provider
      value={{
        cartItems,
        addToCart,
        removeFromCart,
        clearCart,
        getCartTotal,
      }}
    >
      {children}
    </CartContext.Provider>
  );
};
    