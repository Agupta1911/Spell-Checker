# Spell-Checker
A Java program that simulates a spell checker that identifies misspelled words and suggests corrections.

Spell Checking with Hash Maps:

A Java program that simulates a spell checker that identifies misspelled words and suggests corrections.

Key Features:

Implement a hash table to store a dictionary of correctly spelled words.

Create a function to check if a word is spelled correctly.

Implement a function to suggest corrections for misspelled words.

Use at least one collision resolution technique.

Provide a simple command-line interface for users to input words or text for spell-checking.

Include comments in your code explaining your implementation choices and any challenges faced.

Use a simple hash function (e.g., sum of ASCII values modulo table size).

Implement separate chaining for collision resolution. Hint: Use arrays for the hash table and ArrayLists for the buckets. 

Suggest corrections based on words with the same first letter with a string length +/- 1 of the original string length

Use a small dictionary (e.g., 1000 common English wordsLinks to an external site.).

Implement a function to add new words to the dictionary.

Display the load factor of the hash table

Use the polynomial hash function.

For collision resolution, implement a binary search tree for each bucket to store values that collide for a particular. The BST improves the  time complexity of the get/set/search operation.

Suggest corrections based on words with the same two starting letters with a string length +/- 1 of the original string length

Implement a function to remove words from the dictionary.

Use a medium-sized dictionary (e.g., 172K English wordsLinks to an external site.).

Implement open addressing with linear probing and quadratic probing and compare the two for performance

Use a large dictionary (e.g., 400K+ English wordsLinks to an external site.).

Implement a simple file input option to spell-check entire text documents.

Implement dynamic resizing of the hash table when the load factor exceeds a certain threshold.

Instructions:

Follow prompts for user inputs.

The dictionary is read from a text file named Dictionary.txt 

A sample test text (test.txt) is given to check that the program can spell check an entire document.

You can switch between linear probing and quadratic probing in the program to compare performance, when you switch the dictionary is reread into the program from the file (therefore any added or removed words from the dictionary hash table are reset and undone).

I limited the maximum number of suggestions to be 5.
