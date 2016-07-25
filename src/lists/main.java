package lists;
import java.util.*;
import java.io.*;
import javax.swing.JFileChooser;
public class main {
	public static HashMap<String, ArrayList<Book>> Author = new HashMap<String, ArrayList<Book>>();
	public static ArrayList<Book> allBooks = new ArrayList<Book>();
	public static ArrayList<List> allLists = new ArrayList<List>();
	public static File newFile = new File("");
	public static File original = new File("");
	public static void main (String[] args) throws IOException {
		addBook();
		while(true)
		{
		//Main Menu
		System.out.println("\nPlease type the corresponding number to select a function from the menu below:\n");
		System.out.println("1: Edit Book");
		System.out.println("2: Search by Author");
		System.out.println("3: See all items in a List");
		System.out.println("4: Search by Title");
		System.out.println("5: Search by Keyword");
		System.out.println("6: Exit\n");
		Scanner input2 = new Scanner(System.in);
		int choice = input2.nextInt();
		if(choice == 6)
		/* Make sure to save the changes before exiting. The while loop below will collect user input
		 * and perform the action based on that before exiting the program. The user has 3 options:
		 * 	1) Save: the user's original input file is overwritten by "booksUpdated.txt" and "booksUpdated.txt" is deleted
		 *  2) Save As: the user's original input file is left unchanged; "booksUpdated.txt" is not deleted
		 *  3) Quit Without Saving: the user's original input file is left unchanged. "booksUpdated.txt" is not deleted
		 */
		{	
			while(true)
			{
				System.out.println("\n\bWould you like to save your changes?\b");
				System.out.println("1: Save");
				System.out.println("2: Save As");
				System.out.println("3: Quit without Saving");
				Scanner saving = new Scanner(System.in);
				int toSave = saving.nextInt();
	//			saving.close();
				switch(toSave)
				{
				case 1:
					FileReader readUpdated = new FileReader(newFile);
					BufferedReader brUpdated = new BufferedReader(readUpdated);
					String line = null;
					PrintWriter writer = new PrintWriter(original);
					while ((line = brUpdated.readLine()) != null) {
						writer.println(line);
					}
					writer.close();
					brUpdated.close();
					readUpdated.close();
					File del = new File("booksUpdated.txt");
					del.delete();
					break;
				case 2:
					break;
				case 3:
					File del2 = new File("booksUpdated.txt");
					del2.delete();
					break;
				default:
					System.out.println("Invalid Command. Please Try again.");
					break;
				}
				if(toSave<4)
				{
					break;
				}
				
			}
			System.out.println("Goodbye\n");
			File del = new File("cache.txt");
			del.delete();
			break;
		}
		//input2.close();

		switch(choice)
		{
		case 1:
			Scanner bookTitle = new Scanner(System.in);
			System.out.println("Please enter the Title of the book you wish to edit.");
			String title = bookTitle.nextLine();
			Book book = getBookFromName(title);
			while (true)
			{
			  if (book == null)
			  {
				System.out.println("Did you mean:");
				for(int i=0; i<allBooks.size(); i++)
				{
				   if ( allBooks.get(i).getTitle().toLowerCase().contains(title.toLowerCase())|| 
					    allBooks.get(i).getAuthor().toLowerCase().contains(title.toLowerCase())||
					    allBooks.get(i).getGenre().toLowerCase().contains(title.toLowerCase() ) )
					{
						System.out.println("\"" + allBooks.get(i).getTitle()+ "\" by "+ allBooks.get(i).getAuthor());
					}
				}
				System.out.println("Please enter the title (text within \") of the book you wish to edit");
				
				title = bookTitle.nextLine();
				book = getBookFromName(title);
			  }
			  else
			  {
				break;
			  }
			}
			editBook(book);
			break;
		case 2:
			searchHashMap();
			System.out.println("");
			break;
		case 3: 
			searchByList();
			System.out.println("");
			break;
		case 4:
			searchByTitle();
			System.out.println("");
			break;
		case 5:
			searchByKeyword();
			System.out.println("");
			break;
		default:
			System.out.println("Invalid Argument: Please try again.");
		}}
	}
	
	/* method: addBook()
	 * input : void
	 * output: void
	 * description: Prompts user to choose a .txt file. Reads and parses the file to generate book objects, 
	 * 				list objects, and adds books to respective lists. 
	 */
  public static void addBook ()throws IOException
  {	
	JFileChooser fileChooser = new JFileChooser();
	fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
	int result = fileChooser.showOpenDialog(null);
	if(result == JFileChooser.APPROVE_OPTION)
	{	
		System.out.println("Reading text file \n"); 
		original = fileChooser.getSelectedFile();
		newFile = fileChooser.getSelectedFile();
		FileReader toRead = new FileReader(newFile);
		
		BufferedReader br = new BufferedReader(toRead);
		String line = null;
		PrintWriter copier = new PrintWriter("cache.txt");
		while ((line = br.readLine()) != null) {
			String[] parsedLine = line.split("/");
			String secondAuthorfn = "";
			String secondAuthorln = "";
				String title = parsedLine[0];
				String authorfn = parsedLine[1];
				String authorln = parsedLine[2];
				String genre = parsedLine[3];
				String listName = parsedLine[4];
				Book book = new Book(title, authorln, authorfn, genre, listName);
				copier.println(line);
				if(parsedLine.length > 5)
				{
					secondAuthorfn = parsedLine[5];
					secondAuthorln = parsedLine[6];
					book.addSecondAuthor(secondAuthorfn, secondAuthorln);
				}
				addToHashMaps(book);
				Boolean ListDNE = true;
				for(List l : allLists)
				{
					if(l.getListName().equals(listName))
					{
						ListDNE = false;
						l.addBookToList(book);
						
					}
				}
				if(ListDNE)
				{
					List list = new List(listName);
					list.addBookToList(book);
				}
			
		}
	 
		br.close();
		copier.close();
	 }
	
   }
  /* method: editBook()
	 * input : Book object
	 * output: void
	 * description: Prompts the user to select an item from the menu. 
	 * 				Using user input, the method edits various properties of a given book.
	 */
   public static void editBook (Book toBeEdited) throws IOException
   {
		System.out.println("\bPlease select an item from the menu below:");
		System.out.println("1: Add to List");
		System.out.println("2: Add a second author");
		System.out.println("3: Edit Title");
		System.out.println("4: Edit Author");
		System.out.println("5: Exit");
		Scanner reader = new Scanner(System.in);
		int choice = reader.nextInt();
		//reader.close();
		switch(choice)
		{
		case 1:
			String oldList = toBeEdited.getListName();
			List old = getListFromName(oldList);
			System.out.println("Please enter the name of the list:");
			Scanner listReader = new Scanner(System.in);
			String listName = listReader.nextLine();
			List list = getListFromName(listName);
			if(list == null)
			{
				list = new List(listName);
				System.out.println("New list '"+listName+"' created");
			}
			list.addBookToList(toBeEdited);
			writeToFile(toBeEdited,listName,4);
			old.removeBookFromList(toBeEdited);
			break;
		case 2:
			Scanner string = new Scanner(System.in);
			System.out.print("First Name: ");
			String fn = string.nextLine();
			System.out.print("Last Name: ");
			String ln = string.next();
			writeToFile(toBeEdited, fn,5);
			writeToFile(toBeEdited, ln,6);
			toBeEdited.addSecondAuthor(fn, ln);
			break;
		case 3:
			Scanner titleReader = new Scanner(System.in);
			System.out.print("Title: ");
			String title = titleReader.nextLine();
			writeToFile(toBeEdited, title,0);
			toBeEdited.setTitle(title);
			break;
		case 4:
			Scanner author = new Scanner(System.in);
			System.out.print("First Name: ");
			String firstname = author.nextLine();
			System.out.println("Last Name: ");
			String lastname = author.next();
			writeToFile(toBeEdited, firstname,1);
			writeToFile(toBeEdited, lastname,2);
			toBeEdited.setAuthorFirstname(firstname);
			toBeEdited.setAuthorLastname(lastname);
			break;
		case 5:
			break;
		default:
			System.out.println("Invalid Argument: Please try again");
			editBook(toBeEdited);
		}
	}
   
   /* method: writeToFile()
	 * input : Book object, String, integer
	 * output: void
	 * description: Overwrites a required segment of a line (determined using the inputs) in the file "booksUpdated.txt". 
	 */
	public static void writeToFile (Book book, String string, int index) throws IOException
	{
		FileReader toRead = new FileReader("cache.txt");
		File updated = new File("booksUpdated.txt");
		BufferedReader br = new BufferedReader(toRead);
		String line = null;
		PrintWriter writer = new PrintWriter("booksUpdated.txt");
		while ((line = br.readLine()) != null) {
			String[] parsing = line.split("/");
			
			if(parsing[0].equals(book.getTitle())){
				if(index<parsing.length){
				parsing[index]=string;
				line = "";
				for(String item : parsing)
				{
					line = line + item+"/";
				}
				
			}
				else{
				line = line +"/"+ string;	
				}
			}
			
			writer.println(line);
		}
		writer.close();
		File cacheFile = new File("cache.txt");
		PrintWriter cache = new PrintWriter(cacheFile);
		FileReader updatedReader = new FileReader(updated);
		BufferedReader reader2 = new BufferedReader(updatedReader);
		String line2 = null;
		while ((line2 = reader2.readLine()) != null) {
			cache.println(line2);
		}
		newFile = updated;
		reader2.close();	
		cache.close();
		br.close();
	}
	
	/* method: addToHashMaps()
	 * input : Book object
	 * output: void
	 * description: This method adds a book to the "Author" HashMap. The key is the book's author(s), 
	 * 				and the value is an ArrayList of Book objects
	 */
	public static void addToHashMaps(Book book) {
		String author1FirstName = book.getAuthorFirstname().toLowerCase();
		String author1LastName = book.getAuthorLastname().toLowerCase();
		String author2FirstName = book.getAuthor2Firstname().toLowerCase();
		String author2LastName = book.getAuthor2Lastname().toLowerCase();
		String key = author1FirstName+"/"+author1LastName+"/"+author2FirstName+"/"+author2LastName;
		ArrayList<Book> value = new ArrayList<Book>();
		if(Author.containsKey(key))
		{
			value = (ArrayList<Book>) Author.get(key);
		}
		value.add(book);
		Author.put(key, value);
	}
	
	/* method: searchHashMap()
	 * input : void
	 * output: void
	 * description: This method collects the first and last names of an author (type: String) from the user 
	 * 				It searches the HashMap "Author" and displays all books which have the same author. 
	 */
	public static void searchHashMap()
	{	
		System.out.println("Please enter the name of the Author you wish to search for");
	    System.out.print("First Name:");
	    Scanner scan = new Scanner(System.in);
		String authorFirstName = scan.nextLine();
	    System.out.print("Last Name:");
		String authorLastName = scan.nextLine();
	    System.out.println("\n");
		authorFirstName = authorFirstName.toLowerCase();
		authorLastName = authorLastName.toLowerCase();
		ArrayList<String> allKeys = new ArrayList<String>(Author.keySet());
		ArrayList<String> desiredKeys = new ArrayList<String>();
		for (int i=0; i<allKeys.size(); i++)
		{	
			String key = (String)allKeys.get(i);
			String[]parsedKey = key.split("/");
			String fn1 = parsedKey[0];
			String ln1 = parsedKey[1];
			String fn2 = "";
			String ln2 = "";
			if (parsedKey.length>2)
			{
				fn2 = parsedKey[2];
				ln2 = parsedKey[3];
			}
			if (authorFirstName.length()>0)
			{
				if (authorLastName.length()>0)
				{
					if ((authorFirstName.equals(fn1)|| authorFirstName.equals(fn2)) && 
						(authorLastName.equals(ln1) || authorLastName.equals(ln2)))
					{
						desiredKeys.add(key);
					}
				}
				else 
				{
					if (authorFirstName.equals(fn1) || authorFirstName.equals(fn2))
					{
						desiredKeys.add(key);
					}
				}
			}
			else
			{
				if (authorLastName.equals(ln1) || authorLastName.equals(ln2))
				{
					desiredKeys.add(key);
				}
			}
		}
		for (int j = 0; j<desiredKeys.size(); j++)
		{
			String newkey = (String)desiredKeys.get(j);
			ArrayList<Book> booklist = (ArrayList<Book>)Author.get(newkey);
			for(int i = 0; i<booklist.size();i++)
			{
				Book book = (Book)booklist.get(i);
				System.out.println(book.getTitle() + " by " + book.getAuthor());
			}
		}	
		if (desiredKeys.isEmpty())
		{
			System.out.println("Author not found");
		}
		//scan.close();

	}
	
	
	/* method: searchByList()
	 * input : void
	 * output: void
	 * description: This method displays all existing lists and then collects a list name (type: String)
	 * 				from the user, and displays all book objects within the list. 
	 */
	public static void searchByList()
	{
		System.out.println("Please select a list from below:");
		for (int i=0; i<allLists.size(); i++)
		{
			System.out.println(allLists.get(i).getListName());
		}
		Scanner reader = new Scanner(System.in);
		String name = reader.nextLine();
		name = name.toLowerCase();
		List list = getListFromName(name);
		printList(list);
		//reader.close();
	}
	
	/* method: printList()
	 * input : List object
	 * output: void
	 * description: This method takes a book list (type: List) as its input. It displays 
	 * 				the title and author of every book in the list.  
	 */
	public static void printList(List booklist)
	{
		for (int i=0; i<booklist.getListOfBooks().size(); i++)
		{
			System.out.println(booklist.getListOfBooks().get(i).getTitle()+ " by "+booklist.getListOfBooks().get(i).getAuthor());
		}
	}
	
	/* method: getBookFromName()
	 * input : String
	 * output: Book object
	 * description: This method takes a book's title (type: String) as its input. It returns the 
	 * 				Book object which corresponds to the title. It is case insensitive. 
	 */
	public static Book getBookFromName(String name)
	{
		name = name.toLowerCase();
		for (int i=0; i<allBooks.size(); i++)
		{
			String title = allBooks.get(i).getTitle().toLowerCase();
			if (title.equals(name))
			{
				Book book = allBooks.get(i);
				return book;
			}
		}
		System.out.println("Could not find the book.");
		return null;
	}
	
	/* method: getListFromName()
	 * input : String
	 * output: List object
	 * description: This method takes the list name (type: String) as its input. It returns the 
	 * 				List object which corresponds to list name. It is case insensitive. 
	 */
	public static List getListFromName(String name)
	{
		name = name.toLowerCase();
		for (int i=0; i<allLists.size(); i++)
		{
			String title = allLists.get(i).getListName().toLowerCase();

			if (title.equals(name))
			{
				List list = allLists.get(i);
				return list;
			}
		}
		System.out.println("Could not find the list.");
		return null;
	}
	
	
	/* method: searchByTitle()
	 * input : void
	 * output: void
	 * description: This method collects a title (type: String) from the user, and displays all 
	 * 				book objects which have the same title. It is case insensitive. 
	 */
	public static void searchByTitle()
	{	
		System.out.println("Please enter the title of the book you are looking for");
		Scanner reader = new Scanner(System.in);
		String title = reader.nextLine();
		
		for (int i=0; i<allBooks.size(); i++)
		{
			if (allBooks.get(i).getTitle().toLowerCase().contains(title.toLowerCase()))
			{
				System.out.println(allBooks.get(i).getTitle());
			}
		}
		//reader.close();
	}
	
	
	/* method: searchByKeyword()
	 * input : void
	 * output: void
	 * description: This method collects a keyword (type: String) from the user, and displays all book objects 
	 *              which contain the keyword in one of its fields (namely title, author, or genre).
	 *              It is case insensitive. 
	 */
	public static void searchByKeyword()
	{	
		System.out.println("Please enter the title of the book you are looking for");
		Scanner reader = new Scanner(System.in);
		String keyword = reader.nextLine();
		boolean noResult = true;
		for (int i=0; i<allBooks.size(); i++)
		{
			if (allBooks.get(i).getTitle().toLowerCase().contains(keyword.toLowerCase())|| allBooks.get(i).getAuthor().toLowerCase().contains(keyword.toLowerCase())||allBooks.get(i).getGenre().toLowerCase().contains(keyword.toLowerCase()))
			{
				System.out.println(allBooks.get(i).getTitle()+ " by "+ allBooks.get(i).getAuthor());
				noResult = false;
			}
		}
		if(noResult)
		{
			System.out.println("No entries match your query");
		}
		//reader.close();
	}
}


