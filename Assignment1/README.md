## Assignment 1
The purpose of this project is: given two words, create a ladder between them, where each step of the ladder is each word being only one letter off from the next. This project is composed of 4 Java files.

_Queue.java_ implements a generic Queue and a generic Node, both via a linked list. The queue has two specific functions: enqueuing elements at the head and dequeuing at the tail, using the FIFO principles. 

_WordInfo.java_ represents information about a word, including the word itself, the number of moves associated with it, and it's history. It provides basic methods such as getWord, getMoves, and getHistory. 

_LadderGame.java_ creates the structure of the game, where players try to form a ladder of words starting from a given word and ending at another word. 

_WordLadders.java_ is where the game is ultimately played out with a total of 8 different ladders being attempted. This is the configuration was based. Based on the given `dictionary.txt`, only one ladder is not able to be found, showcasing the effectiveness of the implemented algorithms and data structures.

