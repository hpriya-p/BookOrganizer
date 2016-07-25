Book Organizer
============
Description
------------
The book organizer reads a .txt file and generates book objects. Each book object is added to its respective list. The user is then redirected to the main menu which offers several functions for editing and searching the books. All changes are written to the file ``booksUpdated.txt``. Before exiting, the user is prompted to ``Save``, ``Save As``, or ``Quit without Saving``. The original and ``booksUpdated.txt`` files are manipulated accordingly. 

Running the Program
-------------------
###Windows###
In Command prompt, ``cd`` into the directory which contains ``book.jar``. Then enter ``java -jar book.jar``. You will be prompted to choose a file: select the .txt file which contains all of the books you wish to add. The rest of the program will run in Command Prompt itself. 

Input Specifications
--------------------
Every line in the input .txt file is parsed as a single book object. Each line should be written as follows:


``Title/Author First name/Author Last name/genre/List name``

The ``/`` character is used for parsing the string. ``List name`` is the name of the list to which the book should be added. For a sample input file, please refer to ``book.txt``

Menu Functions
--------------
Currently, the program supports the following functions, which can be accessed from the main menu:


 -``Edit Book`` - changes properties of the book. All changes are written into ``booksUpdated.txt``
   -``Add to List`` - removes book from current list and adds it to a new list
   *``Edit Title`` - replaces title with new string
   *``Edit First Author`` - replaces author first/last name with new string(s)
   *``Add/Edit Second Author`` - appends second author first name and last name to end of line in ``booksUpdated.txt``. If a second author was already present, the second author first/last names are replaced with new string(s). 
*``See all items in a List`` - displays the title and author of every book in a given list
*``Search by Title`` - displays all books with a given title
*``Search by Author`` - displays all books by a given author
*``Search by Keyword`` - displays all books which contain the desired keyword in one of its fields (i.e. title, author, or genre)
*``Exit``
  *``Save`` - overwrites original file
  *``Save As`` - does not change original file. Keeps ``booksUpdated.txt`` which contains changes
  *``Quit without Saving`` - deletes ``booksUpdated.txt`` and does not change original file.

  
