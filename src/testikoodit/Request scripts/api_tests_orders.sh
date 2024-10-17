#!/bin/bash

BASE_URL="http://localhost:8080"
SUMMARY=() # Array to store the summary of all requests

# Function to extract error messages from JSON response
extract_errors() {
    local body="$1"
    echo "$body" | jq -r '.error // empty'
}

# Function to perform a GET request for an Order by ID
get_order_by_id() {
    local order_id=$1
    echo "GET Order by ID: $order_id"
    local response=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/orders/$order_id" -H "Accept: application/json")
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    local errors=$(extract_errors "$body")
    echo "Response Body:"
    echo "$body" | jq
    echo "------------------------"
    SUMMARY+=("Request: GET Order by ID $order_id returned $http_status. ERRORS: ${errors:-None}")
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
    local errors=$(extract_errors "$body")
    echo "Response Body:"
    echo "$body" | jq
    echo "------------------------"
    SUMMARY+=("Request: POST Add New Order returned $http_status. ERRORS: ${errors:-None}")
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
    local errors=$(extract_errors "$body")
    echo "Response Body:"
    echo "$body" | jq
    echo "------------------------"
    SUMMARY+=("Request: PUT Edit Order $order_id returned $http_status. ERRORS: ${errors:-None}")
}

# Function to perform a PATCH request to update an Order partially
patch_order() {
    local order_id=$1
    echo "PATCH Update Order with ID: $order_id"
    local response=$(curl -s -w "%{http_code}" -X PATCH "$BASE_URL/orders/$order_id" \
        -H "Content-Type: application/json" \
        -d '{
            "customerId": 2
        }')
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    local errors=$(extract_errors "$body")
    echo "Response Body:"
    echo "$body" | jq
    echo "------------------------"
    SUMMARY+=("Request: PATCH Update Order $order_id returned $http_status. ERRORS: ${errors:-None}")
}

# Function to perform a GET request for all Orders
get_all_orders() {
    echo "GET All Orders"
    local response=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/orders" -H "Accept: application/json")
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    echo "Response Body:"
    echo "$body" | jq
    echo "------------------------"
    SUMMARY+=("Request: GET All Orders returned $http_status. ERRORS: None")
}

# Function to perform a DELETE request for an Order by ID
delete_order_by_id() {
    local order_id=$1
    echo "DELETE Order by ID: $order_id"
    local response=$(curl -s -w "%{http_code}" -X DELETE "$BASE_URL/orders/$order_id")
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    local errors=$(extract_errors "$body")
    echo "Response Body:"
    echo "$body" | jq
    echo "------------------------"
    SUMMARY+=("Request: DELETE Order by ID $order_id returned $http_status. ERRORS: ${errors:-None}")
}

# Function to perform a GET request for all OrderDetails
get_all_orderdetails() {
    echo "GET All OrderDetails"
    local response=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/orderdetails" -H "Accept: application/json")
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    echo "Response Body:"
    echo "$body" | jq
    echo "------------------------"
    SUMMARY+=("Request: GET All OrderDetails returned $http_status. ERRORS: None")
}

# Function to perform a GET request for an OrderDetails by ID
get_orderdetails_by_id() {
    local orderdetails_id=$1
    echo "GET OrderDetails by ID: $orderdetails_id"
    local response=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/orderdetails/$orderdetails_id" -H "Accept: application/json")
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    local errors=$(extract_errors "$body")
    echo "Response Body:"
    echo "$body" | jq
    echo "------------------------"
    SUMMARY+=("Request: GET OrderDetails by ID $orderdetails_id returned $http_status. ERRORS: ${errors:-None}")
}

# Function to perform a PUT request to edit an OrderDetails
edit_orderdetails() {
    local orderdetails_id=$1
    echo "PUT Edit OrderDetails with ID: $orderdetails_id"
    local response=$(curl -s -w "%{http_code}" -X PUT "$BASE_URL/orderdetails/$orderdetails_id" \
        -H "Content-Type: application/json" \
        -d '{
            "orderDetailId": '"$orderdetails_id"',
            "order": {
                "orderId": 1
            },
            "ticket": {
                "id": 1
            },
            "quantity": 10,
            "unitPrice": 20.0
        }')
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    local errors=$(extract_errors "$body")
    echo "Response Body:"
    echo "$body" | jq
    echo "------------------------"
    SUMMARY+=("Request: PUT Edit OrderDetails $orderdetails_id returned $http_status. ERRORS: ${errors:-None}")
}

# Function to perform a DELETE request for an OrderDetails by ID
delete_orderdetails_by_id() {
    local orderdetails_id=$1
    echo "DELETE OrderDetails by ID: $orderdetails_id"
    local response=$(curl -s -w "%{http_code}" -X DELETE "$BASE_URL/orderdetails/$orderdetails_id")
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    local errors=$(extract_errors "$body")
    echo "Response Body:"
    echo "$body" | jq
    echo "------------------------"
    SUMMARY+=("Request: DELETE OrderDetails by ID $orderdetails_id returned $http_status. ERRORS: ${errors:-None}")
}

# Function to perform a GET request for a non-existent Order
get_order_non_existent() {
    local order_id=99999
    echo "GET Non-existent Order by ID: $order_id"
    local response=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/orders/$order_id" -H "Accept: application/json")
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    local errors=$(extract_errors "$body")
    echo "Response Body:"
    echo "$body" | jq
    echo "------------------------"
    SUMMARY+=("Request: GET Non-existent Order by ID $order_id returned $http_status. ERRORS: ${errors:-None}")
}

# Function to perform a POST request with missing customerId
add_new_order_missing_customer() {
    echo "POST Add New Order Missing customerId"
    local response=$(curl -s -w "%{http_code}" -X POST "$BASE_URL/orders" \
        -H "Content-Type: application/json" \
        -d '{
            "salespersonId": 1,
            "orderDetails": [
                {
                    "ticketId": 2,
                    "unitPrice": 25.0,
                    "quantity": 2
                }
            ]
        }')
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    local errors=$(extract_errors "$body")
    echo "Response Body:"
    echo "$body" | jq
    echo "------------------------"
    SUMMARY+=("Request: POST Add New Order Missing customerId returned $http_status. ERRORS: ${errors:-None}")
}

# Function to perform a POST request with invalid quantity
add_new_order_invalid_quantity() {
    echo "POST Add New Order with Invalid Quantity"
    local response=$(curl -s -w "%{http_code}" -X POST "$BASE_URL/orders" \
        -H "Content-Type: application/json" \
        -d '{
            "customerId": 1,
            "salespersonId": 1,
            "orderDetails": [
                {
                    "ticketId": 2,
                    "unitPrice": 25.0,
                    "quantity": -5
                }
            ]
        }')
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    local errors=$(extract_errors "$body")
    echo "Response Body:"
    echo "$body" | jq
    echo "------------------------"
    SUMMARY+=("Request: POST Add New Order with Invalid Quantity returned $http_status. ERRORS: ${errors:-None}")
}

# Function to perform a POST request with zero quantity
add_new_order_zero_quantity() {
    echo "POST Add New Order with Zero Quantity"
    local response=$(curl -s -w "%{http_code}" -X POST "$BASE_URL/orders" \
        -H "Content-Type: application/json" \
        -d '{
            "customerId": 1,
            "salespersonId": 1,
            "orderDetails": [
                {
                    "ticketId": 2,
                    "unitPrice": 25.0,
                    "quantity": 0
                }
            ]
        }')
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    local errors=$(extract_errors "$body")
    echo "Response Body:"
    echo "$body" | jq
    echo "------------------------"
    SUMMARY+=("Request: POST Add New Order with Zero Quantity returned $http_status. ERRORS: ${errors:-None}")
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
patch_order 2
get_all_orders
delete_order_by_id 1
get_order_non_existent
add_new_order_missing_customer
add_new_order_invalid_quantity
add_new_order_zero_quantity
get_all_orderdetails
get_orderdetails_by_id 1
edit_orderdetails 1
delete_orderdetails_by_id 1

# Print the summary at the end
print_summary