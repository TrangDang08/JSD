import os
import json

# Create a function named countWords_1() that takes two file names as the inputs. 
# The first one is the input file’s name (a text file) and 
# the second one is the output file’s name (a JSON file). 
# The function will count the number of times the words in the input text
# file occur then write the result (in JSON format) into the output file.
def countWords_1(file1, file2):
    # open and read file
    with open(file1, 'r') as f:
        data = f.read().lower() # convert all words into lower case

    # replace line break with space
    wordListRaw = data.replace('\n', ' ')
    # split all words into seperate item in list
    wordList = wordListRaw.split()
    
    # add a new non-existed word into frequency dictionary 
    # and change frequency each time the word repeat again
    # as key and value pairs
    frequency = {}
    for word in wordList:
        if word not in frequency:
            frequency[word] = 0
        frequency[word] += 1

    # write dictionary result into json file
    with open(file2, 'w') as json_file:
        json.dump(frequency, json_file)

# example
countWords_1('Assignment2_Sample_Inputs/sample.txt', 'sample_out_1.json')