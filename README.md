# Inventory-Manager

This is the first Java program that I wrote. The goal of the program is to provide
a graphical user interface (GUI) to allow a user to keep track of parts and products
used in a fictious business. Note that the program is not connected to a database and
therefore does not save any events that occur while using the program. Functionality
includes:
* Creating Parts and Products through a GUI.
* Updating and Deleting Parts or Products that already exist.
* When creating or updating a Product, the user can view everything 
currently in the database and adding it to the Product.

What was learned:
* The Model-View-Controller structure in creating a Full-Stack project.
* How the Model handles the data, the View presents it, and the Controller
sits in between the Model and View to process all user requests.
* How to encapsulate data into objects to protect it, inherit the methods and fields of 
a parent class to reduce redundant code, polymorphism which works with inheritance to allow
child classes to take various forms, and abstraction to remove the need to understand the
underlying mechanics to handling the data.


What can be improved:
* The methods written in the Controller should be refactored so it does not have
as much control. It was pointer out that this can cause errors or security issues.
* The lack of a database.
