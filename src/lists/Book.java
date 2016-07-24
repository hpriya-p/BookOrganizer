package lists;
import java.util.*;
public class Book {
	private String title;
	private String authorLastname;
	private String authorFirstname;
	private String genre;
	private String author2Lastname;
	private String author2Firstname;
	private String listName;
	public Book(String title, String authorLastname, String authorFirstname, String genre, String listName) {
		this.title = title;
		this.authorLastname = authorLastname;
		this.authorFirstname = authorFirstname;
		this.author2Lastname = "";
		this.author2Firstname = "";
		this.genre = genre;
		this.listName = listName;
		main.allBooks.add(this);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthorLastname() {
		return authorLastname;
	}
	public String getListName() {
		return listName;
	}
	public void setAuthorLastname(String authorLastname) {
		this.authorLastname = authorLastname;
	}
	public String getAuthorFirstname() {
		return authorFirstname;
	}
	public void setAuthorFirstname(String authorFirstname) {
		this.authorFirstname = authorFirstname;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public void addSecondAuthor (String first, String last){
		this.author2Firstname = first;
		this.author2Lastname = last;
	}
	public String getAuthor2Firstname () {
		return author2Firstname;
	}
	public String getAuthor2Lastname (){
		return author2Lastname;
	}
	public String getAuthor() {
		String author1first = getAuthorFirstname();
		String author1last = getAuthorLastname();
		String author2first = getAuthor2Firstname();
		String author2last = getAuthor2Lastname ();
		if(author2first != ""){
			author2first = "and " + author2first;
		}
		return author1first + " "+ author1last + " "+ author2first + " "+ author2last;
		
	}
	
}