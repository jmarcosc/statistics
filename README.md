# statistics
Microservice para análises estatísticas de transações em tempo real

<b><h2>Registering transactions</h2></b>

<i><h3>Request</h3></i>

POST /transactions HTTP/1.1</br>
Host: localhost:8080</br>
Content-Type: application/json</br>
cache-control: no-cache</br>
Postman-Token: 8bc1e052-9c01-4f01-b24c-7c30145156d8</br></br>
{</br>
	"timestamp" : 1556492104000,</br>
	"amount": 300.00</br>
}</br>

<i><h3>Response</h3></i>

HTTP Status 201 CREATED - Transaction created</br>
HTTP Status 204 NO CONTENT - Transaction not created

<b><h2>Retrieving statistics</h2></b>

<i><h3>Request</h3></i>

GET /statistics HTTP/1.1</br>
Host: localhost:8080</br>
cache-control: no-cache</br>
Postman-Token: 02d81345-e029-4195-a67e-19034e700c37</br>

<i><h3>Response</h3></i>

HTTP Status 200 OK - Statistics generated</br>

{</br>
    "sum": 1500,</br>
    "min": 300,</br>
    "max": 300,</br>
    "avg": 300,</br>
    "count": 5</br>
}</br>

HTTP Status 404 NOT FOUND - Statistics not found for the last 60 seconds</br>

<b><h2>Docker commands</h2></b>

<i><h3>Dockerfile</h3></i>

FROM openjdk:8</br>
EXPOSE 8080:8080</br>
ADD target/statistics-0.0.1-SNAPSHOT.jar statistics.jar</br>
ENTRYPOINT ["java", "-jar", "statistics.jar"]</br>

<i><h3>Build and run application with two instances</h3></i>

<ol>
  <li>sudo docker build -f Dockerfile -t statistics .</li>
  <li>sudo docker run -it --name statistics-application-instance-one -p 8080:8080 statistics</li>
  <li>sudo docker run -p 8080:8080 statistics-application-instance-one</li>
  <li>sudo docker run -it --name statistics-application-instance-two -p 9090:8080 statistics</li>
  <li>sudo docker run -p 9090:8080 statistics-application-instance-two</li>
</ol>
