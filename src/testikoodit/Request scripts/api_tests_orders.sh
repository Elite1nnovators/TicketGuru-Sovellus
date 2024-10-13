#!/bin/bash

BASE_URL="http://localhost:8080"
SUMMARY=() # Array to store the summary of all requests

# Function to perform a GET request for an Order by ID
get_order_by_id() {
    local order_id=$1
    echo "GET Order by ID: $order_id"
    local response=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/orders/$order_id" -H "Accept: application/json")
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    echo "Response Body:"
    echo $body | jq
    echo "------------------------"
    SUMMARY+=("Request: GET Order by ID $order_id returned $http_status")
}

# Function to perform a POST request to add a new Order
add_new_order() {
    echo "POST Add New Order"
    local response=$(curl -s -w "%{http_code}" -X POST "$BASE_URL/orders" \
        -H "Content-Type: application/json" \
        -d '{
            "customerId": 1,
            "salespersonId": 1,
            "orderDetails": [
                {
                    "ticketId": 2,
                    "unitPrice": 25.0,
                    "quantity": 2
                },
                {
                    "ticketId": 1,
                    "unitPrice": 15.0,
                    "quantity": 5
                }
            ]
        }')
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    echo "Response Body:"
    echo $body | jq
    echo "------------------------"
    SUMMARY+=("Request: POST Add New Order returned $http_status")
}

# Function to perform a PUT request to edit an Order
edit_order() {
    local order_id=$1
    echo "PUT Edit Order with ID: $order_id"
    local response=$(curl -s -w "%{http_code}" -X PUT "$BASE_URL/orders/$order_id" \
        -H "Content-Type: application/json" \
        -d '{
            "customerId": 2,
            "salespersonId": 3,
            "orderDate": "2024-10-06T06:06:06.666+00:00",
            "orderDetails": [
                {
                    "ticketId": 2,
                    "quantity": 23,
                    "unitPrice": 25.0
                },
                {
                    "ticketId": 1,
                    "quantity": 51,
                    "unitPrice": 15.0
                }
            ]
        }')
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    echo "Response Body:"
    echo $body | jq
    echo "------------------------"
    SUMMARY+=("Request: PUT Edit Order $order_id returned $http_status")
}

# Function to perform a GET request for all Orders
get_all_orders() {
    echo "GET All Orders"
    local response=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/orders" -H "Accept: application/json")
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    echo "Response Body:"
    echo $body | jq
    echo "------------------------"
    SUMMARY+=("Request: GET All Orders returned $http_status")
}

# Function to perform a DELETE request for an Order by ID
delete_order_by_id() {
    local order_id=$1
    echo "DELETE Order by ID: $order_id"
    local response=$(curl -s -w "%{http_code}" -X DELETE "$BASE_URL/orders/$order_id")
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    echo "Response Body:"
    echo $body | jq
    echo "------------------------"
    SUMMARY+=("Request: DELETE Order by ID $order_id returned $http_status")
}

# Function to print the summary of all requests
print_summary() {
    echo "Summary of Requests:"
    for summary in "${SUMMARY[@]}"; do
        echo "$summary"
    done
}

# Example function calls
get_order_by_id 1
add_new_order
edit_order 2
get_all_orders
delete_order_by_id 1

# Print the summary at the end
print_summary
