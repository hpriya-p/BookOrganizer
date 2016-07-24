package lists;

import java.util.*;

public class List {
	private String listName;
	private ArrayList<Book> listOfBooks;
	private Boolean isCompleted;
	public List(String listName) {
		this.listName = listName;
		this.listOfBooks = new ArrayList<Book>();
		this.isCompleted = false;
		main.allLists.add(this);
	}
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public ArrayList<Book> getListOfBooks() {
		return listOfBooks;
	}
	public void setListOfBooks(ArrayList<Book> booklist) {
		this.listOfBooks = booklist;
	}
	public Boolean getIsCompleted() {
		return isCompleted;
	}
	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	
	public void addBookToList (Book book){
		ArrayList<Book> booklist = getListOfBooks();
		booklist.add(book);		
		System.out.println(book.getTitle() + " has been added to " + getListName());
	}
	public void removeBookFromList (Book book){
		ArrayList<Book> booklist = getListOfBooks();
		booklist.remove(book);
	}
}
