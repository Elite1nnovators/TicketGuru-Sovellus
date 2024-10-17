#!/bin/bash

BASE_URL="http://localhost:8080"
SUMMARY=() # Array to store the summary of all requests

# Function to perform a GET request for an Event by ID
get_event_by_id() {
    local event_id=$1
    echo "GET Event by ID: $event_id"
    local response=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/events/$event_id" -H "Accept: application/json")
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    echo "Response Body:"
    echo $body | jq
    echo "------------------------"
    SUMMARY+=("Request: GET Event by ID $event_id returned $http_status")
}

# Function to perform a PUT request to edit an Event
edit_event() {
    local event_id=$1
    echo "PUT Edit Event with ID: $event_id"
    local response=$(curl -s -w "%{http_code}" -X PUT "$BASE_URL/events/$event_id" \
        -H "Content-Type: application/json" \
        -d '{
            "eventName": "Update Concert name",
            "eventDate": "2024-10-01T09:43:35.689+00:00",
            "eventAddress": "Update Event Address 1",
            "eventCity": "Testi update City",
            "eventDescription": "Update description",
            "eventTicketTypes": [
                {
                    "id": 1,
                    "ticketType": {
                        "id": 1,
                        "name": "Aikuinen"
                    },
                    "price": 20.0,
                    "ticketsInStock": 50
                },
                {
                    "id": 2,
                    "ticketType": {
                        "id": 2,
                        "name": "Lapsi"
                    },
                    "price": 10.0,
                    "ticketsInStock": 60
                },
                {
                    "id": 3,
                    "ticketType": {
                        "id": 3,
                        "name": "VIP"
                    },
                    "price": 100.0,
                    "ticketsInStock": 15
                }
            ]
        }')
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    echo "Response Body:"
    echo $body | jq
    echo "------------------------"
    SUMMARY+=("Request: PUT Edit Event $event_id returned $http_status")
}

# Function to perform a POST request to add a new Event
add_new_event() {
    echo "POST Add New Event"
    local response=$(curl -s -w "%{http_code}" -X POST "$BASE_URL/events" \
        -H "Content-Type: application/json" \
        -d '{
            "eventName": "Concert 1",
            "eventDate": "2024-10-01T05:08:30.651+00:00",
            "eventAddress": "Event Address 1",
            "eventCity": "Helsinki",
            "eventDescription": "A great concert event",
            "eventTicketTypes": [
                {
                    "ticketType": {
                        "id": 1,
                        "name": "Aikuinen"
                    },
                    "price": 10,
                    "ticketsInStock": 40
                }
            ]
        }')
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    echo "Response Body:"
    echo $body | jq
    echo "------------------------"
    SUMMARY+=("Request: POST Add New Event returned $http_status")
}

# Function to perform a GET request for all Events
get_all_events() {
    echo "GET All Events"
    local response=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/events" -H "Accept: application/json")
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    echo "Response Body:"
    echo $body | jq
    echo "------------------------"
    SUMMARY+=("Request: GET All Events returned $http_status")
}

# Function to perform a DELETE request for an Event by ID
delete_event_by_id() {
    local event_id=$1
    echo "DELETE Event by ID: $event_id"
    local response=$(curl -s -w "%{http_code}" -X DELETE "$BASE_URL/events/$event_id")
    local http_status="${response: -3}"
    local body="${response:0:${#response}-3}"
    echo "Response Body:"
    echo $body | jq
    echo "------------------------"
    SUMMARY+=("Request: DELETE Event by ID $event_id returned $http_status")
}

# Function to print the summary of all requests
print_summary() {
    echo "Summary of Requests:"
    for summary in "${SUMMARY[@]}"; do
        echo "$summary"
    done
}

# Example function calls
get_event_by_id 2
edit_event 1
add_new_event
get_all_events
delete_event_by_id 2

# Print the summary at the end
print_summary
