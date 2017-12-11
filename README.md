#  Create, Build and Deploy a JAX-RS Microservice (Application/API Endpoint) in OpenShift CP

**Prerequisities:**
1.  OpenShift CP v3.6 or above.
2.  Select a MySql Server v5.7 (or above) image when deploying the database server Pod.

This JEE7 JAX-RS application demonstrates how to build and deploy a *Purchase Order* microservice as a containerized application (po-service) on OpenShift CP. The JAX-RS application is deployed on a Apache TomEE 7.x (JEE7) application server.  The deployed microservice supports all CRUD operations on purchase orders.

### A] First, create a new project in OpenShift using the Web Console (UI).
Name the project as *myproject*.
### B] Deploy a ephemeral MySql database server instance (Pod) in OpenShift.
Name the application as *mysql*.  Specify the following values for the database parameters
```
Database service name = mysql
Database name = testdb
Database user name = mysql
Database password = password
Database admin password = password
```
Use the DDL and SQL scripts provided in the [sql](https://github.com/ganrad/ocp-tomee7-jaxrs-po/tree/master/src/main/resources/META-INF/sql) folder to create the *po_table* in *testdb* database and populate it with a few sample purchase orders.

### C] Deploy the *Purchase Order* microservice application on OpenShift CP.
1. First, build and deploy the **S2I Builder** image for Apache Tomcat Plus 7.0.4 application server on OpenShift CP.  Refer to the GitHub project [openshift-s2i-tomee7-wp-jdk8](https://github.com/ganrad/openshift-s2i-tomee7-wp-jdk8) for step by step instructions for creating, building and deploying this image on OpenShfit CP.
2. Use the OpenShift Web Console (UI) to deploy this JAX-RS application instance (Pod) on OpenShift. Provide values as specified below in the *New* application definition wizard/workflow and click *Create* application.
- Name the application *po-service* (or pick any other name).
- In the *Browse Images* tab, search for and select the template titled *tomee7-plus-centos7*
- Copy and paste the location of this GitHub repository in the field **GIT_URI**
- Specify the build type for this application in the field **BUILD_TYPE**.  Leave the default (*Maven*) as is or specify *Gradle*.  Build scripts for running both Maven and Gradle builds are included in this repository.
- (Optional) Specify the build arguments in fields **MAVEN_ARGS_APPEND** or **GRADLE_ARGS_APPEND** which you want to pass to the build runtime.

Allow the application build to finish and the application Pod to come up (start).  Test the microservice by using **CURL** in a terminal window or by using a REST client (Browser Plugin).

The *po-service* microservice supports all CRUD operations on purchase orders. The REST API exposed by this microservice can be accessed via the following context path URI's based on the build type used to build this application.
- For *Maven* build, the context path is - /PurchaseOrderAPI/api/
- For *Gradle* build, the context path is - /PurchaseOrderAPI-1.0/api/

The REST API endpoint's exposed by this microservice is as follows.

**`URI Template : HTTP VERB : DESCRIPTION`**
- `order/list`: GET : Retrieve all available purchase orders in the backend database. A sample JSON file containing a purchase order is provided in *add.json*.
- `order/{id}`: GET : Retrieve order details by `order id`.
- `order`: POST: Create a new purchase order. The API consumes and produces orders in `JSON` format.
- `order/{id}`: PUT : Update a purchase order identified by *id*. Provide field values to be updated as Query parameters.  A sample HTTP URL for updating a purchase order is provided in *update.url*
- `order/{id}`: DELETE : Delete purchase order for *id*.

You can access the Purchase Order REST API from your web browser, eg.,
* http://<hostname_route_url>/PurchaseOrderAPI/api/order/list
* http://<hostname_route_url>/PurchaseOrderAPI/api/order/1

Congrats!  You have just built and deployed a simple Springboot microservice on OpenShift CP.
