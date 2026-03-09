package com.softserve.academy;

import com.softserve.academy.BookManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookManagerTest {

    // AAA: Arrange-Act-Assert pattern
    // Arrange - підготовка даних (BeforeEach створює базову колекцію)
    // Act - виконання дії
    // Assert - перевірка результату

    private BookManager bookManager;

    @BeforeEach
    void setUp() {
        // Arrange - створюємо базову колекцію книг для кожного тесту
        bookManager = new BookManager();
        bookManager.addBook(new Book("Fairy Tale", "Stephen King", "Horror", 2022));
        bookManager.addBook(new Book("The Great Adventure", "Alice Johnson", "Drama", 2022));
        bookManager.addBook(new Book("Space Odyssey", "Alice Johnson", "Fantastic", 2024));
        bookManager.addBook(new Book("Life's Journey", "Bob Smith", "Drama", 2021));
        bookManager.addBook(new Book("Science Explained", "Charlie Brown", "Science", 2019));
    }

    // ===== addBook() tests =====

    @Test
    void shouldAddNewUniqueBook() {
        // Arrange
        Book newBook = new Book("New Discoveries", "Diana Green", "Drama", 2024);

        // Act
        bookManager.addBook(newBook);

        // Assert
        assertEquals(6, bookManager.size());
    }

    @Test
    void shouldThrowExceptionWhenAddingNullBook() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookManager.addBook(null));
    }

    @Test
    void shouldThrowExceptionWhenAddingDuplicateBook() {
        // Arrange
        Book duplicateBook = new Book("The Great Adventure", "Alice Johnson", "Drama", 2022);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookManager.addBook(duplicateBook));
    }

    // ===== listOfAllAuthors() tests =====

    @Test
    void shouldReturnListOfAllUniqueAuthors() {
        // Arrange - дані вже в setUp()

        // Act
        List<String> authors = bookManager.listOfAllAuthors();

        // Assert
        assertEquals(4, authors.size());
        assertTrue(authors.contains("Alice Johnson"));
        assertTrue(authors.contains("Bob Smith"));
        assertTrue(authors.contains("Charlie Brown"));
    }

    @Test
    void shouldReturnEmptyListWhenNoBooks() {
        // Arrange
        BookManager emptyManager = new BookManager();

        // Act
        List<String> authors = emptyManager.listOfAllAuthors();

        // Assert
        assertTrue(authors.isEmpty());
    }

    // ===== listAuthorsByGenre() tests =====

    @Test
    void shouldReturnAuthorsForExistingGenre() {
        // Arrange
        String genre = "Drama";

        // Act
        List<String> authors = bookManager.listAuthorsByGenre(genre);

        // Assert
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Alice Johnson"));
        assertTrue(authors.contains("Bob Smith"));
    }

    @Test
    void shouldReturnEmptyListForNonExistingGenre() {
        // Arrange
        String genre = "Love story";

        // Act
        List<String> authors = bookManager.listAuthorsByGenre(genre);

        // Assert
        assertTrue(authors.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenGenreIsNull() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookManager.listAuthorsByGenre(null));
    }

    @Test
    void shouldThrowExceptionWhenGenreIsBlank() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookManager.listAuthorsByGenre("   "));
    }

    // ===== listAuthorsByYear() tests =====

    @Test
//    @Disabled("TODO: Реалізувати тест - має повертати авторів для конкретного року")
    void shouldReturnAuthorsForSpecificYear() {
        int yearOfBook = 2022;
        List<String> listAuthorsByYear = bookManager.listAuthorsByYear(yearOfBook);
        assertNotNull(listAuthorsByYear, "Test should return authors for chosen year");
    }

    @Test
    void shouldReturnEmptyListForYearWithNoBooks() {
        int yearOfBook = 2018;
        List<Book> listBooksByYear = bookManager.findBooksByYear(yearOfBook);
        assertTrue(listBooksByYear.isEmpty(), "Рік " + yearOfBook + " не містить книг.");
    }

    @Test
    void shouldThrowExceptionWhenYearIsNegative() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookManager.listAuthorsByYear(-2022));
    }

    @Test
    void shouldThrowExceptionWhenYearIsZero() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookManager.listAuthorsByYear(0));
    }

    // ===== findBookByAuthor() tests =====

    @Test
    void shouldFindFirstBookByExistingAuthor() {
        String author = "Alice Johnson";
//        String author = "Taras Shevchenko";
        Optional<Book> getFirstBookByList = bookManager.findBookByAuthor(author).stream().findFirst();

        assertEquals(author, getFirstBookByList.get().getAuthor(), "За автором " + author + " знайдено книгу " + getFirstBookByList.get().getTitle());
    }

    @Test
    void shouldReturnEmptyOptionalForNonExistingAuthor() {
        // Arrange
        String author = "Unknown Author";

        // Act
        Optional<Book> result = bookManager.findBookByAuthor(author);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void shouldThrowExceptionWhenAuthorIsNull() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookManager.findBookByAuthor(null));
    }

    @Test
    void shouldThrowExceptionWhenAuthorIsBlank() {
        String blankData = "";

        assertThrows(IllegalArgumentException.class, () -> {
                    bookManager.findBookByAuthor(blankData);
                },
                "Test should throw exception if author is null"
        );
        // Anton 09/03
    }

    // ===== findBooksByYear() tests =====

    @Test
    void shouldFindAllBooksByYear() {
        int yearOfBook = 2022;

        List<Book> bookList = bookManager.findBooksByYear(yearOfBook);

        assertNotNull(bookList);
        assertEquals(2, bookList.size(), "Test should return all books by chosen year");
    }

    @Test
    void shouldReturnEmptyListWhenNoBooksByYear() {
        int yearOfBook = 2030;
        List<Book> bookList = bookManager.findBooksByYear(yearOfBook);
        assertEquals(0, bookList.size(), "Collection should be empty if there aren't any books by chosen year");
    }

    @Test
    void shouldThrowExceptionWhenYearIsInvalid() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookManager.findBooksByYear(-1));
    }

    // ===== findBooksByGenre() tests =====

    @Test
    void shouldFindAllBooksByGenre() {
        String chosenGenre = "Horror";
//        String chosenGenre = "Fiction";
        List<Book> bookList = bookManager.findBooksByGenre(chosenGenre);
        assertFalse(bookList.isEmpty(), "Test should return all books by chosen genre");
    }

    @Test
    void shouldReturnEmptyListWhenNoBooksByGenre() {
        // Arrange
        String genre = "Comedy";

        // Act
        List<Book> books = bookManager.findBooksByGenre(genre);

        // Assert
        assertTrue(books.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenGenreIsNullInFind() {
        assertThrows(IllegalArgumentException.class, () -> bookManager.findBooksByGenre(null), "Test should throw exception if genre null");
    }

    // ===== removeBooksByAuthor() tests =====

    @Test
    void shouldRemoveAllBooksByAuthor() {
        String author = "Stephen King";

        bookManager.removeBooksByAuthor(author);

        assertTrue(bookManager.findBookByAuthor(author).isEmpty(), "Test should return all books by chosen author");
    }

    @Test
    void shouldDoNothingWhenRemovingNonExistingAuthor() {
        String author = "Test";
        assertDoesNotThrow(() -> bookManager.removeBooksByAuthor(author), "If author in collection doesnt exist, test do nothing");
    }

    @Test
    void shouldThrowExceptionWhenAuthorIsNullInRemove() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookManager.removeBooksByAuthor(null));
    }

    // ===== sortBooksByCriterion() tests =====

    @Test
    void shouldSortBooksByTitle() {

        bookManager.sortBooksByCriterion("title");

        List<String> actual = List.of(
                "Fairy Tale",
                "Life's Journey",
                "Science Explained",
                "Space Odyssey",
                "The Great Adventure"
        );

        List<String> expected = bookManager.getBooks().stream()
                .map(Book::getTitle)
                .toList();

        assertEquals(expected, actual, "All books in collection should be sort by title");
    }

    @Test
    void shouldSortBooksByAuthor() {
        List<String> expected = bookManager.listOfAllAuthors().stream().sorted().toList();

        bookManager.sortBooksByCriterion("author");

        List<String> actual = bookManager.listOfAllAuthors();

        assertEquals(expected, actual, "All books should be sorted by authors");
    }

    @Test
    void shouldSortBooksByYear() {
        bookManager.sortBooksByCriterion("year");

        List<Integer> sortedListByYear = bookManager.getBooks().stream().map(Book::getPublicationYear).toList();

        List<Integer> expectedYears = List.of(2019, 2021, 2022, 2022, 2024);

        assertEquals(expectedYears, sortedListByYear, "All books in collection should be sort by year");
    }

    @Test
    void shouldThrowExceptionWhenCriterionIsInvalid() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookManager.sortBooksByCriterion("invalid"));
    }

    // ===== mergeCollections() tests =====

    @Test
    void shouldMergeNonDuplicateBooks() {

        List<Book> testList = List.of(
                new Book("Fairy Tale", "Stephen King", "Horror", 2022),
                new Book("It", "Stephen King", "Horror", 1986),
                new Book("Dune", "Frank Herbert", "Sci-Fi", 1965)
        );

        bookManager.mergeCollections(testList);

        assertEquals(7, bookManager.size(), "Test should merge two collection without duplicates");
    }

    @Test
    void shouldSkipDuplicateBooksWhenMerging() {

        List<Book> testList = List.of(
                new Book("Fairy Tale", "Stephen King", "Horror", 2022),
                new Book("It", "Stephen King", "Horror", 1986),
                new Book("Dune", "Frank Herbert", "Sci-Fi", 1965)
        );

        bookManager.mergeCollectionsWithDuplicates(testList);

        List<Book> mergedList = bookManager.getBooks();
        assertEquals(8, mergedList.size(), "Merged collection should be size 8 elements");
    }

    @Test
    void shouldThrowExceptionWhenMergingNullCollection() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookManager.mergeCollections(null));
    }

    @Test
    void shouldSkipNullBooksInCollection() {

        List<Book> currentList = bookManager.getBooks();

        currentList.add(null);
        List<Book> resultList = currentList.stream().toList();

        assertEquals(6, resultList.size(), "Should skip null object in collection");
    }

    // ===== subCollectionByGenre() tests =====

    @Test
    void shouldReturnSubCollectionByGenre() {
        String genre = "Horror";
        List<Book> subCollectionByGenre = bookManager.subCollectionByGenre(genre);
        assertFalse(subCollectionByGenre.isEmpty(), "Return collection with books chosen by genre");
    }

    @Test
    void shouldReturnEmptySubCollectionForNonExistingGenre() {
        String genre = "Love story";
        List<Book> subCollectionByGenre = bookManager.subCollectionByGenre(genre);
        assertTrue(subCollectionByGenre.isEmpty(), "If genre not found, collection should be with zero elements ");
    }

    @Test
    void shouldThrowExceptionWhenGenreIsNullInSubCollection() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookManager.subCollectionByGenre(null));
    }

    // ===== size() tests =====

    @Test
    void shouldReturnCorrectSize() {
        // Arrange - дані в setUp()

        // Act
        int size = bookManager.size();

        // Assert
        assertEquals(5, size);
    }

    @Test
    void shouldReturnZeroForEmptyCollection() {
        // Arrange
        BookManager emptyManager = new BookManager();

        // Act
        int size = emptyManager.size();

        // Assert
        assertEquals(0, size);
    }

}