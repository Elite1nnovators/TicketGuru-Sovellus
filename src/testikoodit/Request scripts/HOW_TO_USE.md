## HOW_TO_RUN.md

### Prerequisites

1. **Bash Shell**: Make sure you have a Bash shell available on your system. Bash is typically available by default on most Unix-based systems (Linux, macOS). For Windows, you can use Git Bash, WSL (Windows Subsystem for Linux), or Cygwin.

2. **`curl`**: The script uses `curl` for making HTTP requests. Make sure `curl` is installed on your system.
   - **To check if `curl` is installed**, run:
     ```bash
     curl --version
     ```
   - If not installed, you can install it:
     - On **Ubuntu/Debian**: `sudo apt-get install curl`
     - On **macOS**: `brew install curl`
     - On **Windows**: Install Git Bash, which comes with `curl`.

3. **`jq`**: This tool is used for pretty-printing JSON output.
   - **To check if `jq` is installed**, run:
     ```bash
     jq --version
     ```
   - If not installed, you can install it:
     - On **Ubuntu/Debian**: `sudo apt-get install jq`
     - On **macOS**: `brew install jq`
     - On **Windows**: Download from [jq official website](https://stedolan.github.io/jq/).

### Step 1: Download the Script

1. Save the provided script as `api_test.sh` in a directory of your choice.

### Step 2: Make the Script Executable

1. Open a terminal and navigate to the directory where you saved the script.
2. Run the following command to make the script executable:
   ```bash
   chmod +x api_test.sh
   ```

### Step 3: Running the Script

1. Run the script by executing:
   ```bash
   ./api_test.sh
   ```
2. The script will perform the following actions:
   - Execute various API requests (GET, POST, PUT, DELETE).
   - Display the response for each request.
   - Provide a summary of all requests and their HTTP status codes at the end.

### Step 4: Customizing the Script

1. **Change API Endpoints**: You can modify the `BASE_URL` variable in the script to point to a different server:
   ```bash
   BASE_URL="http://your-server-url:port"
   ```
2. **Modify Request Data**: To change the payload for POST or PUT requests, edit the JSON data inside the corresponding functions. For example, in `add_new_order`:
   ```bash
   -d '{
       "customerId": 1,
       "salespersonId": 1,
       ...
   }'
   ```

### Troubleshooting

1. **Permission Denied Error**: If you get a "Permission denied" error, ensure the script is executable:
   ```bash
   chmod +x api_test.sh
   ```
2. **Command Not Found Errors**:
   - If `curl` or `jq` commands are not found, ensure they are installed and accessible in your system's PATH.

3. **Network Issues**: If you encounter issues reaching the server, ensure the server is running and accessible at the specified `BASE_URL`.

### Optional: Running the Script with Different Input Parameters

You can edit the function calls at the end of the script to change the parameters or add more tests. For example, to get an order with a different ID:
```bash
get_order_by_id 5
```

### Example Output

The script will output the details of each API call, followed by a summary like this:
```plaintext
GET Order by ID: 1
Response Body:
{
  "orderId": 1,
  "customerId": 1,
  "salespersonId": 1,
  ...
}
------------------------
Summary of Requests:
Request: GET Order by ID 1 returned 200
Request: POST Add New Order returned 201
...
```
