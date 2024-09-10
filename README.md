# Employee Management System

EmploySync / EmployWise : Employee Management System a Spring Boot project that involves employee management and NoSQL database integration. Connection between employees and their reporting structures

This is a Spring Boot application for managing employees using MongoDB as the database.

## Setup

1. Install Java 17 or later.
2. Install MongoDB or use a cloud-hosted MongoDB instance.
3. Clone this repository.
4. Create a `.env` file in the root directory with the following content:
   ```env
   MONGODB_URI=mongodb+srv://<username>:<password>@<cluster-url>/<database-name>?retryWrites=true&w=majority&appName=Cluster0
   SMTP_HOST=smtp.gmail.com
   SMTP_PORT=587
   SMTP_USERNAME=your-email@gmail.com
   SMTP_PASSWORD=your-gmail-app-password
   ```
   Replace the placeholders with your actual MongoDB URI and Gmail credentials.
   
   Note: For SMTP_PASSWORD, use an App Password generated from your Google Account settings:
   - Go to your Google Account settings
   - Navigate to Security > 2-Step Verification (enable if not already)
   - Scroll down to "App passwords" and create a new one for this application

## Running the application locally

1. Open a terminal in the project root directory.
2. Run `./mvnw spring-boot:run` to start the application.
3. The application will be available at `http://localhost:8080`.

## API Documentation

### Add Employee
- URL: POST /api/employees
- Request Body:
  ```json
  {
    "employeeName": "John Doe",
    "phoneNumber": "+1234567890",
    "email": "john.doe@example.com",
    "reportsTo": "manager-uuid",
    "profileImage": "https://example.com/profile.jpg"
  }
  ```
- Response: UUID of the created employee
- Notes: 
  - All fields are required except `reportsTo`
  - `phoneNumber` must be a valid phone number format
  - `email` must be a valid email format
  - `profileImage` must be a valid URL

### Get All Employees
- URL: GET /api/employees
- Query Parameters:
  - page (default: 0)
  - size (default: 10)
  - sort (default: "employeeName,asc")
- Response: Page of employees
- Example: GET /api/employees?page=0&size=5&sort=email,desc

### Get Employee by ID
- URL: GET /api/employees/{id}
- Response: Employee details

### Update Employee
- URL: PUT /api/employees/{id}
- Request Body: Same as Add Employee
- Response: Updated employee details

### Delete Employee
- URL: DELETE /api/employees/{id}
- Response: 204 No Content

### Get nth Level Manager
- URL: GET /api/employees/{id}/manager/{level}
- Response: Manager details
- Example: GET /api/employees/123e4567-e89b-12d3-a456-426614174000/manager/2

### Health Check
- URL: GET /health
- Response: "Application is running"

## Deployment on Render

1. Fork this repository to your GitHub account.
2. Create a new Web Service on Render (https://dashboard.render.com/).
3. Connect your GitHub account and select the forked repository.
4. Render will automatically detect the `render.yaml` file and configure the deployment.
5. Set up the environment variables in Render as specified in the `.env` file.
6. Click "Create Web Service" to deploy the application.

Render will automatically build and deploy your application. Once deployed, you can access your API at the provided Render URL.

## Testing

You can use tools like Postman or curl to test the API endpoints. Make sure to replace `{base_url}` with either `http://localhost:8080` for local testing or the Render-provided URL for the deployed version.

Example curl command to add an employee:
```bash
curl -X POST {base_url}/api/employees \
-H "Content-Type: application/json" \
-d '{"employeeName":"John Doe","phoneNumber":"1234567890","email":"john.doe@example.com","reportsTo":"manager-uuid","profileImage":"https://example.com/profile.jpg"}'
```

Replace `manager-uuid` with an actual UUID of an existing employee to set as the manager.

## Error Handling

The application includes basic error handling. If an operation fails, you'll receive an appropriate HTTP status code and an error message in the response body.

## Security Considerations

This application does not include authentication or authorization. In a production environment, you should implement proper security measures to protect your API and data.

## AUTHOR: JAYANTH THALLAPELLI

GITHUB: https://github.com/jayanth9676

mail: thallapellijayanth963@gmail.com