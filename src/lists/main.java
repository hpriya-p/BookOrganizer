package lists;
import java.util.*;
import java.io.*;
import javax.swing.JFileChooser;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
public class main {
	public static HashMap Author = new HashMap();
	public static ArrayList<Book> allBooks = new ArrayList();
	public static ArrayList<List> allLists = new ArrayList();
	public static File newFile = new File("");
	public static File original = new File("");
	public static void main(String[] args) throws IOException {
		addBook();
		while(true)
		{
		System.out.println("Please type the corresponding number to select a function from the menu below:");
		System.out.println("1: Edit Book");
		System.out.println("2: Search by Author");
		System.out.println("3: See all items in a List");
		System.out.println("4: Search by Title");
		System.out.println("5: Search by Keyword");
		System.out.println("6: Exit");
		Scanner input = new Scanner(System.in);
		int choice = input.nextInt();
		if(choice==6)
		{	while(true)
			{
				System.out.println("Would you like to save your changes?");
				System.out.println("1: Save");
				System.out.println("2: Save As");
				System.out.println("3: Quit without Saving");
				Scanner saving = new Scanner(System.in);
				int toSave = saving.nextInt();
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
					System.out.println("Invalid Command. Please Try again");
					break;
				}
				if(toSave<4)
				{
					break;
				}
				
			}
			System.out.println("Goodbye");
			File del = new File("cache.txt");
			del.delete();
			break;
		}
		switch(choice)
		{
		case 1:
			System.out.println("Please enter the title of the book you wish to edit");
			Scanner bookTitle = new Scanner(System.in);
			String title = bookTitle.nextLine();
			Book book = getBookFromName(title);
			while(true)
			{
			if(book == null)
			{
				System.out.println("Did you mean:");
				for(int i=0; i<allBooks.size(); i++)
				{
					if(allBooks.get(i).getTitle().toLowerCase().contains(title.toLowerCase())|| allBooks.get(i).getAuthor().toLowerCase().contains(title.toLowerCase())||allBooks.get(i).getGenre().toLowerCase().contains(title.toLowerCase()))
					{
						System.out.println(allBooks.get(i).getTitle()+ " by "+ allBooks.get(i).getAuthor());
					}
				}
				System.out.println("Please enter the title of the book you wish to edit");
				Scanner bookTitle2 = new Scanner(System.in);
				title = bookTitle.nextLine();
				book = getBookFromName(title);
			}
			else{
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
			System.out.println("Invalid Argument: Please try again");
		}}
	}
	public static void addBook ()throws IOException
	{		
	JFileChooser fileChooser = new JFileChooser();
	fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
	int result = fileChooser.showOpenDialog(null);
	if(result == JFileChooser.APPROVE_OPTION)
	{	original = fileChooser.getSelectedFile();
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

	public static void editBook (Book toBeEdited) throws IOException
	{
		System.out.println("Please select an item from the menu below:");
		System.out.println("1: Add to List");
		System.out.println("2: Add a second author");
		System.out.println("3: Edit Title");
		System.out.println("4: Edit Author");
		System.out.println("5: Exit");
		Scanner reader = new Scanner(System.in);
		int choice = reader.nextInt();
		switch(choice)
		{
		case 1:
			String oldList = toBeEdited.getListName();
			List old = getListFromName(oldList);
			System.out.println("Please enter the name of the list");
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
	public static void addToHashMaps(Book book) {
		String author1FirstName = book.getAuthorFirstname().toLowerCase();
		String author1LastName = book.getAuthorLastname().toLowerCase();
		String author2FirstName = book.getAuthor2Firstname().toLowerCase();
		String author2LastName = book.getAuthor2Lastname().toLowerCase();
		String key = author1FirstName+"/"+author1LastName+"/"+author2FirstName+"/"+author2LastName;
		ArrayList value = new ArrayList();
		if(Author.containsKey(key)){
			value = (ArrayList) Author.get(key);
		}
		value.add(book);
		Author.put(key, value);
	}
	
	public static void searchHashMap()
	{	System.out.println("Please write the name of the author you wish to search for");
	System.out.print("First Name:");
	Scanner scan = new Scanner(System.in);
		String authorFirstName = scan.nextLine();
	System.out.print("Last Name:");
		String authorLastName = scan.nextLine();
	System.out.println("");
		authorFirstName = authorFirstName.toLowerCase();
		authorLastName = authorLastName.toLowerCase();
		ArrayList allKeys = new ArrayList(Author.keySet());
		ArrayList desiredKeys = new ArrayList();
		for(int i=0; i<allKeys.size(); i++)
		{	String key = (String)allKeys.get(i);
			String[]parsedKey = key.split("/");
			String fn1 = parsedKey[0];
			String ln1 = parsedKey[1];
			String fn2 = "";
			String ln2 = "";
			if(parsedKey.length>2){
				fn2 = parsedKey[2];
				ln2 = parsedKey[3];
			}
			if(authorFirstName.length()>0){
				if(authorLastName.length()>0)
				{
					if((authorFirstName.equals(fn1)|| authorFirstName.equals(fn2)) && (authorLastName.equals(ln1) || authorLastName.equals(ln2)))
					{
						desiredKeys.add(key);
					}
				}
				else {
					if(authorFirstName.equals(fn1) || authorFirstName.equals(fn2))
					{
						desiredKeys.add(key);
					}
				}
			}
			else{
				if(authorLastName.equals(ln1) || authorLastName.equals(ln2))
				{
					desiredKeys.add(key);
				}
			}
		}
		for(int j = 0; j<desiredKeys.size();j++){
			String newkey = (String)desiredKeys.get(j);
			ArrayList booklist = (ArrayList)Author.get(newkey);
			for(int i = 0; i<booklist.size();i++)
			{
				Book book = (Book)booklist.get(i);
				System.out.println(book.getTitle() + " by " + book.getAuthor());
			}
		}	
		if(desiredKeys.isEmpty())
		{
			System.out.println("Author not found");
		}
	}
	
	public static void searchByList()
	{
		System.out.println("Please select a list from below:");
		for(int i=0; i<allLists.size();i++)
		{
			
			System.out.println(allLists.get(i).getListName());
			
		}
		Scanner reader = new Scanner(System.in);
		String name = reader.nextLine();
		name = name.toLowerCase();
		List list = getListFromName(name);
		printList(list);
	}
	public static void printList(List booklist)
	{
		for(int i=0; i<booklist.getListOfBooks().size();i++){
			System.out.println(booklist.getListOfBooks().get(i).getTitle()+ " by "+booklist.getListOfBooks().get(i).getAuthor());
		}
	}
	public static Book getBookFromName(String name)
	{
		name = name.toLowerCase();
		for(int i=0; i<allBooks.size();i++)
		{
			String title = allBooks.get(i).getTitle().toLowerCase();
			if(title.equals(name))
			{
				Book book = allBooks.get(i);
				return book;
			}
			
		}
		System.out.println("Could not find book");
		return null;
	}
	public static List getListFromName(String name)
	{
		name = name.toLowerCase();
		for(int i=0; i<allLists.size();i++)
		{
			String title = allLists.get(i).getListName().toLowerCase();
			if(title.equals(name))
			{
				List list = allLists.get(i);
				return list;
			}
			
		}
		System.out.println("Could not find list");
		return null;
	}
	public static void searchByTitle()
	{	System.out.println("Please enter the title of the book you are looking for");
		Scanner reader = new Scanner(System.in);
		String keyword = reader.nextLine();
		for(int i=0; i<allBooks.size(); i++)
		{
			if(allBooks.get(i).getTitle().toLowerCase().contains(keyword.toLowerCase()))
			{
				System.out.println(allBooks.get(i).getTitle());
			}
		}
	}
	public static void searchByKeyword()
	{	System.out.println("Please enter the title of the book you are looking for");
		Scanner reader = new Scanner(System.in);
		String keyword = reader.nextLine();
		for(int i=0; i<allBooks.size(); i++)
		{
			if(allBooks.get(i).getTitle().toLowerCase().contains(keyword.toLowerCase())|| allBooks.get(i).getAuthor().toLowerCase().contains(keyword.toLowerCase())||allBooks.get(i).getGenre().toLowerCase().contains(keyword.toLowerCase()))
			{
				System.out.println(allBooks.get(i).getTitle()+ " by "+ allBooks.get(i).getAuthor());
			}
		}
	}
}


