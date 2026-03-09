#This project is a Spring Boot application that connects to the GitHub REST API and generates a report showing which users have access to which repositories within a given organization.

#The service retrieves repositories for a GitHub organization, fetches collaborator information for each repository, and aggregates the data into a user-to-repositories access report.

#Run the application using:

Windows
.\mvnw.cmd spring-boot:run

Linux / Mac
./mvnw spring-boot:run

#Features

Authenticate with GitHub using a Personal Access Token

Retrieve repositories for a given organization

Fetch collaborators for each repository

Generate an aggregated access report

Expose a REST API endpoint returning JSON data

#Project Structure
src/main/java/com/shweta/github_access_report

controller
 └── AccessReportController.java

service
 └── GithubService.java

GithubAccessReportApplication.java

resources
 └── application.properties

#The architecture follows a clean separation of concerns:

Controller → Handles HTTP requests

Service → Contains GitHub API logic

Configuration → Stores API configuration and token

#How to Run the Project
1️. Clone the repository
git clone https://github.com/your-username/github-access-report.git
cd github-access-report
2️. Set the GitHub token

#Set your GitHub Personal Access Token as an environment variable.

Windows (PowerShell)

$env:GITHUB_TOKEN="your_token_here"

Linux / Mac

export GITHUB_TOKEN=your_token_here
3️. Configure application properties

src/main/resources/application.properties

github.api.url=https://api.github.com
github.token=${GITHUB_TOKEN}
4️. Run the application
mvn spring-boot:run

The application will start on:

http://localhost:8080
API Endpoint
Get Access Report
GET /api/access-report/{organization}

Example:

http://localhost:8080/api/access-report/google
Example Response
{
  "organization": "example-org",
  "accessReport": {
    "alice": ["repo1", "repo2"],
    "bob": ["repo3"]
  }
}

If collaborator information is not publicly accessible:

{
  "organization": "google",
  "accessReport": {}
}
Authentication

The application authenticates with GitHub using a Personal Access Token (PAT).

The token is passed in API requests using the Authorization header.

Example request header:

Authorization: Bearer <GITHUB_TOKEN>

The token is not stored in the repository for security reasons and is instead provided through environment variables.

#Design Decisions
1️. Service Layer

GitHub API calls are implemented in a dedicated service class to keep business logic separate from the controller.

2️. Aggregated Access Report

The service aggregates data into a structure mapping:

user → list of repositories

This allows easy understanding of which repositories each user can access.

3️. Error Handling

If collaborator information cannot be retrieved for a repository (due to permission restrictions or API errors), the repository is skipped so the service can continue processing.

4️. Security

The GitHub token is stored in environment variables instead of hardcoding it in the project.

#Assumptions

GitHub collaborator data is only available for repositories where the authenticated user has appropriate permissions.

Some organizations (e.g., Google, Apache, Microsoft) restrict collaborator information, which may result in an empty access report.

The implementation focuses on demonstrating API integration and data aggregation rather than full enterprise GitHub permission management.

#Technologies Used

Java

Spring Boot

Maven

GitHub REST API

RestTemplate

Future Improvements

Use parallel API calls to improve performance for organizations with many repositories.

Add pagination support for large organizations.

Implement better logging and monitoring.

