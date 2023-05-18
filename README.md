# Book Management Application

The Book Management Application is a RESTful API that allows you to manage books in a virtual bookstore. You can perform operations like creating a book, retrieving book details, updating book information, and deleting books.
Technologies Used

    -> Scala
    -> Akka HTTP
    -> Spray JSON
    -> sbt (Scala Build Tool)

### Prerequisites

Make sure you have the following installed on your system:

    -> Java Development Kit (JDK) version 8 or above
    -> Scala
    -> sbt

#### Getting Started

Follow the steps below to run the Book Store Application on your local machine:

1. Clone the repository:


    git clone https://github.com/PradyumanKnoldus/bookManagementApplication.git
2. Navigate to the project directory:


    cd bookManagementApplication
3. Compile the application using sbt:


    sbt compile
4. Run the application:


    sbt run

>The application will start running on http://localhost:8080.
API Endpoints

The Book Store Application provides the following API endpoints:

#### Create a Book

Endpoint: POST /bookStore

Create a new book by sending a POST request to the /bookStore endpoint. Include the book details in the request body as JSON.

Example Request:

    POST /bookStore
Content-Type: application/json

    {
    "bookTitle": "The Great Gatsby",
    "authorName": "F. Scott Fitzgerald",
    "publishingYear": 1925
    }

#### Retrieve All Books

Endpoint: GET /bookStore

Retrieve all books by sending a GET request to the /bookStore endpoint.

Example Request:

    GET /bookStore

#### Retrieve a Book

Endpoint: GET /bookStore/{bookID}

Retrieve a specific book by sending a GET request to the /bookStore/{bookID} endpoint. Replace {bookID} with the ID of the book you want to retrieve.

Example Request:

    GET /bookStore/1

#### Update a Book

Endpoint: PUT /bookStore/{bookID}

Update the details of a book by sending a PUT request to the /bookStore/{bookID} endpoint. Replace {bookID} with the ID of the book you want to update. Include the updated book details in the request body as JSON.

Example Request:

PUT /bookStore/1
Content-Type: application/json

    {
    "id": "1"
    "bookTitle": "New Title",
    "authorName": "New Author",
    "publishingYear": 2023
    }

#### Delete a Book

Endpoint: DELETE /bookStore/{bookID}

Delete a specific book by sending a DELETE request to the /bookStore/{bookID} endpoint. Replace {bookID} with the ID of the book you want to delete.

Example Request:

    DELETE /bookStore/1

### Testing

To execute the test cases for the Book Store Application, run the following command:

    sbt test

The test results will be displayed in the console.

### Conclusion

The Book Management Application provides a simple and convenient way to manage books in a virtual bookstore. You can easily create, retrieve, update, and delete books using the provided API endpoints. Feel free to explore and customize the application according to your needs.

For any questions or issues, please contact pradyuman.singh@knoldus.com.

Happy reading!