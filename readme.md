Book Organizer
============
Description
------------
The book organizer reads a .txt file and generates book objects. Each book object is added to its respective list. The user is then redirected to the main menu which offers several functions for editing and searching the books. All changes are written to the file ``booksUpdated.txt``. Before exiting, the user is prompted to ``Save``, ``Save As``, or ``Quit without Saving``. The original and ``booksUpdated.txt`` files are manipulated accordingly. 

Running the Program
-------------------
####Windows####
In Command prompt, ``cd`` into the directory which contains ``book.jar``. Then enter ``java -jar book.jar``. You will be prompted to choose a file: select the .txt file which contains all of the books you wish to add. The rest of the program will run in Command Prompt itself. 

Input Specifications
--------------------
Every line in the input .txt file is parsed as a single book object. Each line should be written as follows:


``Title/Author First name/Author Last name/genre/List name``

The ``/`` character is used for parsing the string. ``List name`` is the name of the list to which the book should be added. For a sample input file, please refer to ``book.txt``

